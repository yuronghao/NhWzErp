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
				    width: '700px',
					height: '300px',
					content: 'url:${ctx}/wms/unit_toAddUnit.emi',
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
					      				  url: '${ctx}/wms/unit_addUnit.emi',
					      				  success: function(req){
					      					  if(req=='success'){
					      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/unit_getUnit.emi";});
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
			    width: '700px',
				height: '300px',
				content: 'url:${ctx}/wms/unit_toUptUnit.emi?gid='+gid,
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
				      				  url: '${ctx}/wms/unit_uptUnit.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/unit_getUnit.emi";});
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
					  url: '${ctx}/wms/unit_deleteUnit.emi',
					  dataType:'text',
					  success: function(req){
						  if(req=='success'){
							  $.dialog.alert_s('删除成功',function(){
								  location.href="${ctx}/wms/unit_getUnit.emi";
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
	 			<!-- <li class="fl"><input type="button" id="MeasurementGroup" class="fenzBtn" value="分组"></li> -->
	 			<li class="fl"><input type="button" id="MeasurementUnit" class="addBtn" value="新增"></li>
	 			<li class="fl"><input type="button" id="MaterieFileEdit" class="editBtn" value="编辑"></li>
	 			<li class="fl"><input type="button" id="del" class="delBtn" value="删除"> </li>
	 			<!--<li class="fl"><input type="button" class="searchBtn" value="查询"> </li>-->
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
	 	<!--按钮部分 end-->
	 	<!--表格部分-->
	 	<form action="" id="myform">
		 	<div class="creattable">
		 		<div class="tabletitle">计量单位</div>		 		
		 		<div class="createdList">
		 			<table id="myTable">
			 			<tbody>
			 				<tr>
			 					<th style="width: 120px;">
			 						<input id="" type="checkbox" name="" onclick="if(this.checked==true) { checkAll('strsum'); } else { clearAll('strsum'); }" />
			 					</th>
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
			 				
				 				<tr>
				 					<td style="width: 120px;"><input type="checkbox"  name="strsum" value="${units.gid }" /> </td>
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