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

<style type="text/css">
.Name1{
	color: #2b6c94;
	font-weight: bold;
	font-size: 14px;
	padding-left: 30px;
}
</style>
</head>
<script type="text/javascript">

$(function() {

	var width = $('#createwords').width();
	$('#content').css('width', width + 10 + "px");

});

/*
 * 打开用户选择界面
 */
function showUser(){
	
}

function doBack(){
	window.location.href="${ctx}/role_roleList.emi";
}
</script>
<body>
	<div id="container">
		<div id="content">
			<form action="${ctx }/role_addRole.emi" method="post"  class="myform"  onsubmit="return checkdata()">
				<!--按钮栏-->
				<div id="toolBar">
					<div class="fl btn">
						<input id="backBtn" type="button" value="返回" onclick="doBack();"/>
						<span class="Name1">角色名称：${role.roleName }</span>
					</div>
					<div class="cf"></div>
				</div>
				<!--内容部分-->
				<div id="createbody">
					 <!--标题名-->
					<%-- <div id="createName">
						<div class="Name">角色：${role.roleName }</div>
					</div > --%>
                    
	              <div id="createwords" style="height: 320px">
	              <div style="width: 20%;height:100%" class="fl" id="rightT">
				    	<iframe frameborder="0" width="100%" height="100%" src="${ctx }/right_toRightTree4Auth.emi" id="rightTreeAuth" name="rightTreeAuth"></iframe>
				    </div>
				    <div style="width:80%;float: left;height:100%" class="fl" id="rightF" >
				   	 	<iframe frameborder="0" width="100%" height="100%" src="${ctx }/right_toRightAuth.emi" id="rightAuth" name="rightAuth"></iframe>
				    </div>
		          </div>
              </div></form>
              
		</div>
		
	</div>


</body>
<script type="text/javascript">


function checkdata(){
	
	for(var i=0;i<2;i++){
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