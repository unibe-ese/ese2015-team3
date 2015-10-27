package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.service.FormService;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EditController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private FormService formService;
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView viewEditProfile() {
		ModelAndView model = new ModelAndView("edit");
		model.addObject("editForm", new EditForm());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addObject("user", userDao.findByUsername(userDetail.getUsername()));
		}

		return model;
	}
	
    @RequestMapping(value = "/submitEdit", method = RequestMethod.POST)
    public ModelAndView editProfile(@Valid EditForm editForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	formService.saveFrom(editForm);
            	model = new ModelAndView("editDone");
            } catch (InvalidUserException e) {
            	model = new ModelAndView("edit");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("edit");
        }   	
    	return model;
    }
	
}
