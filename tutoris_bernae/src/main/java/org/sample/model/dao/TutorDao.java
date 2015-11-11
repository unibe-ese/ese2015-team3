package org.sample.model.dao;

import java.math.BigDecimal;

import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Dao for the Model Tutor.
 * @author G.Corsini
 *
 */
public interface TutorDao extends CrudRepository<Tutor,Long> {

	/**
	 * Searches the database for tutors who have registered a course similar to the parameter.
	 * 
	 * @param course
	 * @return List of Tutors that match the criteria.
	 */
	public Iterable<Tutor> findByCoursesLike(StudyCourse course);

	/**
	 * Searches the database for tutors who have registered a class similar to the parameter.
	 * 
	 * @param course
	 * @return List of Tutors that match the criteria.
	 */
	public Iterable<Tutor> findByClassesLike(Classes classCriteria);
        
	/**
	 * Searches the database for tutors who have registered a fee between the parameters.
	 * 
	 * @param min
	 * @param fee
	 * @return List of Tutors that match the criteria.
	 */
	public Iterable<Tutor> findByFeeBetween(BigDecimal min,BigDecimal fee);
        
}
