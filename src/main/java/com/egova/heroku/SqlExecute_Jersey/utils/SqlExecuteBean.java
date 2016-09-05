package com.egova.heroku.SqlExecute_Jersey.utils;

import java.io.Serializable;

public class SqlExecuteBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8995716603132512565L;

	private String sql;
	
	private Integer executeType;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getExecuteType() {
		return executeType;
	}

	public void setExecuteType(Integer executeType) {
		this.executeType = executeType;
	}

	@Override
	public String toString() {
		return "SqlExecuteBean [sql=" + sql + ", executeType=" + executeType + "]";
	}
}
