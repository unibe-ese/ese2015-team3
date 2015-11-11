package org.sample.test.suites;

import org.junit.runners.Suite;
import org.sample.test.service.EditFormServiceTest;
import org.sample.test.service.EditFormServiceTransactionTest;
import org.sample.test.service.RegisterFormServiceTest;
import org.sample.test.service.RegisterFormServiceTransactionTest;
import org.sample.test.service.TutorFormServiceTest;
import org.sample.test.service.TutorFormServiceTransactionTest;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({EditFormServiceTest.class, EditFormServiceTransactionTest.class,
					RegisterFormServiceTest.class, RegisterFormServiceTransactionTest.class,
					TutorFormServiceTest.class, TutorFormServiceTransactionTest.class})
public class ServiceTestSuite {}