package org.sample.controller.pojos;

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
    
    
	/**
	 * Creates a MessageForm for answering the selected message, so the reciever is 
	 * the sender of the selectedMessage and the subject is the "AW:" + the subject of the
	 * selected message (the AW: isn't added if the subject already contains that)
	 * @param selectedMessage the message you want to respond to with this
	 * MessageForm
	 */
	public MessageForm(Message selectedMessage) {
		String oldSubject = selectedMessage.getMessageSubject();
		if(oldSubject.contains("AW: "))
			messageSubject = oldSubject;
		else
			messageSubject = "AW: "+selectedMessage.getMessageSubject();
		receiver = selectedMessage.getSender().getEmail();
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
