<!DOCTYPE HTML>
<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%String contextPath = request.getContextPath();%> 
<c:set var="ctx" value="<%=contextPath %>"/>
<script type="text/javascript" src="<%=contextPath %>/scripts/lhgdialog/lhgdialog.js"></script>
<html>
 <head>
    
  <title>工序设计</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
 <style type="text/css">
 .mytd{
 	vertical-align: middle;
 }
 </style>
 </head>
 <script type="text/javascript">
 	//选择成品
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
				}
				
			},
			cancelVal:"关闭",
			cancel:true
		});	
 	}
 	function onClik(){  
 		the_productId = $('#productId').val();
		the_productCode = $('#productCode').val();
		the_productName = $('#productName').val();
		the_productStandard = $('#productStandard').val();
		the_productUnit = $('#productUnit').val();
		document.getElementById('btn_cancel').click();
	} 
 	$(function(){
 		$('#productId').val(the_productId);
 		$('#productCode').val(the_productCode);
 		$('#productName').val(the_productName);
 		$('#productStandard').val(the_productStandard);
 		$('#productUnit').val(the_productUnit);
 	});
 </script>
<body>
	<h4>产品信息</h4>
	<br>
	<input type="hidden" name="productId" id="productId" >
	<table style="margin-left: 25px">
		<tr>
		<td style="text-align: right;">成品编码：</td>
		<td><input type="text" readonly="readonly" name="productCode" id="productCode" style="width:150px" >
		<img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;" onclick="fn_chooseGoods()"></img>
		</td>
		<td style="padding-left: 25px;text-align: right;">成品名称：</td><td><input type="text" readonly="readonly" name="productName" id="productName" style="width:150px" ></td>
		</tr>
		<tr>
		<td style="text-align: right;">成品规格：</td><td><input type="text" readonly="readonly" name="productStandard" id="productStandard" style="width:150px" ></td>
		<td style="padding-left: 25px;text-align: right;">计量单位：</td><td><input type="text" readonly="readonly" name="productUnit" id="productUnit" style="width:150px"></td>
		</tr>
	</table>
	<br>

<div>
  <hr/>
  <span class="pull-right">
  	<button class="btn btn-primary" onclick="onClik()" type="button" id="attributeOK">确定</button>
     <a href="#" class="btn" id="btn_cancel" data-dismiss="modal" aria-hidden="true">取消</a>
      
  </span>
</div>


</form>
    
</body>
</html>