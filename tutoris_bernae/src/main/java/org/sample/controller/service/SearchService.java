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
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchService {
    @Autowired
    TutorDao tutorDao;
    
    @Autowired
    StudyCourseDao studyCourseDao;
    
    @Autowired
    ClassesDao classesDao;
    
    @Transactional
    public List<Tutor> findTutorsBySearchCriterias(SearchForm searchForm){
        StudyCourse courseCriteria = studyCourseDao.findOne(searchForm.getStudyCourseId());
        Classes classCriteria = classesDao.findOne(searchForm.getClassesId());
        BigDecimal fee = searchForm.getFee();
        System.out.println(fee);
        System.out.println(classCriteria.getName());
        System.out.println(courseCriteria.getName());
        List<Tutor> tutorsMatchingCourse = new ArrayList<Tutor>();
        List<Tutor> tutorsMatchingClass = new ArrayList<Tutor>();
        List<Tutor> tutorsMatchingFee = new ArrayList<Tutor>();
        if(courseCriteria!=null)
        	 tutorsMatchingCourse = (List<Tutor>) tutorDao.findByCoursesLike(courseCriteria);
        if(classCriteria!=null)
        	tutorsMatchingClass = (List<Tutor>) tutorDao.findByClassesLike(classCriteria);
        if(fee!=null)
        	 tutorsMatchingFee = (List<Tutor>) tutorDao.findByFeeLike(fee);
        //List<List<Tutor>> searchResults = new ArrayList<List<Tutor>>();
        List<Tutor> searchResults = new ArrayList<Tutor>();
        System.out.println("Size: (Fee)"+tutorsMatchingFee.size());
        System.out.println("Size: (Class)"+tutorsMatchingClass.size());
        System.out.println("Size: (Course) "+tutorsMatchingCourse.size());
        //searchResults.add(tutorsMatchingCourse);
        //searchResults.add(tutorsMatchingClass);
        //searchResults.add(tutorsMatchingFee);
        searchResults.addAll(tutorsMatchingCourse);
        searchResults.addAll(tutorsMatchingClass);
        searchResults.addAll(tutorsMatchingFee);
        //System.out.println("Size: "+findCommonElements(searchResults).size());
        System.out.println("Size: "+searchResults.size());
        //return findCommonElements(searchResults);
        return searchResults;
    }
    
    private <T> List<T> deleteCommonElements(Collection<? extends Collection<T>> collections){
        
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
        /*Iterable<Tutor> tutors = tutorDao.findAll();
        Set<StudyCourse> allCourses = new HashSet<StudyCourse>();
        for (Tutor tutor : tutors) {
            Set<StudyCourse> courses = tutor.getCourses();
            allCourses.addAll(courses);
        }
        return allCourses;*/
        
        return studyCourseDao.findAll();
    }
    
    public Iterable<Classes> getAllClasses() {
        //TODO: get list of all classes for selected course
        return classesDao.findAll();
    }
}
