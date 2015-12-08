package org.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controls the creation of the homepage
 *
 */
@Controller
public class IndexController extends PageController{
    
    /**
     * Creates the homepage. Users also get here after logging out.
     * @param logout a non required String RequestParam
     * @param session
     * @return a ModelAndView with ViewName "index", if the user got here by logging out the object "message"
     * a logout message is added
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@RequestParam(value = "logout", required = false) String logout, HttpSession session) {
    	ModelAndView model = new ModelAndView("index");
    	 if (logout != null) {
    			model.addObject("message", "You've been logged out successfully.");
                        session.removeAttribute(SESSIONATTRIBUTE_USER);
    		  }
        return model;
    }
}
  
