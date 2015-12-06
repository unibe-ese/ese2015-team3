package org.sample.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.Test;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.TutorFormService;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ServiceTransactionTest;
import org.springframework.beans.factory.annotation.Autowired;

public class TutorFormServiceTransactionTest extends ServiceTransactionTest{
	
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
        assertEquals(tutor, tutor.getStudent().getTutor());
    }
    
    @Test(expected = InvalidUserException.class)
    public void tutorsCannotUpgradeToTutorAgain(){
    	User user = new User();
    	user.setEmail("mail@mail.m");
    	user = userDao.save(user);
    	Tutor tutor = new Tutor();
    	tutorDao.save(tutor);
    	user.setTutor(tutor);
    	user = userDao.save(user);
       	Classes classes1 = new Classes();
    	CompletedClasses completedClasses1 = new CompletedClasses(classes1, 5);
    	LinkedList<CompletedClasses> completedClassesList = new LinkedList<CompletedClasses>();
    	completedClassesList.add(completedClasses1);
    	TutorForm tutorForm = new TutorForm();
    	tutorForm.setUserId(user.getId());
    	tutorForm.setClassList(completedClassesList);
    	tutorForm.setStudyCourseList(new LinkedList<StudyCourse>());
    	tutorForm.setBio("newBio");
    	tutorForm.setFee(new BigDecimal(20.50));
    	tutorFormService.saveFrom(tutorForm);
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