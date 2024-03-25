package org.github.dmikhaylenko.modules.users;

import java.util.Objects;
import java.util.Optional;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.model.AuthTokenModel;

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
	
	private static final String FIND_USER_BY_CREDENTIALS_QUERY = "SELECT CHK_CREDS(?, ?) AS USER_ID FROM DUAL";

	private UserIdModel getUserIdByCredentials(AuthTokenModel token) {
		return findUserByCredentials().filter(value -> Objects.equals(value, token.getAuthenticatedUser())).map(UserIdModel::new)
		.orElseThrow(WrongLoginOrPasswordException::new);
	}
	
	private Optional<Long> findUserByCredentials() {
		return DatabaseUtils.executeWithPreparedStatement(FIND_USER_BY_CREDENTIALS_QUERY,
				(connection, statement) -> {
					statement.setString(1, username);
					statement.setString(2, oldPassword);
					return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(),
							RowParsers.longValueRowMapper());
				});
	}
}
