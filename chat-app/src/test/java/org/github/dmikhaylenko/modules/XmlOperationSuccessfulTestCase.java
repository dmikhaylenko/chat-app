package org.github.dmikhaylenko.modules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
@XmlType(name = "OperationSuccessfulTestCase")
public abstract class XmlOperationSuccessfulTestCase<C extends OperationTestContext, Q, S>
		extends XmlOperationTestCase<C, Q, S> {
	@Override
	protected final void run(C testContext, Operation<Q, S> operation, OperationContext context, Q request) {
		S result = operation.execute(context, request);
		Assert.assertEquals(expectedResult(), result);
	}

	protected abstract S expectedResult();
}
