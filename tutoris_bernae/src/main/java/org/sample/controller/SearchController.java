package org.sample.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SearchService;
import org.sample.model.Classes;
import org.sample.model.ClassesEditor;
import org.sample.model.StudyCourse;
import org.sample.model.StudyCourseEditor;
import org.sample.model.Tutor;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
    public static final String SESSIONATTRIBUE_FOUNDBYSEARCHFORM = "foundBySearchForm";
    
    @Autowired
    private SearchService searchService;
    @Autowired
    private ClassesDao classesDao;
    @Autowired
    private StudyCourseDao studyCourseDao;
    
	@InitBinder("searchForm")
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Classes.class, new ClassesEditor(classesDao));
		binder.registerCustomEditor(StudyCourse.class, new StudyCourseEditor(studyCourseDao));
	}
    
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
     * @return ModelAndView model with View "searchResults" and attributes "classes", 
     * "grade" and "tutors"
     */
    @RequestMapping(value = "/submitSearch", method = RequestMethod.POST)
    public ModelAndView searchResults(@Valid SearchForm searchForm, BindingResult result,HttpSession session) {
        ModelAndView model;
        
        if(!result.hasErrors()) {
            Set<Tutor> tutors = searchService.findTutorsBySearchCriterias(searchForm);
            if (tutors.isEmpty()){
                model = findTutor();
                model.addObject("noTutor", "no tutors found");
            }
            else{
            model = findTutor();
            model.addObject("tutors",tutors);
            session.setAttribute(SESSIONATTRIBUE_FOUNDBYSEARCHFORM, searchForm);
            }
        }
        else model = new ModelAndView(PAGE_SEARCH);
        model.addObject("searchForm", searchForm);
        return model;
    }
}
