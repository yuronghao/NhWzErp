$(document).ready(function(){
	//初始化权限，隐藏没有权限的功能元素
	if(sys_isAdmin!=1){
		//1、放出有权限的元素rightFunJson
		if(req_menuCode && rightFunJson!=null){
			//查询所有权限
			var funsInMenu = rightFunJson[req_menuCode];
			//控制 input 和div  如果要改成所有，用*
			var inputElements = document.getElementsByTagName("input"); 
			var divElements = document.getElementsByTagName("div"); 
			var aElements = document.getElementsByTagName("a"); 
			for(var i=0;i<inputElements.length;i++){
				initShow(inputElements[i],funsInMenu);
			}
			for(var i=0;i<divElements.length;i++){
				initShow(divElements[i],funsInMenu);
			}
			for(var i=0;i<aElements.length;i++){
				initShow(aElements[i],funsInMenu);
			}
			
		}
		
	}
});
var tip_editSelect = '请选择要修改的记录！';
var tip_dontMultEdit = '请只选择一条记录进行编辑！';
var tip_stopSelect = '请选择要停用的记录！';
var tip_confirmStop = '确认要停用所选择的记录吗？';
var tip_deleteSelect = '请选择要删除的记录！';
var tip_confirmDelete = '确认要删除所选择的记录吗？';
//全选，取消
function checkAll(name) { 
	var el = document.getElementsByTagName('input');
	var len = el.length;
	for (var i = 0; i < len; i++) {
		if ((el[i].type == "checkbox") && (el[i].name == name)) {
			el[i].checked = true;
		}
	}
}

function clearAll(name) {
	var el = document.getElementsByTagName('input');
	var len = el.length;
	for (var i = 0; i < len; i++) {
		if ((el[i].type == "checkbox") && (el[i].name == name)) {
			el[i].checked = false;
		}
	}
}

/**
 * 检查是否已勾选
 * @param elementsName 元素name
 * @param isDelete 是否是删除的提示 true:删除；默认是编辑的提示
 * @returns {String}
 */
function checkSelectId(elementsName,isDelete,tips){
	var chek=document.getElementsByName(elementsName);
	var gid='';
	var total=0;
	for(var i=0;i<chek.length;i++){
		if(chek[i].checked){
			 total += 1;
			 gid +=chek[i].value+",";
		}
	}
	if(gid.length>0){gid=gid.substring(0, gid.length-1);}
	//勾选了多个
	if(total>1){
		if(isDelete){
			return gid;
		}else{
			$.dialog.alert(tip_dontMultEdit);
		}
		return '';
	}
	else if(total<1){//没有勾选
		if(isDelete){
			$.dialog.alert(tip_deleteSelect);
		}else{
			tips = tips || tip_editSelect;
			$.dialog.alert(tips);
		}
		return '';
	}
	return gid;
}
function changefg(name,all_name)
	{
		var el = document.getElementsByTagName('input'); 
		var len = el.length; 
		var all_ck = true;
		for(var i=0; i<len; i++) { 
			if((el[i].type=="checkbox") && (el[i].name==name)) { 
				all_ck=el[i].checked && all_ck; 
			} 
		} 
		document.getElementsByName(all_name)[0].checked=all_ck;
	}
function initShow(element,funsInMenu){
	if(element.getAttribute("funCode") && element.getAttribute("funCode")!=null && element.getAttribute("funCode")!=''){
		element.style.display="none";
		if(funsInMenu && funsInMenu.length>0){
			for(var i=0;i<funsInMenu.length;i++){
				if(funsInMenu[i].rightCode==element.getAttribute("funCode")){
					element.style.display="";
					break;
				}
			}
		}
	}
	
}

/*
 * 初始化权限
 */
function initSysRight(sys_isAdmin){
	//加载完页面，如果不是管理员，初始化权限  元素自定义属性 emiRight : add/edit/delete/audit/check
	//var sys_isAdmin = "${sessionScope.isAdmin}";
	if(sys_isAdmin!=1){
		//var sys_rightFuns = ${sessionScope.rightFuns};
		//1、先隐藏所有指定元素 TODO
		
		//2、放出有权限的元素
		if(sys_rightCode){
			//查询所有权限
			var fun_value = sys_rightFuns.rightCode;
			alert(fun_value);
		}
	}
}


/**
 * 日期格式化 转成字符串
 */
Date.prototype.pattern=function(fmt) {           
    var o = {           
    "M+" : this.getMonth()+1, //月份           
    "d+" : this.getDate(), //日           
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时           
    "H+" : this.getHours(), //小时           
    "m+" : this.getMinutes(), //分           
    "s+" : this.getSeconds(), //秒           
    "q+" : Math.floor((this.getMonth()+3)/3), //季度           
    "S" : this.getMilliseconds() //毫秒           
    };           
    var week = {           
    "0" : "/u65e5",           
    "1" : "/u4e00",           
    "2" : "/u4e8c",           
    "3" : "/u4e09",           
    "4" : "/u56db",           
    "5" : "/u4e94",           
    "6" : "/u516d"          
    };           
    if(/(y+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));           
    }           
    if(/(E+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);           
    }           
    for(var k in o){           
        if(new RegExp("("+ k +")").test(fmt)){           
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
        }           
    }           
    return fmt;           
};

/**
 * 键入的是否是数值
 */
function checkNumber(){ 
	if(!(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) 
	|| (window.event.keyCode == 13) || (window.event.keyCode == 46) 
	|| (window.event.keyCode == 45))){ 
		window.event.keyCode = 0 ; 
	} 
} 

//lhgdialog加载提示
function showTips(tip){
	if(!tip){
		tip = "数据加载中...";
	}
	$.dialog.tips(tip,600,'loading.gif');
}
function hideTips(tip,func){
	if(!tip){
		tip = "数据加载完毕";
	}
	$.dialog.tips(tip,0.1,'loading.gif',func);
}

/**
 * 判断值是否是数字
 */
function isNumber(val){
	if(!isNaN(val)){
	   return true;
	}else{
	   return false;
	}
}

/*
 * 打开人员选择器
 * selectValue 已选择的值
 * targetElementId 目标元素id
 * targetElementName 目标元素名称
 * title 弹出框标题
 * multiSel 是否可以多选
 * hasAccount 是否只显示有账号的人员
 * doType 发送对象类型
 * forIds 发送对象id
 * personOrUser 返回人员还是用户id  1：用户id 其他：人员id
 * fun 选择了人之后需要执行的方法
 */
function openPersonSelector(selectValue,targetElementId,targetElementName,title,multiSel,hasAccount,doType,forIds,personOrUser,fun){
	var pwdWin = $.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:''+title,
	    width: '800px',
		height: '500px',
		zIndex: 2000,
		content: 'url:flow/select_personSelect.emi?selectValue='+selectValue+'&hasAccount='+hasAccount+'&doType='+doType+'&forIds='+forIds+'&personOrUser='+personOrUser,
		okVal:"确定",
		ok:function(){
			if(!$('#dialog_searchable',this.content.document).val())
            {
                //alert("未选择");//这里不提示了，万一他要清空呢
            }else
            {
            	var nameText = [];
                var idText = [];
                var len = 0;
                $('#dialog_searchable option',this.content.document).each(function(){
	                if(this.getAttribute('selected'))
	                {
	                    nameText.push($(this).text());
	                    idText.push($(this).val());
	                    len ++;
	                }
                });
                if(!multiSel && len>1){
                	$.dialog.alert_e("请不要选择多个");
                	return false;
                }
                $('#'+targetElementName).val(nameText.join(','));
                $('#'+targetElementId).val(idText.join(','));
            }
			//执行传入的方法
			if(fun){
				fun();
			}
		},
		cancelVal:"关闭",
		cancel:true
	});	
}

/**
 * 初始化单据翻页
 * @param table_name 表名
 * @param id_column gid字段名(一般是gid不用改)
 * @param gid	当前数据的gid值
 * @param condition	数据的过滤条件
 * @param [el_id_column] 【非必填】后台接取gid使用的参数名(默认叫gid，如有需要可以修改)
 * @param [div_id] 【非必填】翻页按钮所在的div的id(默认叫emi_page_turning,如有需要可以修改)
 */
function initPageTurning(action,table_name,id_column,gid,condition,el_id_column,div_id){
	var div = document.getElementById("emi_page_turning");//根据规定id找到div
	if(div_id){//如果有自定义的div id，采用自定义的
		div = document.getElementById(div_id);
	}
	var param_gid = 'gid';
	if(el_id_column){//如果有自定义的gid的参数名，采用自定义的
		param_gid = el_id_column;
	}
	if(div){
		var html = '<input type="button" id="first_page_turn" class="btns" value="首张" >'
				+'<input type="button" id="pre_page_turn" class="btns" value="上张" >'
				+'<input type="button" id="next_page_turn" class="btns" value="下张" >'
				+'<input type="button" id="last_page_turn" class="btns" value="末张" >';
		div.innerHTML=html;	
		//var action = div.getAttribute("action");//跳转链接
		$.ajax({
			  data: $("#myform").serialize(),
			  type: 'POST',
			  url: getContextPath()+'/plugin_getPageTurnIds.emi?tableName='+table_name+"&thisGid="+gid+"&idColumn="+id_column+"&condition="+condition,//+JSON.stringify(condition)
			  dataType:'json',
			  success: function(req){
				  //为4个按钮添加点击事件
				  var preurl = action.indexOf('?')>0?(action+'&'):(action+'?');
				  $('#first_page_turn').on('click',function(){//首张
					  if(req.first_id==''){
						  $.dialog.alert('已是第一张');
						  return;
					  }
					  location.href=preurl + param_gid +'='+req.first_id;
				  });
				  $('#pre_page_turn').on('click',function(){//上张
					  if(req.pre_id==''){
						  $.dialog.alert('已是第一张');
						  return;
					  }
					  location.href=preurl + param_gid +'='+req.pre_id;
				  });
				  $('#next_page_turn').on('click',function(){//下张
					  if(req.next_id==''){
						  $.dialog.alert('已是最后一张');
						  return;
					  }
					  location.href=preurl + param_gid +'='+req.next_id;
				  });
				  $('#last_page_turn').on('click',function(){//末张
					  if(req.last_id==''){
						  $.dialog.alert('已是最后一张');
						  return;
					  }
					  location.href=preurl + param_gid +'='+req.last_id;
				  });
			  }
		});
	}
}

//checkbox点击事件
function clickCheck(all_id,check_name){
	var allchecked = true;
	var uck = document.getElementsByName(check_name);
	var allck = document.getElementById(all_id);
	
	for(var i=0;i<uck.length;i++){
		if(!uck[i].checked){
			allchecked = false;
		}
	}
	if(allchecked){
		allck.checked=true;
	}else{
		allck.checked=false;
	}
	
}

//全选按钮
function selectAll(all_id,check_name){
	var ckAll = document.getElementById(all_id);
	var isck = ckAll.checked;
	var uck = document.getElementsByName(check_name);
	for(var i=0;i<uck.length;i++){
		uck[i].checked=isck;
	}
}

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
       
