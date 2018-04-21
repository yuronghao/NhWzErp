<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>
<script type="text/javascript">
	$(function() {
		$('.searchBtn').click(function(e) {
							$.dialog({
										drag : false,
										lock : true,
										resize : false,
										title : '查询条件',
										width : '300px',
										height : '100px',
										content : 'url:${ctx}/wms/prty_getSerchAA_Category.action',
										okVal : '查询',
										ok:function(){
											debugger;
											var Categorname = this.content.document.getElementById('Categorname').value;
											$("#sname").val(Categorname);
											document.forms[0].submit();
										},
										cancelVal : '关闭',
										cancel : true
									});
		});
		$('.addBtn').click(function() {
			var orgclassid = document.getElementById("leftframe").contentDocument.getElementById("orgclassid").value;
			if(orgclassid==""){
				$.dialog.alert("请选择一个组织");
				return false;
			}
			    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'新增组织信息',
			    width: '800px',
				height: '590px',
				content: 'url:${ctx}/wms/sob_toAddorg.emi?orgclassid='+orgclassid,
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
				      				  url: '${ctx}/wms/sob_addOrg.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/sob_treeandorg.emi";});
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
			var orgclassid = document.getElementById("leftframe").contentDocument.getElementById("orgclassid").value;
			if(orgclassid==""){
				$.dialog.alert("请选择一个组织");
				return false;
			}
		    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'修改组织信息',
			    width: '800px',
				height: '590px',
				content: 'url:${ctx}/wms/sob_toUpdateorg.emi?orgclassid='+orgclassid,
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
				      				  url: '${ctx}/wms/sob_updateorg.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/sob_treeandorg.emi";});
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
			var orgclassid = document.getElementById("leftframe").contentDocument.getElementById("orgclassid").value;
			if(orgclassid==""){
				$.dialog.alert("请选择一个组织");
				return false;
			}
			$.dialog.confirm(tip_confirmDelete, function(){		
				$.ajax({
					  data:{orgclassid:orgclassid},
					  type: 'POST',
					  url: '${ctx}/wms/sob_deleteorg.emi',
					  dataType:'text',
					  success: function(req){
						  if(req=='success'){
							  $.dialog.alert_s('删除成功',function(){
								  location.href="${ctx}/wms/sob_treeandorg.emi";
							  });
						  }else {
							  $.dialog.alert_e("请先删除下面子类");
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
		<div class="EMonecontent" style="padding-top: 10px"> 
			<!-- <div style="width: 100%;height: 15px;"></div> -->
			<!--按钮部分-->
		 	 <div class="toolbar">
		 		<ul>
		 			<li class="fl"><input type="button" id="mfAdd" class="addBtn" value="新增"></li>
		 			<li class="fl"><input type="button" class="editBtn" value="编辑"></li>
		 			<li class="fl"><input type="button" id="del" class="delBtn" value="删除"> </li>
		 			<div class="cf"></div>
		 		</ul>
		 	</div> 
		 	<!--按钮部分 end-->
		 	<div class="xz_attribute" style="">
				<!-- <div style="height:10px;"></div> -->
				<div class="fl tree_div" style="width:17%;margin-left:16px;">
					<!-- 树状图 -->
					<iframe src="${ctx}/wms/sob_getorgclassify.emi" style="border:1px #ccc solid;" id="leftframe" name="leftframe" frameborder="0" width="100%" height="500px;"></iframe>
					
				</div>
				<div class="fl" style="width:81%;margin-top: 20px">
				 	<iframe src="${ctx}/wms/sob_getorganizeInfo.emi" frameborder="0" id="rightframe" name="rightframe" width="100%" style="margin-left:5px;min-height: 500px;" ></iframe>
				</div>
				
				<!-- <div style="height:20px;"></div> -->	
			</div>
		</div>
	</body>
</html>
