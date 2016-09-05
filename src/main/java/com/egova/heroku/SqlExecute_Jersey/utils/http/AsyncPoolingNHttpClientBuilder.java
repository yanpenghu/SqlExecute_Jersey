package com.egova.heroku.SqlExecute_Jersey.utils.http;

import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.Args;

public class AsyncPoolingNHttpClientBuilder {

	private PoolingNHttpClientConnectionManager poolingNHttpClientConnectionManager;

	private IdleConnectionEvictor idleConnectionEvictor;

	public AsyncPoolingNHttpClientBuilder poolingNHttpClientConnectionManager(
			PoolingNHttpClientConnectionManager poolingNHttpClientConnectionManager) {
		this.poolingNHttpClientConnectionManager = poolingNHttpClientConnectionManager;
		return this;
	}

	public AsyncPoolingNHttpClientBuilder idleConnectionEvictor(IdleConnectionEvictor idleConnectionEvictor) {
		this.idleConnectionEvictor = idleConnectionEvictor;
		return this;
	}

	public static AsyncPoolingNHttpClientBuilder create() {
		return new AsyncPoolingNHttpClientBuilder();
	}

	public AsyncPoolingNHttpClient build() throws IOReactorException {
		Args.notNull(poolingNHttpClientConnectionManager, "PoolingNHttpClientConnectionManager requied");
		AsyncPoolingNHttpClient client = new AsyncPoolingNHttpClient();
		client.setPoolingNHttpClientConnectionManager(poolingNHttpClientConnectionManager);
		client.setIdleConnectionEvictor(idleConnectionEvictor);
		client.afterInit();
		return client;
	}

}
