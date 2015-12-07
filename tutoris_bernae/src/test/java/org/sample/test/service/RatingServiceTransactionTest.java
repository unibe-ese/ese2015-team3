package org.sample.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	public void createsCorrectRatingForm(){
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);

		RatingForm createdForm = ratingService.createRatingForm(tutor.getId(), user);

		assertEquals(tutor.getId(),createdForm.getRatedTutorId());
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
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);
        
        ratingService.saveRating(ratingForm, user);
        
        Rating savedRating = tutor.getRatings().iterator().next();
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
        
        ratingService.saveRating(ratingForm, user);
        
        Rating savedRating = tutor.getRatings().iterator().next();
        assertEquals("Feedback",savedRating.getFeedback());
        assertEquals(user,savedRating.getCommentator());
        assertEquals(new BigDecimal(1),savedRating.getRating());
    }
    
    @Test(expected = InvalidTutorShipException.class)
    public void canOnlyRateConfirmedTutorShips() {
        RatingForm ratingForm = new RatingForm();
        ratingForm.setFeedback("Feedback");
        ratingForm.setRatedTutorId(tutor.getId());
        ratingForm.setRating(new BigDecimal(1));
        
        TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShipDao.save(tutorShip);
		
        ratingService.saveRating(ratingForm, user);
        
        Rating savedRating = tutor.getRatings().iterator().next();
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
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);
        
		ratingService.saveRating(ratingForm, user);
        ratingService.saveRating(ratingForm, user);
        
        Rating savedRating = tutor.getRatings().iterator().next();
        assertEquals("Feedback",savedRating.getFeedback());
        assertEquals(user,savedRating.getCommentator());
        assertEquals(new BigDecimal(1),savedRating.getRating());
    }
    
    
	/**
	 * Tests all relevant cases in which a user can rate a tutor or not
	 */
    //This test case is not split up because the messages in the assert statements should
    //make clear what failed, and naming each case different wouldn't result in a better
    //overview
	@Test
	public void canRateTutorBooleanExpressionIsCorrect(){
		assertFalse("Should be false because there exist no tutorship",ratingService.canRateTutor(tutor.getId(),user));
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setRated(true);
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);
		assertFalse("Should be false because the tutorship is rated",ratingService.canRateTutor(tutor.getId(),user));
		tutorShipDao.delete(tutorShip.getId());
		TutorShip tutorShip2 = new TutorShip();
		tutorShip2.setStudent(user);
		tutorShip2.setTutor(tutor);
		tutorShip2.setConfirmed(false);
		tutorShipDao.save(tutorShip2);
		assertFalse("Should be false because the tutorship is unconfirmed",ratingService.canRateTutor(tutor.getId(),user));
		tutorShipDao.delete(tutorShip2.getId());
		TutorShip tutorShip3 = new TutorShip();
		tutorShip3.setStudent(user);
		tutorShip3.setTutor(tutor);
		tutorShip3.setConfirmed(true);
		tutorShipDao.save(tutorShip3);
		assertTrue("Should be true because the tutorship is confirmed and unrated",ratingService.canRateTutor(tutor.getId(),user));
	}
    

}