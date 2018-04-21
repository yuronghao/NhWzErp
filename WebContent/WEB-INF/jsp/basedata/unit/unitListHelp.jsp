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
		$('.unitList').click(function(){
			
			$('.unitList').removeAttr("style");
			$(this).attr("style","background-color:rgb(60,144,208)");
			
			$('#id').val($(this).attr("unitGid"));
			$('#name').val($(this).attr("unitName"));
		});	
	});
	</script>
	
	

<body>

	<div class="EMonecontent">
		<div style="width: 100%;height: 15px;"></div>
		<!--按钮部分-->
<!-- 	 	<div class="toolbar">
	 		<ul>
	 			<li class="fl"><input type="button" id="MeasurementGroup" class="fenzBtn" value="分组"></li>
 	 			<li class="fl"><input type="button" id="MeasurementUnit" class="addBtn" value="新增"></li>
	 			<li class="fl"><input type="button" id="MaterieFileEdit" class="editBtn" value="编辑"></li>
	 			<li class="fl"><input type="button" id="del" class="delBtn" value="删除"> </li>
	 			<li class="fl"><input type="button" class="searchBtn" value="查询"> </li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div> -->
	 	<!--按钮部分 end-->
	 	<!--表格部分-->
	 	<form action="" id="myform">
	 	
	 		<input id="id" name="id" type="hidden"/>
	 		<input id="name" name="name" type="hidden"/>
	 	
		 	<div class="creattable">
		 		<div class="tabletitle">计量单位</div>		 		
		 		<div class="createdList">
		 			<table id="myTable">
			 			<tbody>
			 				<tr>
<!-- 			 					<th style="width: 120px;">
			 						<input id="" type="checkbox" name="" onclick="if(this.checked==true) { checkAll('strsum'); } else { clearAll('strsum'); }" />
			 					</th> -->
			 					<th style="width: 120px;">序号</th>
			 					<th>单位编号</th>
			 					<th>单位名称</th>
	<!-- 		 					<th>单位组编码</th>
			 					<th>计量单位组名称</th>
			 					<th>计量单位组类别</th>
			 					<th>是否主单位</th>
			 					<th>换算率</th> -->
			 				</tr>
			 				<c:forEach items="${units }" var="units" varStatus="vs">
			 				
				 				<tr class="unitList" unitGid="${units.gid }" unitName="${units.unitname }" >
				 					<%-- <td style="width: 120px;"><input type="checkbox"  name="strsum" value="${units.gid }" /> </td> --%>
				 					<td style="width: 120px;">${vs.count }</td>
				 					<td>${units.unitcode }</td>
				 					<td>${units.unitname }</td>
		<!-- 		 					<td>101</td>
				 					<td></td>
				 					<td>无换算</td>
				 					<td>是</td>
				 					<td></td> -->
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