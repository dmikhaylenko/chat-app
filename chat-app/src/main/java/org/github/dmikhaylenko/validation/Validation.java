package org.github.dmikhaylenko.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Validation {
	private ObjectValidator validator = null;

	public void initialize(ObjectValidator validator) {
		Validation.validator = validator;
	}

	public <T> void checkConstraints(T validatable) {
		if (!validator.isValid(validatable)) {
			throw new ValidationException();
		}
	}

	public static interface ObjectValidator {
		boolean isValid(Object value);
	}
}
