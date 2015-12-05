package org.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.controller.service.TutorFormService;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.UserDao;
import org.sample.validators.UserEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controls the creating of user and tutor profiles, as well as the profile upgrade to tutor for normal user.
 * @author pf15ese
 *
 */
@Controller
public class RegisterController {
	public static final String PAGE_SUBMIT = "submitPage";
	public static final String PAGE_REGISTER = "register";
	
    @Autowired
    private StudyCourseDao studyCourseDao;
    @Autowired
    private ClassesDao classesDao;
	@Autowired
	private RegisterFormService registerFormService;
	@Autowired
	private TutorFormService tutorFormService;
	@Autowired
	private UserDao userDao;

    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;
    
	@InitBinder("registerForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UserEmailValidator(userDao));
	}
    
    /**
     * Creates the register page for users with a register form. This is 
     * also the first step for registering as a tutor, because every tutor needs a user profile
     * @return a ModelAndView with ViewName "register" and attribute "registerForm", a new RegisterForm 
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerBasePage() {
        ModelAndView model = new ModelAndView(PAGE_REGISTER);
        model.addObject("registerForm", new RegisterForm());
        return model;
    }
    
    /**
     * Saves a user profile and leads to the success page, or if the user wants to register as a tutor
     * the user profile is saved and a form for the Tutor registration is displayed
     * @param session
     * @param request
     * @param registerForm a Valid RegisterForm
     * @param result
     * @param registerastutor a not required Requested param. Not null means the user wants to register as a tutor
     * @return a ModelAndView with ViewName "submitPage" or if registerastutor is not null
     * a ModelAndView with ViewName "tutorregistration" and attributes "tutorForm", a new TutorForm
     * and "studyCourseList", a list of all StudyCourses (for a dropdown-menu on the page).
     * If the form was not valid, the register page is displayed again with the same registerform
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView submitRegisterForm(HttpSession session, HttpServletRequest request, @Valid RegisterForm registerForm, BindingResult result,
            @RequestParam(value = "registerastutor", required = false) String registerastutor) {
        ModelAndView model;
        
        if (!result.hasErrors()) {
            try { 
                registerForm = registerFormService.saveFrom(registerForm);
                if (registerastutor != null) {
                	TutorForm tutorForm = new TutorForm();
                	tutorForm.setUserId(registerForm.getId());
                    return createTutorFormPage(tutorForm);
                }
                authenticateUserAndSetSession(userDao.findOne(registerForm.getId()),request);
                model = new ModelAndView(PAGE_SUBMIT);
            } catch (InvalidUserException e) {
                model = new ModelAndView(PAGE_REGISTER);
                model.addObject("page_error", e.getMessage());
            }
        } else {
            model = new ModelAndView(PAGE_REGISTER);
        }   
    	return model;
    }
    
    private ModelAndView createTutorFormPage(TutorForm form)
    {
    	ModelAndView model = new ModelAndView("tutorregistration");
    	model.addObject("tutorForm", form);
    	model.addObject("allClasses", classesDao.findAll());
        model.addObject("allCourses", studyCourseDao.findAll());
        return model;
    }
    
    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String username = user.getUsername();
        String password = user.getPassword();
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);    
    }
}
