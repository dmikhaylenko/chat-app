package org.github.dmikhaylenko.errors;

import lombok.ToString;

@ToString
public class UserWithNickNameExistsException extends ApplicationException {
	private static final long serialVersionUID = -8756369285104714233L;

	public UserWithNickNameExistsException() {
		super(3L);
	}
}
