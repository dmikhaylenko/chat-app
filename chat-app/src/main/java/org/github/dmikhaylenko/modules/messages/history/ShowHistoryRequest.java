package org.github.dmikhaylenko.modules.messages.history;

import org.github.dmikhaylenko.modules.Pagination;
import org.github.dmikhaylenko.modules.Pagination.DefaultPageNumberCalculator;
import org.github.dmikhaylenko.modules.users.UserIdModel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowHistoryRequest implements ShowHistoryCommand {
	private final Long userId;
	private final Long pageNumber;
	private final Long pageSize;

	@Override
	public UserIdModel getUserId() {
		return new UserIdModel(userId);
	}

	@Override
	public Pagination getPagination(DefaultPageNumberCalculator pageNumberCalculator) {
		return Pagination.of(pageNumber, pageSize).pageSizeDefaults(500, 50).calculateDefaultPageNumber(pageNumberCalculator);
	}
}
