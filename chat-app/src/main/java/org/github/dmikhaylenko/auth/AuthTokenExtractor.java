package org.github.dmikhaylenko.auth;

import java.util.Optional;

public interface AuthTokenExtractor {
	Optional<String> extractTokenValue();
}
