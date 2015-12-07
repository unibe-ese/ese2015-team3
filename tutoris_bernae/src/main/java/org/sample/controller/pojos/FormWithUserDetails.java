package org.sample.controller.pojos;

/**
 * A form taking care of a user by either giving enough informations to create a new one
 * or to change informations of an existing one
 */
public interface FormWithUserDetails {

	public String getPassword();

	public void setPassword(String password);
	
	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);

	public String getEmail();

	public void setEmail(String email);
	
	/**
	 * @return the Id of the user in the database treated by this form
	 * Should be 0L if the form handles a new user
	 */
	public Long getUserId();

	/**
	 * Defines the user changed by this form, should be 0
	 * if the form is used to create a new user
	 * @param id of the user which this form
	 * cares about, 0L if the user doesn't exist in
	 * the database yet
	 */
	public void setUserId(Long id);
}