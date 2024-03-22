package org.github.dmikhaylenko.utils;

import org.github.dmikhaylenko.errors.MissingRequestedUserException;
import org.github.dmikhaylenko.errors.UserWithNickNameExistsException;
import org.github.dmikhaylenko.errors.UserWithPhoneExistsException;
import org.github.dmikhaylenko.model.UserModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtils {
	public void checkThatUserWithPhoneExists(UserModel userModel) {
		if (userModel.existsWithThePhone()) {
			throw new UserWithPhoneExistsException();
		}
	}
	
	public void checkThatUserWithNickNameExists(UserModel userModel) {
		if (userModel.existsWithTheNickname()) {
			throw new UserWithNickNameExistsException();
		}
	}
	
	public void checkThatRequestedUserExits(Long userId) {
		if (!UserModel.existsById(userId)) {
			throw new MissingRequestedUserException();
		}
	}
}
