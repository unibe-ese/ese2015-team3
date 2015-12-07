package org.sample.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sample.controller.PageController;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * A custom authenticationSucessHandler which adds the user under
 * the session attribute PageController.SESSIONATTRIBUTE_USER to the session, 
 * so we have access to all user data on every page after login
 * (Which we need for example in the header to show the users first name)
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private UserDao userDao;

	//Setter injection, reference defined in springSecurity.xml
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,final HttpServletResponse response, 
										final Authentication authentication) throws IOException, ServletException {
		
		super.onAuthenticationSuccess(request, response, authentication);

		HttpSession session = request.getSession(true);

		User user = userDao.findByEmailLike(authentication.getName());

		session.setAttribute(PageController.SESSIONATTRIBUTE_USER, user);
	}


}
