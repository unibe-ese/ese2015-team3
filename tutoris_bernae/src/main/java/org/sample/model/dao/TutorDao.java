package org.sample.model.dao;

import java.math.BigDecimal;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface TutorDao extends CrudRepository<Tutor,Long> {
        public Iterable<Tutor> findByClassesNameLike(String string);
        public Iterable<Tutor> findByFeeLike(BigDecimal string);
      	public Iterable<Tutor> findByCoursesNameLike(String string);

}
