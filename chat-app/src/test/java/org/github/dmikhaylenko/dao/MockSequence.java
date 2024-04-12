package org.github.dmikhaylenko.dao;

import java.util.concurrent.atomic.AtomicLong;

public abstract class MockSequence implements Sequence<Long>, SequenceConfigurer {
	private final AtomicLong sequence = new AtomicLong(0L);

	@Override
	public final Long nextValue() {
		return sequence.incrementAndGet();
	}
	
	@Override
	public final void initializeBy(Long value) {
		sequence.set(value);
	}

	@Override
	public Long currentValue() {
		return sequence.get();
	}
	
	
}
