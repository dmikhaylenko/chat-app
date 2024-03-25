package org.github.dmikhaylenko.modules.contacts;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.UserIdModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactModel {
	@Null
	@XmlElement
	private Long userId;

	@NotNull
	@XmlElement
	private Long contactId;
	
	public ContactModel(AuthTokenModel authToken, Long contactId) {
		super();
		this.userId = authToken.getAuthenticatedUser();
		this.contactId = contactId;
	}
	
	private static String CHECK_CONTACT_EXISTS_QUERY = "SELECT COUNT(*) > 0 FROM CONTACT WHERE WHOSE_ID = ? AND WHO_ID = ?";

	public void addContact(AuthTokenModel authToken) {
		this.userId = authToken.getAuthenticatedUser();
		checkThatRequestedUserExits();
		checkThatContactDoesNotExistIntoTable();
		insertIntoContactTable();
	}
	
	public void deleteContact() {
		checkThatContactExistsIntoTable();
		deleteFromContactTable();
	}
	
	private void checkThatRequestedUserExits() {
		UserIdModel userId = new UserIdModel(getContactId());
		userId.checkThatRequestedUserExists();
	}

	private void checkThatContactDoesNotExistIntoTable() {
		if (existsIntoContactTable()) {
			throw new ContactAlreadyExistsException();
		}
	}

	private void checkThatContactExistsIntoTable() {
		if (!existsIntoContactTable()) {
			throw new MissingRequestedContactException();
		}
	}
	
	private boolean existsIntoContactTable() {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_CONTACT_EXISTS_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					statement.setLong(2, contactId);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static String INSERT_INTO_CONTACT_TABLE_QUERY = "INSERT INTO CONTACT(WHOSE_ID, WHO_ID) VALUES (?, ?)";

	private void insertIntoContactTable() {
		DatabaseUtils.executeWithPreparedStatement(INSERT_INTO_CONTACT_TABLE_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					statement.setLong(2, contactId);
					statement.executeUpdate();
					return null;
				});
	}

	private static String DELETE_FROM_TABLE_QUERY = "DELETE FROM CONTACT WHERE WHOSE_ID = ? AND WHO_ID = ?";

	private void deleteFromContactTable() {
		DatabaseUtils.executeWithPreparedStatement(DELETE_FROM_TABLE_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					statement.setLong(2, contactId);
					statement.executeUpdate();
					return null;
				});
	}
}
