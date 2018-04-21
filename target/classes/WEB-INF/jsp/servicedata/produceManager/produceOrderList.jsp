<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>生产订单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
<body>
<form action="${ctx}/wms/produceOrder_produceOrderList.emi" name="myform" id="myform" method="post">
	<input type="hidden" id="changeOrder" name="changeOrder" value="${changeOrder }">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<input type="text" name="billkeyWord" placeholder="单据编号关键字" class="write_input" style="margin-left: 100px" value="${billkeyWord }"> 
		 			<input type="text" name="goodskeyWord" placeholder="物料关键字" class="write_input" style="margin-left: 100px" value="${goodskeyWord }"> 
		 			<input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle"><c:if test="${changeOrder=='1'}">改制</c:if>生产订单列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">序号</th>
			 					<th>生产订单编码</th>
			 					<c:if test="${changeOrder=='1'}"><th>原生产订单编码</th></c:if>
			 					<th>主表备注</th>
			 					<th>单据日期</th>
			 					<!-- <th>录入人</th> -->
			 					<th>成品编码</th>
			 					<th>成品名称</th>
			 					<th>规格型号</th>
			 					<th>生产数量</th>
			 					<th>已完工数量</th>
			 					<th>计划开工日</th>
			 					<th>计划完工日</th>
			 					<th>转出数量</th>
			 					<th>子表备注</th>
			 					
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;">${stat.count}</td>
								<td><a href="${ctx}/wms/produceOrder_toAddproduceOrder.emi?produceOrdergid=${bean.gid}&changeOrder=${changeOrder}">${bean.billcode}</a></td>
								<c:if test="${changeOrder=='1'}"><td>${bean.sourcebillCade }</td></c:if>
								<td>${bean.notes}</td>
								<td>${fn:substring(bean.billdate,0,10)}</td>
								<%-- <td>${bean.providercustomername}</td> --%>
								<td>${bean.aagoods.goodscode}</td>
								<td>${bean.aagoods.goodsname}</td>
								<td>${bean.aagoods.goodsstandard}</td>
								<td>${bean.number}</td>
								<td>${bean.completedNum}</td>
								<td>${fn:substring(bean.startDate,0,10)}</td>
								<td>${fn:substring(bean.endDate,0,10)}</td>
								<td>${bean.turnoutNum}</td>
								<td>${bean.note}</td>
								
								
								
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