package org.sample.test.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Before;
import org.junit.Test;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class ViewTutorProfileControllerIntegrationTest extends ControllerIntegrationTest {
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private UserDao userDao;
	
	private User newUser;
	private User newTutorUser;
	private Tutor newTutor;

	MockHttpSession session;
	
	@Before
	public void setUp() throws Exception
	{
		newUser = new User();
		newUser.setPassword("123");
		newUser.setEmail("mail@mail.mail");
		newUser = userDao.save(newUser);
		newTutor = new Tutor();
		newTutor = tutorDao.save(newTutor);
		newTutorUser = new User();
		newTutorUser.setPassword("123");
		newTutorUser.setEmail("tutormail@mail.mail");
		newTutorUser.setTutor(newTutor);
		newTutorUser.setTutor(newTutor);
		newTutorUser = userDao.save(newTutorUser);
		
		session = createSessionWithUser("test", "123", "ROLE_USER");
	}
	
	@Test
	public void viewProfilePage() throws Exception
	{
	mockMvc.perform(get("/view?tutorId="+newTutor.getId()).session(session))		
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(forwardedUrl(completeUrl("viewTutorProfile")))
				.andExpect(view().name("viewTutorProfile"))
				.andExpect(model().attribute("tutor", newTutor)); //we should get excatly the newTutor, because we searched his id
	}
	
	@Test
	public void noTutorFound() throws Exception
	{
		mockMvc.perform(get("/view?tutorId=0").session(session)) //Will not be found because ids start at 1
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(forwardedUrl(completeUrl("notutorfound")));
		
	}
	
	@Test
	public void noTutorId() throws Exception
	{
		mockMvc.perform(get("/view").session(session))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(forwardedUrl(completeUrl("notutorfound")));
	}
	
	@Test
	public void loginNeeded() throws Exception
	{
		mockMvc.perform(get("/view?tutorId=0"))
				.andExpect(status().isMovedTemporarily()); //MovedTemporarily because we are moved to the login page
	}

}
