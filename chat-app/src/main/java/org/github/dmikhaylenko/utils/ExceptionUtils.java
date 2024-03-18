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
	
	public ApplicationException createWrongLoginOrPasswordException() {
		return createApplicationException(4L);
	}
	
	public ApplicationException createAuthenticationException(String... challenges) {
		AuthenticationException exception = new AuthenticationException();
		exception.setCode(5L);
		exception.setChallenge(Arrays.asList(challenges));
		exception.setChallenges(Arrays.asList(challenges));
		return exception;
	}
	
	public ApplicationException createMissingRequestedUserException() {
		return createApplicationException(6L);
	}
	
	public ApplicationException createContactAlreadyExistsException() {
		return createApplicationException(7L);
	}
	
	public ApplicationException createMissingRequestedContactException() {
		return createApplicationException(8L);
	}
	
	public ApplicationException createMissingRequestedMessageException() {
		return createApplicationException(9L);
	}
	
	public ApplicationException createItIsForbiddenToEditForeignUsersMessagesException() {
		return createApplicationException(10L);
	}
	
	public ApplicationException createItIsForbiddenToDeleteForeignUsersMessagesException() {
		return createApplicationException(11L);
	}
	
	private ApplicationException createApplicationException(Long code) {
		ApplicationException exception = new ApplicationException();
		exception.setCode(code);
		return exception;
	}
}
