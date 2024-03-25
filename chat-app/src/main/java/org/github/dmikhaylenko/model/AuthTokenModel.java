package org.github.dmikhaylenko.model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.dao.Dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthTokenModel {
	private final String token;

	private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");

	public AuthTokenModel() {
		this(null);
	}

	public static AuthTokenModel getTokenFromHeader(HttpHeaders headers) {
		return getFromAuthorizationHeader(headers).map(AuthTokenModel::new).orElseGet(AuthTokenModel::new);
	}

	public void checkThatAuthenticated() {
		if (!isAuthenticated()) {
			throw new AuthenticationException("AUTH_TOKEN");
		}
	}

	public void logout() {
		Dao.authDao().executeLogout(getToken());
	}
	
	public Long getAuthenticatedUser() {
		return Dao.authDao().getAuthenticatedUser(getToken());
	}

	private static Optional<String> getFromAuthorizationHeader(HttpHeaders headers) {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION))
				.map(AUTHORIZATION_PATTERN::matcher).filter(Matcher::matches).map(matcher -> matcher.group(2));
	}

	private boolean isAuthenticated() {
		return Dao.authDao().isAuthenticated(getToken());
	}
}
