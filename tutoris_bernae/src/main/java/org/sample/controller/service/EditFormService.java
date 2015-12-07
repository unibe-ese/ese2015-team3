package org.sample.controller.service;


import java.util.HashSet;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Updates users which are probably also tutors with the information given
 * by either an editForm (for normal users) or a TutorEditForm (for user with tutor profile)
 * @author pf15ese
 */
@Service
public class EditFormService{

    @Autowired UserDao userDao;
    @Autowired TutorDao tutorDao;
    @Autowired StudyCourseDao studyCourseDao;
    @Autowired ClassesDao classesDao;

    /**
     * Saves the user with his changed details given by the EditForm to the database
     * @param editForm a EditForm, not null
     * @return the given EditForm
     * @throws InvalidUserException if the new email or the new username is already in use by another user
     */
    @Transactional
    public EditForm saveFrom(EditForm editForm) throws InvalidUserException{
    	assert(editForm!=null);
    	assert(editForm.getUserId()!=null);
		User user = userDao.findOne(editForm.getUserId());
		String email = editForm.getEmail();
		if(!email.equals(user.getEmail())&&!emailAvailable(email)) throw new InvalidUserException("Your email must be unique");
		user.setEmail(email);
		user.setFirstName(editForm.getFirstName());
		user.setLastName(editForm.getLastName());
		user.setPassword(editForm.getPassword());
		userDao.save(user);		// it automatically updates user (based on id)
    	return editForm;
    }
    
    /**
     * Saves the tutor with his changed user and tutor details given by the TutorEditForm to the database
     * @param tutorEditForm a TutorEditForm, not null
     * @return the given tutorEditForm
     * @throws InvalidUserException if the new email or the new username is already in use by another user
     */
    @Transactional
    public TutorEditForm saveFrom(TutorEditForm editForm) throws InvalidUserException{
    	assert(editForm!=null);
    	assert(editForm.getUserId()!=null);
    	assert(editForm.getTutorId()!=null);
		User user = userDao.findOne(editForm.getUserId());
		String email = editForm.getEmail();
		if(!email.equals(user.getEmail())&&!emailAvailable(email)) throw new InvalidUserException("Your email must be unique");
		user.setEmail(email);
		user.setFirstName(editForm.getFirstName());
		user.setLastName(editForm.getLastName());
		user.setPassword(editForm.getPassword());
		user = userDao.save(user);		// it automatically updates user (based on id)
		Tutor tutor = tutorDao.findOne(editForm.getTutorId());
		tutor.setCompletedClasses(new HashSet<CompletedClasses>(editForm.getClassList()));
		tutor.setCourses(new HashSet<StudyCourse>(editForm.getStudyCourseList()));
		tutor.setBio(editForm.getBio());
		tutor.setFee(editForm.getFee());
		tutor = tutorDao.save(tutor);
    	return editForm;
    }
    
    private boolean emailAvailable(String email){
    	return userDao.findByEmailLike(email)==null;
    }
}