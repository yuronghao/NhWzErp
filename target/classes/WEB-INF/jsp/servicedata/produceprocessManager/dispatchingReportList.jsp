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
<form action="${ctx}/wms/produceprocess_dispatchingReportList.emi" name="myform" id="myform" method="post">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			订单编号：<input type="text" name="billcode" placeholder="订单编号" class="write_input" style="margin-right: 50px;width: 150px" value="${billcode }">
		 			存货：<input type="text" name="goods" placeholder="存货编码/名称" class="write_input" style="margin-right: 50px;width: 150px" value="${goods }">
		 			开工日期：<input type="text" name="startDate" placeholder="开始时间" class="write_input Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" style="width: 100px" value="${startDate }" > - 
		 			<input type="text" name="endDate" placeholder="结束时间" class="write_input Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" style="margin-right: 50px;width: 100px" value="${endDate }">
		 			工序：<input type="text" name="process" placeholder="工序编码/名称" class="write_input" style="margin-right: 50px;width: 150px" value="${process }">
		 			 <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit();">
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">报工列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="">序号</th>
			 					<th>订单编号</th>
			 					<th>单据日期</th>
			 					<th>存货编码</th>
			 					<th>存货名称</th>
			 					<th>规格</th>
			 					<th>下达生产量</th>
			 					<th>工序</th>
			 					<th>报工日期</th>
			 					<th>报工对象（人或组）</th>
			 					<th>报工数量</th>
			 					
<!-- 			 					<th>设备编码</th>
			 					<th>设备名称</th>
			 					<th>设备部门</th>
			 					<th>模具编码</th>
			 					<th>模具名称</th>
			 					<th>模比</th>
			 					<th>班次</th> -->
			 					
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
							<tr>
								<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
								<td title="${bean.billCode}" rptgid=${bean.rptgid } class="billcode">${bean.billCode}</td>
								<td title="${bean.billDate }">${fn:substring(bean.billDate,0,10)}</td>
								<td title="${bean.goodsCode}">${bean.goodsCode}</td>
								<td title="${bean.goodsName}">${bean.goodsName}</td>
								<td title="${bean.goodsStandard}">${bean.goodsStandard}</td>
								<td title="${bean.produceNumber}"><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.produceNumber}"></fmt:formatNumber> </td>
								<td title="${bean.opName}">${bean.opName}</td>
								<td title="${bean.endTime}">${fn:substring(bean.endTime,0,19)}</td>
								<td title="${bean.dispatchingObjName}">${bean.dispatchingObjName}</td>
								<td title="${bean.reportOkNum}"><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.reportOkNum}"></fmt:formatNumber></td>
							
<%-- 								<td>${bean.equipmentCode}</td>
			 					<td>${bean.equipmentName}</td>
			 					<td>${bean.equipmentDeptName}</td>
			 					<td>${bean.mouldCode}</td>
			 					<td>${bean.mouldName}</td>
			 					<td>${bean.mouldRatio}</td>
			 					<td><c:if test="${bean.workingTime==0}">白班</c:if><c:if test="${bean.workingTime==1}">夜班</c:if> </td> --%>
			 					
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
		
		<script type="text/javascript">
			$(function(){
				$('.billcode').dblclick(function(){
					
					var gid=$(this).attr("rptgid");
					window.parent.CreateDiv('erp_03_03_07',"${ctx}/wms/produceprocess_toReportWork.emi?reportGid="+gid,'报工',true);
				});
			});
		</script>
	</body>
</html>