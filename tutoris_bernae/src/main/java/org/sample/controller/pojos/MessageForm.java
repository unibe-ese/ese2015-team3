package org.sample.controller.pojos;

import org.hibernate.validator.constraints.NotEmpty;
import org.sample.model.Message;

/**
 * Stores a message consisting of a subject, a text and the wished reciever
 */
public class MessageForm {

    @NotEmpty
    private String reciever;
    @NotEmpty
    private String messageSubject;
    @NotEmpty
    private String messageText;
    
    private Long id;
    
    
	public MessageForm(Message selectedMessage) {
		reciever = selectedMessage.getSender().getUsername();
		messageSubject = "AW: "+selectedMessage.getMessageSubject();
	}
	
	public MessageForm() {

	}

	public MessageForm(String reciever) {
		this.reciever = reciever;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
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
