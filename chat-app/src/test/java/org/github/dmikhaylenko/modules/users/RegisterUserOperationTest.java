package org.github.dmikhaylenko.modules.users;

import java.util.Collection;

import org.github.dmikhaylenko.modules.MockOperationTestContext;
import org.github.dmikhaylenko.modules.OperationTestContext;
import org.github.dmikhaylenko.modules.XmlOperationTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RunWith(Parameterized.class)
public class RegisterUserOperationTest {
	@Parameters
	public static Collection<Object[]> data() {
		return XmlRegisterUserTestCases.load();
	}

	private final XmlOperationTestCase<OperationTestContext, RegisterUserCommand, Long> testCase;
	private final OperationTestContext testContext = new MockOperationTestContext();

	@Test
	public void registerUserTestCase() {
		testCase.run(testContext);
	}
}
