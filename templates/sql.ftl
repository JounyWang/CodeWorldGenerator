drop table if exists `${table.name}`;
create table `${table.name}` (
<#list table.columns as column>
	`${column.name}`	
	<#switch column.type>
		<#case -6>
		tinyint<#if column.unsigned> unsigned</#if>
			<#break>
		<#case 5>
		smallint<#if column.unsigned> unsigned</#if>
			<#break>
		<#case -5>
		bigint<#if column.unsigned> unsigned</#if>
			<#break>
		<#case 14>
		int(${column.size})<#if column.unsigned> unsigned</#if>
			<#break>			
		<#case 91>
		datetime<#break>			
		<#case 93>
		timestamp<#break>
		<#default>
		varchar(${column.size})
	</#switch>
	<#if !column.nullable>
		not null
	</#if>
	<#if !column.nullable && column.defaultValue??>
		default '${column.defaultValue}'
	</#if>
	<#if column.autoIncrement>
		auto_increment
	</#if>
		comment '${column.remarks}<#if column.element?? && column.element.options?? && column.element.class.simpleName=='Select'><#assign options=column.element.options> (
			<#list options as option>
				${option.value}:"${option.name}"<#sep>; </#list>
		)</#if>',
</#list>
	primary key(
	<#list table.columns as column>
		<#assign flag=false>
		<#if column.primaryKey>
			<#if flag>, </#if>${column.name}
			<#assign flag=true>
		</#if>
	</#list>
	)
);
alter table `${table.name}` comment = '${table.remarks}';