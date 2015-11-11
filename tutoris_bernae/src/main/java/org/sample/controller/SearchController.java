package org.sample.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SearchService;
import org.sample.model.Tutor;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for search tutor feature.
 * 
 */
@Controller
public class SearchController {
    public static final String PAGE_SEARCH = "search";
    public static final String PAGE_RESULTS = "searchResults";
    public static final String PAGE_NORESULTS = "noSearchResults";
    
    @Autowired
    private SearchService searchService;
    @Autowired
    private TutorDao tutorDao;
    

    @Autowired
    private StudyCourseDao studyCourseDao;
    
    /**
     * Creates the search page with a search form.
     * Model has attributes "searchForm":       a new SearchForm
     *                      "studyCourseList":  a list of all studycourses
     *                      "classesList":      a list of all classes 
     *
     * @return ModelAndView with View "search" and attributes "searchForm",
     * "studyCourseList" and "classesList"
     */
    @RequestMapping(value = "/findTutor", method = RequestMethod.GET)
    public ModelAndView findTutor() {
        ModelAndView model = new ModelAndView(PAGE_SEARCH);
        model.addObject("searchForm", new SearchForm());

        model.addObject("studyCourseList", searchService.getAllCourses());
        model.addObject("classesList", searchService.getAllClasses());
        return model;
    }
    
    /**
     * Searches for tutors matching criteria entered in SearchForm and displays the results.
     * If SearchForm is invalid, the search page with error messages is shown.
     * Search results are displayed in a table with username,feem, class and grade.
     * If no tutors are found, display message with possibility to another search.
     * 
     * @param searchForm: a valid SearchForm
     * @param result
     * @return ModelAndView model with View "searchResults" and attributes "classe", 
     * "grade" and "tutors"
     */
    @RequestMapping(value = "/submitSearch", method = RequestMethod.POST)
    public ModelAndView searchResults(@Valid SearchForm searchForm, BindingResult result) {
        ModelAndView model;
        
        if(!result.hasErrors()) {
            Set<Tutor> tutors = searchService.findTutorsBySearchCriterias(searchForm);
            if (tutors.isEmpty()){
                model = new ModelAndView(PAGE_NORESULTS);
            }
            else{
            model = new ModelAndView(PAGE_RESULTS);
            model.addObject("classe", searchService.getClasseName(searchForm));
            model.addObject("grade", searchService.getClasseGrade(searchForm));
            model.addObject("tutors",tutors);}
            
        }
        else model = new ModelAndView(PAGE_SEARCH);
        return model;
    }
}
