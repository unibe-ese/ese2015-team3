package org.sample.model.dao;

import org.sample.model.Classes;
import org.springframework.data.repository.CrudRepository;

public interface ClassesDao extends CrudRepository<Classes,Long> {
}
