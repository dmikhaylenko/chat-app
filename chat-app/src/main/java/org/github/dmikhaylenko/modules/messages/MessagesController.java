package org.github.dmikhaylenko.modules.messages;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.validation.ValidationUtils;

@Path("/messages")
public class MessagesController {
	@PUT
	@Path("/{messageId}")
	public EditMessageResponse editMessageText(@Context HttpHeaders headers, @PathParam("messageId") Long messageId,
			EditMessageModel model) {
		ValidationUtils.checkConstraints(model);
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		MessageModel messageModel = MessageModel.getById(messageId);
		messageModel.editMessage(token, model);
		return new EditMessageResponse();
	}

	@DELETE
	@Path("/{messageId}")
	public DeleteMessageResponse deleteMessage(@Context HttpHeaders headers, @PathParam("messageId") Long messageId) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		MessageModel messageModel = MessageModel.getById(messageId);
		messageModel.deleteMessage(token);
		return new DeleteMessageResponse();
	}
}
