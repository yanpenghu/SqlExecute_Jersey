package com.egova.heroku.SqlExecute_Jersey.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;

import com.egova.heroku.SqlExecute_Jersey.utils.SQLFilter;
import com.egova.heroku.SqlExecute_Jersey.utils.SqlExecuteBean;
import com.egova.heroku.SqlExecute_Jersey.utils.http.HttpClientHelper;

@Path("sql")
public class SQLExecuteController {

	@POST
	@Path("execute")
	@Produces(MediaType.APPLICATION_JSON)
	public String execute(@FormParam("url") String url, @FormParam("parkID") Integer parkID,
			@FormParam("parkCode") String parkCode, @FormParam("sql") String sql) throws Exception {

		List<SqlExecuteBean> sqlBeanList = SQLFilter.filter(sql);
		if (!CollectionUtils.isEmpty(sqlBeanList) && sqlBeanList.size() > 1) {
			throw new Exception("暂时一次只能执行一条SQL！");
		}
		Map<String, Object> maps = new HashMap<>();
		maps.put("parkID", parkID);
		maps.put("parkCode", parkCode);
		maps.put("sql", sqlBeanList.get(0).getSql());
		maps.put("executeType", sqlBeanList.get(0).getExecuteType());
		return HttpClientHelper.post(url, maps);
	}

	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	public String Object() throws Exception {
		throw new Exception("暂时一次只能执行一条SQL！");
	}
}
