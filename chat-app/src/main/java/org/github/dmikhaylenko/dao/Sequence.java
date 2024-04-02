package org.github.dmikhaylenko.dao;

public interface Sequence<I> {
	I nextValue();
}
