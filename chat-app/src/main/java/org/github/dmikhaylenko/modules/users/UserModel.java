package org.github.dmikhaylenko.modules.users;

import java.time.LocalDateTime;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.commons.time.TimeUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public class UserModel {
	private Long id;
	private String avatar;
	private String phone;
	private String password;
	private String publicName;
	private LocalDateTime lastAuth;
	
	public UserModel(RegisterUserRequest command) {
		super();
		this.avatar = command.getAvatar();
		this.phone = command.getPhone();
		this.password = command.getPassword();
		this.publicName = command.getPublicName();
		this.lastAuth = TimeUtils.currentLocalDateTime();
	}
	
	public Long registerUser() {
		checkThatUserWithPhoneExists();
		checkThatUserWithNickNameExists();
		return insertToUserTable();
	}
	
	public void changePassword(String password) {
		this.password = password;
		updateIntoUserTable();
	}
	
	private void checkThatUserWithPhoneExists() {
		if (existsWithThePhone()) {
			throw new UserWithPhoneExistsException();
		}
	}
	
	private void checkThatUserWithNickNameExists() {
		if (existsWithTheNickname()) {
			throw new UserWithNickNameExistsException();
		}
	}
	
	private static final String CHECK_EXISTS_WITH_PHONE_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE PHONE = ?";

	private boolean existsWithThePhone() {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_WITH_PHONE_QUERY,
				(connection, statement) -> {
					statement.setString(1, getPhone());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String CHECK_EXISTS_WITH_NICKNAME_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE USERNAME = ?";

	private boolean existsWithTheNickname() {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_WITH_NICKNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, getPublicName());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String INSERT_TO_USER_TABLE_QUERY = "INSERT INTO USER(PHONE, PASSWORD, USERNAME, AVATAR_HREF) VALUES(?,?,?,?)";

	private Long insertToUserTable() {
		return DatabaseUtils.executeWithPreparedStatement(INSERT_TO_USER_TABLE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, getPhone());
					statement.setString(2, getPassword());
					statement.setString(3, getPublicName());
					statement.setString(4, getAvatar());
					statement.executeUpdate();
					Long id = DatabaseUtils.lastInsertedId(connection).get();
					connection.commit();
					return id;
				});
	}

	private static final String UPDATE_INTO_USER_TABLE_QUERY = "UPDATE USER SET PHONE=?, PASSWORD=?, USERNAME=?, AVATAR_HREF=? WHERE ID=?";

	private UserModel updateIntoUserTable() {
		return DatabaseUtils.executeWithPreparedStatement(UPDATE_INTO_USER_TABLE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, getPhone());
					statement.setString(2, getPassword());
					statement.setString(3, getPublicName());
					statement.setString(4, getAvatar());
					statement.setLong(5, getId());
					statement.executeUpdate();
					connection.commit();
					return this;
				});
	}
}
