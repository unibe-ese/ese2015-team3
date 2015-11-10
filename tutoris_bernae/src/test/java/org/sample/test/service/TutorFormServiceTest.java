package org.sample.test.service;
//
//package org.sample.controller.service;
//
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.sample.controller.exceptions.InvalidTutorException;
//import org.sample.controller.pojos.RegisterForm;
//import org.sample.controller.pojos.TutorForm;
//import org.sample.model.Classes;
//import org.sample.model.StudyCourse;
//import org.sample.model.Tutor;
//import org.sample.model.User;
//import org.sample.model.dao.AddressDao;
//import org.sample.model.dao.TutorDao;
//import org.sample.model.dao.UserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.math.BigDecimal;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.mockito.Mockito.reset;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
//public class TutorFormServiceTest {
//private List<Classes> classes = new LinkedList<Classes>();
//private List<StudyCourse> courses = new LinkedList<StudyCourse>();
//private User testUser = new User();
//	@Configuration
//    static class ContextConfiguration {
//
//	     @Bean
//	     public TutorDao tutorDaoMock() {
//	    	 TutorDao tutorDao = mock(TutorDao.class);
//	    	 return tutorDao;
//	     }
//	     @Bean
//	     public UserDao userDaoMock() {
//	    	 UserDao userDao = mock(UserDao.class);
//	    	 return userDao;
//	     }
//	     
//        @Bean
//        public TutorFormService tutorFormService() {
//            TutorFormService tutorFormService = new TutorFormService();
//            return tutorFormService;
//        }
//    }
//	
//	@Autowired
//    private TutorFormService tutorFormService;
//	@Qualifier("tutorDaoMock")
//	@Autowired
//	private TutorDao tutorDao;
//	@Qualifier("userDaoMock")
//	@Autowired
//	private UserDao userDao;
//	
//    @Test
//    public void CorrectDataSaved() {
//
//        TutorForm tutorForm = new TutorForm();
//        
//        for(int i = 0;i<2;i++)
//        {
//        	StudyCourse course = new StudyCourse();
//        	course.setName("Course "+i);
//        	course.setFaculty("Faculty "+i);
//        	courses.add(course);
//        }
//        
//        for(int i = 0;i<3;i++)
//        {
//        	Classes nextClass = new Classes();
//        	nextClass.setName("Class"+i);
//        	//nextClass.setStudyCourse(courses.get(i%2));
//        	nextClass.setGrade(i+2);
//        	classes.add(nextClass);
//        }
//        
//        tutorForm.setFee(new BigDecimal(20.50));
//        tutorForm.setStudyCourseList(courses);
//        tutorForm.setClassList(classes);
//        tutorForm.setBio("I am awesome");
//        tutorForm.setUserId(1L);
//
//        when(tutorDao.save(any(Tutor.class)))
//                .thenAnswer(new Answer<Tutor>() {
//                    public Tutor answer(InvocationOnMock invocation) throws Throwable {
//                        Tutor tutor = (Tutor) invocation.getArguments()[0];
//                        assertEquals(new HashSet<Classes>(classes),tutor.getClasses());
//                        assertEquals(new HashSet<StudyCourse>(courses),tutor.getCourses());
//                        assertEquals(new BigDecimal(20.50),tutor.getFee());
//                        assertEquals("I am awesome",tutor.getBio());
//                        assertEquals(testUser, tutor.getStudent());
//
//                        tutor.setId(1L);
//                        return tutor;
//                    }
//                });
//        when(userDao.findOne(any(Long.class))).thenAnswer(new Answer<User>() {
//            public User answer(InvocationOnMock invocation) throws Throwable {
//                Long id = (Long) invocation.getArguments()[0];
//                assertEquals(new Long(1), id);
//                return testUser;
//            }
//        });
//        when(userDao.save(any(User.class)))
//        .thenAnswer(new Answer<User>() {
//            public User answer(InvocationOnMock invocation) throws Throwable {
//                User user = (User) invocation.getArguments()[0];
//                assertEquals(true, user.isTutor());
//                //How to check if the tutor is correct? does not have him as an instance variable
//
//                return user;
//            }
//        });
//
//
//        assertNull(tutorForm.getId());
//        
//
//        tutorFormService.saveFrom(tutorForm);
//
//        assertNotNull(tutorForm.getId());
//        assertTrue(tutorForm.getId() > 0);
//    }
//    
//    @After
//    public void reset_mocks() {
//        reset(tutorDao);
//    }
//
//}