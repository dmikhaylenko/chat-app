package org.github.dmikhaylenko.dao;

import java.util.List;
import java.util.Optional;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.commons.time.TimeUtils;

public class UserDao {
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM USER WHERE ID = ?";

	public Optional<DBUser> findById(Long userId) {
		return DatabaseUtils.executeWithPreparedStatement(FIND_BY_ID_QUERY, (connection, statement) -> {
			statement.setLong(1, userId);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), new DBUserRowParser());
		});
	}

	private static final String FIND_USER_BY_CREDENTIALS_QUERY = "SELECT CHK_CREDS(?, ?) AS USER_ID FROM DUAL";

	public Optional<Long> findUserByCredentials(String phone, String password) {
		return DatabaseUtils.executeWithPreparedStatement(FIND_USER_BY_CREDENTIALS_QUERY, (connection, statement) -> {
			statement.setString(1, phone);
			statement.setString(2, password);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper());
		});
	}
	
	// @formatter:off
	private static final String FIND_BY_PHONE_OR_USERNAME_QUERY = 
			"SELECT \r\n"
			+ "    ID, AVATAR_HREF, PHONE, USERNAME, NULL AS PASSWORD, NULL AS LAST_AUTH \r\n" 
			+ "FROM\r\n" 
			+ "    USER\r\n" 
			+ "WHERE\r\n"
			+ "    LOWER(PHONE) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))),'%') OR\r\n"
			+ "    LOWER(USERNAME) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))),'%')\r\n" 
			+ "LIMIT ?\r\n" 
			+ "OFFSET ?";
	// @formatter:on

	public List<DBUser> findByPhoneOrUsername(String searchString, DBPaginate pagination) {
		return DatabaseUtils.executeWithPreparedStatement(FIND_BY_PHONE_OR_USERNAME_QUERY, (connection, statement) -> {
			statement.setString(1, searchString);
			statement.setString(2, searchString);
			statement.setLong(3, pagination.getPageSize());
			statement.setLong(4, pagination.getOffset());
			return DatabaseUtils.parseResultSet(statement.executeQuery(), new DBUserRowParser());
		});
	}

	private static final String CHECK_EXISTS_BY_ID_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE ID = ?";

	public boolean existsById(Long userId) {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_BY_ID_QUERY, (connection, statement) -> {
			statement.setLong(1, userId);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
					.get();
		});
	}
	
	private static final String CHECK_EXISTS_WITH_PHONE_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE PHONE = ?";

	public boolean existsWithThePhone(String phone) {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_WITH_PHONE_QUERY,
				(connection, statement) -> {
					statement.setString(1, phone);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String CHECK_EXISTS_WITH_NICKNAME_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE USERNAME = ?";

	public boolean existsWithTheNickname(String publicName) {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_WITH_NICKNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, publicName);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}
	
	// @formatter:off
	private static final String COUNT_BY_PHONE_OR_USERNAME_QUERY = 
			"SELECT \r\n" 
	        + "    COUNT(*) AS TOTAL\r\n"
			+ "FROM\r\n" 
	        + "    USER\r\n" 
			+ "WHERE\r\n"
			+ "    LOWER(PHONE) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))), '%') OR\r\n"
			+ "    LOWER(USERNAME) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))), '%')";
	// @formatter:on

	public Long countByPhoneOrUsername(String searchString) {
		return DatabaseUtils.executeWithPreparedStatement(COUNT_BY_PHONE_OR_USERNAME_QUERY, (connection, statement) -> {
			statement.setString(1, searchString);
			statement.setString(2, searchString);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper())
					.get();
		});
	}
	
	private static final String INSERT_TO_USER_TABLE_QUERY = "INSERT INTO USER(PHONE, PASSWORD, USERNAME, AVATAR_HREF, LAST_AUTH) VALUES(?,?,?,?,?)";

	public Long insertToUserTable(DBUser dbUser) {
		return DatabaseUtils.executeWithPreparedStatement(INSERT_TO_USER_TABLE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, dbUser.getPhone());
					statement.setString(2, dbUser.getPassword());
					statement.setString(3, dbUser.getUsername());
					statement.setString(4, dbUser.getAvatarHref());
					statement.setTimestamp(5, TimeUtils.createTimestamp(dbUser.getLastAuth()));
					statement.executeUpdate();
					Long id = DatabaseUtils.lastInsertedId(connection).get();
					connection.commit();
					return id;
				});
	}

	private static final String UPDATE_INTO_USER_TABLE_QUERY = "UPDATE USER SET PHONE=?, PASSWORD=?, USERNAME=?, AVATAR_HREF=?, LAST_AUTH=? WHERE ID=?";

	public void updateIntoUserTable(DBUser dbUser) {
		DatabaseUtils.executeWithPreparedStatement(UPDATE_INTO_USER_TABLE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, dbUser.getPhone());
					statement.setString(2, dbUser.getPassword());
					statement.setString(3, dbUser.getUsername());
					statement.setString(4, dbUser.getAvatarHref());
					statement.setTimestamp(5, TimeUtils.createTimestamp(dbUser.getLastAuth()));
					statement.setLong(6, dbUser.getId());
					statement.executeUpdate();
					connection.commit();
					return null;
				});
	}
}
