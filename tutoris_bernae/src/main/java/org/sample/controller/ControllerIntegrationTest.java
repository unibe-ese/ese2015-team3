package org.sample.controller;


import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/springMVC.xml","file:src/main/webapp/WEB-INF/config/springData.xml","file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ControllerIntegrationTest {
	@Autowired 
	private FilterChainProxy springSecurityFilterChain;
	@Autowired
	private WebApplicationContext context;
	
	protected MockMvc mockMvc;
	
	/**
	Adding this SecurityContext to your HttpSession allows to test correct site access
	(For example that only logged in users or tutors can access a site)
	*/ 
	/*
	Needed because "It turned out that the SecurityContextPersistenceFilter, 
	which is part of the Spring Security filter chain, always resets my SecurityContext, 
	which I set calling SecurityContextHolder.getContext().setAuthentication(principal) 
	(or by using the .principal(principal) method). This filter sets the SecurityContext in the SecurityContextHolder 
	with a SecurityContext from a SecurityContextRepository OVERWRITING the one I set earlier. 
	The repository is a HttpSessionSecurityContextRepository by default. 
	The HttpSessionSecurityContextRepository inspects the given HttpRequest 
	and tries to access the corresponding HttpSession. If it exists, it will try to read 
	the SecurityContext from the HttpSession. If this fails, the repository generates an empty SecurityContext.
	Thus, my solution is to pass a HttpSession along with the request, which holds the SecurityContext:"
		
	See http://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication
	
	Note that this doesn't really affect the test. Their result aren't different as with a normal security context, this just
	allows us to test it correctly
	*/


	public static class TestSecurityContext implements SecurityContext {

        private static final long serialVersionUID = -1386535243513362694L;

        private Authentication authentication;

        public TestSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }
        
   
        public Authentication getAuthentication() {
            return this.authentication;
        }


        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }
	
	@Before
	public void setUpMockMvc() throws Exception
	{
		mockMvc =  MockMvcBuilders.webAppContextSetup(this.context).addFilters(springSecurityFilterChain).build();
	}

	protected String completeUrl(String page) {
		return "/pages/"+page+".jsp";
	}
	
	protected MockHttpSession createSessionWithUser(String username,String password, String authoritie)
	{
		MockHttpSession session = new MockHttpSession();
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authoritie);
		Authentication authentication = 
		        new UsernamePasswordAuthenticationToken(username,password, authorities);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, 
                new TestSecurityContext(authentication));
        return session;
	}
	

}
