package org.sample.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.service.MailService;
import org.sample.controller.service.MessageService;
import org.sample.model.Message;
import org.sample.model.MessageSubject;
import org.sample.model.User;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.MessageSubjectDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class MessageServiceTest {	
	
	@Configuration
    static class ContextConfiguration {

		@Bean
		public UserDao userDaoMock() {
			UserDao userDao = mock(UserDao.class);
			return userDao;
		}
		@Bean
		public MessageDao messageDaoMock() {
			MessageDao messageDao = mock(MessageDao.class);
			return messageDao;
		}
		
		@Bean
		public MessageSubjectDao messageSubjectDaoMock() {
			MessageSubjectDao messageSubjectDao = mock(MessageSubjectDao.class);
			return messageSubjectDao;
		}

		@Bean
		public MessageService messageService() {
			MessageService messageService = new MessageService();
			return messageService;
		}
		
		@Bean
		public MailService mailServiceMock() {
			MailService mailService = mock(MailService.class);
			return mailService;
		}

	}
	@Qualifier("userDaoMock")
	@Autowired
	private UserDao userDao;
	@Qualifier("messageDaoMock")
	@Autowired
	private MessageDao messageDao;
	@Qualifier("messageSubjectDaoMock")
	@Autowired
	private MessageSubjectDao messageSubjectDao;
	@Qualifier("mailServiceMock")
	@Autowired
	private MailService mailServiceMock;
	@Autowired
    private MessageService messageService;

	private User sender;
	private User receiver;
	private Message message1;
	private Message message2;
	private Message message3;
	private List<Message> unorderedMessageList = new LinkedList<Message>();
	private List<Message> orderedMessageList = new LinkedList<Message>();

	
	@Before
	public void setUpExampleDatas(){
		receiver = new User();
		sender = new User();
	}
	
    @Test
    public void messageFormCorrectDataSaved() {
    	MessageForm messageForm = new MessageForm();
    	messageForm.setReceiver("tutortest");
    	final MessageSubject test = new MessageSubject();
    	messageForm.setMessageSubject(test);
    	messageForm.setMessageText(".....");
    	
    	when(userDao.findByUsername(any(String.class)))
           .thenAnswer(new Answer<User>() {
               public User answer(InvocationOnMock invocation) throws Throwable {	
                   return receiver;
               }
           });
    	
        when(messageDao.save(any(Message.class)))
        .thenAnswer(new Answer<Message>() {
            public Message answer(InvocationOnMock invocation) throws Throwable {
            	Message message = (Message) invocation.getArguments()[0];
                assertEquals(test, message.getMessageSubject());
                assertEquals(".....", message.getMessageText());
                Date now = new Date();
                assertTrue(now.compareTo(message.getSendDate())>=0); //now should be after or at the same moment as the text was sended
                assertEquals(sender,message.getSender());
                assertEquals(receiver,message.getReceiver());
                assertEquals(false,message.getWasRead());
                return message;
            }
        });
        
    	messageService.sendMessageFromForm(messageForm,sender);
    }
    
    @Test(expected=InvalidUserException.class)
    public void receiverUnexisting() {
    	MessageForm messageForm = new MessageForm();
    	messageForm.setReceiver(null);
    	MessageSubject test = new MessageSubject();
    	messageForm.setMessageSubject(test);
    	messageForm.setMessageText(".....");
    	
    	when(userDao.findByUsername(any(String.class)))
           .thenAnswer(new Answer<User>() {
               public User answer(InvocationOnMock invocation) throws Throwable {	
                   return null;
               }
           });
       
    	messageService.sendMessageFromForm(messageForm,sender);
    }
    
    @Test
    public void MessagesRead() {
    	Date now = new Date();
    	message1 = new Message();
    	message1.setSendDate(now);
    	message1.setReceiver(receiver);
    	
        when(messageDao.findOne(any(Long.class)))
        .thenAnswer(new Answer<Message>() {
            public Message answer(InvocationOnMock invocation) throws Throwable {
                return message1;
            }
        });   
    	
        when(messageDao.save(any(Message.class)))
        .thenAnswer(new Answer<Message>() {
            public Message answer(InvocationOnMock invocation) throws Throwable {
            	Message message = (Message) invocation.getArguments()[0];
                assertEquals(true, message.getWasRead());
                return message;
            }
        });      
        
        messageService.read(0L, receiver);

    }
    
    // makes more sense in a unit test, isn't changed at all through transaction
    @Test
    public void MessagesCorrectOrdered() {
    	Date now = new Date();
    	Date before = new Date(now.getTime()-1000);
    	Date beforeBefore = new Date(now.getTime()-10000);
    	message1 = new Message();
    	message1.setSendDate(now);
    	message1.setReceiver(receiver);
    	message2 = new Message();
    	message2.setSendDate(before);
    	message2.setReceiver(receiver);
    	message3 = new Message();
    	message3.setSendDate(beforeBefore);
    	message3.setReceiver(receiver);
    	unorderedMessageList.add(message2);
    	unorderedMessageList.add(message3);
    	unorderedMessageList.add(message1);
    	orderedMessageList.add(message1);
    	orderedMessageList.add(message2);
    	orderedMessageList.add(message3);
    	
        when(messageDao.findAllByReceiver(any(User.class)))
        .thenAnswer(new Answer<Iterable<Message>>() {
            public Iterable<Message> answer(InvocationOnMock invocation) throws Throwable {	
                return unorderedMessageList;
            }
        });
        
        List<Message> shouldBeOrderedMessageList = (List<Message>) messageService.getOrderedMessagesList(receiver);
        assertEquals(orderedMessageList, shouldBeOrderedMessageList);	

    }
    
    @After
    public void reset_mocks() {
        reset(userDao);
        reset(messageDao);
    }

}