package org.github.dmikhaylenko.errors;

import lombok.ToString;

@ToString
public class ItIsForbiddenToEditForeignUsersMessagesException extends ApplicationException {
	private static final long serialVersionUID = -7318180884984485470L;

	public ItIsForbiddenToEditForeignUsersMessagesException() {
		super(10L);
	}
}
