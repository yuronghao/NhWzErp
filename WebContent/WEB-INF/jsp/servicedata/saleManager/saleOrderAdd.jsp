<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>销售订单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script  type="text/javascript" src="${ctx}/scripts/LodopFuncs.js"></script>
<script type="text/javascript"	src="${ctx }/scripts/emiwms.js"></script> 
<script type="text/javascript" src="<%=contextPath %>/scripts/plugins/jquery.numeral.js"></script>
<script type="text/javascript">
		function checkForm()
		{
			if(document.getElementById('billDate').value==""){
				$.dialog.alert_w("单据日期不能为空!");
			  	return false;
			}
			 else if(document.getElementById('depName').value==""){
				$.dialog.alert_w("部门不能为空!");
			  	return false;
			}
			 else if(document.getElementById('customerName').value==""){
					$.dialog.alert_w("客户不能为空!");
				  	return false;
				}
			var trs=$('.serialTr');
			debugger;
			for(var i=0;i<trs.length;i++){
				if($('.numric').eq(i).val()==''){
			    	  $.dialog.alert('主数量不能为空！');
			    	  return false;
			      }
			      else if(($('.cassComUnitNam').eq(i).val()=='')&&($('.assistNumbe').eq(i).val()!='')){
			    	  $.dialog.alert('辅计量为空，请不要填写辅数量！');
			    	  return false;
			      }
			      else if($('.localTaxPric').eq(i).val()==''){
			    	  $.dialog.alert('本币含税单价不能为空！');
			    	  return false;
			      }
			      else if($('.localTaxMone').eq(i).val()==''){
			    	  $.dialog.alert('本币含税金额不能为空！');
			    	  return false;
			      }
			      else if(($('.cassComUnitNam').eq(i).val()!='')&&($('.assistNumbe').eq(i).val()=='')){
			    	  $.dialog.alert('辅数量不能为空！');
			    	  return false;
			      }
			}
			return true;
		}
		
		//按钮事件
		$(function(){
			var toDeal=$('.toDealInput');
			inputCannotUse(toDeal);
			var isSave=true;//新增保存、修改保存标识
			//新增 
			$('#addBtn').click(function(){					
				$('.wordul').children().children().children('input').val("");//清理文本框内容
				$('tr').not($('.NO')).remove();//表格内容全清
				$('.dwrow').remove();
				$('.addrow').show();
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				$("select").removeAttr("disabled");
				$("select option").removeAttr("selected");
				$(".addrow").attr("onclick","insertRow()");
				$("#depName").attr("onclick","selectdep()");
				$("#customerName").attr("onclick","selectcustomer()");
				$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				$.ajax({
					data:{billType:0130},
					url:'${ctx}/wms/saleOrder_getBillId.emi',
					type:"post",
					dataType:"json",
					success:function(da){					
						$('#billDate').val(da.nowDate);
						$('#recordDate').val(da.nowDate);
						$('#billCode').val(da.billId);
						$('#recordPersonName').val(da.recordname);
						$('#recordPersonUid').val(da.gRecordPersonUid);
					}				
				});
				addBtn();

			});
			//修改
			$('#revBtn').click(function(){	
				var saleOrdergid = $('#saleOrdergid').val();
				if($('#billState').val()==0){
					$.ajax({
						url : '${ctx}/wms/saleOrder_checksaleOrder.emi',
						type : 'post',
						data : {gid : saleOrdergid},
						success : function(data) {	
								isSave=false;	
								getSerial();
								var toDeal=$('.toDealInput');
								inputCanUse(toDeal);	
								$('.dwrow').remove();
								$('.delrow').show();
								$('.addrow').show();
								$("select").removeAttr("disabled");
								$("select option").removeAttr("selected");
								$(".delrow").addClass("delno");
								$(".addrow").attr("onclick","insertRow()");
								$("#depName").attr("onclick","selectdep()");
								$("#customerName").attr("onclick","selectcustomer()");
								$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
								revBtn();
						},
						error : function() {
							alert("服务器异常");
						}
					});
				}else{
					alert("请先弃审此订单再删除！");
				}
				
			});	
			//保存
			$('#saveBtn').click(function(){		
				if(checkForm()){
				if(isSave){//新增保存			
					$.ajax({
						url:'${ctx}/wms/saleOrder_addsaleOrder.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){				
							if(da=='success'){
								window.location.href = '${ctx}/wms/saleOrder_toAddsaleOrder.emi';
							}					
						}				
					});
				}else{//修改保存			
					$.ajax({
						url:'${ctx}/wms/saleOrder_updatesaleOrder.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){					
							if(da=='success'){
								window.location.href = '${ctx}/wms/saleOrder_toAddsaleOrder.emi';
							}					
						}				
					});			
				}
				}
				
			});
			
			//检测是否已有工艺路线
			$.ajax({
				url:'${ctx}/wms/orderpd_checksaleRoute.emi?orderId=${saleOrder.WMsaleOrdergid}',
				type:"post",
				data:$('#myform').serialize(),
				dataType:'text',
				success:function(req){			
					if(req=='success'){
						$('#delRouteLi').show();
					}					
				}				
			});	
		});
		
		//删除订单
		function deletesob(gid) {
			if (confirm("是否确定删除?")) {
				if($('#billState').val()==0){
					$.ajax({
						url : '${ctx}/wms/saleOrder_deletesaleOrder.emi',
						type : 'post',
						data : {gid : gid},
						success : function(data) {	
							if (data == "success") {
								window.location.href = "${ctx}/wms/saleOrder_toAddsaleOrder.emi";
							} else {
								alert("此订单下存在订单工艺路线");
							}
						},
						error : function() {
							alert("服务器异常");
						}
					});
				}else{
					alert("请先弃审此订单再删除！");
				}
			}
		}

		//关闭
		function stopaudit(gid) {
			if (confirm("是否要关闭?")) {
				$.ajax({
							url : '${ctx}/wms/saleOrder_stopsaleOrder.emi',
							type : 'post',
							data : {gid : gid},
							success : function(data) {	
								if (data == "success") {
									window.location.href = "${ctx}/wms/saleOrder_toAddsaleOrder.emi";
								} else {
									alert("关闭失败");
								}
							},
							error : function() {
								alert("服务器异常");
							}
						});
			}
		}
		
		//已审
		function audit(gid) {
			if (confirm("是否要审核?")) {
				$.ajax({
							url : '${ctx}/wms/saleOrder_auditsaleOrder.emi',
							type : 'post',
							data : {gid : gid},
							success : function(data) {	
								if (data == "success") {
									window.location.href = "${ctx}/wms/saleOrder_toAddsaleOrder.emi";
								} else {
									alert("审核失败");
								}
							},
							error : function() {
								alert("服务器异常");
							}
						});
			}
		}
		
		//未审
		function disaudit(gid) {
			if (confirm("是否要弃审?")) {
				$.ajax({
							url : '${ctx}/wms/saleOrder_disauditsaleOrder.emi',
							type : 'post',
							data : {gid : gid},
							success : function(data) {	
								if (data == "success") {
									window.location.href = "${ctx}/wms/saleOrder_toAddsaleOrder.emi";
								} else {
									alert("弃审失败");
								}
							},
							error : function() {
								alert("服务器异常");
							}
						});
			}
		}
		
		
		//放弃
		function giveup() {
			location.href = '${ctx}/wms/saleOrder_toAddsaleOrder.emi';
		}
		
		function selectorg(){
			    $.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'选择组织',
			    width: '800px',
				height: '400px',
				zIndex:2000,
				content: 'url:${ctx}/wms/sob_orgSelect.emi',
				okVal:"确定",
				ok:function(){
					var id = this.content.document.getElementById("id").value;
					var name = this.content.document.getElementById("name").value;
					document.getElementById('orgName').value=name;
					document.getElementById('orgId').value=id;
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}
		
		function getprocurearrivallist(){
			window.location.href = '${ctx}/wms/saleOrder_saleOrderList.emi';
		}
		
		function getSerial(){
			
			var trs=$('.serialTr');
			 for(var i=0;i<trs.length;i++){
				trs.eq(i).children('.goodsUid').children().attr('id','goodsUid'+(i+1));
				trs.eq(i).children('.goodsCode').children().attr('id','goodsCode'+(i+1));
				trs.eq(i).children('.goodsName').children().attr('id','goodsName'+(i+1));
				trs.eq(i).children('.goodsstandard').children().attr('id','goodsstandard'+(i+1));
				trs.eq(i).children('.number').children().attr('id','number'+(i+1));
				trs.eq(i).children('.unitName').children().attr('id','unitName'+(i+1));
				trs.eq(i).children('.assistNumber').children().attr('id','assistNumber'+(i+1));
				trs.eq(i).children('.cassComUnitName').children().attr('id','cassComUnitName'+(i+1));
				trs.eq(i).children('.localTaxPrice').children().attr('id','localTaxPrice'+(i+1));
				trs.eq(i).children('.localTaxMoney').children().attr('id','localTaxMoney'+(i+1));
				trs.eq(i).children('.note').children().attr('id','note'+(i+1));
			} 
		}
		
		function insertRow(){
		    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'物料档案',
			    width: '1100px',
				height: '590px',
				content: 'url:${ctx}/wms/goods_getGoodsHelp.emi',
				okVal:"确定",
				ok:function(){
					var chek = $('.goodsSelected:checked',this.content.document.getElementById("rtframe").contentDocument); 
					for (var i = 0; i < chek.length; i++) {
						var strs='<tr class="serialTr">'+
						'<td><div class="delrow delno" name = "deleteButton" value=""  style="margin-left:15px;"></div></td>'+
						'<input type="hidden" id="" name="gid" class="listword" value="">'+
						'<td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="'+chek.eq(i).val()+'"></td>'+
						'<td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="'+chek.eq(i).attr("goodsCode")+'" readonly="readonly"></td>'+
						'<td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="'+chek.eq(i).attr("goodsName")+'" readonly="readonly"></td>'+
						'<td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="'+chek.eq(i).attr("goodsstandard")+'" readonly="readonly"></td>'+
						'<td class="number"><input type="text" id="" name="number" class="listword numric" value=""></td>'+
						'<td class="unitName"><input type="text" id="" name="unitName" class="listword" value="'+chek.eq(i).attr("unitName")+'" readonly="readonly"></td>'+
		                '<td class="assistNumber"><input type="text" id="" name="assistNumber" class="listword toDealInput assistNumbe" value=""></td>'+
		                '<td class="cassComUnitName"><input type="text" id="" name="cassComUnitName" class="listword cassComUnitNam" value="'+chek.eq(i).attr("cstComUnitName")+'" readonly="readonly"></td>'+
		                '<td class="localTaxPrice"><input type="text" id="" name="localTaxPrice" class="listword localTaxPric" value="0.00" onchange="settaxmoney(this)"></td>'+
		                '<td class="localTaxMoney"><input type="text" id="" name="localTaxMoney" class="listword localTaxMone" value="0.00"></td>'+
		                '<td class="note"><input type="text" id="" name="note" class="listword" value=""></td>'+
						'</tr>';
						$("#contr").append(strs);
						getSerial();
						$(".numric").numeral(6);
					}
				},
				cancelVal:"关闭",
				cancel:true
				
				});
			
		}
		$(function(){
			$('body').on('click','.delno',function(){
				/* var obj=$(this);
				$.dialog.confirm("确定是否删除？",function(){
					obj.parent().parent().remove();},function(){}); */
				var deleteGids = $('#deleteGids').val();
				var obj=$(this);
				$.dialog.confirm("确定是否删除？",function(){
					var delId = obj.parent().next().val();
					if(delId !=null && delId!=''){
						deleteGids+="'"+delId+"',";
					}
					$('#deleteGids').val(deleteGids);	
					obj.parent().parent().remove();},function(){});
			});
			
			$(".numric").numeral(6);
		});
		
		function printbarcode(){
			$.ajax({
				url:'${ctx}/wms/saleOrder_printbarcode.emi',
				type:"post",
				data:$('#myform').serialize(),
				success:function(da){				
					if(da=='success'){
						alert("打印成功");
					}					
				}				
			});
		}
		
		//生成订单工艺路线
		function gensaleRoute(){
			showTips('数据处理中...');
			//检测是否已有进行的任务
			$.ajax({
				url:'${ctx}/wms/orderpd_checkDoingTask.emi?orderId=${saleOrder.WMsaleOrdergid}&type=0',
				type:"post",
				dataType:'text',
				success:function(da){				
					if(da=='1'){
						$.dialog.alert('任务正在进行，无法再生成订单工艺路线！');
						hideTips();
					}else if(da=='0'){
						$.ajax({
							url:'${ctx}/wms/orderpd_gensaleRoute.emi?orderId=${saleOrder.WMsaleOrdergid}',
							type:"post",
							//data:$('#myform').serialize(),
							dataType:'text',
							success:function(da){				
								if(da=='success'){
									$.dialog.alert_s('生成成功！');
									$('#delRouteLi').show();
								}else{
									$.dialog.alert_e('生成失败！');
								}	
								hideTips();
							}				
						});
					} else{
						$.dialog.alert_e('服务异常！');
						hideTips();
					}		
				}				
			});
			
			
		}
		
		//重新设计工艺路线
		function designsaleRoute(orderId,orderCid,number,goodsId){
			//var routeId = checkSelectId('userCheck');
			//if(routeId!=''){
				var maxWidth = window.screen.width-40;//document.body.clientWidth;
				var maxHeight = window.screen.height-200;//document.body.clientHeight;
				$.dialog({ 
					drag: true,
					lock: false,
					resize: true,
					title:'订单工艺路线设计',
				    width: maxWidth+'px',
					height: maxHeight+'px',
					zIndex:2000,
					content: 'url:${ctx}/wms/orderpd_toDesignOrderRoutePage.emi?orderId='+orderId+'&orderCid='+orderCid+'&number='+number+'&goodsId='+goodsId,
					okVal:"关闭",
					ok:function(){
						//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
					},
					//cancelVal:"关闭",
					//cancel:true
				});	
			//}
		}
		
		//派发任务
		function startTask(){
			showTips('数据处理中...');
			//1、检测生成的工艺路线是否有必填但未填的（如 ：实际工价、）
			$.ajax({
				url:'${ctx}/wms/orderpd_checkData.emi?orderId=${saleOrder.WMsaleOrdergid}',
				type:"post",
				//data:$('#myform').serialize(),
				dataType:'text',
				success:function(da){				
					if(da=='success'){
						//2、检测是否已有进行的任务
						$.ajax({
							url:'${ctx}/wms/orderpd_checkDoingTask.emi?orderId=${saleOrder.WMsaleOrdergid}&type=1',
							type:"post",
							dataType:'text',
							success:function(da){				
								if(da=='1'){
									$.dialog.alert('任务正在进行，无法再派发任务！');
									hideTips();
								}else if(da=='0'){
									$.ajax({
										url:'${ctx}/wms/orderpd_startTask.emi?orderId=${saleOrder.WMsaleOrdergid}',
										type:"post",
										//data:$('#myform').serialize(),
										dataType:'text',
										success:function(da){				
											if(da=='success'){
												$.dialog.alert_s('成功！');
											}else{
												$.dialog.alert_e('失败！');
											}	
											hideTips();
										}				
									});
								} else{
									$.dialog.alert_e('服务异常！');
									hideTips();
								}		
							}				
						});
					}else{
						$.dialog.alert(da);
						hideTips();
					}	
				}				
			});
			
		}
		
		//打开生产详情
		function showProductStep(orderId,orderCid,number,goodsName){
			//var routeId = checkSelectId('userCheck');
			//if(routeId!=''){
				var maxWidth = window.screen.width-40;//document.body.clientWidth;
				var maxHeight = window.screen.height-200;//document.body.clientHeight;
				$.dialog({ 
					drag: true,
					lock: false,
					resize: true,
					title:'生产详情【'+goodsName+'】',
				    width: maxWidth+'px',
					height: maxHeight+'px',
					zIndex:2000,
					content: 'url:${ctx}/wms/orderpd_showProductStep.emi?orderId='+orderId+'&orderCid='+orderCid+'&number='+number,
					okVal:"关闭",
					ok:function(){
						//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
					},
					//cancelVal:"关闭",
					//cancel:true
				});	
			//}
		}
		
		function selectdep(){
			var pwdWin = $.dialog({ 
				drag: true,
				lock: true,
				resize: false,
				title:'选择部门',
			    width: '400px',
				height: '400px',
				zIndex:3004,
				content: 'url:${ctx}/wms/basepd_getselectdep.emi',
				okVal:"确定",
				ok:function(){
					var name = this.content.document.getElementById("name").value;
					var id = this.content.document.getElementById("id").value;
					document.getElementById('depName').value=name;
					document.getElementById('depUid').value=id;
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}
		
		//选择工作中心
	 	function selectcustomer(){
	 		$.dialog({ 
				drag: true,
				lock: false,
				resize: false,
				title:'选择客户',
			    width: '800px',
				height: '500px',
				zIndex:3000,
				content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PROVIDERCUSTOMER%>&multi=0&showTree=0', 
				okVal:"确定",
				ok:function(){
					var returnArray = this.content.window.jsonArray;
					if(returnArray.length>0){
						$('#customerUid').val(returnArray[0].gid);
						$('#customerName').val(returnArray[0].pcname);
					}
				},
				cancelVal:"关闭",
				cancel:true
			});
	 	}
		
		function selectmanager(){
			var pwdWin = $.dialog({ 
				drag: true,
				lock: true,
				resize: false,
				title:'选择负责人',
			    width: '800px',
				height: '400px',
				zIndex:3004,
				content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PERSON%>&multi=0&showTree=0',
				okVal:"确定",
				ok:function(){
					var usercheck = this.content.window.jsonArray;
					document.getElementById('managerName').value=usercheck[0].pername;
					document.getElementById('managerGid').value=usercheck[0].gid;
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}
		
		//删除订单工艺路线
		function deletesaleRoute(){
			$.dialog.confirm('是否确定删除？',function(){
				showTips('数据处理中...');
				//检测是否已有进行的任务
				$.ajax({
					url:'${ctx}/wms/orderpd_checkDoingTask.emi?orderId=${saleOrder.WMsaleOrdergid}&type=0',
					type:"post",
					dataType:'text',
					success:function(da){				
						if(da=='1'){
							$.dialog.alert('任务正在进行，无法删除订单工艺路线！');
							hideTips();
						}else if(da=='0'){
							$.ajax({
								url:'${ctx}/wms/orderpd_deleteOrderRoute.emi?orderId=${saleOrder.WMsaleOrdergid}',
								type:"post",
								//data:$('#myform').serialize(),
								dataType:'text',
								success:function(da){				
									if(da=='success'){
										$.dialog.alert('删除成功！');
									}else{
										$.dialog.alert_e('删除失败！');
									}
									hideTips();
								}				
							});
						} else{
							$.dialog.alert_e('服务异常！');
							hideTips();
						}		
					}				
				});
				
			});
			
		}
		
		function settaxmoney(val){
			var obj=$(val);
			var taxprice = obj.val();
			var amount = obj.parent().parent().find(".number").children().val();
			obj.parent().parent().find(".localTaxMone").val(taxprice*amount);
		}
		function prn1_preview() {	
			CreateOneFormPage();	
			LODOP.PREVIEW();
		};
		function CreateOneFormPage(){
			LODOP=getLodop();  
			//LODOP.PRINT_INIT("");
			LODOP.SET_PRINT_STYLE("FontSize",14);
			LODOP.SET_PRINT_STYLE("Bold",1);
			//LODOP.ADD_PRINT_TEXT(50,231,260,39,"打印页面部分内容");
			var css1 = '<link href="${ctx }/style2/css/style.css" rel="stylesheet" type="text/css" />' ;
			
			//LODOP.ADD_PRINT_HTM(0,0,"100%","100%",css1+'<body>'+document.getElementById("printdiv").innerHTML+'</body>');
			//LODOP.ADD_PRINT_HTM(88,200,350,600,document.getElementById("printdiv").innerHTML);
			
			LODOP.ADD_PRINT_TABLE(128,"5%","90%",314,css1+document.getElementById("printcontent").innerHTML);
			LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
			LODOP.ADD_PRINT_HTM(26,"5%","90%",109,document.getElementById("printhead").innerHTML);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
			LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
		    LODOP.ADD_PRINT_HTM(444,"5%","90%",54,document.getElementById("printfoot").innerHTML);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
			LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	

		};
	</script>
</head>
<body style="background-color: #FFFFFF;">
<form id="myform">
<input type="hidden" id="saleOrdergid" name="saleOrdergid" value="${saleOrder['WMsaleOrdergid']}">
<input type="hidden" name="deleteGids" id="deleteGids" />
<input type="hidden" name="billState" id="billState" value="${saleOrder['billState']}"/>
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<li class="fl"><input type="button" class="btns" value="新增" id="addBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="修改" id="revBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="删除" id="delBtn" onclick="deletesob('${saleOrder['WMsaleOrdergid']}')"> </li>
		 			<li class="fl"><input type="button" class="btns" value="保存" id="saveBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>
		 			<li class="fl"><input type="button" class="btns" value="审核" onclick="audit('${saleOrder['WMsaleOrdergid']}')"> </li>
			 		<li class="fl"><input type="button" class="btns" value="弃审" onclick="disaudit('${saleOrder['WMsaleOrdergid']}')"> </li>
			 		<li class="fl"><input type="button" class="btns" value="关闭" onclick="stopaudit('${saleOrder['WMsaleOrdergid']}')"> </li>
		 			<li class="fl"><input type="button" class="btns" value="列表" id="tableBtn" onclick="getprocurearrivallist()"></li>
		 			<li class="fl">
	 				<!-- 单据翻页begin -->
		 			<div id="emi_page_turning" ></div>
		 			<script>
		 				$(function() {
		 					/*
							 * 初始化单据翻页 
							 * @param action 表单请求地址，不需要在此url中传gid，如有其他参数，可以传
							 * @param table_name 表名
							 * @param id_column gid字段名(一般是gid不用改)
							 * @param gid	当前数据的gid值
							 * @param condition	数据的过滤条件sql
							 * @param [el_id_column] 【非必填】后台接取gid使用的参数名(默认叫gid，如有需要可以修改)
							 * @param [div_id] 【非必填】翻页按钮所在的div的id(默认叫emi_page_turning,如有需要可以修改)
							 */
							 var cond = "";
		 					initPageTurning('${ctx }/wms/saleOrder_toAddsaleOrder.emi','WM_saleOrder','gid',"${saleOrder['WMsaleOrdergid']}",
		 							cond,'saleOrdergid');
		 				});
		 			</script>
		 			<!-- 单据翻页end -->
	 			</li>
	 			<!-- <li class="fl"><input type="button" class="btns" value="定位"> </li>-->
				<li class="fl"><input type="button" class="btns" value="打印" onclick="prn1_preview()"></li> 
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle" style="margin-top:6px;">销售订单</div>		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">单据编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${saleOrder['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(saleOrder['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl">
							<input type="text" name="depName" id="depName" class="toDealInput" value="${department.depname}">
	 						<input type="hidden" id="depUid" name="depUid" value="${saleOrder['deptGid']}">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							 <div class="wordname fl">客户：</div>
							<div class="wordnameinput fl"><input type="text" id="customerName" name="customerName" class="toDealInput" value="${aaProviderCustomer.pcname}">
							<input type="hidden" id="customerUid" name="customerUid" value="${saleOrder['customerUid']}"> </div>
							<div class="cf"></div> 
		 				</li>
		 			</ul>
		 			<div class="cf"></div>
		 			<ul class="wordul">
		 			<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl"><input type="text" value="${saleOrder['salenotes']}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">单据状态：</div>
							<div class="wordnameinput fl">
							<c:if test="${saleOrder['billState']==0}"><font style="color:blue">未审核</font></c:if>
							</div>
							<div class="wordnameinput fl">
							<c:if test="${saleOrder['billState']==1}"><font style="color:green">已审核</font></c:if>
							</div>
							<div class="wordnameinput fl">
							<c:if test="${saleOrder['billState']==2}"><font style="color:red">停用</font></c:if>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<%--<li class="wordli fl">
							<div class="wordname fl">仓库：</div>
							<div class="wordnameinput fl"><input type="text" value="${saleOrder['transportation']}" id="transportation" name="transportation" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">供应商：</div>
							<div class="wordnameinput fl"><input type="text" value="${saleOrder['pcName']}" id="supplierUid" name="supplierUid" placeholder="单击选择" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">业务员：</div>
							<div class="wordnameinput fl"><input type="text" value="${saleOrder['perName']}" id="perName" name="perName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li> --%>
		 				
		 			</ul>
		 			<!-- <ul class="wordul">
		 				
		 				<div class="cf"></div> 
		 			</ul> -->
		 			<div style="height: 30px;"></div>
		 			<!--end-->
		 			<div style="max-height:300px;overflow:auto;">
			 			<table>
				 			<tbody id="contr">
				 				<tr class="NO">
				 					<th style="width:50px;">操作</th>
				 					<th>物料编号</th>
				 					<th>物料名称</th>
				 					<th>规格型号</th>
				 					<th>数量</th>
				 					<th>单位</th>
				 					<th>辅数量</th>
				 					<th>辅单位</th>
				 					<th>本币含税单价</th>
				 					<th>本币含税金额</th>
				 					<th>备注</th>
				 				</tr>
				 			<c:forEach var="type" items="${saleOrderc}" varStatus="stat">
				 		    <tr class="serialTr">
				 		    <td><div class="delrow" name = "deleteButton" value=""  style="margin-left:15px;float: left;display: none" title="删除"></div></td>
				 		    <input type="hidden" id="" name="gid" class="listword" value="${type.gid}">
				 		    <td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="${type.goodsuid}"></td>
			                <td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="${type.good.goodscode}" readonly="readonly" ></td>
			                <td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="${type.good.goodsname}" readonly="readonly"></td>
			                <td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="${type.good.goodsstandard}" readonly="readonly"></td>
			                <td class="number"><input type="text" id="" name="number" class="listword toDealInput numric" value="<fmt:formatNumber type="number" value="${type.number}" minFractionDigits="2" groupingUsed="false"/>"></td>
			                <td class="unitName"><input type="text" id="" name="unitName" class="listword" value="${type.good.unitName}" readonly="readonly"></td>
			                <td class="assistNumber"><input type="text" id="" name="assistNumber" class="listword toDealInput assistNumbe" value="<fmt:formatNumber type="number" value="${type.assistNumber}" minFractionDigits="2" groupingUsed="false"/>"></td>
			                <td class="cassComUnitName"><input type="text" id="" name="cassComUnitName" class="listword cassComUnitNam" value="${type.good.cassComUnitName}" readonly="readonly"></td>
			                <td class="localTaxPrice"><input type="text" id="" name="localTaxPrice" class="listword toDealInput localTaxPric" value="<fmt:formatNumber type="number" value="${type.localTaxPrice}" minFractionDigits="2" groupingUsed="false"/>" onchange="settaxmoney(this)"></td>
			                <td class="localTaxMoney"><input type="text" id="" name="localTaxMoney" class="listword toDealInput localTaxMone" value="<fmt:formatNumber type="number" value="${type.localTaxMoney}" minFractionDigits="2" groupingUsed="false"/>"></td>
			                <td class="note"><input type="text" id="" name="note" class="listword toDealInput" value="${type.notes}" readonly="readonly"></td>
				 		    </tr>
				 	        </c:forEach>
				 			</tbody>
				 		</table>
				 		<!-- 添加按钮 -->
				 		<div class="addrow fl" style="margin-left:35px;margin-top:-23px;display: none">
					</div>
			 		</div>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		 	<ul class="wordul fr" style="width: 80%;">
 				<li class="wordli fl">
					<div class="wordname fl">录入人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${saleOrder['recordpersonName']}" id="recordPersonName" name="recordPersonName" readonly="readonly">
					<input type="hidden" id="recordPersonUid" name="recordPersonUid" value="${saleOrder['recordPersonUid']}">
					</div>
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">录入日期：</div>
					<div class="wordnameinput fl"><input type="text" value="${fn:substring(saleOrder['recordDate'],0,10)}" id="recordDate" name="recordDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li>
 				<%-- <li class="wordli fl">
					<div class="wordname fl">审核人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${saleOrder['auditpersonName']}" id="auditpersonName" name="auditpersonName" readonly="readonly"> </div>
					<input type="hidden" id="auditPersonUid" name="auditPersonUid" value="${saleOrder['auditPersonUid']}">
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">审核日期：</div>
					<div class="wordnameinput fl"><input type="text" <c:if test="${saleOrder['auditDate']!=null}">value="${fn:substring(saleOrder['auditDate'],0,10)}"</c:if> id="auditDate" name="auditDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li> --%>
 				<div class="cf"></div> 
 			</ul>
		 	<div class="cf"></div> 
		</div>
				<div  id="printdiv" style="display:none;">
			<div id="printhead" >
		 		<div class="tabletitle" style="margin-top:6px;;text-align:center;">销售订单
		 		</div>
		 		<div style="float:right" class="creatbarcode"></div>
		 		<div style="clear:both;"></div>			 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%;">
							<div class="wordname fl" style="float:left">单据编号：</div>
							<div class="wordnameinput fl" style="float:left;"><input type="text" style="border:none;" value="${saleOrder['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%">
							<div class="wordname fl" style="float:left;">单据日期：</div>
							<div class="wordnameinput fl" style="float:left;"><input type="text" style="border:none;" value="${fn:substring(saleOrder['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%">
							<div class="wordname fl" style="float:left">部门：</div>
							<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${department.depname}" id="perName" name="perName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%">
							<div class="wordname fl" style="float:left;text-align:right">客户：</div>
								<div class="wordnameinput fl" style="float:left">
									<input type="text" id="customerName" name="customerName" class="toDealInput" value="${aaProviderCustomer.pcname}" style="border:0">
									<input type="hidden" id="customerUid" name="customerUid" value="${saleOrder['customerUid']}"> 									
								</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="list-style:none;float:left;width:32%">
							<div class="wordname fl" style="float:left">备注：</div>
							<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${saleOrder['salenotes']}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 
		 			</ul>		 		
		 			<div style="height: 30px;"></div>
		 		</div>
		 	</div>
		 			<!--end-->
 			<div style="max-height:300px;overflow:auto;" id="printcontent">
	 			<table cellpadding="0" cellspacing="0" style="border:1px #ccc solid;margin-left:20px;width:95%;">
		 			<thead>
		 				<tr class="NO" style="border:1px #ccc solid;height:35px;line-height:35px;background-color:#e2eaf0;">
		 					<th class="printth" style="border-right:1px #ccc solid;">物料编号</th>
		 					<th class="printth" style="border-right:1px #ccc solid;">物料名称</th>
		 					<th class="printth" style="border-right:1px #ccc solid;">规格型号</th>
		 					<th class="printth" style="border-right:1px #ccc solid;">数量</th>
		 					<th class="printth" style="border-right:1px #ccc solid;">单位</th>
		 					<th class="printth" style="border-right:1px #ccc solid;">辅数量</th>
		 					<th class="printth" style="border-right:1px #ccc solid;">辅单位</th>
		 					<th class="printth" style="border-right:1px #ccc solid;">本币含税单价</th>
		 					<th style="width: 14%;border-right:1px #ccc solid;">本币含税金额</th>
		 				</tr>
		 			</thead>
		 			<tbody id="contr">
		 			<c:forEach var="type" items="${saleOrderc}" varStatus="stat">
		 		    <tr class="serialTr" style="border:1px #ccc solid;">
		 		    
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="updateTime">
	               		<input type="text" style="border:none;text-align:center" id="" name="updateTime" class="listword" value="${type.good.goodscode}" readonly="readonly">
	                </td>
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="barcode">
	                	<div class="creatbarcode" style="border:none;text-align:center"> ${type.good.goodsname}</div>
	                </td>
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center">${type.good.goodsstandard}</div>
	                </td>
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center"><fmt:formatNumber type="number" value="${type.number}" minFractionDigits="2" groupingUsed="false"/></div>
	                </td>
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center">${type.good.unitName}</div>
	                </td>
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center"><fmt:formatNumber type="number" value="${type.assistNumber}" minFractionDigits="2" groupingUsed="false"/></div>
	                </td>	
	                <td class="assistUnitcode" style="display:none"><input type="text" id="" name="assistUnitcode" class="listword jnumric" value="${type.good.cstcomunitcode}" readonly="readonly"></td>
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center">${type.good.cassComUnitName}</div>
	                </td>
	               
	                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center">$<fmt:formatNumber type="number" value="${type.localTaxPrice}" minFractionDigits="2" groupingUsed="false"/></div>
	                </td>
	                <td style="border-top:1px #ccc solid;border-right:none;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center"><fmt:formatNumber type="number" value="${type.localTaxMoney}" minFractionDigits="2" groupingUsed="false"/></div>
	                </td>
	               <%--  <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;height:55px;" class="opname">
	                	<div class="creatbarcode" style="border:none;text-align:center"><fmt:formatNumber type="number" value="${type.assistNumber}" minFractionDigits="2" groupingUsed="false"/></div>
	                </td> --%>
		 		    </tr>
		 	        </c:forEach>
		 			</tbody>
		 		</table>
		 		<div class="addrow fl" style="margin-left:50px;margin-top:-23px;">
				</div>
	 		</div>	 			
	 		<div style="margin-top:4%;" id="printfoot">
				<ul class="wordul">
		 			<li class="wordli fl" style="list-style:none;float:left;width:30%">
						<div class="wordname fl" style="float:left;text-align:right">出库人：</div>
						<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${saleOrder['recordpersonName']}" id="notes" name="notes" class="toDealInput"> </div>
						<div class="cf"></div> 
	 				</li>
	 				<li class="wordli fl" style="list-style:none;float:left;width:32%">
						<div class="wordname fl" style="float:left">出库日期：</div>
						<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${fn:substring(saleOrder['recordDate'],0,10)}" id="notes" name="notes" class="toDealInput"> </div>
						<div class="cf"></div> 
	 				</li>
	 				<div class="cf"></div> 
	 			</ul>
			</div> 		
			</div>
		</form>
	</body>
</html>