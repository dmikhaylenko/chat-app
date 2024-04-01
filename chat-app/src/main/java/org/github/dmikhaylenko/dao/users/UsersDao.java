package org.github.dmikhaylenko.dao.users;

import java.util.List;
import java.util.Optional;

import org.github.dmikhaylenko.dao.DBPaginate;

public interface UsersDao {
	Optional<DBUser> findById(Long userId);

	Optional<Long> findUserByCredentials(String phone, String password);

	List<DBUser> findByPhoneOrUsername(String searchString, DBPaginate pagination);

	boolean existsById(Long userId);

	boolean existsWithThePhone(String phone);

	boolean existsWithTheNickname(String publicName);

	Long countByPhoneOrUsername(String searchString);

	void insertToUserTable(DBUser dbUser);

	void updateIntoUserTable(DBUser dbUser);
}