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
@XmlRootElement(name = "DeleteContactTestCases")
public class XmlDeleteContactTestCases {
	public static final String DESCRIPTOR_PATH = "org/github/dmikhaylenko/modules/contacts/DeleteContactOperationTestCases.xml";

	// @formatter:off
	@XmlElements({
		@XmlElement(name = "DeleteContactSuccessfulTestCase", type = XmlDeleteContactSuccessfulTestCase.class),
		@XmlElement(name = "DeleteContactFailureTestCase", type = XmlDeleteContactFailureTestCase.class)
	})
	// @formatter:on
	private List<XmlOperationTestCase<OperationTestContext, DeleteContactCommand, Void>> testCases = new ArrayList<>();

	// @formatter:off
	public static Collection<Object[]> load() {
		return XmlTestCasesLoader
				.valueOf(
						XmlDeleteContactTestCases.class, 
						XmlDeleteContactTestCases::getTestCases,
						DESCRIPTOR_PATH)
				.load();
	}
	// @formatter:on
	
	@Data
	@XmlRootElement
	@NoArgsConstructor
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlDeleteContactComand implements DeleteContactCommand {
		@XmlElement
		private Long contactId;
	}

	
	@Data
	@XmlRootElement
	@NoArgsConstructor
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlDeleteContactSuccessfulTestCase
			extends XmlOperationSuccessfulTestCase<OperationTestContext, DeleteContactCommand, Void> {
		@XmlElement
		private XmlDeleteContactComand command = new XmlDeleteContactComand();

		@Override
		protected Void expectedResult() {
			return null;
		}

		@Override
		protected DeleteContactCommand createRequest() {
			return command;
		}

		@Override
		protected Operation<DeleteContactCommand, Void> createOperation() {
			return new DeleteContactOperation();
		}
	}

	@Data
	@XmlRootElement
	@NoArgsConstructor
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class XmlDeleteContactFailureTestCase
			extends XmlOperationFailureTestCase<OperationTestContext, DeleteContactCommand, Void> {
		@XmlElement
		private XmlDeleteContactComand command = new XmlDeleteContactComand();

		@Override
		protected DeleteContactCommand createRequest() {
			return command;
		}

		@Override
		protected Operation<DeleteContactCommand, Void> createOperation() {
			return new DeleteContactOperation();
		}
	}
}
