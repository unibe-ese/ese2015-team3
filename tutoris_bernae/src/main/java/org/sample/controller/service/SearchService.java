package org.sample.controller.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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

/**
 * Search Service provides the necessary methods to filter tutors.
 * Criteria for search are provided by SearchForm.
 */
@Service
public class SearchService {
    @Autowired
    TutorDao tutorDao;

    @Autowired
    StudyCourseDao studyCourseDao;
    
    @Autowired
    ClassesDao classesDao;
    
    /**
     * Find tutors matching provided criteria.
     * For multiple criteria only tutors matching all of them are returned.
     * 
     * @param searchForm stores the search criteria 
     * @return a list of tutors matching the criteria
     */
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
        if(fee!= null)
        	 tutorsMatchingFee = (List<Tutor>) tutorDao.findByFeeBetween(new BigDecimal(0),fee);

        List<List<Tutor>> searchResults = new ArrayList<List<Tutor>>();
        System.out.println("Size: "+tutorsMatchingCourse.size()+tutorsMatchingClass.size()+tutorsMatchingFee.size());
        if(!tutorsMatchingCourse.isEmpty()) searchResults.add(tutorsMatchingCourse);
        if(!tutorsMatchingClass.isEmpty())searchResults.add(tutorsMatchingClass);
        if(!tutorsMatchingFee.isEmpty())searchResults.add(tutorsMatchingFee);
        
        return findCommonElements(searchResults);
    }
    
    /**
     * helper function to find common elements in multiple lists.
     * 
     * @return list containing common elements
     */
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
    
    /**
     * Get all study courses available.
     * @return all courses in DB
     */
    public Iterable<StudyCourse> getAllCourses() {   
        return studyCourseDao.findAll();
    }
    
    /**
     * Get all classes available.
     * @return all classes in DB
     */
    public Iterable<Classes> getAllClasses() {
        return classesDao.findAll();
    }
    
    /**
     * Get name of class in the search form.
     * If class criteria was left empty return null.
     * 
     * @param searchForm has class criteria stored
     * @return name of the class if a criteria was entered, null otherwise
     */
    public String getClasseName(SearchForm searchForm) {
        if (searchForm.getClassesId() != null){
            Classes classe = classesDao.findOne(searchForm.getClassesId());
            if (classe != null) {
                return classe.getName();
            }
        }

        return null;
    }
    
    /**
     * Get grade of class.
     * If no class criteria was entered return null.
     * 
     * @param searchForm has class criteria stored
     * @return grade achieved or null if no criteria was provided
     */
    public Integer getClasseGrade(SearchForm searchForm) {
        if (searchForm.getClassesId() != null) {
            Classes classe = classesDao.findOne(searchForm.getClassesId());
            if (classe != null) {
                return classe.getGrade();
            }
        }
        return null;
    }
}
