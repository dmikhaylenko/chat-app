package org.github.dmikhaylenko.modules.users;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.http.HttpOperationContext;

@Path("/users")
public class UserController {
	@Inject
	private RegisterUserOperation registerUserOperation;
	
	@Inject
	private SearchUsersOperation searchUsersOperation;
	
	@Inject
	private ChangePasswordOperation changePasswordOperation;

	@POST
	public RegisterUserResponse registerUser(@Context HttpHeaders headers, RegisterUserRequest model) {
		Long userId = registerUserOperation.execute(new HttpOperationContext(headers), model);
		return new RegisterUserResponse(userId);
	}

	@GET
	@Path("/search")
	public SearchUsersResponse searchUsers(@Context HttpHeaders headers, @QueryParam("sstr") String searchString,
			@QueryParam("pg") Long pageNumber, @QueryParam("ps") Long pageSize) {
		SearchUserQueryResult result = searchUsersOperation.execute(new HttpOperationContext(headers),
				new SearchUserRequest(searchString, pageNumber, pageSize));
		return new SearchUsersResponse(result);
	}

	@POST
	@Path("/current/password")
	public ChangePasswordResponse changePassword(@Context HttpHeaders headers, ChangePasswordRequest request) {
		Long userId = changePasswordOperation.execute(new HttpOperationContext(headers), request);
		return new ChangePasswordResponse(userId);
	}
}
