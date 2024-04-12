package org.github.dmikhaylenko.modules;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.auth.AuthTokenExtractor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpAuthTokenParser implements AuthTokenExtractor {
	private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");

	private final HttpHeaders headers;

	@Override
	public Optional<String> extractTokenValue() {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION))
				.map(AUTHORIZATION_PATTERN::matcher).filter(Matcher::matches).map(matcher -> matcher.group(2));
	}
}
