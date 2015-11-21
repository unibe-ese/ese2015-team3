package org.sample.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.service.MessageService;
import org.sample.model.CompletedClasses;
import org.sample.model.Message;
import org.sample.model.MessageSubject;
import org.sample.model.User;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.MessageSubjectDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/springDataTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MessageServiceTransactionTest {	
	@Autowired
    private MessageService messageService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private MessageSubjectDao messageSubjectDao;
	
	private User sender;
	private User receiver;
	private MessageSubject test;
	private Message message1;
	private Message message2;
	private Message message3;
	private List<Message> unorderedMessageList;
	private List<CompletedClasses> completedClassesNew;

	
	@Before
	public void setUpExampleDatas(){
		sender = new User();
		sender.setUsername("test");
		sender.setEmail("mail@mail.mail");
		sender = userDao.save(sender);
		receiver = new User();
		receiver.setUsername("tutortest");
		receiver.setEmail("tutormail@mail.mail");
		receiver = userDao.save(receiver);
		test = new MessageSubject();
		test = messageSubjectDao.save(test);
	}

    @Test
    public void messageFormCorrectDataSavedInDatabase() {
    	MessageForm messageForm = new MessageForm();
    	messageForm.setReceiver("tutortest");
    	messageForm.setMessageSubject(test);
    	messageForm.setMessageText(".....");
    	messageService.send(messageForm,sender);
    	Message message = messageDao.findOne(messageForm.getId());
        assertEquals(test, message.getMessageSubject());
        assertEquals(".....", message.getMessageText());
        Date now = new Date();
        assertTrue(now.compareTo(message.getSendDate())>=0); //now should be after or at the same moment as the text was sended
        assertEquals(sender,message.getSender());
        assertEquals(receiver,message.getReceiver());
        assertEquals(false,message.getWasRead());
    }
    
    @Test(expected=InvalidUserException.class) 
    public void receiverUnexisting() {
    	MessageForm messageForm = new MessageForm();
    	messageForm.setReceiver(null);
    	messageForm.setMessageSubject(test);
    	messageForm.setMessageText(".....");
    	messageService.send(messageForm,sender);
    }
    
    @Test
    public void readMessage()
    {
    	Message message = new Message();
    	message.setReceiver(receiver);
    	message = messageDao.save(message);
    	messageService.read(message.getId(),receiver);
    	assertEquals(true, messageDao.findOne(message.getId()).getWasRead());
    }
    
    @Test
    public void readForeignMessageReturnsNullAndDoesNotRead()
    {
    	Message message = new Message();
    	message.setReceiver(sender);
    	message = messageDao.save(message);
    	//Try reading the message sender got although we are reciever
    	//Therefore we should get a null value and not be able to read it
    	Message shouldBeNull = messageService.read(message.getId(),receiver);
    	assertTrue(shouldBeNull==null);
    	assertEquals(false, messageDao.findOne(message.getId()).getWasRead());
    }
    
    // makes more sense in a unit test, isn't changed at all through transaction
    /*@Test
    public void MessagesCorrectOrdered() {
    }*/

}