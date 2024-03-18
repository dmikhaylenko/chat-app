package org.github.dmikhaylenko.utils;

import org.github.dmikhaylenko.model.UserModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtils {
	public void checkThatUserWithPhoneExists(UserModel userModel) {
		if (userModel.existsWithThePhone()) {
			throw ExceptionUtils.createUserWithPhoneExistsException();
		}
	}
	
	public void checkThatUserWithNickNameExists(UserModel userModel) {
		if (userModel.existsWithTheNickname()) {
			throw ExceptionUtils.createUserWithNickNameExistsException();
		}
	}
	
	public void checkThatRequestedUserExits(Long userId) {
		if (!UserModel.existsById(userId)) {
			throw ExceptionUtils.createMissingRequestedUserException();
		}
	}
}
