package org.github.dmikhaylenko.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.github.dmikhaylenko.errors.ValidationException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {
	private Validator validator = null;
	
	public void initialize(Validator validator) {
		ValidationUtils.validator = validator;
	}
	
	public <T> void checkConstraints(T validatable) {
		Set<ConstraintViolation<T>> violations = validator.validate(validatable);
		if (!violations.isEmpty()) {
			throw new ValidationException();
		}
	}
}
