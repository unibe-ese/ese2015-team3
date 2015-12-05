package org.sample.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sample.test.controller.EditControllerIntegrationTest;
import org.sample.test.controller.EditTutorControllerIntegrationTest;
import org.sample.test.controller.MessageControllerIntegrationTest;
import org.sample.test.controller.ProfileControllerIntegrationTest;
import org.sample.test.controller.RegisterControllerIntegrationTest;
import org.sample.test.controller.SearchControllerIntegrationTest;
import org.sample.test.controller.ViewTutorProfileControllerIntegrationTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({EditControllerIntegrationTest.class, ProfileControllerIntegrationTest.class,
					 RegisterControllerIntegrationTest.class, SearchControllerIntegrationTest.class, 
					 ViewTutorProfileControllerIntegrationTest.class,
					 MessageControllerIntegrationTest.class, EditTutorControllerIntegrationTest.class})
public class ControllerTestSuite {}