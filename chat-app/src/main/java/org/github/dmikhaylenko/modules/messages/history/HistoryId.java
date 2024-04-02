package org.github.dmikhaylenko.modules.messages.history;

import java.util.List;
import java.util.stream.Collectors;

import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.modules.PageSize;
import org.github.dmikhaylenko.modules.Pagination;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class HistoryId {
	private final Long currentUserId;
	private final Long userId;

	public Long getTotalMessages() {
		return Dao.historiesDao().getTotalMessages(currentUserId, userId);
	}

	public Long getLastPageNumber(PageSize pageSize) {
		return getTotalMessages() / pageSize.getPageSize() + 1L;
	}

	public List<MessageViewModel> findMessages(Pagination pagination) {
		return Dao.historiesDao().findMessages(currentUserId, userId, pagination).stream().map(MessageViewModel::new)
				.collect(Collectors.toList());
	}
}
