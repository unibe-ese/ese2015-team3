package org.sample.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

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

/**
 * Shows the profile of the logged in user or tutor
 * @author pf15ese
 */
@Controller
public class ProfileController {

@Autowired
private UserDao userDao;
private static final String SESSIONATTRIBUTE_USER="loggedInUser";	
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
	
		 User user = getUserFromSecurityContext();
                 session.setAttribute(SESSIONATTRIBUTE_USER, user);
		 model.addObject("user", user);
		 if(user.isTutor())
			 model.addObject("tutor", user.getTutor());	 
		 return model;
	}
   
    private User getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userDao.findByEmailLike(name);
        return user;
    }
}