package org.github.dmikhaylenko.modules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.github.dmikhaylenko.errors.ApplicationException;
import org.github.dmikhaylenko.operations.Operation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.junit.Assert;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@XmlRootElement
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationFailureTestCase")
public abstract class XmlOperationFailureTestCase<C extends OperationTestContext, Q, S>
		extends XmlOperationTestCase<C, Q, S> {
	@XmlElement
	private Long expectedErrorCode;

	@Override
	protected final void run(C testContext, Operation<Q, S> operation, OperationContext context, Q request) {
		try {
			operation.execute(context, request);
			Assert.fail();
		} catch (ApplicationException error) {
			Assert.assertEquals(expectedErrorCode, error.getCode());
		}
	}
}
