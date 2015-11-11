package org.sample.model.dao;

import org.sample.model.StudyCourse;
import org.springframework.data.repository.CrudRepository;

/**
 * Dao for the Model StudyCourse.
 * @author pf15ese
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

	/**
	 * Searches the database for an element related to the tutor, whose biography contains the parameter.
	 * 
	 * @param string
	 * @return StudyCourse for the tutor, whose biography contains the string.
	 */
	public StudyCourse findByTutorBioLike(String string);

}
