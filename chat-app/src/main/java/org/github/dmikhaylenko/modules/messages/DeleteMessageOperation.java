package org.github.dmikhaylenko.modules.messages;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.operations.AuthenticationDecorator;
import org.github.dmikhaylenko.operations.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;

@Default
public class DeleteMessageOperation extends GenericOperation<DeleteMessageCommand, Void> {
	public DeleteMessageOperation() {
		super(configurer -> {
			configurer.decorate(new AuthenticationDecorator<>());	
		});
	}

	@Override
	public Void executeOperation(OperationContext context, DeleteMessageCommand request) {
		MessageIdModel messageId = request.getMessageId();
		MessageModel messageModel = messageId.getById();
		messageModel.deleteMessage(context.getAuthentication());
		return null;
	}
}
