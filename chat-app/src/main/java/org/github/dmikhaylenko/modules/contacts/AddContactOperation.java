package org.github.dmikhaylenko.modules.contacts;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.operations.AuthenticationDecorator;
import org.github.dmikhaylenko.operations.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.operations.ValidationDecorator;

@Default
public class AddContactOperation extends GenericOperation<AddContactCommand, Void> {
	public AddContactOperation() {
		super(configurer -> {
			configurer.decorate(new ValidationDecorator<>());
			configurer.decorate(new AuthenticationDecorator<>());
		});
	}

	@Override
	public Void executeOperation(OperationContext context, AddContactCommand request) {
		ContactModel contact = new ContactModel(context.getAuthenticatedUser(), request.getContactId());
		contact.addContact();
		return null;
	}
}
