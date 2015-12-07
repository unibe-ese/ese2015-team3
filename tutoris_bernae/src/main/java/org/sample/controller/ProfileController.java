package org.sample.controller;

import javax.servlet.http.HttpSession;

import org.sample.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Shows the profile of the logged in user or tutor
 */
@Controller
public class ProfileController extends PageController{


	/**
	 * Creates a page with all user informations of the current logged in user. If the user is also a tutor
	 * all tutor informations are added to the page as well
	 * @return ModelAndView with ViewName "profile" and ModelAttribute "user", the logged in user
	 * and if the user was a tutor there exist the ModelAttribute "tutor" the logged in users tutor
	 * informations
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView showProfile(HttpSession session) {
		 ModelAndView model = new ModelAndView("profile");
	
		 User user = getCurrentUser();
		 model.addObject("user", user);
		 if(user.getTutor()!=null)
			 model.addObject("tutor", user.getTutor());	 
		 return model;
	}

}