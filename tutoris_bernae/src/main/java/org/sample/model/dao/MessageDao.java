package org.sample.model.dao;

import org.sample.model.Message;
import org.sample.model.User;
import org.springframework.data.repository.CrudRepository;

public interface MessageDao extends CrudRepository<Message,Long> {

	Iterable<Message> findAllByReceiver(User user);
}
