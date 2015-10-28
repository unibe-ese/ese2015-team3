package org.sample.controller.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.model.Address;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Team;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TeamDao;
import org.sample.model.dao.TutorDao;
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
public class EditFormService{

    @Autowired UserDao userDao;
    @Autowired TutorDao tutorDao;
    @Autowired
    StudyCourseDao studyCourseDao;
    
    @Autowired
    ClassesDao classesDao;
  
    @Transactional
    public EditForm saveFrom(EditForm editForm) throws InvalidUserException{
		User user = userDao.findOne(editForm.getUserId());
		String email = editForm.getEmail();
		if(!email.equals(user.getEmail())&&!emailAvailable(email)) throw new InvalidUserException("Your email must be unique");
		user.setEmail(email);
		user.setFirstName(editForm.getFirstName());
		user.setLastName(editForm.getLastName());
		user.setPassword(editForm.getPassword());
		String username = editForm.getUsername();
		if(!username.equals(user.getUsername())&&!usernameAvailable(username)) throw new InvalidUserException("Your username must be unique");
		user.setUsername(username);
		user = userDao.save(user);		// it automatically updates user (based on id)
    	return editForm;
    }
    
    @Transactional
    public TutorEditForm saveFrom(TutorEditForm editForm) throws InvalidUserException{
		User user = userDao.findOne(editForm.getUserId());
		String email = editForm.getEmail();
		if(!email.equals(user.getEmail())&&!emailAvailable(email)) throw new InvalidUserException("Your email must be unique");
		user.setEmail(email);
		user.setFirstName(editForm.getFirstName());
		user.setLastName(editForm.getLastName());
		user.setPassword(editForm.getPassword());
		String username = editForm.getUsername();
		if(!username.equals(user.getUsername())&&!usernameAvailable(username)) throw new InvalidUserException("Your username must be unique");
		user.setUsername(username);
		user = userDao.save(user);		// it automatically updates user (based on id)
		Tutor tutor = tutorDao.findOne(editForm.getTutorId());
		tutor.setClasses(new HashSet<Classes>(editForm.getClassList()));
		tutor.setCourses(new HashSet<StudyCourse>(editForm.getStudyCourseList()));
		tutor.setBio(editForm.getBio());
		tutor.setFee(editForm.getFee());
		tutor = tutorDao.save(tutor);
    	return editForm;
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