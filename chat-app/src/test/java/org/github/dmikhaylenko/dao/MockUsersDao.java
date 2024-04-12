package org.github.dmikhaylenko.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.github.dmikhaylenko.dao.users.DBUser;
import org.github.dmikhaylenko.dao.users.UsersDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockUsersDao implements UsersDao {
	private final MockDataSet<DBUser> dataSet;

	@Override
	public Optional<DBUser> findById(Long userId) {
		return dataSet.stream().filter(item -> userId.equals(item.getId())).findFirst();
	}

	@Override
	public Optional<Long> findUserByCredentials(String username, String password) {
		return dataSet.stream().filter(item -> username.equals(item.getPhone()) && password.equals(item.getPassword()))
				.map(DBUser::getId).findFirst();
	}

	@Override
	public List<DBUser> findByPhoneOrUsername(String searchString, DBPaginate pagination) {
		// @formatter:off
		return dataSet.stream()
				.filter(searchUserPredicate(searchString))
				.skip(pagination.getOffset())
				.limit(pagination.getPageSize())
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public boolean existsById(Long userId) {
		return dataSet.stream().anyMatch(item -> userId.equals(item.getId()));
	}

	@Override
	public boolean existsWithThePhone(String phone) {
		return dataSet.stream().anyMatch(item -> phone.equals(item.getPhone()));
	}

	@Override
	public boolean existsWithTheNickname(String publicName) {
		return dataSet.stream().anyMatch(item -> publicName.equals(item.getUsername()));
	}

	@Override
	public Long countByPhoneOrUsername(String searchString) {
		// @formatter:off
		return dataSet.stream()
				.filter(searchUserPredicate(searchString))
				.count();
		// @formatter:on
	}

	@Override
	public void insertToUserTable(DBUser dbUser) {
		dataSet.add(dbUser);
	}

	@Override
	public void updateIntoUserTable(DBUser dbUser) {
		dataSet.remove(findById(dbUser.getId()).get());
		insertToUserTable(dbUser);
	}

	private Predicate<DBUser> searchUserPredicate(String searchString) {
		return user -> {
			// @formatter:off
			return user.getUsername().startsWith(searchString) ||
					user.getPhone().startsWith(searchString);
			// @formatter:on
		};
	}
}
