package org.github.dmikhaylenko.modules.messages.history;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.modules.users.UserIdModel;
import org.github.dmikhaylenko.operations.AuthenticationDecorator;
import org.github.dmikhaylenko.operations.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;

@Default
public class ClearHistoryOperation extends GenericOperation<ClearHistoryCommand, Void> {
	public ClearHistoryOperation() {
		super(configurer -> {
			configurer.decorate(new AuthenticationDecorator<>());	
		});
	}

	@Override
	public Void executeOperation(OperationContext context, ClearHistoryCommand request) {
		UserIdModel userIdModel = request.getUserId();
		userIdModel.checkThatRequestedUserExists();
		HistoriesModel historiesModel = new HistoriesModel();
		historiesModel.clearAllMessages(context.getAuthentication().getAuthenticatedUser(), userIdModel);
		return null;
	}
}
