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
			$('.addBtn').click(function() {
			    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'新增部门档案',
			    width: '800px',
				height: '500px',
				content: 'url:${ctx}/wms/org_toAddDepartment.emi',
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
				      				  url: '${ctx}/wms/org_addDepartment.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/org_getDepartment.emi";});
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
				var gid;
				if((document.getElementById("rightframe").contentDocument).getElementById('depGid').value==""){
					$.dialog.alert(tip_editSelect);
					return;
				}else{
					gid=(document.getElementById("rightframe").contentDocument).getElementById('depGid').value;
				}
				
			    $.dialog({
					drag: true,
					lock: true,
					resize: false,
					title:'修改部门档案',
				    width: '800px',
					height: '500px',
					content: 'url:${ctx}/wms/org_toEditDepartment.emi?gid='+gid,
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
					      				  url: '${ctx}/wms/org_editDepartment.emi',
					      				  success: function(req){
					      					  if(req=='success'){
					      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/org_getDepartment.emi";});
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
				
				
				var gid;
				if((document.getElementById("rightframe").contentDocument).getElementById('depGid').value==""){
					$.dialog.alert(tip_deleteSelect);
					return;
				}else if($('#isLast').val()=="true"){
					$.dialog.alert_w("非末级,不能删除");
					return;
				}
				else{
					gid=(document.getElementById("rightframe").contentDocument).getElementById('depGid').value;
				}
				
				$.dialog.confirm(tip_confirmDelete, function(){		
					$.ajax({
						  data:$("#myform").serialize(),
						  type: 'POST',
						  url: "${ctx}/wms/org_deleteDepartment.emi?gid="+gid,
						  dataType:'text',
						  success: function(req){
							  if(req=='success'){
								  $.dialog.alert_s('删除成功',function(){
									  location.href="${ctx}/wms/org_getDepartment.emi";
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
	 			<!--<li class="fl"><input type="button" class="backBtn" value="返回" onclick="history.go(-1)"> </li>-->
	 			<!-- <li class="fl"><input type="button" class="saveBtn" id="saveBtn" value="保存" disabled="disabled"> </li> -->
		 		<li class="fl"><input type="button" id="MaterieFileAdd" class="addBtn" value="新增"></li>
		 		<li class="fl"><input type="button" id="MaterieFileEdit" class="editBtn" value="编辑"></li>
		 		<li class="fl"><input type="button" id="del" class="delBtn" value="删除"> </li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div>

	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="tabletitle">部门设置</div>	
	 		<div class="xz_attribute"  >
	 			
	 			<input type="hidden" id="isLast" name="isLast"/>
	 			<input type="hidden" id="urlAddress" name="urlAddress" value=""/>    <!--跳转地址-->
	 			
	 			<div class="tree_div fl" style="width:17%;">
	 				<iframe src="${ctx}/wms/org_getDepartmentTree.emi" style="border:1px #ccc solid;"  id="" name="" frameborder="0" width="100%"  height="500px;"></iframe>
	 			</div>
	 			
	 			<div class="fl" style="width:81%;margin-top: 20px;">
	 				<iframe src="${ctx }/wms/org_getDepartmentDetail.emi" frameborder="0" id="rightframe" name="rightframe" width="100%" style="margin-left:5px;min-height: 500px;"></iframe>
	 			</div>
	 			
	 			<div class="cf"></div>
	 			
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	 	 
	</div>


</body>
</html>