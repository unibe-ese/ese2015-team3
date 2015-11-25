package org.sample.controller.service;

import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.model.Message;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.TutorShipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates and confirms Tutorships. Uses message with specific
 * Subject () to decide if it needs to create new TutorShips.
 * @author pf15ese
 *
 */
@Service
public class TutorShipService{
	
	@Autowired TutorShipDao tutorShipDao;
	@Autowired MessageService messageService;  
	public static final String ACTION_OFFER_TUTORSHIP = "OFFER_TUTORSHIP";
	 
	/**
	 * Modifies messages with message action ACTION_OFFER_TUTORSHIP, by
	 * creating a tutorship and adding a link to confirm this tutorship
	 * @param message the message which should be modified, does nothing 
	 * except the messageSubjectAction is ACTION_OFFER_TUTORSHIP, should not be null
	 */
	public void addOfferedTutorShip(Message message) {
		assert(message!=null);
		assert(message.getMessageSubject()!=null);
		if (!(ACTION_OFFER_TUTORSHIP).equals(message.getMessageSubject().getAction())) return;
		
		TutorShip newTutorShip = createTutorShip(message.getSender().getTutor(), message.getReceiver());
		message.setMessageText(message.getMessageText()+"<br>"
				+"<a href=\"/tutoris_baernae/confirmTutorShip?tutorUserId="
				+newTutorShip.getTutor().getId()+"\"> Click here to confirm this TutorShip </a>");
	}
		
	/**
	 * Creates a tutorship between the given tutor and the student
	 * which can then be confirmed by the student
	 * (private, because tutorShips are currently created while sending messages, via on send)
	 * @param tutor the tutor offering the tutorship
	 * @param student the user which later needs to confirm (accept) the tutorship
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
	 * contact details via a message between them
	 * @param tutor the tutor who offered the tutorship, not null
	 * @param student the user who confirmed the tutorship, not null
	 * @throws InvalidTutorShipException if no tutorship between this tutor and student was created 
	 * or when the tutorship was already confirmed
	 */
	public void confirmTutorShip(Tutor tutor, User student) throws InvalidTutorShipException {
		assert(tutor!=null);
		assert(student!=null);
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
