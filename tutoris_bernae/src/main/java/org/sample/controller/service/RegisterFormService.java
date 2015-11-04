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


@Service
public class RegisterFormService{

    @Autowired UserDao userDao;
    
    @Transactional
    public RegisterForm saveFrom(RegisterForm registerForm) throws InvalidUserException{

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