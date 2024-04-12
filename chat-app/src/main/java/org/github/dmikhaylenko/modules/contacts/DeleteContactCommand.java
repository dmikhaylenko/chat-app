package org.github.dmikhaylenko.modules.contacts;

import javax.validation.constraints.NotNull;

public interface DeleteContactCommand {

	@NotNull
	Long getContactId();

}