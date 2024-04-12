package org.github.dmikhaylenko.modules.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.modules.OperationTestContext;
import org.github.dmikhaylenko.modules.XmlOperationFailureTestCase;
import org.github.dmikhaylenko.modules.XmlOperationSuccessfulTestCase;
import org.github.dmikhaylenko.modules.XmlOperationTestCase;
import org.github.dmikhaylenko.modules.XmlTestCasesLoader;
import org.github.dmikhaylenko.operations.Operation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RegisterUserTestCases")
public class XmlRegisterUserTestCases {
	public static final String DESCRIPTOR_PATH = "org/github/dmikhaylenko/modules/users/RegisterUserOperationTestCases.xml";

	// @formatter:off
	@XmlElements({
		@XmlElement(name = "RegisterUserSuccessfulTestCase", type = XmlRegisterUserSuccessfulTestCase.class),
		@XmlElement(name = "RegisterUserFailureTestCase", type = XmlRegisterUserFailureTestCase.class)
	})
	// @formatter:on
	private List<XmlOperationTestCase<OperationTestContext, RegisterUserCommand, Long>> testCases = new ArrayList<>();

	// @formatter:off
	public static Collection<Object[]> load() {
		return XmlTestCasesLoader
				.valueOf(
						XmlRegisterUserTestCases.class, 
						XmlRegisterUserTestCases::getTestCases,
						DESCRIPTOR_PATH)
				.load();
	}
	// @formatter:on

	@Data
	@XmlRootElement
	@NoArgsConstructor
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlRegisterUserCommand implements RegisterUserCommand {
		@XmlElement
		private String avatar;

		@XmlElement
		private String phone;

		@XmlElement
		private String password;

		@XmlElement
		private String publicName;
	}

	@Data
	@XmlRootElement
	@NoArgsConstructor
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlRegisterUserSuccessfulTestCase
			extends XmlOperationSuccessfulTestCase<OperationTestContext, RegisterUserCommand, Long> {
		@XmlElement
		private XmlRegisterUserCommand command = new XmlRegisterUserCommand();

		@XmlElement
		private Long expectedResult;

		@Override
		protected Long expectedResult() {
			return expectedResult;
		}

		@Override
		protected RegisterUserCommand createRequest() {
			return command;
		}

		@Override
		protected Operation<RegisterUserCommand, Long> createOperation() {
			return new RegisterUserOperation();
		}
	}

	@Data
	@XmlRootElement
	@NoArgsConstructor
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlRegisterUserFailureTestCase
			extends XmlOperationFailureTestCase<OperationTestContext, RegisterUserCommand, Long> {
		@XmlElement
		private XmlRegisterUserCommand command = new XmlRegisterUserCommand();

		@Override
		protected RegisterUserCommand createRequest() {
			return command;
		}

		@Override
		protected Operation<RegisterUserCommand, Long> createOperation() {
			return new RegisterUserOperation();
		}
	}
}
