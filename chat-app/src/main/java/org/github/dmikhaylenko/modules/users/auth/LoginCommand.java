package org.github.dmikhaylenko.modules.users.auth;

public interface LoginCommand {

	String getPassword();

	String getUsername();

}