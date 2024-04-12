package org.github.dmikhaylenko.modules;

import java.util.Optional;

public interface OperationContextConfigurer {
	void initTimezone(Optional<String> value);

	void initAuthToken(Optional<String> value);
}