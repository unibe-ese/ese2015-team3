package org.sample.test.service;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ServiceTransactionTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class RegisterFormServiceTransactionTest extends ServiceTransactionTest{	
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
        assertEquals("test@test.com",user.getEmail());
        assertEquals("123456",user.getPassword());
    }
    
    @Test(expected=InvalidUserException.class) 
    public void AssertEmailUniqueness() {

        registerFormService.saveFrom(registerForm);
        //We will check username uniqueness in another test, so we have to choose another username to assure only emails are the same
        
        registerFormService.saveFrom(registerForm);
    }
//    
//    @Test(expected=InvalidUserException.class) 
//    public void AssertUsernameUniqueness() {
//        registerFormService.saveFrom(registerForm);
//        //We will check email uniqueness in another test
//        registerForm.setEmail("mail@mail.c");
//        
//        registerFormService.saveFrom(registerForm);
//    }
//
//   


}