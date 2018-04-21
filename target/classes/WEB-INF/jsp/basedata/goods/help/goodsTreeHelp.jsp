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
								
								callback:{
									onClick: zTreeOnClick
								} 

							};
							var	zNodes = ${classify};
							var treeObj =$.fn.zTree.init($("#ztree"), setting, zNodes);
							

		});
		
function zTreeOnClick(event, treeId, treeNode) {

	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var sNodes = treeObj.getSelectedNodes();
	if (sNodes.length > 0) {
		level = sNodes[0].level;
		parentNode = sNodes[0].getParentNode();
		if(parentNode !=null){
			parentNodeCode=parentNode.code;
		}
		
		isParent = sNodes[0].isParent;
	}
	
	var id=treeNode.id;
	var type=treeNode.type;
	parent.document.getElementById("rtframe").src="${ctx}/wms/goods_getRightGoodsHelp.emi?id="+id+"&type="+type;

}
	
	
</script>





<body>
	<form action="" name="myform" id="myform" method="post">
		<ul id="ztree" class="ztree"></ul>
	</form>	

</body>
</html>