package org.github.dmikhaylenko.commons.errors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.github.dmikhaylenko.model.ResponseModel;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
	@Override
	public Response toResponse(ApplicationException exception) {
		ResponseModel responseEntity = new ApplicationErrorResponse(exception);
		return Response.status(Status.BAD_REQUEST).entity(responseEntity).build();
	}
}
