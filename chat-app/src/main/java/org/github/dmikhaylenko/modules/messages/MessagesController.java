package org.github.dmikhaylenko.modules.messages;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.http.HttpOperationContext;

@Path("/messages")
public class MessagesController {
	@Inject
	private EditMessageOperation editMessageOperation;
	
	@Inject
	private DeleteMessageOperation deleteMessageOperation;

	@PUT
	@Path("/{messageId}")
	public EditMessageResponse editMessageText(@Context HttpHeaders headers, @PathParam("messageId") Long messageId,
			MessageContentData messageContent) {
		editMessageOperation.execute(new HttpOperationContext(headers),
				new EditMessageRequest(messageId, messageContent));
		return new EditMessageResponse();
	}

	@DELETE
	@Path("/{messageId}")
	public DeleteMessageResponse deleteMessage(@Context HttpHeaders headers, @PathParam("messageId") Long messageId) {
		deleteMessageOperation.execute(new HttpOperationContext(headers), new DeleteMessageRequest(messageId));
		return new DeleteMessageResponse();
	}
}
