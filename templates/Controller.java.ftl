<#include "./func.ftl">
package ${namespace};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${namespace?replace('.controller', '.entity')}.${upper_camel(table.name)};
import ${namespace?replace('.controller', '.entity')}.Pagination;
import ${namespace?replace('.controller', '.entity')}.PagedList;
import ${namespace?replace('.controller', '.service')}.${upper_camel(table.name)}Service;

<#assign service>${lower_camel(table.name)}Service</#assign>
<#assign Entity>${upper_camel(table.name)}</#assign>
<#assign entity>${lower_camel(table.name)}_</#assign>
<#assign entities>${pluralize(lower_camel(table.name))}</#assign>

/**
* <p>The Controller of ${upper_camel(table.name)}<br>
* Created on ${now?string("yyyy-MM-dd HH:mm:ss")}
* @author ${author}
*/
@Controller
public class ${upper_camel(table.name)}Controller {
	@Autowired
	private ${upper_camel(table.name)}Service ${lower_camel(table.name)}Service;

	@RequestMapping(value = "/${entities}", method = RequestMethod.GET)
	public @ResponseBody ModelMap search(@RequestParam(value = "nameLikes", required = false) String nameLikes,
			@RequestParam(value = "status", required = false) Short status,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize) {
		Pagination pagination = new Pagination();
		pagination.setSize(pageSize);
		pagination.setPage(page);
		PagedList<${Entity}> ${entities} = ${service}.search(nameLikes, status, pagination);
		ModelMap result = new ModelMap();
		result.put("${entities}", ${entities});
		return result;
	}

	@RequestMapping(value = "/${lower_camel(table.name)}/{id}", method = RequestMethod.GET)
	public @ResponseBody ModelMap findById(@PathVariable Long id) {
		${Entity} ${entity} = ${service}.find(id);
		ModelMap result = new ModelMap();
		result.put("${lower_camel(table.name)}", ${entity});
		return result;
	}

	@RequestMapping(value = "/${lower_camel(table.name)}", method = RequestMethod.POST)
	public @ResponseBody ModelMap save(@RequestBody ${Entity} ${entity}) {
		long id = ${service}.insert(${entity});
		ModelMap result = new ModelMap();
		result.put("id", id);
		return result;
	}

	@RequestMapping(value = "/${lower_camel(table.name)}/{id}", method = RequestMethod.PUT)
	public @ResponseBody ModelMap update(@PathVariable Long id, @RequestBody ${Entity} ${entity}) {
		${service}.update(${entity});
		ModelMap result = new ModelMap();
		return result;
	}

	@RequestMapping(value = "/${lower_camel(table.name)}/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ModelMap remove(@PathVariable Long id) {
		${service}.delete(id);
		ModelMap result = new ModelMap();
		return result;
	}

}
//end
