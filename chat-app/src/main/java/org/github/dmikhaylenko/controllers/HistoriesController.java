package org.github.dmikhaylenko.controllers;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.HistoryModel;
import org.github.dmikhaylenko.model.MessageModel;
import org.github.dmikhaylenko.model.MessageViewModel;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.utils.AuthUtils;
import org.github.dmikhaylenko.utils.PageUtils;
import org.github.dmikhaylenko.utils.ResponseUtils;
import org.github.dmikhaylenko.utils.TimezoneUtils;
import org.github.dmikhaylenko.utils.UserUtils;
import org.github.dmikhaylenko.utils.ValidationUtils;

@Path("/histories")
public class HistoriesController {
	@GET
	public ResponseModel searchHistories(@Context HttpHeaders headers, @QueryParam("pg") Long pg,
			@QueryParam("ps") Long ps) {
		TimezoneUtils.loadZoneOffset(headers);
		AuthTokenModel token = AuthUtils.parseAuthToken(headers);
		AuthUtils.checkThatAuthenticated(token);
		Long page = PageUtils.normalizePage(pg);
		Long pageSize = PageUtils.normalizePageSize(ps, 500, 500);
		List<HistoryModel> histories = HistoryModel.findHistories(token, page, pageSize);
		Long total = HistoryModel.countHistories(token);
		Long totalUnwatched = HistoryModel.countUnwatchedHistories(token);
		return ResponseUtils.createSearchHistoriesResponse(histories, total, totalUnwatched);
	}
	
	@POST
	@Path("/{userId}")
	public ResponseModel postMessage(@Context HttpHeaders headers, @PathParam("userId") Long userId, MessageModel message) {
		ValidationUtils.checkConstraints(message);
		AuthTokenModel token = AuthUtils.parseAuthToken(headers);
		AuthUtils.checkAuthenticated(token);
		UserUtils.checkThatRequestedUserExits(userId);
		message.setSrcId(token.getAuthenticatedUser());
		message.setDestId(userId);
		return ResponseUtils.createPostMessageResponse(message.insertIntoMessageTable());
	}

	@DELETE
	@Path("/{userId}")
	public ResponseModel clearHistory(@Context HttpHeaders headers, @PathParam("userId") Long userId) {
		AuthTokenModel token = AuthUtils.parseAuthToken(headers);
		AuthUtils.checkAuthenticated(token);
		UserUtils.checkThatRequestedUserExits(userId);
		HistoryModel.clearAllMessages(token.getAuthenticatedUser(), userId);
		return ResponseUtils.createClearHistoryResponse();
	}
	
	@GET
	@Path("/{userId}/messages")
	public ResponseModel showHistory(@Context HttpHeaders headers, @PathParam("userId") Long userId, @QueryParam("pg") Long pg,
			@QueryParam("ps") Long ps) {
		AuthTokenModel token = AuthUtils.getTokenFromHeader(headers);
		AuthUtils.checkThatAuthenticated(token);
		UserUtils.checkThatRequestedUserExits(userId);
		Long currentUserId = token.getAuthenticatedUser();
		Long pageSize = PageUtils.normalizePageSize(ps, 500, 500);
		Long pageNumber = PageUtils.normalizePage(pg, MessageViewModel.getLastPage(currentUserId, pageSize));
		Long total = MessageViewModel.getTotalMessages(userId, currentUserId);
		List<MessageViewModel> messages = MessageViewModel.findMessages(userId, currentUserId, pageNumber, pageSize);
		return ResponseUtils.createShowHistoryMessages(pageNumber, total, messages);
	}
}
