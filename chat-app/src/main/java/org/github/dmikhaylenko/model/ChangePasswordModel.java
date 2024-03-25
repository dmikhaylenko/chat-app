package org.github.dmikhaylenko.model;

import java.util.Objects;

import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.modules.users.ChangePasswordRequest;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ChangePasswordModel {
	private String username;
	private String oldPassword;
	private String newPassword;

	public ChangePasswordModel(ChangePasswordRequest command) {
		super();
		this.username = command.getUsername();
		this.oldPassword = command.getOldPassword();
		this.newPassword = command.getNewPassword();
	}

	public Long changePassword(AuthTokenModel authToken) {
		UserIdModel userId = getUserIdByCredentials(authToken);
		UserModel userModel = userId.findById().get();
		userModel.changePassword(newPassword);
		return userId.unwrap();
	}

	private UserIdModel getUserIdByCredentials(AuthTokenModel token) {
		return Dao.userDao().findUserByCredentials(username, oldPassword)
				.filter(value -> Objects.equals(value, token.getAuthenticatedUser())).map(UserIdModel::new)
				.orElseThrow(WrongLoginOrPasswordException::new);
	}
}
