package org.github.dmikhaylenko.dao;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Dao {
	@Getter
	@Accessors(fluent = true)
	private UserDao userDao = new UserDao();
	
	@Getter
	@Accessors(fluent = true)
	private AuthDao authDao = new AuthDao();
}
