<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>变更生产订单数量</title>
</head>

<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">

<body style="height:100%;">
	<div style="padding-top:45;padding-left:5px;">
		<div class="fl">调整后数量:</div>
		<input type="text" id="ch" class="fl" style="margin-left:5px;" value="<fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${number}"></fmt:formatNumber>" readonly="readonly"/>
		<div class="cf"></div>
	</div>
	<script type="text/javascript">
	
		
	</script>
</body>
</html>