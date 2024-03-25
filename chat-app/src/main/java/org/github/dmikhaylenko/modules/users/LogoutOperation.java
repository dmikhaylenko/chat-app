package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.model.AuthTokenModel;

public class LogoutOperation {
	public LogoutResponse execute(AuthTokenModel token) {
		token.checkThatAuthenticated();
		token.logout();
		return new LogoutResponse();
	}
}
