package org.github.dmikhaylenko.model;

import org.github.dmikhaylenko.model.errors.ApplicationException;

import lombok.ToString;

@ToString
public class UserWithPhoneExistsException extends ApplicationException {
	private static final long serialVersionUID = 5730524179580060112L;

	public UserWithPhoneExistsException() {
		super(2L);
	}
}
