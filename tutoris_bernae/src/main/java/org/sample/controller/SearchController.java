package org.sample.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;

import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.controller.service.SearchService;
import org.sample.controller.service.TutorFormService;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    

    @RequestMapping(value="/findTutor", method=RequestMethod.GET)
    public ModelAndView findTutor(HttpSession session,HttpServletRequest request){
        ModelAndView model = new ModelAndView(PAGE_SEARCH);
        model.addObject("searchForm", new SearchForm());

        model.addObject("studyCourseList", searchService.getAllCourses());
        model.addObject("classesList", searchService.getAllClasses());
        return model;
    }
//    
//    @RequestMapping(value="/findTutor", method=RequestMethod.POST, params={"submitAction=studyCourseSelected"})
//    public ModelAndView populateClasses(HttpServletRequest request, @ModelAttribute(value = "searchForm") SearchForm searchForm){
//        ModelAndView model = new ModelAndView(PAGE_SEARCH);
//        model.addObject("searchForm", searchForm);
//        model.addObject("classes", searchService.getAllClasses(searchForm.getStudyCourse()));
//        return model;
//    }
    
    @RequestMapping(value="/submitSearch", method=RequestMethod.POST)
    public ModelAndView searchResults(HttpSession session,HttpServletRequest request, @Valid SearchForm searchForm, BindingResult result) {
        ModelAndView model;
        
        if(!result.hasErrors()){
            model = new ModelAndView(PAGE_RESULTS);
            
            model.addObject("tutors",searchService.findTutorsBySearchCriterias(searchForm));
            
        }
        else model = new ModelAndView(PAGE_SEARCH);
        return model;
    }
}
