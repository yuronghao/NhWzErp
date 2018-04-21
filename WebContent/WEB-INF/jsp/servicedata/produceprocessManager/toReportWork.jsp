<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>生产订单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript"	src="${ctx }/scripts/emiwms.js"></script> 
<script  type="text/javascript" src="${ctx}/scripts/LodopFuncs.js"></script>
<script  type="text/javascript" src="${ctx}/scripts/jquery-barcode-2.0.1.js"></script>
<script type="text/javascript">
		function addCheckForm()
		{
			
			var reportOkNumObjValues=$('.reportOkNum');//报工合格
			var reportNotOkNumObjValues=$('.reportNotOkNum');//报工不合格
			
			for(var i=0;i<reportOkNumObjValues.length;i++){
				var canReportNum=parseFloat(reportOkNumObjValues.eq(i).parent().prev().text());
				
				var reportNow=(reportOkNumObjValues.eq(i).val()==""?0:parseFloat(reportOkNumObjValues.eq(i).val()))+
					 	  (reportNotOkNumObjValues.eq(i).val()==""?0:parseFloat(reportNotOkNumObjValues.eq(i).val()))
				
				if(reportNow > canReportNum ){
					$.dialog.alert_w("报工数量大于可报工数量！");
					return false;
				}
				
				if( reportNow ==0){
					$.dialog.alert_w("报工数量不能为0！");
					return false;
				}
				
			}
				return true;
		}
		
		function updCheckForm()
		{
			
			var reportOkNumObjValues=$('.reportOkNum');//报工合格
			var reportNotOkNumObjValues=$('.reportNotOkNum');//报工不合格
			
			var isAllZero=true;
			for(var i=0;i<reportOkNumObjValues.length;i++){
				var canReportNum=parseFloat(reportOkNumObjValues.eq(i).attr("sumv"));
				
				var reportNow=(reportOkNumObjValues.eq(i).val()==""?0:parseFloat(reportOkNumObjValues.eq(i).val()))+
						 	  (reportNotOkNumObjValues.eq(i).val()==""?0:parseFloat(reportNotOkNumObjValues.eq(i).val()))
				
				if(reportNow > canReportNum ){
					$.dialog.alert_w("报工数量大于可报工数量！");
					return false;
				}
				
				if( reportNow >0){
					isAllZero=false;
				}
			}
			
			if(isAllZero){
				$.dialog.alert_w("报工数量不能都为0！");
				return false;
			}
			
			return true;
		}
		
		//按钮事件
		$(function(){
			var toDeal=$('.toDealInput');
			inputCannotUse(toDeal);
			var isAdd=true;//新增保存标识
			var isUpd=true;//修改保存标识
			var isClick=false;//是否已点击(初始值)
			
			//新增 
			$('#addBtn').click(function(){
				isAdd=true;
				isUpd=false;
				isAdjustment=false;
				isClick=false;
				
				$('#billCode').removeAttr("disabled");
				$('.wordul').children().children().children('input').val("");//清理文本框内容
				$('tr').not($('.NO')).remove();//表格内容全清
		        //getOrder();	
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				$("select").removeAttr("disabled");
				$("select option").removeAttr("selected");
				$(".addrow").attr("onclick","insertRow()");
				/* $('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})"); */
				addBtn();

			});
			
			//复制
			$('#copyReport').click(function(){
				isAdd=true;
				isUpd=false;	
				isAdjustment=false;
				isClick=false;
				var barCode=$('#processSelect').children(':selected').attr("barcode");
				var dispatchingObj=$('#processSelect').children(':selected').attr("dispatchingObj");
				$('#dispatchingObjHidden').val(dispatchingObj);
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				$("select").removeAttr("disabled");
				$("select option").removeAttr("selected");
				$(".delrow").addClass("delno");
				$(".addrow").attr("onclick","insertRow()");
				$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				$('.planAOG').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				revBtn();
				
				initProcessOption('${map.orderInfor.pocgid}',barCode);
			});
			
			//修改
			$('#revBtn').click(function(){	
				isAdd=false;
				isUpd=true;	
				isAdjustment=false;
				isClick=false;
				
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				$("select").removeAttr("disabled");
				$("select option").removeAttr("selected");
				$(".delrow").addClass("delno");
				$(".addrow").attr("onclick","insertRow()");
				$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				$('.planAOG').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				revBtn();
			});
			
			//调整人员
			$('#adjustmentPeople').click(function(){
				isAdd=false;
				isUpd=false;
				isAdjustment=true;
				isClick=false;
				
				adjustmentPeopleBtn();
			})
			
			//保存
			$('#saveBtn').click(function(){
				if(isClick){
					$.dialog.alert_e("请不要重复提交！");
					return;
				}
				
				if(isAdd ){
					if(addCheckForm()){
						isClick=true;
						$.ajax({
							url:'${ctx}/wms/produceprocess_reportWork.emi',
							type:"post",
							data:$('#myform').serialize(),
							success:function(da){	
								var jsonboj=eval("("+da+")");
								if(jsonboj.success=='1'){
									  $.dialog.alert_s('保存成功',function(){
										  window.location.href = '${ctx}/wms/produceprocess_toReportWork.emi';
									  });
								}					
							},
							error:function(){
								isClick=false;
								$.dialog.alert_e("报工失败！");
							}
						});
					}
					
				}
				if(isUpd){
					if(updCheckForm()){
						isClick=true;
						$.ajax({
							url:'${ctx}/wms/produceprocess_updateReportWork.emi',
							type:"post",
							data:$('#myform').serialize(),
							success:function(da){
								var jsonboj=eval("("+da+")");
								if(jsonboj.success=='1'){
									  $.dialog.alert_s('保存成功',function(){
										  window.location.href = '${ctx}/wms/produceprocess_toReportWork.emi';
									  });
								}else{
									isClick=false;
									$.dialog.alert_s(jsonboj.failInfor,function(){
										  window.location.href = '${ctx}/wms/produceprocess_toReportWork.emi';
									  });
								}					
							},
							
						error:function(){
							isClick=false;
							$.dialog.alert_e("报工失败！");
						}
						});	
					}

				}
				
				if(isAdjustment){//调整人员
					
					isClick=true;
					$.ajax({
						url:'${ctx}/wms/produceprocess_updAdjustmentPeopleGroupReport.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){					
							var jsonboj=eval("("+da+")");
							if(jsonboj.success=='1'){
								$.dialog.alert_s('调整人员成功',function(){
									  window.location.href = '${ctx}/wms/produceprocess_toReportWork.emi';
								  });
							}else {
								  isClick=false;
								  $.dialog.alert_e(jsonboj.failInfor);
							  }					
						},
						error:function(){
							isClick=false;
							$.dialog.alert_e(jsonboj.failInfor);
						}
					});	

				}
				
			});
			
		});
		
		
		
		function giveup() {
			location.href = '${ctx}/wms/produceprocess_toReportWork.emi';
		}
		
		
		function deletesob(obj){
			var reportGid=$("#reportGid").val();
			var pprcGid=$("#pprcGid").val();
			
			$.dialog.confirm(tip_confirmDelete, function(){		
				$.ajax({
					  data:{reportGid:reportGid,pprcGid:pprcGid},
					  type: 'post',
					  url: '${ctx}/wms/produceprocess_deleteReportWork.emi',
					  success: function(da){
						  var jsonboj=eval("("+da+")");
						  if(jsonboj.success=='1'){
							  $.dialog.alert_s('删除成功',function(){
								  window.location.href="${ctx}/wms/produceprocess_toReportWork.emi";
							  });
						  }else {
							  $.dialog.alert_e(jsonboj.failInfor);
						  }
						  
					  }
				});
			});
		}
		
		
		//根据订单号 获取订单相关信息
		function getInforByOrderCode(){
			var pwdWin = $.dialog({ 
				drag: true,
				lock: true,
				resize: false,
				title:'选择订单产品',
			    width: '800px',
				height: '400px',
				zIndex:4004,
				content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PRODUCEORDERLIST%>&multi=0&showTree=0',
				okVal:"确定",
				ok:function(){
					var usercheck = this.content.window.jsonArray;
					document.getElementById('billDate').value=usercheck[0].billDate;
					document.getElementById('billCode').value=usercheck[0].billCode;
					document.getElementById('goodsCode').value=usercheck[0].goodsCode;
					document.getElementById('goodsName').value=usercheck[0].goodsName;
					document.getElementById('goodsStandand').value=usercheck[0].goodsStandand;
					document.getElementById('number').value=usercheck[0].number;
					document.getElementById('startDate').value=usercheck[0].startDate;
					document.getElementById('endDate').value=usercheck[0].endDate;
					
					var pocgid=usercheck[0].pocgid;
					initProcessOption(pocgid);
					
				},
				cancelVal:"关闭",
				cancel:true
			});
		}
		
		function initProcessOption(pocgid,selVal){
			$.ajax({
				url:'${ctx}/wms/produceprocess_getProduceProcessInforByOrder.emi',
				type:"post",
				data:{pocgid:pocgid},
				success:function(da){
					var obj = eval (da);
					$('#processSelect').children().remove();
					$('#processSelect').append("<option barCode='-1'>请选择工序</option>");
					for(var i=0;i<obj.length;i++){
						var isselect = "";
						if(selVal && selVal==obj[i].barcode){
							isselect = 'selected=\"selected\"';
						}
						$('#processSelect').append("<option value="+i+" "+isselect+" barCode="+obj[i].barcode+" dispatchingObj="+obj[i].dispatchingType+" >"+obj[i].opName+"</option>");
					}
					 
				}		
			})
		}
		
		//选择工序获得 可报工数量
		function getCanDispatchedNum(obj){
			var barCode=$(obj).children(':selected').attr("barcode");
			var dispatchingObj=$(obj).children(':selected').attr("dispatchingObj");
			if(barCode==-1){
				$('.retr').remove();
				$('#mainNotes').val("");
				return;
			}
			
			$.ajax({
				url:'${ctx}/wms/produceprocess_getInfoByBarcodeReport.emi',
				type:"post",
				data:{barCode:barCode,dispatchingObj:dispatchingObj},
				success:function(da){
					var obj = eval("("+da+")");
					var results=obj.personUnitVendor;
					$('#mainNotes').val(obj.task.notes);
					$('.detailNotes').val("");
					$('#dispatchingObjHidden').val(obj.task.dispatchingObj);
					$('.retr').remove();
					
					for(var i=0;i<results.length;i++){
						$('#contr').append("<tr class='retr'>"+
												"<td style='display: none;'><input type='text' id='' name='personUnitVendorGid' class='' value='"+results[i].personUnitVendorGid+"'/></td>"+
												"<td style='display: none;'><input type='text' id='' name='discGid' class='' value='"+results[i].discGid+"'/></td>"+
												"<td style='display: none;'><input type='text' id='' name='produceProcessRoutecGid' class='' value='"+results[i].produceProcessRoutecGid+"'/></td>"+
			 					                "<td>"+results[i].personUnitVendorName+"</td>"+
			 					                "<td>"+results[i].canReprotNum+"</td>"+
			 					                "<td><input type='text' id='' name='reportOkNum' class='reportOkNum' value=''/></td>"+
			 					                "<td><input type='text' id='' name='reportNotOkNum' class='reportNotOkNum' value=''/></td>"+
			 					                "<td><input type='text' id='' name='detailNotes' class='detailNotes' value='"+results[i].notes+"'/></td>"+
			 					                
			 					                "<td></td>"+
				 					            "<td></td>"+
				 					            "<td></td>"+
				 					            "<td></td>"+
				 					            "<td></td>"+
				 					            "<td></td>"+
				 					            "<td></td>"+
			 					                
			 				                "</tr>");
					}
					
					
					
				}	
			});
		}
		
		
		
		//调整人员时获得方法
		function getAdjustmentPeople(obj){
			
			var dispatchingObj=$("#adjustmentDispatchingObjHidden").val();
			if(dispatchingObj==0){/*派发到人*/
				var pwdWin = $.dialog({ 
					drag: true,
					lock: true,
					resize: false,
					title:'选择人人员',
				    width: '800px',
					height: '400px',
					zIndex:3004,
					content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PERSON%>&multi=0&showTree=1',
					okVal:"确定",
					ok:function(){
						var usercheck = this.content.window.jsonArray;
						if(usercheck.length>0){
							$(obj).text(usercheck[0].pername)
							$(obj).prev().children(".adjustmentPeopleGroupGid").val(usercheck[0].gid)
						}
						
					},
					cancelVal:"关闭",
					cancel:true
				});
			}else if(dispatchingObj==1){/*派发到组*/
				var pwdWin = $.dialog({ 
					drag: true,
					lock: true,
					resize: false,
					title:'选择组',
				    width: '800px',
					height: '400px',
					zIndex:3004,
					content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GROUP%>&multi=0&showTree=0',
					okVal:"确定",
					ok:function(){
						
						var usercheck = this.content.window.jsonArray;
						if(usercheck.length>0){
							$(obj).text(usercheck[0].groupname)
							$(obj).prev().children(".adjustmentPeopleGroupGid").val(usercheck[0].gid)
						}
						
					},
					cancelVal:"关闭",
					cancel:true
				});
			}
		}
		
		
		
	</script>
	
</head>
<body style="background-color: #FFFFFF;">
<form id="myform">
<input type="hidden" id="produceOrdergid" name="produceOrdergid" value="${produceOrder['produceOrdergid']}">
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	
		 	<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<li class="fl" style=""><input type="button" class="btns" value="新增" id="addBtn"> </li>
		 			<li class="fl" style=""><input type="button" class="btns" value="修改" id="revBtn"> </li>
		 			<li class="fl" style=""><input type="button" class="btns" value="删除" id="delBtn" onclick="deletesob(this)"> </li>
		 			<li class="fl" style=""><input type="button" class="btns" value="保存" id="saveBtn"> </li>
		 			<li class="fl" style=""><input type="button" class="btns" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="审核"> </li>
			 		<li class="fl" style="display:none"><input type="button" class="btns" value="弃审"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="列表" id="tableBtn" onclick="getprocurearrivallist()"></li>
		 			<li class="fl" style="">
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
			 					initPageTurning('${ctx }/wms/produceprocess_toReportWork.emi','MES_WM_ReportOrder','gid',"${map.gid}",'','reportGid');
			 				});
			 			</script>
			 			<!-- 单据翻页end -->
	 				</li>
	 				<li class="fl" style="display:none"><input type="button" class="btns" value="定位"> </li>
	 				<li class="fl" style=""><input type="button" class="btns" id="adjustmentPeople" value="调整人员"> </li>
	 				<!-- <li class="fl" style=""><input type="button" class="btns" id="copyReport" value="复制"> </li> -->
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle" style="margin-top:6px;">报工<div style="float:right" class="creatbarcode">${produceprocess['produceordercpk']}</div></div>	
		 					
		 		<input type="hidden" id="dispatchingObjHidden" name="dispatchingObjHidden" class="" value="" />	<!--派工对象-->	
		 		
		 		<input type="hidden" id="adjustmentDispatchingObjHidden" name="adjustmentDispatchingObjHidden" class="" value="${map.dispatchingObj }" />
		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">订单编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${map.orderInfor['billCode']}" id="billCode" name="billCode" class="toDealInput" readonly="readonly" disabled="disabled" onclick="getInforByOrderCode();" > </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(map.orderInfor['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">存货编码：</div>
							<div class="wordnameinput fl"><input type="text" value="${map.orderInfor.goodsCode}" id="goodsCode" name="goodsCode" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">存货名称：</div>
							<div class="wordnameinput fl"><input type="text" value="${map.orderInfor.goodsName}" id="goodsName" name="goodsName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">规格：</div>
							<div class="wordnameinput fl"><input type="text" value="${map.orderInfor.goodsStandand}" id="goodsStandand" name="goodsStandand" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">下达生产量：</div>
							<div class="wordnameinput fl"><input type="text" value="<fmt:formatNumber type="number" value="${map.orderInfor.number}" minFractionDigits="2"/>" id="number" name="number" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">计划开工日：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(map.orderInfor.startDate,0,10)}" id="startDate" name="startDate" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">计划完工日：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(map.orderInfor.endDate,0,10)}" id="endDate" name="endDate" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">工序：</div>
							<div class="wordnameinput fl">
								<select id="processSelect"  onchange="getCanDispatchedNum(this)">
									<c:if test="${map.childrenMap  !=null }">
										<option value="" barCode="${map.childrenMap[0].barcode }" dispatchingObj="${map.dispatchingObj }">${ map.childrenMap[0].opname }</option>
									</c:if>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl"><input type="text" value="${map.memo }" id="mainNotes" name="mainNotes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">报工编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${map.rptCode }" id="rptcode" name="rptcode" class="toDealInput" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">批次：</div>
							<div class="wordnameinput fl"><input type="text" value="${map.batch }" id="" name="" class="toDealInput" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 			</ul>
		 			
		 			<div style="height: 30px;"></div>
		 			<!--end-->
		 			
		 			<input type="hidden" id="reportGid" name="reportGid" class="" value="${map.gid }" />
		 			<input type="hidden" id="pprcGid" name="pprcGid" class="" value="${map.childrenMap[0].pprcgid }" />
		 			
		 			<div style="max-height:300px;overflow:auto;">
			 			<table>
				 			<tbody id="contr">
				 				<tr class="NO">
				 					<th>分配对象(人或组)</th>
				 					<th>可报工数量</th>
				 					<th>本次报工数量(合格)</th>
				 					<th>本次报工数量(不合格)</th>
				 					<th>备注</th>
				 					
					 				<th>设备编码</th>
				 					<th>设备名称</th>
				 					<th>设备部门</th>
				 					<th>模具编码</th>
				 					<th>模具名称</th>
				 					<th>模比</th>
				 					<th>班次</th>
				 				</tr>
				 				
				 				<c:forEach items="${map.childrenMap }" var="cm" >
			 						<tr class='retr'>
										<td style='display: none;'><input type='text' id='' name='personUnitVendorGid' class='' value='${cm.personUnitVendorGid }'/></td>
										<td style='display: none;'><input type='text' id='' name='rptcGids' class='' value='${cm.gid }'/></td>
										<td style='display: none;'><input type='text' id='' name='discGid' class='' value='${cm.discGid }'/></td>
										<td style='display: none;'>
											<input type='text' id='' name='produceProcessRoutecGid' class='' value='${cm.produceProcessRouteCGid }'/>
											<input type='hidden'  value='${cm.personUnitVendorGid }' id='' name='adjustmentPeopleGroupGid' class='adjustmentPeopleGroupGid'/><!--记录调整人员时所选的人或组-->
										</td>
		 					            <td onclick="getAdjustmentPeople(this)">${cm.reportObjName }  </td>
		 					            <td>${cm.candisNum }</td>
		 					            <td><input type='text' id='' name='reportOkNum' class='reportOkNum' sumv='${cm.reportOkNum+cm.reportNotOkNum+cm.candisNum }' value='${cm.reportOkNum }'/></td>
		 					            <td><input type='text' id='' name='reportNotOkNum' class='reportNotOkNum' sumv='${cm.reportOkNum+cm.reportNotOkNum+cm.candisNum }' value='${cm.reportNotOkNum }'/></td>
		 					            <td><input type='text' id='' name='detailNotes' class='detailNotes' value='${cm.notes }'/></td>
		 					            
		 					            <td>${cm.equipmentCode }</td>
		 					            <td>${cm.equipmentName }</td>
		 					            <td>${cm.depname }</td>
		 					            <td>${cm.mouldCode }</td>
		 					            <td>${cm.mouldName }</td>
		 					            <td>${cm.mouldRatio }</td>
		 					            <td><c:if test="${cm.workingTime==0}">白班</c:if><c:if test="${cm.workingTime==1}">夜班</c:if></td>
		 				            
		 				            
		 				            </tr>
				 				</c:forEach>
				 				
				 			</tbody>
				 		</table>
					</div>
			 		</div>
		 		</div>		 		
		 	</div>

		</div>
		</form>
	</body>
	<script type="text/javascript">
	function getBarcode(){
		
		var codetype="code128";
		var list=$('.creatbarcode');
		for(var i=0;i<list.length;i++){
			var getval=list.eq(i).html();
			list.eq(i).barcode(getval, codetype,{barWidth:1.8, barHeight:20,fontSize:10});  
		}
	}
	getBarcode();
		
</script>
</html>