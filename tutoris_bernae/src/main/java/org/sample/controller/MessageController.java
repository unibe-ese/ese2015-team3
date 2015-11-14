package org.sample.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.MessageForm;
import org.sample.controller.service.MessageService;
import org.sample.model.Message;
import org.sample.model.User;
import org.sample.model.dao.MessageDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Shows the profile of the logged in user or tutor
 * @author pf15ese
 */
@Controller
public class MessageController {

@Autowired
private UserDao userDao;
@Autowired
private MessageDao messageDao;
@Autowired
private MessageService messageService;
	
	/**
	 * Creates a page with all user informations of the current logged in user. If the user is also a tutor
	 * all tutor informations are added to the page as well
	 * @return ModelAndView with ViewName "profile" and ModelAttribute "user", the logged in user
	 * and if the user was a tutor there exist the ModelAttribute "tutor" the logged in users tutor
	 * informations
	 */
	@RequestMapping(value = "/messageInbox", method = RequestMethod.GET)
	public ModelAndView showMessageInbox() {
		 ModelAndView model = new ModelAndView("messageInbox");
		 User user = getUserFromSecurityContext();
		 model.addObject("user", user);
		 model.addObject("messages", messageService.getOrderedMessagesList(user));
		 return model;
	}

	private User getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     String name = authentication.getName();
		 User user = userDao.findByUsername(name);
		return user;
	}
	
	@RequestMapping(value = "/messageInboxShow", method = RequestMethod.GET)
	public ModelAndView showSelectedMessage(@RequestParam(value = "messageId", required = true) Long messageId) {
		ModelAndView model;
		User user = getUserFromSecurityContext();
		model = createInboxPage(user);
		Message selectedMessage = messageService.read(messageId);
		if(selectedMessage != null) {
			model.addObject("selectedMessage", selectedMessage);
			return model;
		}
		else {
			return model;
		}
	}

	private ModelAndView createInboxPage(User user) {
		ModelAndView model;
		model = new ModelAndView("messageInbox");
		model.addObject("messages", messageService.getOrderedMessagesList(user));
		return model;
	}
	
	private ModelAndView createAnswerPage(User user, MessageForm messageForm) {
		ModelAndView model;
		model = new ModelAndView("messageAnswer");
		model.addObject("messages", messageService.getOrderedMessagesList(user));
		model.addObject("messageForm", messageForm);
		return model;
	}
	
	
	/**
	 * an
	 * @param messageId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/messageInboxAnswer", method = RequestMethod.GET)
	public ModelAndView answerSelectedMessage(@RequestParam(value = "messageId", required = true) Long messageId, HttpSession session) {
		ModelAndView model;
		User user = getUserFromSecurityContext();
		Message selectedMessage = messageService.read(messageId);
		if(selectedMessage != null) {
			session.setAttribute("answeredMessage", selectedMessage);
			model = createAnswerPage(user,new MessageForm(selectedMessage));
			return model;
		}
		else {
			model = createAnswerPage(user,new MessageForm());
			return model;
		}
	}
	
	@RequestMapping(value = "/messageNew", method = RequestMethod.GET)
	public ModelAndView writeNewMessage() {
		ModelAndView model;
		User user = getUserFromSecurityContext();
		model = createAnswerPage(user,new MessageForm());
		return model;
	}
	
	@RequestMapping(value = "/messageSubmit", method = RequestMethod.POST)
	public ModelAndView submitMessage(@Valid MessageForm messageForm, BindingResult result,HttpSession session) {
		User user = getUserFromSecurityContext();
    	if (!result.hasErrors()) {
            try {
            	if(session.getAttribute("answeredMessage")!=null)
            		session.removeAttribute("answeredMessage");
            	messageService.send(messageForm,user);
            	ModelAndView model = createInboxPage(user);
            	model.addObject("submitMessage", "message sent!");
            	return model;
            } catch (InvalidUserException e) {
            	ModelAndView model = createAnswerPage(user,messageForm);
            	model.addObject("submitMessage", e.getMessage());
            	return model;
            }
        } else {
        	return createAnswerPage(user,messageForm);
        }   	
    }
	
}