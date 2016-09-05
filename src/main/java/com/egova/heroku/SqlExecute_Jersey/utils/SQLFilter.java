package com.egova.heroku.SqlExecute_Jersey.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.druid.util.JdbcConstants;

public abstract class SQLFilter {

	public static List<String> CAN_DELETE_TABLE = new ArrayList<String>();

	static {
		CAN_DELETE_TABLE.add("TOSTATPARKINFO");
		CAN_DELETE_TABLE.add("TOSTATPARKOPERATOR");
		CAN_DELETE_TABLE.add("TOSTATPARKCARFLOW");
		CAN_DELETE_TABLE.add("TOSTATPARKTEMP");
	}

	public static List<SqlExecuteBean> filter(String sql) {
		String dbType = JdbcConstants.POSTGRESQL;
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
		PGSchemaStatVisitor pgVisitor = new PGSchemaStatVisitor();
		List<SqlExecuteBean> value = new ArrayList<>();
		SqlExecuteBean bean ;
		for (SQLStatement sqlStatement : stmtList) {
			sqlStatement.accept(pgVisitor);
			bean = new SqlExecuteBean();
			//默认的SQL是SELECT
			bean.setExecuteType(1);
			Map<Name, TableStat> tables = pgVisitor.getTables();
			for (Name key : tables.keySet()) {

				TableStat stat = tables.get(key);

				if (stat.getDeleteCount() > 0) {
					boolean canDelete = false;
					for (String tableName : CAN_DELETE_TABLE) {
						if (tableName.equalsIgnoreCase(key.getName())) {
							canDelete = true;
						}
					}
					if (!canDelete) {
						throw new RuntimeException("不能删除表数据:" + key.getName());
					}
				}
				if (stat.getDropCount() > 0) {
					throw new RuntimeException("不能删除表:" + key.getName());
				}
				bean.setExecuteType(2);

				if (stat.getSelectCount() > 0) {
					bean.setExecuteType(1);
				}
			}
			bean.setSql(sqlStatement.toString());
			value.add(bean);
		}
	
		return value;
	}

	public static void main(String[] args) {
		System.out.println(filter("select * from tcuser;select *,2 from tcuser"));
	}
}
