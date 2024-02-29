package org.github.dmikhaylenko.utils;

import java.util.Arrays;

import org.github.dmikhaylenko.errors.ApplicationException;
import org.github.dmikhaylenko.errors.AuthenticationException;
import org.github.dmikhaylenko.errors.CheckedExceptionWrapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {
	public CheckedExceptionWrapper uncheckedOf(Exception error) {
		return new CheckedExceptionWrapper(error);
	}

	public ApplicationException createValidationErrorException() {
		return createApplicationException(1L);
	}

	public ApplicationException createUserWithPhoneExistsException() {
		return createApplicationException(2L);
	}

	public ApplicationException createUserWithNickNameExistsException() {
		return createApplicationException(3L);
	}

	public ApplicationException createAuthenticationException(String... challenges) {
		AuthenticationException exception = new AuthenticationException();
		exception.setCode(5L);
		exception.setChallenge(Arrays.asList(challenges));
		exception.setChallenges(Arrays.asList(challenges));
		return exception;
	}
	
	private ApplicationException createApplicationException(Long code) {
		ApplicationException exception = new ApplicationException();
		exception.setCode(code);
		return exception;
	}
}
