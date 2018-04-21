<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料树</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

<link rel="stylesheet" href="${ctx }/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/jquery.contextmenu.css">
<script type="text/javascript" src="${ctx}/scripts/ztree/js/jquery.ztree.core-3.5.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script src="${ctx}/scripts/jquery.contextmenu.js"></script>
	    

</head>

<script type="text/javascript">
var check_enable = '${showList}'=='0' ? ('${multi}'=='1'? true:false):false;
$(document)
.ready(
		function() {

							var setting = {
								data : {
									simpleData : {
										enable : true,
										idKey:'id',
										pIdKey:'pid'
									}
								},
								check: {
									enable: check_enable,
									chkboxType : {
										"Y" : "s",
										"N" : "ps"
									}
								},
								
								callback:{
									onClick: zTreeOnClick,
									onCheck: zTreeOnCheck,
									onNodeCreated: zTreeOnNodeCreated
								} 

							};
							var	zNodes = [];
							if('${treeJson}'!=''){
								zNodes = eval('${treeJson}');
							}
							var treeObj =$.fn.zTree.init($("#ztree"), setting, zNodes);
							treeObj.expandAll(true);

		});
		
function zTreeOnClick(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var id=treeNode.id;
	if('${showList}'=='0'){
		//隐藏右边列表，从左侧树选择
		var jsonv = {'id':treeNode.id,'pid':treeNode.pid,'name':treeNode.name,'isParent':treeNode.isParent};
		if(!check_enable){
			//只有树且单选的时候，点击节点触发选择事件（修改array中的值）
			clearJson();
			parent.jsonArray.push(jsonv);
		}else{
			//多选
			var nodecheck = treeNode.checked;
			treeObj.checkNode(treeNode, !nodecheck, true);
			pushJson(jsonv,nodecheck);
		}
	}else{
		parent.document.getElementById("rightframe").src="${ctx}/plugin_selectList.emi?id="+id+"&treeType=${treeType}&multi=${multi}";
	}

}

function zTreeOnCheck(event, treeId, treeNode) {
	var id=treeNode.id;
	if('${showList}'=='0'){
		//隐藏右边列表，从左侧树选择
		var jsonv = {'id':treeNode.id,'pid':treeNode.pid,'name':treeNode.name,'isParent':treeNode.isParent};
		if(check_enable){
			//多选
			var nodecheck = treeNode.checked;
			pushJson(jsonv,!nodecheck);
		}
	}else{
		parent.document.getElementById("rightframe").src="${ctx}/plugin_selectList.emi?id="+id+"&treeType=${treeType}&multi=${multi}";
	}
}

function zTreeOnNodeCreated(event, treeId, treeNode){
	//节点生成时，初始化选中状态
	if(check_enable){
		for(var i=0;i<parent.selectedArray.length;i++){
			if(parent.selectedArray[i]==treeNode.id){
				zTreeOnClick(event, treeId, treeNode);
				parent.selectedArray.splice(i,1);
				break;
			}
		}
	}
}

//把选择的值放入
function pushJson(jsonv,remove){
	var hasv = false;
	for(var i=0;i<parent.jsonArray.length;i++){
		if(parent.jsonArray[i]['id']==jsonv['id']){
			if(remove){
				parent.jsonArray.splice(i,1);
			}else{
				hasv = true;
    			break;
			}
			
		}
	}
	if(!hasv && !remove){
		parent.jsonArray.push(jsonv);
	}
}
//清除array
function clearJson(){
	parent.jsonArray.length=0;
}
	
</script>





<body>
	<form action="" name="myform" id="myform" method="post">
		<ul id="ztree" class="ztree"></ul>
	</form>	

</body>
</html>