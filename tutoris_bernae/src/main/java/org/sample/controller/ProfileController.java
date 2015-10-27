package org.sample.controller;

import java.security.Principal;

import org.sample.model.User;
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
public class ProfileController {

@Autowired
private UserDao userDao;
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView showProfile(Principal pricipal) {
		 ModelAndView model = new ModelAndView("profile");
		 User user = userDao.findByUsername(pricipal.getName());
		 model.addObject("user", user);
		 if(user.isTutor())
			 model.addObject("tutor", user.getTutor());
	 

	  return model;

	}
	

}