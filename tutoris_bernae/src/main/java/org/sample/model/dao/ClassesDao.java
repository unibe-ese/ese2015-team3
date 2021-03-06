package org.sample.model.dao;

import org.sample.model.Classes;
import org.springframework.data.repository.CrudRepository;

/**
 * Dao for the Model Classes.
 * @author G.Corsini
 *
 */
public interface ClassesDao extends CrudRepository<Classes,Long> {
}
