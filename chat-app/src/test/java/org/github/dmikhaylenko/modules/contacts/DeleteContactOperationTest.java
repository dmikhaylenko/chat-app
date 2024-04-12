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
public class DeleteContactOperationTest {
	@Parameters
	public static Collection<Object[]> data() {
		return XmlDeleteContactTestCases.load();
	}

	private final XmlOperationTestCase<OperationTestContext, DeleteContactCommand, Void> testCase;
	private final OperationTestContext testContext = new MockOperationTestContext();

	@Test
	public void deleteContactTestCase() {
		testCase.run(testContext);
	}
}