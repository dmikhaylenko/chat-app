package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.modules.Pagination;

public interface SearchUserQuery {

	String getSearchString();

	Pagination getPagination();

}