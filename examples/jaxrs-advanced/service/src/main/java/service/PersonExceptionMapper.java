package service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import common.PersonUpdateException;

/**
 * JAX-RS ExceptionMapper which transforms PersonUpdateExceptions into HTTP 400 response codes.
 *
 */
@Provider
public class PersonExceptionMapper implements ExceptionMapper<PersonUpdateException> {

	@Override
	public Response toResponse(PersonUpdateException exception) {
		return Response.status(400).build();
	}

}
