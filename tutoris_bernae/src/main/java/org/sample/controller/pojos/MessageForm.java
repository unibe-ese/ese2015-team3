package org.sample.controller.pojos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.sample.model.Message;

/**
 * Stores a message consisting of a subject, a text and the wished receiver
 */
public class MessageForm {

    @NotEmpty
    private String receiver;
    @NotEmpty
    private String messageSubject;
    @NotEmpty
    private String messageText;
    
    private Long id;
    
    
	public MessageForm(Message selectedMessage) {
		receiver = selectedMessage.getSender().getEmail();
		messageSubject = selectedMessage.getMessageSubject();
	}
	
	public MessageForm() {

	}

	public MessageForm(String receiver) {
		this.receiver = receiver;
	}

	public MessageForm(String receiver, String subject) {
		this.receiver = receiver;
		this.messageSubject = subject;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
