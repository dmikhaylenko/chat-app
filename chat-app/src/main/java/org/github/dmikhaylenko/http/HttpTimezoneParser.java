package org.github.dmikhaylenko.http;

import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.time.TimezoneParser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpTimezoneParser implements TimezoneParser {
	private static final String TIMEZONE_HEADER = "X-TIMEZONE-OFFSET";

	private final HttpHeaders headers;

	@Override
	public Optional<ZoneOffset> parseTimezone() {
		try {
			return Optional.ofNullable(headers.getHeaderString(TIMEZONE_HEADER)).map(ZoneOffset::of);
		} catch (DateTimeException error) {
			return Optional.empty();
		}
	}
}
