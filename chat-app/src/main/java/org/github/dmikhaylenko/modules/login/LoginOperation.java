package org.github.dmikhaylenko.modules.login;

import org.github.dmikhaylenko.model.LoginModel;

public class LoginOperation {
	public LoginResponse execute(LoginRequest request) {
		LoginModel model = new LoginModel(request);
		String token = model.login();
		return new LoginResponse(token);
	}
}
