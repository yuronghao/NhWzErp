<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<style type="text/css">

</style>
</head>

	<script type="text/javascript">
	    $(function() {
	        var $my_chose = $("input[name='my_chose']");
	        $my_chose.click(function(){
	        	   var re_check=this.checked;
	        		//单选,清除所有选中，再选择目标
	        		$('input[name="my_chose"]').each(function(index, element) {
	    	        	this.checked = false;
	                });
	        		this.checked = re_check;
	        });
	    });
	</script>


<body>
	<form action="" id="myform" method="post" name="myform">
		<input type="hidden" name="id" value="${id}">
		<div class="EMonecontent" style="">
			<div style="width: 100%;height: 15px;"></div>
		</div>
		<!--主体部分-->
	 	<div class="mainword">
	 		<!-- <div class="tabletitle">物料设置</div> -->	
	 			
	 			<div class="creattable" style="width:100%;margin-top: 5px;margin-bottom: 15px;">
	 			 <input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" > <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit()">
		 			<div style="margin-top: 5px">
			 			<table id="myTable">
				 			<tbody>
				 				<tr>
				 					<th style="width: 8%;"><input id="checkAll" type="checkbox" disabled="disabled" /></th>
				 					<th style="width: 8%;">序号</th>
									<th style="width: 15%;">货位编号</th>
									<th style="width:20%;">货位名称</th>
				 				</tr>
				 				
				 				<c:forEach items="${data.list }" var="gs" varStatus="stat">
					 				<tr>
					 					<td >
				 							<input type="checkbox"  class="goodsSelected" id="check_${gs.gid }" name="my_chose" value="${gs.gid }" allocationCode="${gs.code }" allocationName="${gs.name}"  /> 
					 					</td>
					 					<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
					 					<td >${gs.code}</td>
					 					<td >${gs.name}</td>
					 				</tr>
				 				
				 				</c:forEach>

				 						 				 
				 			</tbody>
			 			</table>
			 		</div>
			 		
					<!--表格部分 end-->
					<!--分页部分-->
					<div style="margin-left: -22px">
					<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
					</div>
					
					<!--分页部分 end-->
		
		 		</div>
	 			
	 	</div>
	</form>
</body>
</html>