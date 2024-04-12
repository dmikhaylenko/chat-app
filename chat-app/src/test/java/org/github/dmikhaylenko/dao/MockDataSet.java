package org.github.dmikhaylenko.dao;

import java.util.HashSet;
import java.util.Set;

public class MockDataSet<D> extends HashSet<D> implements DataSet<D> {
	private static final long serialVersionUID = -8828886704282187339L;

	@Override
	public Set<D> getData() {
		return this;
	}

	@Override
	public void load(Set<D> data) {
		addAll(data);
	}
}
