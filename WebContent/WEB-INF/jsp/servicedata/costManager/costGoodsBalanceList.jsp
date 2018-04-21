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

<div class="mymodal"
     style="position: fixed;width: 100%;height: 100%;background:#AADFFD;z-index: 20;opacity: 0.1;display: none"></div>
<img class="mymodal" src="../img/load.gif" alt=""
     style="position: fixed;top:45%;left:45%;z-index: 21;;display: none">

<body>
<form action="${ctx}/wms/cost_getCostGoodsBalanceList.emi?mainGid=${mainGid}" name="myform" id="myform" method="post">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar" style="display:none">
		 		<ul>
		 		
		 			<input type="hidden" value="${mainGid}" id="mgid" name="mgid">
		 			 <input class="exportBtn" type="button" value="导出" onclick="exportData()">
		 			 
		 			 <input class="searchBtn" type="button" value="低阶码运算" onclick="getLowOrderCode()">
		 			 
		 			 <input class="searchBtn" type="button" value="分摊工费" onclick="shareFee()">
		 			 
		 			 <input class="searchBtn" type="button" value="计算成本" onclick="calculateCost()">
		 			 
		 			 <input class="searchBtn" type="button" value="更新库存单据" onclick="uptRecords()">
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">月末结存</div>		 		
		 		<div>
		 			<table style="width:2500px;">
			 			<tbody>
			 				<tr>
			 					<th style="width: 80px;">序号</th>
			 					<th>存货编码</th>
			 					<th>存货名称</th>
			 					<th>自由项</th>
			 					<th>规格</th>
			 					<th>仓库</th>
			 					<th>u8数量</th>
			 					<th>u8单价</th>
			 					<th>u8金额</th>
			 					
			 					<th>直接材料1</th>
			 					<th>直接材料2</th>
			 					<th>间接材料</th>
			 					<th>委托加工费用</th>
			 					<th>直接人工</th>
			 					<th>间接人工</th>
			 					<th>燃料动力</th>
			 					<th>折旧摊销</th>
			 					<th>其他费用</th>
			 					<th>二次分配</th>
			 					
			 					
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
								<tr>
									<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
									<td title="${bean.goodsCode}" docgid="${bean.goodsCode }" class="billcode">${bean.goodsCode}</td>
									<td title="">${bean.goodsName}</td>
									<td title="${bean.goodsStandard }">${bean.goodsStandard }</td>
									<td title="${bean.cfree1}">${bean.cfree1}</td>
									<td title="">${bean.cwhName}</td>
									<td title="${bean.number}"><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.number}"></fmt:formatNumber></td>
									<td title="${bean.iunitCost}"><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="6" value="${bean.iunitCost}"></fmt:formatNumber></td>
									<td title="${bean.iprice}"><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.iprice}"></fmt:formatNumber></td>
									
									
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf001}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf002}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf003}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf004}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf005}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf006}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf007}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf008}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf009}"></fmt:formatNumber></td>
									<td title=""><fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.icostf010}"></fmt:formatNumber></td>
									
									
									
									
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
			$('.mymodal').show();
			$.ajax({
				data: {mainGid:$('#mgid').val()},
				type: 'POST',
				url: "${ctx}/wms/cost_getLowOrderCode.emi",
				dataType:"JSON",
				success: function(req){
					if(req.success==1){
						$.dialog.alert_s("低阶码计算成功");
						window.location.reload();
						$('.mymodal').hide();
						
					}else{
						$.dialog.alert_e(req.failInfor);
						$('.mymodal').hide();
					}
				}
			})
		}
		
		//分摊费用
		function shareFee(){
			$('.mymodal').show();
			$.ajax({
				data: {mainGid:$('#mgid').val()},
				type: 'POST',
				url: "${ctx}/wms/cost_shareFee.emi",
				dataType:"JSON",
				success: function(req){
					if(req.success==1){
						$.dialog.alert_s("分摊工费成功");
						window.location.reload();
						$('.mymodal').hide();
						
					}else{
						$.dialog.alert_e(req.failInfor);
						$('.mymodal').hide();
					}
				}
			})
		}
		
		//计算成本
		function calculateCost(){
			$('.mymodal').show();
			$.ajax({
				data: {mainGid:$('#mgid').val()},
				type: 'POST',
				url: "${ctx}/wms/cost_calculateCost.emi",
				dataType:"JSON",
				success: function(req){
					if(req.success==1){
						$.dialog.alert_s("计算成本成功");
						window.location.reload();
						$('.mymodal').hide();
						
					}else{
						$.dialog.alert_e(req.failInfor);
						$('.mymodal').hide();
					}
				}
			})
		}

		
		//更新u8单据
		function uptRecords(){
			$('.mymodal').show();
			$.ajax({
				data: {mainGid:$('#mgid').val()},
				type: 'POST',
				url: "${ctx}/wms/cost_uptRecords.emi",
				dataType:"JSON",
				success: function(req){
					if(req.success==1){
						$.dialog.alert_s("计算成本成功");
						window.location.reload();
						$('.mymodal').hide();
						
					}else{
						$.dialog.alert_e(req.failInfor);
						$('.mymodal').hide();
					}
				}
			})
		}
		
		
		</script>
		
	</body>
</html>