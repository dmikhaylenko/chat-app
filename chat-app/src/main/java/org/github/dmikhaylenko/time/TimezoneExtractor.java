package org.github.dmikhaylenko.time;

import java.util.Optional;

public interface TimezoneExtractor {
	Optional<String> extractTimezone();
}
