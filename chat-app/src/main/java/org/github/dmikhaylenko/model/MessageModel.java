package org.github.dmikhaylenko.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.errors.ItIsForbiddenToDeleteForeignUsersMessagesException;
import org.github.dmikhaylenko.errors.ItIsForbiddenToEditForeignUsersMessagesException;
import org.github.dmikhaylenko.errors.MissingRequestedMessageException;
import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.utils.Resources;
import org.github.dmikhaylenko.utils.TimeUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageModel {
	@Null
	@XmlElement
	private Long id;

	@Null
	@XmlElement
	private Long srcId;

	@Null
	@XmlElement
	private Long destId;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 500)
	private String messageText;

	@Null
	@XmlElement
	private Boolean watched;

	@Null
	@XmlElement
	private LocalDateTime posted;

	public static MessageModel getById(Long id) {
		return findById(id).orElseThrow(MissingRequestedMessageException::new);
	}

	public Long writeMessage(AuthTokenModel authToken, Long userId) {
		UserModel.checkThatRequestedUserExits(userId);
		this.srcId = authToken.getAuthenticatedUser();
		this.destId = userId;
		return insertIntoMessageTable();
	}

	public void editMessage(AuthTokenModel authToken, EditMessageModel command) {
		checkMessageEditingAvailabilityForUser(authToken.getAuthenticatedUser());
		this.messageText = command.getMessageText();
		updateIntoMessageTable();
	}

	public void deleteMessage(AuthTokenModel authToken) {
		checkMessageDeleteAvailabilityForUser(authToken.getAuthenticatedUser());
		deleteFromMessageTable();
	}

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
	
	// @formatter:off
	private static final String FIND_MESSAGE_BY_ID_QUERY = "SELECT * FROM MESSAGE WHERE ID = ?";
	// @formatter:on

	private static Optional<MessageModel> findById(Long id) {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), FIND_MESSAGE_BY_ID_QUERY,
				(connection, statement) -> {
					statement.setLong(1, id);
					return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), new MessageModelRowParser());
				});
	}
	
	// @formatter:off
	private static final String INSERT_MESSAGE_QUERY = "INSERT INTO \r\n"
			+ "    MESSAGE(SRC_ID, DEST_ID, MESSAGE) \r\n"
			+ "VALUES \r\n"
			+ "    (?, ?, ?)";
	// @formatter:on

	private Long insertIntoMessageTable() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), INSERT_MESSAGE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setLong(1, srcId);
					statement.setLong(2, destId);
					statement.setString(3, messageText);
					statement.executeUpdate();
					Long id = DatabaseUtils.lastInsertedId(connection).get();
					connection.commit();
					return id;
				});
	}

	// @formatter:off
	private static final String UPDATE_MESSAGE_QUERY = "UPDATE \r\n"
			+ "    MESSAGE \r\n"
			+ "SET \r\n"
			+ "    SRC_ID = ?,\r\n"
			+ "    DEST_ID = ?,\r\n"
			+ "    MESSAGE = ?,\r\n"
			+ "    WATCHED = ?,\r\n"
			+ "    POSTED = ?\r\n"
			+ "WHERE \r\n"
			+ "    ID = ?";
	// @formatter:on

	private void updateIntoMessageTable() {
		DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), UPDATE_MESSAGE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setLong(1, srcId);
					statement.setLong(2, destId);
					statement.setString(3, messageText);
					statement.setBoolean(4, watched);
					statement.setTimestamp(5, TimeUtils.createTimestamp(posted));
					statement.setLong(6, id);
					statement.executeUpdate();
					connection.commit();
					return null;
				});
	}

	// @formatter:off
	private static final String DELETE_MESSAGE_QUERY = "DELETE FROM MESSAGE WHERE ID = ?";
	// @formatter:on

	private void deleteFromMessageTable() {
		DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), DELETE_MESSAGE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setLong(1, id);
					statement.executeUpdate();
					connection.commit();
					return null;
				});
	}

	private static class MessageModelRowParser implements RsRowParser<MessageModel> {
		// @formatter:off
		@Override
		public MessageModel parseRow(ResultSet resultSet) throws SQLException {
			return MessageModel.builder()
					.id(resultSet.getLong("ID"))
					.srcId(resultSet.getLong("SRC_ID"))
					.destId(resultSet.getLong("DEST_ID"))
					.messageText(resultSet.getString("MESSAGE"))
					.watched(resultSet.getBoolean("WATCHED"))
					.posted(TimeUtils.createLocalDateTime(resultSet.getTimestamp("POSTED")))
					.build();
		}
		// @formatter:on
	}
}
