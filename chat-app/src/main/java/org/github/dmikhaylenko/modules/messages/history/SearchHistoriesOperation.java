package org.github.dmikhaylenko.modules.messages.history;

import java.util.List;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.auth.AuthToken;
import org.github.dmikhaylenko.modules.AuthenticationDecorator;
import org.github.dmikhaylenko.modules.GenericOperation;
import org.github.dmikhaylenko.modules.Pagination;
import org.github.dmikhaylenko.operations.OperationContext;

@Default
public class SearchHistoriesOperation extends GenericOperation<SearchHistoriesRequest, SearchHistoriesResponse> {
	public SearchHistoriesOperation() {
		super(configurer -> {
			configurer.decorate(new AuthenticationDecorator<>());	
		});
	}

	@Override
	public SearchHistoriesResponse executeOperation(OperationContext context, SearchHistoriesRequest request) {
		return findHistories(context.getAuthentication(), request.getPagination());
	}

	private SearchHistoriesResponse findHistories(AuthToken token, Pagination pagination) {
		HistoriesModel historiesModel = new HistoriesModel();
		List<HistoryModel> histories = historiesModel.findHistories(token, pagination);
		Long total = historiesModel.countHistories(token);
		Long totalUnwatched = historiesModel.countUnwatchedHistories(token);
		return new SearchHistoriesResponse(histories, total, totalUnwatched);
	}
}
