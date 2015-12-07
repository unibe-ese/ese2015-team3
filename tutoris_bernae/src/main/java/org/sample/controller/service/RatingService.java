package org.sample.controller.service;

import java.util.List;
import java.util.Set;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RatingForm;
import org.sample.model.Rating;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.RatingDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.TutorShipDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {
	@Autowired 
	private TutorDao tutorDao;
	@Autowired 
	private RatingDao ratingDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TutorShipDao tutorShipDao;
	
	/**
	 * True if the user is able to rate the tutor, so if there 
	 * is an unrated, confirmed tutorship between them
	 * @param ratedTutorId the id of the tutor that should get rated
	 * @param commentator the user who wants to rate the tutor
	 * @return true if there is a confirmed tutorship between the user and the tutor which is unrated
	 */
	public boolean canRateTutor(Long ratedTutorId, User commentator){
		Tutor ratedTutor = tutorDao.findOne(ratedTutorId);
		TutorShip ratedTutorShip = tutorShipDao.findByTutorAndStudent(ratedTutor, commentator);
		return (ratedTutorShip!=null)&&(!ratedTutorShip.getRated())&&(!ratedTutorShip.getConfirmed());
	}
	
	public RatingForm createRatingForm(Long ratedTutorId, User commentator) throws InvalidTutorShipException{
		Tutor ratedTutor = tutorDao.findOne(ratedTutorId);
    	TutorShip ratedTutorShip = tutorShipDao.findByTutorAndStudent(ratedTutor, commentator);
    	if(ratedTutorShip==null||!ratedTutorShip.getConfirmed()) throw new InvalidTutorShipException("There exists no confirmed tutorship between you and this tutor");
    	if(ratedTutorShip.getRated()) throw new InvalidTutorShipException("This tutorship is already rated");
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
    @Transactional
	public void saveFrom(RatingForm ratingForm, User commentator) throws InvalidTutorShipException{
    	assert commentator!=null;
    	Tutor ratedTutor = tutorDao.findOne(ratingForm.getRatedTutorId());
    	TutorShip ratedTutorShip = tutorShipDao.findByTutorAndStudent(ratedTutor, commentator);
    	if(ratedTutorShip==null||!ratedTutorShip.getConfirmed()) throw new InvalidTutorShipException("There exists no confirmed tutorship between this student and tutor");
    	if(ratedTutorShip.getRated()) throw new InvalidTutorShipException("This tutorship is already rated");
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





}