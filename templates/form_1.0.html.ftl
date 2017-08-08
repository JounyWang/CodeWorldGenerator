<#include "./func.ftl">
<#assign entity_name>${table.name}</#assign>
<!-- Generated by ${author} on ${now?string("yyyy-MM-dd HH:mm:ss")} -->
<div class="box" id="app">
	<div class="box-header with-border">
		<h3 class="box-title">${table.remarks}</h3>
	</div>
<!-- form begin -->
<#assign form_name>${entity_name}_form</#assign>
<form name="${form_name}" @submit.prevent="save" method="POST" action="">
	<div class="box-body">
<#list table.columns as column>
	<#if column.element??>
		<#assign column_name>${column.name}</#assign>
		<#assign object_id>${to_object_id(column_name, 'obj', '')}</#assign>
		<#assign bind_id>item.${to_variable_name(column_name)}</#assign>
		<#if column.element.class.simpleName == 'Hidden'>
			<input id="${object_id}" type="hidden" v-model="${bind_id}" name="${column_name}" value=""/>	
		<#else>
		<div class="form-group">		
			<label for="${object_id}">
				<#if column.remarks?? && column.remarks!="">${column.remarks}<#else>${column_name}</#if><#if !column.nullable>*</#if>
			</label>
			<#switch column.element.class.simpleName>
				<#case 'Select'>
			<select id="${object_id}" v-model="${bind_id}" name="${column_name}" class="form-control">
					<#if column.element.options??>
						<#list column.element.options as option>
				<option value="${option.value}">${option.name}</option>
						</#list>
					</#if>
			</select>
					<#break>
				<#case 'DatePicker'>
			<div class="input-group date">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>
				<input id="${object_id}" type="text" data-provide="datepicker" data-date-format="yyyy-mm-dd" class="form-control pull-right datepicker">
			</div>
					<#break>
				<#case 'DateMask'>
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>
				<input id="${object_id}" type="text" class="form-control" data-inputmask="'alias': 'yyyy-mm-dd'" data-mask>
			</div>
					<#break>					
				<#case 'TextArea'>
			<textarea id="${object_id}" v-model="${bind_id}" name="${column_name}" cols="64" rows="4" class="form-control"<#if !column.nullable> required</#if>></textarea>
					<#break>
				<#default>
			<input id="${object_id}" type="<#if is_number(column.type)>number<#else>text</#if>" v-model="${bind_id}" name="${column_name}" placeholder="" value="" class="form-control"<#if !column.nullable> required</#if>/>
			</#switch>
		</div>
		</#if>
	</#if>
</#list>
	</div>
	<div class="box-footer">
		<button type="submit" class="btn btn-default">提交</button>	
	</div>
</form>
<!-- form end -->
</div>
<script>
<#assign load_url>./json/${entity_name}.json</#assign>
<#assign save_url>./${entity_name}.do</#assign>
app = new Vue({
	el: '#app',
	data: {
		item: {}
	},
	methods: {
		load: function(){
			let id = router.params.id;
			if(id){
				$.get('${load_url}?rand='+Math.random(), function(item){
					console.log('loaded.');
					app.item = item;
				});
			}
			else{
				this.item = {
	<#assign flag=true>
	<#list table.columns as column>
		<#if column.element?? && column.element.class.simpleName=='Select' && column.defaultValue??>
					<#if !flag>, </#if>
					${to_object_id(column.name, '', '')}: "${column.defaultValue}"
			<#assign flag=false>
		</#if>
	</#list>
				};
			}
		},
		save: function(e){
			$.ajax({
				url: '${save_url}',
				type: this.item.id?'PUT':'POST',
				data: JSON.stringify(this.item)
			}).done(function(data){
				console.log('Saved!');
			});			
		}
	},
	mounted: function(){
	<#list table.columns as column>
		<#if column.element?? && column.element.class.simpleName=='DateMask'>
		$('#${to_object_id(column.name, 'obj', '')}').inputmask('yyyy-mm-dd', {yearrange:{yearrange: 1900, maxyear: 2099}});
		</#if>
	</#list>	
		console.log('mounted...');
	}
});
app.load();
</script>