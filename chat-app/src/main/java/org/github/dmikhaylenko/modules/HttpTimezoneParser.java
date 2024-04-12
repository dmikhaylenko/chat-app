package org.github.dmikhaylenko.modules;

import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.time.TimezoneExtractor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpTimezoneParser  implements TimezoneExtractor {
	private static final String TIMEZONE_HEADER = "X-TIMEZONE-OFFSET";

	private final HttpHeaders headers;
	
	@Override
	public Optional<String> extractTimezone() {
		return Optional.ofNullable(headers.getHeaderString(TIMEZONE_HEADER)).map(String::trim);
	}
}
