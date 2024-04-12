package org.github.dmikhaylenko.modules;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.time.Time.CurrentTimeProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockCurrentTimeProvider implements CurrentTimeProvider {
	private final OperationContext operationContext;
	private final LocalDateTime currentTime;

	@Override
	public Instant now() {
		ZoneOffset zoneOffet = operationContext.getZoneOffset().orElse(ZoneOffset.UTC);
		return currentTime.toInstant(zoneOffet);
	}
}
