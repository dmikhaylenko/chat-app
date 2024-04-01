package org.github.dmikhaylenko.operations;

import org.github.dmikhaylenko.operations.Operation.OperationDecorator;

import lombok.extern.java.Log;

@Log
public class AuthenticationDecorator<Q, S> implements OperationDecorator<Q, S> {
	@Override
	public S execute(Operation<Q, S> original, OperationContext context, Q request) {
		log.entering(getClass().toString(), "execute");
		context.getAuthentication().checkThatAuthenticated();
		S result = original.execute(context, request);
		log.entering(getClass().toString(), "execute");
		return result;
	}
}
