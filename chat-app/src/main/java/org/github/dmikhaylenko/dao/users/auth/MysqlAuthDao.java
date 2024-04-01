package org.github.dmikhaylenko.dao.users.auth;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.Database;
import org.github.dmikhaylenko.dao.Database.RowParsers;

@ApplicationScoped
public class MysqlAuthDao implements AuthDao {
	private final Database database;

	@Inject
	public MysqlAuthDao(Database database) {
		super();
		this.database = database;
	}

	private static final String CALL_LOGIN_QUERY = "SELECT LOGIN(?, ?) AS TOKEN FROM DUAL";

	@Override
	public Optional<String> executeLogin(String username, String password) {
		return database.executeWithPreparedStatement(CALL_LOGIN_QUERY, (connection, statement) -> {
			statement.setString(1, username);
			statement.setString(2, password);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.stringValueRowMapper());
		});
	}

	private static final String CALL_LOGOUT_PROCEDURE = "{CALL LOGOUT(?)}";

	@Override
	public void executeLogout(String token) {
		database.executeWithCallStatement(CALL_LOGOUT_PROCEDURE, (connection, statement) -> {
			statement.setString(1, token);
			statement.execute();
			return null;
		});
	}

	private static final String CHECK_AUTHENTICATED_QUERY = "SELECT AUTHENTICATE(?) FROM DUAL";

	@Override
	public boolean isAuthenticated(String token) {
		return database.executeWithPreparedStatement(CHECK_AUTHENTICATED_QUERY, (connection, statement) -> {
			statement.setString(1, token);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper()).get();
		});
	}

	private static final String GET_AUTHENTICATED_USER_QUERY = "SELECT USER_ID FROM AUTH WHERE TOKEN = ?";

	@Override
	public Long getAuthenticatedUser(String token) {
		return database.executeWithPreparedStatement(GET_AUTHENTICATED_USER_QUERY, (connection, statement) -> {
			statement.setString(1, token);
			return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
		});
	}
}
