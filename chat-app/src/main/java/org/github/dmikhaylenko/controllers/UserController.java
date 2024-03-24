package org.github.dmikhaylenko.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.ChangePasswordModel;
import org.github.dmikhaylenko.model.ChangePasswordResponse;
import org.github.dmikhaylenko.model.Pagination;
import org.github.dmikhaylenko.model.RegisterUserResponse;
import org.github.dmikhaylenko.model.SearchUsersResponse;
import org.github.dmikhaylenko.model.UserModel;
import org.github.dmikhaylenko.utils.ValidationUtils;

@Path("/users")
public class UserController {
	@POST
	public RegisterUserResponse registerUser(UserModel model) {
		ValidationUtils.checkConstraints(model);
		Long userId = model.registerUser();
		return new RegisterUserResponse(userId);
	}

	@GET
	@Path("/search")
	public SearchUsersResponse searchUsers(@Context HttpHeaders headers, @QueryParam("sstr") String searchString,
			@QueryParam("pg") Long pageNumber, @QueryParam("ps") Long pageSize) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		Pagination pagination = Pagination.of(pageNumber, pageSize).defaults(1, 1000L, 50L);
		List<UserModel> users = UserModel.findByPhoneOrUsername(searchString, pagination);
		Long total = UserModel.countByPhoneOrUsername(searchString);
		return new SearchUsersResponse(users, total);
	}

	@POST
	@Path("/current/password")
	public ChangePasswordResponse changePassword(@Context HttpHeaders headers, ChangePasswordModel model) {
		ValidationUtils.checkConstraints(model);
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		Long userId = model.changePassword(token);
		return new ChangePasswordResponse(userId);
	}
}
