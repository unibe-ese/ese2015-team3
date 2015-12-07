package org.sample.test.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.sample.controller.pojos.RatingForm;
import org.sample.model.Rating;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.RatingDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.TutorShipDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

public class RatingControllerIntegrationTest extends ControllerIntegrationTest{
	@Autowired
	private UserDao userDao;
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private TutorShipDao tutorShipDao;
	@Autowired
	private RatingDao ratingDao;
	
	private User user;
	private Tutor tutor;
	private MockHttpSession session;
	
	
	@Before
	public void setUp(){
		user = new User();
		user.setEmail("test@test.mail");
		user.setPassword("#etrZ4ad");
		userDao.save(user);
		tutor = new Tutor();
		tutorDao.save(tutor);
	}
	
	@Test
	public void ratingPageWithForm() throws Exception
	{
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(get("/rate?tutorId="+tutor.getId()).session(session)).andExpect(status().isOk())
									.andExpect(model().attribute("ratingForm", is(RatingForm.class)))
									.andExpect(model().hasNoErrors())
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl("rate")));
	}
	
	@Test
	public void noRatingFormIfNoTutorShipExist() throws Exception
	{
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(get("/rate?tutorId="+tutor.getId()).session(session)).andExpect(status().isOk())
									.andExpect(status().isOk())
									.andExpect(model().attributeExists("page_error"))
									.andExpect(forwardedUrl(completeUrl("ratingFailed")));
	}
	
	@Test
	public void noRatingFormIfTutorShipIsAlreadyRated() throws Exception
	{
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setConfirmed(true);
		tutorShip.setRated(true);
		tutorShipDao.save(tutorShip);
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(get("/rate?tutorId="+tutor.getId()).session(session)).andExpect(status().isOk())
									.andExpect(status().isOk())
									.andExpect(model().attributeExists("page_error"))
									.andExpect(forwardedUrl(completeUrl("ratingFailed")));
	}
	
	@Test
	public void canSumbmitValidRating() throws Exception
	{
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(post("/submitRating").session(session)
									.param("ratedTutorId", tutor.getId().toString())
									.param("rating", "4")
									.param("feedback", "it was ok"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl("ratedSuccessfull")));
	}
	
	@Test
	public void validRatingIsSavedToDatabase() throws Exception
	{
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(post("/submitRating").session(session)
									.param("ratedTutorId", tutor.getId().toString())
									.param("rating", "4")
									.param("feedback", "it was ok"));
		Rating newRating = tutor.getRatings().iterator().next();
		assertEquals(user, newRating.getCommentator());
		assertEquals("it was ok", newRating.getFeedback());
		assertEquals(new BigDecimal(4), newRating.getRating());
		assertEquals(new BigDecimal(4), tutor.getAverageRating());
	}
	
	@Test
	public void sumbmitRatingWithFieldErrors() throws Exception
	{
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setConfirmed(true);
		tutorShipDao.save(tutorShip);
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(post("/submitRating").session(session)
									.param("ratedTutorId", tutor.getId().toString())
									.param("rating", "8")
									.param("feedback", ""))
									.andExpect(status().isOk())
									.andExpect(model().attributeHasFieldErrors("ratingForm", "feedback"))
									.andExpect(model().attributeHasFieldErrors("ratingForm", "rating"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl("rate")));
	}
	
	@Test
	public void sumbmitRatingWithoutConfirmedTutorShip() throws Exception
	{
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(post("/submitRating").session(session)
									.param("ratedTutorId", tutor.getId().toString())
									.param("rating", "3")
									.param("feedback", "Was good"))
									.andExpect(status().isOk())
									.andExpect(model().attributeExists("page_error"))
									.andExpect(forwardedUrl(completeUrl("ratingFailed")));
	}
	
	@Test
	public void sumbmitRatingWithoutOnAlreadyRatedTutorShip() throws Exception
	{
		TutorShip tutorShip = new TutorShip();
		tutorShip.setStudent(user);
		tutorShip.setTutor(tutor);
		tutorShip.setConfirmed(true);
		tutorShip.setRated(true);
		tutorShipDao.save(tutorShip);
		session = createSessionWithUser("test@test.mail", "#etrZ4ad", "ROLE_USER");
		mockMvc.perform(post("/submitRating").session(session)
									.param("ratedTutorId", tutor.getId().toString())
									.param("rating", "3")
									.param("feedback", "Was good"))
									.andExpect(status().isOk())
									.andExpect(model().attributeExists("page_error"))
									.andExpect(forwardedUrl(completeUrl("ratingFailed")));
	}
	
}
