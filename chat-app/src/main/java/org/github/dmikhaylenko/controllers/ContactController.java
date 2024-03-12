package org.github.dmikhaylenko.controllers;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
		checkThatContactDoesNotExistIntoTable(contact);
		contact.insertIntoContactTable();
		return ResponseUtils.createAddContactResponse();
	}
	
	@DELETE
	@Path("/{contactId}")
	public ResponseModel deleteContact(@Context HttpHeaders headers, @PathParam("contactId") Long contactId) {
		AuthTokenModel authToken = AuthUtils.parseAuthToken(headers);
		AuthUtils.checkAuthenticated(authToken);
		ContactModel contact = new ContactModel();
		contact.setUserId(authToken.getAuthenticatedUser());
		contact.setContactId(contactId);
		checkThatContactExistsIntoTable(contact);
		contact.deleteFromContactTable();
		return ResponseUtils.createDeleteContactResponse();
		
	}

	private void checkThatRequestedUserExits(ContactModel contact) {
		if (!UserModel.existsById(contact.getContactId())) {
			throw ExceptionUtils.createMissingRequestedUserException();
		}
	}

	private void checkThatContactDoesNotExistIntoTable(ContactModel contact) {
		if (contact.existsIntoContactTable()) {
			throw ExceptionUtils.createContactAlreadyExistsException();
		}
	}
	
	private void checkThatContactExistsIntoTable(ContactModel contact) {
		if (!contact.existsIntoContactTable()) {
			throw ExceptionUtils.createMissingRequestedContactException();
		}
	}
}
