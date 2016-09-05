package com.egova.heroku.SqlExecute_Jersey.config;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 所有异常转换为Json
 * @author Administrator
 *
 */
@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		return Response.ok(exception.getMessage(), MediaType.APPLICATION_JSON).status(200).build();
	}

}
