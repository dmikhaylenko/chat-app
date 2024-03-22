package org.github.dmikhaylenko.controllers;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.errors.MissingRequestedMessageException;
import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.EditMessageModel;
import org.github.dmikhaylenko.model.MessageModel;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.utils.AuthUtils;
import org.github.dmikhaylenko.utils.MessagesUtils;
import org.github.dmikhaylenko.utils.ResponseUtils;
import org.github.dmikhaylenko.utils.ValidationUtils;

@Path("/messages")
public class MessagesController {
	@PUT
	@Path("/{messageId}")
	public ResponseModel editMessageText(@Context HttpHeaders headers, @PathParam("messageId") Long messageId,
			EditMessageModel model) {
		ValidationUtils.checkConstraints(model);
		AuthTokenModel token = AuthUtils.getTokenFromHeader(headers);
		AuthUtils.checkThatAuthenticated(token);
		MessageModel messageModel = MessageModel.findById(messageId)
				.orElseThrow(MissingRequestedMessageException::new);
		MessagesUtils.checkMessageEditingAvailabilityForUser(token.getAuthenticatedUser(), messageModel);
		messageModel.setMessageText(model.getMessageText());
		messageModel.updateIntoMessageTable();
		return ResponseUtils.createEditMessageResponse();
	}

	@DELETE
	@Path("/{messageId}")
	public ResponseModel deleteMessage(@Context HttpHeaders headers, @PathParam("messageId") Long messageId) {
		AuthTokenModel token = AuthUtils.getTokenFromHeader(headers);
		AuthUtils.checkThatAuthenticated(token);
		MessageModel messageModel = MessageModel.findById(messageId)
				.orElseThrow(MissingRequestedMessageException::new);
		MessagesUtils.checkMessageDeleteAvailabilityForUser(token.getAuthenticatedUser(), messageModel);
		messageModel.deleteFromMessageTable();
		return ResponseUtils.createDeleteMessageResponse();
	}
}
