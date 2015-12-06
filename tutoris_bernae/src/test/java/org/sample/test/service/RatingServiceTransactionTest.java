package org.sample.test.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.pojos.RatingForm;
import org.sample.controller.service.RatingService;
import org.sample.model.Rating;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.TutorShipDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ServiceTransactionTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RatingServiceTransactionTest extends ServiceTransactionTest{	
	@Autowired
    private RatingService ratingService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private TutorShipDao tutorShipDao;
	
	private User user;
	private Tutor tutor;
	
	@Before
	public void setUp(){
		user = new User();
		tutor = new Tutor();
		userDao.save(user);
		tutorDao.save(tutor);
	}

    @Test
    public void ratingSavedInDatabase() {
        RatingForm ratingForm = new RatingForm();
        ratingForm.setFeedback("Feedback");
        ratingForm.setRatedTutorId(tutor.getId());
        ratingForm.setRating(new BigDecimal(1));
        
        TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShipDao.save(tutorShip);
        
        ratingService.saveFrom(ratingForm, user);
        
        Rating savedRating = tutor.getComments().iterator().next();
        assertEquals("Feedback",savedRating.getFeedback());
        assertEquals(user,savedRating.getCommentator());
        assertEquals(new BigDecimal(1),savedRating.getRating());
    }
    
    @Test(expected = InvalidTutorShipException.class)
    public void canOnlyRateExisistingTutorShips() {
        RatingForm ratingForm = new RatingForm();
        ratingForm.setFeedback("Feedback");
        ratingForm.setRatedTutorId(tutor.getId());
        ratingForm.setRating(new BigDecimal(1));
        
        ratingService.saveFrom(ratingForm, user);
        
        Rating savedRating = tutor.getComments().iterator().next();
        assertEquals("Feedback",savedRating.getFeedback());
        assertEquals(user,savedRating.getCommentator());
        assertEquals(new BigDecimal(1),savedRating.getRating());
    }
    
    @Test(expected = InvalidTutorShipException.class)
    public void cannotRateASingleTutorShipTwice() {
        RatingForm ratingForm = new RatingForm();
        ratingForm.setFeedback("Feedback");
        ratingForm.setRatedTutorId(tutor.getId());
        ratingForm.setRating(new BigDecimal(1));
        
        TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setRated(true);
		tutorShipDao.save(tutorShip);
        
        ratingService.saveFrom(ratingForm, user);
        
        Rating savedRating = tutor.getComments().iterator().next();
        assertEquals("Feedback",savedRating.getFeedback());
        assertEquals(user,savedRating.getCommentator());
        assertEquals(new BigDecimal(1),savedRating.getRating());
    }
    

}