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
<form action="" name="myform" id="myform" method="post">
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
		 		<div class="tabletitle">费用取数列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 80px;">序号</th>
			 					<th>成本项目</th>
			 					<th>金额</th>
			 					<th>年</th>
			 					<th>月</th>
			 					<th>部门名称</th>
			 					<th>编辑</th>
			 				</tr>
			 				<c:forEach var="bean" items="${data }" varStatus="stat">
								<tr>
									<td>${stat.count }</td>
									<td title="" >${bean.costItemName}</td>
									<td title=""><input  class="todeal" type="text" value="<fmt:formatNumber type="number" groupingUsed="false" maxFractionDigits="2" value="${bean.iprice}"></fmt:formatNumber>"/> </td>
									<td title="">${bean.year}</td>
									<td title="">${bean.month}</td>
									<td title="">${bean.deptName}</td>
									<td title=""><input type="button" value="保存" onclick="upt(this)" feeGid="${bean.gid}" /></td>
								</tr>
							</c:forEach>
			 			</tbody>
			 		</table>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		</div>
		</form>
		
		<script type="text/javascript">
		
		function upt(obj){
		  var r=confirm("确定修改数据？")
		  if (r==true){
				  $.ajax({
				  url:"${ctx}/wms/cost_uptCostFee.emi",
				  type:"post",
				  data:{"feeGid":$(obj).attr("feeGid"),"toUptFee":$(obj).parent().parent().find(".todeal").val()},
				  dataType:"json",
				  success:function(res){
					  if(res.success==1){
						  alert("保存成功")
					  }
				  }
			  }) 
		  }else{
			  
		  }
		}

		</script>
		
	</body>
</html>