package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.modules.Pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchUserRequest implements SearchUserQuery {
	@Getter
	private final String searchString;
	private final Long pageNumber;
	private final Long pageString;

	@Override
	public Pagination getPagination() {
		return Pagination.of(pageNumber, pageString).defaults(1L, 1000L, 50L);
	}
}
