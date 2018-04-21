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
<script type="text/javascript" src="${ctx}/scripts/emiwms.js"></script>
	    

</head>

<script type="text/javascript">
var treeObj;
$(document)
.ready(
		function() {

							var setting = {
								 check : {
									enable : true
								},
								data : {
									simpleData : {
										enable : true
									}
								},
								check: {
									enable: false,
									chkboxType : {
										"Y" : "s",
										"N" : "ps"
									}
								},
								async:{
									enable:true,
									url:"${ctx}/wms/warehouse_getAllocationTree4Async.emi",
									autoParam:["id","pId","name"]
									//dataFilter: filter

								}, 
								
								callback:{
									onClick: zTreeOnClick,
									onAsyncSuccess: zTreeOnAsyncSuccess
								} 

							};
							var	zNodes = ${warehouseTree};
							treeObj =$.fn.zTree.init($("#ztree"), setting, zNodes);
							asyncAll();
							//treeObj.expandAll(true); 
							//treeObj.expandNode(treeObj.getNodes(),true,false, true);
							
							//debugger;
							//treeObj.reAsyncChildNodes(treeObj.getNodesByParam("id", "8EFD10AA-A92F-45D8-A50E-BDD2523AB8F5", null)[0], "refresh", true);
							//treeObj.getNodesByParam("id", "8EFD10AA-A92F-45D8-A50E-BDD2523AB8F5", null)[0].zAsync=true;
							//treeObj.getNodesByParam("id", "8EFD10AA-A92F-45D8-A50E-BDD2523AB8F5", null)[0].name="newName";
							//treeObj.updateNode(treeObj.getNodesByParam("id", "8EFD10AA-A92F-45D8-A50E-BDD2523AB8F5", null)[0]);
		}); 
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		//childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}


function asyncAll() {
	asyncNodes(treeObj.getNodesByParam("pId", "0", null));
}
function asyncNodes(nodes) {
	if (!nodes) return;
	for (var i=0, l=nodes.length; i<l; i++) {
		if (nodes[i].isParent && nodes[i].zAsync) {
			asyncNodes(nodes[i].children);
		} else {
			goAsync = true;
			setTimeout(reAsyncZtreeNodes(nodes[i]), 10);//设置延迟异步提交，防止过多线程阻塞
			
		}
	}
}
function reAsyncZtreeNodes(node){
	treeObj.reAsyncChildNodes(node, "refresh", true);
}

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	if(treeNode.children.length==0){
		console.log(treeNode.children.length);
		treeNode.isParent=false;
		treeObj.updateNode(treeNode);
	}
}

function zTreeOnClick(event, treeId, treeNode) {

	var sNodes = treeObj.getSelectedNodes();
	var name;
	if (sNodes.length > 0) {
		level = sNodes[0].level;
		parentNode = sNodes[0].getParentNode();
		if(parentNode !=null){
			parentNodeCode=parentNode.code;
			name=parentNode.name;
		}
		
		isParent = sNodes[0].isParent;
	}
	
	var id=treeNode.id;
	if(name=='全部'){
		name='';
	}
	parent.document.getElementById("rightframe").src="${ctx}/wms/warehouse_getRightGoodsAllocation.emi?id="+id+"&name="+encodeURI(encodeURI(name));
	
}
	
	
</script>





<body>
	<form action="" name="myform" id="myform" method="post">
		<ul id="ztree" class="ztree"></ul>
	</form>	

</body>
</html>