package org.sample.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.service.MessageService;
import org.sample.model.Message;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.sample.validators.MessageReceiverValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Allows the user to see the messages he received and writing new ones
 * @author pf15ese
 */
@Controller
public class MessageController {

@Autowired
private UserDao userDao;
@Autowired
private MessageService messageService;
	

@InitBinder("messageForm")
public void initBinder(WebDataBinder binder) {
	binder.addValidators(new MessageReceiverValidator(userDao));
}
	
	/**
	 * Creates a page with all messages that the logged in user received
	 * @return ModelAndView with ViewName "messageInbox" and modelattribute "user", the logged in user,
	 * and "messages" all his messages
	 */
	@RequestMapping(value = "/messageInbox", method = RequestMethod.GET)
	public ModelAndView showMessageInbox() {
		 ModelAndView model = new ModelAndView("messageInbox");
		 User user = UserHelper.getUserFromSecurityContext(userDao);
		 model.addObject("messages", messageService.getOrderedMessagesList(user));
		 return model;
	}

	/**
	 * Creates a page with one selected message given by the parameter 
	 * and all other messages that the logged in user received
	 * @param messageId Long the id of the message that should be under the "selectedMessage" attribute
	 * @return ModelAndView with ViewName "messageInbox" and modelattribute "user", the logged in user,
	 * "selectedMessage" the message given by the messageId and "messages" all his messages
	 */
	@RequestMapping(value = "/messageInboxShow", method = RequestMethod.GET)
	public ModelAndView showSelectedMessage(@RequestParam(value = "messageId", required = true) Long messageId) {
		ModelAndView model;
		User user = UserHelper.getUserFromSecurityContext(userDao);
		model = createInboxPage(user);
		Message selectedMessage = messageService.read(messageId,user);
		if(selectedMessage != null) {
			model.addObject("selectedMessage", selectedMessage);
			return model;
		}
		else {
			return model;
		}
	}
	
	/**
	 * Creates a page with a message form to answer one selected message given by the parameter 
	 * and all other messages that the logged in user received
	 * @param messageId Long the id of the message that we want to answer to and that 
	 * should be under the "selectedMessage" attribute
	 * @return ModelAndView with ViewName "messageAnswer" and modelattribute "messageForm" a prefilled message form
	 * suited to the message we want too answer to, "user", the logged in user,
	 * "selectedMessage" the message given by the messageId and "messages" all his messages
	 * Also stores the message we answer to in the session under "answeredMessage"
	 */
	@RequestMapping(value = "/messageInboxAnswer", method = RequestMethod.GET)
	public ModelAndView answerSelectedMessage(@RequestParam(value = "messageId", required = true) Long messageId, HttpSession session) {
		ModelAndView model;
		User user = UserHelper.getUserFromSecurityContext(userDao);
		Message selectedMessage = messageService.read(messageId,user);
		if(selectedMessage != null) {
			session.setAttribute("answeredMessage", selectedMessage);
			model = createAnswerPage(user,new MessageForm(selectedMessage),true,session);
			return model;
		}
		else {
			model = createAnswerPage(user,new MessageForm(),false,session);
			return model;
		}
	}
	
	/**
	 * Creates a page with a message form to answer one selected message given by the parameter 
	 * and all other messages that the logged in user received
	 * @param reciver a String of the username that we want to contact via a message
	 * @return ModelAndView with ViewName "messageAnswer" and modelattribute "messageForm" a prefilled message form
	 * suited to the receiver we want to write to, "user", the logged in user,
	 * and "messages" all his messages
	 */
	@RequestMapping(value = "/messageNewTo", method = RequestMethod.GET)
	public ModelAndView writeNewMessage(@RequestParam(value = "receiver", required = true) String receiver,
										HttpSession session) {
		ModelAndView model;
		User user = UserHelper.getUserFromSecurityContext(userDao);
		model = createAnswerPage(user,new MessageForm(receiver),false,session);
		return model;
	}
	
	/**
	 * Creates a page with a message form to write a message to anyone.
	 * Also has all  messages that the logged in user received
	 * @return ModelAndView with ViewName "messageAnswer" and modelattribute "messageForm" an empty message form
	 * "user", the logged in user, and "messages" all his messages
	 */
	@RequestMapping(value = "/messageNew", method = RequestMethod.GET)
	public ModelAndView writeNewMessage(HttpSession session) {
		ModelAndView model;
		User user = UserHelper.getUserFromSecurityContext(userDao);
		model = createAnswerPage(user,new MessageForm(),false,session);
		return model;
	}
	
	
	/**
	 * Saves (sends) a message and displays a success message back on the messageInbox page. Returns to the
	 * messageAnswer page if the form is not filled out correctly. Also deletes the sessionattribute "answeredMessaged"
	 * if the message could be sent. 
	 * @param messageForm a Valid MessageForm
	 * @param result
	 * @param session
	 * @return a new ModelAndView with ViewName "messageInbox", a modelattribute "submitMessage", a success message
	 * "user", the logged in user, and "messages" all his messages. Or if the form was not filled correctly
	 * a ModelAndView with ViewName "messageAnswer", and the modelattributes "submitMessage", a message with informations about 
	 * the fault, "messageForm" a messageForm and "user", the logged in user, and "messages"
	 */
	@RequestMapping(value = "/messageSubmit", method = RequestMethod.POST)
	public ModelAndView submitMessage(@Valid MessageForm messageForm, BindingResult result,HttpSession session) {
		User user = UserHelper.getUserFromSecurityContext(userDao);
    	if (!result.hasErrors()) {
            try {
            	if(session.getAttribute("answeredMessage")!=null)
            		session.removeAttribute("answeredMessage");
            	messageService.send(messageForm,user);
            	ModelAndView model = createInboxPage(user);
            	model.addObject("submitMessage", "message sent!");
            	return model;
            } catch (InvalidUserException e) {
            	ModelAndView model = createAnswerPage(user,messageForm,true,session);
            	model.addObject("submitMessage", e.getMessage());
            	return model;
            }
        } else {
        	return createAnswerPage(user,messageForm,true,session);
        }   	
    }
	
	private ModelAndView createInboxPage(User user) {
		ModelAndView model;
		model = new ModelAndView("messageInbox");
		model.addObject("messages", messageService.getOrderedMessagesList(user));
		return model;
	}
	
	private ModelAndView createAnswerPage(User user, MessageForm messageForm, boolean keepAnsweredMessage, HttpSession session) {
		ModelAndView model;
		if(!keepAnsweredMessage)
			session.removeAttribute("answeredMessage");
		model = new ModelAndView("messageAnswer");
		model.addObject("messages", messageService.getOrderedMessagesList(user));
		model.addObject("messageForm", messageForm);
		return model;
	}
	
}