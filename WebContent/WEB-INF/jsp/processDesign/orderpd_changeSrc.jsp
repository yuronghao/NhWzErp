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
<script type="text/javascript" src="<%=contextPath %>/scripts/plugins/jquery.numeral.js"></script>
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
 	var cleared = false;//是否已清除
 	//清空改制来源
 	function fn_clearChangeSrc(){
 		if(confirm('确定清空？')){
 			$.ajax({
 				url:'${ctx}/wms/orderpd_clearChangeSrc.emi?ordercId='+$('#orderCid').val()+'&routecId='+$('#routeCid').val()+'&thisRouteId='+$('#thisRouteId').val(),
 				type:"post",
 				dataType:'text',
 				success:function(data){			
 					if(data=='success'){
 						$('#changeOrderDiv').hide();
 						$('#chooseChangeSrc').show();
 						$('#clearChangeSrc').hide();
 						//清空json
 						changeSrcJson={};
 						cleared = true;
 						$('#orderCid').val('');
 						$('#routeCid').val('');
 					}else{
 						alert("清除失败!");
 					}
 				},
 				error:function(){
 					alert("error!");
 				}	
 			 });
 		}
 	}
 
	//选择改制来源
	function fn_chooseChangeSrc(){
		
		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:'选择改制来源',
		    width: '1000px',
			height: '700px',
			zIndex:3000,
			content: 'url:${ctx}/wms/orderpd_changeOrderSrc.emi', 
			okVal:"确定",
			ok:function(){
				//TODO 是否允许重新选择，重新选择时要删除原先关联数据
				var ordercId = this.content.window.rsp_orderCid;
				var routecId = this.content.window.document.getElementById('frame_detail').contentWindow.rsp_processId;
				if(ordercId!='' && routecId!=''){
					$('#src_loading').show();
					$.ajax({
						url:'${ctx}/wms/orderpd_getChangeOrderInfo.emi?ordercId='+ordercId+'&routecId='+routecId,
						type:"post",
						dataType:'json',
						success:function(data){			
							$('#src_loading').hide();
							if(data.success=='1'){
								setSrcValue(data);
							}
						}		
					 });
				}
			},
			cancelVal:"关闭",
			cancel:true
		});	
	}
	
	function setSrcValue(data){
		var orderc = data.orderc;
		var routec = data.routec;
		
		var canChange=routec.canChange==null || routec.canChange=='' ?0:routec.canChange;
		
		$('#changeOrderDiv').show();
		$('#co_billCode').html(orderc.billCode);
		$('#co_billDate').html(orderc.billDate.substring(0,10));
		$('#co_departmentName').html(orderc.departmentName);
		$('#co_personName').html(orderc.personName);
		$('#co_goodsCode').html(orderc.goodsCode);
		$('#co_goodsName').html(orderc.goodsName);
		$('#co_goodsStandard').html(orderc.goodsStandard);
		$('#co_number').html(orderc.number);
		$('#co_opCode').html(routec.opCode);
		$('#co_opName').html(routec.opName);
		$('#co_canChange').html(canChange);
		if(data.thisRoute && data.thisRoute.changeSrcNumber){
			//$('#thisRouteId').val(data.thisRoute.gid);
			$('#co_changeNumber').val(data.thisRoute.changeSrcNumber);
			$('#co_canChange').html((canChange-0)+(data.thisRoute.changeSrcNumber-0));//
		}else{
			$('#co_changeNumber').val(canChange);
		}
		
		
		$('#orderCid').val(orderc.gid);
		$('#routeCid').val(routec.gid);
	}
	
 	function onClik(){  
 		 if(($('#co_changeNumber').val()-0)>($('#co_canChange').html()-0)){
        	/*  $.dialog. */alert("'转出数量'不能大于'可转数量'");
        	 return;
         }
 		if(!cleared){
 			changeSrcJson['orderCid'] = $('#orderCid').val();
 	 		changeSrcJson['routeCid'] = $('#routeCid').val();
 	 		changeSrcJson['thisRouteId'] = $('#thisRouteId').val();
 	 		
 	 		changeSrcJson['billCode'] = $('#co_billCode').html();
 	 		changeSrcJson['billDate'] = $('#co_billDate').html();
 	 		changeSrcJson['departmentName'] = $('#co_departmentName').html();
 	 		changeSrcJson['personName'] = $('#co_personName').html();
 	 		changeSrcJson['goodsCode'] = $('#co_goodsCode').html();
 	 		changeSrcJson['goodsName'] = $('#co_goodsName').html();
 	 		changeSrcJson['goodsStandard'] = $('#co_goodsStandard').html();
 	 		changeSrcJson['number'] = $('#co_number').html();
 	 		changeSrcJson['opCode'] = $('#co_opCode').html();
 	 		changeSrcJson['opName'] = $('#co_opName').html();
 	 		changeSrcJson['canChange'] = $('#co_canChange').html();
 	 		changeSrcJson['changeNumber'] = $('#co_changeNumber').val();
 		}
 		
 		document.getElementById('btn_cancel').click();
 		
	}
 	$(function(){
 		$("#co_changeNumber").numeral(6);//转出数量
 		$('#src_loading').show();
 		$('#thisRouteId').val(the_flow_id);
 		//是否已有任务在进行
 		$.ajax({
			url:'${ctx}/wms/orderpd_checkDoingTaskByOrderc.emi?ordercId='+ordercId+'&type=0',
			type:"post",
			dataType:'text',
			success:function(da){				
				if(da=='1'){
					//有任务情况下，隐藏改制来源按钮
					$('#chooseChangeSrc').hide();
					$('#clearChangeSrc').hide();
				}	
			}
		});
 		debugger;
 		if(changeSrcJson['billCode'] && changeSrcJson['billCode']!=''){
 			//如果已选过，则直接显示
 			$('#changeOrderDiv').show();
			$('#co_billCode').html(changeSrcJson['billCode']);
			$('#co_billDate').html(changeSrcJson['billDate']);
			$('#co_departmentName').html(changeSrcJson['departmentName']);
			$('#co_personName').html(changeSrcJson['personName']);
			$('#co_goodsCode').html(changeSrcJson['goodsCode']);
			$('#co_goodsName').html(changeSrcJson['goodsName']);
			$('#co_goodsStandard').html(changeSrcJson['goodsStandard']);
			$('#co_number').html(changeSrcJson['number']);
			$('#co_opCode').html(changeSrcJson['opCode']);
			$('#co_opName').html(changeSrcJson['opName']);
			$('#co_canChange').html((changeSrcJson['canChange']-0));//+(changeSrcJson['changeNumber']-0)
			$('#co_changeNumber').val(changeSrcJson['changeNumber']);
			
			$('#orderCid').val(changeSrcJson['orderCid']);
			$('#routeCid').val(changeSrcJson['routeCid']);
			
			$('#chooseChangeSrc').hide();
			$('#clearChangeSrc').show();
			$('#src_loading').hide();
 		}else if(changeSrcJson['orderCid'] && changeSrcJson['orderCid']!='' && changeSrcJson['routeCid'] && changeSrcJson['routeCid']!=''){
 			$.ajax({
				url:'${ctx}/wms/orderpd_getChangeOrderInfo.emi?ordercId='+changeSrcJson['orderCid']+'&routecId='+changeSrcJson['routeCid']+'&thisOrdercId='+ordercId,
				type:"post",
				dataType:'json',
				success:function(data){			
					$('#src_loading').hide();
					if(data.success=='1'){
						//隐藏选择按钮
						$('#chooseChangeSrc').hide();
						$('#clearChangeSrc').show();
						setSrcValue(data);
					}else{
						$('#src_loading').hide();
						$('#chooseChangeSrc').show();
					}
				}		
			 });
 		}else{
 			$('#src_loading').hide();
 			$('#chooseChangeSrc').show();
			$('#clearChangeSrc').hide();
 		}
 		
 	});
 </script>
<body>
	<h4>改制来源</h4>
	<input type="hidden" name="orderCid" id="orderCid" >
	<input type="hidden" name="routeCid" id="routeCid" >
	<input type="hidden" name="thisRouteId" id="thisRouteId" >
	<!-- attrCost begin 改制-->
    	<button class="btn " style="display: none" onclick="fn_chooseChangeSrc()" type="button" id="chooseChangeSrc">+&nbsp;选择改制来源</button>
    	<button class="btn " style="display: none" onclick="fn_clearChangeSrc()" type="button" id="clearChangeSrc">×&nbsp;清空改制来源</button>
    	<!-- loading -->
    	<div id="src_loading" style="margin-top: 50px;margin-bottom: 50px;width: 100%;text-align: center;display: none"><img src="${ctx }/img/loading.gif" alt=""></img></div>
    	<div id="changeOrderDiv" style="display: none">
    		<table>
    			<tr style="height: 50px">
    				<td style="">订单编号：</td><td id="co_billCode"></td>
    				<td style="padding-left: 20px">订单日期：</td><td id="co_billDate"></td>
    				<td style="padding-left: 20px">部门：</td><td id="co_departmentName"></td>
    				<td style="padding-left: 20px">负责人：</td><td id="co_personName"></td>
    			</tr>
    			<tr style="height: 50px">
    				<td>产品编码：</td><td id="co_goodsCode"></td>
    				<td style="padding-left: 20px">产品名称：</td><td id="co_goodsName"></td>
    				<td style="padding-left: 20px">产品规格：</td><td id="co_goodsStandard"></td>
    				<td style="padding-left: 20px">生产数量：</td><td id="co_number"></td>
    			</tr>
    			<tr style="height: 50px">
    				<td>工序编码：</td><td id="co_opCode"></td>
    				<td style="padding-left: 20px">工序名称：</td><td id="co_opName"></td>
    				<td style="padding-left: 20px">可转数量：</td><td id="co_canChange"></td>
    				<td style="padding-left: 20px">转出数量：</td><td><input type="text" id="co_changeNumber" name="co_changeNumber" style="width: 100px"></td>
    			</tr>
    		</table>
    	</div>
     <!-- attrCost end 改制-->
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