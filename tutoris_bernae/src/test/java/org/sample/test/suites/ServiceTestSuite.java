package org.sample.test.suites;

import org.junit.runners.Suite;
import org.sample.test.service.EditFormServiceTest;
import org.sample.test.service.EditFormServiceTransactionTest;
import org.sample.test.service.RegisterFormServiceTest;
import org.sample.test.service.RegisterFormServiceTransactionTest;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({EditFormServiceTest.class, EditFormServiceTransactionTest.class,
					RegisterFormServiceTest.class, RegisterFormServiceTransactionTest.class})
public class ServiceTestSuite {}