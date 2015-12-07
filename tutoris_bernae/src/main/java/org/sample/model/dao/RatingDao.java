package org.sample.model.dao;

import org.sample.model.Rating;
import org.springframework.data.repository.CrudRepository;


public interface RatingDao extends CrudRepository<Rating,Long> {
	
}
