<#include "./func.ftl">
package ${namespace};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ${namespace?replace('.service', '.entity')}.${upper_camel(table.name)};
import ${namespace?replace('.service', '.entity')}.Pagination;
import ${namespace?replace('.service', '.entity')}.PagedList;
import ${namespace?replace('.service', '.mapper')}.${upper_camel(table.name)}Mapper;


/**
* <p>The Service of ${upper_camel(table.name)}<br/>
* Created on ${now?string("yyyy-MM-dd HH:mm:ss")}
* @author ${author}
*/
<#assign mapper>${lower_camel(table.name)}Mapper</#assign>
<#assign Entity>${upper_camel(table.name)}</#assign>
<#assign entity>_${lower_camel(table.name)}</#assign>
<#assign entities>_${lower_camel(pluralize(table.name))}</#assign>

@Service
public class ${upper_camel(table.name)}Service {

	@Autowired
	private ${upper_camel(table.name)}Mapper ${lower_camel(table.name)}Mapper;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ${upper_camel(table.name)} find(long id) {
		return ${mapper}.findById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public PagedList<${upper_camel(table.name)}> search(String nameLikes, Short status, Pagination pagination) {
		int offset = 0;
		int size = 1000;
		if(pagination != null){
			size = pagination.getSize();
			offset = (pagination.getPage() - 1) * size;
		}
		List<${upper_camel(table.name)}> items = ${mapper}.search(nameLikes, status, offset, size);
		Integer count = ${mapper}.count(nameLikes, status, offset, size);
		if (count != null) {
			pagination.setResultCount(count);
		}
		PagedList<${Entity}> ${entities} = new PagedList<${Entity}>();
		${entities}.setItems(items);
		${entities}.setPagination(pagination);
		return ${entities};
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<${upper_camel(table.name)}> list() {
		return ${mapper}.list();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long insert(${Entity} ${entity}) {
		return ${mapper}.insert(${entity});
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(${Entity} ${entity}) {
		${mapper}.update(${entity});
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {
		${mapper}.delete(id);
	}

}