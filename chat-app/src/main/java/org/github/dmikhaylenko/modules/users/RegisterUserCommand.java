package org.github.dmikhaylenko.modules.users;

public interface RegisterUserCommand {

	String getAvatar();

	String getPassword();

	String getPhone();

	String getPublicName();

}