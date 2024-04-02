package org.github.dmikhaylenko.operations;

import org.github.dmikhaylenko.operations.Operation.OperationDecorator;
import org.github.dmikhaylenko.validation.Validation;

import lombok.extern.java.Log;

@Log
public class ValidationDecorator<Q, S> implements OperationDecorator<Q, S> {

	@Override
	public S execute(Operation<Q, S> original, OperationContext context, Q request) {
		log.entering(getClass().toString(), "execute");
		Validation.checkConstraints(request);
		S result = original.execute(context, request);
		log.exiting(getClass().toString(), "execute");
		return result;
	}

}
