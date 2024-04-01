package org.github.dmikhaylenko.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Validation {
	private ObjectValidator validator = null;
	
	public void initialize(ObjectValidator validator) {
		Validation.validator = validator;
	}
	
	public <T> void checkConstraints(T validatable) {
		validator.validate(validatable);
	}
	
	public static interface ObjectValidator {
		default void validate(Object value) {
			if (!isValid(value)) {
				throw new ValidationException();
			}
		}
		
		boolean isValid(Object value);
	}
}
