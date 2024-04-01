package org.github.dmikhaylenko.modules.messages;

import java.util.Optional;

import org.github.dmikhaylenko.dao.Dao;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class MessageIdModel {
	private final Long messageId;

	public Long unwrap() {
		return messageId;
	}

	private Optional<Long> getMessageId() {
		return Optional.ofNullable(messageId);
	}

	public MessageModel getById() {
		return findById().orElseThrow(MissingRequestedMessageException::new);
	}

	public Optional<MessageModel> findById() {
		return getMessageId().flatMap(Dao.messagesDao()::findById).map(MessageModel::new);
	}

}
