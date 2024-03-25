package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.model.UserModel;
import org.github.dmikhaylenko.model.validation.ValidationUtils;

public class RegisterUserOperation {
	public RegisterUserResponse execute(RegisterUserRequest command) {
		ValidationUtils.checkConstraints(command);
		UserModel model = new UserModel(command);
		Long userId = model.registerUser();
		return new RegisterUserResponse(userId);
	}
}
