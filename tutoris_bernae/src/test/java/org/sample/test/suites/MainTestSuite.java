package org.sample.test.suites;

import org.junit.runners.Suite;
import org.sample.test.controller.EditControllerIntegrationTest;
import org.sample.test.controller.ProfileControllerIntegrationTest;
import org.sample.test.controller.RegisterControllerIntegrationTest;
import org.sample.test.controller.SearchControllerIntegrationTest;
import org.sample.test.controller.ViewTutorProfileControllerIntegrationTest;
import org.sample.test.controller.ViewTutorProfileControllerTest;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({ServiceTestSuite.class, ControllerTestSuite.class})
public class MainTestSuite {}