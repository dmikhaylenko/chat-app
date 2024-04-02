package org.github.dmikhaylenko.modules.users.auth;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.operations.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;

@Default
public class LoginOperation extends GenericOperation<LoginCommand, String> {
	@Override
	public String executeOperation(OperationContext context, LoginCommand request) {
		LoginModel model = new LoginModel(request);
		return model.login();
	}
}
