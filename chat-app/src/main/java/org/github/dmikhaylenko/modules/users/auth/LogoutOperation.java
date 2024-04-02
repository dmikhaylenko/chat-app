package org.github.dmikhaylenko.modules.users.auth;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.operations.AuthenticationDecorator;
import org.github.dmikhaylenko.operations.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;

@Default
public class LogoutOperation extends GenericOperation<Void, Void> {
	public LogoutOperation() {
		super(configurer -> {
			configurer.decorate(new AuthenticationDecorator<>());	
		});
	}

	@Override
	public Void executeOperation(OperationContext context, Void request) {
		context.getAuthentication().logout();
		return null;
	}	
}
