package org.github.dmikhaylenko.modules.users;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;

@Path("/users")
public class UserController {
	private RegisterUserOperation registerUserOperation = new RegisterUserOperation();
	private SearchUsersOperation searchUsersOperation = new SearchUsersOperation();
	private ChangePasswordOperation changePasswordOperation = new ChangePasswordOperation();

	@POST
	public RegisterUserResponse registerUser(RegisterUserRequest model) {
		return registerUserOperation.execute(model);
	}

	@GET
	@Path("/search")
	public SearchUsersResponse searchUsers(@Context HttpHeaders headers, @QueryParam("sstr") String searchString,
			@QueryParam("pg") Long pageNumber, @QueryParam("ps") Long pageSize) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		return searchUsersOperation.execute(token, new SearchUserRequest(searchString, pageNumber, pageSize));
	}

	@POST
	@Path("/current/password")
	public ChangePasswordResponse changePassword(@Context HttpHeaders headers, ChangePasswordRequest request) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		return changePasswordOperation.execute(token, request);
	}
}
