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
	<link href="${ctx }/css/bootstrap.min.css" rel="stylesheet">
	<link href="${ctx }/css/public.css" rel="stylesheet">

      
</head>

<style>
	body{
		background: url('${ctx }/img/logobg20180524.png') no-repeat center/cover;
	}


	#loginWrap{
		width: 900px;
		height: 440px;
		margin: 0 auto;
		box-shadow: 0 0 30px rgba(0,0,0,0.4);
	}


	#loginWrap>.loginLeft{
		width: 60%;
		/*background: rgba(0,154,122, 0.5);*/
		background: url('${ctx }/img/2.png') no-repeat center/110% 120%;
	}
	#loginWrap>.loginLeft>header{
		top: 20px;
		left: 20px;
		width: 130px;
		height: 26px;
		background: url('${ctx }/img/logo.png') no-repeat left top/100% 100%;
	}
	#loginWrap>.loginLeft>footer{
		bottom: 20px;
		right: 20px;
		width: 86%;
		height: 12%;
		background: url('${ctx }/img/logointro.png') no-repeat left top/100% 100%;
	}


	#loginWrap>.loginRight{
		width: 40%;
	}
	#loginWrap>.loginRight>div{
		padding: 40px 60px;
	}
	#loginWrap>.loginRight>div>header>span{
		padding: 10px 20px;
		border-bottom: 1px solid #169F82;
		color: #169F82;
	}
	#loginWrap>.loginRight>div>section{
		margin: 40px 0 80px;
	}
	#loginWrap>.loginRight>div>section>p{
		margin-bottom: 0;
		padding: 10px 0;
		border-bottom:1px solid #D1D1D1;
	}
	#loginWrap>.loginRight>div>section>p>input:focus{
		outline:none;
	}
	#loginWrap>.loginRight>div>section>p>input:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
		color: #CCC;
	}

	#loginWrap>.loginRight>div>section>p>input::-moz-placeholder { /* Mozilla Firefox 19+ */
		color: #CCC;
	}

	#loginWrap>.loginRight>div>section>p>input:-ms-input-placeholder{
		color: #CCC;
	}

	#loginWrap>.loginRight>div>section>p>input::-webkit-input-placeholder{
		color: #CCC;
	}
	#loginWrap>.loginRight>div>section>p:last-child>input{
		width: 60%;
	}
	#loginWrap>.loginRight>div>section>p>img{
		top: 6px;
		right: 0px;
		width: 60px;
		height: 30px;
	}


	#loginWrap>.loginRight>div>footer>div>input{
		margin-top: 0;
	}
	#loginWrap>.loginRight>div>footer>div>span{
		color: #BBB;
	}
	#loginWrap>.loginRight>div>footer>button{
		margin-top: 12px;
		background: #009A7A;
	}
	#loginWrap>.loginRight>div>footer>.btn-default:focus,
	#loginWrap>.loginRight>div>footer>.btn-default:hover{
		color :#FFF;
	}
</style>

<body onkeydown="loginSubmit(event);">
<form action="${ctx}/login_toIndex.emi" id="loginform" method="post">
	<div class="h100">
		<input type="hidden" name="sysName" id="sysName" value="<%=Constants.SYSNAME_WMS%>">
		<div id="loginWrap" class=-"clearfix">
			<div class="loginLeft h100 fl pr">
				<header class="pa"></header>
				<footer class="pa"></footer>
			</div>
			<div class="loginRight h100 fr bgw" style="padding: 40px 40px 0;">
				<div class="h100">
					<header class="tc">
						<span class="size20">用户登录</span>
					</header>
					<section style="margin: 60px 0 40px;">
						<p><input class="w100 bn size14" type="text" id="userName" name="userName" placeholder="请输入账号"></p>
						<p><input class="w100 bn size14" type="password" id="password" name="password" placeholder="请输入密码"></p>
						<%--<p class="pr">--%>
							<%--<input class="w100 bn size14" type="text" placeholder="请输入验证码">--%>
							<%--<img class="pa cur" src="${ctx }/img/loginbg.png" alt="">--%>
						<%--</p>--%>
					</section>
					<footer>
						<div class="size12">
							<input class="vm cur" type="checkbox">
							<span class="vm">记住密码</span>
						</div>
						<button type="button" class="btn btn-default w100 bn cw size16" onclick="logincheck()">登录</button>
					</footer>
				</div>
			</div>
		</div>
	</div>
</form>

	<%--<form action="${ctx}/login_toIndex.emi" id="loginform" method="post">--%>
		<%--<div class="divcon">--%>
			<%--<div class="divtop">--%>
				<%--<img src="img/bg3.png" />--%>
			<%--</div>--%>
			<%--<div class="divmiddle">--%>
				<%--<input type="hidden" name="sysName" id="sysName" value="<%=Constants.SYSNAME_WMS%>">--%>
				<%--<!-- <img src="img/bg2.png" style="width:100%;height:100%;" /> -->--%>
				<%--<div class="divlogin"> --%>
					<%--<div class="userlogin">用户登录</div>--%>
					<%--<!--<div style="height: 10px;"></div>-->--%>
					<%--<ul>--%>
						<%--<li>--%>
							<%--<input type="text" id="userName" name="userName" value="" placeholder="用户名"/>--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<input type="password" id="password" name="password" value="" placeholder="密码"/> --%>
						<%--</li>--%>
						<%--<li style="margin-top:10px;height:30px;line-height: 30px;color:#84898a;">--%>
							<%--<input type="checkbox"  id="pswcheck" name="pswcheck" style="vertical-align: middle;" />记住密码--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<input type="button" class="libtn" id="loginBtn" name="" value="登录" onclick="logincheck()"/>--%>
						<%--</li>--%>
					<%--</ul>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="divbottom">--%>
				<%--江苏一米智能科技股份有限公司版权所有--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</form>--%>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${ctx }/js/bootstrap.min.js"></script>

</body>
	
<script type="text/javascript">

    (function(){
        // 动态设置loginWrapmarginTop值
        var oLoginWrap = document.getElementById('loginWrap');
        console.log(oLoginWrap);
        sizeChange();
        window.onresize = sizeChange;
        function sizeChange(){
            oLoginWrap.style.marginTop = (document.documentElement.clientHeight - oLoginWrap.clientHeight)/2 + 'px';
        }
    })();


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