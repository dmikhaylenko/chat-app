package org.github.dmikhaylenko.modules.messages;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.auth.AuthToken;
import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.dao.messages.DBMessage;
import org.github.dmikhaylenko.modules.messages.history.PostMessageCommand;
import org.github.dmikhaylenko.time.Time;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@XmlRootElement
@EqualsAndHashCode
public class MessageModel {
	private Long id;
	private Long srcId;
	private Long destId;
	private String messageText;
	private Boolean watched;
	private LocalDateTime posted;

	public MessageModel(AuthToken authToken, PostMessageCommand request) {
		super();
		this.id = Dao.messageIdSequence().nextValue();
		this.srcId = authToken.getAuthenticatedUser();
		this.destId = request.getReceiverId().unwrap();
		this.messageText = request.getMessageContent().getMessageText();
		this.posted = Time.currentLocalDateTime();
		this.watched = false;
	}

	public MessageModel(DBMessage message) {
		super();
		this.id = message.getId();
		this.srcId = message.getSrcId();
		this.destId = message.getDestId();
		this.messageText = message.getMessage();
		this.watched = message.getWatched();
		this.posted = message.getPosted();
	}

	public Long writeMessage() {
		Dao.messagesDao().insertIntoMessageTable(createDBMessage());
		return id;
	}

	public void editMessage(AuthToken authToken, EditMessageCommand command) {
		checkMessageEditingAvailabilityForUser(authToken.getAuthenticatedUser());
		this.messageText = command.getMessageContent().getMessageText();
		Dao.messagesDao().updateIntoMessageTable(createDBMessage());
	}

	public void deleteMessage(AuthToken authToken) {
		checkMessageDeleteAvailabilityForUser(authToken.getAuthenticatedUser());
		Dao.messagesDao().deleteFromMessageTable(id);
	}

	// @formatter:off
	private DBMessage createDBMessage() {
		return DBMessage.builder()
				.id(id)
				.srcId(srcId)
				.destId(destId)
				.message(messageText)
				.watched(watched)
				.posted(posted)
				.build();
	}
	// @formatter:on

	private void checkMessageEditingAvailabilityForUser(Long userId) {
		if (!userId.equals(getSrcId())) {
			throw new ItIsForbiddenToEditForeignUsersMessagesException();
		}
	}

	private void checkMessageDeleteAvailabilityForUser(Long userId) {
		if (!userId.equals(getSrcId())) {
			throw new ItIsForbiddenToDeleteForeignUsersMessagesException();
		}
	}
}
