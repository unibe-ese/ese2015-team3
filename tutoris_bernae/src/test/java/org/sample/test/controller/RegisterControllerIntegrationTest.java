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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

public class RegisterControllerIntegrationTest extends ControllerIntegrationTest{
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
	
	@Test
	public void registerPage() throws Exception
	{
		mockMvc.perform(get("/register")).andExpect(status().isOk())
									.andExpect(model().attribute("registerForm", is(RegisterForm.class)))
									.andExpect(model().hasNoErrors());
	}
	@Test
	public void submit() throws Exception
	{
		mockMvc.perform(post("/submit").param("email", "mail@mail.de")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("password", "1Password*"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_SUBMIT)));
		User user = userDao.findByEmailLike("mail@mail.de"); //by email because email is unique
		assertEquals("first", user.getFirstName());
		assertEquals("last", user.getLastName());
		assertEquals("1Password*", user.getPassword());
	}
	
	@Test
	public void submitAndContinueRegisteringAsTutor() throws Exception
	{
		mockMvc.perform(post("/submit").param("email", "mail@mail.de")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("password", "1Password*")
									.param("registerastutor","true"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(model().attribute("tutorForm", is(TutorForm.class)))
									.andExpect(forwardedUrl(completeUrl("tutorregistration")));
		//Although we continue registration, the userprofile is already saved
		User user = userDao.findByEmailLike("mail@mail.de"); //by email because email is unique
		assertEquals("first", user.getFirstName());
		assertEquals("last", user.getLastName());
		assertEquals("1Password*", user.getPassword());
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
		user.setPassword("1");
		user.setEmail("test");
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
	
	@Test
	public void wrongFieldsRegisterForm() throws Exception
	{
		mockMvc.perform(post("/submit").param("email", "")
				.param("firstName", "")
				.param("lastName", "")
				.param("password", ""))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_REGISTER)))
				.andExpect(model().attributeHasFieldErrors("registerForm", "email"))
				.andExpect(model().attributeHasFieldErrors("registerForm", "firstName"))
				.andExpect(model().attributeHasFieldErrors("registerForm", "lastName"))
				.andExpect(model().attributeHasFieldErrors("registerForm", "password"));
	}
	
	@Test
	public void alreadyInUseEmailNotAllowed() throws Exception
	{
		User userWithThisEmailAdress = new User();
		userWithThisEmailAdress.setEmail("test@test.testmail");
		userDao.save(userWithThisEmailAdress);
		mockMvc.perform(post("/submit").param("email", userWithThisEmailAdress.getEmail())
				.param("firstName", "Test")
				.param("lastName", "Test")
				.param("username", "test")
				.param("password", "W*3avsadf"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_REGISTER)))
				.andExpect(model().attributeHasFieldErrors("registerForm", "email"));
	}
	
}
