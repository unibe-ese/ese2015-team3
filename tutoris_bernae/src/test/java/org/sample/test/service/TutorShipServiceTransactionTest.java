package org.sample.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.controller.exceptions.InvalidTutorShipException;
import org.sample.controller.service.TutorShipService;
import org.sample.model.Message;
import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.TutorShipDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ServiceTransactionTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

public class TutorShipServiceTransactionTest extends ServiceTransactionTest {	
	@Autowired
    private TutorShipService tutorShipService ;
	@Autowired
	private TutorShipDao tutorShipDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TutorDao tutorDao;
	
	private User sender;
	private User receiver;

	@Before
	public void setUpExampleDatas(){
		sender = new User();
		sender.setFirstName("tutortest");
		sender.setLastName("tutortest");
		sender.setEmail("tutormail@mail.mail");
		Tutor senderTutor = new Tutor();
		tutorDao.save(senderTutor);
		sender.setTutor(senderTutor);
		sender = userDao.save(sender);
		senderTutor.setStudent(sender);
		tutorDao.save(senderTutor);
		receiver = new User();
		receiver.setFirstName("test");
		receiver.setLastName("test");
		receiver.setEmail("mail@mail.mail");
		receiver = userDao.save(receiver);
	
	}

    @Test
    public void AddedTutorShipSavedInDatabase() {
    	Message message = new Message();
    	message.setMessageSubject("test");
    	message.setReceiver(receiver);
    	message.setSender(sender);
    	tutorShipService.addOfferedTutorShip(message);
    	TutorShip newTutorShip = tutorShipDao.findByTutorAndStudent(sender.getTutor(), receiver);
    	assertNotNull(newTutorShip);
    	assertFalse(newTutorShip.getConfirmed());
    }
    
    @Test
    public void FindsExistingTutorShipInDatabase() {
    	Message message = new Message();
    	message.setMessageSubject("test");
    	message.setReceiver(receiver);
    	message.setSender(sender);
    	//Two times to possibly provoke an error in the following code (if not working as it should)
    	tutorShipService.addOfferedTutorShip(message);
    	tutorShipService.addOfferedTutorShip(message);
    	//If its done wrong, the following will throw an error because it found multiple result
    	//when there should be one
    	TutorShip newTutorShip = tutorShipDao.findByTutorAndStudent(sender.getTutor(), receiver);
    	assertNotNull(newTutorShip);
    }
    
    @Test
    public void AddsCorrectConfirmLinkInMessage() {
    	Message message = new Message();
    	message.setMessageSubject("test");
    	message.setReceiver(receiver);
    	message.setSender(sender);
    	tutorShipService.addOfferedTutorShip(message);
    	TutorShip newTutorShip = tutorShipDao.findByTutorAndStudent(sender.getTutor(), receiver);
    	assertNotNull(newTutorShip);
    	assertTrue(newTutorShip.getTutor().getId().equals(sender.getTutor().getId()));
    	assertTrue(message.getMessageText().contains("<a href=\"/tutoris_baernae/confirmTutorShip?tutorUserId="
				+newTutorShip.getTutor().getId()+"\">"));
    }
       
    @Test
    public void ConfirmationSavedInDatabase() throws InvalidTutorShipException {
    	TutorShip tutorShip = new TutorShip();
    	tutorShip.setStudent(receiver);
    	tutorShip.setTutor(sender.getTutor());
    	tutorShipDao.save(tutorShip);
    	tutorShipService.confirmTutorShip(sender.getTutor(), receiver);
    	TutorShip newTutorShip = tutorShipDao.findByTutorAndStudent(sender.getTutor(), receiver);
    	assertNotNull(newTutorShip);
    	assertTrue(newTutorShip.getConfirmed());
    }
    
    @Test
    public void increasesConfirmedTutorShipsForTutorInDatabase() throws InvalidTutorShipException {
    	TutorShip tutorShip = new TutorShip();
    	tutorShip.setStudent(receiver);
    	tutorShip.setTutor(sender.getTutor());
    	tutorShipDao.save(tutorShip);
    	tutorShipService.confirmTutorShip(sender.getTutor(), receiver);
    	tutorShipDao.findByTutorAndStudent(sender.getTutor(), receiver);
    	assertEquals(sender.getTutor().getConfirmedTutorShips() , new Integer(1));
    }

}