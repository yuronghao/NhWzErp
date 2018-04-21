<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>


	<script type="text/javascript">
		var jsonArray = [];
		var selectedArray = [];
		var selectedId = '${selectedId}';
		if(selectedId!=''){
			selectedArray = selectedId.split(',');
		}
	</script>
	

<body>
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<!--主体部分-->
	 	<div class="mainword">
	 			<c:set var="treeUrl" value="${ctx}/plugin_selectTree.emi?treeType=${treeType}&multi=${multi}&showList=${showList}"></c:set>
	 			<c:set var="listUrl" value="${ctx }/plugin_selectList.emi?treeType=${treeType}&multi=${multi}&showTree=${showTree}&showList=${showList}"></c:set>
	 			
	 			<c:set var="treeWidth" value="23%"></c:set>
	 			<c:set var="listWidth" value="76%"></c:set>
	 			<c:if test="${showTree=='0' }"><c:set var="listWidth" value="98%"></c:set></c:if>
	 			<c:if test="${showList=='0' }"><c:set var="treeWidth" value="95%"></c:set></c:if>
	 			
	 			<!-- 显示树 -->
	 			<c:if test="${showTree!='0' }">
	 				<div class="tree_div fl" style="width:${treeWidth};">
	 					<iframe src="${treeUrl }" style="border:1px #ccc solid;"  id="" name="" frameborder="0" width="100%"  height="460px;"></iframe>
		 			</div>
		 			
	 			</c:if>
	 			
	 			<!-- 显示列表 -->
	 			<c:if test="${showList!='0' }">
	 				<div class="fl" style="width:${listWidth};">
		 				<iframe src="${listUrl }" frameborder="0" id="rightframe" name=""  style="margin-left:5px;min-height: 460px;width: 100%"></iframe>
		 			</div>
	 			</c:if>
	 			
	 			<div class="cf"></div>
	 			
	 	</div>
	 	<!--主体部分 end-->	
	 	 
	</div>


</body>
</html>