
package org.sample.controller;

import org.sample.model.Tutor;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controls the creation of the profile page for viewing other tutors, without their user details.
 * @author pf15ese
 *
 */
@Controller
public class ViewTutorProfileController {

	@Autowired
	private TutorDao tutorDao;

	/**
	 * Creates the profile page for the tutor given by the tutorId, completely without the tutors user
	 * details
	 * @param tutorId the id of the tutor i want to see, not required
	 * @return a ModelAndView with ViewNam "viewTutorProfile" and the object "tutor", the tutor given by the id, 
	 * or if no tutor with this id exist or no id was given a ModelAndView with ViewName "notutorfound"
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewEditProfile(@RequestParam(value = "tutorId", required = false) Long tutorId) {
		ModelAndView model;
		if(tutorId==null) return new ModelAndView("notutorfound");
		
		Tutor tutor = tutorDao.findOne(tutorId);
		if(tutor != null) {
			model = new ModelAndView("viewTutorProfile");
			model.addObject("tutor", tutor);
			return model;
		}
			
		else {
			model = new ModelAndView("notutorfound");
			return model;
		}
	}

}
