package org.sample.test.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidTutorException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/springData.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TutorFormServiceTransactionTest {
	
	@Autowired
    private TutorFormService tutorFormService;

	@Autowired
	private TutorDao tutorDao;
	
	@Autowired
	private UserDao userDao;

    @Test
    public void CorrectDataSavedToDataBase() {


    	TutorForm tutorForm = new TutorForm();
    	User user = new User();
    	user.setEmail("mail@mail.m");
    	user.setEmail("test");
    	user = userDao.save(user);
    	Classes classes1 = new Classes();
    	CompletedClasses completedClasses1 = new CompletedClasses(classes1, 5);
    	LinkedList<CompletedClasses> completedClassesList = new LinkedList<CompletedClasses>();
    	completedClassesList.add(completedClasses1);
    	tutorForm.setUserId(user.getId());
    	tutorForm.setClassList(completedClassesList);
    	tutorForm.setStudyCourseList(new LinkedList<StudyCourse>());
    	tutorForm.setBio("newBio");
    	tutorForm.setFee(new BigDecimal(20.50));
        assertNull(tutorForm.getId());
        
        
        tutorFormService.saveFrom(tutorForm);

        assertNotNull(tutorForm.getId());
        assertTrue(tutorForm.getId() > 0);
        
        Tutor tutor = tutorDao.findOne(tutorForm.getId());
        assertTrue(CompletedClassesCollectionEqualsWithoutId(completedClassesList,tutor.getCompletedClasses()));
        assertEquals(new HashSet<StudyCourse>(),tutor.getCourses());
        assertEquals(new BigDecimal(20.50),tutor.getFee());
        assertEquals(new BigDecimal(5),tutor.getAverageGrade());
        assertEquals("newBio",tutor.getBio());
        assertEquals(user, tutor.getStudent());
        assertEquals(true, user.isTutor());
        assertEquals(tutor, tutor.getStudent().getTutor());
    }
    
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