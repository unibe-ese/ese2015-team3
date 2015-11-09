package org.sample.controller.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/springData.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class EditFormServiceTransactionTest {	
	@Autowired
    private EditFormService editFormService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TutorDao tutorDao;
	
	
	private User newUser;
	private User newTutorUser;
	private Tutor newTutor;
	private TutorEditForm tutorEditForm;
	private EditForm editForm;

	
	@Before
	public void setUp(){
		newUser = new User();
		newUser.setUsername("test");
		newUser.setFirstName("First");
		newUser.setLastName("Last");
		newUser.setPassword("123");
		newUser.setEmail("mail@mail.mail");
		newUser = userDao.save(newUser);
		newTutor = new Tutor();
		newTutor.setClasses(new HashSet<Classes>());
		newTutor.setCourses(new HashSet<StudyCourse>());
		newTutor = tutorDao.save(newTutor);
		newTutorUser = new User();
		newTutorUser.setUsername("tutortest");
		newTutorUser.setPassword("123");
		newTutorUser.setEmail("tutormail@mail.mail");
		newTutorUser.setTutor(true);
		newTutorUser.setTutor(newTutor);
		newTutorUser = userDao.save(newTutorUser);
		
		editForm = new EditForm();
        editForm.setFirstName("First2");
        editForm.setLastName("Last2");
        editForm.setUsername("user");
        editForm.setEmail("test@test.com");
        editForm.setPassword("123456");
        editForm.setUserId(newUser.getId());
        
        tutorEditForm = new TutorEditForm();
        tutorEditForm.setFirstName("newTutorTest");
        tutorEditForm.setLastName("newLast");
        tutorEditForm.setUsername("newuser");
        tutorEditForm.setEmail("newtutortest@tutortest.com");
        tutorEditForm.setPassword("123456");
        tutorEditForm.setUserId(newTutorUser.getId());
        tutorEditForm.setClassList(new LinkedList<Classes>());
        tutorEditForm.setStudyCourseList(new LinkedList<StudyCourse>());
        tutorEditForm.setBio("newBio");
        tutorEditForm.setFee(new BigDecimal(20));
        tutorEditForm.setTutorId(newTutor.getId());
	}

    @Test
    public void EditFormCorrectDataSavedInDatabase() {
        editFormService.saveFrom(editForm);
        User user = userDao.findOne(editForm.getUserId());
        assertEquals("First2",user.getFirstName());
        assertEquals("Last2",user.getLastName());
        assertEquals("user",user.getUsername());
        assertEquals("test@test.com",user.getEmail());
        assertEquals("123456",user.getPassword());
        assertEquals(false,user.isTutor());
        assertEquals(false,user.isTimetableActive());
    }
    
    // TODO: test classes and courses list as well
    @Test
    public void TutorEditFormCorrectDataSavedInDatabase() {
        editFormService.saveFrom(tutorEditForm);
        User user = userDao.findOne(tutorEditForm.getUserId());
        assertEquals("newTutorTest",user.getFirstName());
        assertEquals("newLast",user.getLastName());
        assertEquals("newuser",user.getUsername());
        assertEquals("newtutortest@tutortest.com",user.getEmail());
        assertEquals("123456",user.getPassword());
        assertEquals(true,user.isTutor());
        assertEquals(false,user.isTimetableActive());
        Tutor tutor = tutorDao.findOne(tutorEditForm.getTutorId());
        assertEquals("newBio",tutor.getBio());
        assertEquals(new BigDecimal(20),tutor.getFee());
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertEmailUniqueness() {
    	editForm.setEmail("tutormail@mail.mail"); //Will throw an exception because the tutoruser already uses this mail
        editFormService.saveFrom(editForm);
    }
    
    
    @Test(expected=InvalidUserException.class) 
    public void AssertUsernameUniqueness() {
		editForm.setUsername("tutortest"); //Will throw an exception because the tutoruser already uses this mail
        editFormService.saveFrom(editForm);

    }
	
   


}