package org.github.dmikhaylenko.modules.messages.history;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.http.HttpOperationContext;
import org.github.dmikhaylenko.modules.messages.MessageContentData;

@Path("/histories")
public class HistoriesController {
	@Inject
	private SearchHistoriesOperation searchHistoriesOperation;
	
	@Inject
	private PostMessageOperation postMessageOperation;
	
	@Inject
	private ClearHistoryOperation clearHistoryOperation;
	
	@Inject
	private ShowHistoryOperation showHistoryOperation;

	@GET
	public SearchHistoriesResponse searchHistories(@Context HttpHeaders headers, @QueryParam("pg") Long pg,
			@QueryParam("ps") Long ps) {
		return searchHistoriesOperation.execute(new HttpOperationContext(headers), new SearchHistoriesRequest(pg, ps));
	}

	@POST
	@Path("/{userId}")
	public PostMessageResponse postMessage(@Context HttpHeaders headers, @PathParam("userId") Long userId,
			MessageContentData message) {
		Long messageId = postMessageOperation.execute(new HttpOperationContext(headers),
				new PostMessageRequest(userId, message));
		return new PostMessageResponse(messageId);
	}

	@DELETE
	@Path("/{userId}")
	public ClearHistoryResponse clearHistory(@Context HttpHeaders headers, @PathParam("userId") Long userId) {
		clearHistoryOperation.execute(new HttpOperationContext(headers), new ClearHistoryRequest(userId));
		return new ClearHistoryResponse();
	}

	@GET
	@Path("/{userId}/messages")
	public ShowHistoryResponse showHistory(@Context HttpHeaders headers, @PathParam("userId") Long userId,
			@QueryParam("pg") Long pageNumber, @QueryParam("ps") Long pageSize) {
		ShowHistoryQueryResult result = showHistoryOperation.execute(new HttpOperationContext(headers),
				new ShowHistoryRequest(userId, pageNumber, pageSize));
		return new ShowHistoryResponse(result);
	}
}
