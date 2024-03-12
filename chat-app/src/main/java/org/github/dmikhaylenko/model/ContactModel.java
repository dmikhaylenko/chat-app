package org.github.dmikhaylenko.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.utils.Resources;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactModel {
	@Null
	@XmlElement
	private Long userId;

	@NotNull
	@XmlElement
	private Long contactId;

	private static String CHECK_CONTACT_EXISTS_QUERY = "SELECT COUNT(*) > 0 FROM CONTACT WHERE WHOSE_ID = ? AND WHO_ID = ?";

	public boolean existsIntoContactTable() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), CHECK_CONTACT_EXISTS_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					statement.setLong(2, contactId);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static String INSERT_INTO_CONTACT_TABLE_QUERY = "INSERT INTO CONTACT(WHOSE_ID, WHO_ID) VALUES (?, ?)";

	public void insertIntoContactTable() {
		DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), INSERT_INTO_CONTACT_TABLE_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					statement.setLong(2, contactId);
					statement.executeUpdate();
					return null;
				});
	}
}
