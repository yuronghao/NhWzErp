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
<script type="text/javascript" src="${ctx}/scripts/emiwms.js"></script>

</head>

	<script type="text/javascript">
		function checkdata()
		{
			if(document.getElementById('code').value==""){
				$.dialog.alert_w("物料编码不能为空!");
			  	return false;
			}else if(document.getElementById('name').value==""){
				$.dialog.alert_w("物料名称不能为空!");
			  	return false;
			}else if(document.getElementById('istemp').value==0){
				if(document.getElementById('whuid').value==""){
					$.dialog.alert_w("仓库不能为空!");
					return false;
				}else{
					return true;
				}
			}
			else
			{ 
				return true;
			}
		}
		
		
		function init(){
			var inputToDeal=$(".toDeal");
			inputCannotUse(inputToDeal);
		}
		
		
		//选择仓库
		function selectWh(){
			
		    $.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'选择仓库',
			    width: '800px',
				height: '400px',
				zIndex:2000,
				content: 'url:${ctx}/wms/warehouse_getWarehouseHelp.emi',
				okVal:"确定",
				ok:function(){
					var id = this.content.document.getElementById("id").value;
					var name = this.content.document.getElementById("name").value;
					
					document.getElementById('whName').value=name;
					document.getElementById('whuid').value=id;
						
					
				},
				cancelVal:"关闭",
				cancel:true
			});	

		}
		
		
 		$(function(){
			$('#istemp').change(function(){
				if($(this).val()==1){
					$('#selectWareHouse').attr("style","display:none;");
				}else{
					$('#selectWareHouse').removeAttr("style");
				}
			});
		}) 
		
		
	</script>


<body onload="init();parent.giveUpBtn();">
	<form action="${ctx}/wms/goods_getRightGoods.emi" id="myform" method="post">
	
		<div class="EMonecontent" style="">
			<div style="width: 100%;height: 15px;"></div>
		</div>
		<!--主体部分-->
		<div class="EMonecontent" style="">
			<div style="width: 100%;height: 15px;"></div>

		 	<div class="xz_attribute">
				<div style="height:10px;"></div>

				<div class="fl" style="width: 76%;">
					<!--表格部分-->
				 	<div class="creattable">
				 		<div>
				 			<ul>
				 				<li>
				 					<div class="fl div_text">编码：</div>
				 					<div class="fl div_ipt">
				 						<input type="hidden" value="${aaGoodsallocation.gid }" id="gid" name="gid" class="toDeal"/>
				 						<input type="text" value="${aaGoodsallocation.code }" id="code" name="code" class="toDeal" />
				 					</div>
				 					<div class="cf"></div>
				 				</li>
				 				<li>
				 					<div class="fl div_text ">名称：</div>
				 					<div class="fl div_ipt">
				 						<input type="text" value="${aaGoodsallocation.name }" id="name" name="name" class="toDeal"/>
				 					</div>
				 					<div class="cf"></div>
				 				</li>
				 				<li>
				 					<div class="fl div_text">货位条码：</div>
				 					<div class="fl div_ipt">
				 						<input type="text" value="${aaGoodsallocation.allocationbarcode }" id="allocationbarcode" name="allocationbarcode" class="toDeal"/>
				 					</div>
				 					<div class="cf"></div>
				 				</li>
				 				<li>
				 					<div class="fl div_text">是否为临时货位：</div>
				 					<div class="fl div_ipt">
				 						<select style="width:220px;" id="istemp" name="istemp">
				 							<c:choose>
				 								<c:when test="${aaGoodsallocation.istemp==1 }">
				 									<option value="0">否</option>
				 									<option value="1" selected="selected">是</option>
				 								</c:when>
				 								<c:when test="${aaGoodsallocation.istemp==0 }">
				 									<option value="0" selected="selected">否</option>
				 									<option value="1" >是</option>
				 								</c:when>
				 								<c:otherwise>
				 									<option value="0" >否</option>
				 									<option value="1" >是</option>
				 								</c:otherwise>
				 							</c:choose>
				 			
				 						</select>
				 					</div>
				 					<div class="cf"></div>
				 				</li>
				 				<li>
				 					<div class="fl div_text">所属仓库：</div>
				 					<div class="fl div_ipt" style="position: relative;">
				 						<input type="hidden" value="${aaGoodsallocation.whuid }" id="whuid" name="whuid" class="toDeal" />
				 						<input type="text" value="${aaGoodsallocation.whName }" id="whName" name="whName" class="toDeal" />
				 						<img src="${ctx}/img/sousuo.png" onclick="selectWh();" id="selectWareHouse">
				 						<!--<input type="text" style="background-image: url(img/sousuo.png); background-repeat: no-repeat;background-position: 100%;;" value="" id=""  />-->
				 					</div>
				 					<div class="cf"></div>
				 				</li>
<!-- 				 				<li>
				 					<div class="fl div_text">启用日期：</div>
				 					<div class="fl div_ipt">
				 						<input type="text" value="" id=""  />
				 					</div>
				 					<div class="cf"></div>
				 				</li>
				 				<li>
				 					<div class="fl div_text">停用日期：</div>
				 					<div class="fl div_ipt">
				 						<input type="text" value="" id=""  />
				 					</div>
				 					<div class="cf"></div>
				 				</li> -->
				 				<li>
				 					<div class="fl div_text">备注：</div>
				 					<div class="fl div_ipt">
				 						<input type="text" value="${aaGoodsallocation.notes }" id="notes" name="notes" class="toDeal"/>
				 					</div>
				 					<div class="cf"></div>
				 				</li>
				 			</ul>
				 			<div style="height:10px;"></div>
				 		</div>		 		
				 	</div>
				 	
				</div>
				<div class="cf"></div>
				<div style="height:20px;"></div>
			</div>
		</div>
	</form>
</body>
</html>