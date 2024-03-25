package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.validation.ValidationUtils;

public class ChangePasswordOperation {
	public ChangePasswordResponse execute(AuthTokenModel token, ChangePasswordRequest request) {
		ChangePasswordModel model = new ChangePasswordModel(request);
		ValidationUtils.checkConstraints(request);
		token.checkThatAuthenticated();
		Long userId = model.changePassword(token);
		return new ChangePasswordResponse(userId);
	}
}
