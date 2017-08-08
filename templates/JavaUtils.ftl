<#function to_data_type type>
	<#local data_type="">
	<#switch type>
		<#case 4>
			<#local data_type="int">
			<#break>
		<#case 12>
			<#local data_type="String">
			<#break>
		<#default>
			<#local data_type="long">
	</#switch>
	<#return data_type>
</#function>

<#function to_property_name str>
	<#local class_name="">
	<#list str?split("_") as word>
		<#if word?is_first>
			<#local class_name=class_name+word?lower_case>
		<#else>
			<#local class_name=class_name+word?capitalize>
		</#if>
	</#list>
	<#return class_name>
</#function>

<#function to_class_name str>
	<#local class_name="">
	<#list str?split("_") as word>
		<#local class_name=class_name+word?capitalize>
	</#list>
	<#return class_name>
</#function>