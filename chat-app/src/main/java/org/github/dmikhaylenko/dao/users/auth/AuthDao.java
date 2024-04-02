package org.github.dmikhaylenko.dao.users.auth;

import java.util.Optional;

public interface AuthDao {
	Optional<String> executeLogin(String username, String password);

	void executeLogout(String token);

	boolean isAuthenticated(String token);

	Long getAuthenticatedUser(String token);
}