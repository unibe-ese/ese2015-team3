package org.sample.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sample.test.controller.EditControllerIntegrationTest;
import org.sample.test.controller.ProfileControllerIntegrationTest;
import org.sample.test.controller.RegisterControllerIntegrationTest;
import org.sample.test.controller.SearchControllerIntegrationTest;
import org.sample.test.controller.ViewTutorProfileControllerIntegrationTest;
import org.sample.test.controller.ViewTutorProfileControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({EditControllerIntegrationTest.class, ProfileControllerIntegrationTest.class,
					 RegisterControllerIntegrationTest.class, SearchControllerIntegrationTest.class, 
					 ViewTutorProfileControllerTest.class, ViewTutorProfileControllerIntegrationTest.class})
public class ControllerTestSuite {}