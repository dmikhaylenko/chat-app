package org.github.dmikhaylenko.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Validator;

import org.github.dmikhaylenko.validation.Validation.ObjectValidator;

@ApplicationScoped
public class BeanValidator implements ObjectValidator {
	private final Validator validator;

	@Inject
	public BeanValidator(Validator validator) {
		super();
		this.validator = validator;
	}

	@Override
	public boolean isValid(Object value) {
		return validator.validate(value).isEmpty();
	}
}
