package org.github.dmikhaylenko.modules.users.auth;

import java.util.logging.Level;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.github.dmikhaylenko.modules.ApplicationErrorResponse;
import org.github.dmikhaylenko.modules.AuthenticationException;
import org.github.dmikhaylenko.modules.ResponseModel;

import lombok.extern.java.Log;

@Log
@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {
	@Override
	public Response toResponse(AuthenticationException exception) {
		log.log(Level.SEVERE, exception.getMessage(), exception);
		ResponseModel responseEntity = new ApplicationErrorResponse(exception);
		String challengesHeader = String.join(", ", exception.getChallenges());
		return Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, challengesHeader)
				.entity(responseEntity).build();
	}
}
