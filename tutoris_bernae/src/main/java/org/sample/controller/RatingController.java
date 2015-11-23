package org.sample.controller;

import java.util.List;

import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class RatingController {
	
@Autowired
private UserDao userDao;
	
@Autowired
private TutorDao tutorDao;
	
	/**
	 * allows the current user to add a vote to a tutor
	 * a user can only rate, once, a tutor with whom he had a confirmed tutorship
	 * @param tutorUserName the username of the tutor that is being rated by the user
	 * @return ModelAndView with a confirmation or an error message
	 */
	@RequestMapping(value = "/voteTutor", method = RequestMethod.GET)
	public ModelAndView addVote(@RequestParam(value = "tutorUserName", required = true) String tutorUserName, 
			@RequestParam(value = "vote", required = true) int vote) {
		ModelAndView model = new ModelAndView();
		User user = UserHelper.getUserFromSecurityContext(userDao);
		User tutorUser = userDao.findByUsername(tutorUserName);
		assert tutorUser.isTutor();
		Tutor tutor = tutorDao.findByStudent(tutorUser);
		
		List<User> canBeRatedBy = tutor.getCanBeRatedBy(); // to implement after tutorship confirmation!
		
		if (canBeRatedBy.contains(user)) {
			updateRating(tutor, vote);
			canBeRatedBy.remove(user);
			tutor.setCanBeRatedBy(canBeRatedBy);
			String message = "Your vote has been accepted!";
			model.addObject("page_message", message);
		} else {
			String message = "Error: you can only give a vote to tutors you've studied with.";
			model.addObject("page_error", message);
		}
		
		return model;
	}
	
	/**
	 * updates the ratings of the tutor, with a new average which accounts for the new vote
	 * @param tutor the tutor whose rating will be updated
	 * @param vote the vote to be added to the tutor's rating
	 */
	public void updateRating(Tutor tutor, int vote) {
		int oldNRatings = tutor.getNRatings();
		if (oldNRatings < 1) {
			tutor.setNRatings(1);
			tutor.setRating((double) vote);
		}
		int oldScore = (int) (tutor.getRating() * oldNRatings + 0.5);
		tutor.setNRatings(oldNRatings + 1);
		tutor.setRating((oldScore + vote) / (double) (oldNRatings + 1));
	}
}
