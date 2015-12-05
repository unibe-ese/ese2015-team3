package org.sample.test.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.sample.controller.RegisterController;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

public class TutorRegisterControllerIntegrationTest extends ControllerIntegrationTest{
	@Autowired
	private RegisterFormService registerFormService;
	@Autowired
	private UserDao userDao;

	@Test
	public void upgradePage() throws Exception
	{
		User newUser = new User();
		newUser.setPassword("123");
		newUser.setEmail("mail@mail.mail");
		newUser = userDao.save(newUser);
		MockHttpSession session = createSessionWithUser("mail@mail.mail", "123", "ROLE_USER");
		mockMvc.perform(get("/upgrade").session(session)).andExpect(status().isOk())
									.andExpect(model().attribute("tutorForm", is(TutorForm.class)))
									.andExpect(forwardedUrl(completeUrl("tutorregistration")))
									.andExpect(model().hasNoErrors());
	}
	
	//TODO find out how to correctly add class and course list as parameters
	@Test
	public void wrongFieldsOnsubmitandsaveTutorForm() throws Exception
	{
		mockMvc.perform(post("/submitastutor").param("bio", "")
									.param("fee", "")
									.param("userId", "0")
									.param("save","true"))
									.andExpect(status().isOk())
									.andExpect(model().attributeHasFieldErrors("tutorForm", "bio"))
									.andExpect(model().attributeHasFieldErrors("tutorForm", "fee"))
									.andExpect(model().attribute("tutorForm", is(TutorForm.class)))
									.andExpect(forwardedUrl(completeUrl("tutorregistration")));		
	}
	
	//TODO find out how to correctly add class and course list as parameters
	@Test
	public void submitAndSaveTutorForm() throws Exception
	{
		User user = new User();
		user = userDao.save(user);
		mockMvc.perform(post("/submitastutor").param("bio", "bio")
									.param("fee", "22")
									.param("userId", user.getId().toString())
									.param("save","true"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_SUBMIT)));
		Tutor tutor = user.getTutor();
		assertEquals("bio", tutor.getBio());
		assertEquals(new BigDecimal(22), tutor.getFee());
		assertEquals(user, tutor.getStudent());
	}
	
}
