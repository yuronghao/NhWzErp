<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>

	<script type="text/javascript">

		function checkdata()
		{
			if(document.getElementById('userCode').value==""){
				$.dialog.alert_w("用户编码不能为空!");
			  	return false;
			}else if(document.getElementById('userName').value==""){
				$.dialog.alert_w("用户名称不能为空!");
			  	return false;
			}else if('edit'!='${type}' && document.getElementById('passWord').value==""){
				$.dialog.alert_w("用户密码不能为空!");
			  	return false;
			}
			if('edit'=='${type}' && $('#passWord').val()!=$('#confirmPassWord').val()){
				$.dialog.alert_w("密码与确认密码不一致!");
				return false;
			}
			return true;
		}
	
	</script>


<body>
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>

	 	<!--主体部分-->
	 	<form action="" id="myform" method="post">
		 	<div class="mainword">
		 		<div class="tabletitle">用户信息</div>	
		 		<div class="mainaddword" style="min-height: 350px;">
		 		<input type="hidden" name="type" id="type" value="${type }">
		 		<input type="hidden" name="gid" id="gid" value="${ymuser.gid }">
		 			<ul>
		 				<li>
		 					<div class="fl mainaddword_name">用户编码：</div>
		 					<div class="fl mainaddword_word"><input type="text" class="addwordinput"  id="userCode" name="userCode" readonly="readonly" value="${ymuser.userCode }"><span style="color: red;">*</span></div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name">用户名称：</div>
		 					<div class="fl mainaddword_word"><input type="text" class="addwordinput" id="userName" name="userName" readonly="readonly" value="${ymuser.userName }"><span style="color: red;">*</span></div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl mainaddword_name"><c:if test="${type=='edit' }">新</c:if>密码：</div>
		 					<div class="fl mainaddword_word"><input type="password" class="addwordinput" id="passWord" name="passWord" value="" <c:if test="${type=='edit' }">placeholder="输入需要重置的密码"</c:if>><span style="color: red;">*</span></div>
		 					<div class="cf"></div>
		 				</li>
		 				<c:if test="${type=='edit' }">
							<li>
		 					<div class="fl mainaddword_name">确认新密码：</div>
		 					<div class="fl mainaddword_word"><input type="password" class="addwordinput" id="confirmPassWord" name="confirmPassWord" value="" placeholder="确认需要重置的密码" ><span style="color: red;">*</span></div>
		 					<div class="cf"></div>
		 				</li>
						</c:if> 
						
						<li>
		 					<div class="fl mainaddword_name">是否停用：</div>
		 					<div class="fl mainaddword_word">
		 						<select id="isUse" name="isUse" class="addwordinput">
		 							<c:choose>
		 								<c:when test="${ymuser.isDelete==0 || ymuser.isDelete==null }">
		 									<option value="0" selected="selected">否</option>
		 									<option value="1">是</option>
		 								</c:when>
			 							<c:otherwise>
			 								<option value="0">否</option>
			 								<option value="1" selected="selected">是</option>
			 							</c:otherwise>
		 							</c:choose>
		 						</select>
		 					</div>
		 					<div class="cf"></div>
		 				</li>
						
						
		 				<li>
		 					<div class="fl mainaddword_name">备注：</div>
		 					<div class="fl mainaddword_word"><input type="text" class="addwordinput" id="notes" name="notes" value="${ymuser.notes }"> </div>
		 					<div class="cf"></div>
		 				</li>
		 			</ul>
		 		</div>
		 	</div>
	 	</form>
	 	<!--主体部分 end-->	
	</div>
</body>
</html>