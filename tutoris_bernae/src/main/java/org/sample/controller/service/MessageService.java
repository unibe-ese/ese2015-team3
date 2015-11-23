package org.sample.controller.service;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.MessageForm;
import org.sample.model.Message;
import org.sample.model.MessageSubject;
import org.sample.model.User;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.MessageSubjectDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;

/**
 * Offers services to load messages, to send (by saving them to the database) and 
 * to "read" them. Creates generated messages and also automatically sends notification for new messages
 * via mail.
 * Also assures that all messageSubjects needed by this service to autocreate messages are in the database
 * and creates them if not
 * @author pf15ese
 */
@Service
public class MessageService{

    @Autowired UserDao userDao;  
    @Autowired MessageDao messageDao;
    @Autowired MessageSubjectDao messageSubjectDao; 
    @Autowired TutorShipService tutorShipService;
    @Autowired MailService mailService;
	private MessageSubject contactDetailsSubject;  
	private MessageSubject confirmedTutorShipSubject;
    
    private static final String ACTION_OFFER_TUTORSHIP = "OFFER_TUTORSHIP";
    
	/**
     * Compares messages by date to order them from newest to oldest
     */
   public static Comparator<Message> MessageDateComparator = new Comparator<Message>()
    {
		public int compare(Message m1, Message m2) {
			return -(m1.getSendDate().compareTo(m2.getSendDate()));
		}
    	
    };
    
    @PostConstruct
    public void intitializeSubject()
    {
    	createNeededMessageSubjects();
    }
    /**
     * Returns all messages the user has received, ordered form newest send date to oldest.
     * @param user from which we want to get all messages
     * @return an ordered list of messages, form newest send date to oldest.
     */
   	@Transactional
    public List<Message> getOrderedMessagesList(User user) {
    	List<Message> messages = (List<Message>) messageDao.findAllByReceiver(user);
    	Collections.sort(messages,MessageDateComparator);
    	return messages;
    }
    
    /**
     * Sends the message by saving it to the database.
     * @param messageForm a valid messageForm, not null
     * @param Userthe sender of the message, not null
     * @throws InvalidUserException if the receiver doesn't exist/couldn't be found by the
     * string receiver given in the messageForm
     */
    @Transactional
    public MessageForm sendMessageFromForm(MessageForm messageForm, User sender) throws InvalidUserException{
    	assert(messageForm!=null);
    	assert(sender!=null);
    	Message message = new Message();
    	User receiver = userDao.findByUsername(messageForm.getReceiver());
    	if(receiver==null) throw new InvalidUserException("The user you want to send a message does not exist");
    	message.setSender(sender);
    	message.setReceiver(receiver);
    	message.setMessageSubject(messageForm.getMessageSubject());
    	message.setMessageText(messageForm.getMessageText());
    	message = send(message);
    	messageForm.setId(message.getId());
    	return messageForm;
    }
    
    private Message send(Message message)
    {
    	message.setSendDate(new Date());
    	if(message.getMessageSubject().getAction() != null)
    		handleMessageAction(message);
    	message = messageDao.save(message);
    	mailService.sendMessageNotificationMail(message);
    	return message;
    }

	private void handleMessageAction(Message message) {
		if(message.getMessageSubject().getAction().equals(ACTION_OFFER_TUTORSHIP))
			tutorShipService.createTutorShip(message.getSender().getTutor(), message.getReceiver());
		
	}

	/**
	 * "Reads" a message for a user by returning it and setting the wasRead variabel of
	 * the message to true in the database. If the user isn't the receiver, the message
	 * isn't read and null is returned
	 * @param messageId a Long defining the message we want to read
	 * @param user the user who wants to read the message
	 * @return the Message given by the id with wasRead = true if the user is the receiver
	 * of the message or null if the user isn't the reciever of the message
	 */
	public Message read(Long messageId, User user) {
		Message opened = messageDao.findOne(messageId);
		if(opened == null  || !opened.getReceiver().equals(user))
			return null;
		opened.setWasRead(true);
		messageDao.save(opened);
		return opened;
	}
	
	public Message sendContactDetails(User sender, User receiver) {
		assert(!sender.equals(receiver));
		Message contactDetails = new Message();
		contactDetails.setMessageSubject(contactDetailsSubject);
		String allContactInformations = new StringBuilder().append("You can contact me as follows: \n")
				.append("Full name: "+sender.getFirstName()+" "+sender.getLastName()+" \n")
				.append("Email: "+sender.getEmail()+" \n")
				.append("This message is auto generated. Do not answer")
				.toString();
		contactDetails.setMessageText(allContactInformations);
		contactDetails.setReceiver(receiver);
		contactDetails.setSender(sender);
		return send(contactDetails);
	}
	
	public Message sendTutorShipConfirmedMessage(User sender, User receiver) {
		Message acceptanceMessage = new Message();
		acceptanceMessage.setMessageSubject(confirmedTutorShipSubject);
		String messageText = new StringBuilder().append("Your Tutorship for "+sender.getFirstName()+"was accepted by him!")
				.append("This message is auto generated. Do not answer")
				.toString();
		acceptanceMessage.setMessageText(messageText);
		acceptanceMessage.setReceiver(receiver);
		acceptanceMessage.setSender(sender);
		return send(acceptanceMessage);
	}

	public List<MessageSubject> getAccessibleSubjects(User user) {
		List<MessageSubject> accessibleSubjects = (List<MessageSubject>) messageSubjectDao.findAllByRole("ROLE_USER");
		if(user.isTutor()) accessibleSubjects.addAll((List<MessageSubject>) messageSubjectDao.findAllByRole("ROLE_TUTOR"));
		return accessibleSubjects;
	}
	
    private void createNeededMessageSubjects() {
		if(messageSubjectDao.findByAction(ACTION_OFFER_TUTORSHIP) == null){
			MessageSubject offerTutorShip = new MessageSubject();
			offerTutorShip.setMessageSubjectName("Offer Tutorship");
			offerTutorShip.setRole("ROLE_TUTOR");
			offerTutorShip.setAction(ACTION_OFFER_TUTORSHIP);
			offerTutorShip.setGenericEmailMessage("offers you a tutorship");
			offerTutorShip.setActionBaseLink("/confirmTutorShip?tutorUserId=");
			messageSubjectDao.save(offerTutorShip);
		}
		if(messageSubjectDao.findByMessageSubjectName("Contact Details") == null){
			MessageSubject contactDetails = new MessageSubject();
			contactDetails.setMessageSubjectName("Contact Details");
			contactDetails.setRole("ROLE_GENERATED");
			contactDetails.setGenericEmailMessage("shared his contact details with you");
			contactDetailsSubject = messageSubjectDao.save(contactDetails);
		}
		else{
			contactDetailsSubject = messageSubjectDao.findByMessageSubjectName("Contact Details");
		}
		if(messageSubjectDao.findByMessageSubjectName("Tutorship accepted!") == null){
			MessageSubject confirmedTutorship = new MessageSubject();
			confirmedTutorship.setMessageSubjectName("Tutorship accepted!");
			confirmedTutorship.setRole("ROLE_GENERATED");
			confirmedTutorship.setGenericEmailMessage("accepted your tutorship offer");
			confirmedTutorShipSubject = messageSubjectDao.save(confirmedTutorship);
		}
		else{
			confirmedTutorShipSubject = messageSubjectDao.findByMessageSubjectName("Tutorship accepted!");
		}
	}
	public void exchangeContactDetails(User user1, User user2) {
		sendContactDetails(user1, user2);
		sendContactDetails(user2, user1);
	}
}