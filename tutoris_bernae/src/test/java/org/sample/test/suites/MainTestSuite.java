package org.sample.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ServiceTestSuite.class, ControllerTestSuite.class})
public class MainTestSuite {}