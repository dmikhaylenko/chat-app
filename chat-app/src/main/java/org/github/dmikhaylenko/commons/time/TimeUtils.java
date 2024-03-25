package org.github.dmikhaylenko.commons.time;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtils {
	public Instant currentInstant() {
		return Instant.now();
	}
	
	public LocalDateTime currentLocalDateTime() {
		return createLocalDateTime(currentInstant());
	}
	
	public LocalDateTime createLocalDateTime(Instant instant) {
		return Optional.ofNullable(instant).map(value -> {
			return LocalDateTime.ofInstant(value, TimezoneUtils.getZoneOffset());
		}).orElse(null);
	}

	public LocalDateTime createLocalDateTime(Timestamp timestamp) {
		return Optional.ofNullable(timestamp).map(Timestamp::toInstant).map(TimeUtils::createLocalDateTime)
				.orElse(null);
	}

	public Instant createInstant(LocalDateTime localDateTime) {
		return Optional.ofNullable(localDateTime).map(value -> {
			return value.toInstant(TimezoneUtils.getZoneOffset());
		}).orElse(null);
	}

	public Timestamp createTimestamp(LocalDateTime localDateTime) {
		// @formatter:off
		return Optional.ofNullable(localDateTime)
				.map(TimeUtils::createInstant)
				.map(Timestamp::from)
				.orElse(null);
		// @formatter:on
	}
}
