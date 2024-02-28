package org.github.dmikhaylenko.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.model.UserModel;
import org.github.dmikhaylenko.utils.ExceptionUtils;
import org.github.dmikhaylenko.utils.ResponseUtils;
import org.github.dmikhaylenko.utils.ValidationUtils;

@Path("/users")
public class UserController {
	@POST
	public ResponseModel registerUser(UserModel model) {
		ValidationUtils.checkConstraints(model);
		if (model.existsWithThePhone()) {
			throw ExceptionUtils.createUserWithPhoneExistsException();
		}

		if (model.existsWithTheNickname()) {
			throw ExceptionUtils.createUserWithNickNameExistsException();
		}
		
		return ResponseUtils.createRegisterUserResponse(model.insertToUserTable().getId());
	}
}
