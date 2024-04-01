package org.github.dmikhaylenko.modules.users;

import java.util.Objects;

import org.github.dmikhaylenko.auth.AuthToken;
import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.modules.WrongLoginOrPasswordException;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ChangePasswordModel {
	private String username;
	private String oldPassword;
	private String newPassword;

	public ChangePasswordModel(ChangePasswordCommand command) {
		super();
		this.username = command.getUsername();
		this.oldPassword = command.getOldPassword();
		this.newPassword = command.getNewPassword();
	}

	public Long changePassword(AuthToken authToken) {
		UserIdModel userId = getUserIdByCredentials(authToken);
		UserModel userModel = userId.findById().get();
		userModel.changePassword(newPassword);
		return userId.unwrap();
	}

	private UserIdModel getUserIdByCredentials(AuthToken token) {
		return Dao.userDao().findUserByCredentials(username, oldPassword)
				.filter(value -> Objects.equals(value, token.getAuthenticatedUser())).map(UserIdModel::new)
				.orElseThrow(WrongLoginOrPasswordException::new);
	}
}
