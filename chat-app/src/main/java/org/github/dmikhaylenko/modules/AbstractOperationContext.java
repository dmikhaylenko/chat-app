package org.github.dmikhaylenko.modules;

import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.util.Optional;

import org.github.dmikhaylenko.auth.AuthToken;
import org.github.dmikhaylenko.auth.AuthTokenExtractor;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.time.TimezoneExtractor;

public abstract class AbstractOperationContext implements OperationContext {
	@Override
	public Optional<ZoneOffset> getZoneOffset() {
		try {
			return getTimezoneParser().extractTimezone().map(ZoneOffset::of);
		} catch (DateTimeException error) {
			return Optional.empty();
		}
	}

	@Override
	public AuthToken getAuthentication() {
		return AuthToken.valueOf(getAuthTokenParser().extractTokenValue());
	}

	protected abstract TimezoneExtractor getTimezoneParser();

	protected abstract AuthTokenExtractor getAuthTokenParser();
}
