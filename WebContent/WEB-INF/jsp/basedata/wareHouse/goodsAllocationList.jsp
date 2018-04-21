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
<script type="text/javascript" src="${ctx}/scripts/emiwms.js"></script>
</head>


	<script type="text/javascript">
	
		var saveFlag;//修改标志  1修改保存 0增加保存
		var inputToDeal;
		function init(){
			inputToDeal=$(".toDeal",document.getElementById("rightframe").contentDocument);
			inputCannotUse(inputToDeal);
		}
		
		$(function(){
			
			$('.addBtn').click(function() {
				saveFlag=0;
				inputToDeal=$(".toDeal",document.getElementById("rightframe").contentDocument);
				
				for(var i=0;i<inputToDeal.length;i++){
					inputToDeal.eq(i).val("");
				}
				
				inputCanUse(inputToDeal);
				addBtn();
			});
			
			
			$('.saveBtn').click(function(){
				
          	  if(!window.frames["rightframe"].checkdata()){
					return;
				}
				
          	  
          	  	if(saveFlag==0){//增加保存
            		$.ajax({
      				  data: $("#myform",document.getElementById("rightframe").contentDocument).serialize(),
      				  type: 'POST',
      				  url: '${ctx}/wms/warehouse_addGoodsAllocation.emi',
      				  success: function(req){
      					  if(req=='success'){
      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/warehouse_getGoodsAllocation.emi";});
      					  }else{
      						  $.dialog.alert_e(req);
      					  }
      				  },
      				  error:function(){
      					  $.dialog.alert_e("添加失败");
      				  }
      				});
          	  	}
          	  	
          	  	if(saveFlag==1){//修改保存
            		$.ajax({
        				  data: $("#myform",document.getElementById("rightframe").contentDocument).serialize(),
        				  type: 'POST',
        				  url: '${ctx}/wms/warehouse_uptGoodsAllocation.emi',
        				  success: function(req){
        					  if(req=='success'){
        						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/warehouse_getGoodsAllocation.emi";});
        					  }else{
        						  $.dialog.alert_e(req);
        					  }
        				  },
        				  error:function(){
        					  $.dialog.alert_e("修改失败");
        				  }
        				});
          	  	}

			});
			
			
			
			$('.editBtn').click(function() {
				
				if($("#code",document.getElementById("rightframe").contentDocument).val()==''){
					$.dialog.alert(tip_editSelect);
					return;
				}
				
				saveFlag=1;
				inputToDeal=$(".toDeal",document.getElementById("rightframe").contentDocument);
				inputCanUse(inputToDeal);
				revBtn();
			});
			
			
			$('.delBtn').click(function() {
				
				if($("#code",document.getElementById("rightframe").contentDocument).val()==''){
					$.dialog.alert(tip_deleteSelect);
					return;
				}

				$.dialog.confirm(tip_confirmDelete, function(){		
					$.ajax({
						  data:$("#myform",document.getElementById("rightframe").contentDocument).serialize(),
						  type: 'POST',
						  url: '${ctx}/wms/warehouse_deleteGoodsAllocation.emi',
						  dataType:'text',
						  success: function(req){
							  if(req=='success'){
								  $.dialog.alert_s('删除成功',function(){
									  location.href="${ctx}/wms/warehouse_getGoodsAllocation.emi";
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
			
			
 			$('#giveUpBtn').click(function(){
 				giveUpBtn();
 			})
 			
 			
 			
 			$('#test').click(function() {
				
			    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'物料档案',
			    width: '1100px',
				height: '490px',
				content: 'url:${ctx}/wms/goods_getGoodsHelp.emi',
				
				okVal:"确定",
				ok:function(){
					
					var chek = $('.goodsSelected:checked',this.content.document.getElementById("rightframe").contentDocument); 
					var total = 0;
					
					var arr=new Array();
					for (var i = 0; i < chek.length; i++) {
						var values={};
						values['goodGid']=chek.eq(i).val();
						values['goodCode']=chek.eq(i).attr("goodsCode");
						values['goodsstandard']=chek.eq(i).attr("goodsstandard");
						values['unitName']=chek.eq(i).attr("unitName");
						values['cassComUnitName']=chek.eq(i).attr("cassComUnitName");
						arr.push(values);
					}
					
					if (total < 1) {
						$.dialog.alert(tip_editSelect);
						return false;
					}
					
				},
				cancelVal:"关闭",
				cancel:true
				
				});
			}); 
			
 			
		});
	</script>
	
	




<body onload="init();">
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<!--按钮部分-->
	 	<div class="toolbar">
	 		<ul>
	 			<!--<li class="fl"><input type="button" class="backBtn" value="返回" onclick="history.go(-1)"> </li>-->
	 			
		 		<li class="fl"><input type="button" id="addBtn" class="addBtn" value="新增"></li>
		 		<li class="fl"><input type="button" id="revBtn" class="editBtn" value="编辑"></li>
		 		<li class="fl"><input type="button" class="saveBtn" id="saveBtn" value="保存" > </li>
		 		<li class="fl"><input type="button" class="giveUpBtn" id="giveUpBtn" value="放弃" > </li>
		 		<li class="fl"><input type="button" id="delBtn" class="delBtn" value="删除"> </li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div>

	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="tabletitle">货位设置</div>	
	 		<div class="xz_attribute"  >
	 			<div class="tree_div fl" style="width:17%;">
	 				<iframe src="${ctx}/wms/warehouse_getWarehouseTree.emi?classtype='03'" style="border:1px #ccc solid;"  id="" name="" frameborder="0" width="100%"  height="500px;"></iframe>
	 			</div>
	 			
	 			<div class="fl" style="width:81%;margin-top: 20px;">
	 				<iframe src="${ctx }/wms/warehouse_getRightGoodsAllocation.emi" frameborder="0" id="rightframe" name="rightframe" width="100%" style="margin-left:5px;min-height: 500px;"></iframe>
	 			</div>
	 			
	 			<div class="cf"></div>
	 			
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	 	 
	</div>

</body>
</html>