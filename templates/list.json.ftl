<#include "func.ftl"/>
{
	"data":
	[
	<#list 1..5 as i>
		<#assign flag=false/>
		{
		<#list table.columns as column>
			"${to_json_key(column.name)}": 
			<#if is_number(column.type)>
				<#if column.primaryKey>
					${i_index}
				<#else>
					<#if column.dictionary??>
						<#list column.dictionary as key, value>
							${key}
							<#break>
						</#list>
					<#else>
						1
					</#if>
				</#if>
			<#elseif is_date(column.type)>
				"${now?string('yyyy-MM-dd HH:mm:ss')}"
			<#else>
				"${column.remarks}_${i?counter}"
			</#if>
			<#sep>,
		</#list>
		}<#sep>,
	</#list>
	]
	,
	"pagination": {
        "total": 0,
        "pageSize": 10, 
        "page": 1,
        "totalPages": 100
  	}
}