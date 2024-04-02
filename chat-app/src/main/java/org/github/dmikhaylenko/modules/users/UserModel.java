package org.github.dmikhaylenko.modules.users;

import java.time.LocalDateTime;

import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.dao.users.DBUser;
import org.github.dmikhaylenko.time.Time;

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

	public UserModel(RegisterUserCommand command) {
		super();
		this.id = Dao.userIdSequence().nextValue();
		this.avatar = command.getAvatar();
		this.phone = command.getPhone();
		this.password = command.getPassword();
		this.publicName = command.getPublicName();
		this.lastAuth = Time.currentLocalDateTime();
	}

	public UserModel(DBUser dbUser) {
		super();
		this.id = dbUser.getId();
		this.phone = dbUser.getPhone();
		this.password = dbUser.getPassword();
		this.avatar = dbUser.getAvatarHref();
		this.publicName = dbUser.getUsername();
		this.lastAuth = dbUser.getLastAuth();
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

	private boolean existsWithThePhone() {
		return Dao.userDao().existsWithThePhone(getPhone());
	}

	private boolean existsWithTheNickname() {
		return Dao.userDao().existsWithTheNickname(getPublicName());
	}

	private DBUser createDBUser() {
		return DBUser.builder().id(getId()).phone(getPhone()).password(getPassword()).username(getPublicName())
				.avatarHref(getAvatar()).lastAuth(getLastAuth()).build();
	}

	private Long insertToUserTable() {
		Dao.userDao().insertToUserTable(createDBUser());
		return id;
	}

	private UserModel updateIntoUserTable() {
		Dao.userDao().updateIntoUserTable(createDBUser());
		return this;
	}
}
