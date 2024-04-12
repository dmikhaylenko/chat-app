package org.github.dmikhaylenko.modules;

import org.github.dmikhaylenko.dao.DaoConfigurer;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.validation.Validation.ObjectValidator;

public interface OperationTestContext {
	ObjectValidator objectValidator();

	DaoConfigurer daoConfigurer();

	OperationContext operationContext();

	OperationContextConfigurer operationContextConfigurer();
}