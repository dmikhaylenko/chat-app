package org.github.dmikhaylenko.auth;

import java.util.Optional;

public interface AuthTokenParser {
	Optional<String> parseTokenValue();
}
