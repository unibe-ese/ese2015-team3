package org.sample.test.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.EditFormService;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class RegisterFormServiceTest {

	@Configuration
    static class ContextConfiguration {

		@Bean
		public UserDao userDaoMock() {
			UserDao userDao = mock(UserDao.class);
			return userDao;
		}
		@Bean
		public StudyCourseDao studyCourseDaoMock() {
			StudyCourseDao studyCourseDao= mock(StudyCourseDao.class);
			return studyCourseDao;
		}
		@Bean
		public ClassesDao classesDaoMock() {
			ClassesDao classesDao= mock(ClassesDao.class);
			return classesDao;
		}
		@Bean
		public TutorDao tutorDaoMock() {
			TutorDao tutorDao = mock(TutorDao.class);
			return tutorDao;
		}
		@Bean
		public RegisterFormService registerFormService() {
			RegisterFormService registerFormService = new RegisterFormService();
			return registerFormService;
		}
    }
	

	@Qualifier("userDaoMock")
	@Autowired
	private UserDao userDao;
	@Qualifier("tutorDaoMock")
	@Autowired
	private TutorDao tutorDao;
	@Qualifier("studyCourseDaoMock")
	@Autowired
	private StudyCourseDao studyCourseDao;
	@Qualifier("classesDaoMock")
	@Autowired
	private ClassesDao classesDao;
	@Autowired
    private RegisterFormService registerFormService;


    @Test
    public void CorrectDataSaved() {

        RegisterForm registerForm = new RegisterForm();
        
        registerForm.setFirstName("First");
        registerForm.setLastName("Last");
        registerForm.setUsername("user");
        registerForm.setEmail("test@test.com");
        registerForm.setPassword("123456");

        when(userDao.save(any(User.class)))
                .thenAnswer(new Answer<User>() {
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        User user = (User) invocation.getArguments()[0];
                        assertEquals(user.getFirstName(), "First");
                        assertEquals(user.getLastName(), "Last");
                        assertEquals(user.getUsername(), "user");
                        assertEquals(user.getEmail(), "test@test.com");
                        assertEquals(user.getPassword(), "123456");
                        user.setId(1L);
                        return user;
                    }
                });

        assertNull(registerForm.getId());
        

        registerFormService.saveFrom(registerForm);

        assertNotNull(registerForm.getId());
        assertTrue(registerForm.getId() > 0);
    }
    
    
    @After
    public void reset_mocks() {
        reset(userDao);
    }

   


}