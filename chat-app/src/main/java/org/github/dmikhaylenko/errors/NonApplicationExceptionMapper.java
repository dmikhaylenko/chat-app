package org.github.dmikhaylenko.errors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.github.dmikhaylenko.model.NonApplicationErrorResponse;
import org.github.dmikhaylenko.model.ResponseModel;

@Provider
public class NonApplicationExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		ResponseModel responseEntity = new NonApplicationErrorResponse();
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(responseEntity).build();
	}
}
