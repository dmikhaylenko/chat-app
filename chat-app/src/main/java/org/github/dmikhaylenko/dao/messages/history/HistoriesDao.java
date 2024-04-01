package org.github.dmikhaylenko.dao.messages.history;

import java.util.List;

import org.github.dmikhaylenko.dao.DBPaginate;
import org.github.dmikhaylenko.dao.messages.DBMessageView;

public interface HistoriesDao {

	List<DBHistory> findHistories(String token, DBPaginate pagination);

	Long countHistories(String token);

	Long countUnwatchedHistories(String token);

	void clearAllMessages(Long currentUserId, Long userId);

	Long getTotalMessages(Long currentUserId, Long userId);

	List<DBMessageView> findMessages(Long currentUserId, Long userId, DBPaginate pagination);

}