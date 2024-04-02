package org.github.dmikhaylenko.modules;

import java.util.logging.Level;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.github.dmikhaylenko.errors.ApplicationException;

import lombok.extern.java.Log;

@Log
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
	@Override
	public Response toResponse(ApplicationException exception) {
		log.log(Level.SEVERE, exception.getMessage(), exception);
		ResponseModel responseEntity = new ApplicationErrorResponse(exception);
		return Response.status(Status.BAD_REQUEST).entity(responseEntity).build();
	}
}
