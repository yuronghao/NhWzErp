<!DOCTYPE html >
<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%><html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content=""/>
<meta name="keywords" content=""/>
<meta name="robots" content="index,follow" />
	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
</head>
<script type="text/javascript">

$(function() {

	var width = $('#createwords').width();
	$('#content').css('width', width + 10 + "px");

});

function fn_chooseGoods(){
		$.dialog({ 
		drag: true,
		lock: false,
		resize: true,
		title:'选择物料',
	    width: '1000px',
		height: '500px',
		zIndex:3000,
		content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GOODS%>',
		okVal:"确定",
		ok:function(){
			var returnArray = this.content.window.jsonArray;
			if(returnArray.length>0){
				var p = returnArray[0];
				$('#productId').val(p.gid);
				$('#productCode').val(p.goodscode);
				$('#productName').val(p.goodsname);
				$('#productStandard').val(p.goodsstandard);
				$('#productUnit').val(p.unitName);
				$('#routname').val('产品'+p.goodscode);
			}
			
		},
		cancelVal:"关闭",
		cancel:true
	});	
}
	
function saveRoute(){
	if(checkdata()){
		$.ajax({
			data: $("#myform").serialize(),
			type: 'POST',
			url: '${ctx}/wms/basepd_saveBaseRoute.emi',
			dataType:'text',
			success: function(req){
				if(req=='success'){
					$.dialog.alert_s('保存成功',function(){
						location.href="${ctx}/wms/basepd_toBaseRouteList.emi";
					});
					
				}else if(req=='error'){
					$.dialog.alert_e('保存失败');
				}
			},
			error:function(){
				$.dialog.alert_e('error');
			}
		});
	}
}

function doBack(){
	location.href='${ctx}/wms/basepd_toBaseRouteList.emi';
}
</script>
<body>
	<form action="${ctx }/wms/basepd_saveBaseRoute.emi" method="post"  class="myform" id="myform" onsubmit="return checkdata()">
		<input type="hidden" name="route.gid" id="gid" value="${route.gid }">
		<div class="EMonecontent">
		<div style="width: 100%;height: 15px;"></div>
		<!--按钮部分-->
	 	<div class="toolbar">
	 		<ul>
	 			<li class="fl"><input type="button" class="saveBtn" value="保存" onclick="saveRoute()"> </li>
	 			<li class="fl"><input type="button" class="backBtn" value="返回" onclick="doBack()"></li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="creattable">
	 		<div class="tabletitle"> <c:if test="${route.gid == null}">新增</c:if><c:if test="${route.gid != null}">修改</c:if> 标准工艺路线</div>		 		
	 		<div class="xz_attribute">
	 			<ul>
	 				<%-- <li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>编码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="route.routcode" id="routcode"  value="${route.routcode }"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li> --%>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>工艺路线名称：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="route.routname" id="routname"  value="${route.routname}"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>产品编码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="hidden" class="" style="width:260px;height:25px;line-height: 25px;" name="route.goodsUid" id="productId"  value="${route.goodsUid}"/>
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" name="productCode" id="productCode" readonly="readonly" value="${product.goodscode}"/>
	 						<img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;" onclick="fn_chooseGoods()"></img>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">产品名称：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="" style="width:260px;height:25px;line-height: 25px;" name="productName" id="productName" readonly="readonly" value="${product.goodsname}"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">产品规格：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="" style="width:260px;height:25px;line-height: 25px;" name="productStandard" id="productStandard" readonly="readonly" value="${product.goodsstandard}"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">产品单位：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" class="" style="width:260px;height:25px;line-height: 25px;" name="productUnit" id="productUnit" readonly="readonly" value="${product.unitName}"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">备注：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="route.routdes" id="routdes"  value="${route.routdes}"/>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 			</ul>
	 			<div style="height:10px;"></div>
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	</div>
		
	</form>
              
</body>
<script type="text/javascript">


function checkdata(){
	
	for(var i=0;i<2;i++){
	      if($('.inputxt').eq(i).val()==''){
	    	  $.dialog.alert('内容填写不完整');
	    	  return false;
	      }
	}
	return true;
}


</script>
</html>