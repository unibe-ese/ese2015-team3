package org.sample.controller.service;

import java.util.Set;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.pojos.RatingForm;
import org.sample.model.Rating;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.RatingDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.TutorShipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Enables a user to rate a tutor by creating ratingForms
 * and saving them. Also validates that a user is allowed 
 * to rate a specific tutor. (He is only allowed to do so
 * if there exists an confirmed, unrated tutorship between him
 * and the tutor)
 */
@Service
public class RatingService {
	@Autowired 
	private TutorDao tutorDao;
	@Autowired 
	private RatingDao ratingDao;
	@Autowired
	private TutorShipDao tutorShipDao;
	
	/**
	 * True if the user is able to rate the tutor, so if there 
	 * is an unrated, confirmed tutorship between them
	 * This returns no specific reason why the user can't rate the tutor
	 * so use this only if you don't want to give feedback to the user why he 
	 * can not rate this tutor
	 * @param ratedTutorId the id of the tutor that should get rated
	 * @param commentator the user who wants to rate the tutor
	 * @return true if there is a confirmed tutorship between the user and the tutor which is unrated
	 */
	public boolean canRateTutor(Long ratedTutorId, User commentator){
		Tutor ratedTutor = tutorDao.findOne(ratedTutorId);
		TutorShip ratedTutorShip = tutorShipDao.findByTutorAndStudent(ratedTutor, commentator);
		return (ratedTutorShip!=null)&&(!ratedTutorShip.getRated())&&(ratedTutorShip.getConfirmed());
	}
	
	
	/**
	 * Creates a ratingForm to rate the tutor given by the ratedTutorId
	 * and throws exception if its impossible to create the rating form
	 * (if there exist no confirmed, unrated tutorShip between the commentator and
	 * the tutor given by the id)
	 * It is recommended to use this instead of directly using the constructor of the ratingForm
	 * to recognize if it makes no sense to create a ratingForm, because it wouldn't
	 * be accepted when saved and also to get a specific reason via the message of the thrown
	 * exception
	 * @param ratedTutorId the id of the tutor which you want to rate
	 * @param commentator the user which wants to rate a tutor
	 * @return a ratingForm with ratedTutorId set to the given ratedTutorId
	 * @throws InvalidTutorShipException if there is no confirmed, unrated tutorship
	 * between the commentator and the tutor given by the ratedTutorId
	 */
	//See the comment for checkTutorShipCanBeRated() why we throw exceptions here
	//as well as in saveFrom()
	public RatingForm createRatingForm(Long ratedTutorId, User commentator) throws InvalidTutorShipException{
		Tutor ratedTutor = tutorDao.findOne(ratedTutorId);
    	TutorShip ratedTutorShip = tutorShipDao.findByTutorAndStudent(ratedTutor, commentator);
    	checkTutorShipCanBeRated(ratedTutorShip);
    	RatingForm ratingForm = new RatingForm();
		ratingForm.setRatedTutorId(ratedTutorId);
		return ratingForm;
	}
	
	/**
     * Creates a tutor out of a TutorForm and save him to the database, and also
     * updates the user belonging to that tutor.
     * @param tutorForm a TutorForm, not null
	 * @throws InvalidTutorShipException if there is no confirmed, unrated tutorship
	 * between the commentator and the tutor given by the ratingform
     */
	//See the comment for checkTutorShipCanBeRated() why we throw exceptions here
	//as well as in createRatingForm()
    @Transactional
	public void saveRating(RatingForm ratingForm, User commentator) throws InvalidTutorShipException{
    	assert commentator!=null;
    	Tutor ratedTutor = tutorDao.findOne(ratingForm.getRatedTutorId());
    	TutorShip ratedTutorShip = tutorShipDao.findByTutorAndStudent(ratedTutor, commentator);
    	checkTutorShipCanBeRated(ratedTutorShip);
    	Rating rating = new Rating();
    	rating.setCommentator(commentator);
    	rating.setFeedback(ratingForm.getFeedback());
    	rating.setRating(ratingForm.getRating());
    	rating = ratingDao.save(rating);
    	Set<Rating> existingRatings = ratedTutor.getRatings();
    	existingRatings.add(rating);
    	ratedTutor.setRatings(existingRatings);
    	tutorDao.save(ratedTutor);
    	ratedTutorShip.setRated(true);
    	tutorShipDao.save(ratedTutorShip);
	}

    //Checks if a tutorShip can be rated, and throws a InvalidTutorShipException if not
    //we need this although we have the canRateTutor() because we want to
    //throw exceptions with different messages for different causes and not one general
    //We also need to use this method to be called in the createRatingForm() and
    //saveFrom(). Because for example if its only in the creation of the form you can create two rating forms and
    //then hand in both and both get saved. If it would be only in the saveForm() you would possibly try to rate a
    //tutor and only after writing the rating and handing it in you would know that you should not have been able to do that
    private void checkTutorShipCanBeRated(TutorShip ratedTutorShip) throws InvalidTutorShipException{
    	if(ratedTutorShip==null) throw new InvalidTutorShipException("There exists no tutorship between this student and tutor");
    	if(!ratedTutorShip.getConfirmed()) throw new InvalidTutorShipException("The tutorship between this student and tutor is not confirmed");
    	if(ratedTutorShip.getRated()) throw new InvalidTutorShipException("This tutorship is already rated");
    }

}
