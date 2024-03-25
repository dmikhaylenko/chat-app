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
	
	public void checkThatRequestedUserExists() {
		if (!existsById()) {
			throw new MissingRequestedUserException();
		}
	}
	
	public boolean existsById() {
		return Dao.userDao().existsById(userId);
	}
	
	public Optional<UserModel> findById() {
		return Dao.userDao().findById(userId).map(UserModel::new);
	}
}
