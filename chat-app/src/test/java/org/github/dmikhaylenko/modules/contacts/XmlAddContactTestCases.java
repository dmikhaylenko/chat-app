package org.github.dmikhaylenko.modules.contacts;

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
@XmlRootElement(name = "AddContactTestCases")
public class XmlAddContactTestCases {
	public static final String DESCRIPTOR_PATH = "org/github/dmikhaylenko/modules/contacts/AddContactOperationTestCases.xml";

	// @formatter:off
	@XmlElements({
		@XmlElement(name = "AddContactSuccessfulTestCase", type = XmlAddContactSuccessfulTestCase.class),
		@XmlElement(name = "AddContactFailureTestCase", type = XmlAddContactFailureTestCase.class)
	})
	// @formatter:on
	private List<XmlOperationTestCase<OperationTestContext, AddContactOperation, Void>> testCases = new ArrayList<>();

	
	
	// @formatter:off
	public static Collection<Object[]> load() {
		return XmlTestCasesLoader
				.valueOf(
						XmlAddContactTestCases.class, 
						XmlAddContactTestCases::getTestCases,
						DESCRIPTOR_PATH)
				.load();
	}
	// @formatter:on
	
	@Data
	@XmlRootElement
	@NoArgsConstructor
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlAddContactCommand implements AddContactCommand {
		@XmlElement
		private Long contactId;
	}
	
	@Data
	@XmlRootElement
	@NoArgsConstructor
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlAddContactSuccessfulTestCase
			extends XmlOperationSuccessfulTestCase<OperationTestContext, AddContactCommand, Void> {
		@XmlElement
		private XmlAddContactCommand command = new XmlAddContactCommand();

		@Override
		protected Void expectedResult() {
			return null;
		}

		@Override
		protected AddContactCommand createRequest() {
			return command;
		}

		@Override
		protected Operation<AddContactCommand, Void> createOperation() {
			return new AddContactOperation();
		}
	}
	
	@Data
	@XmlRootElement
	@NoArgsConstructor
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlAddContactFailureTestCase
			extends XmlOperationFailureTestCase<OperationTestContext, AddContactCommand, Void> {
		@XmlElement
		private XmlAddContactCommand command = new XmlAddContactCommand();

		@Override
		protected AddContactCommand createRequest() {
			return command;
		}

		@Override
		protected Operation<AddContactCommand, Void> createOperation() {
			return new AddContactOperation();
		}
	}
}
