package org.github.dmikhaylenko.modules.users;

import java.util.List;

import org.github.dmikhaylenko.dao.DBPaginate;
import org.github.dmikhaylenko.model.AuthTokenModel;

public class SearchUsersOperation {
	public SearchUsersResponse execute(AuthTokenModel token, SearchUserRequest request) {
		UsersModel usersModel = new UsersModel();
		String searchString = request.getSearchString();
		DBPaginate pagination = request.getPagination();
		token.checkThatAuthenticated();
		List<UserModel> users = usersModel.findByPhoneOrUsername(searchString, pagination);
		Long total = usersModel.countByPhoneOrUsername(searchString);
		return new SearchUsersResponse(users, total);
	}
}
