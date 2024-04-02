package org.github.dmikhaylenko.modules.users;

public interface ChangePasswordCommand {

	String getNewPassword();

	String getOldPassword();

	String getUsername();

}