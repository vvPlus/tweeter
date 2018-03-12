package com.vv.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFoundException> {

	@Override
	public Response toResponse(ResourceNotFoundException e) {
		return Response
				.status(Status.NOT_FOUND)
				.entity(e.getMessage())
				.type(MediaType.TEXT_PLAIN)
                .build();
	}
}
