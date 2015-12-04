package org.sample.controller.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
     * @param searchForm stores the search criteria, not null 
     * @return a set of tutors matching the criteria
     */
    @Transactional
    public Set<Tutor> findTutorsBySearchCriterias(SearchForm searchForm){
    	assert(searchForm!=null);
        StudyCourse courseCriteria = searchForm.getStudyCourse();
        Classes classCriteria = searchForm.getClasses();
        BigDecimal fee = searchForm.getFee();
        List<List<Tutor>> searchResults = new ArrayList<List<Tutor>>();
        //Add the list of results form all criterias that we searched for
        //Obviously if the criteria is null it wasn't filled out in the form and we don't ask for it
        if(courseCriteria!=null){
        	searchResults.add((List<Tutor>) tutorDao.findByCoursesLike(courseCriteria));
        }
        if(classCriteria!=null){
        	searchResults.add((List<Tutor>) tutorDao.findByCompletedClassesClassesLike(classCriteria));
        }
        if(fee!= null){
        	 searchResults.add((List<Tutor>) tutorDao.findByFeeBetween(new BigDecimal(0),fee));
        }
        return findCommonElements(searchResults);
    }
    

	/**
     * helper function to find common elements in multiple lists.
     * @return list containing common elements
     */
    private Set<Tutor> findCommonElements(List<List<Tutor>> collections){
        
        Set<Tutor> common = new HashSet<Tutor>();
        
        if (!collections.isEmpty()){
            Iterator<List<Tutor>> iterator = collections.iterator();
            common.addAll(iterator.next());
            while (iterator.hasNext()) {
            	List<Tutor> next = iterator.next();
            	common.retainAll(next); 
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
     * Get all search criteria from search form.
     * 
     * @param searchForm has all search criteria stored
     * @return List of all search criteria
     */
    public List<String> getSearchCriteria(SearchForm searchForm) {
        List<String> searchCriteria = new ArrayList<String>();
        if (searchForm.getClasses()!= null) 
        	searchCriteria.add(searchForm.getClasses().getName());
        if (searchForm.getFee()!= null) {
            String fee = searchForm.getFee().toString();
            fee += " CHF";
            searchCriteria.add(fee);
        }
        if (searchForm.getStudyCourse()!= null) {
                searchCriteria.add(searchForm.getStudyCourse().getName());
        }
        
        return searchCriteria;
    }
}
