package org.github.dmikhaylenko.modules.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface RegisterUserCommand {
	@NotNull
	@Size(max = 1000)
	String getAvatar();

	@NotNull
	@Size(min = 1, max = 50)
	String getPassword();

	@NotNull
	@Size(min = 1, max = 50)
	@Pattern(regexp = "\\+(\\d)+")
	String getPhone();

	@NotNull
	@Size(min = 1, max = 50)
	String getPublicName();
}