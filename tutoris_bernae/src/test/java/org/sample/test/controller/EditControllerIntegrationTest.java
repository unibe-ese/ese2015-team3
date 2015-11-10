package org.sample.test.controller;

import static org.mockito.Mockito.mock;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpSession;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;

public class EditControllerIntegrationTest extends ControllerIntegrationTest{
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private UserDao userDao;
	
	private User newUser;
	private User newTutorUser;
	private Tutor newTutor;
	
	MockHttpSession session;
	@Before
	public void setUp()
	{
		newUser = new User();
		newUser.setUsername("test");
		newUser.setFirstName("first");
		newUser.setLastName("last");
		newUser.setPassword("1232w%Dres");
		newUser.setEmail("mail@mail.mail");
		newUser = userDao.save(newUser);
		newTutor = new Tutor();
		newTutor.setClasses(new HashSet<Classes>());
		newTutor.setCourses(new HashSet<StudyCourse>());
		newTutor = tutorDao.save(newTutor);
		newTutorUser = new User();
		newTutorUser.setUsername("tutortest");
		newTutorUser.setPassword("1232w%dfa");
		newTutorUser.setEmail("tutormail@mail.mail");
		newTutorUser.setTutor(true);
		newTutorUser.setTutor(newTutor);
		
		newTutorUser.setTutor(newTutor);
		newTutorUser = userDao.save(newTutorUser);
	}
	
	/**
	 * Tests if we get the correct edit page as a normal user
	 * @throws Exception
	 */
	@Test
	public void editUserProfilePage() throws Exception
	{
		session = createSessionWithUser("test", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(get("/edit").session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("editForm", is(EditForm.class)))
										.andExpect(forwardedUrl(completeUrl("edit")))
										// Check that fields are correctly prefilled 
										.andExpect(model().attribute("editForm", hasProperty("username", Matchers.is("test"))))
										.andExpect(model().attribute("editForm", hasProperty("firstName", Matchers.is("first"))))
										.andExpect(model().attribute("editForm", hasProperty("lastName", Matchers.is("last"))))
										.andExpect(model().attribute("editForm", hasProperty("email", Matchers.is("mail@mail.mail"))))
										.andExpect(model().attribute("editForm", hasProperty("password", Matchers.is("1232w%Dres"))));
	}
	
	/**
	 * Tests if we get the correct edit page as a tutor
	 * @throws Exception
	 */
	@Test
	public void editTutorProfilePage() throws Exception
	{
		session = createSessionWithUser("tutortest", "123", "ROLE_TUTOR");
		mockMvc.perform(get("/edit").session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("tutorForm", is(TutorEditForm.class)))
										.andExpect(forwardedUrl(completeUrl("editTutor")));
	}
	
	@Test
	public void editUserDone() throws Exception
	{
		session = createSessionWithUser("test", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(post("/editSubmit").session(session)
										.param("userId", newUser.getId().toString())
										.param("firstName","test")
										.param("lastName","test")
										.param("username","test")
										.param("password","123A#qqq")
										.param("email","test@mail.de"))
										.andExpect(status().isOk())
										.andExpect(forwardedUrl(completeUrl("editDone")));
		//Check if new datas are saved correctly
		assertEquals("test@mail.de", newUser.getEmail());
		assertEquals("test", newUser.getLastName());
		assertEquals("test", newUser.getFirstName());
		assertEquals("test", newUser.getUsername());
		assertEquals("test@mail.de", newUser.getEmail());
		assertEquals("123A#qqq", newUser.getPassword());
	}
	
	@Test
	public void editUserEditFormErrors() throws Exception
	{
		session = createSessionWithUser("test", "123", "ROLE_USER");
		mockMvc.perform(post("/editSubmit").session(session)
										.param("userId", newUser.getId().toString())
										.param("firstName","")
										.param("lastName","")
										.param("username","")
										.param("password","")
										.param("email",""))
										.andExpect(status().isOk())
										.andExpect(forwardedUrl(completeUrl("edit")))
										.andExpect(model().attributeHasFieldErrors("editForm", "email"))
										.andExpect(model().attributeHasFieldErrors("editForm", "firstName"))
										.andExpect(model().attributeHasFieldErrors("editForm", "lastName"))
										.andExpect(model().attributeHasFieldErrors("editForm", "username"))
										.andExpect(model().attributeHasFieldErrors("editForm", "password"));

	}
	
	@Test
	public void needsLogin() throws Exception
	{
		mockMvc.perform(get("/edit")).andExpect(status().isMovedTemporarily()); //moved Temporarily because your moved to the login page
	}
	// TODO Find out how to add a form to a mockmvc request or how to add course and classlist as parameters
	// so that this test can be completed
	/*
	@Test
	public void editTutorDone() throws Exception
	{
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_TUTOR");
		Authentication authentication = 
		        new UsernamePasswordAuthenticationToken("tutortest","123", authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		mockMvc.perform(post("/submitTutorEdit").principal(authentication)
										.param("userId", newUser.getId().toString())
										.param("firstName","test")
										.param("lastName","test")
										.param("username","test")
										.param("password","123")
										.param("email","test@mail.de"))
										.andExpect(status().isOk())
										.andExpect(forwardedUrl(completeUrl("editDone")));
	}*/
	

}
