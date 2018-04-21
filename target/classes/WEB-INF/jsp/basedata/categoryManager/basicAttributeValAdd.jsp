<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>属性方案</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript">
		function checkdata()
		{
			if(document.getElementById('name').value==""){
				$.dialog.alert_w("属性值名称不能为空!");
			  	return false;
			}
			else
			{ 
				
				return true;
			}
		}
	</script>
</head>
<body><form name="myform" id="myform" method="post" action="" onsubmit="return checkdata()">
		<div class="EMonecontent">
				<input type="hidden"  value="${bean.gid}" id="gid" name="gid" />
				<input type="hidden"  value="${Ugid}" id="Ugid" name="Ugid" />
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
<!-- 		 	<div class="toolbar">
		 		<ul>
		 			<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>
		 			<li class="fl"><input type="button" class="saveBtn" value="保存"> </li>
		 			<div class="cf"></div>
		 		</ul>
		 	</div> -->
		 	<!--按钮部分 end-->
		 	<!--主体部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">属性值名称</div>		 		
		 		<div class="xz_attribute" style="border:0">
		 			<ul>
		 				<li>
		 					<div style="width:35%;text-align:right;" class="fl">属性值名称：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<input type="text" style="width:260px;height:25px;line-height: 25px;" value="${bean.name}" id="name" name="name"  />
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 			</ul>
		 			<div style="height:10px;"></div>
		 		</div>
		 	</div>
		 	<!--主体部分 end-->	
		</div>
		</form>
</body>
</html>