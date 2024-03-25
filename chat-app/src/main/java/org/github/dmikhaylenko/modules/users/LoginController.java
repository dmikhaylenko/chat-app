package org.github.dmikhaylenko.modules.users;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.ResponseModel;

@Path("/auth")
public class LoginController {
	private LoginOperation loginOperation = new LoginOperation();
	private LogoutOperation logoutOperation = new LogoutOperation();
	
	@POST
	@Path("/logout")
	public LogoutResponse logout(@Context HttpHeaders headers) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		return logoutOperation.execute(token);
	}

	@POST
	@Path("/login")
	public ResponseModel login(LoginRequest request) {
		return loginOperation.execute(request);
	}
}
