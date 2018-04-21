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
			if(document.getElementById('orgName').value==""){
				$.dialog.alert_w("客商名称不能为空!");
			  	return false;
			}
			else
			{ 
				return true;
			}
		}
		
	</script>
</head>
<body>
<form id="myform">
<input type="hidden" id="orgclassid" name="orgclassid" value="${orgclassid}">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
		 	<!--主体部分-->
		 	<div class="creattable" style="">
		 		<div class="tabletitle">组织信息</div>	
		 		<div class="xz_attribute">
		 			<ul>
		 				<li style="margin-top:10px;">
		 					<div class="fl div_text">组织编码：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgCode" name="orgCode" value="${aaorg['orgCode']}">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">组织名称：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgName" name="orgName" value="${aaorg['orgName']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">地址：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgAdder" name="orgAdder" value="${aaorg['orgAdder']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">电话：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgTel" name="orgTel" value="${aaorg['orgTel']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">传真：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgFax" name="orgFax" value="${aaorg['orgFax']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">手机：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgMoboile" name="orgMoboile" value="${aaorg['orgMoboile']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">法人代表：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="legal" name="legal" value="${aaorg['legal']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">邮编：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgPostCode" name="orgPostCode" value="${aaorg['orgPostCode']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">网址：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgUrls" name="orgUrls" value="${aaorg['orgUrls']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">url：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgUrl" name="orgUrl" value="${aaorg['orgUrl']}" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">备注：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="notes" name="notes" value="${aaorg['notes']}" >
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