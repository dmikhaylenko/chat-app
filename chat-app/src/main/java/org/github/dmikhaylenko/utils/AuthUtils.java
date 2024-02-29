package org.github.dmikhaylenko.utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthUtils {
	private final Pattern REGEX = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");
	private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");
	private static final Pattern PARSE_PATTERN = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");

	public AuthTokenModel getTokenFromHeader(HttpHeaders headers) {
		AuthTokenModel model = new AuthTokenModel();
		getFromAuthorizationHeader(headers).ifPresent(model::setToken);
		return model;
	}

	public void checkAuthenticated(AuthTokenModel token) {
		if (!token.isAuthenticated()) {
			throw ExceptionUtils.createAuthenticationException("AUTH_TOKEN");
		}
	}

	public AuthTokenModel parseAuthToken(HttpHeaders headers) {
		AuthTokenModel model = new AuthTokenModel();
		parseAuthTokenString(headers).ifPresent(model::setToken);
		return model;
	}

	private Optional<String> parseAuthTokenString(HttpHeaders headers) {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION)).map(REGEX::matcher)
				.filter(Matcher::matches).map(matcher -> matcher.group(2));
	}

	private Optional<String> getFromAuthorizationHeader(HttpHeaders headers) {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION))
				.map(AUTHORIZATION_PATTERN::matcher).filter(Matcher::matches).map(matcher -> matcher.group(2));
	}

	public void checkThatAuthenticated(AuthTokenModel authToken) {
		if (!authToken.isAuthenticated()) {
			throw ExceptionUtils.createUserNotAuthenticatedException("AUTH_TOKEN");
		}
	}

	public AuthTokenModel getTokenFromHeaders(HttpHeaders headers) {
		AuthTokenModel model = new AuthTokenModel();
		getTokenStringFromHeaders(headers).ifPresent(model::setToken);
		return model;
	}

	private Optional<String> getTokenStringFromHeaders(HttpHeaders headers) {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION)).map(PARSE_PATTERN::matcher)
				.filter(Matcher::matches).map(matcher -> matcher.group(2));
	}
}
