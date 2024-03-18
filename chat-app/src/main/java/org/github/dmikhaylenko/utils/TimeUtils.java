package org.github.dmikhaylenko.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtils {
	public LocalDateTime createLocalDateTime(Instant instant) {
		return LocalDateTime.ofInstant(instant, TimezoneUtils.getZoneOffset());
	}
	
	public LocalDateTime createLocalDateTime(Timestamp timestamp) {
		return createLocalDateTime(timestamp.toInstant());
	}
	
	public Instant createInstant(LocalDateTime localDateTime) {
		return localDateTime.toInstant(TimezoneUtils.getZoneOffset());
	}
	
	public Timestamp createTimestamp(LocalDateTime localDateTime) {
		return Timestamp.from(createInstant(localDateTime));
	}
	

}
