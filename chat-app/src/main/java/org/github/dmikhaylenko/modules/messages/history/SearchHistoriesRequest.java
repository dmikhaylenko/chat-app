package org.github.dmikhaylenko.modules.messages.history;

import org.github.dmikhaylenko.modules.Pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchHistoriesRequest {
	private final Long pageNumber;
	private final Long pageString;

	public Pagination getPagination() {
		return Pagination.of(pageNumber, pageString).defaults(1L, 500L, 500L);
	}
}
