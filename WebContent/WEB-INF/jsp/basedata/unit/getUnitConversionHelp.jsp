<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>


	<script type="text/javascript">
		
	$(function(){
		$('.unitGroupList').click(function(){
			$('.unitGroupList').removeAttr("style");
			$(this).attr("style","background-color:rgb(60,144,208)");
			
			$('#groupid').val($(this).attr("unitGroupGid"));
			$('#groupname').val($(this).attr("unitGroupName"));
			
			$('#mid').val($(this).attr("mugid"));
			$('#mname').val($(this).attr("muname"));
			
			$('#fid').val($(this).attr("fugid"));
			$('#fname').val($(this).attr("funame"));
		});	
	});
	</script>
	
	




<body>
	<div class="EMonecontent">
		<div style="width: 100%;height: 15px;"></div>
		<!--按钮部分-->
	 	<div class="toolbar">
<!-- 	 		<ul>
	 			<li class="fl"><input type="button" id="ConversionAdd" class="addBtn" value="新增"></li>
	 			<li class="fl"><input type="button" id="ConversionEdit" class="editBtn" value="编辑"></li>
	 			<li class="fl"><input type="button" id="del" class="delBtn" value="删除"> </li>
	 			<li class="fl"><input type="button" class="searchBtn" value="查询"> </li>
	 			<div class="cf"></div>
	 		</ul> -->
	 	</div>
	 	<!--按钮部分 end-->
	 	<!--表格部分-->
	 	<form action="" id="myform">
	 	
	 		<input id="groupid" name="groupid" type="hidden"/>
	 		<input id="groupname" name="groupname" type="hidden"/>
	 		
	 		<input id="mid" name="groupid" type="hidden"/>
	 		<input id="mname" name="groupname" type="hidden"/>
	 		
	 		<input id="fid" name="groupid" type="hidden"/>
	 		<input id="fname" name="groupname" type="hidden"/>
	 	
		 	<div class="creattable">
		 		<div class="tabletitle">单位换算</div>		 		
		 		<div class="createdList">
		 			<table id="myTable">
			 			<tbody>
			 				<tr>
			 					<!-- <th style="width: 120px;"><input  type="checkbox" onclick="if(this.checked==true) { checkAll('strsum'); } else { clearAll('strsum'); }" /></th> -->
			 					<th style="width: 120px;">序号</th>
			 					<th>类别</th>
			 					<th>计量单位组</th>
			 					<th>数量</th>
			 					<th>主单位</th>
			 					<th>等于</th>
			 					<th>数量</th>
			 					<th>辅单位</th>
			 				</tr>
			 				
			 				<c:forEach items="${maps }" var="map" varStatus="vs">
			 					
				 				<tr class="unitGroupList" unitGroupGid="${map.gid }" unitGroupName="${map.unitGroupName }"  mugid="${map.mainUnit }" muname="${map.muname }"  fugid="${map.auxiliaryUnit }" funame="${map.funame }"   >
				 					<%-- <td style="width: 120px;"><input type="checkbox" id=""  value="${map.gid }" name="strsum" /> </td> --%>
				 					<td style="width: 120px;">${vs.count }</td>
				 					
				 					<c:if test="${map.UnitOfMeasurement==1 }">
				 						<td>固定换算</td>
				 					</c:if>
				 					
				 					<c:if test="${map.UnitOfMeasurement==2 }">
				 						<td>浮动换算</td>
				 					</c:if>
				 					
				 					<td>${map.unitGroupName }</td>
				 					<td>1</td>
				 					<td>${map.muname }</td>
				 					<td>=</td>
				 					<td>${map.auxiliaryQuantity }</td>
				 					<td>${map.funame }</td>
				 				</tr>
			 				
			 				</c:forEach>
			 				
	
	
	
			 						 				 
			 			</tbody>
			 		</table>
		 		</div>		 		
		 	</div>
	 	</form>
	 	<!--表格部分 end-->	
	 	<!--分页部分-->
	 	<!--分页部分 end-->
	</div>
</body>
</html>