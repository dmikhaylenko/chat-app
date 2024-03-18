package org.github.dmikhaylenko.controllers;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.EditMessageModel;
import org.github.dmikhaylenko.model.MessageModel;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.utils.AuthUtils;
import org.github.dmikhaylenko.utils.ExceptionUtils;
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
				.orElseThrow(ExceptionUtils::createMissingRequestedMessageException);
		MessagesUtils.checkMessageEditingAvailabilityForUser(token.getAuthenticatedUser(), messageModel);
		messageModel.setMessageText(model.getMessageText());
		messageModel.updateIntoMessageTable();
		return ResponseUtils.createEditMessageResponse();
	}
}
