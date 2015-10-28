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
 
    @Transactional
    public Iterable<Tutor> findTutorsBySearchCriterias(SearchForm searchForm){
        String courseCriteria = searchForm.getStudyCourse();
        String classCriteria = searchForm.getClasses();
        BigDecimal fee = searchForm.getFee();
        List<Tutor> tutorsMatchingCourse = null;
        List<Tutor> tutorsMatchingClass = null;
        List<Tutor> tutorsMatchingFee = null;

        if (courseCriteria != null) {
            tutorsMatchingCourse = (List<Tutor>) tutorDao.findByCoursesNameLike(courseCriteria);
        }

        if (classCriteria != null) {
            tutorsMatchingClass = (List<Tutor>) tutorDao.findByClassesNameLike(classCriteria);
        }

        if (fee != null) {
            tutorsMatchingFee = (List<Tutor>) tutorDao.findByFeeLike(fee);
        }
        List<List<Tutor>> searchResults = new ArrayList<List<Tutor>>();
        if (tutorsMatchingClass.isEmpty() && !tutorsMatchingCourse.isEmpty()) {
            searchResults.add(tutorsMatchingCourse);
        } else if (!tutorsMatchingClass.isEmpty()) {
            searchResults.add(tutorsMatchingClass);
        }
        if (tutorsMatchingFee != null) {
            searchResults.add(tutorsMatchingFee);
        }

        if (searchResults.isEmpty()) {
            return null;
        }
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
    
    public Iterable<String> getAllCoursesNames() {
        Iterable<Tutor> tutors = tutorDao.findAll();
        Set<String> allNames = new HashSet<String>();
        for (Tutor tutor : tutors) {
            Set<StudyCourse> courses = tutor.getCourses();
            for (StudyCourse course : courses) {
                allNames.add(course.getName());
            }
        }
        
        return allNames;
        
//        return studyCourseDao.findAll();
    }
    
    public Iterable<String> getAllClasses() {
        Iterable<Tutor> tutors = tutorDao.findAll();
        Set<String> allNames = new HashSet<String>();
        for (Tutor tutor : tutors) {
            Set<Classes> classes = tutor.getClasses();
            for (Classes classe : classes) {
                allNames.add(classe.getName());
            }
        }
        
        return allNames;
    }
}
