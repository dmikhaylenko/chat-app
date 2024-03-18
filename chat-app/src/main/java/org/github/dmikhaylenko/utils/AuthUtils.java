package org.github.dmikhaylenko.utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthUtils {
	private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");

	public AuthTokenModel getTokenFromHeader(HttpHeaders headers) {
		AuthTokenModel model = new AuthTokenModel();
		getFromAuthorizationHeader(headers).ifPresent(model::setToken);
		return model;
	}

	public void checkThatAuthenticated(AuthTokenModel authToken) {
		if (!authToken.isAuthenticated()) {
			throw ExceptionUtils.createAuthenticationException("AUTH_TOKEN");
		}
	}

	private Optional<String> getFromAuthorizationHeader(HttpHeaders headers) {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION))
				.map(AUTHORIZATION_PATTERN::matcher).filter(Matcher::matches).map(matcher -> matcher.group(2));
	}
}
