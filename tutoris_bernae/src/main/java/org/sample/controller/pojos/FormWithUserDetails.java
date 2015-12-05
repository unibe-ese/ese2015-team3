package org.sample.controller.pojos;

/**
 * A form taking care of a user by either giving enough informations to create a new one
 * or to change informations of an existing one
 */
public interface FormWithUserDetails {

	String getPassword();

	void setPassword(String password);
	
	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	String getEmail();

	void setEmail(String email);

	String getUsername();

	void setUsername(String userName);
	
	/**
	 * @return the Id of the user in the database treated by this form
	 * Should be 0L if the form handles a new user
	 */
	Long getUserId();

	/**
	 * Defines the user changed by this form, should be 0
	 * if the form is used to create a new user
	 * @param id of the user which this form
	 * cares about
	 */
	void setUserId(Long id);
}