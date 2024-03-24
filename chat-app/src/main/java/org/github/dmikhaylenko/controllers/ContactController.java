package org.github.dmikhaylenko.controllers;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AddContactResponse;
import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.ContactModel;
import org.github.dmikhaylenko.model.DeleteContactResponse;
import org.github.dmikhaylenko.utils.ValidationUtils;

@Path("/contacts")
public class ContactController {
	@POST
	public AddContactResponse addContact(@Context HttpHeaders headers, ContactModel contact) {
		ValidationUtils.checkConstraints(contact);
		AuthTokenModel authToken = AuthTokenModel.getTokenFromHeader(headers);
		authToken.checkThatAuthenticated();
		contact.addContact(authToken);
		return new AddContactResponse();
	}

	@DELETE
	@Path("/{contactId}")
	public DeleteContactResponse deleteContact(@Context HttpHeaders headers, @PathParam("contactId") Long contactId) {
		AuthTokenModel authToken = AuthTokenModel.getTokenFromHeader(headers);
		authToken.checkThatAuthenticated();
		ContactModel contact = new ContactModel(authToken, contactId);
		contact.deleteContact();
		return new DeleteContactResponse();
	}
}
