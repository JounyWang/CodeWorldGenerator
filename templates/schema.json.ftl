<#include "./func.ftl">
{
	"title": "${table.name}",
	"type": "object",
	"description": "${table.remarks}",
	"properties": {
<#list table.columns as column>
		"${column.name}": {
		<#if is_number(column.type)>
			"type": "number",
			<#if column.element?? &&  column.element.options??>
			"enum": [<#list column.element.options as option>${option.value}<#sep>, </#list>],
			</#if>
		<#elseif is_date(column.type)>
			"type": "string",
			"pattern": "^/\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}/$",
		<#else>
			"type": "string",
		</#if>
			"description": "${column.remarks}<#if column.element?? && column.element.options??> {<#list column.element.options as option>${option.value}:\"${option.name}\"<#sep>,</#list>}</#if>",
		<#if column.nullable>
			"nullable": true,
		</#if>
		<#if column.primaryKey>
			"primaryKey": true,
		</#if>
		<#if column.defaultValue??>
			"defaultValue": "${column.defaultValue}",
		</#if>	
		<#if column.size != 0>
			"size": ${column.size},
		</#if>
			"displays": {
		<#if column.element??>
				"form": {
					"element" : "${column.element.class.simpleName}"
				},
		</#if>
		<#if column.display??>
				"list": {
					<#if column.display.element??>
					"element" : "${column.display.element.class.simpleName}",
					<#else>
					"text": "{{ ${lower_underscore(column.name)} }}",
					</#if>
					"attributes": {
						<#assign sep=''>
						<#if column.display.width??>
							${sep}"width": "${column.display.width}"
							<#assign sep=", ">
						</#if>
						<#if column.display.style??>
							${sep}"class": "${column.display.style}"
							<#assign sep=", ">
						</#if>
					}
				},
		</#if>
				"detail": {}
			}
		}
		<#sep>,
</#list>
	},
	"required": [ 
		<#assign flag=false><#list table.columns as column>
		<#if column.primaryKey>
			<#if flag>, </#if>"${column.name}"<#assign flag=true></#if>
		</#list>
	]
}