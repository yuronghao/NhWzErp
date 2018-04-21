<!DOCTYPE html >
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%><html>
<%
	long code = new Date().getTime();
%>
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

	// 打开用户选择界面
	function showUser(){
		
	}
	
	// 保存权限模块
	function saveRight(){
		if(checkdata()){
			/*var useFuns = 0;
			var ck = document.getElementsByName("funs");
			for(var i=0;i<ck.length;i++){
				if(ck[i].checked){
					useFuns += (ck[i].value-0);
				}
			}
			document.getElementById("useFuns").value=useFuns;*/
			$.ajax({
				data: $("#myform").serialize(),
				type: 'POST',
				url: '${ctx}/right_addRight.emi',
				success: function(req){
					if(req=='success'){
						$.dialog.alert_s('添加成功');
						var superGid = document.getElementById('superiorRightId').value;
						var treeWin = parent.document.getElementById("rightTree").contentWindow;
						var tree = treeWin.getZTree();
						if(superGid=='' ){
							tree.reAsyncChildNodes(treeWin.selectedNode()[0], "refresh");
						}else{
							//tree.reAsyncChildNodes(tree.getNodeByParam("gid",superGid, null),"refresh");
							tree.reAsyncChildNodes(treeWin.selectedNode()[0].getParentNode(),"refresh");
						}
						
						/*var treeWin = parent.document.getElementById("rightTree").contentWindow;
						if(thisgid=='null'){
							 treeWin.getZTree().reAsyncChildNodes(null, "refresh");
						}else{
						 treeWin.getZTree().reAsyncChildNodes(node[0].getParentNode(),"refresh");
						 treeWin.getZTree().reAsyncChildNodes(treeWin.getZTree().getNodeByParam("gid",thisgid, null),"refresh");
						}*/
					}else if(req=='error'){
						$.dialog.alert_e('添加失败');
					}else if(req=='sameCode'){
						$.dialog.alert_e('该模块代码已存在，不能重复');
					}
				}
			});
		}
	}
	
	//改变文本
	function changeReqType(){
		var reqType = document.getElementById("reqType").value;
		document.getElementById("reqTr_0").style.display="none";
		document.getElementById("reqTr_1").style.display="none";
		document.getElementById("reqTr_2").style.display="none";
		
		document.getElementById("reqTr_"+reqType).style.display="block";
	}
</script>
<body>
	<form action="${ctx }/right_addRight.emi" method="post" id="myform" class="myform"  onsubmit="return checkdata()">
		<div class="EMonecontent">
		<div class="toolbar" style="margin-top: 15px">
	 		<ul>
	 			<!--<li class="fl"><input type="button" class="backBtn" value="返回" onclick="history.go(-1)"> </li>-->
	 			<li class="fl"><input type="button" class="saveBtn" value="保存" onclick="saveRight()"> </li>
	 			<li class="fl"><input type="button" class="backBtn" value="返回" onclick="window.history.go(-1)"> </li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="tabletitle">模块新增</div>	
	 		<input type="hidden" name="right.rightType" value="0">
	 		<div class="mainaddword" style="min-height: 350px;">
	 			<ul class="EMtree_word fl" style="width: 100%;">
	 				<li>
	 					<div class="fl mainaddword_name">模块名称：</div>
	 					<div class="fl mainaddword_word"><input class="addwordinput" type="text"  name="right.rightName" id="rightName" ><span style="color: red;">*</span></div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">模块代码：</div>
	 					<div class="fl mainaddword_word"><input class="addwordinput" type="text"  name="right.rightCode" id="rightCode" value="${superiorRightCode }_"><span style="color: red;">*</span> </div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">请求地址：</div>
	 					<div class="fl mainaddword_word"><input class="addwordinput" type="text"  name="right.rightUrl" id="rightUrl" placeholder="例：login_userLogin.do"></div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">图标：</div>
	 					<div class="fl mainaddword_word"><input class="addwordinput" type="text"  name="right.imgUrl" id="imgUrl" placeholder="例：img/logo.png"> </div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">上级模块：</div>
	 					<div class="fl mainaddword_word">
	 						<input class="addwordinput" type="text"  name="right.superiorRightName" id="superiorRightName" readonly="readonly" value="${superiorRightName }">
		                    <input type="hidden" name="right.superiorRightId" id="superiorRightId" value="${superiorRightId }">
	 						<!-- <input type="button" class="upclass" title="选择" onclick="showRightTree();">  -->
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li >
	 					<div class="fl mainaddword_name">是否显示：</div>
	 					<div class="fl mainaddword_word">
	 						<select class="addwordselect"  name="right.isShow" id="isShow" >
			                    <option value="1">显示</option>
			                  	<option value="0">不显示</option>
		                    </select>
						</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">排序号：</div>
	 					<div class="fl mainaddword_word"><input class="addwordinput" type="text"  name="right.rightIndex" id="rightIndex" placeholder="请输入正整数" value="${right.rightIndex }"> </div>
	 					<div class="cf"></div>
	 				</li>
	 				
	 				<li style="display: none">
	 					<div class="fl mainaddword_name">所属系统：</div>
	 					<div class="fl mainaddword_word">
							<select class="addwordselect" name="right.ownerSys" id="ownerSys" >
			                    <c:forEach var="sys" items="${systems }">
			                  	  <option value="${sys.shortName }" <c:if test="${ownerSys==sys.shortName}">selected</c:if> >${sys.fullName}</option>
			                    </c:forEach>
		                    </select>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li style="display: none">
	 					<div class="fl mainaddword_name">菜单类型：</div>
	 					<div class="fl mainaddword_word">
	 						<select class="addwordselect"  name="right.subGenerate" id="subGenerate" >
			                    <option value="0">普通菜单</option>
			                    <option value="1">任务流菜单</option>
		                    </select>
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
<script type="text/javascript">


function checkdata(){
	
	for(var i=0;i<2;i++){
	      if($('.inputxt').eq(i).val()==''){
	    	  $.dialog.alert_w('内容填写不完整');
	    	  return false;
	      }
	}
	var   re   = /^[1-9]+[0-9]*]*$/ ;
	if($('#rightIndex').val()!='' && !(re.test($('#rightIndex').val()))){
		$.dialog.alert_w('排序号请输入正整数');
    	return false;
	}
	return true;
}


</script>
</html>