package org.github.dmikhaylenko.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.HistoryModel;
import org.github.dmikhaylenko.model.SearchHistoriesResponse;
import org.github.dmikhaylenko.utils.AuthUtils;
import org.github.dmikhaylenko.utils.PageUtils;
import org.github.dmikhaylenko.utils.ResponseUtils;
import org.github.dmikhaylenko.utils.TimezoneUtils;

@Path("/histories")
public class HistoriesController {
	@GET
	public SearchHistoriesResponse searchHistories(@Context HttpHeaders headers, @QueryParam("pg") Long pg, @QueryParam("ps") Long ps) {
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
}
