package org.github.dmikhaylenko.operations;

import org.github.dmikhaylenko.time.Timezone;

import lombok.extern.java.Log;

@Log
public abstract class GenericOperation<Q, S> implements Operation<Q, S> {
	private Operation<Q, S> internalExecutor = new InternalOperationExecutor();

	protected GenericOperation() {
		this(configurer -> {
		});
	}

	protected GenericOperation(OperationConfigurationFunction<Q, S> configurationFunction) {
		super();
		configurationFunction.configure(new InternalOperationConfigurer());
	}

	@Override
	public final S execute(OperationContext context, Q request) {
		log.entering(getClass().toString(), "execute");
		log.info(String.format("Run operation: %s.execute(context, request)", getClass().toString()));
		S result = internalExecutor.execute(context, request);
		log.exiting(getClass().toString(), "execute");
		return result;
	}

	protected abstract S executeOperation(OperationContext context, Q request);

	private class InternalOperationExecutor implements Operation<Q, S> {
		@Override
		public S execute(OperationContext context, Q request) {
			Timezone.loadZoneOffset(context.getZoneOffset());
			return executeOperation(context, request);
		}
	}

	private class InternalOperationConfigurer implements OperationConfigurer<Q, S> {
		@Override
		public OperationConfigurer<Q, S> decorate(OperationDecorator<Q, S> decorator) {
			internalExecutor = internalExecutor.decorate(decorator);
			return this;
		}
	}

	protected static interface OperationConfigurer<Q, S> {
		OperationConfigurer<Q, S> decorate(OperationDecorator<Q, S> decorator);
	}

	protected static interface OperationConfigurationFunction<Q, S> {
		void configure(OperationConfigurer<Q, S> configurer);
	}
}
