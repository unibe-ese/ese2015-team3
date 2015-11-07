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
import org.sample.model.User;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class RegisterController {
	public static final String PAGE_SUBMIT = "submitPage";
	public static final String PAGE_REGISTER = "register";
	
    @Autowired
    private StudyCourseDao studyCourseDao;
	@Autowired
	private RegisterFormService registerFormService;
	@Autowired
	private TutorFormService tutorFormService;
	@Autowired
	private UserDao userDao;
	
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerBasePage() {  	
    	ModelAndView model = new ModelAndView(PAGE_REGISTER);
    	model.addObject("registerForm", new RegisterForm());
        return model;
    }

    @RequestMapping(value = "/upgrade", method = RequestMethod.GET)
    public ModelAndView upgradePage() {  	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String name = authentication.getName();
		User user = userDao.findByUsername(name);
        return createTutorFormPage(user.getId());
    }
    
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView create(HttpSession session,HttpServletRequest request,@Valid RegisterForm registerForm, BindingResult result, 
    						@RequestParam(value = "registerastutor", required = false) String registerastutor) {
    	ModelAndView model;  
    	
    	if (!result.hasErrors()) {
            try {
            	registerForm = registerFormService.saveFrom(registerForm);
            	
            	if(registerastutor!=null)
            	{
                	return createTutorFormPage(registerForm.getId());
            	}
            	return model = new ModelAndView(PAGE_SUBMIT);
            } catch (InvalidUserException e) {
            	model = new ModelAndView(PAGE_REGISTER);
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView(PAGE_REGISTER);
        }   

    	return model;
    }
    
    public ModelAndView createTutorFormPage(Long id)
    {
    	ModelAndView model = new ModelAndView(PAGE_SUBMIT);
    	model = new ModelAndView("tutorregistration");
    	TutorForm tutorForm = new TutorForm();
    	tutorForm.setUserId(id);
    	model.addObject("tutorForm", tutorForm);
        model.addObject("studyCourseList", studyCourseDao.findAll());
        return model;
    }
        
    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST)
    public ModelAndView create(HttpSession session,HttpServletRequest request,@ModelAttribute TutorForm tutorForm) {
    	ModelAndView model = new ModelAndView("tutorregistration");
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	model.addObject("tutorForm", tutorForm);
    	return model;
    }
    
    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST, params = { "save" })
    public ModelAndView create(@Valid TutorForm tutorForm, HttpSession session,
    		@RequestParam Boolean save,HttpServletRequest request) {
    	ModelAndView model = new ModelAndView(PAGE_SUBMIT);
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	tutorFormService.saveFrom(tutorForm);
    	return model;
    }
    
}
