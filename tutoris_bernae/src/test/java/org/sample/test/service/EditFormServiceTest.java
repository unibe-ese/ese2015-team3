package org.sample.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.service.EditFormService;
import org.sample.model.Classes;
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
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class EditFormServiceTest {

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
		public EditFormService EditFormFormService() {
			EditFormService editFormService = new EditFormService();
			return editFormService;
		}
    }
	
	@Autowired
    private EditFormService editFormService;
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
	private User user;

    @Test
    public void EditFormCorrectDataSaved() {
        EditForm editForm = new EditForm();
        user = new User();
        editForm.setFirstName("First2");
        editForm.setLastName("Last2");
        editForm.setUsername("user2");
        editForm.setEmail("test@test2.com");
        editForm.setPassword("1234567");
        editForm.setUserId(0L);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("user");
        user.setEmail("test@test.com");
        user.setPassword("123456");

        when(userDao.findOne(any(Long.class))).thenReturn(user);
        when(userDao.save(any(User.class)))
                .thenAnswer(new Answer<User>() {
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        User user = (User) invocation.getArguments()[0];
                        assertEquals( "First2",user.getFirstName());
                        assertEquals( "Last2",user.getLastName());
                        assertEquals( "user2", user.getUsername());
                        assertEquals( "test@test2.com",user.getEmail());
                        assertEquals("1234567",user.getPassword());
                        return user;
                    }
                });
        
        editFormService.saveFrom(editForm);
    }
    
    // TODO: test classes and courses list as well
    @Test
    public void TutorEditFormCorrectDataSaved() {
        TutorEditForm editForm = new TutorEditForm();
        user = new User();
        editForm.setFirstName("First2");
        editForm.setLastName("Last2");
        editForm.setUsername("user2");
        editForm.setEmail("test@test2.com");
        editForm.setPassword("1234567");
        editForm.setUserId(0L);
        editForm.setClassList(new LinkedList<Classes>());
        editForm.setStudyCourseList(new LinkedList<StudyCourse>());
        editForm.setBio("newBio");
        editForm.setFee(new BigDecimal(20));
        editForm.setTutorId(0L);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("user");
        user.setEmail("test@test.com");
        user.setPassword("123456");
        Tutor tutor = new Tutor();
        tutor.setBio("bio");
        tutor.setFee(new BigDecimal(14));

        when(userDao.findOne(any(Long.class))).thenReturn(user);
        when(userDao.save(any(User.class)))
        .thenAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable {
                User user = (User) invocation.getArguments()[0];
                assertEquals( "First2",user.getFirstName());
                assertEquals( "Last2",user.getLastName());
                assertEquals("user2",user.getUsername());
                assertEquals( "test@test2.com",user.getEmail());
                assertEquals("1234567",user.getPassword());
                return user;
            }
        });
        when(tutorDao.findOne(any(Long.class))).thenReturn(tutor);
        when(tutorDao.save(any(Tutor.class)))
        .thenAnswer(new Answer<Tutor>() {
            public Tutor answer(InvocationOnMock invocation) throws Throwable {
                Tutor tutor= (Tutor) invocation.getArguments()[0];
                assertEquals( "newBio",tutor.getBio());
                assertEquals( new BigDecimal(20),tutor.getFee());
                return tutor;
            }
        });
        
        editFormService.saveFrom(editForm);
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertEmailUniqueness() {
        EditForm editForm = new EditForm();
        user = new User();
        User otherUser = new User();
        otherUser.setEmail("newtest@test.com");
        editForm.setFirstName("First");
        editForm.setLastName("Last");
        editForm.setUsername("user");
        editForm.setEmail("newtest@test.com");
        editForm.setPassword("123456");
        editForm.setUserId(0L);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("user");
        user.setEmail("test@test.com");
        user.setPassword("123456");
       
        when(userDao.findOne(any(Long.class))).thenReturn(user);
        when(userDao.findByEmailLike(any(String.class))).thenReturn(otherUser);
        
        editFormService.saveFrom(editForm);
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertUsernameUniqueness() {
        EditForm editForm = new EditForm();
        user = new User();
        User otherUser = new User();
        otherUser.setUsername("user");
        editForm.setFirstName("First");
        editForm.setLastName("Last");
        editForm.setUsername("newuser");
        editForm.setEmail("newtest@test.com");
        editForm.setPassword("123456");
        editForm.setUserId(0L);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("user");
        user.setEmail("test@test.com");
        user.setPassword("123456");
       
        when(userDao.findOne(any(Long.class))).thenReturn(user);
        when(userDao.findByUsernameLike(any(String.class))).thenReturn(otherUser);
        
        editFormService.saveFrom(editForm);
    }
    
    @After
    public void reset_mocks() {
        reset(userDao);
    }

}