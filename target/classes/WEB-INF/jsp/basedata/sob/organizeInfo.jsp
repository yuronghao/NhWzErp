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

</head>
<body>
		<div class="EMonecontent">
		 	<!--主体部分-->
		 	<div class="creattable" style="">
		 		<div class="tabletitle">组织信息</div>	
		 		<div class="xz_attribute">
		 			<ul>
		 				<li style="margin-top:10px;">
		 					<div class="fl div_text">组织编码：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgcode']}" readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">组织名称：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgName']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">地址：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgAdder']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">电话：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgTel']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">传真：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgfax']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">手机：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgmoboile']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">法人代表：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="legal" name="legal" value="${organizeInfo['legal']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">邮编：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgpostcode']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">网址：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgurls']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">url：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['orgurl']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">备注：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgcode" name="orgcode" value="${organizeInfo['notes']}"  readonly="readonly">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 			</ul>
		 			<div style="height:10px;"></div>
		 		</div>
		 	</div>
		 	<!--主体部分 end-->	
		</div>
	</body>
</html>