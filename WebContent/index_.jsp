<!DOCTYPE html >
<%@page import="com.emi.sys.util.SysPropertites"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>一米移动ERP</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />
	<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath() %>/emi.ico"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common/index_rm.css">
	<script src="<%=request.getContextPath() %>/scripts/plugins/bootstrap-treeview/js/jquery.min.js"></script>
	<script type="text/javascript">
	</script>
	<!--自动调整宽度\高度-->
	<script type="text/javascript">

			function getHeight(){
				var valueH=document.body.clientHeight;
				var mainH=valueH-140;
				$('#mainContent').attr("style","height:"+mainH+"px");
			}
		
			$(function(){
				
				window.onresize = function winResize() {
					
					
					if( $('#mainBody').width()<1024  ){
						$('#mainContainer').attr("style","min-width:1024px;");
					}else{
						$('#mainContainer').removeAttr("style");
					}
				};
				getHeight();
			});
	
	</script>
	 
</head>
<body id="mainbody" style="border:1px green solid;height:100%;">

	<div id="mainContainer" style="height:100%;border:1px red solid;">
		<!--logo部分-->
		<div class="header">
			<iframe src="<%=request.getContextPath() %>/top.jsp" frameborder="0" id="topMain" name="topMain" width="100%" height="100%"></iframe>
		</div>
		<!--整体部分-->
		<div id="mainContent" style="width:100%;height:100%;">
			<!--菜单栏-->
			<div class="fl leftmenu" id="leftFrame" style="border:1px green solid;width:14%;overfolw-x:hidden;height:100%;">
				<iframe src="<%=request.getContextPath() %>/login_showLeft.emi" frameborder="0" id="leftMain" name="leftMain" width="100%" height="100%"></iframe>
			</div>
			<!--主体部分--> 
			<div class="fl" id="rightFrame" style="width:85.5%;">
				<div id="openClose" ></div>
				<iframe src="<%=request.getContextPath() %>/loginWelcome.jsp" framemagin="0" frameborder="0" id="rightMain" name="rightMain" width="100%" height="100%"></iframe>
			</div>
			<div class="cf"></div>
		</div>
	</div>
	
	
	
	

	

</body>
</html>