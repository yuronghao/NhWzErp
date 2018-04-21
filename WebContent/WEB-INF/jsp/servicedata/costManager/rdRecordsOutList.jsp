<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>报工/入库列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>
<body>
<form action="${ctx}/wms/cost_getRdRecordsOutList.emi?mainGid=${mainGid}" name="myform" id="myform" method="post">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 		
		 			<input type="hidden" value="${mainGid}" id="mgid" name="mgid">
		 			<!-- <input class="exportBtn" type="button" value="导出" onclick="exportData()"> -->
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">材料出库列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 80px;">序号</th>
			 					<th>订单编号</th>
			 					<th>单据日期</th>
			 					<th>存货编码</th>
			 					<th>存货名称</th>
			 					<th>自由项</th>
			 					<th>规格</th>
			 					<th>数量</th>
			 					<th>单价</th>
			 					<th>金额</th>
			 					<th>仓库名称</th>
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
								<tr>
									<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
									<td title="${bean.produceOrderCode}" docgid="${bean.produceOrderCode }" class="billcode">${bean.produceOrderCode}</td>
									<td title="${bean.billdate }">${fn:substring(bean.billdate,0,10)}</td>
									<td title="${bean.goodsCode}">${bean.goodsCode}</td>
									<td title="">${bean.goodsName}</td>
									<td title="${bean.cfree1}">${bean.cfree1}</td>
									<td title="${bean.goodsStandard}">${bean.goodsStandard}</td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.inum}"></fmt:formatNumber> </td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.iunitCost}"></fmt:formatNumber> </td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.iprice}"></fmt:formatNumber> </td>
									<td title="">${bean.cwhName}</td>
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
		function exportData(){
			location.href = "${ctx}/wms/cost_exportReportProductInList.emi?mainGid=${mainGid}";
		}
		
		//低阶码运算
		function getLowOrderCode(){
			
			$.ajax({
				data: {mainGid:$('#mgid').val()},
				type: 'POST',
				url: "${ctx}/wms/cost_getLowOrderCode.emi",
				dataType:"JSON",
				success: function(req){
					if(req.success==1){
						$.dialog.alert_s("低阶码计算成功");
						
					}else if(req=='error'){
						$.dialog.alert_e('取数失败');
					}
				}
			})
		}

		
		</script>
		
	</body>
</html>