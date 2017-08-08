<#include "func.ftl"/>
{
	"data":
	{
	<#list table.columns as column>
		"${lower_underscore(column.name)}":
		<#if is_number(column.type)>
			<#if column.dictionary??>
				<#list column.dictionary as key, value>
					${key}
					<#break>
				</#list>
			<#else>
				1
			</#if>
		<#elseif is_date(column.type)>
			"${now?string('yyyy-MM-dd HH:mm:ss')}"
		<#else>
			"${column.remarks}"
		</#if>
		<#sep>,</#sep>
	</#list>
	}
}