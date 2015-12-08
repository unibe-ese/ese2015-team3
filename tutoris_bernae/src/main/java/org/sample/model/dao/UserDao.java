package org.sample.model.dao;

import org.sample.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Dao for the Model User.
 * @author G.Corsini
 *
 */
public interface UserDao extends CrudRepository<User,Long> {
	
	/**
	 * Searches the database for a user with the corresponding id.
	 * 
	 * @param id
	 * @return User that corresponds to the id.
	 */
	public User findOne(Long id);

	/**
	 * Searches the database for a user that contains the email.
	 * 
	 * @param email
	 * @return User that contains the email.
	 */
	public User findByEmailLike(String email);

	/**
	 * Searches the database for a user that corresponds to the firstName.
	 * 
	 * @param firstName
	 * @return User that corresponds to the username.
	 */
	public User findByFirstNameLike(String firstName);

}
