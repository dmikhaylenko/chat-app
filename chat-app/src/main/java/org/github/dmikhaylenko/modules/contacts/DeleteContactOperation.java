package org.github.dmikhaylenko.modules.contacts;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.modules.AuthenticationDecorator;
import org.github.dmikhaylenko.modules.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.operations.ValidationDecorator;

@Default
public class DeleteContactOperation extends GenericOperation<DeleteContactCommand, Void> {
	public DeleteContactOperation() {
		super(configurer -> {
			configurer.decorate(new ValidationDecorator<>());
			configurer.decorate(new AuthenticationDecorator<>());
		});
	}

	@Override
	public Void executeOperation(OperationContext context, DeleteContactCommand request) {
		ContactModel contact = new ContactModel(context.getAuthenticatedUser(), request.getContactId());
		contact.deleteContact();
		return null;
	}
}
