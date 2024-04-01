package org.github.dmikhaylenko.modules.users.auth;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.http.HttpOperationContext;
import org.github.dmikhaylenko.modules.ResponseModel;

@Path("/auth")
public class LoginController {
	@Inject
	private LoginOperation loginOperation;
	
	@Inject
	private LogoutOperation logoutOperation;

	@POST
	@Path("/logout")
	public LogoutResponse logout(@Context HttpHeaders headers) {
		logoutOperation.execute(new HttpOperationContext(headers), null);
		return new LogoutResponse();
	}

	@POST
	@Path("/login")
	public ResponseModel login(@Context HttpHeaders headers, LoginRequest request) {
		String token = loginOperation.execute(new HttpOperationContext(headers), request);
		return new LoginResponse(token);
	}
}
