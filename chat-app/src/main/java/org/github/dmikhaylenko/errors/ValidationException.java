package org.github.dmikhaylenko.errors;

import lombok.ToString;

@ToString
public class ValidationException extends ApplicationException {
	private static final long serialVersionUID = -5570583166308338865L;

	public ValidationException() {
		super(1L);
	}
}
