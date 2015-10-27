package org.sample.controller.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.sample.controller.pojos.SearchForm;
import org.sample.model.Tutor;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchService {
    @Autowired
    TutorDao tutorDao;
    
    @Transactional
    public List<Tutor> findTutorsBySearchCriterias(SearchForm searchForm){
        String courseCriteria = searchForm.getCourseCriteria();
        String classCriteria = searchForm.getClassCriteria();
        List<Tutor> tutorsMatchingCourse = (List<Tutor>) tutorDao.findByCourseLike(courseCriteria);
        List<Tutor> tutorsMatchingClass = (List<Tutor>) tutorDao.findByClassLike(classCriteria);
        List<List<Tutor>> searchResults = new ArrayList<List<Tutor>>();
        searchResults.add(tutorsMatchingCourse);
        searchResults.add(tutorsMatchingClass);
        return findCommonElements(searchResults);
    }
    
    private <T> List<T> findCommonElements(Collection<? extends Collection<T>> collections){
        
        List<T> common = new ArrayList<T>();
        if (!collections.isEmpty()){
            Iterator<? extends Collection<T>> iterator = collections.iterator();
            common.addAll(iterator.next());
            while (iterator.hasNext()) {
                common.retainAll(iterator.next());
            }
        } 
        return common;
    }
    
}
