
package org.sample.controller.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidTutorException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
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
		private List<Classes> classes = new LinkedList<Classes>();
		private List<StudyCourse> courses = new LinkedList<StudyCourse>();
		private User testUser = new User();
	
	
	@Autowired
    private TutorFormService tutorFormService;

	@Autowired
	private TutorDao tutorDao;
	
	@Autowired
	private UserDao userDao;

    @Test
    public void CorrectDataSavedToDataBase() {

        TutorForm tutorForm = new TutorForm();
        
        for(int i = 0;i<2;i++)
        {
        	StudyCourse course = new StudyCourse();
        	course.setName("Course "+i);
        	course.setFaculty("Faculty "+i);
        	courses.add(course);
        }
        
        for(int i = 0;i<3;i++)
        {
        	Classes nextClass = new Classes();
        	nextClass.setName("Class"+i);
        	//nextClass.setStudyCourse(courses.get(i%2));
        	nextClass.setGrade(i+2);
        	classes.add(nextClass);
        }
        
        tutorForm.setFee(new BigDecimal(20.50));
        tutorForm.setStudyCourseList(courses);
        tutorForm.setClassList(classes);
        tutorForm.setBio("I am awesome");
        testUser = userDao.save(testUser);
        tutorForm.setUserId(testUser.getId());

        assertNull(tutorForm.getId());
        
        userDao.save(testUser);
        tutorFormService.saveFrom(tutorForm);

        assertNotNull(tutorForm.getId());
        assertTrue(tutorForm.getId() > 0);
        
        Tutor tutor = tutorDao.findOne(tutorForm.getId());
        assertEquals(new HashSet<Classes>(classes),tutor.getClasses());
        assertEquals(new HashSet<StudyCourse>(courses),tutor.getCourses());
        assertEquals(new BigDecimal(20.50),tutor.getFee());
        assertEquals("I am awesome",tutor.getBio());
        assertEquals(testUser, tutor.getStudent());
        assertEquals(true, testUser.isTutor());
        assertEquals(tutor, tutor.getStudent().getTutor());
    }
    
}