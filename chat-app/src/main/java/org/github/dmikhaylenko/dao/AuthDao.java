package org.github.dmikhaylenko.dao;

import java.util.Optional;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;

public class AuthDao {
	private static final String CALL_LOGIN_QUERY = "SELECT LOGIN(?, ?) AS TOKEN FROM DUAL";

	public Optional<String> executeLogin(String username, String password) {
		return DatabaseUtils.executeWithPreparedStatement(CALL_LOGIN_QUERY, (connection, statement) -> {
			statement.setString(1, username);
			statement.setString(2, password);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.stringValueRowMapper());
		});
	}

	private static final String CALL_LOGOUT_PROCEDURE = "{CALL LOGOUT(?)}";

	public void executeLogout(String token) {
		DatabaseUtils.executeWithCallStatement(CALL_LOGOUT_PROCEDURE, (connection, statement) -> {
			connection.setAutoCommit(false);
			statement.setString(1, token);
			statement.execute();
			connection.commit();
			return null;
		});
	}

	private static final String CHECK_AUTHENTICATED_QUERY = "SELECT AUTHENTICATE(?) FROM DUAL";

	public boolean isAuthenticated(String token) {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_AUTHENTICATED_QUERY, (connection, statement) -> {
			statement.setString(1, token);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
					.get();
		});
	}

	private static final String GET_AUTHENTICATED_USER_QUERY = "SELECT USER_ID FROM AUTH WHERE TOKEN = ?";

	public Long getAuthenticatedUser(String token) {
		return DatabaseUtils.executeWithPreparedStatement(GET_AUTHENTICATED_USER_QUERY, (connection, statement) -> {
			statement.setString(1, token);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper())
					.get();
		});
	}
}
