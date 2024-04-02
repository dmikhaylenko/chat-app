package org.github.dmikhaylenko.validation;

import org.github.dmikhaylenko.errors.ApplicationException;

import lombok.ToString;

@ToString
public class ValidationException extends ApplicationException {
	private static final long serialVersionUID = -5570583166308338865L;

	public ValidationException() {
		super(1L);
	}
}
