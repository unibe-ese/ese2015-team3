package org.sample.model.dao;

import java.util.List;

import org.sample.model.Classes;
import org.sample.model.Tutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Dao for the Model Classes.
 * @author G.Corsini
 *
 */
public interface CompletedClassesDao extends CrudRepository<Classes,Long> {


}
