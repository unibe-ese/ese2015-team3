package org.sample.controller.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.sample.model.Message;
import org.sample.model.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


/**
 * A simple email sending service. Helps notifying a user if he gets a new message
 * @author pf15ese
 *
 */
public class MailService {

    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }
    

    /**
     * Sends a notification via email to the receiver of the message. The notification
     * consist of a short description based on the subject of the message and a
     * link to the message
     * NOTE: creates a new thread to send the message, so when this method ends the mail is mostly not send
     * yet (this takes relatively long, several seconds)
     * @param message The Message you want to notify the receiver about, not null
     * and should have been saved with the MessageDao before
     */
    public void sendMessageNotificationMail(Message message) {
    	System.out.println("smn calleds");
    	assert(message!=null);
        String notificationText = new StringBuilder().append("From:"+message.getSender().getFirstName()+" concerning:"+message.getMessageSubject())
					   .append("<br>"+"<a href=\"http://localhost:8080/tutoris_baernae/messageInboxShow?messageId="+message.getId()+"\">Click here to read the message </a>")
					   .append("<br>"+"This message is auto generated. Do not answer!")
					   .toString();
        final MimeMessage mimeMessage = setupMimeMessage(message.getReceiver(),"You received a new message",notificationText);
        sendMail(mimeMessage);
    }


	private void sendMail(final MimeMessage mimeMessage) {
		new Thread() {
    		@Override
    		public void run() {
    			mailSender.send(mimeMessage);
    		}
    	}.start();
	}
    
    private MimeMessage setupMimeMessage(User receiver, String subject, String textInHtml)
    {
    	MimeMessage mimeMessage = mailSender.createMimeMessage();
    	try {
    		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
    		helper.setFrom("tutoris.baernae@openmailbox.org");
			helper.setTo(receiver.getEmail());
	    	helper.setSubject(subject);
	    	mimeMessage.setContent(textInHtml, "text/html");
		} catch (MessagingException e) {
			// Ugly workaround but we assume that this steps before mostly works
			// and if they fail it doesn't matter that much (no mail is sent)
			System.out.println(e);
		}
    	return mimeMessage;
    	
    }



}