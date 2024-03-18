package org.github.dmikhaylenko.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {
	public <T> void checkConstraints(T validatable) {
		Validator validator = Resources.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(validatable);
		if (!violations.isEmpty()) {
			throw ExceptionUtils.createValidationErrorException();
		}
	}
}
