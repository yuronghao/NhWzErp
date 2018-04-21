<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<link rel="stylesheet" href="${ctx }/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/scripts/ztree/js/jquery.ztree.core-3.5.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/ztree/js/jquery.ztree.excheck-3.5.js"></script>
</head>
<script type="text/javascript">

$(function() {

	var width = $('#createwords').width();
	$('#content').css('width', width + 10 + "px");

});

</script>
<body>
	<div id="container">
		<div id="content">
			<form action="" method="post" id="myform"  class="myform"  onsubmit="return checkdata()">
				<!--按钮栏-->
				<!--内容部分-->
					<!--标题名-->
		                <div id="createwords" style="height: 400px">

					<div class="zTreeDemoBackground fl" style="width: 23%">
						<ul id="ztree" class="ztree" style="min-width: 150px;"></ul>
					</div>
				</div>
				<input type="hidden" id="name" name="name" value="">
				<input type="hidden" id="id" name="id" value="">
              </form>
              
		</div>
		
	</div>


</body>
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
								
								callback:{
									onClick: zTreeOnClick,
								}

							};
							var	zNodes = ${classify};
							treeObj =$.fn.zTree.init($("#ztree"), setting,zNodes);
							treeObj.expandAll(true);

							

		});
function zTreeOnClick(event, treeId, treeNode) {

treeObj = $.fn.zTree.getZTreeObj("ztree");
var sNodes = treeObj.getSelectedNodes();
if (sNodes.length > 0) {
level = sNodes[0].level;
parentNode = sNodes[0].getParentNode();
if(parentNode !=null){
	parentNodeCode=parentNode.code;
}

isParent = sNodes[0].isParent;
}
document.getElementById("name").value = treeNode.name;
document.getElementById("id").value = treeNode.id;
}

</script>

</html>