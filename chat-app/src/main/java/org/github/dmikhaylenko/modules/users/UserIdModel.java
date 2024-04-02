package org.github.dmikhaylenko.modules.users;

import java.util.Optional;

import org.github.dmikhaylenko.dao.Dao;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserIdModel {
	private final Long userId;

	public Long unwrap() {
		return userId;
	}

	private Optional<Long> getUserId() {
		return Optional.ofNullable(userId);
	}

	public void checkThatRequestedUserExists() {
		if (!existsById()) {
			throw new MissingRequestedUserException();
		}
	}

	public boolean existsById() {
		return getUserId().map(Dao.userDao()::existsById).orElse(false);
	}

	public Optional<UserModel> findById() {
		return getUserId().flatMap(Dao.userDao()::findById).map(UserModel::new);
	}
}
