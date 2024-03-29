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

import org.github.dmikhaylenko.commons.time.TimezoneUtils;
import org.github.dmikhaylenko.dao.DBPaginate;
import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.model.UserIdModel;
import org.github.dmikhaylenko.model.pagination.Pagination;
import org.github.dmikhaylenko.model.validation.ValidationUtils;
import org.github.dmikhaylenko.modules.messages.MessageModel;

@Path("/histories")
public class HistoriesController {
	@GET
	public ResponseModel searchHistories(@Context HttpHeaders headers, @QueryParam("pg") Long pg,
			@QueryParam("ps") Long ps) {
		TimezoneUtils.loadZoneOffset(headers);
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		DBPaginate pagination = Pagination.of(pg, ps).defaults(1, 500L, 50L);
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
		UserIdModel userIdModel = new UserIdModel(userId);
		userIdModel.checkThatRequestedUserExists();
		HistoryModel.clearAllMessages(token.getAuthenticatedUser(), userIdModel);
		return new ClearHistoryResponse();
	}

	@GET
	@Path("/{userId}/messages")
	public ShowHistoryMessages showHistory(@Context HttpHeaders headers, @PathParam("userId") Long userId,
			@QueryParam("pg") Long pg, @QueryParam("ps") Long ps) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		UserIdModel userIdModel = new UserIdModel(userId);
		userIdModel.checkThatRequestedUserExists();
		Long currentUserId = token.getAuthenticatedUser();
		DBPaginate pagination = Pagination.of(pg, ps).pageSizeDefaults(500, 50).defaultPageNumber((currentPageNumber, currentPageSize) -> {
			return MessageViewModel.getLastPage(currentUserId, currentPageSize.getPageSize());
		});
		Long total = MessageViewModel.getTotalMessages(userIdModel, currentUserId);
		List<MessageViewModel> messages = MessageViewModel.findMessages(userIdModel, currentUserId, pagination);
		return new ShowHistoryMessages(pagination.getPageNumber(), total, messages);
	}
}
