package org.sample.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;

import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.MailService;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controls the creation of the homepage
 *
 */
@Controller
public class IndexController {
    @Autowired
    private UserDao userDao;
    private static final String SESSIONATTRIBUTE_USER="loggedInUser";
    
    /**
     * Creates the homepage. Users also get here after logging out.
     * @param logout a non required String RequestParam
     * @param session
     * @return a ModelAndView with ViewName "index", if the user got here by logging out the object "msg"
     * a logout message is added
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@RequestParam(value = "logout", required = false) String logout, HttpSession session) {
    	ModelAndView model = new ModelAndView("index");
    	 if (logout != null) {
    			model.addObject("message", "You've been logged out successfully.");
                        session.removeAttribute(SESSIONATTRIBUTE_USER);
    		  }
         User user = getUserFromSecurityContext();
         if (user != null) {
             session.setAttribute(SESSIONATTRIBUTE_USER, user);
         }
         
        return model;
    }

    private User getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userDao.findByEmailLike(name);
        return user;
    }

}
  
