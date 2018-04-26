<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>领用申请单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
<body>
<form action="${ctx}/wms/wareHouse_materialApplyWarehouseList.emi" name="myform" id="myform" method="post">
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
		 		<div class="tabletitle">领用申请单列表</div>
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">序号</th>
			 					<th>单据号</th>
			 					<!-- <th>部门</th> -->
			 					<th>仓库</th>
			 					<!-- <th>录入人</th>
			 					<th>日期</th>
			 					<th>备注</th> -->
			 					<th>物料编号</th>
								<th>物料名称</th>
								<th>物料规格型号</th>
								<th>主单位</th>
								<th>主数量</th>
								<%--<th>辅单位</th>--%>
								<%--<th>辅数量</th>--%>
								<th>货位号</th>
								<th>条形码</th>
								<%--<th>生产订单编号</th>--%>
								<%--<th>产品名称</th>--%>
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">

							<tr>
								<td style="width: 20px;">${stat.count}</td>
								<td><a href="${ctx}/wms/wareHouse_toAddMaterialApply.emi?materialApplygid=${bean.owhGid}">${bean.owhCode}</a></td>
								<%-- <td>${bean.departName}</td> --%>
								<td>${bean.wareHouseName}</td>					
								<td>${bean.good.goodscode}</td>
								<td>${bean.good.goodsname}</td>
								<td>${bean.good.goodsstandard}</td>
								<td>${bean.good.unitName}</td>
								<td><fmt:formatNumber type="number" value="${bean.number }" minFractionDigits="2" groupingUsed="false"/></td>
								<%--<td>${bean.good.cstComUnitName}</td>--%>
								<%--<c:if test="${not empty bean.good.cstcomunitcode}">	--%>
								<%--<td><fmt:formatNumber type="number" value="${bean.assistNumber}" minFractionDigits="2"/></td>--%>
								<%--</c:if>--%>
								<%--<c:if test="${empty bean.good.cstcomunitcode}">--%>
								<%--<td></td>--%>
								<%--</c:if>--%>
								<td>${bean.alocation}</td>
								<td>${bean.barCode}</td>
								<td>${bean.produceCode}</td> 	
								<td>${bean.goodName}</td> 									
								<%-- <td>${bean.recordPersonName}</td>
								<td>${fn:substring(bean.billdate,0,10)}</td>
								<td>${bean.notes}</td> --%>
							</tr>
							<%-- </c:if>
							</c:forEach> --%>
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