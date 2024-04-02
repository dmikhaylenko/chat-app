package org.github.dmikhaylenko.auth;

public interface AuthToken {
	void checkThatAuthenticated();

	Long getAuthenticatedUser();

	void logout();
	
	String getToken();
}
