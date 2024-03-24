package org.github.dmikhaylenko.modules.history;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.commons.auth.AuthTokenModel;
import org.github.dmikhaylenko.commons.pagination.Pagination;
import org.github.dmikhaylenko.commons.time.TimezoneUtils;
import org.github.dmikhaylenko.commons.validation.ValidationUtils;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.modules.messages.MessageModel;
import org.github.dmikhaylenko.modules.users.UserModel;

@Path("/histories")
public class HistoriesController {
	@GET
	public ResponseModel searchHistories(@Context HttpHeaders headers, @QueryParam("pg") Long pg,
			@QueryParam("ps") Long ps) {
		TimezoneUtils.loadZoneOffset(headers);
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		Pagination pagination = Pagination.of(pg, ps).defaults(1, 500L, 50L);
		List<HistoryModel> histories = HistoryModel.findHistories(token, pagination);
		Long total = HistoryModel.countHistories(token);
		Long totalUnwatched = HistoryModel.countUnwatchedHistories(token);
		return new SearchHistoriesResponse(histories, total, totalUnwatched);
	}

	@POST
	@Path("/{userId}")
	public PostMessageResponse postMessage(@Context HttpHeaders headers, @PathParam("userId") Long userId,
			MessageModel message) {
		ValidationUtils.checkConstraints(message);
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		Long messageId = message.writeMessage(token, userId);
		return new PostMessageResponse(messageId);
	}

	@DELETE
	@Path("/{userId}")
	public ClearHistoryResponse clearHistory(@Context HttpHeaders headers, @PathParam("userId") Long userId) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		UserModel.checkThatRequestedUserExits(userId);
		HistoryModel.clearAllMessages(token.getAuthenticatedUser(), userId);
		return new ClearHistoryResponse();
	}

	@GET
	@Path("/{userId}/messages")
	public ShowHistoryMessages showHistory(@Context HttpHeaders headers, @PathParam("userId") Long userId,
			@QueryParam("pg") Long pg, @QueryParam("ps") Long ps) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		UserModel.checkThatRequestedUserExits(userId);
		Long currentUserId = token.getAuthenticatedUser();
		Pagination pagination = Pagination.of(pg, ps).pageSizeDefaults(500, 50).defaultPageNumber((currentPageNumber, currentPageSize) -> {
			return MessageViewModel.getLastPage(currentUserId, currentPageSize.getPageSize());
		});
		Long total = MessageViewModel.getTotalMessages(userId, currentUserId);
		List<MessageViewModel> messages = MessageViewModel.findMessages(userId, currentUserId, pagination);
		return new ShowHistoryMessages(pagination.getPageNumber(), total, messages);
	}
}
