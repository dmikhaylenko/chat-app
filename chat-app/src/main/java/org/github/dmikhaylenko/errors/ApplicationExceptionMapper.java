package org.github.dmikhaylenko.errors;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.utils.ResponseUtils;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
	@Override
	public Response toResponse(ApplicationException exception) {
		ResponseModel responseEntity = ResponseUtils.createErrorResponse(exception);
		if (exception instanceof AuthenticationException) {
			AuthenticationException authException = (AuthenticationException) exception;
			String challengeStr = String.join(", ", authException.getChallenge());
			return Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, challengeStr)
					.entity(responseEntity).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity(responseEntity).build();
		}
	}
}
