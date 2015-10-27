package org.sample.model.dao;

import org.sample.model.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface TutorDao extends CrudRepository<Tutor,Long> {
    public Tutor findOne(Long id);
    
    public Iterable<Tutor> findAll();
    
    public Iterable<Tutor> findByCourseLike(String course);
    
    public Iterable<Tutor> findByClassLike(String classCriteria);
}
