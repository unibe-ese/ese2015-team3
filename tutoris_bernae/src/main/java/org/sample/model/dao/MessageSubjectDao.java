package org.sample.model.dao;

import java.util.List;

import org.sample.model.Classes;
import org.sample.model.Message;
import org.sample.model.MessageSubject;
import org.sample.model.User;
import org.springframework.data.repository.CrudRepository;

public interface MessageSubjectDao extends CrudRepository<MessageSubject,Long> {


	Iterable<MessageSubject> findAllByRole(String string);

}
