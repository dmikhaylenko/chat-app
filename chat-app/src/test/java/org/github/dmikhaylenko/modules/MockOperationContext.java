package org.github.dmikhaylenko.modules;

import java.util.Optional;

import org.github.dmikhaylenko.auth.AuthTokenExtractor;
import org.github.dmikhaylenko.time.TimezoneExtractor;
import org.mockito.Mockito;

public class MockOperationContext extends AbstractOperationContext implements OperationContextConfigurer {
	private final TimezoneExtractor mockTimezoneParser = Mockito.mock(TimezoneExtractor.class);
	private final AuthTokenExtractor mockAuthTokenParser = Mockito.mock(AuthTokenExtractor.class);

	public MockOperationContext() {
		super();
		Mockito.doReturn(Optional.empty()).when(mockTimezoneParser).extractTimezone();
		Mockito.doReturn(Optional.empty()).when(mockAuthTokenParser).extractTokenValue();
	}

	@Override
	protected TimezoneExtractor getTimezoneParser() {
		return mockTimezoneParser;
	}

	@Override
	protected AuthTokenExtractor getAuthTokenParser() {
		return mockAuthTokenParser;
	}

	@Override
	public void initTimezone(Optional<String> value) {
		Mockito.doReturn(value).when(mockTimezoneParser).extractTimezone();
	}

	@Override
	public void initAuthToken(Optional<String> value) {
		Mockito.doReturn(value).when(mockAuthTokenParser).extractTokenValue();
	}
}
