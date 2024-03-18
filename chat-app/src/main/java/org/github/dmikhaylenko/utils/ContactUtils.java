package org.github.dmikhaylenko.utils;

import org.github.dmikhaylenko.model.ContactModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContactUtils {
	public void checkThatRequestedUserExits(ContactModel contact) {
		UserUtils.checkThatRequestedUserExits(contact.getContactId());
	}

	public void checkThatContactDoesNotExistIntoTable(ContactModel contact) {
		if (contact.existsIntoContactTable()) {
			throw ExceptionUtils.createContactAlreadyExistsException();
		}
	}

	public void checkThatContactExistsIntoTable(ContactModel contact) {
		if (!contact.existsIntoContactTable()) {
			throw ExceptionUtils.createMissingRequestedContactException();
		}
	}
}
