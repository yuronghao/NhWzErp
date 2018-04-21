/**
 * 导出列表
 */
function emi_export(){
	var ths = document.getElementsByTagName("th"); 
	var column_obj = {};
	for(var i=0;i<ths.length;i++){
		var th = ths[i];
		var column_name = th.getAttribute("exp_column");
		if(column_name && column_name!=''){
			column_obj[column_name] = th.innerHTML;
		}
	}
	//创建隐藏域，放导出的中英文名字
	if(!document.getElementById('emi_export_column')){
		var input = document.createElement("input");
		input.type = 'hidden';
		input.id = 'emi_export_column';
		input.name = 'emi_export_column';
		document.forms[0].appendChild(input);
	}
	document.getElementById('emi_export_column').value= JSON.stringify(column_obj);
	
	var e_url = 'pno=0&exportData=1';
	var o_action = document.forms[formIndex].action;
	o_action = o_action.indexOf('?')>0?(o_action+'&'+e_url):(o_action+'?'+e_url);
	document.forms[0].action = o_action;
	document.forms[0].submit();
}

