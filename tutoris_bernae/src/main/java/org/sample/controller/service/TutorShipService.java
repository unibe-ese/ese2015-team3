package org.sample.controller.service;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.model.Message;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.TutorShipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates and confirms Tutorships. Enhances message with links
 * to give access to confirm tutorships.
 */
@Service
public class TutorShipService{
	
	@Autowired 
	private TutorShipDao tutorShipDao;
	@Autowired 
	private TutorDao tutorDao;
	@Autowired
	private MessageService messageService;  

	 
	/**
	 * Modifies messages by creating a tutorship between the sender of the message as tutor
	 * and the receiver as student and adding a link to confirm this tutorship
	 * If there already exist a tutorship between them, this tutorship is linked again in the message
	 * @param message the message which should be modified, should not be null
	 * @throws InvalidUserException if the sender of the message isn't a tutor and should therefore
	 * not be able to offer a tutorship
	 */
	public void addOfferedTutorShip(Message message) throws InvalidUserException{
		assert(message!=null);
		Tutor offeringTutor = message.getSender().getTutor();
		if(offeringTutor==null) 
				throw new InvalidUserException("The sender of this message cannot offer a tutorship");
		TutorShip newTutorShip = createTutorShip(offeringTutor, message.getReceiver());
		message.setMessageText(message.getMessageText()+"<br>"
				+"<a href=\"/tutoris_baernae/paypal?tutorshipId="+newTutorShip.getId()
				+"\"><u> Click here to confirm this TutorShip and pay the fee.</u></a>");
	}
		
	/**
	 * Creates a tutorship between the given tutor and the student
	 * which can then be confirmed by the student
	 * (private, because tutorShips are currently created while sending messages, via on addOfferedTutorShip)
	 * @param tutor the tutor offering the tutorship, not null
	 * @param student the user which later needs to confirm (accept) the tutorship, not null
	 */
	private TutorShip createTutorShip(Tutor tutor, User student) {
		TutorShip possibleExistingTutorShip = tutorShipDao.findByTutorAndStudent(tutor, student);
		if(possibleExistingTutorShip != null)
			return possibleExistingTutorShip;
		TutorShip tutorShip = new TutorShip(tutor, student);
		return tutorShipDao.save(tutorShip);
	}
	
	/**
	 * Confirms a tutorship between the given tutor and student (user) and exchanges
	 * contact details via a message between them. Also reminds the student that he can 
	 * rate the tutor
	 * @param tutor the tutor who offered the tutorship, not null
	 * @param student the user who confirmed the tutorship, not null
	 * @throws InvalidTutorShipException if no tutorship between this tutor and student was created 
	 * or when the tutorship was already confirmed
	 */
	public void confirmTutorShip(Long tutorshipId) throws InvalidTutorShipException {
		TutorShip confirmedTutorShip = tutorShipDao.findOne(tutorshipId);
		if(confirmedTutorShip == null)
			throw new InvalidTutorShipException("No such tutorship has been offered");
		if(confirmedTutorShip.getConfirmed())
			throw new InvalidTutorShipException("This tutorship has already been confirmed");
		confirmedTutorShip.setConfirmed(true);
		Tutor tutor = confirmedTutorShip.getTutor();
		User student = confirmedTutorShip.getStudent();
		messageService.sendTutorShipConfirmedMessage(student, tutor.getStudent());
		messageService.exchangeContactDetails(tutor.getStudent(), student);
		//Currently the reminder for rating is send directly, would make more sense after a week
		messageService.sendRatingReminder(tutor, student);
		tutorShipDao.save(confirmedTutorShip);
		tutor.setConfirmedTutorShips(tutor.getConfirmedTutorShips()+1);
		tutorDao.save(tutor);
	}

}
