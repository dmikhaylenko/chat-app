package org.github.dmikhaylenko.dao;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface DataSet<D> {
	Set<D> getData();

	default void load(Stream<D> data) {
		load(data.collect(Collectors.toSet()));
	}

	void load(Set<D> data);

	void clear();
}
