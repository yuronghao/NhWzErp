<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<%@page import="com.emi.common.bean.core.TreeType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>计件工资详情表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
	<body>
		<form action="${ctx}/wms/reportForm_getOrderStatusList.emi" name="myform" id="myform" method="post">
			<div class="EMonecontent">
				<div style="width: 100%;height: 15px;"></div>
				<!--按钮部分-->
			 	<div class="toolbar">
			 		<ul>
			 			<li class="wordliNoWidth fl">
							<div class="wordname fl" >开始日期：</div>
							<div class="wordnameinput fl">
								<input type="text" id="startDate" name="startDate"  class="toDealInput" value="${ startDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordliNoWidth fl">
							<div class="wordname fl">结束日期：</div>
							<div class="wordnameinput fl">
								<input type="text" id="endDate" name="endDate"  class="toDealInput" value="${endDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
			 			<input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" style="margin-left: 100px" value="${keyWord }"> <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
			 		</ul>
			 	</div>
			 	<!--按钮部分 end-->
			 	<!--表格部分-->
			 	<div class="creattable">
			 		<div class="tabletitle">生产订单状况表</div>		 		
			 		<div>
			 			<table>
				 			<tbody>
				 				<tr>
				 					<th style="width: 50px;">序号</th>
				 					<th>订单编号</th>
				 					<th>订单日期</th>
				 					<th>产品编码</th>
				 					<th>产品名称</th>
				 					<th>规格型号</th>
				 					<th>表头备注</th>
				 					<th>生产数量</th>
				 					<th>转出数量</th>
				 					<th>已报工数量</th>
				 					<th>未完工数量</th>
				 					<th>表体备注</th>
				 					
				 				</tr>
				 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
									<tr>
										<td style="width: 50px;">${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
										<td nowrap class="longText" title="${bean.billCode }">${bean.billCode}</td>
										<td nowrap class="longText" title="${bean.billDate }">${fn:substring(bean.billDate,0,10) }</td>
										<td nowrap class="longText" title="${bean.goodsCode }">${bean.goodsCode}</td>
										<td nowrap class="longText" title="${bean.goodsName }">${bean.goodsName}</td>
										<td nowrap class="longText" title="${bean.goodsStandard }">${bean.goodsStandard}</td>
										<td nowrap class="longText" title="${bean.ponotes }">${bean.ponotes}</td>
										<td nowrap class="longText" title="${bean.number }"><fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.number}"></fmt:formatNumber> </td>
										<td nowrap class="longText" title="${bean.turnoutNum }"><fmt:formatNumber minFractionDigits="2" groupingUsed="false" type="number" value="${bean.turnoutNum}"></fmt:formatNumber></td>
										
										<td nowrap class="longText" title="${bean.reportedNum }">${bean.reportedNum}</td>
										<td nowrap class="longText" title="${bean.unFinishNum }">${bean.unFinishNum}</td>
										
										<td nowrap class="longText" title="${bean.pocnotes }">${bean.pocnotes}</td>
										
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