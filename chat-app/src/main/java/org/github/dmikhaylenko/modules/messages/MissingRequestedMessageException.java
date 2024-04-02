package org.github.dmikhaylenko.modules.messages;

import org.github.dmikhaylenko.errors.ApplicationException;

import lombok.ToString;

@ToString
public class MissingRequestedMessageException extends ApplicationException {
	private static final long serialVersionUID = -3270107156050391993L;

	public MissingRequestedMessageException() {
		super(9L);
	}
}
