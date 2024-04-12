package org.github.dmikhaylenko.dao;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.github.dmikhaylenko.dao.messages.DBMessage;
import org.github.dmikhaylenko.dao.messages.history.DBHistory;
import org.github.dmikhaylenko.dao.messages.history.DBMessageView;
import org.github.dmikhaylenko.dao.messages.history.HistoriesDao;
import org.github.dmikhaylenko.time.Time;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockHistoriesDao implements HistoriesDao {
	private final MockAuthDao authDao;
	private final MockContactsDao contactsDao;
	private final MockMessagesDao messagesDao;

	@Override
	public List<DBHistory> findHistories(String token, DBPaginate pagination) {
		// @formatter:off
		return authDao.findUserByToken(token).map(authenicatedUser -> {
			Map<Long, List<DBMessage>> allHistories = findAllHistories(authenicatedUser.getId());
			return allHistories.keySet().stream()
				.map(historyId -> {
					return DBHistory.builder()
							.id(authenicatedUser.getId())
							.publicName(authenicatedUser.getUsername())
							.avatar(authenicatedUser.getAvatarHref())
							.online(isOnline(authenicatedUser.getLastAuth()))
							.lastAccess(authenicatedUser.getLastAuth())
							.unwatched(countUnwatched(allHistories.get(historyId)))
							.build();
				}).collect(Collectors.<DBHistory>toList());
		}).orElse(Collections.emptyList());
		// @formatter:on
	}

	@Override
	public Long countHistories(String token) {
		return authDao.findUserByToken(token).map(authenicatedUser -> {
			return (long) findAllHistories(authenicatedUser.getId()).size();
		}).orElse(0L);
	}

	@Override
	public Long countUnwatchedHistories(String token) {
		return authDao.findUserByToken(token).map(authenticatedUser -> {
			Map<Long, List<DBMessage>> allHistories = findAllHistories(authenticatedUser.getId());
			// @formatter:off
			List<DBMessage> mergedMessages = allHistories.values().stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());
			// @formatter:on
			return countUnwatched(mergedMessages);
		}).orElse(0L);
	}

	@Override
	public void clearAllMessages(Long currentUserId, Long userId) {
		messagesDao.clearMessagesBetween(currentUserId, userId);
	}

	@Override
	public Long getTotalMessages(Long currentUserId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DBMessageView> findMessages(Long currentUserId, Long userId, DBPaginate pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	private long countUnwatched(List<DBMessage> messages) {
		// @formatter:off
		return messages.stream()
			.filter(message -> !message.getWatched())
			.count();
		// @formatter:on
	}

	private boolean isOnline(LocalDateTime lastAuth) {
		return Optional.ofNullable(lastAuth).map(ldt -> {
			return ldt.plusHours(1).isBefore(Time.currentLocalDateTime());
		}).orElse(false);
	}

	private Map<Long, List<DBMessage>> findAllHistories(Long currentUserId) {
		Set<Long> result = new LinkedHashSet<Long>(messagesDao.findUsersIHaveChattedWith(currentUserId));
		result.addAll(contactsDao.findAllMyContacts(currentUserId));
		return messagesDao.findMessagesBetweenAll(currentUserId, result);
	}

}
