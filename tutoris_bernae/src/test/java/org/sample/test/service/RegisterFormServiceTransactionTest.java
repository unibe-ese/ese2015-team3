package org.sample.test.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.User;
import org.sample.model.dao.AddressDao;
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
public class RegisterFormServiceTransactionTest {	
	@Autowired
    private RegisterFormService registerFormService;
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
        registerFormService.saveFrom(registerForm);

        assertNotNull(registerForm.getId());
        assertTrue(registerForm.getId() > 0);
        
        User user = userDao.findOne(registerForm.getId());
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

        registerFormService.saveFrom(registerForm);
        //We will check username uniqueness in another test, so we have to choose another username to assure only emails are the same
        registerForm.setUsername("usr");
        
        registerFormService.saveFrom(registerForm);
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertUsernameUniqueness() {
        registerFormService.saveFrom(registerForm);
        //We will check email uniqueness in another test
        registerForm.setEmail("mail@mail.c");
        
        registerFormService.saveFrom(registerForm);
    }

   


}