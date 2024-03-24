package org.github.dmikhaylenko.model;

import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.errors.WrongLoginOrPasswordException;
import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginModel {
	@XmlElement
	private String username;
	@XmlElement
	private String password;
	
	private static final String CALL_LOGIN_QUERY = "SELECT LOGIN(?, ?) AS TOKEN FROM DUAL";
	
	public String login() {
		return executeLogin().orElseThrow(WrongLoginOrPasswordException::new);
	}
	
	private Optional<String> executeLogin() {
		return DatabaseUtils.executeWithPreparedStatement(CALL_LOGIN_QUERY, (connection, statement) -> {
			statement.setString(1, getUsername());
			statement.setString(2, getPassword());
			return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), RowParsers.stringValueRowMapper());
		});
	}
}
