package org.github.dmikhaylenko.errors;

import lombok.ToString;

@ToString
public class MissingRequestedContactException extends ApplicationException {
	private static final long serialVersionUID = -5562204850988097432L;

	public MissingRequestedContactException() {
		super(8L);
	}
}
