package org.github.dmikhaylenko.modules;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.auth.AuthTokenExtractor;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.time.TimezoneExtractor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpOperationContext extends AbstractOperationContext implements OperationContext {
	private final HttpHeaders headers;

	@Override
	protected TimezoneExtractor getTimezoneParser() {
		return new HttpTimezoneParser(headers);
	}

	@Override
	protected AuthTokenExtractor getAuthTokenParser() {
		return new HttpAuthTokenParser(headers);
	}
}
