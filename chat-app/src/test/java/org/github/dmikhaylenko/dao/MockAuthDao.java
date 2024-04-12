package org.github.dmikhaylenko.dao;

import java.util.Objects;
import java.util.Optional;

import org.github.dmikhaylenko.dao.users.DBUser;
import org.github.dmikhaylenko.dao.users.auth.AuthDao;
import org.github.dmikhaylenko.time.Time;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockAuthDao implements AuthDao {
	private final MockDataSet<DBAuth> authDataSet;
	private final MockUsersDao usersDao;

	@Override
	public Optional<String> executeLogin(String username, String password) {
		return findUserByCredentials(username, password).map(user -> {
			return findAuthByUserId(user.getId()).orElseGet(() -> {
				DBAuth auth = new DBAuth(user.getId());
				authDataSet.add(auth);
				return auth;
			}).getToken();
		});
	}

	@Override
	public void executeLogout(String token) {
		findAuthByToken(token).ifPresent(DBAuth::logout);
	}

	@Override
	public boolean isAuthenticated(String token) {
		Optional<DBUser> authenicatedUser = findUserByToken(token);
		authenicatedUser.ifPresent(user -> {
			// @formatter:off
			usersDao.updateIntoUserTable(user.toBuilder()
					.lastAuth(Time.currentLocalDateTime())
					.build());
			// @formatter:on
		});
		return authenicatedUser.isPresent();
	}

	@Override
	public Long getAuthenticatedUser(String token) {
		// @formatter:off
		return findAuthByToken(token)
				.map(DBAuth::getUserId)
				.get();
		// @formatter:on
	}

	public Optional<DBUser> findUserByToken(String token) {
		// @formatter:off
		return findAuthByToken(token)
				.map(DBAuth::getUserId)
				.flatMap(usersDao::findById);
		// @formatter:on
	}

	private Optional<DBAuth> findAuthByToken(String token) {
		// @formatter:off
		return authDataSet.stream()
				.filter(item -> Objects.equals(token, item.getToken()))
				.filter(item -> item.getExp().isAfter(Time.currentLocalDateTime()))
				.filter(DBAuth::isLoggedIn)
				.findFirst();
		// @formatter:on
	}

	private Optional<DBAuth> findAuthByUserId(Long userId) {
		// @formatter:off
		return authDataSet.stream()
				.filter(item -> userId.equals(item.getUserId()))
				.findFirst();
		// @formatter:on
	}

	private Optional<DBUser> findUserByCredentials(String phone, String password) {
		return usersDao.findUserByCredentials(phone, password).flatMap(usersDao::findById);
	}
}
