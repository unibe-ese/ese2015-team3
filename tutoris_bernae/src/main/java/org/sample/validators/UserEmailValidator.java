package org.sample.validators;

import org.sample.controller.pojos.FormWithUserDetails;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Checks if the email-address of a user given by a form is available
 * and rejects the form otherwise
 * An email is available if either the user already uses this email-address 
 * or if there in no other user in the database which uses this email-address
 */
@Component
public class UserEmailValidator implements Validator
{
	private UserDao userDao;
	
	//Spring needs an empty constructor for whatever reason; Never use this!
	/**
	 * DO NOT USE!
	 * Just there because Spring needs an empty one for yet unknown reason
	 * Use UserEmailValidator(UserDao userDao) instead OR IT WIIL NOT WORK
	 */
	public UserEmailValidator() {
	}
	
	/**
	 * Creates a validator for user emails which needs to check
	 * if an email already exist in the database with the given userDao

	 * @param userDao
	 */
	public UserEmailValidator(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/** 
	 * Supports all classes implementing the FormWithUserDetailsInterface
	 * Sea also:
	 * @see org.sample.controller.pojos.FormWithUserDetails
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> clazz) {
		return FormWithUserDetails.class.isAssignableFrom(clazz);  
	}
	


	/** 
	 * Rejects a supported form if the email in it
	 * already exists in the database and is not used by the user
	 * given by the form
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		FormWithUserDetails formWithUserDetails = (FormWithUserDetails) target;
		if(!emailAvailable(formWithUserDetails))
			errors.rejectValue("email","email.AlreadyInUse","The email you want to use is already in use by another user");
	}
    
    private boolean emailAvailable(FormWithUserDetails formWithUserDetails){
    	User user = userDao.findOne(formWithUserDetails.getUserId());
    	//Return true if we just reuse the old email
    	if(user!=null && formWithUserDetails.getEmail().equals(user.getEmail())) return true;
    	//Also return true if no one uses this email address
    	if(userDao.findByEmailLike(formWithUserDetails.getEmail())==null) return true;
    	return false;
    }
}