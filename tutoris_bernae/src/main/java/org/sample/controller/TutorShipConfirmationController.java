package org.sample.controller;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.service.TutorShipService;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Creates the pages for confirming tutorships
 */
@Controller
public class TutorShipConfirmationController extends PageController{

@Autowired
private TutorDao tutorDao;
@Autowired
private TutorShipService tutorShipService;
	

/**
 * Creates the tutorship confirmed if a tutorship between the currently logged in user
 * and the user given by the tutorUserId exist and can be confirmed
 *  or a confirm failed page otherwise
 * @param tutorUserId the user id of the tutor you want to have a tutorship 
 * @return a ModelAndView with ViewName "confirmed" if the tutorship could be confirmed
 * or a a ModelAndView with ViewName "confirmFailed" otherwise
 */
@RequestMapping(value = "/confirmTutorShip", method = RequestMethod.GET)
public ModelAndView confirmTutorShip(@RequestParam(value = "tutorUserId", required = true) Long tutorUserId) {
	User user = getCurrentUser();
	Tutor tutor = tutorDao.findOne(tutorUserId);
	if(tutor == null){
		ModelAndView model = new ModelAndView("confirmFailed");
		model.addObject("page_error", "no tutorship found");
		return model;
	}
	try{
		tutorShipService.confirmTutorShip(tutor, user);
		return new ModelAndView("confirmed");
	}
	catch(InvalidTutorShipException e){
		ModelAndView model = new ModelAndView("confirmFailed");
		model.addObject("page_error", e.getMessage());
		return model;
	}

}
	
}