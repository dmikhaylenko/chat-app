package org.github.dmikhaylenko.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.ContactModel;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.model.UserModel;
import org.github.dmikhaylenko.utils.AuthUtils;
import org.github.dmikhaylenko.utils.ExceptionUtils;
import org.github.dmikhaylenko.utils.ResponseUtils;
import org.github.dmikhaylenko.utils.ValidationUtils;


@Path("/contacts")
public class ContactController {
	@POST
	public ResponseModel addContact(@Context HttpHeaders headers, ContactModel contact) {
		ValidationUtils.checkConstraints(contact);
		AuthTokenModel authToken = AuthUtils.parseAuthToken(headers);
		AuthUtils.checkAuthenticated(authToken);
		contact.setUserId(authToken.getAuthenticatedUser());
		checkThatRequestedUserExits(contact);
		checkThatContactExistsIntoTable(contact);
		contact.insertIntoContactTable();
		return ResponseUtils.createAddContactResponse();
	}

	private void checkThatRequestedUserExits(ContactModel contact) {
		if (!UserModel.existsById(contact.getContactId())) {
			throw ExceptionUtils.createMissingRequestedUserException();
		}
	}

	private void checkThatContactExistsIntoTable(ContactModel contact) {
		if (contact.existsIntoContactTable()) {
			throw ExceptionUtils.createContactAlreadyExistsException();
		}
	}
}
