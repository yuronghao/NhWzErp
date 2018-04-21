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
<form action="${ctx}/wms/produceprocess_dispatchingorderList.emi" name="myform" id="myform" method="post">
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
		 		<div class="tabletitle">订单开工报工明细表</div>		 		
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
			 					<th>开始时间</th>
			 					<th>工序条码</th>
			 					<th>工序名称</th>
			 					<th>派工对象</th>
			 					<th>派工数量</th>
			 					<th>报工合格数量</th>
			 					<th>报工不合格数量</th>
			 					<th>备注</th>
			 					<!-- <th>报工问题数量</th> -->
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td style="width: 120px;">${stat.count}</td>
								<td>${bean.billCode}</td>
								<td>${fn:substring(bean.billDate,0,10)}</td>
								<td>${bean.good.goodsname}</td>
								<td>${bean.good.goodscode}</td>
								<td>${bean.good.goodsstandard}</td>
								<td>${fn:substring(bean.startTime,0,10)}</td>
								<td>${bean.opcode}</td>
								<td>${bean.opname}</td>
								<td>
								<c:if test="${bean.dispatchingObj=='0'}">
				                ${bean.aaperson.pername}
				                </c:if>
				                <c:if test="${bean.dispatchingObj=='1'}">
				                ${bean.aagroup.groupname}
				                </c:if>
				                <c:if test="${bean.dispatchingObj=='2'}">
				                ${bean.aaprovidercustomer.pcName}
				                </c:if></td>
				                <td><fmt:formatNumber type="number" value="${bean.disNum}" minFractionDigits="2"/></td>
				                <td><fmt:formatNumber type="number" value="${bean.reportOkNum}" minFractionDigits="2"/></td>
				                <td><fmt:formatNumber type="number" value="${bean.reportNotOkNum}" minFractionDigits="2"/></td>
				                <td>${bean.notes}</td>
				                <%-- <td>${bean.reportProblemNum}</td> --%>
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