package org.sample.test.service;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.service.EditFormService;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


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
	@Autowired
	private ClassesDao classesDao;
	
	
	private User newUser;
	private User newTutorUser;
	private Tutor newTutor;
	private TutorEditForm tutorEditForm;
	private EditForm editForm;
	private Classes classes1;
	private CompletedClasses completedClasses1;
	private Set<CompletedClasses> completedClassesOld;
	private Classes classes2;
	private CompletedClasses completedClasses2;
	private List<CompletedClasses> completedClassesNew;

	
	@Before
	public void setUpExampleDatas(){
		newUser = new User();
		newUser.setFirstName("First");
		newUser.setLastName("Last");
		newUser.setPassword("123");
		newUser.setEmail("mail@mail.mail");
		newUser = userDao.save(newUser);
		newTutor = new Tutor();
		classes1 = new Classes();
		classesDao.save(classes1);
		completedClasses1 = new CompletedClasses(classes1, 4);
		completedClassesOld = new HashSet<CompletedClasses>();
		completedClassesOld.add(completedClasses1);
		newTutor.setCompletedClasses(completedClassesOld);
		newTutor.setCourses(new HashSet<StudyCourse>());
		newTutor = tutorDao.save(newTutor);
		newTutorUser = new User();
		newTutorUser.setPassword("123");
		newTutorUser.setEmail("tutormail@mail.mail");
		newTutorUser.setTutor(true);
		newTutorUser.setTutor(newTutor);
		newTutorUser = userDao.save(newTutorUser);
		
		editForm = new EditForm();
        editForm.setFirstName("First2");
        editForm.setLastName("Last2");
        editForm.setEmail("test@test.com");
        editForm.setPassword("123456");
        editForm.setUserId(newUser.getId());
	}

    @Test
    public void EditFormCorrectDataSavedInDatabase() {
        editFormService.saveFrom(editForm);
        User user = userDao.findOne(editForm.getUserId());
        assertEquals("First2",user.getFirstName());
        assertEquals("Last2",user.getLastName());
        assertEquals("test@test.com",user.getEmail());
        assertEquals("123456",user.getPassword());
        assertEquals(false,user.isTutor());
        assertEquals(false,user.isTimetableActive());
    }
    
    // TODO: test changed courses list as well
    @Test
    public void TutorEditFormCorrectDataSavedInDatabase() {
        classes2 = new Classes();
        classesDao.save(classes2);
		completedClasses2 = new CompletedClasses(classes2, 4);
		completedClassesNew = new LinkedList<CompletedClasses>();
		completedClassesNew.add(completedClasses2);
        tutorEditForm = new TutorEditForm();
        tutorEditForm.setFirstName("newTutorTest");
        tutorEditForm.setLastName("newLast");
        tutorEditForm.setEmail("newtutortest@tutortest.com");
        tutorEditForm.setPassword("123456");
        tutorEditForm.setUserId(newTutorUser.getId());
        tutorEditForm.setClassList(completedClassesNew);
        tutorEditForm.setStudyCourseList(new LinkedList<StudyCourse>());
        tutorEditForm.setBio("newBio");
        tutorEditForm.setFee(new BigDecimal(20));
        tutorEditForm.setTutorId(newTutor.getId());
        
        editFormService.saveFrom(tutorEditForm);
       
        User user = userDao.findOne(tutorEditForm.getUserId());
        assertEquals("newTutorTest",user.getFirstName());
        assertEquals("newLast",user.getLastName());
        assertEquals("newtutortest@tutortest.com",user.getEmail());
        assertEquals("123456",user.getPassword());
        assertEquals(true,user.isTutor());
        assertEquals(false,user.isTimetableActive());
        Tutor tutor = tutorDao.findOne(tutorEditForm.getTutorId());
        assertEquals("newBio",tutor.getBio());
        assertEquals(new BigDecimal(20),tutor.getFee());
        //We need to compare the completedClasses, but because our example data isn't saved we have to compare like this
        assertTrue(CompletedClassesCollectionEqualsWithoutId(completedClassesNew,tutor.getCompletedClasses()));
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertEmailUniqueness() {
    	editForm.setEmail("tutormail@mail.mail"); //Will throw an exception because the tutoruser already uses this mail
        editFormService.saveFrom(editForm);
    }
    
//    
//    @Test(expected=InvalidUserException.class) 
//    public void AssertUsernameUniqueness() {
//		editForm.setUsername("tutortest"); //Will throw an exception because the tutoruser already uses this mail
//        editFormService.saveFrom(editForm);
//
//    }
//    
    //Checks if two collections of completed classes are equals, except the id of the classes.
    //Use this if comparing a saved (in the database) collection of completedClasses and and unsaved.
    public boolean CompletedClassesCollectionEqualsWithoutId(Collection<CompletedClasses> expected, Collection<CompletedClasses> given)
    {
    	assertEquals(expected.size(),given.size());
    	Checking: for(CompletedClasses c : given){
    		for(CompletedClasses e : expected){
    			if(e.getGrade() == c.getGrade() && e.getClasses().equals(c.getClasses())) continue Checking;
    		}
    		return false;
    	}
    	return true;
    	
    }

}