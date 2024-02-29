package org.github.dmikhaylenko.model;

import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.Resources;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;

import lombok.Data;

@Data
public class AuthTokenModel {
	private String token;

	private static final String AUTHENICATED_QUERY = "SELECT AUTHENTICATE(?) FROM DUAL";

	public boolean isAuthenticated() {
		return DatabaseUtils.executeWithCallStatement(Resources.getChatDb(), AUTHENICATED_QUERY,
				(connection, statement) -> {
					statement.setString(1, getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}
}
