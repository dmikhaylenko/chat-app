package org.github.dmikhaylenko.utils;

import org.github.dmikhaylenko.model.UserModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtils {
	public void checkThatRequestedUserExits(Long userId) {
		if (!UserModel.existsById(userId)) {
			throw ExceptionUtils.createMissingRequestedUserException();
		}
	}
}
