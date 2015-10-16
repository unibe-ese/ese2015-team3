package org.sample.model.dao;

import org.sample.model.StudyCourse;
import org.springframework.data.repository.CrudRepository;

public interface StudyCourseDao extends CrudRepository<StudyCourse,Long> {
}
