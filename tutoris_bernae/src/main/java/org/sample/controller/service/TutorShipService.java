package org.sample.controller.service;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.TutorShipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorShipService {
	
	 @Autowired TutorShipDao tutorShipDao;
	 @Autowired MessageService messageService;  

	/**
	 * Creates a tutorship between the given tutor and the student
	 * which can then be confirmed by the student
	 * @param tutor the tutor offering the tutorship
	 * @param student the user which later needs to confirm (accept) the tutorship
	 */
	public void createTutorShip(Tutor tutor, User student) {
		if(tutorShipDao.findByTutorAndStudent(tutor, student) != null)
			return;
		TutorShip tutorShip = new TutorShip(tutor, student);
		tutorShipDao.save(tutorShip);
	}
	
	/**
	 * Confirms a tutorship between the given tutor and student (user) and exchanges
	 * contact details via a message between them
	 * @param tutor the tutor who offered the tutorship
	 * @param student the user who confirmed the tutorship
	 * @throws InvalidTutorShipException if no tutorship between this tutor and student was created 
	 * or when the tutorship was already confirmed
	 */
	public void confirmTutorShip(Tutor tutor, User student) throws InvalidTutorShipException {
		TutorShip confirmedTutorShip = tutorShipDao.findByTutorAndStudent(tutor, student);
		if(confirmedTutorShip == null)
			throw new InvalidTutorShipException("No such tutorship has been offered");
		if(confirmedTutorShip.getConfirmed())
			throw new InvalidTutorShipException("This tutorship has already been confirmed");
		confirmedTutorShip.setConfirmed(true);
		messageService.sendTutorShipConfirmedMessage(student, tutor.getStudent());
		messageService.exchangeContactDetails(tutor.getStudent(), student);
		tutorShipDao.save(confirmedTutorShip);
	}

}
