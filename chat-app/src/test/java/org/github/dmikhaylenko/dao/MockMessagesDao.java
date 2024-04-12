package org.github.dmikhaylenko.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.github.dmikhaylenko.dao.messages.DBMessage;
import org.github.dmikhaylenko.dao.messages.MessagesDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockMessagesDao implements MessagesDao {
	private final MockDataSet<DBMessage> dataSet;

	@Override
	public Optional<DBMessage> findById(Long id) {
		// @formatter:off
		return dataSet.stream()
				.filter(item -> id.equals(item.getId()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public void insertIntoMessageTable(DBMessage message) {
		dataSet.add(message);
	}

	@Override
	public void updateIntoMessageTable(DBMessage message) {
		deleteFromMessageTable(message.getId());
		insertIntoMessageTable(message);
	}

	@Override
	public void deleteFromMessageTable(Long id) {
		dataSet.remove(findById(id).get());
	}

	public void clearMessagesBetween(Long currentUserId, Long userId) {
		dataSet.removeAll(findMessagesBetween(currentUserId, userId));
	}

	public Set<Long> findUsersIHaveChattedWith(Long currentUserId) {
		return dataSet.stream().filter(message -> {
			// @formatter:off
			return currentUserId.equals(message.getSrcId()) || 
					currentUserId.equals(message.getDestId());
			// @formatter:on
		}).map(message -> {
			if (currentUserId.equals(message.getDestId())) {
				return message.getSrcId();
			} else {
				return message.getDestId();
			}
		}).collect(Collectors.toSet());
	}

	public Map<Long, List<DBMessage>> findMessagesBetweenAll(Long currentUserId, Collection<Long> usersIds) {
		Map<Long, List<DBMessage>> result = new LinkedHashMap<Long, List<DBMessage>>();
		usersIds.forEach(userId -> {
			// @formatter:off
			result
				.computeIfAbsent(userId, k -> new ArrayList<>())
				.addAll(findMessagesBetween(currentUserId, userId));
			// @formatter:on
		});
		return result;
	}

	private List<DBMessage> findMessagesBetween(Long currentUserId, Long userId) {
		return dataSet.stream().filter(message -> {
			// @formatter:off
			return currentUserId.equals(message.getSrcId()) ||
					currentUserId.equals(message.getDestId()) ||
					userId.equals(message.getSrcId()) ||
					userId.equals(message.getDestId());
			// @formatter:on
		}).collect(Collectors.toList());
	}
}
