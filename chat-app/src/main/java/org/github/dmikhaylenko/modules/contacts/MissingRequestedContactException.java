package org.github.dmikhaylenko.modules.contacts;

import org.github.dmikhaylenko.errors.ApplicationException;

import lombok.ToString;

@ToString
public class MissingRequestedContactException extends ApplicationException {
	private static final long serialVersionUID = -5562204850988097432L;

	public MissingRequestedContactException() {
		super(8L);
	}
}
