package org.sample.controller.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.model.User;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.TeamDao;
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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/springData.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class FormServiceTransactionTest {	
	@Autowired
    private FormService FormService;
	@Autowired
	private UserDao userDao;
	private RegisterForm registerForm;
	
	@Before
	public void setUp(){
		registerForm = new RegisterForm();
        
        registerForm.setFirstName("First");
        registerForm.setLastName("Last");
        registerForm.setUsername("user");
        registerForm.setEmail("test@test.com");
        registerForm.setPassword("123456");
	}

    @Test
    public void CorrectDataSavedInDatabase() {
        FormService.saveFrom(registerForm);

        assertNotNull(registerForm.getId());
        assertTrue(registerForm.getId() > 0);
        
        Iterable<User> users = userDao.findAll();
        User user = users.iterator().next();
        assertEquals("First",user.getFirstName());
        assertEquals("Last",user.getLastName());
        assertEquals("user",user.getUsername());
        assertEquals("test@test.com",user.getEmail());
        assertEquals("123456",user.getPassword());
        assertEquals(false,user.isTutor());
        assertEquals(false,user.isTimetableActive());
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertEmailUniqueness() {

        FormService.saveFrom(registerForm);
        //We will check username uniqueness in another test
        registerForm.setUsername("usr");
        
        FormService.saveFrom(registerForm);
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertUsernameUniqueness() {
        FormService.saveFrom(registerForm);
        //We will check email uniqueness in another test
        registerForm.setEmail("mail@mail.c");
        
        FormService.saveFrom(registerForm);
    }

   


}