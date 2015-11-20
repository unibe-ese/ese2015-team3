package org.sample.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;

import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.controller.service.SearchService;
import org.sample.controller.service.TutorFormService;
import org.sample.model.Classes;
import org.sample.model.ClassesEditor;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.StudyCourseEditor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.UserDao;
import org.sample.validators.ClassCourseListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
