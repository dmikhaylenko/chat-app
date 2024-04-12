package org.github.dmikhaylenko.modules;

import org.github.dmikhaylenko.operations.Operation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.operations.Operation.OperationDecorator;

import lombok.extern.java.Log;

@Log
public class AuthenticationDecorator<Q, S> implements OperationDecorator<Q, S> {
	@Override
	public S execute(Operation<Q, S> original, OperationContext context, Q request) {
		log.entering(getClass().toString(), "execute");
		context.getAuthentication().checkThatAuthenticated();
		S result = original.execute(context, request);
		log.exiting(getClass().toString(), "execute");
		return result;
	}
}
