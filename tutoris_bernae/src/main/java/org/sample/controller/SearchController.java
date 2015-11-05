package org.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SearchService;
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
    
    @Autowired
    private SearchService searchService;
    @Autowired
    private TutorDao tutorDao;
    

    @Autowired
    private StudyCourseDao studyCourseDao;
    
    /**
     * View search page.
     * Model with attributes searchForm, a list of all studycourses and all classes.
     * @return 
     */
    @RequestMapping(value="/findTutor", method=RequestMethod.GET)
    public ModelAndView findTutor(){
        ModelAndView model = new ModelAndView(PAGE_SEARCH);
        model.addObject("searchForm", new SearchForm());

        model.addObject("studyCourseList", searchService.getAllCourses());
        model.addObject("classesList", searchService.getAllClasses());
        return model;
    }
    
    /**
     * View search results.
     * Search results are displayed in a table with username, class, grade (and rating)
     * @param searchForm: stores entered search criterias
     * @param result
     * @return 
     */
    @RequestMapping(value="/submitSearch", method=RequestMethod.POST)
    public ModelAndView searchResults(@Valid SearchForm searchForm, BindingResult result) {
        ModelAndView model;
        
        if(!result.hasErrors()) {
            model = new ModelAndView(PAGE_RESULTS);
            model.addObject("classe", searchService.getClasseName(searchForm));
            model.addObject("grade", searchService.getClasseGrade(searchForm));
            model.addObject("tutors",searchService.findTutorsBySearchCriterias(searchForm));
            
        }
        else model = new ModelAndView(PAGE_SEARCH);
        return model;
    }
}
