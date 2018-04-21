<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>生产订单</title>
<style type="text/css">
	/* input[readonly]{  
	    background-color: #efefef;  
	}  */
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript"	src="${ctx }/scripts/emiwms.js"></script> 
<script type="text/javascript" src="<%=contextPath %>/scripts/plugins/jquery.numeral.js"></script>
<script type="text/javascript">
		function checkForm()
		{
			if(document.getElementById('billDate').value==""){
				$.dialog.alert_w("单据日期不能为空!");
			  	return false;
			}
			/* else if(document.getElementById('depName').value==""){
				$.dialog.alert_w("部门不能为空!");
			  	return false;
			} */
			var trs=$('.serialTr');
			for(var i=0;i<trs.length;i++){
			      if($('.numric').eq(i).val()==''){
			    	  $.dialog.alert('内容填写不完整');
			    	  return false;
			      }
			      else if($('.startDay').eq(i).val()==''){
			    	  $.dialog.alert('开工日期不能为空！');
			    	  return false;
			      }
			      else if($('.endDay').eq(i).val()==''){
			    	  $.dialog.alert('完工日期不能为空！');
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
				$('.freeset').remove();
				$('.dwrow').remove();
				$('.addrow').show();
				$('#genRoute').hide();
				$('#genTask').hide();
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				$("select").removeAttr("disabled");
				$("select option").removeAttr("selected");
				$(".addrow").attr("onclick","insertRow()");
				$("#depName").attr("onclick","selectdep()");
				$("#managerName").attr("onclick","selectmanager()");
				$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				$.ajax({
					data:{billType:'0140'},
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
				var produceOrdergid = $('#produceOrdergid').val();
				$.ajax({
					url : '${ctx}/wms/produceOrder_checkproduceOrder.emi',
					type : 'post',
					data : {gid : produceOrdergid},
					success : function(data) {	
						if (data == "success") {
							isSave=false;	
							getSerial();
							var toDeal=$('.toDealInput');
							inputCanUse(toDeal);	
							$('.freeset').remove();
							$('.dwrow').remove();
							$('.delrow').show();
							$('.addrow').show();
							$('#genRoute').hide();
							$('#genTask').hide();
							$("select").removeAttr("disabled");
							$("select option").removeAttr("selected");
							$(".delrow").addClass("delno");
							$(".addrow").attr("onclick","insertRow()");
							$("#depName").attr("onclick","selectdep()");
							$("#managerName").attr("onclick","selectmanager()");
							$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
							$('.startDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
							$('.endDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
							revBtn();
						} else {
							/* alert("此订单下存在订单工艺路线,您不能修改此订单"); */
							isSave=false;	
							getSerial();
							var toDeal=$('.numric');
							inputCanUse(toDeal);	
							$('.freeset').remove();
							$('.dwrow').remove();
							revBtn();
						}
					},
					error : function() {
						  $.dialog.alert_e("服务器异常");
					}
				});
			});	
			//保存
			$('#saveBtn').click(function(){		
				if(checkForm()){
				if(isSave){//新增保存			
					$.ajax({
						url:'${ctx}/wms/produceOrder_addproduceOrder.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){				
							if(da=='success'){
								window.location.href = '${ctx}/wms/produceOrder_toAddproduceOrder.emi?changeOrder=${changeOrder}';
							}					
						}				
					});
				}else{//修改保存			
					$.ajax({
						url:'${ctx}/wms/produceOrder_updateproduceOrder.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){					
							if(da=='success'){
								window.location.href = '${ctx}/wms/produceOrder_toAddproduceOrder.emi?changeOrder=${changeOrder}';
							}					
						}				
					});			
				}
				}
				
			});
			
			//检测是否已有工艺路线
			$.ajax({
				url:'${ctx}/wms/orderpd_checkProduceRoute.emi?orderId=${produceOrder.WMProduceOrdergid}',
				type:"post",
				data:$('#myform').serialize(),
				dataType:'text',
				success:function(req){			
					if(req=='success'){
						$('#delRouteLi').show();
						$('#genRoute').hide();
						$('#genTask').show();
					}else{
						$('#delRouteLi').hide();
						$('#genRoute').show();
						$('#genTask').hide();
					}		
				}				
			});	
		});
		
		//删除订单
		function deletesob(gid) {
			if (confirm("是否确定删除?")) {
				$.ajax({
							url : '${ctx}/wms/produceOrder_deleteproduceOrder.emi',
							type : 'post',
							data : {gid : gid},
							success : function(data) {	
								if (data == "success") {
									window.location.href = "${ctx}/wms/produceOrder_toAddproduceOrder.emi?changeOrder=${changeOrder}";
								} else {
									$.dialog.alert("此订单下存在订单工艺路线");
								}
							},
							error : function() {
								$.dialog.alert_e("服务器异常");
							}
						});
			}
		}
		
		//放弃
		function giveup() {
			location.href = '${ctx}/wms/produceOrder_toAddproduceOrder.emi?changeOrder=${changeOrder}';
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
			window.location.href = '${ctx}/wms/produceOrder_produceOrderList.emi?changeOrder=${changeOrder}';
		}
		
		function getSerial(){
			
			var trs=$('.serialTr');
			 for(var i=0;i<trs.length;i++){
				//trs.eq(i).children().first().children().val(i+1);
				//trs.eq(i).children('.trid').children().val(i+1);
				trs.eq(i).children('.goodsUid').children().attr('id','goodsUid'+(i+1));
				trs.eq(i).children('.goodsCode').children().attr('id','goodsCode'+(i+1));
				trs.eq(i).children('.goodsName').children().attr('id','goodsName'+(i+1));
				trs.eq(i).children('.goodsstandard').children().attr('id','goodsstandard'+(i+1));
				trs.eq(i).children('.number').children().attr('id','number'+(i+1));
				trs.eq(i).children('.turnoutNum').children().attr('id','turnoutNum'+(i+1));
				trs.eq(i).children('.completedNum').children().attr('id','completedNum'+(i+1));
				trs.eq(i).children('.startDate').children().attr('id','startDate'+(i+1));
				trs.eq(i).children('.endDate').children().attr('id','endDate'+(i+1));
				trs.eq(i).children('.nowsum').children().attr('id','nowsum'+(i+1));
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
						var strs='<tr class="serialTr">';
							strs += '<td><div class="delrow delno" name = "deleteButton" value=""  style="margin-left:15px;"></div></td>';
							strs += '<input type="hidden" id="" name="gid" class="listword" value="">';
							strs += '<td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="'+chek.eq(i).val()+'"></td>';
							strs += '<td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="'+chek.eq(i).attr("goodsCode")+'" readonly="readonly"></td>';
							strs += '<td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="'+chek.eq(i).attr("goodsName")+'" readonly="readonly"></td>';
							strs += '<td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="'+chek.eq(i).attr("goodsstandard")+'" readonly="readonly"></td>';
							strs += '<td class="number"><input type="text" id="" name="number" class="listword numric" value=""></td>';
							strs += '<td class="turnoutNum"';
							if('${changeOrder}'=='1'){//改制
								strs += ' style="display:none"';
							}
							strs += '><input type="text" id="" name="turnoutNum" class="listword" value="" readonly="readonly"></td>';
							strs += '<td class="completedNum"><input type="text" id="" name="completedNum" class="listword" value="" readonly="readonly"></td>';
							strs += '<td class="startDate"><input type="text" id="" name="startDate" class="listword startDay" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" readonly="readonly"></td>';
							strs += '<td class="endDate"><input type="text" id="" name="endDate" class="listword endDay" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" readonly="readonly"></td>';
							strs += '<td class="nowsum"><input type="text" id="" name="nowsum" class="listword" value="'+(chek.eq(i).attr("nowsum")-0).toFixed(2)+'" readonly="readonly"></td>';
							strs += '<td class="note"><input type="text" id="" name="note" class="listword" value=""></td>';
							strs += '</tr>';
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
				url:'${ctx}/wms/produceOrder_printbarcode.emi',
				type:"post",
				data:$('#myform').serialize(),
				success:function(da){				
					if(da=='success'){
						$.dialog.alert("打印成功");
					}					
				}				
			});
		}
		
		//生成订单工艺路线
		function genOrderRoute(){
			showTips('数据处理中...');
			//检测是否已有进行的任务
			$.ajax({
				url:'${ctx}/wms/orderpd_checkDoingTask.emi?orderId=${produceOrder.WMProduceOrdergid}&type=0',
				type:"post",
				dataType:'text',
				success:function(da){				
					if(da=='1'){
						$.dialog.alert('任务正在进行，无法再生成订单工艺路线！');
						hideTips();
					}else if(da=='0'){
						$.ajax({
							url:'${ctx}/wms/orderpd_genOrderRoute.emi?orderId=${produceOrder.WMProduceOrdergid}',
							type:"post",
							//data:$('#myform').serialize(),
							dataType:'json',
							success:function(da){				
								if(da.success==1){
									$.dialog.alert_s('生成成功！');
									$('.isred').removeAttr("style");
									$('#delRouteLi').show();
									$('#genRoute').hide();
									$('#genTask').show();
								}else{
									var jsonData=da.data;
									$('.isred').removeAttr("style");
									for(var i=0;i<jsonData.length;i++){
										$('.'+jsonData[i].gid).attr('style','background: red')
									}
									if(da.failInfor){
										$.dialog.alert_e(da.failInfor);
									}else{
										$.dialog.alert_e('生成失败！');
									}
									
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
		function designProduceRoute(orderId,orderCid,number,goodsId){
			//var routeId = checkSelectId('userCheck');
			//if(routeId!=''){
				var title = "订单工艺路线设计";
				if('${changeOrder}'=='1'){
					title = "改制" +title;
				}
				var maxWidth = window.screen.width-40;//document.body.clientWidth;
				var maxHeight = window.screen.height-200;//document.body.clientHeight;
				$.dialog({ 
					drag: true,
					lock: false,
					resize: true,
					title:title,
				    width: maxWidth+'px',
					height: maxHeight+'px',
					zIndex:2000,
					content: 'url:${ctx}/wms/orderpd_toDesignOrderRoutePage.emi?changeOrder=${changeOrder}&orderId='+orderId+'&orderCid='+orderCid+'&number='+number+'&goodsId='+goodsId,
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
				url:'${ctx}/wms/orderpd_checkData.emi?orderId=${produceOrder.WMProduceOrdergid}',
				type:"post",
				//data:$('#myform').serialize(),
				dataType:'text',
				success:function(da){				
					if(da=='success'){
						//检测是否料已领，并派发领料
						$.ajax({
							url:'${ctx}/wms/orderpd_startTask.emi?orderId=${produceOrder.WMProduceOrdergid}',
							type:"post",
							//data:$('#myform').serialize(),
							dataType:'text',
							success:function(da){				
								if(da=='success'){
									$.dialog.alert_s('成功！');
								}else if(da=='error'){
									$.dialog.alert_e('失败！');
								}else{
									$.dialog.alert(da);
								}
								hideTips();
							}				
						});
						//2、检测是否已有进行的任务
						/* $.ajax({
							url:'${ctx}/wms/orderpd_checkDoingTask.emi?orderId=${produceOrder.WMProduceOrdergid}&type=1',
							type:"post",
							dataType:'text',
							success:function(da){				
								if(da=='1'){
									$.dialog.alert('任务正在进行，无法再派发任务！');
									hideTips();
								}else if(da=='0'){
									$.ajax({
										url:'${ctx}/wms/orderpd_startTask.emi?orderId=${produceOrder.WMProduceOrdergid}',
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
						}); */
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
					console.log(this.content);
					var name = this.content.document.getElementById("name").value;
					var id = this.content.document.getElementById("id").value;
					document.getElementById('depName').value=name;
					document.getElementById('depUid').value=id;
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
		function deleteProduceRoute(){
			$.dialog.confirm('是否确定删除？',function(){
				showTips('数据处理中...');
				//检测是否已有进行的任务
				$.ajax({
					url:'${ctx}/wms/orderpd_checkDoingTask.emi?orderId=${produceOrder.WMProduceOrdergid}&type=0',
					type:"post",
					dataType:'text',
					success:function(da){				
						if(da=='1'){
							$.dialog.alert('任务正在进行，无法删除订单工艺路线！');
							hideTips();
						}else if(da=='0'){
							$.ajax({
								url:'${ctx}/wms/orderpd_deleteOrderRoute.emi?orderId=${produceOrder.WMProduceOrdergid}',
								type:"post",
								//data:$('#myform').serialize(),
								dataType:'text',
								success:function(da){				
									if(da=='success'){
										$.dialog.alert_s('删除成功！');
										$('#delRouteLi').hide();
										$('#genRoute').show();
										$('#genTask').hide();
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
		
		
		function changeNum(orderId,orderCid,number,goodsId){
			var title = "变更订单数量";
			var maxWidth = window.screen.width-40;//document.body.clientWidth;
			var maxHeight = window.screen.height-200;//document.body.clientHeight;
			$.dialog({ 
				drag: true,
				lock: false,
				resize: true,
				title:title,
			    width:400,
				height: 100,
				zIndex:2000,
				content: 'url:${ctx}/wms/produceOrder_toChangeOrderNum.emi?number='+number,
				okVal:"确定",
				ok:function(){
					
 					$.ajax({
						url:'${ctx}/wms/produceOrder_changeOrderNum.emi',
						type:"post",
						data:{orderId:orderId,orderCid:orderCid,changenumber:this.content.document.getElementById('ch').value},
						dataType:'json',
						success:function(da){				
							if(da.success==1){
								$.dialog.alert_s('变更成功！');
							}else{
								$.dialog.alert_e(da.failInfor);
							}
						}				
					}); 
					
				},
				//cancelVal:"关闭",
				//cancel:true
			});	
		}
		
	</script>
</head>
<body style="background-color: #FFFFFF;">
<form id="myform">
<input type="hidden" id="produceOrdergid" name="produceOrdergid" value="${produceOrder['WMProduceOrdergid']}">
<s:token ></s:token > 
<input type="hidden" name="deleteGids" id="deleteGids" />
<input type="hidden" name="changeOrder" id="changeOrder" value="${changeOrder }">
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<li class="fl"><input type="button" class="btns" value="新增" id="addBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="修改" id="revBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="删除" id="delBtn" onclick="deletesob('${produceOrder['WMProduceOrdergid']}')"> </li>
		 			<li class="fl"><input type="button" class="btns" value="保存" id="saveBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="审核"> </li>
			 		<li class="fl" style="display:none"><input type="button" class="btns" value="弃审"> </li>
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
		 					if('1'=='${changeOrder}'){
		 						cond = "and changeOrder=1";
		 					}else{
		 						cond = "and (changeOrder is null or changeOrder=0)";
		 					}
		 					initPageTurning('${ctx }/wms/produceOrder_toAddproduceOrder.emi?changeOrder=${changeOrder}','WM_ProduceOrder','gid',"${produceOrder['WMProduceOrdergid']}",
		 							cond,'produceOrdergid');
		 				});
		 			</script>
		 			<!-- 单据翻页end -->
	 			</li>
	 			<!-- <li class="fl"><input type="button" class="btns" value="定位"> </li>
				<li class="fl"><input type="button" class="btns" value="打印"> </li> -->
				<li class="fl" style="display:none"><input type="button" class="btns" value="打印条码" onclick="printbarcode()"> </li>
				<%-- <c:if test="${changeOrder!='1' }"> --%>
					<li class="fl" style="border-left: 1px #cccccc dashed;margin-left: 10px"><input type="button" id="genRoute" class="btns" value="生成工艺路线" onclick="genOrderRoute()"> </li>
				<%-- </c:if> --%>
				<li class="fl" style="display: none" id="delRouteLi"><input type="button" id="delRoute" class="btns" value="删除工艺路线" onclick="deleteProduceRoute()"> </li>
				<li class="fl" style=""><input type="button" id="genTask" class="btns" value="派发领料任务" onclick="startTask()"> </li>
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle" style="margin-top:6px;"><c:if test="${changeOrder=='1'}">改制</c:if>生产订单</div>		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul" >
		 				<li class="wordli fl">
							<div class="wordname fl" >单据编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceOrder['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(produceOrder['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl">
							<input type="text" name="depName" id="depName" class="toDealInput" value="${department.depname}">
	 						<input type="hidden" id="depUid" name="depUid" value="${produceOrder['deptGid']}">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">负责人：</div>
							<div class="wordnameinput fl">
							<input type="text" name="managerName" id="managerName" class="toDealInput" value="${aaperson.pername}">
							<input type="hidden" value="${produceOrder['managerGid']}" id="managerGid" name="managerGid"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
		 			<li class="wordli fl">
							<%-- <div class="wordname fl">税率%：</div>
							<div class="wordnameinput fl"><input type="text" id="rate" name="rate" class="toDealInput" value="${produceOrder['rate']}"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">仓库：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceOrder['transportation']}" id="transportation" name="transportation" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">供应商：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceOrder['pcName']}" id="supplierUid" name="supplierUid" placeholder="单击选择" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">业务员：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceOrder['perName']}" id="perName" name="perName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li> --%>
		 				<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceOrder['producenotes']}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 
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
				 					<th style="width:100px;">操作</th>
				 					<th>序号</th>
				 					<th>物料编号</th>
				 					<th>物料名称</th>
				 					<th>规格型号</th>
				 					<th>生产数量</th>
				 					<c:if test="${changeOrder!='1'}"><th>转出数量</th></c:if>
				 					<th>已完工入库数量</th>
				 					<th>计划开工日期</th>
				 					<th>计划完工日期</th>
				 					<th>现存量</th>
				 					<th>备注</th>
				 					<c:forEach var="fs" items="${freeset }">
				 						<th class="freeset">${fs.projectName }</th>
				 					</c:forEach>
				 					
				 					<th>订单类型</th>
			 						<th>状态</th>
			 					
				 				</tr>
				 			<c:forEach var="type" items="${produceOrderc}" varStatus="stat">
				 		    <tr class="serialTr" >
				 		    <td class="isred ${type.gid}"><div class="delrow" name = "deleteButton" value=""  style="margin-left:15px;float: left;display: none" title="删除"></div>
				 		    	<div class="dwrow fl" name = "designButton" value=""  style="margin-left:15px;" title="修改工艺路线" onclick="designProduceRoute('${type.produceOrderUid}','${type.gid}','${type.number }','${type.goodsuid }')"></div>
				 		     	<div class="change fl" name = "changeButton" value=""  style="margin-left:15px;" title="变更数量" onclick="changeNum('${type.produceOrderUid}','${type.gid}','${type.number }','${type.goodsuid }')"></div>
				 		    	<div class="cf"></div>
				 		    </td>
				 		    <input type="hidden" id="" name="gid" class="listword" value="${type.gid}">
				 		    <td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="${type.goodsuid}"></td>
			              
			                <td class="lineNum"><input type="text" id="" name="lineNum" class="listword" value="${type.lineNum}" readonly="readonly"></td>
			              
			                <td class="goodsCode" title="双击查看生产情况" ondblclick="showProductStep('${type.produceOrderUid}','${type.gid}','${type.number }','${type.good.goodsname}')"><input type="text" id="" name="goodsCode" class="listword" value="${type.good.goodscode}" readonly="readonly" ></td>
			                <td class="goodsName" title="双击查看生产情况" ondblclick="showProductStep('${type.produceOrderUid}','${type.gid}','${type.number }','${type.good.goodsname}')"><input type="text" id="" name="goodsName" class="listword" value="${type.good.goodsname}" readonly="readonly"></td>
			                <td class="goodsstandard" title="双击查看生产情况" ondblclick="showProductStep('${type.produceOrderUid}','${type.gid}','${type.number }','${type.good.goodsname}')"><input type="text" id="" name="goodsstandard" class="listword" value="${type.good.goodsstandard}" readonly="readonly"></td>
			                <td class="number"><input type="text" id="" name="number" class="listword toDealInput numric" value="<fmt:formatNumber type="number" value="${type.number}" minFractionDigits="2" groupingUsed="false"/>"></td>
			                <td class="turnoutNum" <c:if test="${changeOrder=='1'}">style="display:none"</c:if> ><input type="text" id="" name="turnoutNum" class="listword" value="<fmt:formatNumber type="number" value="${type.turnoutNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			                <td class="completedNum"><input type="text" id="" name="completedNum" class="listword" value="<fmt:formatNumber type="number" value="${type.completedNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			                <td class="startDate"><input type="text" id="" name="startDate" class="listword startDay" value="${fn:substring(type.startDate,0,10)}" readonly="readonly"></td>
			                <td class="endDate"><input type="text" id="" name="endDate" class="listword endDay" value="${fn:substring(type.endDate,0,10)}" readonly="readonly"></td>
			                <td class="nowsum"><input type="text" id="" name="nowsum" class="listword" <c:if test="${type.nowsum==null}">value="0"</c:if> <c:if test="${type.nowsum!=null}"> value="<fmt:formatNumber type="number" value="${type.nowsum}" minFractionDigits="2"/>"</c:if> readonly="readonly"></td>
			                <td class="note"><input type="text" id="" name="note" class="listword toDealInput" value="${type.notes}" readonly="readonly"></td>
			                <c:forEach var="fs" items="${freeset }">
		 						 <td class="freeset"><input type="text" id="" name="freeset" class="listword" value="${type[fs.projectCode]}" readonly="readonly"></td>
		 					</c:forEach>
		 					
		 						<td><input type="text" id="" name="" class="listword toDealInput" value="${type.productType==1?'标准':'非标'}" readonly="readonly"></td>
								
								<c:choose>
									<c:when test="${type.state==1}"><td><input type="text" id="" name="" class="listword toDealInput" value="开立" readonly="readonly"></td></c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${type.state==2}"><td><input type="text" id="" name="" class="listword toDealInput" value="锁定" readonly="readonly"></td></c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${type.state==3}"><td><input type="text" id="" name="" class="listword toDealInput" value="审核" readonly="readonly"></td></c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${type.state==4}"><td><input type="text" id="" name="" class="listword toDealInput" value="关闭" readonly="readonly"></td></c:when>
								</c:choose>
		 					
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
					<input type="text" value="${produceOrder['recordpersonName']}" id="recordPersonName" name="recordPersonName" readonly="readonly">
					<input type="hidden" id="recordPersonUid" name="recordPersonUid" value="${produceOrder['recordPersonUid']}">
					</div>
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">录入日期：</div>
					<div class="wordnameinput fl"><input type="text" value="${fn:substring(produceOrder['recordDate'],0,10)}" id="recordDate" name="recordDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li>
 				<%-- <li class="wordli fl">
					<div class="wordname fl">审核人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${produceOrder['auditpersonName']}" id="auditpersonName" name="auditpersonName" readonly="readonly"> </div>
					<input type="hidden" id="auditPersonUid" name="auditPersonUid" value="${produceOrder['auditPersonUid']}">
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">审核日期：</div>
					<div class="wordnameinput fl"><input type="text" <c:if test="${produceOrder['auditDate']!=null}">value="${fn:substring(produceOrder['auditDate'],0,10)}"</c:if> id="auditDate" name="auditDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li> --%>
 				<div class="cf"></div> 
 			</ul>
		 	<div class="cf"></div> 
		</div>
		</form>
	</body>
</html>