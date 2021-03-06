package org.sample.controller.service;

import java.util.HashSet;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.TutorForm;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TutorFormService {
	@Autowired TutorDao tutorDao;
	@Autowired UserDao userDao;
	
	/**
     * Creates a tutor out of a TutorForm and save him to the database, and also
     * updates the user belonging to that tutor.
     * @param tutorForm a TutorForm, not null
     * @throws InvalidUserException if the user is already a tutor
     */
    @Transactional
	public void saveFrom(TutorForm tutorForm) throws InvalidUserException{
    	assert (tutorForm!=null);
    	assert (tutorForm.getUserId()!=null);
    	User user = userDao.findOne(tutorForm.getUserId());
    	if(user.getTutor()!= null) throw new InvalidUserException("This user is already a tutor");
		Tutor tutor = new Tutor();
		tutor.setCompletedClasses(new HashSet<CompletedClasses>(tutorForm.getClassList()));
		tutor.setCourses(new HashSet<StudyCourse>(tutorForm.getStudyCourseList()));
		tutor.setBio(tutorForm.getBio());
		tutor.setFee(tutorForm.getFee());
		tutor.setStudent(user);
		tutor = tutorDao.save(tutor);
		user.setTutor(tutor);
		user.setRole("ROLE_TUTOR");
		userDao.save(user);
		tutorForm.setId(tutor.getId());
	}





}
