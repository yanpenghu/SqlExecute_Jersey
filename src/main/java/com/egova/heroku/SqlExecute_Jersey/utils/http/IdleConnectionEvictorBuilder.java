package com.egova.heroku.SqlExecute_Jersey.utils.http;

import org.apache.http.nio.conn.NHttpClientConnectionManager;

public class IdleConnectionEvictorBuilder {

	private NHttpClientConnectionManager connMgr;

	private int maxWaitTime = 500;

	private int idletime = 5;

	public IdleConnectionEvictorBuilder(NHttpClientConnectionManager connMgr) {
		this.connMgr = connMgr;
	}

	public IdleConnectionEvictorBuilder maxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
		return this;
	}

	public IdleConnectionEvictorBuilder idletime(int idletime) {
		this.idletime = idletime;
		return this;
	}
	
	public static IdleConnectionEvictorBuilder create(NHttpClientConnectionManager connMgr){
		return new IdleConnectionEvictorBuilder(connMgr);
	}
	
	public IdleConnectionEvictor build(){
		IdleConnectionEvictor idle =  new IdleConnectionEvictor(connMgr);
		idle.setIdletime(idletime);
		idle.setMaxWaitTime(maxWaitTime);
		return idle;
	}
	
}
