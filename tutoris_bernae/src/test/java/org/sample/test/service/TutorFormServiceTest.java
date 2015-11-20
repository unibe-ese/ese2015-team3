package org.sample.test.service;


import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidTutorException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.CompletedClassesService;
import org.sample.controller.service.TutorFormService;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class TutorFormServiceTest {
	
	@Configuration
    static class ContextConfiguration {

	     @Bean
	     public TutorDao tutorDaoMock() {
	    	 TutorDao tutorDao = mock(TutorDao.class);
	    	 return tutorDao;
	     }
	     @Bean
	     public UserDao userDaoMock() {
	    	 UserDao userDao = mock(UserDao.class);
	    	 return userDao;
	     }
	     
        @Bean
        public TutorFormService tutorFormService() {
            TutorFormService tutorFormService = new TutorFormService();
            return tutorFormService;
        }
		@Bean
		public CompletedClassesService completedClassesServiceMock() {
			CompletedClassesService completedClassesServiceMock = mock(CompletedClassesService.class);
			return completedClassesServiceMock;
		}
    }
	@Qualifier("completedClassesServiceMock")
	@Autowired
	private CompletedClassesService completedClassesServiceMock;
	@Autowired
    private TutorFormService tutorFormService;
	@Qualifier("tutorDaoMock")
	@Autowired
	private TutorDao tutorDao;
	@Qualifier("userDaoMock")
	@Autowired
	private UserDao userDao;
	
    @Test
    public void TutorFormCorrectDataSaved() {
        TutorForm tutorForm = new TutorForm();
        User user = new User();
        Classes classes1 = new Classes();
        CompletedClasses completedClasses1 = new CompletedClasses(classes1, 5);
        LinkedList<CompletedClasses> completedClassesList = new LinkedList<CompletedClasses>();
        completedClassesList.add(completedClasses1);
        tutorForm.setUserId(0L);
        tutorForm.setClassList(completedClassesList);
        tutorForm.setStudyCourseList(new LinkedList<StudyCourse>());
        tutorForm.setBio("newBio");
        tutorForm.setFee(new BigDecimal(20));
        when(userDao.findOne(any(Long.class))).thenReturn(user);
        when(tutorDao.save(any(Tutor.class)))
        .thenAnswer(new Answer<Tutor>() {
            public Tutor answer(InvocationOnMock invocation) throws Throwable {
            	Classes classes1 = new Classes();
                CompletedClasses completedClasses1 = new CompletedClasses(classes1, 5);
                Set<CompletedClasses> completedClassesList = new HashSet<CompletedClasses>();
                completedClassesList.add(completedClasses1);
                Tutor tutor = (Tutor) invocation.getArguments()[0];
                assertEquals(completedClassesList, tutor.getCompletedClasses());
                assertEquals("newBio", tutor.getBio());
                assertEquals(new BigDecimal(20),tutor.getFee());
                return tutor;
            }
        });
        
        when(userDao.save(any(User.class)))
        .thenAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable {
            	User user = (User) invocation.getArguments()[0];
                assertEquals("ROLE_TUTOR", user.getRole());
                assertEquals(true, user.isTutor());
                return user;
            }
        });
        
        tutorFormService.saveFrom(tutorForm);
        
        
    }
   
    
    @After
    public void reset_mocks() {
        reset(tutorDao);
    }

}