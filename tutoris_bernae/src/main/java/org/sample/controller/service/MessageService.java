package org.sample.controller.service;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.Message;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Offers services to load messages, to send (by saving them to the database) and 
 * to "read" them
 * @author pf15ese
 */
@Service
public class MessageService{

    @Autowired UserDao userDao;  
    @Autowired MessageDao messageDao;  
   
  
    
    /**
     * Compares messages by date to order them from newest to oldest
     */
   public static Comparator<Message> MessageDateComparator = new Comparator<Message>()
    {
		public int compare(Message m1, Message m2) {
			return -(m1.getSendDate().compareTo(m2.getSendDate()));
		}
    	
    };
    
    //
    
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
    public MessageForm send(MessageForm messageForm, User sender) throws InvalidUserException{
    	assert(messageForm!=null);
    	assert(sender!=null);
    	Message message = new Message();
    	User receiver = userDao.findByUsername(messageForm.getReceiver());
    	if(receiver==null) throw new InvalidUserException("The user you want to send a message does not exist");
    	message.setSender(sender);
    	message.setReceiver(receiver);
    	message.setSendDate(new Date());
    	message.setMessageSubject(messageForm.getMessageSubject());
    	message.setMessageText(messageForm.getMessageText());
    	message = messageDao.save(message);
    	messageForm.setId(message.getId());
    	return messageForm;
    }

	/**
	 * "Reads" a message by returning it and setting the wasRead variabel of
	 * the message to true in the database
	 * @param messageId a Long defining the message we want to read
	 * @param user the user who wants to read the message
	 * @return the Message given by the id with wasRead = true if the user is the receiver
	 * of the message or null if the user isn't the reciever of the message
	 */
	public Message read(Long messageId, User user) {
		Message opened = messageDao.findOne(messageId);
		if(opened.getReceiver()!=user)
			return null;
		opened.setWasRead(true);
		messageDao.save(opened);
		return opened;
	}

}