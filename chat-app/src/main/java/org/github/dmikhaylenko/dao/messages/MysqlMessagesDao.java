package org.github.dmikhaylenko.dao.messages;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.Database;
import org.github.dmikhaylenko.time.Time;

@ApplicationScoped
public class MysqlMessagesDao implements MessagesDao {
	private final Database database;

	@Inject
	public MysqlMessagesDao(Database database) {
		super();
		this.database = database;
	}

	// @formatter:off
	private static final String FIND_MESSAGE_BY_ID_QUERY = "SELECT * FROM MESSAGE WHERE ID = ?";
	// @formatter:on

	@Override
	public Optional<DBMessage> findById(Long id) {
		return database.executeWithPreparedStatement(FIND_MESSAGE_BY_ID_QUERY, (connection, statement) -> {
			statement.setLong(1, id);
			return database.parseResultSetSingleRow(statement.executeQuery(), new DBMessageRowParser());
		});
	}

	// @formatter:off
	private static final String INSERT_MESSAGE_QUERY = "INSERT INTO \r\n"
		+ "    MESSAGE(ID, SRC_ID, DEST_ID, MESSAGE, WATCHED, POSTED) \r\n"
		+ "VALUES \r\n"
		+ "    (?,?,?,?,?,?)";
	// @formatter:on

	@Override
	public void insertIntoMessageTable(DBMessage message) {
		database.executeWithPreparedStatement(INSERT_MESSAGE_QUERY, (connection, statement) -> {
			statement.setLong(1, message.getId());
			statement.setLong(2, message.getSrcId());
			statement.setLong(3, message.getDestId());
			statement.setString(4, message.getMessage());
			statement.setBoolean(5, message.getWatched());
			statement.setTimestamp(6, Time.createTimestamp(message.getPosted()));
			statement.executeUpdate();
			return null;
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

	@Override
	public void updateIntoMessageTable(DBMessage message) {
		database.executeWithPreparedStatement(UPDATE_MESSAGE_QUERY, (connection, statement) -> {
			statement.setLong(1, message.getSrcId());
			statement.setLong(2, message.getDestId());
			statement.setString(3, message.getMessage());
			statement.setBoolean(4, message.getWatched());
			statement.setTimestamp(5, Time.createTimestamp(message.getPosted()));
			statement.setLong(6, message.getId());
			statement.executeUpdate();
			return null;
		});
	}

	// @formatter:off
	private static final String DELETE_MESSAGE_QUERY = "DELETE FROM MESSAGE WHERE ID = ?";
	// @formatter:on

	@Override
	public void deleteFromMessageTable(Long id) {
		database.executeWithPreparedStatement(DELETE_MESSAGE_QUERY, (connection, statement) -> {
			statement.setLong(1, id);
			statement.executeUpdate();
			return null;
		});
	}
}
