package org.github.dmikhaylenko.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.utils.Resources;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthTokenModel {
	private String token;

	private static final String CHECK_AUTHENTICATED_QUERY = "SELECT AUTHENTICATE(?) FROM DUAL";

	public boolean isAuthenticated() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), CHECK_AUTHENTICATED_QUERY,
				(connection, statement) -> {
					statement.setString(1, getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String CALL_LOGOUT_PROCEDURE = "{CALL LOGOUT(?)}";

	public void logout() {
		DatabaseUtils.executeWithCallStatement(Resources.getChatDb(), CALL_LOGOUT_PROCEDURE,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, getToken());
					statement.execute();
					connection.commit();
					return null;
				});
	}
}
