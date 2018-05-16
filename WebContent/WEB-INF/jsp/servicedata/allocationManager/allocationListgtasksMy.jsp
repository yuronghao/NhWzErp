<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>审批调拨单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
<body>
<form action="${ctx}/wms/allocation_gtasksMygetAllocationList.emi" name="myform" id="myform" method="post">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">调拨单列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">序号</th>
			 					<th>订单编号</th>
			 					<th>单据日期</th>
			 					<th>存货名称</th>
			 					<th>存货编码</th>
			 					<th>规格</th>
			 					<th>调出仓库</th>
			 					<th>调入仓库</th>
			 					<th>调出货位</th>
			 					<th>调入货位</th>
			 					<th>应调出数量</th>
			 					<th>已调出数量</th>
								<th>操作</th>
			 					<%--<c:forEach var="column" items="${columns}" varStatus="stat">--%>
			 					<%--<th>${column.projectName}</th>--%>
			 					<%--</c:forEach>--%>
			 					
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;">${stat.count}</td>
								<td><a href="${ctx}/wms/wareHouse_toAddCallMy.emi?callgid=${bean.gid}&followmovinggid=${bean.followmovinggid}">${bean.billCode}</a></td>
								<td>${fn:substring(bean.billDate,0,10)}</td>
								<td>${bean.good.goodsname}</td>
								<td>${bean.good.goodscode}</td>
								<td>${bean.good.goodsstandard}</td>
								<td>${bean.aawarehouseout.whname}</td>
								<td>${bean.aawarehousein.whname}</td>
								<td>${bean.aagoodsallocationout.name}</td>
								<td>${bean.aagoodsallocationin.name}</td>
								<td><fmt:formatNumber type="number" value="${bean.number}" minFractionDigits="2"/></td>
								<td><fmt:formatNumber type="number" value="${bean.outnumber}" minFractionDigits="2"/></td>
								<%--<c:forEach var="column" items="${columns}" varStatus="stat">--%>
			 					<%--<td>${bean[column.projectCode]}</td>--%>
			 					<%--</c:forEach>--%>
								<td>

									<span ><a href="${ctx}/wms/wareHouse_toAddCallMy.emi?callgid=${bean.gid}&followmovinggid=${bean.followmovinggid}" style="color: blue">审核</a></span>

								</td>
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