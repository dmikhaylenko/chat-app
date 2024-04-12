package org.github.dmikhaylenko.modules.contacts;

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
public class AddContactOperationTest {
	@Parameters
	public static Collection<Object[]> data() {
		return XmlAddContactTestCases.load();
	}

	private final XmlOperationTestCase<OperationTestContext, AddContactOperation, Void> testCase;
	private final OperationTestContext testContext = new MockOperationTestContext();

	@Test
	public void addContactOperationTestCase() {
		testCase.run(testContext);
	}
}