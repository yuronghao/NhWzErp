<!DOCTYPE HTML>
<%@page import="com.emi.sys.init.Config"%>
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
<script type="text/javascript" src="${ctx }/scripts/processDesign/basepd_attribute.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath %>/scripts/common/common.js"></script> --%>
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
 	var routecId = '${routecId}';
 	var freesetJson = [];//物料自由项
 	if('${freesetJson}'!=''){
 		freesetJson = eval('(${freesetJson})');
 	}
 	//选择领用物料
 	function fn_chooseGoods(){
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:'选择物料',
		    width: '1000px',
			height: '500px',
			zIndex:3000,
			content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GOODS%>',
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				// 添加行
				
				for(var i=0;i<returnArray.length;i++){
					if(!$('#g_'+returnArray[i].gid).val() || $('#g_'+returnArray[i].gid).val()==''){
						//调用table新增行方法
						initGoodsSelect(returnArray[i].gid,returnArray[i].goodscode,returnArray[i].goodsname,returnArray[i].goodsstandard,returnArray[i].unitName,$('#processName').val());
					
					}
				}
				//	重新排序号
				resetIndex('goodsIndex');
				 
			},
			cancelVal:"关闭",
			cancel:true
		});	
 	}
 	
 	//选择入库物料
 	function fn_chooseStockGoods(){
 		
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:'选择入库物料',
		    width: '1000px',
			height: '500px',
			zIndex:3000,
			content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GOODS%>',
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				if(returnArray.length>0){
					$('#stockGoodsId').val(returnArray[0].gid);
					$('#stockGoodsCode').val(returnArray[0].goodscode);
					$('#stockGoodsName').val(returnArray[0].goodsname);
				}
				 
			},
			cancelVal:"关闭",
			cancel:true
		});	
 	}
 	
 	//选择半成品
 	function fn_chooseSemiGoods(){
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:'选择半成品',
		    width: '1000px',
			height: '500px',
			zIndex:3000,
			content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GOODS%>',
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				if(returnArray.length>0){
					$('#semiGoodsId').val(returnArray[0].gid);
					$('#semiGoodsCode').val(returnArray[0].goodscode);
					$('#semiGoodsName').val(returnArray[0].goodsname);
				}
				 
			},
			cancelVal:"关闭",
			cancel:true
		});	
 	}
 	
 	//选择工作中心
 	function fn_chooseWorkCenter(){
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:'选择工作中心',
		    width: '800px',
			height: '500px',
			zIndex:3000,
			content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.WORKCENTER%>&multi=0&showTree=0', 
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				if(returnArray.length>0){
					$('#workCenterId').val(returnArray[0].gid);
					$('#workCenterName').val(returnArray[0].wcname);
				}else{
					$('#workCenterId').val('');
					$('#workCenterName').val('');
				}
			},
			cancelVal:"关闭",
			cancel:true
		});
 	}
 	//选择派工对象（人、班组）
 	function fn_chooseDispatching(){
 		var url = "";
 		var obj_type = $('input[name="dispatchingObjType"]:checked ').val();
 		var title_name;
 		if(obj_type=='0'){//人员
 			url = "${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PERSON%>&multi=1&showTree=1";
 			title_name = '选择派工人员';
 		}else if(obj_type=='1'){//组
 			url = "${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GROUP%>&multi=1&showTree=0";
 			title_name = '选择派工组';
		}
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:title_name,
		    width: '800px',
			height: '500px',
			zIndex:3000,
			content: 'url:'+url, 
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				for(var i=0;i<returnArray.length;i++){
					if(!$('#disObjId_'+returnArray[i].gid).val() || $('#disObjId_'+returnArray[i].gid).val()==''){
						//新增行
						var objcode=""; 
						var objname="";
						if(obj_type=='0'){//人员
							objcode = returnArray[i].percode;
							objname = returnArray[i].pername;
						}else if(obj_type=='1'){//组
							objcode = returnArray[i].code;
							objname = returnArray[i].groupname;
						}
						initDispatchingSelect(returnArray[i].gid,objcode,objname);
					}
				}
				//	重新排序号
				resetIndex('dispatchingIndex');
				
			},
			cancelVal:"关闭",
			cancel:true
		});
 	}
 	
 	//选择设备
 	function fn_chooseEquipment(){
 		var url = "${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.EQUIPMENT%>&multi=1&showTree=0";
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:"选择设备",
		    width: '800px',
			height: '500px',
			zIndex:3000,
			content: 'url:'+url, 
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				for(var i=0;i<returnArray.length;i++){
					if(!$('#equipmentId_'+returnArray[i].gid).val() || $('#equipmentId_'+returnArray[i].gid).val()==''){
						//新增行
						initEquipmentSelect(returnArray[i].gid,returnArray[i].equipmentcode,returnArray[i].equipmentname);
					}
				}
				//	重新排序号
				resetIndex('equipmentIndex');
				
			},
			cancelVal:"关闭",
			cancel:true
		});
 	}
 	
 	//选择模具
 	function fn_chooseMould(){
 		var url = "${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.MOULD%>&multi=1&showTree=1";
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:"选择模具",
		    width: '800px',
			height: '500px',
			zIndex:3000,
			content: 'url:'+url, 
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				for(var i=0;i<returnArray.length;i++){
					
					if(!$('#mouldId_'+returnArray[i].gid).val() || $('#mouldId_'+returnArray[i].gid).val()==''){
						//新增行
						initMouldSelect(returnArray[i].gid,returnArray[i].mouldcode,returnArray[i].mouldname,'','','',returnArray[i].mouldRatio);
					}
				}
				//	重新排序号
				resetIndex('mouldIndex');
				
			},
			cancelVal:"关闭",
			cancel:true
		});
 	}
 	
 	//选择人员
 	function fn_choosePerson(){
 		
 	}
 	
 	//选择不合格原因
 	function fn_chooseFailReason(){
 		
 	}
 	
 	//选择对应物料
 	function fn_chooseMatchGoods(){
 		
 	}
 	
 	//选择委外商
 	function fn_chooseOut(){
 		
 	}
 	
 	//选择部门
 	function fn_chooseDept(){
 		var selectedId = "CAA00C1B-7130-4604-8ED3-573A3EDFBDD2,7BD57065-A2A4-4EBB-8C52-1C4D621226B5";
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:'选择部门',
		    width: '400px',
			height: '500px',
			zIndex:3000,
			content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.DEPT%>&multi=1&showTree=1&showList=0&selectedId='+selectedId, 
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				if(returnArray.length>0){
					alert(JSON.stringify(returnArray));
				}
				
			},
			cancelVal:"关闭",
			cancel:true
		});	
 	}
 	
 	//选择工序
 	function fn_chooseProcess(){
 		$.dialog({ 
			drag: true,
			lock: false,
			resize: false,
			title:'选择工序',
		    width: '600px',
			height: '500px',
			zIndex:3000,
			content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PROCESS%>&multi=0&showTree=0', 
			okVal:"确定",
			ok:function(){
				var returnArray = this.content.window.jsonArray;
				if(returnArray.length>0){
					$('#processId').val(returnArray[0].gid);
					$('#processCode').val(returnArray[0].opcode);
					$('#processName').val(returnArray[0].opname);
					$('#isCheck').val(returnArray[0].isCheck);
					$('#passRate').val((returnArray[0].checkRate-0).toFixed(2));
					$('#isStock').val(returnArray[0].isStock);
					$('#standardPrice').val(returnArray[0].standardPrice);
					$('#realPrice').val(returnArray[0].standardPrice);
					$('#isMustScanMould  option[value="'+returnArray[0].isMustScanMould+'"]').prop("selected","selected");//工序说明;
					changeStockGoods();
				}
				
			},
			cancelVal:"关闭",
			cancel:true
		});	
 	}
 	
 /* 	//显示与隐藏入库物料选择
 	function changeStock(){
 		var isStock = $('#isStock').val();
 		if(isStock=='1'){//入库
 			$('.stockGoodsTr').show();
 		}else{//不入库
 			$('.stockGoodsTr').hide();
 		}
 	}
 	
 	//显示与隐藏半成品选择
 	function changeSemi(){
 		var isSemi = $('#isSemi').val();
 		if(isSemi=='1'){//入库
 			$('.semiGoodsTr').show();
 		}else{//不入库
 			$('.semiGoodsTr').hide();
 		}
 	} */
 	
 	function changeStockGoods(setValue){
 		var isStock = $('#isStock').val();
 		var isSemi = $('#isSemi').val();
 		if(isSemi=='1'){//半成品选择时，入库不可编辑
 			//$('#isStock').val('1');
 			$('#isStock').attr('readonly',true);
 			$('#isStock_td').hide();
 		}else{
 			$('#isStock').removeAttr('readonly');
 			$('#isStock_td').show();
 		}
 		
 		if(isSemi=='1' || isStock=='1'){//有入库或半成品时，出现物料信息
 			$('#stockInGoodsTr').show();
 			$('#stockGoodsId').val(the_productId);
 			$('#stockGoodsCode').val(the_productCode);
 			$('#stockGoodsName').val(the_productName);
 			$('#stockGoodsProcess').val($('#processName').val());
 		}else{
 			$('#stockInGoodsTr').hide();
 			$('#stockGoodsId').val('');
 			$('#stockGoodsCode').val('');
 			$('#stockGoodsName').val('');
 			$('#stockGoodsProcess').val('');
 		}
 	}
 	
 	$(function(){
 		$("#passRate").numeral(6);//合格率限制只能数字输入
 		$("#realPrice").numeral(6);//实际工价限制只能数字输入
 		$("#standardHours").numeral(6);//实际工价限制只能数字输入
 		$("#changeNumber").numeral(6);//改制的数量限制只能数字输入
 		$("#processIndex").numeral(0);//工序序号限制只能数字输入
 	
 		
 		//是改制订单，显示改制tab
 		if(changeOrder=='1'){
 			$('#changeOrderLi').show();
 		}
 		
 		
 		//派工对象的初始化，如果没有值则设置成默认
 		if(process_objs[routecId]['base']['disObjType'] ){
 			
 		}
 		var default_disObjType = '<%=Config.DEFAULT_DISPATCHING%>';
 		$('#dispatchingObjType_'+default_disObjType).attr('checked',true);
 		 /*
 		   * 初始化数据
 		   */
 		//if("${isQuery}"!="1"){//未经过数据库查询，则从页面获取值
 			 if(process_codeJson[routecId]){
 				 $('#processIndex').val(process_codeJson[routecId]);//序号 
 			 }
 			 if(process_objs[routecId]){
 				 var thisProcess = process_objs[routecId];
 				 /*基础数据*/
 				 $('#processIndex').val(thisProcess['base']['processIndex']);//序号 
 				 $('#processName').val(thisProcess['base']['processName']);//名称
 				 $('#processCode').val(thisProcess['base']['processCode']);//
 				 $('#processId').val(thisProcess['base']['standardProcessId']);//
 				 $('#isCheck').val(thisProcess['base']['isCheck']);//是否质检
 				 $('#isOut').val(thisProcess['base']['isOut']);//是否委外
 				 $('#isStock').val(thisProcess['base']['isStock']);//是否入库
 				 $('#isSemi').val(thisProcess['base']['isSemi']);//是否半成品
 				 $('#passRate').val(thisProcess['base']['passRate']==null?null:(thisProcess['base']['passRate']-0)*100);//检验合格率
 				 $('#workCenterId').val(thisProcess['base']['workCenterId'] || '');//工作中心id
 				 $('#workCenterName').val(thisProcess['base']['workCenterName'] || '');//工作中心名称
 				$('#stockGoodsId').val(thisProcess['base']['stockGoodsId'] || '');//入库物品id
 				$('#stockGoodsName').val(thisProcess['base']['stockGoodsName'] || '');//入库物品名称
 				$('#semiGoodsId').val(thisProcess['base']['semiGoodsId'] || '');//入库物品id
 				$('#semiGoodsName').val(thisProcess['base']['semiGoodsName'] || '');//入库物品名称
 				$('#standardPrice').val(thisProcess['base']['standardPrice'] || '0');//标准工价
 				$('#realPrice').val(thisProcess['base']['realPrice'] || '');//实际工价
 				$('#standardHours').val(thisProcess['base']['standardHours'] || '');//标准工时
 				$('#barcode').val(thisProcess['base']['barcode'] || '');//条码
 				$('#opdes').val(thisProcess['base']['opdes'] || '');//工序说明
 				$('#isMustScanMould  option[value="'+thisProcess['base']['isMustScanMould']+'"]').prop("selected","selected");//工序说明
 				$('#mouldControlFetch  option[value="'+thisProcess['base']['mouldControlFetch']+'"]').prop("selected","selected");//工序说明
 				
 				 if(thisProcess['base']['standardProcessId']==null || thisProcess['base']['standardProcessId']==''){
 					 //如果读取过来的节点没有选择工序，给出默认名字
 					 $('#processName').val(default_nodeName);//名称
 				 }
 				if($('#realPrice').val()==''){
 					//如果实际工价为空，默认标准工价
 					$('#realPrice').val($('#standardPrice').val());
 				}
 				/* if($('#isStock').val()=='1'){
 					$('.stockGoodsTr').show();
			 	}
 				if($('#isSemi').val()=='1'){
 					$('.semiGoodsTr').show();
			 	} */
 				changeStockGoods(true);	
 			 }
 		// }
 		//产品信息
 		$('#productId').val(the_productId);
 		$('#productCode').val(the_productCode);
 		$('#productName').val(the_productName);
 		$('#productStandard').val(the_productStandard);
 		$('#productUnit').val(the_productUnit);
 		
 		/*
 		*上道工序转入信息
 		*/
 		var proc_json = _canvas.getProcessJson();
 		//可视化插件解析的上道工序id
 		var canvas_pre_id = new Array();
 		for(procId in proc_json){
 			for(var n=0;n<proc_json[procId].process_to.length;n++){
 				var to_id = proc_json[procId].process_to[n];
 				if(to_id==routecId){
 					canvas_pre_id.push(procId);
 					break;
 				}
 			}
 		}
 		//json中的上道工序
 		var attr_pre = process_objs[routecId]['attrPreProc'] || [];
 		for(var i=0;i<canvas_pre_id.length;i++){
 			var hasJson = false;
 			for(var j=0;j<attr_pre.length;j++){
 				if(attr_pre[j]['routeCid']==canvas_pre_id[i]){
 					//原json中有值的，则读取
 					initPreTrHtml(canvas_pre_id[i],attr_pre[j]['baseUse'],attr_pre[j]['baseQuantity'] );
 					hasJson = true;
 					break;
 				}
 			}
 			if(!hasJson){
 				//原json没有值，则初始化
 				initPreTrHtml(canvas_pre_id[i],1,1);
 			}
 		}
 		
 		/* 读取json中的物料 */
 		if(process_objs[routecId]['attrGoods']){
 			for(var i=0;i<process_objs[routecId]['attrGoods'].length;i++){
 	 			var goods_obj = process_objs[routecId]['attrGoods'][i];
 	 			initGoodsSelect(goods_obj.goodsId, goods_obj.goodscode, goods_obj.goodsname, goods_obj.goodsstandard, goods_obj.unitName,goods_obj.free1, goods_obj.baseUse, goods_obj.baseQuantity, goods_obj.number);
 	 		}
 			resetIndex('goodsIndex');
 		}
 		
 		/* 读取json中的派工对象 */
 		$('input[name="dispatchingObjType"]').each(function(){
			if(this.value==(process_objs[routecId]['base']['dispatchingType']+"")){
				this.checked=true;
			}
 		});
 		if(process_objs[routecId]['attrDispatching']){
	 		for(var i=0;i<process_objs[routecId]['attrDispatching'].length;i++){
	 			var dis_obj = process_objs[routecId]['attrDispatching'][i];
	 			initDispatchingSelect(dis_obj.objId, dis_obj.objCode, dis_obj.objName);
	 		}
	 		resetIndex('dispatchingIndex');
 		}
 		
 		/* 读取json中的设备 */
 		if(process_objs[routecId]['attrEquipment']){
	 		for(var i=0;i<process_objs[routecId]['attrEquipment'].length;i++){
	 			var equ_obj = process_objs[routecId]['attrEquipment'][i];
	 			initEquipmentSelect(equ_obj.equipmentId, equ_obj.equipmentCode, equ_obj.equipmentName);
	 		}
	 		resetIndex('equipmentIndex');
 		}
 		
 		/* 读取json中的模具 */
 		if(process_objs[routecId]['attrMould']){
	 		for(var i=0;i<process_objs[routecId]['attrMould'].length;i++){
	 			var mould_obj = process_objs[routecId]['attrMould'][i];
	 			debugger;
	 			initMouldSelect(mould_obj.mouldId, mould_obj.mouldCode, mould_obj.mouldName,mould_obj.goodsCode,mould_obj.grossWeight,mould_obj.netWeight,mould_obj.mouldRatio);
	 		}
	 		resetIndex('mouldIndex');
 		}
 		
 		//添加物料自由项列
 		/* var goodsrows = document.getElementById("table_goods").rows;
 		var head_td = goodsrows[0].insertCell(5);
 		head_td.innerHTML="工序"; */
 	});
 	
 	
 </script>
<body>
	<!--  <input type="button" value="部门" onclick="fn_chooseDept()">  -->
	<input type="hidden" name="barcode" id="barcode" >
	<table style="margin-left: 25px">
		<tr>
		<td style="text-align: right;">成品编码：</td>
		<td><input type="text" readonly="readonly" name="productCode" id="productCode" style="width:150px" >
		<input type="hidden" name="productId" id="productId" >
		</td>
		<td style="padding-left: 25px;text-align: right;">成品名称：</td><td><input type="text" readonly="readonly" name="productName" id="productName" style="width:150px" ></td>
		<td style="text-align: right;">成品规格：</td><td><input type="text" readonly="readonly" name="productStandard" id="productStandard" style="width:150px" ></td>
		</tr>
		<tr>
		<td style="padding-left: 25px;text-align: right;">计量单位：</td><td><input type="text" readonly="readonly" name="productUnit" id="productUnit" style="width:150px"></td>
		<td style="padding-left: 25px;text-align: right;">工序编码：</td><td>
		<input type="hidden" name="processId" id="processId" >
		<input type="text"ame="processCode" id="processCode" style="width:150px" readonly="readonly" ><img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;" onclick="fn_chooseProcess()"></img></td>
		<td style="padding-left: 25px;text-align: right;">工序名称：</td><td><input type="text" name="processName" id="processName" style="width:150px" value="${standardProcess.opname }" readonly="readonly"></td>
		</tr>
		<tr>
		<td style="padding-left: 25px;text-align: right;">工序序号：</td><td><input type="text" name="processIndex" id="processIndex" style="width:150px" maxlength="6" value="${processCode }"></td>
		<td style="padding-left: 25px;text-align: right;">工作中心：</td>
		<td>
		<input type="text" name="workCenterName" id="workCenterName" style="width:150px" readonly="readonly" ><img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;" onclick="fn_chooseWorkCenter()"></img>
		<input type="hidden" name="workCenterId" id="workCenterId" value="">
		</td>
		<td style="padding-left: 25px;text-align: right;">工序说明：</td><td><input type="text" name="opdes" id="opdes" style="width:150px" value="${opdes }"></td>
		<!-- <td style="padding-left: 25px;text-align: right;">是否入库：</td>
		<td>
		<select name="isStock" id="isStock" style="width: 100px">
			<option value="0">否</option>
			<option value="1">是</option>
		</select>
		</td> -->
		</tr>
		<tr>
		<td style="padding-left: 25px;text-align: right;">必扫模具：</td>
		<td>
		<select name="isMustScanMould" id="isMustScanMould" style="width: 165px">
			<option value="0">否</option>
			<option value="1">是</option>
		</select>
		</td> 
		<td style="padding-left: 25px;text-align: right;">模具取数：</td>
		<td>
		<select name="mouldControlFetch" id="mouldControlFetch" style="width: 165px">
			<option value="0">否</option>
			<option value="1">是</option>
		</select>
		</td> 
		</tr>
		
	</table>
	
<ul class="nav nav-tabs" id="attributeTab">
  <li class="active"><a href="#attrPreProc">上道工序转入</a></li>
  <li><a href="#attrGoods">物料领用</a></li>
  <li><a href="#attrEquipment">设备</a></li>
  <li><a href="#attrMould">模具</a></li>
  <li><a href="#attrWorkcenter">派工对象</a></li> 
  <li><a href="#attrCheck">质检</a></li>
  <li><a href="#attrStock">入库/半成品</a></li>
  <li><a href="#attrCost">成本</a></li>
<!--   <li><a href="#attrRole">角色/人员</a></li>
  
  <li><a href="#attrAudit">审批</a></li>
  <li><a href="#attrRelease">放行</a></li>
  <li><a href="#attrOut">委外</a></li> -->
</ul>

<form class="form-horizontal" target="hiddeniframe" method="post" id="flow_attribute" name="flow_attribute" action="${ctx }/flow/flow_saveAttribute.emi">
<input type="hidden" name="route_id" value="${routec.routGid }"/>
<input type="hidden" name="process_id" value="${routec.gid }"/>
  <div class="tab-content">
    <div class="tab-pane active" id="attrPreProc">
		<table class="table table-bordered table-condensed" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="8%" style="text-align: center;">序号</th>
					<th width="30%" style="text-align: center;">工序编码</th>
					<th width="32%" style="text-align: center;">工序名称</th>
					<th width="15%" style="text-align: center;">基本用量</th>
					<th style="text-align: center;">基础数量</th>
					<!-- <th width="15%" style="text-align: center;">标准用量</th> -->
				</tr>
			</thead>
			<tbody id="tbody_preProc">
				<!-- <tr >
					<td  style="text-align: center;vertical-align: middle;">1
						<input type="hidden" name="preProcessId" value="1">
					</td>
					<td style="text-align: center;vertical-align: middle;">0010</td>
					<td style="text-align: center;vertical-align: middle;">工序1</td>
					<td style="vertical-align: middle;"><input type="text" name="baseUse_1" id="baseUse_1" style="width: 100px;height: 16px;text-align: center;"> </td>
					<td style="vertical-align: middle;"><input type="text" name="baseQuantity_1" id="baseQuantity_1" style="width: 100px;height: 16px;text-align: center;" value="1" readonly="readonly"></td>
					<td style="text-align: center;vertical-align: middle;">Default</td>
				</tr> -->
			</tbody>
		</table>
    </div><!-- attrPreProc end 上道工序转入-->


    <div class="tab-pane " id="attrGoods">
    	<button class="btn " onclick="fn_chooseGoods()" type="button" id="chooseGoods" >+&nbsp;添加物料</button>
		<table class="table table-bordered table-condensed" style="margin-top: 5px" contenteditable="false" id="table_goods">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="5%" style="text-align: center;">序号</th>
					<th style="text-align: center;">物料编码</th>
					<th style="text-align: center;">物料名称</th>
					<th style="text-align: center;">规格型号</th>
					<th style="text-align: center;">计量单位</th>
					<c:forEach var="fs" items="${freeset }">
						<th width="13%" style="text-align: center;">${fs.projectName }</th>
					</c:forEach>
					<%-- <c:if test="${isOrder=='1' }">
						<th style="text-align: center;">数量</th>
					</c:if>
					<c:if test="${isOrder!='1' }"> --%>
						<th width="13%" style="text-align: center;">子件用量</th>   <!-- 基本用量 -->
						<th width="13%" style="text-align: center;">母件用量</th>	  <!-- 基础数量 -->
					<%-- </c:if> --%>
					<th width="5%" style="text-align: center;">删除</th>
					<!-- <th style="text-align: center;">标准用量</th> -->
				</tr>
			</thead>
			<tbody id="goodsTable">
				<!-- <tr >
					<td name="goodsIndex" style="text-align: center;vertical-align: middle;">1
					</td>
					<td style="text-align: center;vertical-align: middle;">100010</td>
					<td style="text-align: center;vertical-align: middle;">主板</td>
					<td style="text-align: center;vertical-align: middle;">超微 X8DTL-3f-B</td>
					<td style="text-align: center;vertical-align: middle;">块</td>
					<td style="text-align: center;vertical-align: middle;"><input type="text" name="baseUse_g" id="baseUse_g_1" style="width: 100px;height: 16px"></td>
					<td style="text-align: center;vertical-align: middle;">1</td>
					<td style="text-align: center;vertical-align: middle;">Default</td>
				</tr> -->
			</tbody>
		</table>
    </div><!-- attrGoods end 物料领用-->

	<div class="tab-pane" id="attrEquipment">
		<button class="btn " onclick="fn_chooseEquipment()" type="button" id="chooseEquipment" >+&nbsp;添加设备</button>
		<table class="table table-bordered table-condensed" style="margin-top: 5px" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="8%" style="text-align: center;">序号</th>
					<th width="30%" style="text-align: center;">编码</th>
					<th style="text-align: center;">名称</th>
					<th width="5%" style="text-align: center;">删除</th>
				</tr>
			</thead>
			<tbody id="equipmentTable">
			
			</tbody>
		</table>
    </div><!-- attrEquipment end 设备-->
	    
	<div class="tab-pane" id="attrMould">
		<button class="btn " onclick="fn_chooseMould()" type="button" id="chooseMould" >+&nbsp;添加模具</button>
		<table class="table table-bordered table-condensed" style="margin-top: 5px" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="5%" style="text-align: center;">序号</th>
					<th width="15%" style="text-align: center;">编码</th>
					<th width="15%" style="text-align: center;">名称</th>
					<th width="15%" style="text-align: center;">模比</th>
					
					<th width="10%" style="text-align: center;">存货编码</th>
					<th width="10%" style="text-align: center;">毛重用量</th>
					<th width="10%" style="text-align: center;">净重用量</th>
					
					<th width="10%" style="text-align: center;">删除</th>
				</tr>
			</thead>
			<tbody id="mouldTable">
			
			</tbody>
		</table>
    </div><!-- attrMould end 模具-->

    <div class="tab-pane" id="attrWorkcenter">
		<button class="btn " onclick="fn_chooseDispatching()" type="button" id="chooseDispatching" style="float:left">+&nbsp;添加可派工对象</button>
		<p style="margin-left: 150px">
			类型：<input type="radio" name="dispatchingObjType" value="0" id="dispatchingObjType_0" checked="checked">人员  
				<input type="radio" name="dispatchingObjType" value="1" style="margin-left: 20px" id="dispatchingObjType_1">组
		</p>
		<table class="table table-bordered table-condensed" style="margin-top: 15px" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="8%" style="text-align: center;">序号</th>
					<th width="30%" style="text-align: center;">编码</th>
					<th style="text-align: center;">名称</th>
				<!-- 	<th width="20%" style="text-align: center;">所属部门编码</th>
					<th style="text-align: center;">所属部门名称</th> -->
					<th width="5%" style="text-align: center;">删除</th>
				</tr>
			</thead>
			<tbody id="dispatchingTable">
				<!-- <tr >
					<td  style="text-align: center;vertical-align: middle;" name="dispatchingIndex">1
						<input type="hidden" name="workcenterId">
					</td>
					<td style="text-align: center;vertical-align: middle;">001</td>
					<td style="text-align: center;vertical-align: middle;">组一</td>
					<td style="text-align: center;vertical-align: middle;">0001</td>
					<td style="text-align: center;vertical-align: middle;">生产部</td>
				</tr>
				 -->
			</tbody>
		</table>
    </div><!-- attrDispatching end 派工对象-->
    
    <div class="tab-pane" id="attrCheck">
    	<table >
    		<tr>
	    		<td>是否质检：</td>
	    		<td>
		    		<select name="isCheck" id="isCheck" style="width: 60px">
		    			<option value="1">是</option>
						<option value="0">否</option>
					</select>
	    		</td>
	    		<td style="padding-left: 20px">检验合格率：</td>
	    		<td>
		    		<input type="text" name="passRate" id="passRate" value="" style="width: 100px">%
	    		</td>
    		</tr>
    	</table>
    </div><!-- attrCheck end 质检-->
    
    <div class="tab-pane" id="attrStock">
    	<table >
    		<tr>
	    		<%-- <td style="width: 100px">是否入库：</td>
	    		<td>
		    		<select name="isStock" id="isStock" style="width: 100px" onchange="changeStock()">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
	    		</td>
	    		
	    		<td style="width: 100px">是否半成品：</td>
	    		<td>
		    		<select name="isSemi" id="isSemi" style="width: 100px" onchange="changeSemi()">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
	    		</td>
	    		
	    		<td class="stockGoodsTr" style="width: 150px;text-align: right;display: none">入库物料：</td>
	    		<td class="stockGoodsTr" style="display: none">
	    			<input type="hidden" name="stockGoodsId" id="stockGoodsId" value="" >
	    			<input type="hidden" name="stockGoodsCode" id="stockGoodsCode" value="" >
		    		<input type="text" name="stockGoodsName" id="stockGoodsName" value="" readonly="readonly" >
		    		<img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;" onclick="fn_chooseStockGoods()"></img>
	    		</td> --%>
	    		
	    		<td id="isStock_td">是否入库：<select name="isStock" id="isStock" style="width: 100px" onchange="changeStockGoods()">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
	    		
	    		<td id="isSemi_td">是否半成品：<select name="isSemi" id="isSemi" style="width: 100px" onchange="changeStockGoods()">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
    		</tr>
    		
    		<tr id="stockInGoodsTr" style="display: none">
	    		<td class="stockGoodsTr" style="">
	    			物料编码：<input type="text" name="stockGoodsCode" id="stockGoodsCode" value="" readonly="readonly">
	    		</td>
	    		<td class="stockGoodsTr" style="">
	    			<input type="hidden" name="stockGoodsId" id="stockGoodsId" value="" >
		    		&nbsp;&nbsp;&nbsp;&nbsp;物料名称：<input type="text" name="stockGoodsName" id="stockGoodsName" value="" readonly="readonly" >
		    		<img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;display: none" onclick="fn_chooseStockGoods()"></img>
	    		</td>
	    		<td>工序：<input type="text" name="stockGoodsProcess" id="stockGoodsProcess" value="" readonly="readonly" ></td>
    		</tr>
    		<%-- <tr>
	    		<td style="width: 100px">是否半成品：</td>
	    		<td>
		    		<select name="isSemi" id="isSemi" style="width: 100px" onchange="changeSemi()">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
	    		</td>
	    		
	    		<td class="semiGoodsTr" style="width: 150px;text-align: right;display: none">半成品：</td>
	    		<td class="semiGoodsTr" style="display: none">
	    			<input type="hidden" name="semiGoodsId" id="semiGoodsId" value="" >
	    			<input type="hidden" name="semiGoodsCode" id="semiGoodsCode" value="" >
		    		<input type="text" name="semiGoodsName" id="semiGoodsName" value="" readonly="readonly" >
		    		<img alt="" src="${ctx }/img/sousuo.png" style="cursor: pointer;" onclick="fn_chooseSemiGoods()"></img>
	    		</td>
    		</tr> --%>
    	</table>
    </div><!-- attrStock end 入库-->
    
    <!-- attrCost begin 成本-->
    <div class="tab-pane" id="attrCost">
    	<div>
	    	<span>标准工价：</span><input type="text" style="width: 100px" name="standardPrice" id="standardPrice" readonly="readonly">元
	    	<span style="margin-left: 50px">实际工价：</span><input type="text" style="width: 100px" name="realPrice" id="realPrice" value="">元
	    	<span style="margin-left: 50px">标准工时：</span><input type="text" style="width: 100px" name="standardHours" id="standardHours" value="">
		</div>
		<div style="margin-top: 10px">
			
		</div>
    </div>
     <!-- attrCost end 成本-->
    
    <div class="tab-pane" id="attrRole">
		<button class="btn " onclick="fn_choosePerson()" type="button" id="choosePerson">+&nbsp;角色/人员</button>
		<table class="table table-bordered table-condensed" style="margin-top: 5px" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="8%" style="text-align: center;">序号</th>
					<th width="30%" style="text-align: center;">人员编码</th>
					<th width="30%" style="text-align: center;">人员名称</th>
					<th style="text-align: center;">所属部门</th>
				</tr>
			</thead>
			<tbody>
				<tr >
					<td  style="text-align: center;vertical-align: middle;">1
					</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">01/04/2012</td>
					<td style="text-align: center;vertical-align: middle;"><input type="text" name="baseUse_1" id="baseUse_1" style="width: 100px;height: 16px"> </td>
				</tr>
				<tr>
					<td style="text-align: center;vertical-align: middle;">2
						<input type="hidden" name="" value="2">
					</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">01/04/2012</td>
					<td style="text-align: center;vertical-align: middle;"><input type="text" name="baseUse_2" id="baseUse_2" style="width: 100px;height: 16px"> </td>
				</tr> 
			</tbody>
		</table>
    </div><!-- attrRole end 角色人员-->
    
    
    <div class="tab-pane" id="attrAudit">

    </div><!-- attrAudit end 审批-->
    
    
    <div class="tab-pane" id="attrRelease">

    </div><!-- attrRelease end 放行-->
    
    
    <div class="tab-pane" id="attrOut">
		<div>
	    	<span>是否允许委外：</span><input type="radio" name="isAllowOut" style="margin-top: 0px;margin-left: 20px" value="1" checked="checked">是   <input type="radio" name="isAllowOut" style="margin-top: 0px;margin-left: 30px" value="0">否
			<span style="margin-left: 100px">是否委外：</span><input type="radio" name="isOut" style="margin-top: 0px;margin-left: 20px" value="1" checked="checked">是   <input type="radio" name="isOut" style="margin-top: 0px;margin-left: 30px" value="0">否
		</div>
		<div style="margin-top: 10px">
			<button class="btn " style="margin-left: 10px" onclick="fn_chooseOut()" type="button" id="chooseOut">+&nbsp;选择委外供应商</button>
			<table class="table table-bordered table-condensed" style="margin-top: 5px" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="8%" style="text-align: center;">序号</th>
					<th width="25%" style="text-align: center;">委外商编码</th>
					<th width="42%" style="text-align: center;">委外商名称</th>
					<th style="text-align: center;">委外商加工费单价</th>
				</tr>
			</thead>
			<tbody>
				<tr >
					<td  style="text-align: center;vertical-align: middle;">1
					</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
				</tr>
				<tr>
					<td style="text-align: center;vertical-align: middle;">2
						<input type="hidden" name="" value="2">
					</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
				</tr>
			</tbody>
		</table>
		</div>
    </div><!-- attrOut end 委外-->


  </div>

<div>
  <hr/>
  <span class="pull-right">
  <c:if test="${noEdit=='1' }">
  		<font color="red" id="noEditMsg">(该工序节点已有任务，不允许保存修改)</font>
  </c:if>
  
  <button class="btn btn-primary" onclick="uptPrice()" type="button" id="changeRealPrice">调整工价</button>
  
  <c:if test="${noEdit!='1' }">
  		<button class="btn btn-primary" onclick="onClik()" type="button" id="attributeOK">确定</button>
  </c:if>
     <a href="#" class="btn" id="btn_cancel" data-dismiss="modal" aria-hidden="true">取消</a>
      
  </span>
</div>

</form>
<iframe id="hiddeniframe" style="display: none;" name="hiddeniframe"></iframe>


    
<script type="text/javascript">
	
  /*var flow_id =  '4';//流程ID
  var process_id = '61';//步骤ID
  var get_con_url = "/index.php?s=/Flowdesign/get_con.html";//获取条件
  */
 
  $.fn.serializeObject = function()    
  {    
     var o = {};    
     var a = this.serializeArray();    
     $.each(a, function() {    
         if (o[this.name]) {    
             if (!o[this.name].push) {    
                 o[this.name] = [o[this.name]];    
             }    
             o[this.name].push(this.value || '');    
         } else {    
             o[this.name] = this.value || '';    
         }    
     });    
     return o;    
  };  
  
  		   
  function uptPrice(){
	  
	  $.ajax({
		  url:"${ctx}/wms/orderpd_changeRealPrice.emi",
		  type:"post",
		  data:{routeCid:routecId,realPrice:$('#realPrice').val()},
		  success:function(data){
			  if(data=='success'){
				  alert("保存成功");
			  }
			  
		  }
			
	  })
	  
  }
    
  function onClik(){  
          //var data = $("#form1").serializeArray(); //自动将form表单封装成json  
         var o = {};
         var jsonuserinfo = $('#flow_attribute').serializeObject(); 
        /*  var key = "999";
         o[key] = jsonuserinfo || '';    
         alert(JSON.stringify(jsonuserinfo));   */
         //o['routecId']=routecId;//工艺路线子表id
         //工序基础信息
         var base = {};
         if(process_objs[routecId]){
        	 base = process_objs[routecId]['base']; 
 		}
         //base['semiProdId'] = '';
         //base['productId'] = '';
         base['productId'] = the_productId;
         base['productCode'] = the_productCode;
         base['routeCid'] = routecId;
         
         base['processName'] = $('#processName').val();
         base['processCode'] = $('#processCode').val();
         base['processIndex'] = $('#processIndex').val();
         base['standardProcessId'] = $('#processId').val();
         base['isMustScanMould'] = $('#isMustScanMould').val();
         base['mouldControlFetch'] = $('#mouldControlFetch').val();
         base['isCheck'] = $('#isCheck').val();
         base['isOut'] = '0';
         base['isStock'] = $('#isStock').val();
         base['isSemi'] = $('#isSemi').val();
         base['dispatchingType'] = $('input[name="dispatchingObjType"]:checked ').val();
         base['passRate'] = $('#passRate').val()==''?'':((($('#passRate').val()-0)/100)+'');
         base['workCenterId'] = $('#workCenterId').val(); 
         base['workCenterName'] = $('#workCenterName').val();
         base['stockGoodsId'] = $('#stockGoodsId').val(); 
         base['stockGoodsName'] = $('#stockGoodsName').val();
         base['semiGoodsId'] = $('#semiGoodsId').val(); 
         base['semiGoodsName'] = $('#semiGoodsName').val();
         base['realPrice'] = $('#realPrice').val();
         base['standardHours'] = $('#standardHours').val();
         base['barcode'] = $('#barcode').val();
         base['opdes'] = $('#opdes').val();
         o['base'] = base;
         //上道工序转入[数组]
         var attrPreProc = new Array();
         var preId_info = new Array();
         if(jsonuserinfo['preProcessId']){
        	 if(isArray(jsonuserinfo['preProcessId'])){//值只有一个时，是个字符串而不是数组
        		 preId_info = jsonuserinfo['preProcessId'];
        	 }else{
        		 preId_info.push(jsonuserinfo['preProcessId']);
        	 }
        	 for(var i=0;i<preId_info.length;i++){
            	 var preObj = {};
            	 var preId = preId_info[i];
            	 preObj['routeCid'] = preId;
            	 preObj['baseUse'] = jsonuserinfo['baseUse_'+preId];
            	 preObj['baseQuantity'] = jsonuserinfo['baseQuantity_'+preId];
            	 preObj['number'] = jsonuserinfo['useNumber_'+preId];
            	// preObj['standardUse'] = preObj['baseUse']/preObj['baseQuantity'];
            	 attrPreProc.push(preObj);
             }
             o['attrPreProc'] = attrPreProc;
         }
        
         
         //物料领用[数组]
         var attrGoods = new Array();
         var goodsId_info = new Array();
         if(jsonuserinfo['goodsId']){
        	 if(isArray(jsonuserinfo['goodsId'])){//值只有一个时，是个字符串而不是数组
        		 goodsId_info = jsonuserinfo['goodsId'];
        	 }else{
        		 goodsId_info.push(jsonuserinfo['goodsId']);
        	 }
        	 for(var i=0;i<goodsId_info.length;i++){
            	 var goodsObj = {};
            	 var goodsId = goodsId_info[i];
            	 goodsObj['goodsId'] = goodsId;
            	 goodsObj['goodscode'] = jsonuserinfo['goodscode_'+goodsId];
            	 goodsObj['goodsname'] = jsonuserinfo['goodsname_'+goodsId];
            	 goodsObj['goodsstandard'] = jsonuserinfo['goodsstandard_'+goodsId];
            	 goodsObj['unitName'] = jsonuserinfo['unitName_'+goodsId];
            	 goodsObj['baseUse'] = jsonuserinfo['baseUse_g_'+goodsId];
            	 goodsObj['baseQuantity'] = jsonuserinfo['baseQuantity_g_'+goodsId];
            	 goodsObj['number'] = jsonuserinfo['useNumber_'+goodsId];
            	 goodsObj['free1'] = jsonuserinfo['free1_'+goodsId];
            	 //goodsObj['standardUse'] = goodsObj['baseUse']/goodsObj['baseQuantity'];
            	 attrGoods.push(goodsObj);
             }
             o['attrGoods'] = attrGoods;
         }
         
         //派工对象[数组]
         var attrDispatching = new Array();
         var disId_info = new Array();
         if(jsonuserinfo['disObjId']){
        	 
        	 if(isArray(jsonuserinfo['disObjId'])){//值只有一个时，是个字符串而不是数组
        		 disId_info = jsonuserinfo['disObjId'];
        	 }else{
        		 disId_info.push(jsonuserinfo['disObjId']);
        	 }
        	 for(var i=0;i<disId_info.length;i++){
            	 var disObj = {};
            	 var disId = disId_info[i];
            	 disObj['objId'] = disId;
            	 disObj['objCode'] = jsonuserinfo['disObjCode_'+disId];
            	 disObj['objName'] = jsonuserinfo['disObjName_'+disId];
            	 disObj['objType'] = $('input[name="dispatchingObjType"]:checked ').val();
            	 
            	 attrDispatching.push(disObj);
             }
             o['attrDispatching'] = attrDispatching;
         }
         
       //设备[数组]
         var attrEquipment = new Array();
         var equId_info = new Array();
         if(jsonuserinfo['equipmentId']){
        	 
        	 if(isArray(jsonuserinfo['equipmentId'])){//值只有一个时，是个字符串而不是数组
        		 equId_info = jsonuserinfo['equipmentId'];
        	 }else{
        		 equId_info.push(jsonuserinfo['equipmentId']);
        	 }
        	 for(var i=0;i<equId_info.length;i++){
            	 var equObj = {};
            	 var equId = equId_info[i];
            	 equObj['equipmentId'] = equId;
            	 equObj['equipmentCode'] = jsonuserinfo['equipmentCode_'+equId];
            	 equObj['equipmentName'] = jsonuserinfo['equipmentName_'+equId];
            	 
            	 attrEquipment.push(equObj);
             }
             o['attrEquipment'] = attrEquipment;
         }
         
       //模具[数组]
         var attrMould = new Array();
         var mouldId_info = new Array();
         if(jsonuserinfo['mouldId']){
        	 
        	 if(isArray(jsonuserinfo['mouldId'])){//值只有一个时，是个字符串而不是数组
        		 mouldId_info = jsonuserinfo['mouldId'];
        	 }else{
        		 mouldId_info.push(jsonuserinfo['mouldId']);
        	 }
        	 for(var i=0;i<mouldId_info.length;i++){
        		 
            	 var mouldObj = {};
            	 var mouldId = mouldId_info[i];
            	 mouldObj['mouldId'] = mouldId;
            	 mouldObj['mouldCode'] = jsonuserinfo['mouldCode_'+mouldId];
            	 mouldObj['mouldName'] = jsonuserinfo['mouldName_'+mouldId];
            	 
            	 mouldObj['goodsCode'] = jsonuserinfo['goodsCode_'+mouldId];
            	 mouldObj['grossWeight'] = jsonuserinfo['grossWeight_'+mouldId];
            	 mouldObj['netWeight'] = jsonuserinfo['netWeight_'+mouldId];
            	 
            	 attrMould.push(mouldObj);
             }
             o['attrMould'] = attrMould;
         }
         
         
         process_objs[routecId]=o;
         // 整体数据加入到数组
         //var o_e = false;//是否已存在
        
         /* for(i=0;i<process_objs.length;i++){
        	 if(process_objs[i]['routecId']==routecId){
        		 //存在则替换
        		 process_objs.splice(i,1,o);
        		 o_e = true;
        		 break;
        	 }
         }
         if(!o_e){process_objs.push(o); }  *///不存在则加入
         
         //工序编号更新
         process_codeJson[routecId] = $('#processIndex').val();
         push2updProcess(routecId,true);
         _canvas.updateProcessName(routecId, $('#processName').val() || '');
         _canvas.updateProcessCode(routecId, $('#processIndex').val() || '');
         
         //如果是变更单，判断该工序是否连着已开工的工序，且是否物料从无到有
         if(isAlter=='1'){
        	var processJson = _canvas.getProcessJson();//连接信息
			for(pcId in  processJson){
				var process_to = processJson[pcId]['process_to'];
				for(var i=0;i<process_to.length;i++){
					if(process_to[i]==routecId){
						//找到上道工序id
						//根据工序id检查是否已有任务在进行
				    	$.ajax({
							url:'${ctx}/wms/orderpd_checkProcessTask.emi?routeCid='+pcId,
							type:"post",
							dataType:'text',
							success:function(data){			
								if(data.indexOf('has')>=0){//有任务
									push2TaskProcess(data.split(':')[1]);
									push2TaskNextProcess(routecId);
								}
							}		
						 });
					}
				}
			}
         }
         
         
         document.getElementById('btn_cancel').click();
  } 
  var _out_condition_data = {"63":{"condition":"<option value=\"'data_1' = '1'  AND\">'\u6587\u672c\u6846' = '1'  AND<\/option><option value=\"( 'data_2' = '1' AND 'data_2' = '2' OR 'data_2' = '3' OR 'data_2' = '4' OR 'data_2' = '45')\">( '\u4e0b\u62c9\u83dc\u5355' = '1' AND '\u4e0b\u62c9\u83dc\u5355' = '2' OR '\u4e0b\u62c9\u83dc\u5355' = '3' OR '\u4e0b\u62c9\u83dc\u5355' = '4' OR '\u4e0b\u62c9\u83dc\u5355' = '45')<\/option>","condition_desc":"\u5feb\u6377\u5ba1\u6279\u6761\u4ef6\u4e0d\u6210\u7acb"},"64":{"condition":"<option value=\"'data_2' = '1'  AND\">'\u4e0b\u62c9\u83dc\u5355' = '1'  AND<\/option><option value=\"'data_2' = '2'\">'\u4e0b\u62c9\u83dc\u5355' = '2'<\/option>","condition_desc":"\u597d\u5427"}};
  
  </script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/flowdesign/attribute.js"></script>


</body>
</html>