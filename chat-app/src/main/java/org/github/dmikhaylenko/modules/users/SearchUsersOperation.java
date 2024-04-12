package org.github.dmikhaylenko.modules.users;

import java.util.List;

import javax.enterprise.inject.Default;

import org.github.dmikhaylenko.dao.DBPaginate;
import org.github.dmikhaylenko.modules.AuthenticationDecorator;
import org.github.dmikhaylenko.modules.GenericOperation;
import org.github.dmikhaylenko.operations.OperationContext;

@Default
public class SearchUsersOperation extends GenericOperation<SearchUserQuery, SearchUserQueryResult> {
	public SearchUsersOperation() {
		super(configurer -> {
			configurer.decorate(new AuthenticationDecorator<>());
		});
	}

	@Override
	public SearchUserQueryResult executeOperation(OperationContext context, SearchUserQuery request) {
		UsersModel usersModel = new UsersModel();
		String searchString = request.getSearchString();
		DBPaginate pagination = request.getPagination();
		return new SearchUserQueryResult() {
			
			@Override
			public List<UserModel> getUsers() {
				return usersModel.findByPhoneOrUsername(searchString, pagination);
			}
			
			@Override
			public Long getTotal() {
				return usersModel.countByPhoneOrUsername(searchString);
			}
		};
	}
}
