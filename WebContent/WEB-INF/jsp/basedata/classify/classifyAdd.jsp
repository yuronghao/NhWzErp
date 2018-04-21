<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%><html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content=""/>
<meta name="keywords" content=""/>
<meta name="robots" content="index,follow" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css"/>
	
</head>
<script type="text/javascript">

	$(function() {
	
		var width = $('#createwords').width();
		$('#content').css('width', width + 10 + "px");
	
		if("${isRoot}"=="true"){
			document.getElementById("classifyName").disabled=true;
			document.getElementById("parentName").disabled=true;
		}
		var fun = "${right.functions}"-0;
		//判断checkbox是否需要选中
		var funsCk = document.getElementsByName('funs');
		for(var i=0;i<funsCk.length;i++){
			if(fun&Math.pow(2,i+1)){
				funsCk[i].checked=true;
			}
		}
	});
	
	// 打开用户选择界面
	function showUser(){
		
	}
	
	// 保存权限模块
	function saveClassify(){
		var treeWin = parent.document.getElementById("rightTree").contentWindow;
		var node = treeWin.selectedNode();
		if(node.length>0){
			if(checkdata()){
				$.ajax({
					  data: $("#myform").serialize(),
					  type: 'POST',
					  url: '${ctx}/wms/classify_addClassify.emi',
					  success: function(req){
						  if(req=='success'){
							  var treeWin = parent.document.getElementById("rightTree").contentWindow;
							  $.dialog.alert_s('保存成功');
							  treeWin.getZTree().reAsyncChildNodes(node[0],"refresh");
						  }else{
							  $.dialog.alert_e("保存失败");
						  }
					  }
				});
			}
		}else{
			$.dialog.alert_e("请先选择节点");
		}
	}
	
	//校验数据
	function checkdata(){
		for(var i=0;i<1;i++){
		      if($('.inputxt').eq(i).val()==''){
		    	  $.dialog.alert_w('内容填写不完整');
		    	  return false;
		      }
		}
		return true;
	}
	
	function deleteClassify(){
		var treeWin = parent.document.getElementById("rightTree").contentWindow;
		var node = treeWin.selectedNode();
		if(node.length>0){
			if(confirm("确定删除?")){
				$.ajax({
					  data: $("#myform").serialize(),
					  type: 'POST',
					  url: '${ctx}/wms/classify_deleteClassify.emi?gid=${classify.gid}',
					  success: function(req){
						  if(req=='success'){
							 // $.dialog.alert_s('删除成功');
							 treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
							 location.href="${ctx}/wms/classify_toClassifyForm.emi";
						  }else{
							  $.dialog.alert_e("删除失败");
						  }
					  }
				});
			}
		}else{
			$.dialog.alert_e("请先选择节点");
		}
	}
	
</script>
<body style="height: 95%">
	<form action="${ctx }/right_updateRight.emi" method="post" id="myform" class="myform"  onsubmit="return checkdata()">
		<div class="EMonecontent">
		<div class="toolbar" style="margin-top: 15px">
	 		<ul>
				<input class="saveBtn" class="buttonTool" type="button" value="保存" onclick="saveClassify()"/>
				<input class="backBtn" type="button" value="返回" onclick="window.history.go(-1)"/>
				&nbsp;&nbsp;
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="tabletitle">编辑分类</div>	
	 		
	 		<input type="hidden" name="classify.gid" id="gid" value="${classify.gid }">
	 		<input type="hidden" name="classify.stylegid" id="stylegid" value="${f_classify.stylegid }">
	 		<div class="mainaddword" style="min-height: 300px;">
	 			<ul class="EMtree_word fl" style="width: 100%;">
	 				<li>
	 					<div class="fl mainaddword_name">分类名称：</div>
	 					<div class="fl mainaddword_word"><input class="addwordinput inputxt" type="text"  name="classify.classificationname" id="classificationname" value="${classify.classificationname }"><span style="color: red;">*</span></div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">上级分类：</div> 
	 					<div class="fl mainaddword_word"><input class="addwordinput " type="text" readonly="readonly" name="classify.parentName" id="parentName" value="${f_classify.classificationname }">
	 							<input type="hidden"  name="classify.parentid" id="parentid" value="${f_classify.gid }">
	 					 </div>
	 					<div class="cf"></div>
	 				</li>
	 				 
	 			</ul>
	 			<div class="cf"></div>
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	</form>

</body>
</html>