package org.github.dmikhaylenko.modules.users;

import java.util.List;

public interface SearchUserQueryResult {

	Long getTotal();

	List<UserModel> getUsers();

}