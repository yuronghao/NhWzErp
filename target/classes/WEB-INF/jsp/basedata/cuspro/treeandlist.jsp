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
			    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'新增客商档案',
			    width: '1100px',
				height: '590px',
				content: 'url:${ctx}/wms/cuspro_toAddCuspro.emi',
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
				      				  url: '${ctx}/wms/cuspro_addCuspro.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/cuspro_treeandlist.emi";});
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
			var chek = document.getElementById("rightframe").contentDocument.getElementsByName("strsum");
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
				title:'修改客商档案',
			    width: '1100px',
				height: '590px',
				content: 'url:${ctx}/wms/cuspro_toUpdateCuspro.emi?gid='+gid,
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
				      				  url: '${ctx}/wms/cuspro_updateCuspro.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/cuspro_treeandlist.emi";});
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
			var chek = document.getElementById("rightframe").contentDocument.getElementsByName("strsum");
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
					  data:$("#myform",document.getElementById("rightframe").contentDocument).serialize(),
					  type: 'POST',
					  url: '${ctx}/wms/cuspro_deleteCuspro.emi',
					  dataType:'text',
					  success: function(req){
						  if(req=='success'){
							  $.dialog.alert_s('删除成功',function(){
								  location.href="${ctx}/wms/cuspro_treeandlist.emi";
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
					<iframe src="${ctx}/wms/cuspro_getClassifyList.emi?classtype='01','02'" style="border:1px #ccc solid;" id="" name="" frameborder="0" width="100%" height="500px;"></iframe>
					
				</div>
				<div class="fl" style="width:81%;margin-top: 20px">
				 	<iframe src="${ctx}/wms/cuspro_getcusproList.emi" frameborder="0" id="rightframe" name="rightframe" width="100%" style="margin-left:5px;min-height: 500px;" ></iframe>
				</div>
				
				<!-- <div style="height:20px;"></div> -->	
			</div>
		</div>
	</body>
</html>
