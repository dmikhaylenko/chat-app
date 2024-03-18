package org.github.dmikhaylenko.utils;

import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimezoneUtils {
	private static final String TIMEZONE_HEADER = "X-TIMEZONE-OFFSET";
	private ZoneOffset defaultZoneOffset = ZoneOffset.UTC;
	private final ThreadLocal<ZoneOffset> ZONE_OFFSET = new ThreadLocal<ZoneOffset>();

	public ZoneOffset getZoneOffset() {
		return Optional.ofNullable(ZONE_OFFSET.get()).orElse(defaultZoneOffset);
	}

	public void loadZoneOffset(HttpHeaders headers) {
		getZoneOffset(headers).ifPresent(ZONE_OFFSET::set);
	}

	public void setDefaultZoneOffset(ZoneOffset zoneOffset) {
		defaultZoneOffset = zoneOffset;
	}

	private Optional<ZoneOffset> getZoneOffset(HttpHeaders headers) {
		try {			
			return Optional.ofNullable(headers.getHeaderString(TIMEZONE_HEADER)).map(ZoneOffset::of);
		} catch (DateTimeException error) {
			return Optional.empty();
		}
	}
}
