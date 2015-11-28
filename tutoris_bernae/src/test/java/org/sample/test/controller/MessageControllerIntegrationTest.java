package org.sample.test.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.sample.controller.pojos.MessageForm;
import org.sample.model.Message;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

public class MessageControllerIntegrationTest extends ControllerIntegrationTest{
	@Autowired
	private TutorDao tutorDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ClassesDao classesDao;

	
	private User sender;
	private User receiver;

	
	MockHttpSession session;
	private Message message1;
	private Message message2;
	private Message message3;

	private List<Message> unorderedMessageList;
	@Autowired
	private MessageDao messageDao;
	@Before
	public void setUp()
	{
    	Date now = new Date();
    	Date before = new Date(now.getTime()-1000);
    	Date beforeBefore = new Date(now.getTime()-10000);
		sender = new User();
		sender.setUsername("sender");
		sender.setPassword("1232w%Dres");
		sender = userDao.save(sender);
		Tutor senderTutor = new Tutor();
		senderTutor.setStudent(sender);
		tutorDao.save(senderTutor);
		sender.setTutor(senderTutor);
		sender.setTutor(true);
		sender = userDao.save(sender);
		receiver = new User();
		receiver.setUsername("receiver");
		receiver.setPassword("1232w%Dres");
		receiver = userDao.save(receiver);
    	message1 = new Message();
    	message1.setSendDate(now);
    	message1.setReceiver(receiver);
    	message1.setSender(sender);
    	message1.setMessageSubject("test");
    	message2 = new Message();
    	message2.setSendDate(before);
    	message2.setReceiver(receiver);
       	message2.setSender(sender);
    	message3 = new Message();
    	message3.setSendDate(beforeBefore);
    	message3.setReceiver(receiver);
       	message3.setSender(sender);
       	messageDao.save(message1);
       	messageDao.save(message2);
       	messageDao.save(message3);
       	unorderedMessageList = new LinkedList<Message>();
		unorderedMessageList.add(message1);
		unorderedMessageList.add(message2);
		unorderedMessageList.add(message3);
	}
	
	@Test
	public void messageInbox() throws Exception
	{
		List<Message> unorderedMessageList = new LinkedList<Message>();
		unorderedMessageList.add(message1);
		unorderedMessageList.add(message2);
		unorderedMessageList.add(message3);
		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(get("/messageInbox").session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(forwardedUrl(completeUrl("messageInbox")));
								
	}	
	
	@Test
	public void selectedMessageInbox() throws Exception
	{
		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(get("/messageInboxShow?messageId="+message1.getId()).session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(model().attribute("selectedMessage", Matchers.is(message1)))
										.andExpect(forwardedUrl(completeUrl("messageInbox")));
								
	}
	
	@Test
	public void cannotSelectForeignMessage() throws Exception
	{
		Message foreignMessage = new Message();
		foreignMessage.setReceiver(sender);
		messageDao.save(foreignMessage);
		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(get("/messageInboxShow?messageId="+foreignMessage.getId()).session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(model().attribute("selectedMessage", nullValue()))
										.andExpect(forwardedUrl(completeUrl("messageInbox")));
								
	}	
	
	@Test
	public void answerSelectedMessageInbox() throws Exception
	{
		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(get("/messageInboxAnswer?messageId="+message1.getId()).session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(model().attribute("messageForm", is(MessageForm.class)))
										.andExpect(forwardedUrl(completeUrl("messageAnswer")))
		.andExpect(model().attribute("messageForm", hasProperty("receiver", Matchers.is("sender"))))
		.andExpect(model().attribute("messageForm", hasProperty("messageSubject", Matchers.is("test"))));
								
	}	
	
	@Test
	public void messageSubmit() throws Exception
	{
		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(post("/messageSubmit").session(session).param("receiver", "sender")
										.param("messageSubject", "test")
										.param("messageText", "text"))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(model().attribute("submitMessage", Matchers.is("message sent!")))
										.andExpect(model().attribute("messageForm", is(MessageForm.class)))
										.andExpect(forwardedUrl(completeUrl("messageInbox")));
		assertNotNull((List<Message>)messageDao.findAllByReceiver(sender));
	}
	
	@Test
	public void tutorShipOfferMessageSubmit() throws Exception
	{
		session = createSessionWithUser("sender", "1232w%Dres", "ROLE_TUTOR");
		mockMvc.perform(post("/messageSubmit").session(session).param("receiver", "sender")
										.param("messageSubject", "test")
										.param("messageText", "text")
										.param("offerTutorShip", "true"))
										.andExpect(status().isOk())
										.andExpect(model().attribute("submitMessage", Matchers.is("message sent!")))
										.andExpect(model().attribute("messageForm", is(MessageForm.class)))
										.andExpect(forwardedUrl(completeUrl("messageInbox")));
		assertNotNull((List<Message>)messageDao.findAllByReceiver(sender));
	}
	
	@Test
	public void nonTutorsCannotOfferTutorShip() throws Exception
	{
		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(post("/messageSubmit").session(session).param("receiver", "sender")
										.param("messageSubject", "test")
										.param("messageText", "text")
										.param("offerTutorShip", "true"))
										.andExpect(status().isOk())
										.andExpect(model().attributeExists("submitMessage"))
										.andExpect(forwardedUrl(completeUrl("messageAnswer")));
	}
	
	@Test
	public void messageSubmitWithFieldErrors() throws Exception
	{

		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(post("/messageSubmit").session(session).param("receiver", "")
										.param("messageSubject", "")
										.param("messageText", ""))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(model().attributeHasFieldErrors("messageForm", "receiver"))
										.andExpect(model().attributeHasFieldErrors("messageForm", "messageSubject"))
										.andExpect(model().attributeHasFieldErrors("messageForm", "messageText"))
										.andExpect(forwardedUrl(completeUrl("messageAnswer")));
								
	}
	
	@Test
	public void messageSubmitToUnexistingreceiver() throws Exception
	{

		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(post("/messageSubmit").session(session).param("receiver", "ShouldNotExist")
										.param("messageSubject", "test")
										.param("messageText", "abaac"))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(model().attributeHasFieldErrors("messageForm", "receiver"))
										.andExpect(forwardedUrl(completeUrl("messageAnswer")));					
	}
	
	@Test
	public void newMessageTo() throws Exception
	{
		List<Message> unorderedMessageList = new LinkedList<Message>();
		unorderedMessageList.add(message1);
		unorderedMessageList.add(message2);
		unorderedMessageList.add(message3);
		session = createSessionWithUser("receiver", "1232w%Dres", "ROLE_USER");
		mockMvc.perform(get("/messageNewTo?receiver="+sender.getUsername()).session(session))
										.andExpect(status().isOk())
										.andExpect(model().attribute("messages", Matchers.is(unorderedMessageList)))
										.andExpect(model().attribute("messageForm", is(MessageForm.class)))
										.andExpect(model().attribute("messageForm", hasProperty("receiver", Matchers.is("sender"))))
										.andExpect(forwardedUrl(completeUrl("messageAnswer")));
								
	}
	
	@Test
	public void needsLogin() throws Exception
	{
		mockMvc.perform(get("/message")).andExpect(status().isMovedTemporarily()); //moved Temporarily because your moved to the login page
	}
}
