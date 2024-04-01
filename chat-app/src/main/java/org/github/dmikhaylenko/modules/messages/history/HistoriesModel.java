package org.github.dmikhaylenko.modules.messages.history;

import java.util.List;
import java.util.stream.Collectors;

import org.github.dmikhaylenko.auth.AuthToken;
import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.modules.Pagination;
import org.github.dmikhaylenko.modules.users.UserIdModel;

public class HistoriesModel {
	public List<HistoryModel> findHistories(AuthToken token, Pagination pagination) {
		return Dao.historiesDao().findHistories(token.getToken(), pagination).stream().map(HistoryModel::new)
				.collect(Collectors.toList());
	}

	public Long countHistories(AuthToken token) {
		return Dao.historiesDao().countHistories(token.getToken());
	}

	public Long countUnwatchedHistories(AuthToken token) {
		return Dao.historiesDao().countUnwatchedHistories(token.getToken());
	}

	public void clearAllMessages(Long currentUserId, UserIdModel userId) {
		Dao.historiesDao().clearAllMessages(currentUserId, userId.unwrap());
	}
}
