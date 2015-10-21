package org.sample.controller;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.service.FormService;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/springData.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RegisterControllerIntergrationTest {
	@Autowired
	private WebApplicationContext context;
	MockMvc mockMvc;
	@Autowired
	private RegisterController registerController;
	@Autowired
	private FormService formService;
	@Before
	public void setUp()
	{
		mockMvc =  MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void registerPage() throws Exception
	{
		mockMvc.perform(get("/register")).andExpect(status().isOk())
									.andExpect(model().attribute("regiterForm", any(RegisterForm.class)))
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

	private String completeUrl(String page) {
		return "/pages/"+page+".jsp";
	}
	
}