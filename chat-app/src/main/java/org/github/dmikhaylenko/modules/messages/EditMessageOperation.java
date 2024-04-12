package org.github.dmikhaylenko.modules.messages;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.modules.AuthenticationDecorator;
import org.github.dmikhaylenko.modules.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.operations.ValidationDecorator;

@Default
public class EditMessageOperation extends GenericOperation<EditMessageCommand, Void> {
	public EditMessageOperation() {
		super(configurer -> {
			configurer.decorate(new ValidationDecorator<>());
			configurer.decorate(new AuthenticationDecorator<>());
		});
	}

	@Override
	public Void executeOperation(OperationContext context, EditMessageCommand request) {
		MessageModel messageModel = request.getMessageId().getById();
		messageModel.editMessage(context.getAuthentication(), request);
		return null;
	}
}
