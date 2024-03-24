package org.github.dmikhaylenko.commons.auth;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthTokenModel {
	private final String token;

	private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("AUTH_TOKEN(\\s+)(\\S+)");

	public AuthTokenModel() {
		this(null);
	}

	public static AuthTokenModel getTokenFromHeader(HttpHeaders headers) {
		return getFromAuthorizationHeader(headers).map(AuthTokenModel::new).orElseGet(AuthTokenModel::new);
	}

	public void checkThatAuthenticated() {
		if (!isAuthenticated()) {
			throw new AuthenticationException("AUTH_TOKEN");
		}
	}

	private static final String CALL_LOGOUT_PROCEDURE = "{CALL LOGOUT(?)}";

	public void logout() {
		DatabaseUtils.executeWithCallStatement(CALL_LOGOUT_PROCEDURE,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, getToken());
					statement.execute();
					connection.commit();
					return null;
				});
	}

	private static final String GET_AUTHENTICATED_USER_QUERY = "SELECT USER_ID FROM AUTH WHERE TOKEN = ?";

	public Long getAuthenticatedUser() {
		return DatabaseUtils.executeWithPreparedStatement(GET_AUTHENTICATED_USER_QUERY,
				(connection, statement) -> {
					statement.setString(1, getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	private static Optional<String> getFromAuthorizationHeader(HttpHeaders headers) {
		return Optional.ofNullable(headers.getHeaderString(HttpHeaders.AUTHORIZATION))
				.map(AUTHORIZATION_PATTERN::matcher).filter(Matcher::matches).map(matcher -> matcher.group(2));
	}

	private static final String CHECK_AUTHENTICATED_QUERY = "SELECT AUTHENTICATE(?) FROM DUAL";

	private boolean isAuthenticated() {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_AUTHENTICATED_QUERY,
				(connection, statement) -> {
					statement.setString(1, getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}
}
