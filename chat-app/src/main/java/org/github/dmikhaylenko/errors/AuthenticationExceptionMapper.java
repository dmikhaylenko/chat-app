package org.github.dmikhaylenko.errors;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.utils.ResponseUtils;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {
	@Override
	public Response toResponse(AuthenticationException exception) {
		String challengesHeader = String.join(",", exception.getChallenges());
		ResponseModel responseEntity = ResponseUtils.createErrorResponse(exception);
		return Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, challengesHeader)
				.entity(responseEntity).build();
	}
}
