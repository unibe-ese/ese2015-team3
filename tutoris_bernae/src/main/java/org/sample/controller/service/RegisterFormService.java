package org.sample.controller.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.RegisterForm;
import org.sample.model.Address;
import org.sample.model.User;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;


/**
 * Registers new user by adding them to the database. 
 * Only handles the registering of new Users, tutors are handled by the TutorFormService
 */
@Service
public class RegisterFormService{

    @Autowired UserDao userDao;
    
    /**
     * Creates a user with the details given by the RegisterForm, and saves him to the database
     * @param registerForm a RegisterForm, not null
     * @return the given RegisterForm, with the id set to the new Users id
     * @throws InvalidUserException if the email or the username is already in use by another user
     */
    @Transactional
    public RegisterForm saveFrom(RegisterForm registerForm) throws InvalidUserException{
    	assert(registerForm!=null);
        User user = new User();
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        String username = registerForm.getUsername();
        if(!usernameAvailable(username)) throw new InvalidUserException("Your username must be unique");
        user.setUsername(registerForm.getUsername());
        user.setPassword(registerForm.getPassword());
        String email = registerForm.getEmail();
        if(!emailAvailable(email)) throw new InvalidUserException("Your email must be unique");
        user.setEmail(registerForm.getEmail());
        user.setRole("ROLE_USER");
        
        user = userDao.save(user);   // save object to DB
        
        registerForm.setId(user.getId());

        return registerForm;
    }
    
    private boolean usernameAvailable(String username){
    	if(userDao.findByUsernameLike(username)==null) return true;
    	return false;
    }
    private boolean emailAvailable(String email){
    	if(userDao.findByEmailLike(email)==null) return true;
    	return false;
    }
}