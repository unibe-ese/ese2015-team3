package org.sample.controller;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;



public class RegisterControllerIntergrationTest extends ControllerIntegrationTest{
	@Autowired
	private RegisterController registerController;
	@Autowired
	private RegisterFormService formService;
	@Autowired
	private UserDao userDao;

	@Test
	public void upgradePage() throws Exception
	{
		User newUser = new User();
		newUser.setUsername("test");
		newUser.setPassword("123");
		newUser.setEmail("mail@mail.mail");
		newUser = userDao.save(newUser);
		MockHttpSession session = createSessionWithUser("test", "123", "ROLE_USER");
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
									.param("username", "user")
									.param("password", "password"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_SUBMIT)));		
	}
	
	@Test
	public void submitAsTutor() throws Exception
	{
		mockMvc.perform(post("/submit").param("email", "mail@mail.de")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("username", "user")
									.param("password", "password")
									.param("registerastutor","true"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(model().attribute("tutorForm", is(TutorForm.class)))
									.andExpect(forwardedUrl(completeUrl("tutorregistration")));		
	}
	/*
	@Test
	public void submitAsTutor() throws Exception
	{
		mockMvc.perform(post("/submit").param("email", "mail@mail.de")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("username", "user")
									.param("password", "password")
									.param("registerastutor","true"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(model().attribute("tutorForm", is(TutorForm.class)))
									.andExpect(forwardedUrl(completeUrl("tutorregistration")));		
	}*/
	
	@Test
	public void invalidUserPageError() throws Exception
	{
		RegisterForm registerForm = new RegisterForm();
        registerForm.setFirstName("First");
        registerForm.setLastName("Last");
        registerForm.setUsername("user");
        registerForm.setEmail("test@test.com");
        registerForm.setPassword("123456");
		formService.saveFrom(registerForm);
		mockMvc.perform(post("/submit").param("email", "test@test.com")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("username", "user")
									.param("password", "password"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_REGISTER)))
									.andExpect(model().attributeExists("page_error"));
	}
	
	@Test
	public void wrongFieldEmail() throws Exception
	{
		mockMvc.perform(post("/submit").param("email", "mail")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("username", "user")
									.param("password", "password"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_REGISTER)))
									.andExpect(model().attributeHasFieldErrors("registerForm", "email"));
	}
	
}
