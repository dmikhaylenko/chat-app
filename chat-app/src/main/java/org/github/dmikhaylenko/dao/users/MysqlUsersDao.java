package org.github.dmikhaylenko.dao.users;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.DBPaginate;
import org.github.dmikhaylenko.dao.Database;
import org.github.dmikhaylenko.dao.Database.RowParsers;
import org.github.dmikhaylenko.time.Time;

@ApplicationScoped
public class MysqlUsersDao implements UsersDao {
	private final Database database;

	@Inject
	public MysqlUsersDao(Database database) {
		super();
		this.database = database;
	}

	private static final String FIND_BY_ID_QUERY = "SELECT * FROM USER WHERE ID = ?";

	@Override
	public Optional<DBUser> findById(Long userId) {
		return database.executeWithPreparedStatement(FIND_BY_ID_QUERY, (connection, statement) -> {
			statement.setLong(1, userId);
			return database.parseResultSetSingleRow(statement.executeQuery(), new DBUserRowParser());
		});
	}

	private static final String FIND_USER_BY_CREDENTIALS_QUERY = "SELECT CHK_CREDS(?, ?) AS USER_ID FROM DUAL";

	@Override
	public Optional<Long> findUserByCredentials(String phone, String password) {
		return database.executeWithPreparedStatement(FIND_USER_BY_CREDENTIALS_QUERY, (connection, statement) -> {
			statement.setString(1, phone);
			statement.setString(2, password);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper());
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

	@Override
	public List<DBUser> findByPhoneOrUsername(String searchString, DBPaginate pagination) {
		return database.executeWithPreparedStatement(FIND_BY_PHONE_OR_USERNAME_QUERY, (connection, statement) -> {
			statement.setString(1, searchString);
			statement.setString(2, searchString);
			statement.setLong(3, pagination.getPageSize());
			statement.setLong(4, pagination.getOffset());
			return database.parseResultSet(statement.executeQuery(), new DBUserRowParser());
		});
	}

	private static final String CHECK_EXISTS_BY_ID_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE ID = ?";

	@Override
	public boolean existsById(Long userId) {
		return database.executeWithPreparedStatement(CHECK_EXISTS_BY_ID_QUERY, (connection, statement) -> {
			statement.setLong(1, userId);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper()).get();
		});
	}

	private static final String CHECK_EXISTS_WITH_PHONE_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE PHONE = ?";

	@Override
	public boolean existsWithThePhone(String phone) {
		return database.executeWithPreparedStatement(CHECK_EXISTS_WITH_PHONE_QUERY, (connection, statement) -> {
			statement.setString(1, phone);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper()).get();
		});
	}

	private static final String CHECK_EXISTS_WITH_NICKNAME_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE USERNAME = ?";

	@Override
	public boolean existsWithTheNickname(String publicName) {
		return database.executeWithPreparedStatement(CHECK_EXISTS_WITH_NICKNAME_QUERY, (connection, statement) -> {
			statement.setString(1, publicName);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper()).get();
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

	@Override
	public Long countByPhoneOrUsername(String searchString) {
		return database.executeWithPreparedStatement(COUNT_BY_PHONE_OR_USERNAME_QUERY, (connection, statement) -> {
			statement.setString(1, searchString);
			statement.setString(2, searchString);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
		});
	}

	private static final String INSERT_TO_USER_TABLE_QUERY = "INSERT INTO USER(ID, PHONE, PASSWORD, USERNAME, AVATAR_HREF, LAST_AUTH) VALUES(?,?,?,?,?,?)";

	@Override
	public void insertToUserTable(DBUser dbUser) {
		database.executeWithPreparedStatement(INSERT_TO_USER_TABLE_QUERY, (connection, statement) -> {
			statement.setLong(1, dbUser.getId());
			statement.setString(2, dbUser.getPhone());
			statement.setString(3, dbUser.getPassword());
			statement.setString(4, dbUser.getUsername());
			statement.setString(5, dbUser.getAvatarHref());
			statement.setTimestamp(6, Time.createTimestamp(dbUser.getLastAuth()));
			statement.executeUpdate();
			return null;
		});
	}

	private static final String UPDATE_INTO_USER_TABLE_QUERY = "UPDATE USER SET PHONE=?, PASSWORD=?, USERNAME=?, AVATAR_HREF=?, LAST_AUTH=? WHERE ID=?";

	@Override
	public void updateIntoUserTable(DBUser dbUser) {
		database.executeWithPreparedStatement(UPDATE_INTO_USER_TABLE_QUERY, (connection, statement) -> {
			statement.setString(1, dbUser.getPhone());
			statement.setString(2, dbUser.getPassword());
			statement.setString(3, dbUser.getUsername());
			statement.setString(4, dbUser.getAvatarHref());
			statement.setTimestamp(5, Time.createTimestamp(dbUser.getLastAuth()));
			statement.setLong(6, dbUser.getId());
			statement.executeUpdate();
			return null;
		});
	}
}
