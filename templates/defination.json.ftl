<#include "./func.ftl">
{
	"name": "${table.name}",
	"remarks": "${table.remarks}",
	"columns": [
<#list table.columns as column>
		{
			"name": "${column.name}",
			<#if column.unsigned>"unsigned": true,</#if>
			"type": 
			<#switch column.type>
				<#case -6>
					"tinyint"
					<#break>
				<#case 5>
					"smallint"
					<#break>
				<#case -5>
					"bigint"
					<#break>
				<#case 4>
					"int"
					<#break>
				<#case 91>
					"date"
					<#break>
				<#default>
					"varchar"
			</#switch>
			,
			"description": "${column.remarks}<#if column.element?? && column.element.options??> {<#list column.element.options as option>${option.value}:\"${option.name}\"<#sep>,</#list>}</#if>",
			"nullable": <#if column.nullable>true<#else>false</#if>,		
		<#if column.primaryKey>
			"primary_key": true,
			"auto_increment": true,
		</#if>
		<#if column.defaultValue??>
			"default_value": "${column.defaultValue}",
		</#if>	
			"size": ${column.size},
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
	]
}