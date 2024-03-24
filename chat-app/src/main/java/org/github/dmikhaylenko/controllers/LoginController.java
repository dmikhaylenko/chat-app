package org.github.dmikhaylenko.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.LoginModel;
import org.github.dmikhaylenko.model.LoginResponse;
import org.github.dmikhaylenko.model.LogoutResponse;
import org.github.dmikhaylenko.model.ResponseModel;

@Path("/auth")
public class LoginController {
	@POST
	@Path("/logout")
	public ResponseModel logout(@Context HttpHeaders headers) {
		AuthTokenModel token = AuthTokenModel.getTokenFromHeader(headers);
		token.checkThatAuthenticated();
		token.logout();
		return new LogoutResponse();
	}

	@POST
	@Path("/login")
	public ResponseModel login(LoginModel model) {
		String token = model.login();
		return new LoginResponse(token);
	}
}
