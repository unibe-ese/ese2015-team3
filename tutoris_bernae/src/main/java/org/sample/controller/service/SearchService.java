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
        List<Tutor> tutorsMatchingCourse = new ArrayList<Tutor>();
        List<Tutor> tutorsMatchingClass = new ArrayList<Tutor>();
        List<Tutor> tutorsMatchingFee = new ArrayList<Tutor>();
        if(courseCriteria!=null)
        	 tutorsMatchingCourse = (List<Tutor>) tutorDao.findByCoursesLike(courseCriteria);
        if(classCriteria!=null)
        	tutorsMatchingClass = (List<Tutor>) tutorDao.findByClassesLike(classCriteria);
        if(fee!=null)
        	 tutorsMatchingFee = (List<Tutor>) tutorDao.findByFeeLike(fee);
        List<List<Tutor>> searchResults = new ArrayList<List<Tutor>>();
        System.out.println("Size: "+tutorsMatchingCourse.size()+tutorsMatchingClass.size()+tutorsMatchingFee.size());
        if(!tutorsMatchingCourse.isEmpty()) searchResults.add(tutorsMatchingCourse);
        if(!tutorsMatchingClass.isEmpty())searchResults.add(tutorsMatchingClass);
        if(!tutorsMatchingFee.isEmpty())searchResults.add(tutorsMatchingFee);
        
        return findCommonElements(searchResults);
    }
    
    private <T> List<T> findCommonElements(Collection<? extends Collection<T>> collections){
        
        List<T> common = new ArrayList<T>();

        if (!collections.isEmpty()){
            Iterator<? extends Collection<T>> iterator = collections.iterator();
            
            common.addAll(iterator.next());
            System.out.println("size:"+common.size());
            while (iterator.hasNext()) {
                common.retainAll(iterator.next());
                System.out.println("size:"+common.size());
            }
        } 
        return common;
    }
    

    public Iterable<StudyCourse> getAllCourses() {   
        return studyCourseDao.findAll();
    }
    

    public Iterable<Classes> getAllClasses() {
        return classesDao.findAll();

    }
}
