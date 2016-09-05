package com.egova.heroku.SqlExecute_Jersey.utils.http;

import java.net.URL;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public abstract class HttpClientHelper {

	protected static final String HTTPS_PROTOCOL = "https";

	protected static final String HTTP_PROTOCOL = "http";

	public static String post(String url, Map<String, Object> paras) throws Exception {

		HttpClient client = HttpClientHelper.buildClient(url);
		HttpUriRequest request = HttpClientHelper.buildeParas(url, true, paras);
		HttpResponse response = client.execute(request);
		return doResponse(response);
	}

	public static String get(String url, Map<String, Object> paras) throws Exception {

		HttpClient client = HttpClientHelper.buildClient(url);
		HttpUriRequest request = HttpClientHelper.buildeParas(url, true, paras);
		HttpResponse response = client.execute(request);
		return doResponse(response);
	}

	protected static HttpClient buildClient(String url) throws Exception {

		HttpClientBuilder builder = HttpClientBuilder.create();
		URL _url = new URL(url);

		switch (_url.getProtocol()) {
		case HTTP_PROTOCOL:
			break;
		case HTTPS_PROTOCOL:
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new DefaultX509TrustManager() }, null);
			builder.setSSLContext(sslcontext);
			break;
		default:
			throw new RuntimeException("URL地址不是http或者https请求:" + url);
		}
		return builder.build();
	}

	protected static HttpUriRequest buildeParas(String url, boolean isPost, Map<String, Object> paras) {

		RequestBuilder builder = isPost ? RequestBuilder.post() : RequestBuilder.get();
		builder.setUri(url);
		for (String key : paras.keySet()) {
			builder.addParameter(key, String.valueOf(paras.get(key)));
		}
		return builder.build();
	}

	protected static String doResponse(HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity,"UTF-8");
	}
}
