<#function upper_camel str>
	<#return to_name(str, '', '', -1)>
</#function>

<#function lower_camel str>
	<#return to_name(str, '', '', 0)>
</#function>

<#function upper_underscore str>
	<#return str?split('\\w')?join('_')?upper_case>
</#function>

<#function lower_underscore str>
	<#return str?split('\\w')?join('_')?lower_case>
</#function>

<#function pascal_case str>
	<#return to_name(str, '', '', 0)>
</#function>

<#function to_object_id str prefix postfix>
	<#return to_name(str, prefix, postfix, -1)>
</#function>

<#function to_variable_name str>
	<#return to_name(str, 'obj', '', -1)>
</#function>

<#function to_json_key str>
	<#return str>
</#function>

<#function to_function_name str prefix postfix i>
	<#return to_name(str, prefix, postfix, 0)>
</#function>

<#function to_name str prefix postfix i>
	<#local name=prefix>
	<#list str?split("_") as word>
		<#if word_index==i>
			<#local name=name+word>
		<#else>
			<#local name=name+word?capitalize>
		</#if>
	</#list>
	<#local name=name+postfix>
	<#return name>
</#function>

<#function to_label str>
	<#return str>
</#function>

<#function pluralize str>
	<#local plural=str+"s">
	<#return plural>
</#function>

<#function java_type type>
	<#switch type>
		<#case 4>
			<#!--integer-->
		<#case 5>
			<#!--smallint-->
			<#local java_type="int">
			<break>
		<#case -5>
			<#!--bigint-->
			<#local java_type="long">
			<#break>
		<#case -6>
			<#!--tinyint-->
			<#local java_type="short">
			<#break>
		<#case 91>
			<#local java_type="Date">
			<#break>
		<#default>
			<#local java_type="String">
	</#switch>
	<#return java_type>
</#function>

<#function is_number type>
	<#switch type>
		<#case 4>
		<#case 5>
		<#case -5>
		<#case -6>
			<#return true>
			<#break>
		<#default>
			<#return false>
	</#switch>
</#function>

<#function is_date type>
	<#switch type>
		<#case 91>
			<#return true>
			<#break>
		<#default>
			<#return false>
	</#switch>
</#function>