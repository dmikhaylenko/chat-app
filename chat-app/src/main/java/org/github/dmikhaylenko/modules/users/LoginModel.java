package org.github.dmikhaylenko.modules.users;

import java.util.Optional;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class LoginModel {
	private String username;
	private String password;
	
	public LoginModel(LoginRequest request) {
		super();
		this.username = request.getUsername();
		this.password = request.getPassword();
	}
	
	private static final String CALL_LOGIN_QUERY = "SELECT LOGIN(?, ?) AS TOKEN FROM DUAL";
	
	public String login() {
		return executeLogin().orElseThrow(WrongLoginOrPasswordException::new);
	}
	
	private Optional<String> executeLogin() {
		return DatabaseUtils.executeWithPreparedStatement(CALL_LOGIN_QUERY, (connection, statement) -> {
			statement.setString(1, username);
			statement.setString(2, password);
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.stringValueRowMapper());
		});
	}
}
