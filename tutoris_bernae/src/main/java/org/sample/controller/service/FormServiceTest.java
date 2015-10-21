package org.sample.controller.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.exceptions.InvalidUserPasswordException;
import org.sample.controller.pojos.LoginForm;
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
import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class FormServiceTest {

	@Configuration
    static class ContextConfiguration {

	     @Bean
	     public UserDao userDaoMock() {
	    	 UserDao userDao = mock(UserDao.class);
	    	 return userDao;
	     }
        @Bean
        public FormService formService() {
            FormService formService = new FormService();
            
            return formService;
        }
    }
	
	@Autowired
    private FormService FormService;
	@Qualifier("userDaoMock")
	@Autowired
	private UserDao userDao;

    @Test
    public void CorrectDataSaved() {

        RegisterForm registerForm = new RegisterForm();
        
        registerForm.setFirstName("First");
        registerForm.setLastName("Last");
        registerForm.setUsername("user");
        registerForm.setEmail("test@test.com");
        registerForm.setPassword("123456");

        when(userDao.save(any(User.class)))
                .thenAnswer(new Answer<User>() {
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        User user = (User) invocation.getArguments()[0];
                        assertEquals(user.getFirstName(), "First");
                        assertEquals(user.getLastName(), "Last");
                        assertEquals(user.getUsername(), "user");
                        assertEquals(user.getEmail(), "test@test.com");
                        assertEquals(user.getPassword(), "123456");

                        user.setId(1L);
                        return user;
                    }
                });

        assertNull(registerForm.getId());
        

        FormService.saveFrom(registerForm);

        assertNotNull(registerForm.getId());
        assertTrue(registerForm.getId() > 0);
    }
    
    @After
    public void reset_mocks() {
        reset(userDao);
    }


}