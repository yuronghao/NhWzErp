<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>账套设置列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
<body>
<form action="${ctx}/wms/sob_getsoblist.emi" name="myform" id="myform" method="post">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<div class="toolbar">
	 		<ul>
	 			<!-- <li class="fl"><input type="button" class="addBtn" value="新增"></li> -->
	 			<!-- <li class="fl"><input type="button" id="Emsearch" class="searchBtn " value="查询"> </li> -->
	 			<!-- <li class="fl"><input type="button" class="delBtn" value="停用"> </li> -->
	 			<input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">账套设置列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">序号</th>
			 					<th>账套编码</th>
			 					<th>账套名称</th>
			 					<th>组织</th>
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;">${stat.count}</td>
								<td><a href="${ctx}/wms/sob_toAddsob.emi?sobgid=${bean.gid}">${bean.sobcode}</a></td>
								<td>${bean.sobname}</td>
								<td>${bean.orgname}</td>
							</tr>
						</c:forEach>
			 			</tbody>
			 		</table>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		 	<!--分页部分-->
		<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
		<!--分页部分 end-->
		</div>
		</form>
	</body>
</html>