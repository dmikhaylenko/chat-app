package org.github.dmikhaylenko.model;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.github.dmikhaylenko.model.errors.ApplicationErrorResponse;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {
	@Override
	public Response toResponse(AuthenticationException exception) {
		ResponseModel responseEntity = new ApplicationErrorResponse(exception);
		String challengesHeader = String.join(", ", exception.getChallenges());
		return Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, challengesHeader)
				.entity(responseEntity).build();
	}
}
