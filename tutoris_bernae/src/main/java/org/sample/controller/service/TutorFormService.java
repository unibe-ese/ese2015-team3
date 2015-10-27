package org.sample.controller.service;

import java.util.HashSet;
import java.util.Set;

import org.sample.controller.pojos.TutorForm;
import org.sample.model.Classes;
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

@Service
public class TutorFormService {
	@Autowired TutorDao tutorDao;
	@Autowired UserDao userDao;
	

    
    @Transactional
	public void saveFrom(TutorForm tutorForm, User user) {
		Tutor tutor = new Tutor();
		
		tutor.setClasses(new HashSet<Classes>(tutorForm.getClassList()));
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
