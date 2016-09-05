package com.egova.heroku.SqlExecute_Jersey.utils.http;

import java.util.concurrent.TimeUnit;

import org.apache.http.nio.conn.NHttpClientConnectionManager;

class IdleConnectionEvictor extends Thread {

	final NHttpClientConnectionManager connMgr;

	int maxWaitTime = 500;

	int idletime = 5;

	volatile boolean shutdown;

	IdleConnectionEvictor(NHttpClientConnectionManager connMgr) {
		super();
		this.connMgr = connMgr;
	}

	void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	void setIdletime(int idletime) {
		this.idletime = idletime;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(maxWaitTime);
					connMgr.closeExpiredConnections();
					connMgr.closeIdleConnections(idletime, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException ex) {

		}
	}

	void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
