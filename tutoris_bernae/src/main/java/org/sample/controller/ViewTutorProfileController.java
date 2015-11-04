
package org.sample.controller;

import org.sample.model.Tutor;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewTutorProfileController {

	@Autowired
	private TutorDao tutorDao;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewEditProfile(@RequestParam(required = false) Long tutorId) {
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
