package org.sample.controller;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.service.TutorShipService;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.MessageSubjectDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Allows the user to see the messages he received and writing new ones
 * @author pf15ese
 */
@Controller
public class TutorShipConfirmationController {

@Autowired
private UserDao userDao;
@Autowired
private TutorShipService tutorShipService;
	

@RequestMapping(value = "/confirmTutorShip", method = RequestMethod.GET)
public ModelAndView confirmTutorShip(@RequestParam(value = "tutorUserId", required = true) Long tutorUserId) {
	User user = getUserFromSecurityContext();
	Tutor tutor = userDao.findOne(tutorUserId).getTutor();
	if(tutor == null)
		return new ModelAndView("confirmFailed");
	try{
		tutorShipService.confirmTutorShip(tutor, user);
		return new ModelAndView("confirmed");
	}
	catch(InvalidTutorShipException e){
		return new ModelAndView("confirmFailed");
	}

}
	
	private User getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     String name = authentication.getName();
		 User user = userDao.findByUsername(name);
		return user;
	}
	
}