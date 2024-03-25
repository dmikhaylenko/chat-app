package org.github.dmikhaylenko.modules.users;

import java.util.List;
import java.util.stream.Collectors;

import org.github.dmikhaylenko.dao.DBPaginate;
import org.github.dmikhaylenko.dao.Dao;

public class UsersModel {
	public List<UserModel> findByPhoneOrUsername(String searchString, DBPaginate pagination) {
		return Dao.userDao().findByPhoneOrUsername(searchString, pagination).stream().map(UserModel::new)
				.collect(Collectors.toList());
	}

	public Long countByPhoneOrUsername(String searchString) {
		return Dao.userDao().countByPhoneOrUsername(searchString);
	}
}
