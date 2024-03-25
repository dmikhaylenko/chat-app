package org.github.dmikhaylenko.modules.users;

import java.util.List;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.pagination.Pagination;

public class SearchUsersOperation {
	public SearchUsersResponse execute(AuthTokenModel token, SearchUserRequest request) {
		UsersModel usersModel = new UsersModel();
		String searchString = request.getSearchString();
		Pagination pagination = request.getPagination();
		token.checkThatAuthenticated();
		List<UserModel> users = usersModel.findByPhoneOrUsername(searchString, pagination);
		Long total = usersModel.countByPhoneOrUsername(searchString);
		return new SearchUsersResponse(users, total);
	}
}
