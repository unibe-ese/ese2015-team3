package org.sample.model.dao;

import org.sample.model.StudyCourse;
import org.springframework.data.repository.CrudRepository;

/**
 * Dao for the Model StudyCourse.
 * @author G.Corsini
 *
 */
public interface StudyCourseDao extends CrudRepository<StudyCourse,Long> {

	
	/**
	 * Searches the database for an element which corresponds to the parameter.
	 * 
	 * @param string
	 * @return StudyCourse that contains the string.
	 */
	public StudyCourse findByNameLike(String string);

}
