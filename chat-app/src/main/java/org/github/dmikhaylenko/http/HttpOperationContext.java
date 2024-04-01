package org.github.dmikhaylenko.http;

import java.time.ZoneOffset;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.auth.AuthToken;
import org.github.dmikhaylenko.auth.AuthTokenParser;
import org.github.dmikhaylenko.modules.users.auth.AuthTokenModel;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.time.TimezoneParser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpOperationContext implements OperationContext {
	private final HttpHeaders headers;

	@Override
	public Optional<ZoneOffset> getZoneOffset() {
		TimezoneParser timezoneParser = new HttpTimezoneParser(headers);
		return timezoneParser.parseTimezone();
	}

	@Override
	public AuthToken getAuthentication() {
		AuthTokenParser tokenParser = new HttpAuthTokenParser(headers); 
		return tokenParser.parseTokenValue().map(AuthTokenModel::new).orElseGet(AuthTokenModel::new);
	}
}
