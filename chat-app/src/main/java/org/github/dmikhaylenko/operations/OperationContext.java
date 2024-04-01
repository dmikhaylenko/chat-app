package org.github.dmikhaylenko.operations;

import java.time.ZoneOffset;
import java.util.Optional;

import org.github.dmikhaylenko.auth.AuthToken;

public interface OperationContext {
	Optional<ZoneOffset> getZoneOffset();

	AuthToken getAuthentication();
	
	default Long getAuthenticatedUser() {
		return getAuthentication().getAuthenticatedUser();
	}
}
