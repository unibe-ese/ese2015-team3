package org.sample.controller;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.reset;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class RegisterControllerTest {

	@Configuration
    static class CurrentContextConfiguration {
		 @Bean
	     public UserDao userDaoMock() {
	    	 UserDao userDao = mock(UserDao.class);
	    	 return userDao;
	     }
	     @Bean
	     public RegisterFormService registerFormServiceMock() {
	    	 RegisterFormService registerFormServiceMock = mock(RegisterFormService.class);
	    	 return registerFormServiceMock;
	     }
        @Bean
        public RegisterController registerController() {
            return new RegisterController();
        }
    }

	MockMvc mockMvc;
	@Qualifier("userDaoMock")
	@Autowired
	private UserDao userDao;
	@Qualifier("registerFormServiceMock")
	@Autowired
	private RegisterFormService registerFormService;
	@Autowired
	private RegisterController registerController;

	@Before
	public void setUp()
	{
	}
	
	@Test
	public void submit() throws Exception
	{
		when(registerFormService.saveFrom(any(RegisterForm.class))).thenReturn(null);
		mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
		mockMvc.perform(post("/submit").param("email", "mail@mail.de")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("username", "user")
									.param("password", "password"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(forwardedUrl(RegisterController.PAGE_SUBMIT));
	}
	
	@Test
	public void invalidUser() throws Exception
	{
		when(registerFormService.saveFrom(any(RegisterForm.class))).thenThrow(new InvalidUserException("invalidUserTest"));
		mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
		mockMvc.perform(post("/submit").param("email", "mail@mail.de")
									.param("firstName", "first")
									.param("lastName", "last")
									.param("username", "user")
									.param("password", "password"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(RegisterController.PAGE_REGISTER))
									.andExpect(model().attributeExists("page_error"));
	}
	
	@After 
	public void reset_mocks() {
	    reset(registerFormService);
	}
}
