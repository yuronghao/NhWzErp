<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>EMI SOFTWEAR</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx }/emi.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css"/>
</head>
<body>
   	<iframe frameborder="0" style="float: left;max-width: 235px;min-height: 450px;" width="20%" src="${ctx }/wms/classify_toClassifyTree.emi" id="rightTree" name="rightTree"></iframe>
	<iframe frameborder="0" width="80%" height="98%" src="${ctx }/wms/classify_toClassifyForm.emi" id="rightForm" name="rightForm"></iframe>
</body>

</html>