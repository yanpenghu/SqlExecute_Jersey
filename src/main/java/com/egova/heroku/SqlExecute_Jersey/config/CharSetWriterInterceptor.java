package com.egova.heroku.SqlExecute_Jersey.config;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 * 字符编码修改为:UTF-8
 * @author Administrator
 *
 */
@Provider
public class CharSetWriterInterceptor implements WriterInterceptor {

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		MultivaluedMap<String, Object> headers = context.getHeaders();
		String ContentType = context.getMediaType().toString();
		headers.add("Content-Type", ContentType + ";charset=utf-8");
		context.proceed();
	}

}
