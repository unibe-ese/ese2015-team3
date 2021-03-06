package org.sample.test.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;


public class ProfileControllerIntegrationTest extends ControllerIntegrationTest {

	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private UserDao userDao;
	@Before
	public void setUp()
	{
		User newUser = new User();
		newUser.setEmail("test@test.ch");
		newUser.setPassword("123");
		userDao.save(newUser);
		Tutor newTutor = new Tutor();
		tutorDao.save(newTutor);
		User newTutorUser = new User();
		newTutorUser.setEmail("tutortest@test.ch");
		newTutorUser.setPassword("123");
		newTutorUser.setTutor(newTutor);
		userDao.save(newTutorUser);
	}
	
	@Test
	public void profilePage() throws Exception
	{
        MockHttpSession session = createSessionWithUser("test@test.ch", "123", "ROLE_USER");
		mockMvc.perform(get("/profile").session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("user", is(User.class)));
	}
	
	@Test
	public void tutorProfilePage() throws Exception
	{
		MockHttpSession session = createSessionWithUser("tutortest@test.ch", "123", "ROLE_TUTOR");
		mockMvc.perform(get("/profile").session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("user", is(User.class)))
										.andExpect(model().attribute("tutor", is(Tutor.class)));
	}
	
	@Test
	public void needsLogin() throws Exception
	{
		mockMvc.perform(get("/profile")).andExpect(status().isMovedTemporarily());
	}

}
