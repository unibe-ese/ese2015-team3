package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class RegisterController {
	public static final String PAGE_SUBMIT = "submitPage";
	public static final String PAGE_REGISTER = "register";
	
	@Autowired
	private FormService formService;
	
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerBasePage() {  	
    	ModelAndView model = new ModelAndView(PAGE_REGISTER);
    	model.addObject("registerForm", new RegisterForm());
        return model;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView create(@Valid RegisterForm registerForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	formService.saveFrom(registerForm);
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

}
