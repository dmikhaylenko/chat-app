package org.github.dmikhaylenko.dao;

public interface DBPaginate {
	long getPageNumber();

	long getPageSize();

	long getOffset();
}