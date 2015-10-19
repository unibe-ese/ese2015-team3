package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserPasswordException;
import org.sample.controller.pojos.LoginForm;
import org.sample.controller.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    FormService formService;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
    	ModelAndView model = new ModelAndView("login");    	
    	model.addObject("loginForm", new LoginForm());
        return model;
    }
    
    @RequestMapping(value = "/login-create", method = RequestMethod.POST)
    public ModelAndView create(@Valid LoginForm loginForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;
    	if (!result.hasErrors()) {
            try {
            	formService.saveFrom(loginForm);
            	model = new ModelAndView("show-login");
            } catch (InvalidUserPasswordException e) {
            	model = new ModelAndView("login");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("index");
        }   	
    	return model;
    }
        
	
}
