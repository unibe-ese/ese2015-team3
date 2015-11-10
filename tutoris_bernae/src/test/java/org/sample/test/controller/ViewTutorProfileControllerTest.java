package org.sample.test.controller;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.reset;

import org.sample.controller.ViewTutorProfileController;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
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

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class ViewTutorProfileControllerTest {

	@Configuration
    static class CurrentContextConfiguration {
		 @Bean
	     public TutorDao tutorDaoMock() {
	    	 TutorDao tutorDao = mock(TutorDao.class);
	    	 return tutorDao;
	     }
        @Bean
        public ViewTutorProfileController viewTutorProfileController() {
            return new ViewTutorProfileController();
        }
    }

	MockMvc mockMvc;
	@Qualifier("tutorDaoMock")
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private ViewTutorProfileController viewTutorProfileController;
	
	Tutor exampleTutor = new Tutor();
	User exampleUser = new User();

	@Before
	public void setUp()
	{
		exampleUser.setUsername("User");
		exampleTutor.setStudent(exampleUser);
		exampleTutor.setBio("i am a tutor");
		exampleTutor.setFee(new BigDecimal(25));
		
		mockMvc = MockMvcBuilders.standaloneSetup(viewTutorProfileController).build();
	}
	
	@Test
	public void noTutorFound() throws Exception
	{
		when(tutorDao.findOne(any(Long.class))).thenReturn(null);
		
		mockMvc.perform(get("/view?tutorId=1"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(forwardedUrl("notutorfound"));
	}
	
	@Test
	public void noTutorId() throws Exception
	{
		when(tutorDao.findOne(any(Long.class))).thenReturn(null);
		
		mockMvc.perform(get("/view"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(forwardedUrl("notutorfound"));
	}
	
	@Test
	public void viewTutor() throws Exception
	{
		when(tutorDao.findOne(any(Long.class))).thenReturn(exampleTutor);
		
		mockMvc.perform(get("/view?tutorId=1"))
									.andExpect(status().isOk())
									.andExpect(model().hasNoErrors())
									.andExpect(forwardedUrl("viewTutorProfile"))
									.andExpect(model().attribute("tutor", is(Tutor.class)));
	}
	
	@After 
	public void reset_mocks() {
	    reset(tutorDao);
	}
	
}
