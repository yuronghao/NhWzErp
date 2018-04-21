<!DOCTYPE html >
<%@page import="com.emi.common.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 



<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>一米移动ERP</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx }/emi.ico"/>
      
</head>
<style>
			*{
				margin: 0;
				padding: 0;
				list-style: none;
				text-decoration: none;
			}
			body,html{
				height: 100%;
				width:100%;
				margin: 0px;
				font-size: 12px;
				font-family: 微软雅黑;
				background-color: #FFFFFF;	
			}
			.divcon{
				background: #fff;
				height:99%;
				width:99.9%;
			}
			.divtop{
				height:12%;
				width:100%;
			}
			.divbottom{
				height:8%;
				line-height: 80px;
				color:#868888;
				font-size: 16px;
				width:100%;
				text-align: center;
				
			}
			.divmiddle{
				width:100%;
				height:78%;
				padding-top:8%;
				padding-bottom:8%;
				background-image: url(${ctx}/img/bg2.png);
				/* position: relative; */
			}
			.divlogin{
				/* width:360px; */
				height:420px;
				/* margin-top: 7%; */
				margin-right:5%;
				margin-left:70%;
				border-radius: 8px;
				background: #fff;
				z-index: 999;
				/* position: absolute; */
			}
			.userlogin{
				width:80%;
				margin:0 auto;
				height:80px;
				line-height:80px;
				border-bottom:1px #959697 solid;
				color:#4e5252;
				font-size:28px;
				font-family: '微软雅黑';"
			}
			.divlogin ul li{
				width:80%;
				height: 45px;
				line-height: 45px;
				margin: 30px auto;
			}
			.divlogin ul li input[type="text"],input[type="password"] {
				width: 100%;
				height:45px;
				line-height: 45px;
				font-size: 18px;
				color: #84898a;
				border: 1px #aaa9a9 solid;
				border-radius: 3px;
				padding-left: 3px;
			}
			.fl{float: left;}
			.fr{float: right;}
			.cl{clear: both;}
			.libtn{
				width:100%;
				background: #009ab2;
				border:none;
				height:45px;
				line-height: 45px;
				border-radius: 3px;
				color:#fff;
				font-size: 24px;;
				cursor: pointer;
			}
		</style>

<body onkeydown="loginSubmit(event);">
	<form action="${ctx}/login_toIndex.emi" id="loginform" method="post">
		<div class="divcon">
			<div class="divtop">
				<img src="img/bg3.png" />
			</div>
			<div class="divmiddle">
				<input type="hidden" name="sysName" id="sysName" value="<%=Constants.SYSNAME_WMS%>">
				<!-- <img src="img/bg2.png" style="width:100%;height:100%;" /> -->
				<div class="divlogin"> 
					<div class="userlogin">用户登录</div>
					<!--<div style="height: 10px;"></div>-->
					<ul>
						<li>
							<input type="text" id="userName" name="userName" value="" placeholder="用户名"/>
						</li>
						<li>
							<input type="password" id="password" name="password" value="" placeholder="密码"/> 
						</li>
						<li style="margin-top:10px;height:30px;line-height: 30px;color:#84898a;">
							<input type="checkbox"  id="pswcheck" name="pswcheck" style="vertical-align: middle;" />记住密码
						</li>
						<li>
							<input type="button" class="libtn" id="loginBtn" name="" value="登录" onclick="logincheck()"/>
						</li>
					</ul>
				</div>
			</div>
			<div class="divbottom">
				江苏一米智能科技股份有限公司版权所有
			</div>
		</div>
	</form>
</body>
	
<script type="text/javascript">
$(function(){
	//加载cookie
	getcookie();
});
function logincheck(){
	$.ajax({
	  data: $("#loginform").serialize(),
	  type: 'POST',
	  url: '${ctx}/login_checkLogin.emi',
	  dataType:'text',
	  success: function(req){
	  if(req=='login'){
		//var sysName = document.getElementById("sysName").value;	  
		//window.location.href="${ctx}/login_toIndex.emi?sysName="+sysName;	 
		document.forms[0].submit();
		  }else{ alert(req);}
		 
	  }
	});

}
function getcookie(){
	$.ajax({
		  type: 'POST',
		  url: '${ctx}/login_getcookie.emi',
		  dataType:'text',
		  success: function(req){
			 var data = eval("(" + req+ ")"); 
			 if(data.username!=null){
				 $("#userName").val(data.username);
				 
			 }
			 if(data.password!=null){
				 $("#password").val(data.password);
				 $("#pswcheck").attr("checked",true);
			 }
			 
		  }
		});

}

// 监听键盘事件
function loginSubmit(e){
	var key; 
	if(document.all){ 
		key = window.event.keyCode; 
	}else if( e.which){ 
		key = e.which;
	}else{
		key =  e.charCode;
	}
	if(key == "13"){
		$("#loginBtn").click(); 
	}
}
</script>
</html>