package org.github.dmikhaylenko.auth;

import java.util.Optional;

import org.github.dmikhaylenko.modules.users.auth.AuthTokenModel;

public interface AuthToken {
	static AuthToken valueOf(Optional<String> value) {
		return AuthTokenModel.valueOf(value);
	}
	void checkThatAuthenticated();

	Long getAuthenticatedUser();

	void logout();
	
	String getToken();
}
