package org.sample.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.service.MailService;
import org.sample.controller.service.MessageService;
import org.sample.controller.service.TutorShipService;
import org.sample.model.Message;
import org.sample.model.MessageSubject;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.MessageSubjectDao;
import org.sample.model.dao.TutorShipDao;
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
public class TutorShipServiceTest {	
	
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
		public MessageService messageServiceMock() {
			MessageService messageService = mock(MessageService.class);
			return messageService;
		}
		
		@Bean
		public TutorShipDao tutorShipDaoMock() {
			TutorShipDao tutorShipDao = mock(TutorShipDao.class);
			return tutorShipDao;
		}
		
		@Bean
		public MailService mailServiceMock() {
			MailService mailService = mock(MailService.class);
			return mailService;
		}
		
		
		@Bean
		public TutorShipService tutorShipService() {
			TutorShipService tutorShipService = new TutorShipService();
			return tutorShipService;
		}

	}
	@Qualifier("userDaoMock")
	@Autowired
	private UserDao userDao;
	@Qualifier("tutorShipDaoMock")
	@Autowired
	private TutorShipDao tutorShipDao;
	@Qualifier("mailServiceMock")
	@Autowired
	private MailService mailService;
	@Qualifier("messageServiceMock")
	@Autowired
    private MessageService messageService;
	@Autowired
	private TutorShipService tutorShipService;


	private User sender;
	private User receiver;
	private Tutor senderTutor;
	private Message message1;
	private Message message2;
	private Message message3;
	private List<Message> unorderedMessageList = new LinkedList<Message>();
	private List<Message> orderedMessageList = new LinkedList<Message>();

	
	@Before
	public void setUpExampleDatas(){
		receiver = new User();
		sender = new User();
		sender.setEmail("mail@mail.mail");
		sender.setFirstName("FirstName");
		sender.setLastName("LastName");
		senderTutor = new Tutor();
		senderTutor.setId(1L);
		sender.setTutor(senderTutor);
	}
    
    @Test
    public void AddOfferedTutorShipWithActionOfferTutorShip() {
    	Message message = new Message();
    	MessageSubject offerSubject = new MessageSubject();
    	offerSubject.setAction(TutorShipService.ACTION_OFFER_TUTORSHIP);
    	message.setMessageSubject(offerSubject);
    	message.setSender(sender);
    	message.setReceiver(receiver);
    	//when(tutorShipDao.findByTutorAndStudent(any(Tutor.class), any(User.class))).then(null);
    	when(tutorShipDao.findByTutorAndStudent(any(Tutor.class), any(User.class)))
        .thenAnswer(new Answer<TutorShip>() {
            public TutorShip answer(InvocationOnMock invocation) throws Throwable {	
                return null;
            }
        });
    
      	when(tutorShipDao.save(any(TutorShip.class))).thenAnswer(new Answer<TutorShip>() {
            public TutorShip answer(InvocationOnMock invocation) throws Throwable {	
      		TutorShip tutorShip = (TutorShip) invocation.getArguments()[0];
      		assertEquals(senderTutor, tutorShip.getTutor());
      		assertEquals(receiver, tutorShip.getStudent());
      		assertEquals(false, tutorShip.getConfirmed());
      		return tutorShip;
            }
        });
    	tutorShipService.addOfferedTutorShip(message);
    	//assert that it contains a link
    	assertTrue(message.getMessageText().contains("<a href=\"/tutoris_baernae/confirmTutorShip?tutorUserId="));
    }
    
    @Test
    public void AddOfferedTutorMessageWithoutAction() {
    	Message message = new Message();
    	MessageSubject offerSubject = new MessageSubject();
    	message.setMessageSubject(offerSubject);
    	message.setSender(sender);
    	message.setReceiver(receiver);
    	message.setMessageText("");
    	
    	tutorShipService.addOfferedTutorShip(message);
    	
    	verify(tutorShipDao, never()).save(any(TutorShip.class));
    	verify(tutorShipDao, never()).findByTutorAndStudent(any(Tutor.class), any(User.class));
    	//assert that it contains a link
    	assertFalse(message.getMessageText().contains("<a href=\"/tutoris_baernae/confirmTutorShip?tutorUserId="));
    }
    
    @Test
    public void confirmTutorShip() throws InvalidTutorShipException {
    	final TutorShip tutorShip = new TutorShip();
    	tutorShip.setStudent(receiver);
    	tutorShip.setTutor(senderTutor);
    	when(tutorShipDao.findByTutorAndStudent(any(Tutor.class), any(User.class)))
        .thenAnswer(new Answer<TutorShip>() {
            public TutorShip answer(InvocationOnMock invocation) throws Throwable {	
                return tutorShip;
            }
        });
    	
    	tutorShipService.confirmTutorShip(senderTutor, receiver);
    	
    	assertTrue(tutorShip.getConfirmed());
    }
    
    @Test
    public void verifyContactDetailsAreExchangedAfterConfirmTutorShip() throws InvalidTutorShipException {
    	final TutorShip tutorShip = new TutorShip();
    	tutorShip.setStudent(receiver);
    	tutorShip.setTutor(senderTutor);
    	when(tutorShipDao.findByTutorAndStudent(any(Tutor.class), any(User.class)))
        .thenAnswer(new Answer<TutorShip>() {
            public TutorShip answer(InvocationOnMock invocation) throws Throwable {	
                return tutorShip;
            }
        });
    	
    	tutorShipService.confirmTutorShip(senderTutor, receiver);
    	verify(messageService).exchangeContactDetails(any(User.class), any(User.class));
    }
    
    @Test(expected = InvalidTutorShipException.class)
    public void confirmUnexistingTutorShip() throws InvalidTutorShipException {
    	when(tutorShipDao.findByTutorAndStudent(any(Tutor.class), any(User.class)))
        .thenAnswer(new Answer<TutorShip>() {
            public TutorShip answer(InvocationOnMock invocation) throws Throwable {	
                return null;
            }
        });
    	
    	tutorShipService.confirmTutorShip(senderTutor, receiver);   	
    }
    
    @Test(expected = InvalidTutorShipException.class)
    public void confirmAlreadyConfirmedTutorShip() throws InvalidTutorShipException {
      	final TutorShip tutorShip = new TutorShip();
    	tutorShip.setStudent(receiver);
    	tutorShip.setTutor(senderTutor);
    	tutorShip.setConfirmed(true);
    	when(tutorShipDao.findByTutorAndStudent(any(Tutor.class), any(User.class)))
        .thenAnswer(new Answer<TutorShip>() {
            public TutorShip answer(InvocationOnMock invocation) throws Throwable {	
                return tutorShip;
            }
        });
    	
    	tutorShipService.confirmTutorShip(senderTutor, receiver);   	
    }
   
    
    @After
    public void reset_mocks() {
    	reset(tutorShipDao);
    	reset(messageService);
    }

}