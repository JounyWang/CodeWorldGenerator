<#include "./func.ftl">
package ${namespace};

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import ${namespace?replace('.mapper', '.entity')}.${upper_camel(table.name)};
import ${namespace?replace('.mapper', '.provider')}.${upper_camel(table.name)}SelectProvider;


/**
* <p>The Mapper of ${upper_camel(table.name)}
* <p>
* Created on ${now?string("yyyy-MM-dd HH:mm:ss")}
* @author ${author}
*/
@Repository
public interface ${upper_camel(table.name)}Mapper {
	@Select("SELECT * FROM `${lower_underscore(table.name)}`")
	public List<${upper_camel(table.name)}> list();

	@SelectProvider(type=${upper_camel(table.name)}SelectProvider.class, method="search")
	@Results(value = {
		<#list table.columns as column>
		@Result(<#if column.primaryKey>id = true, </#if>column = "${column.name}", property = "${lower_camel(column.name)}")<#sep>,</#sep>
		</#list>
	})
	public List<${upper_camel(table.name)}> search(String nameLikes, Short status, int offset, int rows);

	@SelectProvider(type=${upper_camel(table.name)}SelectProvider.class, method="count")
	public Integer count(String nameLikes, Short status, int from, int rows);

	@Select("SELECT * FROM `${lower_underscore(table.name)}` where `id`=${'#'}{id}")
	@Results(value = {
		<#list table.columns as column>
		@Result(<#if column.primaryKey>id = true, </#if>column = "${column.name}", property = "${lower_camel(column.name)}")<#sep>,</#sep>
		</#list>
	})	
	public ${upper_camel(table.name)} findById(<#list table.columns as column><#if column.primaryKey>${java_type(column.type)} ${lower_camel(column.name)}</#if></#list>);

	@Insert("INSERT INTO `${lower_underscore(table.name)}`(<#list table.columns as column>`${lower_underscore(column.name)}`<#sep>, </#list>) values (<#list table.columns as column>${'#'}{${lower_camel(column.name)}}<#sep>, </#list>)")
	@Options(keyProperty = "id", useGeneratedKeys = true)
	@SelectKey(before = false, statement = { "SELECT last_insert_id()" }, keyProperty = "id", resultType = Long.class)
	public long insert(${upper_camel(table.name)} ${lower_camel('a_'+table.name)});

	@Update("UPDATE `${lower_underscore(table.name)}` SET <#list table.columns as column><#if !column.primaryKey>`${lower_underscore(column.name)}`=${'#'}{${lower_camel(column.name)}}<#sep>, </#if></#list> where <#list table.columns as column><#if column.primaryKey>`${lower_underscore(column.name)}`=${'#'}{${lower_camel(column.name)}}</#if></#list>")
	public void update(${upper_camel(table.name)} ${lower_camel('a_'+table.name)});

	@Update("UPDATE `${lower_underscore(table.name)}` SET status=${'#'}{status} WHERE id=${'#'}{id}")
	public void updateStatus(long id, short status);

	@Delete("DELETE FROM ${lower_underscore(table.name)} WHERE id=${'#'}{id}")
	public void delete(<#list table.columns as column><#if column.primaryKey>${java_type(column.type)} ${lower_camel(column.name)}</#if></#list>);
}
