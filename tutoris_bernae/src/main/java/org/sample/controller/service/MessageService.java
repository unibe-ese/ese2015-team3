package org.sample.controller.service;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.model.Classes;
import org.sample.model.Message;
import org.sample.model.StudyCourse;
import org.sample.model.User;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Offers services to load messages, to send (by saving them to the database) and 
 * to "read" them. Creates generated messages and also automatically sends notification for new messages
 * via mail.
 * Also assures that all messageSubjects needed by this service or others to autocreate or process
 * messages are in the database and creates them if not
 * @author pf15ese
 */
@Service
public class MessageService{

    @Autowired 
    private UserDao userDao;  
    @Autowired 
    private MessageDao messageDao;
    @Autowired
    private MailService mailService;
	@Autowired 
	private TutorShipService tutorShipService;
    
	/**
     * Compares messages by date to order them from newest to oldest
     */
   public static final Comparator<Message> MessageDateComparator = new Comparator<Message>()
    {
		public int compare(Message m1, Message m2) {
			return -(m1.getSendDate().compareTo(m2.getSendDate()));
		}
    	
    };
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
     * @param User the sender of the message, not null
     * @throws InvalidUserException if the receiver doesn't exist/couldn't be found by the
     * string receiver given in the messageForm
     */
    @Transactional
    public MessageForm sendMessageFromForm(MessageForm messageForm, User sender) throws InvalidUserException{
    	assert(messageForm!=null);
    	assert(sender!=null);
    	Message message = send(getMessageFromForm(messageForm,sender));
    	messageForm.setId(message.getId());
    	return messageForm;
    }
    
    public MessageForm sendTutorShipOffer(MessageForm messageForm, User sender)
    {
    	Message offerMessage = getMessageFromForm(messageForm,sender);
    	tutorShipService.addOfferedTutorShip(offerMessage);
    	offerMessage.setMessageSubject("Tutorship Offer");
    	offerMessage = send(offerMessage);
    	messageForm.setId(offerMessage.getId());
    	return messageForm;
    }
    
    private Message getMessageFromForm(MessageForm messageForm, User sender){
      	Message message = new Message();
    	User receiver = userDao.findByEmailLike(messageForm.getReceiver());
    	if(receiver==null) throw new InvalidUserException("The user you want to send a message does not exist");
    	message.setSender(sender);
    	message.setReceiver(receiver);
    	message.setMessageSubject(messageForm.getMessageSubject());
    	message.setMessageText(messageForm.getMessageText());
    	return message;
    }
    /**
     * "Sends" a message by saving it to the database and setting the send
     * date to now
     * @param message SendDate may not be set, will be set to now.
     * @return the message saved in the database (which is therefore "sent")
     */
    private Message send(Message message)
    {
    	assert message.getSendDate() == null;
    	message.setSendDate(new Date());
		
    	message = messageDao.save(message);
    	mailService.sendMessageNotificationMail(message);
    	return message;
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
	
		String allContactInformations = new StringBuilder().append("You can contact me as follows: \n")
				.append("<br>Full name: "+sender.getFirstName()+" "+sender.getLastName()+" \n")
				.append("<br>Email: "+sender.getEmail()+" \n")
				.append("<br>This message is auto generated. Do not answer")
				.toString();
		contactDetails.setMessageSubject("Contact Details");
		contactDetails.setMessageText(allContactInformations);
		contactDetails.setReceiver(receiver);
		contactDetails.setSender(sender);
		return send(contactDetails);
	}
	
	public Message sendTutorShipConfirmedMessage(User sender, User receiver) {
		Message acceptanceMessage = new Message();
		String messageText = new StringBuilder().append("Your Tutorship for "+sender.getFirstName()+" was accepted by him!\n")
				.append("This message is auto generated. Do not answer")
				.toString();
		acceptanceMessage.setMessageText(messageText);
		acceptanceMessage.setMessageSubject("Tutorship accepted!");
		acceptanceMessage.setReceiver(receiver);
		acceptanceMessage.setSender(sender);
		return send(acceptanceMessage);
	}
	
	public void exchangeContactDetails(User user1, User user2) {
		sendContactDetails(user1, user2);
		sendContactDetails(user2, user1);
	}

	public String createSearchCriteriaSubject(SearchForm searchedCriterias) {
		StudyCourse course = searchedCriterias.getStudyCourse();
		Classes classes = searchedCriterias.getClasses();
		if(course!=null&&classes!=null)
			return "I need a tutor in "+course.getName()+" especially for "+classes.getName();
		else if(course!=null)
			return "I need a tutor in "+course.getName();
		else if(classes!=null)
			return "I need a tutor in "+classes.getName();
		return "I need a tutor";
	}
	

        public String getMessageReceiverFirstName(String mail){
            User receiver = userDao.findByEmailLike(mail);
            if (receiver != null)
                return receiver.getFirstName();
            return null;
        }
}