package org.github.dmikhaylenko.modules.users;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.operations.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.operations.ValidationDecorator;

@Default
public class RegisterUserOperation extends GenericOperation<RegisterUserCommand, Long> {
	public RegisterUserOperation() {
		super(configurer -> {
			configurer.decorate(new ValidationDecorator<>());
		});
	}

	@Override
	public Long executeOperation(OperationContext context, RegisterUserCommand command) {
		UserModel model = new UserModel(command);
		return model.registerUser();
	}
}
