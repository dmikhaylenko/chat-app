package org.github.dmikhaylenko.time;

import java.time.ZoneOffset;
import java.util.Optional;

import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class Timezone {
	private ZoneOffset defaultZoneOffset = ZoneOffset.UTC;
	private final ThreadLocal<ZoneOffset> ZONE_OFFSET = new ThreadLocal<ZoneOffset>();

	public ZoneOffset getZoneOffset() {
		return Optional.ofNullable(ZONE_OFFSET.get()).orElse(defaultZoneOffset);
	}

	public void loadZoneOffset(Optional<ZoneOffset> zoneOffset) {
		zoneOffset.ifPresent(value -> log.info(String.format("Loaded zone offset is: %s", value)));
		zoneOffset.ifPresent(ZONE_OFFSET::set);
	}

	public void setDefaultZoneOffset(ZoneOffset zoneOffset) {
		log.info(String.format("Default zone offset is: %s", zoneOffset));
		defaultZoneOffset = zoneOffset;
	}
}
