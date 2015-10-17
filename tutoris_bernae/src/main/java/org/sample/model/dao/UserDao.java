package org.sample.model.dao;

import org.sample.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User,Long> {
	
	public User findOne(Long id);

	public User findByUsernameLike(String username);

	public User findByEmailLike(String email);
}
