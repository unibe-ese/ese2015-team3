
package org.sample.controller;

import javax.servlet.http.HttpSession;

import org.sample.controller.service.RatingService;
import org.sample.model.Tutor;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controls the creation of the profile page for viewing other tutors, without their user details.
 */
@Controller
public class ViewTutorProfileController extends PageController{

	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private RatingService ratingService;

	/**
	 * Creates the profile page for the tutor given by the tutorId, completely without the tutors user
	 * details
	 * @param tutorId the id of the tutor i want to see, not required
	 * @return a ModelAndView with ViewName "viewTutorProfile" and the object "tutor", the tutor given by the id, 
	 * and also with "canRate) if the user is able to rate the tutor.
	 * If no tutor with this id exist or no id was given a ModelAndView with ViewName "notutorfound" is returned
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewEditProfile(@RequestParam(value = "tutorId", required = false) Long tutorId, HttpSession session) {
		ModelAndView model;
		if(tutorId==null) return new ModelAndView("notutorfound");
		
		Tutor tutor = tutorDao.findOne(tutorId);
                
		if(tutor != null) {
			model = new ModelAndView("viewTutorProfile");
			model.addObject("tutor", tutor);
			if(ratingService.canRateTutor(tutorId, getCurrentUser())) model.addObject("canRate","true");
			return model;
		}
		else {
			model = new ModelAndView("notutorfound");
			return model;
		}
	}
        


}
