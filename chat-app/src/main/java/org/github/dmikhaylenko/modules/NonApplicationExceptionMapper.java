package org.github.dmikhaylenko.modules;

import java.util.logging.Level;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.java.Log;

@Log
@Provider
public class NonApplicationExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		log.log(Level.SEVERE, exception.getMessage(), exception);
		ResponseModel responseEntity = new NonApplicationErrorResponse();
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(responseEntity).build();
	}
}
