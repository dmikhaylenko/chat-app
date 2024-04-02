package org.github.dmikhaylenko.time;

import java.time.ZoneOffset;
import java.util.Optional;

public interface TimezoneParser {
	Optional<ZoneOffset> parseTimezone();
}
