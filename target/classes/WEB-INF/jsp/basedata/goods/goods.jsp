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
				title:'新增物料档案',
			    width: '1100px',
				height: '490px',
				content: 'url:${ctx}/wms/goods_toAddGoods.emi',
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
				      				  url: '${ctx}/wms/goods_addGoods.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/goods_getGoods.emi";});
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
				var chek = document.getElementById("rtframe").contentDocument.getElementsByName("lyzj_chose");
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
					title:'修改物料档案',
				    width: '1100px',
					height: '490px',
					content: 'url:${ctx}/wms/goods_touptGoods.emi?gid='+gid,
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
					      				  url: '${ctx}/wms/goods_uptGoods.emi',
					      				  success: function(req){
					      					  var jobj=eval("("+req+")");
					      					  if(jobj.success==1){
					      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/goods_getGoods.emi";});
					      					  }else{
					      						  $.dialog.alert_e(jobj.failInfor);
					      					  }
					      				  },
					      				  error:function(){
					      					  $.dialog.alert_e(jobj.failInfor);
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
			
		});
		
		/*弹出选择打印机信息弹出框*/
		function openprintpage(){
			
			var chek = document.getElementById("rtframe").contentDocument.getElementsByName("lyzj_chose");
			var total = 0;
			var gid = '';
			for (var i = 0; i < chek.length; i++){
				if (chek[i].checked) {
					total += 1;
					gid += chek[i].value+",";
				}
			}

			if (total < 1) {
				$.dialog.alert("请选择要打印的记录！");
				return false;
			}


			
			
		    $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'打印服务',
		    width: '300px',
			height: '200px',
			content: 'url:wms/print_toopenprintpage.emi',
			okVal:"打印",
			ok:function(){
				var printmodel = this.content.document.getElementById('printmodel').value;//打印模板
				var printservice = this.content.document.getElementById('printservice').value;//打印机
				var quantity = this.content.document.getElementById('quantity').value;//打印机
				
				$.ajax({
					url:'${ctx}/wms/print_printbarcode.emi?quantity='+quantity+'&printservice='+encodeURI(printservice)+"&printType=goodsprint&gid="+gid+"&printmodel="+encodeURI(printmodel),
					type:"post",
					data:$('#myform').serialize(),
					success:function(da){				
						if(da=='success'){
							alert("打印成功");
						}					
					}				
				});
			},
			cancelVal:"关闭",
			cancel:true
		});	
	}
		
		
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
		 		<!-- <li class="fl"><input type="button" id="del" class="delBtn" value="删除"> </li> -->
		 		<li class="fl"><input type="button" id="printBtn" class="printBtn" value="打印条码" onclick="openprintpage();"></li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div>

	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="tabletitle">物料设置</div>	
	 		<div class="xz_attribute"  >
	 			<div class="tree_div fl" style="width:17%;height:70%">
	 				<iframe src="${ctx}/wms/cuspro_getClassifyList.emi?classtype='03'"   id="" name="" frameborder="0" width="100%"  height="100%"></iframe>
	 			</div>
	 			
	 			<div class="fl" style="width:81%;height:70%;">
	 				<iframe src="${ctx }/wms/goods_getRightGoods.emi" frameborder="0" id="rtframe" name="rtframe" width="100%" height="100%"></iframe>
	 			</div>
	 			
	 			<div class="cf"></div>
	 			
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	 	 
	</div>


</body>
</html>