<#include "func.ftl">
package ${namespace};

import java.util.Date;

/**
* <p>
* The entity of ${upper_camel(table.name)}
* <p>
* Created on ${now?string("yyyy-MM-dd HH:mm:ss")}
* @author ${author}
*/
public class ${upper_camel(table.name)}{

<#list table.columns as column>
	<#if column.remarks?? && column.remarks!="" >/** ${column.remarks} */</#if>
	private ${java_type(column.type)} ${lower_camel(column.name)};
</#list>
<#list table.columns as column>
	public void set${upper_camel(column.name)}(${java_type(column.type)} ${lower_camel(column.name)}_){
		this.${lower_camel(column.name)} = ${lower_camel(column.name)}_;
	}
	
	public ${java_type(column.type)} get${upper_camel(column.name)}(){
		return this.${lower_camel(column.name)};
	}
	
</#list>
}