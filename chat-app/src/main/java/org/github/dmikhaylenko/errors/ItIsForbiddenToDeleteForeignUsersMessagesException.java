package org.github.dmikhaylenko.errors;

import lombok.ToString;

@ToString
public class ItIsForbiddenToDeleteForeignUsersMessagesException extends ApplicationException {
	private static final long serialVersionUID = 7204041473982465354L;

	public ItIsForbiddenToDeleteForeignUsersMessagesException() {
		super(11L);
	}
}
