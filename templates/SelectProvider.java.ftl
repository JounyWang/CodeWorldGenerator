<#include "./func.ftl">
package ${namespace};

/**
* <p>The SelectProvider of ${upper_camel(table.name)}<br/>
* Created on ${now?string("yyyy-MM-dd HH:mm:ss")}
* @author ${author}
*/
public class ${upper_camel(table.name)}SelectProvider {

	public String count(String nameLikes, Short status, int offset, int rows){
		return sql(nameLikes, status, offset, rows, true);
	}

	public String search(String nameLikes, Short status, int offset, int rows){
		return sql(nameLikes, status, offset, rows, false);
	}
	
	private String sql(String nameLikes, Short status, int offset, int size, boolean isCount){
		StringBuffer sb = new StringBuffer();
		sb.append("select " + (isCount ? "count(*)" : "*") + " from `${table.name}` where 1");
		if (nameLikes != null) {
			sb.append(" and name like '%" + nameLikes + "%'");
		}
		if (status != null) {
			sb.append(" and status=" + status + "");
		}
		if (!isCount) {
			sb.append(" limit " + offset + ", " + size);
		}
		return sb.toString();
	}
}