package org.github.dmikhaylenko.modules.contacts;

import javax.validation.constraints.NotNull;

public interface AddContactCommand {
	@NotNull
	Long getContactId();
}