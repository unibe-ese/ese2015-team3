package org.sample.test.service;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.service.EditFormService;
import org.sample.controller.service.MessageService;
import org.sample.model.Classes;
import org.sample.model.CompletedClasses;
import org.sample.model.Message;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


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

	
	
	private User sender;
	private User reciever;
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
		reciever = new User();
		reciever.setUsername("tutortest");
		reciever.setEmail("tutormail@mail.mail");
		reciever = userDao.save(reciever);
	}

    @Test
    public void MessageFormCorrectDataSavedInDatabase() {
    	MessageForm messageForm = new MessageForm();
    	messageForm.setReciever("tutortest");
    	messageForm.setMessageSubject("meeting");
    	messageForm.setMessageText(".....");
    	messageService.send(messageForm,sender);
    	Message message = messageDao.findOne(messageForm.getId());
        assertEquals("meeting", message.getMessageSubject());
        assertEquals(".....", message.getMessageText());
        Date now = new Date();
        assertTrue(now.compareTo(message.getSendDate())>=0); //now should be after or at the same moment as the text was sended
        assertEquals(sender,message.getSender());
        assertEquals(reciever,message.getReciever());
        assertEquals(false,message.getWasRead());
    }

}