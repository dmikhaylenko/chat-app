package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.commons.errors.ApplicationException;

import lombok.ToString;

@ToString
public class UserWithPhoneExistsException extends ApplicationException {
	private static final long serialVersionUID = 5730524179580060112L;

	public UserWithPhoneExistsException() {
		super(2L);
	}
}
