package org.sample.validators;

import org.sample.controller.pojos.MessageForm;
import org.sample.model.Message;
import org.sample.model.dao.UserDao;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Validates receiver in messageForms by checking if they exists in the database or not.
 * Also supports message for convenience but does nothing with them
 */
@Component
public class MessageReceiverValidator implements Validator
{
	private UserDao userDao;
	
	//Spring needs an empty constructor for whatever reason; Never use this!
	/**
	 * DO NOT USE!
	 * Just there because Spring needs an empty one for yet unknown reason
	 * Use MessageReceiverValidator(UserDao userDao) instead OR IT WIIL NOT WORK
	 */
	public MessageReceiverValidator() {
	}
	
	public MessageReceiverValidator(UserDao userDao) {
		this.userDao = userDao;
	}

	public void validate(Object target, Errors errors) {
		if(target.getClass().equals(Message.class)) return;
		MessageForm messageForm = (MessageForm) target;
		if(!receiverExists(messageForm.getReceiver()))
			errors.rejectValue("receiver","message.NotExistingreceiver","The user you want to send a message to does not exist");
	}

	public boolean supports(Class<?> clazz) {
		return MessageForm.class.equals(clazz);
	}

	private boolean receiverExists(String receiver) {
		return !(userDao.findByEmailLike(receiver) == null);
	}
}