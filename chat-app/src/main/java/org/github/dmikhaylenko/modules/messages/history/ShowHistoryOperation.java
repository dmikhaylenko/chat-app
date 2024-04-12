package org.github.dmikhaylenko.modules.messages.history;

import java.util.List;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.modules.AuthenticationDecorator;
import org.github.dmikhaylenko.modules.GenericOperation;
import org.github.dmikhaylenko.modules.Pagination;
import org.github.dmikhaylenko.modules.Pagination.DefaultPageNumberCalculator;
import org.github.dmikhaylenko.modules.users.UserIdModel;
import org.github.dmikhaylenko.operations.OperationContext;

@Default
public class ShowHistoryOperation extends GenericOperation<ShowHistoryCommand, ShowHistoryQueryResult> {
	public ShowHistoryOperation() {
		super(configurer -> {
			configurer.decorate(new AuthenticationDecorator<>());
		});
	}

	@Override
	public ShowHistoryQueryResult executeOperation(OperationContext context, ShowHistoryCommand request) {
		UserIdModel userIdModel = request.getUserId();
		userIdModel.checkThatRequestedUserExists();
		Long currentUserId = context.getAuthentication().getAuthenticatedUser();
		HistoryId historyId = new HistoryId(currentUserId, userIdModel.unwrap());
		Pagination pagination = request.getPagination(defaultPageNumberCalculator(historyId));
		return new ShowHistoryQueryResult() {
			@Override
			public Long getTotal() {
				return historyId.getTotalMessages();
			}

			@Override
			public Long getPageNumber() {
				return pagination.getPageNumber();
			}

			@Override
			public List<MessageViewModel> getMessages() {
				return historyId.findMessages(pagination);
			}
		};

	}

	private DefaultPageNumberCalculator defaultPageNumberCalculator(HistoryId historyId) {
		return (currentPageNumber, currentPageSize) -> {
			return historyId.getLastPageNumber(currentPageSize);
		};
	}
}
