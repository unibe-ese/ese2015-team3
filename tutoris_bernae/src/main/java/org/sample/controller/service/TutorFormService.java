package org.sample.controller.service;

import java.util.HashSet;

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
	@Autowired CompletedClassesService completedClassesService;
    
    /**
     * Creates a tutor out of a TutorForm and save him to the database, and also
     * updates the user belonging to that tutor.
     * @param tutorForm a TutorForm, not null
     */
    @Transactional
	public void saveFrom(TutorForm tutorForm) {
    	assert (tutorForm!=null);
    	assert (tutorForm.getUserId()!=null);
		Tutor tutor = new Tutor();
		User user = userDao.findOne(tutorForm.getUserId());
		tutor.setCompletedClasses(new HashSet<CompletedClasses>(tutorForm.getClassList()));
		tutor.setAverageGrade(completedClassesService.calculateAverageGrade(tutorForm.getClassList()));
		tutor.setCourses(new HashSet<StudyCourse>(tutorForm.getStudyCourseList()));
		tutor.setBio(tutorForm.getBio());
		tutor.setFee(tutorForm.getFee());
		tutor.setStudent(user);
		tutor = tutorDao.save(tutor);
		user.setTutor(tutor);
		user.setTutor(true);
		user.setRole("ROLE_TUTOR");
		userDao.save(user);

		
		tutorForm.setId(tutor.getId());
	}





}
