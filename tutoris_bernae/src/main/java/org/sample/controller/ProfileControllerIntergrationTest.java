package org.sample.controller;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
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

import java.security.Principal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/springData.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ProfileControllerIntergrationTest {
	@Autowired
	private WebApplicationContext context;
	MockMvc mockMvc;
	@Autowired
	private ProfileController profileController;
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private UserDao userDao;
	@Before
	public void setUp()
	{
		mockMvc =  MockMvcBuilders.webAppContextSetup(this.context).build();
		User newUser = new User();
		newUser.setUsername("test");
		newUser.setPassword("123");
		userDao.save(newUser);
		Tutor newTutor = new Tutor();
		tutorDao.save(newTutor);
		User newTutorUser = new User();
		newTutorUser.setUsername("tutortest");
		newTutorUser.setPassword("123");
		newTutorUser.setTutor(true);
		newTutorUser.setTutor(newTutor);
		userDao.save(newTutorUser);
	}
	
	@Test
	public void profilePage() throws Exception
	{
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		Authentication authentication = 
		        new UsernamePasswordAuthenticationToken("test","123", authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		mockMvc.perform(get("/profile").principal(authentication))
										.andExpect(status().isOk())
										.andExpect(model().attribute("user", is(User.class)));
	}
	
	@Test
	public void tutorProfilePage() throws Exception
	{
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_TUTOR");
		Authentication authentication = 
		        new UsernamePasswordAuthenticationToken("tutortest","123", authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		mockMvc.perform(get("/profile").principal(authentication))
										.andExpect(status().isOk())
										.andExpect(model().attribute("user", is(User.class)))
										.andExpect(model().attribute("tutor", is(Tutor.class)));
	}

}
