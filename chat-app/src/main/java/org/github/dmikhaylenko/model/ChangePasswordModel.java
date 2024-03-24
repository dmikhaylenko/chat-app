package org.github.dmikhaylenko.model;

import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.errors.WrongLoginOrPasswordException;
import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.utils.Resources;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class ChangePasswordModel {
	@XmlElement
	private String username;

	@XmlElement
	private String oldPassword;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 50)
	private String newPassword;

	public Long changePassword(AuthTokenModel authToken) {
		Long userId = getUserIdByCredentials(authToken);
		UserModel userModel = UserModel.findById(userId).get();
		userModel.changePassword(getNewPassword());
		return userId;
	}
	
	private static final String FIND_USER_BY_CREDENTIALS_QUERY = "SELECT CHK_CREDS(?, ?) AS USER_ID FROM DUAL";

	private Long getUserIdByCredentials(AuthTokenModel token) {
		return findUserByCredentials().filter(value -> Objects.equals(value, token.getAuthenticatedUser()))
		.orElseThrow(WrongLoginOrPasswordException::new);
	}
	
	private Optional<Long> findUserByCredentials() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), FIND_USER_BY_CREDENTIALS_QUERY,
				(connection, statement) -> {
					statement.setString(1, getUsername());
					statement.setString(2, getOldPassword());
					return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(),
							RowParsers.longValueRowMapper());
				});
	}
}
