package org.sample.model.dao;

import org.sample.model.Tutor;
import org.sample.model.TutorShip;
import org.sample.model.User;
import org.springframework.data.repository.CrudRepository;

public interface TutorShipDao extends CrudRepository<TutorShip,Long> {

	TutorShip findByTutorAndStudent(Tutor tutor, User student);



}
