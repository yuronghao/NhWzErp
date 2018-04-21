<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单位</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>


	<script type="text/javascript">
		
	$(function(){
		$('.wareHouseList').click(function(){
			
			$('.wareHouseList').removeAttr("style");
			$(this).attr("style","background-color:rgb(60,144,208)");
			
			$('#id').val($(this).attr("wareHouseGid"));
			$('#name').val($(this).attr("wareHouseName"));
		});	
	});
	</script>
	
	

<body>

	<div class="EMonecontent">
		<div style="width: 100%;height: 15px;"></div>
		
		<input id="id" name="id" type="hidden"/>
	 	<input id="name" name="name" type="hidden"/>

	 	<!--表格部分-->
	 	<form action="" id="myform">
		 	<div class="creattable">
		 		<div class="tabletitle">仓库档案</div>
		 		<div style="width: 100%;height: 15px;"></div>
		 		<div class="createdList">
		 			<table id="myTable">
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">序号</th>
			 					<th>仓库编号</th>
			 					<th>仓库名称</th>
			 					<th>地址</th>
			 					<th>联系人</th>
			 					<th>联系电话</th>
			 				</tr>
			 				
			 				<c:forEach items="${aaWarehouses }" var="aaWarehouse" varStatus="vs">
				 				<tr class="wareHouseList" wareHouseGid="${aaWarehouse.gid }" wareHouseName="${aaWarehouse.whname }" >
				 					<td>${vs.count }</td>
				 					<td>${aaWarehouse.whcode }</td>
				 					<td>${aaWarehouse.whname }</td>
				 					<td>${aaWarehouse.whaddr }</td>
				 					<td>${aaWarehouse.linkman }</td>
				 					<td>${aaWarehouse.whtel }</td>
				 				</tr>
			 				</c:forEach>
			 				 
			 			</tbody>
			 		</table>
		 		</div>		 		
		 	</div>
	 	</form>
	 	
	 	<!--表格部分 end-->	
	 	
	</div>


</body>
</html>