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
		$('.addBtn').click(function(e){
				$.dialog({ 
					drag: false,
					lock: true,
					resize: false,
					title:'计量单位',
				    width: '1000px',
					height: '300px',
					content: 'url:${ctx}/wms/warehouse_toAddWarehouse.emi',
					button: [
					          {
					              name: '保存',
					              callback: function () {
					            	  
					            	  if(!this.content.checkdata()){
						  					return false;
						  				}
					            		$.ajax({
					      				  data: $("#myform",this.content.document).serialize(),
					      				  type: 'POST',
					      				  url: '${ctx}/wms/warehouse_addWarehouse.emi',
					      				  success: function(req){
					      					  if(req=='success'){
					      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/warehouse_getWarehouse.emi";});
					      					  }else{
					      						  $.dialog.alert_e(req);
					      					  }
					      				  },
					      				  error:function(){
					      					  $.dialog.alert_e("添加失败");
					      				  }
					      			});
					                  return false;
					              },
					              focus: true
					          },

					          {
					              name: '关闭'
					          }
					      ]
				});				

		});			 
		
		
		
		$('.editBtn').click(function() {
			var chek = document.getElementsByName("strsum");
			var total = 0;
			var gid = '';
			for (var i = 0; i < chek.length; i++) {
				if (chek[i].checked) {
					total += 1;
					gid = chek[i].value;
				}
			}
			if (total > 1) {
				$.dialog.alert(tip_dontMultEdit);
				return false;
			} else if (total < 1) {
				$.dialog.alert(tip_editSelect);
				return false;
			}
		    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'计量单位',
			    width: '1000px',
				height: '300px',
				content: 'url:${ctx}/wms/warehouse_toUptWarehouse.emi?gid='+gid,
				button: [
				          {
				              name: '保存',
				              callback: function () {
				            	  if(!this.content.checkdata()){
					  					return false;
					  				}
				            		$.ajax({
				      				  data: $("#myform",this.content.document).serialize(),
				      				  type: 'POST',
				      				  url: '${ctx}/wms/warehouse_uptWarehouse.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/warehouse_getWarehouse.emi";});
				      					  }else{
				      						  $.dialog.alert_e(req);
				      					  }
				      				  },
				      				  error:function(){
				      					  $.dialog.alert_e("修改失败");
				      				  }
				      			});
				                  return false;
				              },
				              focus: true
				          },

				          {
				              name: '关闭'
				          }
				      ]

			});
		});
		
		
		$('.delBtn').click(function() {
			var total=0;
			var chek = document.getElementsByName("strsum");
			for(var i=0;i<chek.length;i++){
				if(chek[i].checked){
					 total += 1;
				}
			}
			if(total<=0)
			{
				$.dialog.alert(tip_deleteSelect);
				return false;
			}
			$.dialog.confirm(tip_confirmDelete, function(){		
				$.ajax({
					  data:$("#myform").serialize(),
					  type: 'POST',
					  url: '${ctx}/wms/warehouse_deleteWarehouse.emi',
					  dataType:'text',
					  success: function(req){
						  if(req=='success'){
							  $.dialog.alert_s('删除成功',function(){
								  location.href="${ctx}/wms/warehouse_getWarehouse.emi";
							  });
						  }else {
							  $.dialog.alert_e("删除失败");
						  }
						  
					  }
				});
			}, function(){
			    //$.dialog.tips('执行取消操作');
			});
			
		});
		
		
		
		
	});
	</script>
	
	

<body>

	<div class="EMonecontent">
		<div style="width: 100%;height: 15px;"></div>
		<!--按钮部分-->
	 	<div class="toolbar">
	 		
	 		<ul>
	 			<li class="fl"><input type="button" id="HouseAdd" class="addBtn" value="新增"></li>
	 			<li class="fl"><input type="button" class="editBtn" value="编辑"></li>
	 			<li class="fl"><input type="button" id="del" class="delBtn" value="删除"> </li>
	 			<!--<li class="fl"><input type="button" class="searchBtn" value="查询"> </li>-->
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
	 	<!--按钮部分 end-->
	 	<!--表格部分-->
	 	<form action="${ctx }/wms/warehouse_getWarehouse.emi" id="myform" method="post">
		 	<div class="creattable">
		 		<div class="tabletitle">仓库档案</div>
		 		<div style="width: 100%;height: 15px;"></div>
		 		<div class="">
		 			<table id="myTable">
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;"><input id="" type="checkbox" onclick="if(this.checked==true) { checkAll('strsum'); } else { clearAll('strsum'); }" /></th>
			 					<th style="width: 120px;">序号</th>
			 					<th>仓库编号</th>
			 					<th>仓库名称</th>
			 					<th>地址</th>
			 					<th>联系人</th>
			 					<th>联系电话</th>
			 				</tr>
			 				
			 				<c:forEach items="${data.list }" var="aaWarehouse" varStatus="vs">
				 				<tr>
				 					<td><input type="checkbox" id="" name="strsum" value="${aaWarehouse.gid }"/> </td>
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
		 	
		 <!--表格部分 end-->	
	 	<!--分页部分-->
		<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
		<!--分页部分 end-->
		 	
	 	</form>
	 	

	</div>


</body>
</html>