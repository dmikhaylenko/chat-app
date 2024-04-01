package org.github.dmikhaylenko.operations;

public interface Operation<Q, S> {
	S execute(OperationContext context, Q request);
	
	default Operation<Q, S> decorate(OperationDecorator<Q, S> decorator) {
		Operation<Q, S> original = this;
		return (context, request) -> {
			return decorator.execute(original, context, request);
		};
	}
	
	public interface OperationDecorator<Q, S> {
		S execute(Operation<Q, S> original, OperationContext context, Q request);
	}
}
