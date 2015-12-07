package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.pojos.RatingForm;
import org.sample.controller.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Allows a user to rate to a tutor by creating a page with a RatingForm
 * as well as a method to accept the filled out RatingForm
 */
@Controller
public class RatingController extends PageController {
	
	@Autowired
	private RatingService ratingService;
	/**
	 * Allows the current user to rate a tutor by presenting a site with a 
	 * RatingForm.
	 * A user can only rate, once, a tutor with whom he had a confirmed tutorship
	 * If he cannot rate the tutor, a page with a failure message is displayed
	 * @param tutorId the id of the tutor that will get rated by the user
	 * @return ModelAndView with ViewName "rate" and an object "ratingForm" a new RatingForm, or if
	 * the user can't rate the tutor a ModelAndView with ViewName "ratingFailed" and the reason
	 * as a ModelAttribute "page_error"
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
	
	/**
	 * Saves the rating given by the rating form and shows a success page
	 * or goes back to the "rate"-page if the form is no valid.
	 * If the user cannot rate the tutor because there is no confirmed and unrated
	 * tutorship between them, the rating process is stopped by showing the "ratingFailed"-page
	 * and ignoring the ratingForm
	 * @param ratingForm a RatingForm which gets validated
	 * @param result the BindingResult of the ratingForm validation
	 * @return ModelAndView with Viewname "ratedSuccessfull" if the form was valid and the user
	 * was allowed to rate the tutor. If the form is invalid again a ModelAndView with Viewname
	 * "rate" and the object "ratingForm" a ratingForm is returned. If the user wasn't allowed to 
	 * rate the tutor a ModelAndView with ViewName "ratingFailed" and the object "page_error" the reason
	 * for the failure is returned.
	 */
	@RequestMapping(value = "/submitRating", method = RequestMethod.POST)
	public ModelAndView submitRating(@Valid RatingForm ratingForm, BindingResult result){
		if (!result.hasErrors()){
    		try{
    			ratingService.saveRating(ratingForm, getCurrentUser());
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
