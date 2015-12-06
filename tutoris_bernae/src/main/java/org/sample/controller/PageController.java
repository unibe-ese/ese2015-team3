package org.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;

/**
 * A generic page controller which offers basic functions for every page
 * to get the current user with getUserFromSecurityContext() or to 
 * automatically login one with  authenticateUserAndSetSession()
 */
@Controller
public abstract class PageController {

    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;
    
    @Autowired
    protected UserDao userDao;
    
    public static final String SESSIONATTRIBUTE_USER="loggedInUser";
    
    /**
     * Login the for the 
     * @param user the user you want to login
     * @param request
     */
    protected void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String username = user.getEmail();
        String password = user.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        HttpSession session = request.getSession();
        session.setAttribute(SESSIONATTRIBUTE_USER, user);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);    
    }
    
    /**
     * @return the user currently logged in or null if no one is logged in
     */
    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userDao.findByEmailLike(name);
        return user;
    }
}
