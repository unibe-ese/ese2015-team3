package org.sample.test.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.sample.controller.RegisterController;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.Tutor;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.sample.test.utils.ControllerIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

public class TutorRegisterControllerIntegrationTest extends ControllerIntegrationTest{
	@Autowired
	private RegisterFormService registerFormService;
	@Autowired
	private UserDao userDao;

	@Test
	public void upgradePage() throws Exception
	{
		User newUser = new User();
		newUser.setPassword("123");
		newUser.setEmail("mail@mail.mail");
		newUser = userDao.save(newUser);
		MockHttpSession session = createSessionWithUser("mail@mail.mail", "123", "ROLE_USER");
		mockMvc.perform(get("/upgrade").session(session)).andExpect(status().isOk())
									.andExpect(model().attribute("tutorForm", is(TutorForm.class)))
									.andExpect(forwardedUrl(completeUrl("tutorregistration")))
									.andExpect(model().hasNoErrors());
	}
	
	//TODO find out how to correctly add class and course list as parameters
	@Test
	public void wrongFieldsOnsubmitandsaveTutorForm() throws Exception
	{
		mockMvc.perform(post("/submitastutor").param("bio", "")
									.param("fee", "")
									.param("userId", "0")
									.param("save","true"))
									.andExpect(status().isOk())
									.andExpect(model().attributeHasFieldErrors("tutorForm", "bio"))
									.andExpect(model().attributeHasFieldErrors("tutorForm", "fee"))
									.andExpect(model().attribute("tutorForm", is(TutorForm.class)))
									.andExpect(forwardedUrl(completeUrl("tutorregistration")));		
	}
	
	//TODO find out how to correctly add class and course list as parameters
	@Test
	public void submitAndSaveTutorForm() throws Exception
	{
		User user = new User();
		user.setPassword("123");
		user.setEmail("mail@mail.mail");
		user = userDao.save(user);
		mockMvc.perform(post("/submitastutor").param("bio", "bio")
									.param("fee", "22")
									.param("userId", user.getId().toString())
									.param("save","true"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_SUBMIT)));
		Tutor tutor = user.getTutor();
		assertEquals("bio", tutor.getBio());
		assertEquals(new BigDecimal(22), tutor.getFee());
		assertEquals(user, tutor.getStudent());
	}
	
	/*
	private static final class SessionHolder{
	    private SessionWrapper session;


	    public SessionWrapper getSession() {
	        return session;
	    }

	    public void setSession(SessionWrapper session) {
	        this.session = session;
	    }
	}
	
	private static class SessionWrapper extends MockHttpSession{
	    private final HttpSession httpSession;

	    public SessionWrapper(HttpSession httpSession){
	        this.httpSession = httpSession;
	    }

	    @Override
	    public Object getAttribute(String name) {
	        return this.httpSession.getAttribute(name);
	    }

	}
	private SessionHolder sessionHolder = new SessionHolder();
	@Test
	public void loggedInUserGotUpgraded() throws Exception
	{
		User user = new User();
		user.setEmail("mail@mail.mail");
		user.setPassword("1wkl15%ber");
		user = userDao.save(user);
		MockHttpSession session = createSessionWithUser("mail@mail.mail", "1wkl15%ber", "ROLE_USER");
		List<GrantedAuthority> tutorAuthorities1 = AuthorityUtils.createAuthorityList("ROLE_USER");
		TestSecurityContext actualSecurityContext1 = (TestSecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		assertEquals(tutorAuthorities1,actualSecurityContext1.getAuthentication().getAuthorities());
		mockMvc.perform(post("/submitastutor").session(session)
									.param("bio", "bio")
									.param("fee", "22")
									.param("userId", user.getId().toString())
									.param("save","true"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl(completeUrl(RegisterController.PAGE_SUBMIT)))
									.andDo(new ResultHandler() {
						                @Override
						                
						                public void handle(MvcResult result) throws Exception {
						                    sessionHolder.setSession(new SessionWrapper(result.getRequest().getSession()));
						                }
						            });
		MockHttpSession session2 = sessionHolder.getSession();
		TestSecurityContext actualSecurityContext = (TestSecurityContext) session2.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		assertNotNull(actualSecurityContext);
		List<GrantedAuthority> tutorAuthorities = AuthorityUtils.createAuthorityList("ROLE_TUTOR");
		assertEquals(tutorAuthorities,actualSecurityContext.getAuthentication().getAuthorities());
	}*/
	
}
