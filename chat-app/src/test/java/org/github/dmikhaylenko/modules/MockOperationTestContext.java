package org.github.dmikhaylenko.modules;

import javax.validation.Validation;

import org.github.dmikhaylenko.dao.DaoConfigurer;
import org.github.dmikhaylenko.dao.MockDatabase;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.validation.BeanValidator;
import org.github.dmikhaylenko.validation.Validation.ObjectValidator;

import lombok.Getter;
import lombok.experimental.Accessors;

public class MockOperationTestContext implements OperationTestContext {
	@Getter
	@Accessors(fluent = true)
	private ObjectValidator objectValidator = new BeanValidator(
			Validation.buildDefaultValidatorFactory().getValidator());
	@Getter
	@Accessors(fluent = true)
	private DaoConfigurer daoConfigurer = new MockDatabase();
	private MockOperationContext operationContext = new MockOperationContext();

	@Override
	public OperationContext operationContext() {
		return operationContext;
	}

	@Override
	public OperationContextConfigurer operationContextConfigurer() {
		return operationContext;
	}
}
