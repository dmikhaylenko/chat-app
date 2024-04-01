package org.github.dmikhaylenko.modules.contacts;

import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.dao.contacts.DBContact;
import org.github.dmikhaylenko.modules.users.UserIdModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class ContactModel {
	private Long userId;
	private Long contactId;

	public ContactModel(Long currentUserId, Long contactId) {
		super();
		this.userId = currentUserId;
		this.contactId = contactId;
	}

	public void addContact() {
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
		return Dao.contactsDao().existsIntoContactTable(createDBContact());
	}

	private void insertIntoContactTable() {
		Dao.contactsDao().insertIntoContactTable(createDBContact());
	}

	private void deleteFromContactTable() {
		Dao.contactsDao().deleteFromContactTable(createDBContact());
	}

	private DBContact createDBContact() {
		return DBContact.builder().whoseId(userId).whoId(contactId).build();
	}
}
