package org.github.dmikhaylenko.modules.contacts;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.http.HttpOperationContext;

@Path("/contacts")
public class ContactController {
	@Inject
	private AddContactOperation addContactOperation;
	
	@Inject
	private DeleteContactOperation deleteContactOperation;
	
	@POST
	public AddContactResponse addContact(@Context HttpHeaders headers, AddContactRequest contact) {
		addContactOperation.execute(new HttpOperationContext(headers), contact);
		return new AddContactResponse();
	}

	@DELETE
	@Path("/{contactId}")
	public DeleteContactResponse deleteContact(@Context HttpHeaders headers, @PathParam("contactId") Long contactId) {
		deleteContactOperation.execute(new HttpOperationContext(headers), new DeleteContactRequest(contactId));
		return new DeleteContactResponse();
	}
}
