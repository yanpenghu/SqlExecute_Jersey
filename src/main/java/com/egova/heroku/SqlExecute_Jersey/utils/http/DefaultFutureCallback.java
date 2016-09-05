package com.egova.heroku.SqlExecute_Jersey.utils.http;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

public class DefaultFutureCallback implements FutureCallback<HttpResponse>{

	@Override
	public void completed(HttpResponse result) {
		System.out.println(result);
	}

	@Override
	public void failed(Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void cancelled() {
		System.out.println("cancelled");
	}

}
