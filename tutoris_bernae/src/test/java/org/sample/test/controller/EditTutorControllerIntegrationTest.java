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
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
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

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collection;
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

public class EditTutorControllerIntegrationTest extends ControllerIntegrationTest{
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ClassesDao classesDao;
	
	private User newUser;
	private User newTutorUser;
	private Tutor newTutor;
	
	MockHttpSession session;
	@Before
	public void setUp()
	{
		newTutor = new Tutor();
		newTutor.setCompletedClasses(new HashSet<CompletedClasses>());
		newTutor.setCourses(new HashSet<StudyCourse>());
		newTutor = tutorDao.save(newTutor);
		newTutorUser = new User();
		newTutorUser.setPassword("1232w%dfa");
		newTutorUser.setEmail("tutormail@mail.mail");
		newTutorUser.setTutor(true);
		newTutorUser.setTutor(newTutor);
		
		newTutorUser.setTutor(newTutor);
		newTutorUser = userDao.save(newTutorUser);
	}
	
	/**
	 * Tests if we get the correct edit page as a tutor
	 * @throws Exception
	 */
	@Test
	public void editTutorProfilePage() throws Exception
	{
		session = createSessionWithUser("tutortest", "123", "ROLE_TUTOR");
		mockMvc.perform(get("/editTutor").session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("tutorEditForm", is(TutorEditForm.class)))
										.andExpect(forwardedUrl(completeUrl("editTutor")));
	}
		
	@Test
	public void needsLogin() throws Exception
	{
		mockMvc.perform(get("/editTutor")).andExpect(status().isMovedTemporarily()); //moved Temporarily because your moved to the login page
	}

	//TODO find out how to correctly add class and course list as parameters
	@Test
	public void editTutorDone() throws Exception
	{
        Classes classes = new Classes();
        classesDao.save(classes);
		CompletedClasses completedClasses = new CompletedClasses(classes, 4);
		List<CompletedClasses> completedClassesList = new LinkedList<CompletedClasses>();
		completedClassesList.add(completedClasses);
		session = createSessionWithUser("tutortest","123", "ROLE_TUTOR");
		mockMvc.perform(post("/editTutorSubmit").session(session)
				//.requestAttr("courseList", new LinkedList<StudyCourse>())
				//.requestAttr("classesList", completedClassesList)
				.param("userId", newTutorUser.getId().toString())
				.param("tutorId", newTutor.getId().toString())
				.param("firstName","first")
				.param("lastName","last")
				.param("password","123A#qqq")
				.param("email","test@mail.de")
				.param("bio","new Bio")
				.param("fee","48")
				.param("save","true"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl(completeUrl("editDone")));
		assertEquals("test@mail.de", newTutorUser.getEmail());
		assertEquals("last", newTutorUser.getLastName());
		assertEquals("first", newTutorUser.getFirstName());
		assertEquals("test@mail.de", newTutorUser.getEmail());
		assertEquals("123A#qqq", newTutorUser.getPassword());
		assertEquals("new Bio", newTutor.getBio());
		assertEquals(new BigDecimal(48), newTutor.getFee());
		assertEquals("123A#qqq", newTutorUser.getPassword());
	}
	
}
