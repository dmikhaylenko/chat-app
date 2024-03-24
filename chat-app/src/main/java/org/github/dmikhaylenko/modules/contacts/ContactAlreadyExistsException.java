package org.github.dmikhaylenko.modules.contacts;

import org.github.dmikhaylenko.commons.errors.ApplicationException;

import lombok.ToString;

@ToString
public class ContactAlreadyExistsException extends ApplicationException {
	private static final long serialVersionUID = 668692285284867897L;

	public ContactAlreadyExistsException() {
		super(7L);
	}
}
