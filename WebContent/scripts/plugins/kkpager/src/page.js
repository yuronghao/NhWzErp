function exportData_all(){
	var e_url = 'pno=0&exportData=1';
	var o_action = document.forms[formIndex].action;
	o_action = o_action.indexOf('?')>0?(o_action+'&'+e_url):(o_action+'?'+e_url);
	document.forms[0].action = o_action;
	document.forms[0].submit();
}

//init
$(function(){
	var widget_page_export = false;
	if(typeof(page_export) != "undefined" && page_export!=undefined && page_export){
		widget_page_export = page_export;
	}
	
	var widget_page_size = false;
	if(typeof(isShowPageSize) != "undefined" && isShowPageSize!=undefined && isShowPageSize){
		widget_page_size = isShowPageSize;
	}
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	if(totalRecords>0){
		kkpager.generPageHtml({
			pno : pageIndex,
			//总页码
			total : pageCount,
			//总数据条数
			totalRecords : totalRecords,
			//链接前部
			hrefFormer : 'pager_test',
			//链接尾部
			hrefLatter : '.html',
			//是否有导出按钮
			widget_page_export:widget_page_export,
			isShowPageSize:widget_page_size,
			getLink : function(n){
				var pageSize = "";
				if(document.getElementById("pageSize_input")){
					pageSize = document.getElementById("pageSize_input").value;
				}
				
				if(document.forms[formIndex].action.indexOf('?')>0){
					return document.forms[formIndex].action+"&pno="+n+"&pageSize="+pageSize;
				}else{
					return document.forms[formIndex].action+"?pno="+n+"&pageSize="+pageSize;
				}
			}
		});
	}else{
		document.getElementById('kkpager').style.textAlign='center';
		document.getElementById('kkpager').innerHTML='<font color="red">暂无数据</font>';
	}
});
