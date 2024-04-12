package org.github.dmikhaylenko.modules.users;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.modules.AuthenticationDecorator;
import org.github.dmikhaylenko.modules.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.operations.ValidationDecorator;

@Default
public class ChangePasswordOperation extends GenericOperation<ChangePasswordCommand, Long> {
	public ChangePasswordOperation() {
		super(configurer -> {
			configurer.decorate(new ValidationDecorator<>());
			configurer.decorate(new AuthenticationDecorator<>());
		});
	}

	@Override
	public Long executeOperation(OperationContext context, ChangePasswordCommand request) {
		ChangePasswordModel model = new ChangePasswordModel(request);
		return model.changePassword(context.getAuthentication());
	}
}
