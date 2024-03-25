package org.github.dmikhaylenko.model;

import org.github.dmikhaylenko.model.errors.ApplicationException;

import lombok.ToString;

@ToString
public class MissingRequestedUserException extends ApplicationException {
	private static final long serialVersionUID = 3076875778282260977L;

	public MissingRequestedUserException() {
		super(6L);
	}
}
