package org.sample.test.controller;

import static org.mockito.Mockito.mock;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.controller.SearchController;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.TutorDao;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.SearchService;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;
import org.sample.model.Tutor;

public class SearchControllerIntegrationTest extends ControllerIntegrationTest{


    @Autowired
    private SearchController searchController;
    @Autowired
    private SearchService searchService;
    @Autowired
    private TutorDao tutorDao;
    @Autowired
    private StudyCourseDao studyCourseDao;

    private Tutor tutor1;
    private Tutor tutor2;
    private Tutor tutor3;
    private StudyCourse course1;
	private StudyCourse course2;
	private Classes classes1;
	private Classes classes2;
    @Before
    public void setUpExampleTutors() {
    	course1 = new StudyCourse();
    	course2 = new StudyCourse();
    	course1 = studyCourseDao.save(course1);
    	course2 = studyCourseDao.save(course2);
    	Set<StudyCourse> courses1 = new HashSet<StudyCourse>();
    	courses1.add(course1);
    	Set<StudyCourse> courses2 = new HashSet<StudyCourse>();
    	courses2.add(course2);
    	classes1 = new Classes();
    	classes2 = new Classes();
    	Set<Classes> classesSet1 = new HashSet<Classes>();
    	classesSet1.add(classes1);
    	Set<Classes> classesSet2 = new HashSet<Classes>();
    	classesSet2.add(classes2);
    	tutor1 = new Tutor();
		tutor1.setClasses(classesSet1);
		tutor1.setCourses(courses1);
		tutor1.setFee(new BigDecimal(20.00));
		tutor1 = tutorDao.save(tutor1);
    	tutor2 = new Tutor();
		tutor2.setClasses(classesSet1);
		tutor2.setCourses(courses2);
		tutor2.setFee(new BigDecimal(40.00));
		tutor2 = tutorDao.save(tutor2);
    	tutor3 = new Tutor();
		tutor3.setClasses(classesSet2);
		tutor3.setCourses(courses1);
		tutor3.setFee(new BigDecimal(50.00));
		tutor3 = tutorDao.save(tutor3);
    }

    @Test
    public void searchPage() throws Exception {
        mockMvc.perform(get("/findTutor")).andExpect(status().isOk())
                .andExpect(model().attribute("searchForm", is(SearchForm.class)))
                .andExpect(model().attributeExists("studyCourseList"))
                .andExpect(model().attributeExists("classesList"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void submitAnySearch() throws Exception {
        mockMvc.perform(post("/submitSearch").param("fee", "80.00"))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("tutors"))
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_RESULTS)));
    }
    
    @Test
    public void noSearchResults() throws Exception {
        mockMvc.perform(post("/submitSearch").param("fee", "1.00"))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_NORESULTS)));
    }
    
    @Test
    public void searchByFee() throws Exception {
    	Set<Tutor> expectedTutors = new HashSet<Tutor>();
    	//We expect tutor1 and 2 because their fee is between 0 and 40 (20 for tutor 1, 40 for tutor 2)
    	expectedTutors.add(tutor1);
    	expectedTutors.add(tutor2);
        mockMvc.perform(post("/submitSearch").param("fee", "40.00"))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(model().attribute("tutors", Matchers.is(expectedTutors)))
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_RESULTS)));
    }
    
    @Test
    public void searchByCourse() throws Exception {
    	Set<Tutor> expectedTutors = new HashSet<Tutor>();
    	expectedTutors.add(tutor3);
    	expectedTutors.add(tutor1);
        mockMvc.perform(post("/submitSearch").param("studyCourseId", course1.getId().toString()))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(model().attribute("tutors", Matchers.is(expectedTutors)))
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_RESULTS)));
    }
    
    @Test
    public void searchByClasses() throws Exception {
    	Set<Tutor> expectedTutors = new HashSet<Tutor>();
    	expectedTutors.add(tutor2);
    	expectedTutors.add(tutor1);
        mockMvc.perform(post("/submitSearch").param("classesId", classes1.getId().toString()))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(model().attribute("tutors", Matchers.is(expectedTutors)))
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_RESULTS)));
    }
    
    @Test
    public void searchByMultipleCriteriaOneMatch() throws Exception {
    	Set<Tutor> expectedTutors = new HashSet<Tutor>();
    	expectedTutors.add(tutor1);
        mockMvc.perform(post("/submitSearch").param("fee", "80.00").param("studyCourseId", course1.getId().toString()).param("classesId", classes1.getId().toString()))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(model().attribute("tutors", Matchers.is(expectedTutors)))
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_RESULTS)));
    }
        
    @Test
    public void searchByMultipleCriteriaTwoMatches() throws Exception {
    	Set<Tutor> expectedTutors = new HashSet<Tutor>();
    	expectedTutors.add(tutor1);
    	expectedTutors.add(tutor2);
        mockMvc.perform(post("/submitSearch").param("fee", "40.00").param("studyCourseId", "0").param("classesId", classes1.getId().toString()))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(model().attribute("tutors", Matchers.is(expectedTutors)))
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_RESULTS)));
    }
    
    @Test
    public void searchByMultipleCriteriaButNoMatch() throws Exception {
    	Set<Tutor> expectedTutors = new HashSet<Tutor>();
    	expectedTutors.add(tutor1);
        mockMvc.perform(post("/submitSearch").param("fee", "10.00").param("studyCourseId", course1.getId().toString()).param("classesId", classes1.getId().toString()))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl(completeUrl(SearchController.PAGE_NORESULTS)));
    }
    
    //TODO add test for searching by course, by class and with multiple criterias
}
