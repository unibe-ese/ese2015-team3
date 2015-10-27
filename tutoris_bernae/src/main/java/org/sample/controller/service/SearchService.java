package org.sample.controller.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.sample.controller.pojos.SearchForm;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchService {
    @Autowired
    TutorDao tutorDao;
    
//    @Autowired
//    StudyCourseDao studyCourseDao;
    
    @Transactional
    public Iterable<Tutor> findTutorsBySearchCriterias(SearchForm searchForm){
        StudyCourse courseCriteria = searchForm.getStudyCourse();
        Classes classCriteria = searchForm.getClasses();
        BigDecimal fee = searchForm.getFee();
        
        List<Tutor> tutorsMatchingCourse = (List<Tutor>) tutorDao.findByCoursesLike(courseCriteria);
        List<Tutor> tutorsMatchingClass = (List<Tutor>) tutorDao.findByClassesNameLike(classCriteria);
        List<Tutor> tutorsMatchingFee = (List<Tutor>) tutorDao.findByFeeLike(fee);
        List<List<Tutor>> searchResults = new ArrayList<List<Tutor>>();
        if(tutorsMatchingClass == null) searchResults.add(tutorsMatchingCourse);
        else searchResults.add(tutorsMatchingClass);
        searchResults.add(tutorsMatchingFee);
        
        if (tutorsMatchingCourse == null && tutorsMatchingClass == null && tutorsMatchingFee == null)
            return null;
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
    
    public Iterable<StudyCourse> getAllCourses() {
        Iterable<Tutor> tutors = tutorDao.findAll();
        Set<StudyCourse> allCourses = new HashSet<StudyCourse>();
        for (Tutor tutor : tutors) {
            Set<StudyCourse> courses = tutor.getCourses();
            allCourses.addAll(courses);
        }
        return allCourses;
        
//        return studyCourseDao.findAll();
    }
    
    public Iterable<Classes> getAllClasses() {
        //TODO: get list of all classes for selected course
        return null;
    }
}
