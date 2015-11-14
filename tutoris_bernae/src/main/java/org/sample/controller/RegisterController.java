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
//@SessionAttributes(types = TutorForm.class)
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Classes.class, new ClassesEditor(classesDao));
		binder.registerCustomEditor(StudyCourse.class, new StudyCourseEditor(studyCourseDao));
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
     * Creates a tutor registration page for logged in users. This allows existing users
     * to upgrade their profile to a tutor profile
     * @return a ModelAndView with ViewName "tutorregistration" and attributes "tutorForm", a new TutorForm
     * and "studyCourseList", a list of all StudyCourses (for a dropdown-menu on the page)
     */
    @RequestMapping(value = "/upgrade", method = RequestMethod.GET)
    public ModelAndView upgradePage() {  	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String name = authentication.getName();
		User user = userDao.findByUsername(name);
    	TutorForm tutorForm = new TutorForm();
    	tutorForm.setUserId(user.getId());
        return createTutorFormPage(tutorForm);
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
        
    /**
     * A helping method for adding or deleting elements on the course and class list
     * @param session
     * @param request
     * @param tutorForm a TutorForm, must not be valid
     * @return ModelAndView with ViewName "tutorregistration" and ModelAttribute "tutorForm", the given TutortForm
     * with updated lists
     */

    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST)
    public ModelAndView updateListsForTutorForm(HttpSession session,HttpServletRequest request,@ModelAttribute TutorForm tutorForm) {
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	return createTutorFormPage(tutorForm);
    }
    
    /**
     * Saves the tutor details in the database and displays a succes page, or again the tutor register page if the
     * form was not filled out correctly
     * @param tutorForm a Valid TutorForm
     * @param save a required Boolean RequestParam, indicating that the form needs to be saved and not only updated
     * @param request
     * @return if the tutorform was successfully filled a new ModelAndView with ViewName "submitPage" or else
     * again a new ModelAndView with ViewName "tutorregistration" and ModelAttribute "tutorForm", the given TutorForm
     */
    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST, params = { "save" })
    public ModelAndView submitTutorForm(@Valid TutorForm tutorForm, BindingResult result,
            @RequestParam Boolean save,HttpServletRequest request) {
        ModelAndView model;
        tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
        if (!result.hasErrors()){
        	tutorFormService.saveFrom(tutorForm);
        	model = new ModelAndView(PAGE_SUBMIT);
        }
        else { 
        	model = createTutorFormPage(tutorForm);
        }
        return model;

    }
}
