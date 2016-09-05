package com.egova.heroku.SqlExecute_Jersey.utils.http;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

public abstract class HttpAsyncClientHelper {

	protected static AsyncPoolingNHttpClient buildAsyncClient() throws Exception {
		IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
				.setIoThreadCount(Runtime.getRuntime().availableProcessors()).build();
		ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
		PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
		cm.setMaxTotal(100);
		cm.setDefaultMaxPerRoute(20);
		IdleConnectionEvictor idelMonitor = IdleConnectionEvictorBuilder.create(cm).idletime(10).maxWaitTime(1000)
				.build();
		return AsyncPoolingNHttpClientBuilder.create().poolingNHttpClientConnectionManager(cm)
				.idleConnectionEvictor(idelMonitor).build();
	}
	
	public static String post(String url, Map<String, Object> paras) {
		HttpUriRequest request = HttpClientHelper.buildeParas(url, true, paras);
		return doExecute(request);
	}

	public static String get(String url, Map<String, Object> paras) {
		HttpUriRequest request = HttpClientHelper.buildeParas(url, false, paras);
		return doExecute(request);
	}

	protected static String doExecute(HttpUriRequest request) {
		String value;
		try {
			AsyncPoolingNHttpClient APNHttpClient = buildAsyncClient();
			CloseableHttpAsyncClient client = APNHttpClient.buildClient();
			client.start();
			value = doInnerExecute(client, request);
			APNHttpClient.releaseConnection(client);
		} catch (Exception e) {
			value = e.getMessage();
		}
		return value;
	}

	protected static String doInnerExecute(CloseableHttpAsyncClient client, HttpUriRequest request) throws Exception {
		Args.notNull(client, "CloseableHttpAsyncClient requied");
		Args.notNull(request, "HttpUriRequest requied");
		final CountDownLatch latch = new CountDownLatch(1);
		Future<HttpResponse> future = client.execute(request, new FutureCallback<HttpResponse>() {
			@Override
			public void failed(Exception ex) {
				latch.countDown();
			}

			@Override
			public void completed(HttpResponse result) {
				latch.countDown();
			}

			@Override
			public void cancelled() {
				latch.countDown();
			}
		});
		latch.await();

		return doResponse(future);
	}

	protected static HttpUriRequest buildeParas(String url, boolean isPost, Map<String, Object> paras) {

		RequestBuilder builder = isPost ? RequestBuilder.post() : RequestBuilder.get();
		builder.setUri(url);
		for (String key : paras.keySet()) {
			builder.addParameter(key, String.valueOf(paras.get(key)));
		}
		return builder.build();
	}

	protected static String doResponse(Future<HttpResponse> future) throws Exception {
		HttpResponse response = future.get();
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
	}
	
}
