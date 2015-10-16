package org.sample.model.dao;

import org.sample.model.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface TutorDao extends CrudRepository<Tutor,Long> {
}
