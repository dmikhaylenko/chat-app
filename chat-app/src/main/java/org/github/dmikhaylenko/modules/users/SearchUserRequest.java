package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.model.pagination.Pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchUserRequest {
	@Getter
	private final String searchString;
	private final Long pageNumber;
	private final Long pageString;

	public Pagination getPagination() {
		return Pagination.of(pageNumber, pageString).defaults(1L, 1000L, 50L);
	}
}
