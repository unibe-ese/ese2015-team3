package org.sample.controller.service;

import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.model.User;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.TeamDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sample.controller.pojos.SearchForm;
import org.sample.model.Tutor;
import org.sample.model.dao.TutorDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class SearchServiceTest {

	@Configuration
    static class ContextConfiguration {

	     @Bean
	     public TutorDao tutorDaoMock() {
	    	 TutorDao tutorDao = mock(TutorDao.class);
	    	 return tutorDao;
	     }
        @Bean
        public SearchService searchService() {
            SearchService searchService = new SearchService();
            
            return searchService;
        }
    }
	
	@Autowired
    private SearchService searchService;
	@Qualifier("tutorDaoMock")
	@Autowired
	private TutorDao tutorDao;

    @Test
    public void CorrectTutorFound() {

        SearchForm searchForm = new SearchForm();
        
        searchForm.setCourseCriteria("TestCourse");
        searchForm.setClassCriteria("TestClass");

        when(tutorDao.save(any(Tutor.class)))
                .thenAnswer(new Answer<Tutor>() {
                    public Tutor answer(InvocationOnMock invocation) throws Throwable {
                        Tutor tutor = (Tutor) invocation.getArguments()[0];
                        assertEquals(tutor.getCourses(), "TestCourse");
                        assertEquals(tutor.getClasses(), "TestClass");

                        tutor.setId(1L);
                        return tutor;
                    }
                });

        assertNull(searchForm.getId());
        

        searchService.findTutorsBySearchCriterias(searchForm);

        assertNotNull(searchForm.getId());
        assertTrue(searchForm.getId() > 0);
    }
    
    @After
    public void reset_mocks() {
        reset(tutorDao);
    }

   


}