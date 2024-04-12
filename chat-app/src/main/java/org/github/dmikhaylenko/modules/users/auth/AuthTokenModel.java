package org.github.dmikhaylenko.modules.users.auth;

import java.util.Optional;

import org.github.dmikhaylenko.auth.AuthToken;
import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.modules.AuthenticationException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthTokenModel implements AuthToken {
	private final String token;

	public AuthTokenModel() {
		this(null);
	}

	public static AuthToken valueOf(Optional<String> tokenValue) {
		return tokenValue.map(AuthTokenModel::new).orElseGet(AuthTokenModel::new);
	}

	@Override
	public void checkThatAuthenticated() {
		if (!isAuthenticated()) {
			throw new AuthenticationException("AUTH_TOKEN");
		}
	}

	@Override
	public void logout() {
		Dao.authDao().executeLogout(getToken());
	}

	@Override
	public Long getAuthenticatedUser() {
		return Dao.authDao().getAuthenticatedUser(getToken());
	}

	private boolean isAuthenticated() {
		return Dao.authDao().isAuthenticated(getToken());
	}
}
