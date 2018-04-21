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
<script type="text/javascript"	src="${ctx }/scripts/emiwms.js"></script> 
<script type="text/javascript">
		
	</script>
</head>
<body style="background-color: #FFFFFF;">
<form id="myform">
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	<div class="creattable">
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li style="margin-top:10px;">
							<div class="fl div_text">模板类型：</div>
							<div class="fl div_ipt" style="width:150px;">
								<select id="printmodel" name="printmodel">
								<option value="0">打印条码模板一(90*50)</option>
								<option value="1">打印条码模板二(70*45)</option>
								<option value="2">打印条码模板三(100*90)</option>
								<option value="3">打印料号(40*10)</option>
								<option value="4">打印批号(40*10)</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>	
		 				<li style="margin-top:10px;">
							<div class="fl div_text">打印机：</div>
							<div class="fl div_ipt" style="width:150px;">
								<select id="printservice" name="printservice">
								<c:forEach var="printtype" items="${defaultService}">
								<option value="${printtype}">${printtype}</option>
								</c:forEach>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li> 
		 			</ul>
		 			<!--end-->
			 		</div>
		 		</div>		 		
		 	</div>
		</form>
	</body>
</html>