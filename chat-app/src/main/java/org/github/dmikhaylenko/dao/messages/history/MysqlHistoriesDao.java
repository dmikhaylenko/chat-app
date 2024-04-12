package org.github.dmikhaylenko.dao.messages.history;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.DBPaginate;
import org.github.dmikhaylenko.dao.Database;
import org.github.dmikhaylenko.dao.Database.RowParsers;

@ApplicationScoped
public class MysqlHistoriesDao implements HistoriesDao {
	private final Database database;
	
	@Inject
	public MysqlHistoriesDao(Database database) {
		super();
		this.database = database;
	}

	// @formatter:off
	private static final String FIND_HISTORIES_QUERY = "SELECT \r\n"
			+ "    OPPONENT_ID AS ID,\r\n"
			+ "    OPPONENT_NAME AS NAME,\r\n"
			+ "    OPPONENT_HREF AS AVATAR_HREF,\r\n"
			+ "    OPPONENT_LAST_AUTH AS LAST_AUTH,\r\n"
			+ "    IS_ONLINE(OPPONENT_LAST_AUTH) AS ONLINE,\r\n"
			+ "    UNWATCHED\r\n"
			+ "FROM\r\n"
			+ "    VW_MESSAGE_HISTORY\r\n"
			+ "WHERE\r\n"
			+ "    TOKEN = ?\r\n"
			+ "LIMIT ?\r\n"
			+ "OFFSET ?";
	// @formatter:on

	@Override
	public List<DBHistory> findHistories(String token, DBPaginate pagination) {
		return database.executeWithPreparedStatement(FIND_HISTORIES_QUERY, (connection, statement) -> {
			statement.setString(1, token);
			statement.setLong(2, pagination.getPageSize());
			statement.setLong(3, pagination.getOffset());
			return database.parseResultSet(statement.executeQuery(), new DBHistoryModelRowParser());
		});
	}

	// @formatter:off
	private static final String COUNT_HISTORIES_QUERY = "SELECT \r\n"
			+ "    COUNT(*)\r\n"
			+ "FROM\r\n"
			+ "    VW_MESSAGE_HISTORY\r\n"
			+ "WHERE\r\n"
			+ "    TOKEN = ?";
	// @formatter:on

	@Override
	public Long countHistories(String token) {
		return database.executeWithPreparedStatement(COUNT_HISTORIES_QUERY, (connection, statement) -> {
			statement.setString(1, token);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
		});
	}

	// @formatter:off
	private static final String COUNT_UNWATCHED_HISTORIES_QUERY = "SELECT\r\n"
			+ "    SUM(COALESCE(UW.UNWATCHED, 0)) \r\n"
			+ "FROM\r\n"
			+ "    USER U\r\n"
			+ "    LEFT JOIN VW_UNWATCHED_MESSAGES UW\r\n"
			+ "        ON U.ID = UW.USER_ID\r\n"
			+ "    LEFT JOIN AUTH A\r\n"
			+ "        ON U.ID = A.USER_ID\r\n"
			+ "WHERE\r\n"
			+ "    A.TOKEN = ?\r\n"
			+ "GROUP BY\r\n"
			+ "    U.ID";
	// @formatter:on

	@Override
	public Long countUnwatchedHistories(String token) {
		return database.executeWithPreparedStatement(COUNT_UNWATCHED_HISTORIES_QUERY, (connection, statement) -> {
			statement.setString(1, token);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
		});
	}

	// @formatter:off
	private static final String CLEAR_ALL_MESSAGES_QUERY = "DELETE FROM \r\n"
			+ "    MESSAGE \r\n"
			+ "WHERE\r\n"
			+ "    (SRC_ID = ? AND DEST_ID = ?) OR\r\n"
			+ "    (SRC_ID = ? AND DEST_ID = ?)";
	// @formatter:on

	@Override
	public void clearAllMessages(Long currentUserId, Long userId) {
		database.executeWithPreparedStatement(CLEAR_ALL_MESSAGES_QUERY, (connection, statement) -> {
			statement.setLong(1, currentUserId);
			statement.setLong(2, userId);
			statement.setLong(3, userId);
			statement.setLong(4, currentUserId);
			statement.executeUpdate();
			return null;
		});
	}

	// @formatter:off
		private static final String GET_TOTAL_MESSAGES_QUERY = "SELECT\r\n"
				+ "    COUNT(*) AS TOTAL\r\n"
				+ "FROM\r\n"
				+ "    VW_MESSAGE MSG\r\n"
				+ "    LEFT JOIN USER U\r\n"
				+ "        ON MSG.MSG_AUTHOR = U.ID\r\n"
				+ "WHERE\r\n"
				+ "    MSG.OPPONENT_ID = ? AND\r\n"
				+ "    MSG.USER_ID = ?";
		// @formatter:on

	@Override
	public Long getTotalMessages(Long currentUserId, Long userId) {
		return database.executeWithPreparedStatement(GET_TOTAL_MESSAGES_QUERY, (connection, statement) -> {
			statement.setLong(1, userId);
			statement.setLong(2, currentUserId);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
		});
	}

	// @formatter:off
		private static final String FIND_MESSAGES_QUERY = "SELECT\r\n"
				+ "    MSG.MESSAGE_ID AS ID,\r\n"
				+ "    U.USERNAME AS AUTHOR_NAME,\r\n"
				+ "    U.AVATAR_HREF AS AUTHOR_AVATAR,\r\n"
				+ "    MSG.MESSAGE_BODY AS MESSAGE,\r\n"
				+ "    MSG.MSG_WATCHED AS WATCHED,\r\n"
				+ "    MSG.MSG_POSTED AS POSTED\r\n"
				+ "FROM\r\n"
				+ "    VW_MESSAGE MSG\r\n"
				+ "    LEFT JOIN USER U\r\n"
				+ "        ON MSG.MSG_AUTHOR = U.ID\r\n"
				+ "WHERE\r\n"
				+ "    MSG.OPPONENT_ID = ? AND\r\n"
				+ "    MSG.USER_ID = ?\r\n"
				+ "LIMIT ?\r\n"
				+ "OFFSET ?";
		// @formatter:on

	@Override
	public List<DBMessageView> findMessages(Long currentUserId, Long userId, DBPaginate pagination) {
		return database.executeWithPreparedStatement(FIND_MESSAGES_QUERY, (connection, statement) -> {
			statement.setLong(1, userId);
			statement.setLong(2, currentUserId);
			statement.setLong(3, pagination.getPageSize());
			statement.setLong(4, pagination.getOffset());
			return database.parseResultSet(statement.executeQuery(), new DBMessageViewModelRowParser());
		});
	}
}
