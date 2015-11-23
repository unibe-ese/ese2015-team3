package org.sample.controller;

import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {
	
	public static User getUserFromSecurityContext(UserDao userDao) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     String name = authentication.getName();
		 User user = userDao.findByUsername(name);
		return user;
	}

}
