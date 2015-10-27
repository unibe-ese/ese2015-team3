package org.sample.controller;

import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileControlle {

@Autowired
private UserDao userDao;
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView showProfile() {
		 ModelAndView model = new ModelAndView("profile");
		 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addObject("user", userDao.findByUsername(userDetail.getUsername()));
		  }
		  
	 

	  return model;

	}
	

}