{
	"options":
	[
	<#list 1..5 as i>
		<#assign json="{">
		<#assign flag=false>
		<#list table.columns as column>
			<#if column.display??>
				<#assign k=column.name>
				<#assign v="">
				<#switch column.type>
					<#case 12>
						<#assign v>"${column.remarks}${i?counter}"</#assign>
						<#break>	
					<#case 91>
						<#assign v>"${now?string("yyyy-MM-dd HH:mm:ss")}"</#assign>
						<#break>
					<#case 92>
						<#assign v>"${now?string("yyyy-MM-dd HH:mm:ss")}"</#assign>
						<#break>
					<#default>
						<#assign v>${i?counter}</#assign>					
				</#switch>
				<#if column.element??>
					<#if column.element.class.simpleName=="Select">
						<#assign size=column.element.options?size>
						<#assign v>"${column.element.options[i%size].name}"</#assign>		
					</#if>		
				</#if>
				<#assign json>${json}<#if flag>, </#if>"${k}":${v}</#assign>
				<#assign flag=true>
			</#if>
		</#list>
		<#assign json=json+"}">${json}<#if i?has_next>, </#if>
	</#list>
	]
}