<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>报工入库列表</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">

</head>

<div class="mymodal"
     style="position: fixed;width: 100%;height: 100%;background:#AADFFD;z-index: 20;opacity: 0.1;display: none"></div>
<img class="mymodal" src="../img/load.gif" alt=""
     style="position: fixed;top:45%;left:45%;z-index: 21;;display: none">

<body>
<form action="${ctx}/wms/cost_getCostGoodsBalanceMain.emi" name="myform" id="myform" method="post">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<input type="text" id="startDate" name="startDate" placeholder="取数期间" class="write_input Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM'})" readonly="readonly" style="width: 100px" value="" >
		 			<input class="searchBtn" type="button" value="取数" onclick="getMethod();">
		 			
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">月末结存列表</div>		 		
		 		<div>
		 			<table>
			 			<tbody>
			 				<tr>
			 					<th style="width: 80px;">序号</th>
			 					<th>年份</th>
			 					<th>月份</th>
			 					<th>取数时间</th>
			 					<th>获取</th>
			 				</tr>
			 				<c:forEach var="bean" items="${data.list }" varStatus="stat">
								<tr>
									<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
									<td>${bean.iyear}</td>
									<td>${bean.imonth }</td>
									<td>${fn:substring(bean.ctime,0,10)}</td>
									<td gid='${bean.gid}' onclick="getDetail(this)">详情</td>
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
			//报工/入库取数
			function getMethod(){
				$('.mymodal').show();
				$.ajax({
					data: {start:$('#startDate').val()},
					type: 'POST',
					url: "${ctx}/wms/cost_getCostGoodsBalance.emi",
					dataType:"json",
					success: function(req){
						if(req.success==1){
							$.dialog.alert_s('取数成功');
							window.location.reload();
							$('.mymodal').hide();
						}else{
							$.dialog.alert_e(req.failInfor);
							$('.mymodal').hide();
						}
					},
					error:function(){
						$.dialog.alert_e('error');
						$('.mymodal').hide();
					}
				});
			}
			
			function getDetail(obj){
				window.location.href="${ctx}/wms/cost_getCostGoodsBalanceList.emi?mainGid="+$(obj).attr("gid");
			}
		
		</script>
		
	</body>
</html>