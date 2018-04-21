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
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/common/common_rm.css">
</head>
<script type="text/javascript">

	$(function() {
	
		var width = $('#createwords').width();
		$('#content').css('width', width + "px");
		$('#createwords').css('height', window.innerHeight -60+ "px");
	});
	
	// 打开用户选择界面
	function showUser(){
		
	}
	
	// 保存权限模块
	function saveRight(){
		if(checkdata()){
			var useFuns = 0;
			var ck = document.getElementsByName("funs");
			for(var i=0;i<ck.length;i++){
				if(ck[i].checked){
					useFuns += (ck[i].value-0);
				}
			}
			document.getElementById("useFuns").value=useFuns;
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
						if(superGid==''){
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
	
	function btnClick(ownerSys,rightId,url){
		location.href = '${ctx}/right_pageJump.action?url='+url+'&ownerSys='+ownerSys+'&rightId='+rightId;
		//location.href="${ctx}"+decodeURI(url);
	}
</script>
<body >
	<div id="container">
		<div id="content">
			<form action="${ctx }/right_addRight.emi" method="post" id="myform" class="myform"  onsubmit="return checkdata()">
				<!--按钮栏-->
				<div id="toolBar">
					<div class="fl btn">
						<c:forEach var="btn" items="${btnList }">
							<input class="${ btn.buttonCss}" type="button" value="${ btn.rightName}" onclick="btnClick('${btn.ownerSys }','${btn.gid }','${btn.rightUrl}')"/>
						</c:forEach>
						<input id="backBtn" type="button" value="返回" onclick="window.history.go(-1)"/>
					</div>
					<div class="cf"></div>
				</div>
				<!--内容部分-->
				<div id="createwords" style="height: 100%">
				<%-- <%@include file="../../../../demo/1.html" %> --%>
				<%-- <jsp:include page="../../../../demo/1.jsp"></jsp:include> --%>
				<iframe src="${ctx }${url}" id="inFrame" width="100%" height="100%" framemagin="0" frameborder="0"></iframe> 
				</div>
              </form>
              
		</div>
		
	</div>


</body>
<script type="text/javascript">


function checkdata(){
	
	for(var i=0;i<4;i++){
	      if($('.inputxt').eq(i).val()==''){
	    	  $.dialog({
	    		    title: '系统提示',
	    		    drag: false,
	    		    resize: false,
	    		    lock: true,
	    		    background: '#000', /* 背景色 */
	    		    opacity: 0.5,       /* 透明度 */
	    		    content: '内容填写不完整',
	    		    icon: 'error.gif',
	    		    ok:true
	    		});
	    	  
	    	  return false;
	      }
	}
	return true;
}


</script>
</html>