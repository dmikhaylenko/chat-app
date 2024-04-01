package org.github.dmikhaylenko.http;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.auth.AuthTokenParser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpAuthTokenParser implements AuthTokenParser {
	private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");
	private final HttpHeaders headers;
	
	@Override
	public Optional<String> parseTokenValue() {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION))
				.map(AUTHORIZATION_PATTERN::matcher).filter(Matcher::matches).map(matcher -> matcher.group(2));
	}
}
