package org.github.dmikhaylenko.modules.messages.history;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.modules.AuthenticationDecorator;
import org.github.dmikhaylenko.modules.GenericOperation;
import org.github.dmikhaylenko.modules.messages.MessageModel;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.operations.ValidationDecorator;

@Default
public class PostMessageOperation extends GenericOperation<PostMessageCommand, Long> {
	public PostMessageOperation() {
		super(configurer -> {
			configurer.decorate(new ValidationDecorator<>());
			configurer.decorate(new AuthenticationDecorator<>());	
		});
	}

	@Override
	public Long executeOperation(OperationContext context, PostMessageCommand request) {
		request.getReceiverId().checkThatRequestedUserExists();
		MessageModel messageModel = new MessageModel(context.getAuthentication(), request);
		return messageModel.writeMessage();
	}
}
