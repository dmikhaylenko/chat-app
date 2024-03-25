package org.github.dmikhaylenko.modules.users;

import java.time.LocalDateTime;

import org.github.dmikhaylenko.commons.time.TimeUtils;
import org.github.dmikhaylenko.dao.DBUser;
import org.github.dmikhaylenko.dao.Dao;

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
	
	private DBUser createDbUser() {
		return DBUser.builder()
				.id(getId())
				.phone(getPhone())
				.password(getPassword())
				.username(getPublicName())
				.avatarHref(getAvatar())
				.lastAuth(getLastAuth())
				.build();
	}
	
	private Long insertToUserTable() {
		return Dao.userDao().insertToUserTable(createDbUser());
	}
	
	private UserModel updateIntoUserTable() {
		Dao.userDao().updateIntoUserTable(createDbUser());
		return this;
	}
}
