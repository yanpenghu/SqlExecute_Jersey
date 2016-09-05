package com.egova.heroku.SqlExecute_Jersey.utils.http;

import java.io.IOException;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;

/**
 * HttpAsyncClient 连接池
 * 
 * @author Administrator
 *
 */
class AsyncPoolingNHttpClient {

	private PoolingNHttpClientConnectionManager poolingNHttpClientConnectionManager;

	private IdleConnectionEvictor idleConnectionEvictor;

	PoolingNHttpClientConnectionManager getPoolingNHttpClientConnectionManager() {
		return poolingNHttpClientConnectionManager;
	}

	void setPoolingNHttpClientConnectionManager(
			PoolingNHttpClientConnectionManager poolingNHttpClientConnectionManager) {
		this.poolingNHttpClientConnectionManager = poolingNHttpClientConnectionManager;
	}

	IdleConnectionEvictor getIdleConnectionEvictor() {
		return idleConnectionEvictor;
	}

	void setIdleConnectionEvictor(IdleConnectionEvictor idleConnectionEvictor) {
		this.idleConnectionEvictor = idleConnectionEvictor;
	}

	void afterInit() {
		if (null != idleConnectionEvictor) {
			idleConnectionEvictor.start();
		}
	}

	public CloseableHttpAsyncClient buildClient() {
		return HttpAsyncClients.custom().setConnectionManager(poolingNHttpClientConnectionManager)
				.build();
	}

	public void shutDown() {
		if (null != idleConnectionEvictor) {
			idleConnectionEvictor.shutdown();
		}

	}

	public void releaseConnection(CloseableHttpAsyncClient client) throws IOException {
		client.close();
	}

}
