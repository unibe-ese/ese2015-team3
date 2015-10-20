package org.sample.controller;
import java.util.ArrayList;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.service.FormService;
import org.sample.model.Team;
import org.sample.model.User;
import org.sample.model.dao.TeamDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    FormService formService;
    
    @RequestMapping(value = "/registerStudent", method = RequestMethod.GET)
    public ModelAndView registerStudent() {

        ModelAndView model = new ModelAndView("createProfile_student");
    	model.addObject("studentForm", new RegisterForm());
        return model;
    }
    

    @RequestMapping(value = "/createStudent", method = RequestMethod.POST)
    public ModelAndView createStudent(@ModelAttribute("studentForm") @Valid RegisterForm studentForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	formService.saveFrom(studentForm);
            	model = new ModelAndView("show-registration");
            } catch (InvalidUserException e) {
            	model = new ModelAndView("createProfile_student");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("createProfile_student");
        }   	
    	return model;
    }
    
}


