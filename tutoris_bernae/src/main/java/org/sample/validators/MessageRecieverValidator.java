package org.sample.validators;

import org.sample.controller.pojos.MessageForm;
import org.sample.model.Message;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Validates reciever in messageForms by checking if they exists in the database or not.
 * Also supports message for convenience but does nothing with them
 * @author pf15ese
 */
@Component
public class MessageRecieverValidator implements Validator
{
	private UserDao userDao;
	
	//Spring needs an empty one for whatever reason; Never use this!
	
	/**
	 * DO NOT USE!
	 * Just there because Spring needs an empty one for yet unknown reason
	 * Use MessageRecieverValidator(UserDao userDao) instead
	 */
	public MessageRecieverValidator() {
	}
	
	public MessageRecieverValidator(UserDao userDao) {
		this.userDao = userDao;
	}

	public void validate(Object target, Errors errors) {
		if(target.getClass().equals(Message.class)) return;
		MessageForm messageForm = (MessageForm) target;
		if(!recieverExists(messageForm.getReciever()))
			errors.rejectValue("reciever","message.NotExistingReciever","The user you want to send a message to does not exist");
	}

	public boolean supports(Class<?> clazz) {
		return MessageForm.class.equals(clazz);
	}

	private boolean recieverExists(String reciever) {
		return !(userDao.findByUsername(reciever) == null);
	}
}