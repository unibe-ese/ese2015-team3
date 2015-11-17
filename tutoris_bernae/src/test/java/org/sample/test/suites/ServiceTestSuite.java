package org.sample.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sample.test.service.EditFormServiceTest;
import org.sample.test.service.EditFormServiceTransactionTest;
import org.sample.test.service.MessageServiceTest;
import org.sample.test.service.MessageServiceTransactionTest;
import org.sample.test.service.RegisterFormServiceTest;
import org.sample.test.service.RegisterFormServiceTransactionTest;
import org.sample.test.service.TutorFormServiceTest;
import org.sample.test.service.TutorFormServiceTransactionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({EditFormServiceTest.class, EditFormServiceTransactionTest.class,
					RegisterFormServiceTest.class, RegisterFormServiceTransactionTest.class,
					TutorFormServiceTest.class, TutorFormServiceTransactionTest.class,
					MessageServiceTransactionTest.class,MessageServiceTest.class})
public class ServiceTestSuite {}