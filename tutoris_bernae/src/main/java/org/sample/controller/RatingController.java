package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.pojos.RatingForm;
import org.sample.controller.service.RatingService;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Allows a user to give a vote to a tutor
 * computes the tutor's rating as the average of all his votes
 *
 */
@Controller
public class RatingController extends PageController {
	@Autowired
	private RatingService ratingService;
	/**
	 * allows the current user to add a vote to a tutor
	 * a user can only rate, once, a tutor with whom he had a confirmed tutorship
	 * @param tutorUserName the username of the tutor that is being rated by the user
	 * @param vote the vote assigned to the tutor
	 * @param feedback a message to explain the vote
	 * @return ModelAndView with a confirmation or an error message
	 */
	@RequestMapping(value = "/rate", method = RequestMethod.GET)
	public ModelAndView ratingPage(@RequestParam(value = "tutorId", required = true) Long tutorId){
		try{
			RatingForm ratingForm = ratingService.createRatingForm(tutorId, getCurrentUser());
			ModelAndView model = new ModelAndView("rate");
			model.addObject("ratingForm", ratingForm);
			return model;
		}
		catch(InvalidTutorShipException e){
			ModelAndView model = new ModelAndView("ratingFailed");
			model.addObject("page_error", e.getMessage());
			return model;
		}		
	}
	
	@RequestMapping(value = "/submitRating", method = RequestMethod.POST)
	public ModelAndView ratingPage(@Valid RatingForm ratingForm, BindingResult result){
		if (!result.hasErrors()){
    		try{
    			ratingService.saveFrom(ratingForm, getCurrentUser());
    			return new ModelAndView("ratedSuccessfull");	
    		}
    		catch(InvalidTutorShipException e){
    			ModelAndView model = new ModelAndView("ratingFailed");
    			model.addObject("page_error", e.getMessage());
    			return model;
    		}
    	}
    	else { 
    		ModelAndView model = new ModelAndView("rate");
			model.addObject("ratingForm", ratingForm);
			return model;
    	}
	}
}
