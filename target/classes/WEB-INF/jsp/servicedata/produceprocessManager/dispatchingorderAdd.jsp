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
		function checkForm()
		{
			if(document.getElementById('billDate').value==""){
				$.dialog.alert_w("单据日期不能为空!");
			  	return false;
			}
			else if(document.getElementById('procureType').value==""){
				$.dialog.alert_w("采购类型不能为空!");
			  	return false;
			}
			else if(document.getElementById('depName').value==""){
				$.dialog.alert_w("部门不能为空!");
			  	return false;
			}
			else if(document.getElementById('perName').value==""){
				$.dialog.alert_w("业务员不能为空!");
			  	return false;
			}else{
				return true;
			}
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
		        //getOrder();	
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				$("select").removeAttr("disabled");
				$("select option").removeAttr("selected");
				$(".addrow").attr("onclick","insertRow()");
				$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				$.ajax({
					data:{billType:13},
					url:'${ctx}/wms/sale_getBillId.emi',
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
					isSave=false;	
					getSerial();
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
								window.location.href = '${ctx}/wms/produceOrder_toAddproduceOrder.emi';
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
								window.location.href = '${ctx}/wms/produceOrder_toAddproduceOrder.emi';
							}					
						}				
					});			
				}
				}
				
			});
		});
		
		//删除订单
		function deletesob(gid) {
			if (confirm("是否确定删除?")) {
				$.ajax({
							url : 'order!delSaleOrder.action',
							type : 'post',
							data : {gId : gid},
							dataType : 'json',
							success : function(data) {						
								if (data.success == "1") {
									window.location.href = "${ctx}/order!toEditSaleOrder.action";
								} else {
									alert(data.msg);
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
			location.href = '${ctx}/wms/produceOrder_toAddproduceOrder.emi';
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
			window.location.href = '${ctx}/wms/produceprocess_getdispatchingorderlist.emi';
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
				trs.eq(i).children('.unitName').children().attr('id','unitName'+(i+1));
				trs.eq(i).children('.cassComUnitName').children().attr('id','cassComUnitName'+(i+1));
				trs.eq(i).children('.amount').children().attr('id','amount'+(i+1));
				trs.eq(i).children('.planAOG').children().attr('id','planAOG'+(i+1));
				trs.eq(i).children('.localPrice').children().attr('id','localPrice'+(i+1));
				trs.eq(i).children('.originalTaxPrice').children().attr('id','originalTaxPrice'+(i+1));
				trs.eq(i).children('.originalTaxMoney').children().attr('id','originalTaxMoney'+(i+1));
				trs.eq(i).children('.originalNotaxPrice').children().attr('id','originalNotaxPrice'+(i+1));
				trs.eq(i).children('.originalNotaxMoney').children().attr('id','originalNotaxMoney'+(i+1));
				trs.eq(i).children('.originalTax').children().attr('id','originalTax'+(i+1));
				trs.eq(i).children('.localTaxPrice').children().attr('id','localTaxPrice'+(i+1));
				trs.eq(i).children('.localTaxMoney').children().attr('id','localTaxMoney'+(i+1));
				trs.eq(i).children('.localNotaxPrice').children().attr('id','localNotaxPrice'+(i+1));
				trs.eq(i).children('.localNotaxMoney').children().attr('id','localNotaxMoney'+(i+1));
				trs.eq(i).children('.localTax').children().attr('id','localTax'+(i+1));
				trs.eq(i).children('.receiveNumber').children().attr('id','receiveNumber'+(i+1));
				trs.eq(i).children('.putinNumber').children().attr('id','putinNumber'+(i+1));
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
					var chek = $('.goodsSelected:checked',this.content.document.getElementById("rightframe").contentDocument); 
					
					for (var i = 0; i < chek.length; i++) {
						var strs='<tr class="serialTr">'+
						'<td><div class="delrow delno" name = "deleteButton" value=""  style="margin-left:15px;"></div></td>'+
						'<td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="'+chek.eq(i).val()+'"></td>'+
						'<td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="'+chek.eq(i).attr("goodsCode")+'" readonly="readonly"></td>'+
						'<td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="'+chek.eq(i).attr("goodsName")+'" readonly="readonly"></td>'+
						'<td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="'+chek.eq(i).attr("goodsstandard")+'" readonly="readonly"></td>'+
						'<td class="number"><input type="text" id="" name="number" class="listword"></td>'+
						'<td class="unitName"><input type="text" id="" name="unitName" class="listword" value="'+chek.eq(i).attr("unitName")+'" readonly="readonly"></td>'+
						'<td class="cassComUnitName"><input type="text" id="" name="cassComUnitName" class="listword" value="'+chek.eq(i).attr("cassComUnitName")+'" readonly="readonly"></td>'+
						'<td class="amount"><input type="text" id="" name="amount" class="listword"></td>'+
						'<td class="planAOG"><input type="text" id="" name="planAOG" class="listword" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" readonly="readonly"></td>'+
						'<td class="localPrice"><input type="text" id="" name="localPrice" class="listword toDealInput"></td>'+
						'<td class="originalTaxPrice"><input type="text" id="" name="originalTaxPrice" class="listword toDealInput"></td>'+
		                '<td class="originalTaxMoney"><input type="text" id="" name="originalTaxMoney" class="listword toDealInput" ></td>'+
		                '<td class="originalNotaxPrice"><input type="text" id="" name="originalNotaxPrice" class="listword toDealInput" ></td>'+
		                '<td class="originalNotaxMoney"><input type="text" id="" name="originalNotaxMoney" class="listword toDealInput" ></td>'+
		                '<td class="originalTax"><input type="text" id="" name="originalTax" class="listword toDealInput" ></td>'+
		                '<td class="localTaxPrice"><input type="text" id="" name="localTaxPrice" class="listword toDealInput"></td>'+
		                '<td class="localTaxMoney"><input type="text" id="" name="localTaxMoney" class="listword toDealInput" ></td>'+
		                '<td class="localNotaxPrice"><input type="text" id="" name="localNotaxPrice" class="listword toDealInput" ></td>'+
		                '<td class="localNotaxMoney"><input type="text" id="" name="localNotaxMoney" class="listword toDealInput" ></td>'+
		                '<td class="localTax"><input type="text" id="" name="localTax" class="listword toDealInput" ></td>'+
		                '<td class="receiveNumber"><input type="text" id="" name="receiveNumber" class="listword" readonly="readonly"></td>'+
		                '<td class="putinNumber"><input type="text" id="" name="putinNumber" class="listword" readonly="readonly"></td>'+
						'</tr>';
						$("#contr").append(strs);
						getSerial();
					}
				},
				cancelVal:"关闭",
				cancel:true
				
				});
			
		}
		$(function(){
			$('body').on('click','.delno',function(){
				var obj=$(this);
				$.dialog.confirm("确定是否删除？",function(){
					obj.parent().parent().remove();},function(){});
			});
		});
		
		function printbarcode(){
			$.ajax({
				url:'${ctx}/wms/produceOrder_printbarcode.emi',
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
		function genOrderRoute(){
			$.ajax({
				url:'${ctx}/wms/orderpd_genOrderRoute.emi?orderId=${produceOrder.WMProduceOrdergid}',
				type:"post",
				//data:$('#myform').serialize(),
				dataType:'text',
				success:function(da){				
					if(da=='success'){
						$.dialog.alert('生成成功！');
					}else{
						$.dialog.alert_e('生成失败！');
					}		
				}				
			});
		}
		
		//重新设计工艺路线
		function designProduceRoute(orderId,orderCid,number){
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
					content: 'url:${ctx}/wms/orderpd_toDesignOrderRoutePage.emi?orderId='+orderId+'&orderCid='+orderCid+'&number='+number,
					okVal:"关闭",
					ok:function(){
						//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
					},
					//cancelVal:"关闭",
					//cancel:true
				});	
			//}
		}
		
		function lookreason(gid){
		    $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'查看原因',
		    width: '800px',
			height: '400px',
			zIndex:2000,
			content: 'url:${ctx}/wms/produceprocess_lookproduceprocessreason.emi?produceprocesscgid='+gid,
			cancelVal:"关闭",
			cancel:true
		});	
	}
		
		 var LODOP; //声明为全局变量 
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
				
				LODOP.ADD_PRINT_HTM(0,0,"100%","100%",css1+'<body>'+document.getElementById("printdiv").innerHTML+'</body>');
				//LODOP.ADD_PRINT_HTM(88,200,350,600,document.getElementById("printdiv").innerHTML);
			};
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
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="新增" id="addBtn"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="修改" id="revBtn"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="删除" id="delBtn" onclick="deletesob('')"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="保存" id="saveBtn"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>
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
		 					initPageTurning('${ctx }/wms/produceprocess_toAdddispatchingorder.emi','MES_WM_ProduceProcessRoute','gid',"${produceprocess['produceprocessgid']}",
		 							'','produceprocessgid');
		 				});
		 			</script>
		 			<!-- 单据翻页end -->
	 			</li>
	 			<li class="fl" style="display:none"><input type="button" class="btns" value="定位"> </li>
				<li class="fl"><input type="button" class="btns" value="打印" onclick="prn1_preview()" > </li>
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle" style="margin-top:6px;">派工单<div style="float:right" class="creatbarcode">${produceprocess['produceordercpk']}</div></div>		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">订单编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceprocess['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(produceprocess['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">存货名称：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceprocess.good.goodsname}" id="depName" name="depName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">存货编码：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceprocess.good.goodscode}" id="perName" name="perName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">规格：</div>
							<div class="wordnameinput fl"><input type="text" value="${produceprocess.good.goodsstandard}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">下达生产量：</div>
							<div class="wordnameinput fl"><input type="text" value="<fmt:formatNumber type="number" value="${produceprocess.number}" minFractionDigits="2"/>" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">计划开工日：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(produceprocess.startDate,0,10)}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">计划完工日：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(produceprocess.endDate,0,10)}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 			<c:forEach var="column" items="${columns}" varStatus="stat">
		 				<li class="wordli fl">
							<div class="wordname fl">${column.projectName}:</div>
							<div class="wordnameinput fl"><input type="text" value="${produceprocess[column.projectCode]}"class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				</c:forEach>
		 			</ul>
		 			<div style="height: 30px;"></div>
		 			<!--end-->
		 			<div style="max-height:300px;overflow:auto;">
			 			<table>
				 			<tbody id="contr">
				 				<tr class="NO">
				 					<th style="width:80px;display:none">操作</th>
				 					<th>开始时间</th>
				 					<th>工序条码</th>
				 					<th>工序名称</th>
				 					<th>派工对象</th>
				 					<th>派工数量</th>
				 					<th>报工合格数量</th>
				 					<th>报工不合格数量</th>
				 					<th>质检合格</th>
				 					<th>质检不合格</th>
				 					
				 					<th>抽检合格</th>
				 					<th>抽检不合格</th>
				 					
				 					<th>工序说明</th>
				 					<!-- <th>报工问题数量</th> -->
				 					<th>备注</th>
				 				</tr>
				 			<c:forEach var="type" items="${dispatchingorderc}" varStatus="stat">
				 		    <tr class="serialTr">
				 		    <td style="display:none"><div class="delrow" name = "deleteButton" value=""  style="margin-left:15px;float: left;display: none" title="删除"></div>
				 		    </td>
				 		    <input type="hidden" id="" name="gid" class="listword" value="${type.gid}">
			                <td class="startTime"><input type="text" id="" name="startTime" class="listword" value="${fn:substring(type.startTime,0,10)}" readonly="readonly"></td>
			                <td class="barcode"><div class="creatbarcode">${type.barcode}</div></td>
			                <td class="opname"><input type="text" id="" name="opname" class="listword" value="${type.opname}" readonly="readonly"></td>
			                <td class="personUnitVendorGid">
			                <c:if test="${type.dispatchingObj=='0'}">
			                <input type="text" id="" name="personUnitVendorGid" class="listword" value="${type.aaperson.pername}" readonly="readonly">
			                </c:if>
			                <c:if test="${type.dispatchingObj=='1'}">
			                <input type="text" id="" name="personUnitVendorGid" class="listword" value="${type.aagroup.groupname}" readonly="readonly">
			                </c:if>
			                <c:if test="${type.dispatchingObj=='2'}">
			                <input type="text" id="" name="personUnitVendorGid" class="listword" value="${type.aaprovidercustomer.pcName}" readonly="readonly">
			                </c:if></td>
			                <td class="disNum"><input type="text" id="" name="disNum" class="listword" value="<fmt:formatNumber type="number" value="${type.disNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			                <td class="reportOkNum"><input type="text" id="" name="reportOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.reportOkNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			                <td class="reportNotOkNum"><input type="text" id="" name="reportNotOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.reportNotOkNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			               
			               	<td class="checkOkNum"><input type="text" id="" name="checkOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.checkOkNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			                <td class="checkNotOkNum"><input type="text" id="" name="checkNotOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.checkNotOkNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			               
			               	<td class="randomCheckOkNum"><input type="text" id="" name="randomCheckOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.randomCheckOkNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			                <td class="randomCheckNotOkNum"><input type="text" id="" name="randomCheckNotOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.randomCheckNotOkNum}" minFractionDigits="2"/>" readonly="readonly"></td>
			               
			               
			                <td class="opdes"><input type="text" id="" name="opdes" class="listword" value="${type.opdes}" readonly="readonly"></td>
			                <td><input type="text" id="" name="notes" class="listword" value="${type.notes}" readonly="readonly"></td>
				 		    </tr>
				 	        </c:forEach>
				 			</tbody>
				 		</table>
				 		<div class="addrow fl" style="margin-left:50px;margin-top:-23px;">
					</div>
			 		</div>
		 		</div>		 		
		 	</div>
		 	<div class="creattable" id="printdiv" style="display:none;">
		 		<div class="tabletitle" style="margin-top:6px;;text-align:center;width:80%;float:left;">派工单
		 		</div>
		 		<div style="float:right" class="creatbarcode">${produceprocess['produceordercpk']}</div>
		 		<div style="clear:both;"></div>		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%;">
							<div class="wordname fl" style="float:left">订单编号：</div>
							<div class="wordnameinput fl" style="float:left;"><input type="text" style="border:none;" value="${produceprocess['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%">
							<div class="wordname fl" style="float:left;">单据日期：</div>
							<div class="wordnameinput fl" style="float:left;"><input type="text" style="border:none;" value="${fn:substring(produceprocess['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%">
							<div class="wordname fl" style="float:left;">存货名称：</div>
							<div class="wordnameinput fl" style="float:left;"><input type="text" style="border:none;" value="${produceprocess.good.goodsname}" id="depName" name="depName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%">
							<div class="wordname fl" style="float:left">存货编码：</div>
							<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${produceprocess.good.goodscode}" id="perName" name="perName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="list-style:none;float:left;width:30%">
							<div class="wordname fl" style="float:left;text-align:right">规格：</div>
							<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${produceprocess.good.goodsstandard}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="list-style:none;float:left;width:32%">
							<div class="wordname fl" style="float:left">下达生产量：</div>
							<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="<fmt:formatNumber type="number" value="${produceprocess.number}" minFractionDigits="2"/>" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl" style="list-style:none;float:left;width:31%">
							<div class="wordname fl" style="float:left">计划开工日：</div>
							<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${fn:substring(produceprocess.startDate,0,10)}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl" style="float:left;list-style:none;width:31%">
							<div class="wordname fl" style="float:left">计划完工日：</div>
							<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${fn:substring(produceprocess.endDate,0,10)}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli" style="list-style:none;float:left;width:30%;">
								<div class="wordname fl" style="float:left"></div>
								<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value=""class="toDealInput"> </div>
								<div class="cf"></div> 
			 				</li>
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul" style="">
		 				<c:forEach var="column" items="${columns}" varStatus="stat">
			 				<li class="wordli" style="list-style:none;float:left;width:30%;margin-left:0px;">
								<div class="wordname fl" style="float:left;">${column.projectName}:</div>
								<div class="wordnameinput fl" style="float:left"><input type="text" style="border:none;" value="${produceprocess[column.projectCode]}"class="toDealInput"> </div>
								<div class="cf"></div> 
			 				</li>
		 				</c:forEach>
		 				<div class="cf"></div>
		 			</ul>
		 			<div style="height: 30px;"></div>
		 			<!--end-->
		 			<div style="max-height:300px;overflow:auto;border:1px #ccc solid;">
		 				<table  cellpadding="0" cellspacing="0">
				 			<tbody id="contr">
				 				<tr class="NO" style="height:35px;line-height:35px;background-color:#e2eaf0;border:1px #ccc solid;">
				 					<th style="width:100px;">开始时间</th>
				 					<th style="width:100px;">工序条码</th>
				 					<th style="width:100px;">工序名称</th>
				 					<th style="width:90px;">派工对象</th>
				 					<th style="width:90px;">派工数量</th>
				 					<th style="width:140px;">报工合格数量</th>
				 					<th style="width:130px;">报工不合格数量</th>
				 					<!-- <th style="width:130px;">报工问题数量</th> -->
				 				</tr>
				 				<c:forEach var="type" items="${dispatchingorderc}" varStatus="stat">
				 		    <tr class="serialTr" style="border:1px #ccc solid;">
				 		    <input type="hidden" id="" name="gid" class="listword" value="${type.gid}">
			                <td style="border-top:1px #ccc solid;border-right:none;height:55px;" class="startTime">
			               		<input type="text" style="border:none;width:90%;" id="" name="startTime" class="listword" value="${fn:substring(type.startTime,0,10)}" readonly="readonly">
			                </td>
			                <td style="border-top:1px #ccc solid;border-right:none;height:55px;" class="barcode">
			                	<div class="creatbarcode">${type.barcode}</div>
			                </td>
			                <td style="border-top:1px #ccc solid;" class="opname">
			                	<input type="text" style="border:none;width:100%" id="" name="opname" class="listword" value="${type.opname}" readonly="readonly">
			                </td>
			                <c:if test="${type.dispatchingObj=='0'}">
			                 <td style="border-top:1px #ccc solid;" class="personUnitVendorGid">
			                	<input type="text" style="border:none;width:100%" id="" name="personUnitVendorGid" class="listword" value="${type.aaperson.pername}" readonly="readonly">
			                </td>
			                </c:if>
			                 <c:if test="${type.dispatchingObj=='1'}">
			                 <td style="border-top:1px #ccc solid;" class="personUnitVendorGid">
			                	<input type="text" style="border:none;width:100%" id="" name="personUnitVendorGid" class="listword" value="${type.aagroup.groupname}" readonly="readonly">
			                </td>
			                </c:if>
			                 <c:if test="${type.dispatchingObj=='2'}">
			                 <td style="border-top:1px #ccc solid;" class="personUnitVendorGid">
			                	<input type="text" style="border:none;width:100%" id="" name="personUnitVendorGid" class="listword" value="${type.aaprovidercustomer.pcName}" readonly="readonly">
			                </td>
			                </c:if>
			                <c:if test="${type.dispatchingObj==null}">
			                 <td style="border-top:1px #ccc solid;" class="personUnitVendorGid">
				                <input type="text" style="border:none;width:100%" id="" name="" class="listword" value="" readonly="readonly">
				                </td>
				                </c:if>
			                 <td style="border-top:1px #ccc solid;" class="disNum">
			                	<input type="text" style="border:none;width:100%" id="" name="disNum" class="listword" value="<fmt:formatNumber type="number" value="${type.disNum}" minFractionDigits="2"/>" readonly="readonly">
			                </td>
			                 <td style="border-top:1px #ccc solid;" class="reportOkNum">
			                	<input type="text" style="border:none;width:100%" id="" name="reportOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.reportOkNum}" minFractionDigits="2"/>" readonly="readonly">
			                </td>
			                 <td style="border-top:1px #ccc solid;" class="reportNotOkNum">
			                	<input type="text" style="border:none;" id="" name="reportNotOkNum" class="listword" value="<fmt:formatNumber type="number" value="${type.reportNotOkNum}" minFractionDigits="2"/>" readonly="readonly">
			                </td>
			                 <!-- <td style="border-top:1px #ccc solid;" class="reportProblemNum">
			                	<input type="text" style="border:none;width:100%" id="" name="reportProblemNum" class="listword" value="" readonly="readonly">
			                </td> -->
				 		    </tr>
				 	        </c:forEach>
				 			</tbody>
				 		</table>
			 			<%-- <table cellpadding="0" cellspacing="0" style="border:1px #ccc solid;margin-left:10px;width:60%;">
				 			<tbody id="contr">
				 				<tr class="NO" style="border:1px #000 solid;height:35px;line-height:35px;background-color:red;">
				 					<th style="width:10%">开始时间</th>
				 					<th style="width:10%">工序条码</th>
				 					<th style="width:10%">工序名称</th>
				 					<th style="width:10%">派工对象</th>
				 					<th style="width:10%">派工数量</th>
				 					<th style="width:10%">报工合格数量</th>
				 					<th style="width:10%">报工不合格数量</th>
				 					<th style="width:10%">报工问题数量</th>
				 				</tr>
				 			<c:forEach var="type" items="${dispatchingorderc}" varStatus="stat">
				 		    <tr class="serialTr" style="border:1px #ccc solid;">
				 		    <input type="hidden" id="" name="gid" class="listword" value="${type.gid}">
			                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;" class="startTime">
			               		<input type="text" style="border:none;" id="" name="startTime" class="listword" value="${fn:substring(type.startTime,0,10)}" readonly="readonly">
			                </td>
			                <td style="border-top:1px #ccc solid;border-right:1px #ccc solid;" class="barcode">
			                	<div class="creatbarcode">${type.barcode}</div>
			                </td>
			                <td style="border-top:1px #ccc solid;" class="opname">
			                	<input type="text" style="border:none;" id="" name="opname" class="listword" value="${type.opname}" readonly="readonly">
			                </td>
			                <c:if test="${type.dispatchingObj=='0'}">
			                 <td style="border-top:1px #ccc solid;" class="personUnitVendorGid">
			                	<input type="text" style="border:none;" id="" name="personUnitVendorGid" class="listword" value="${type.aaperson.pername}" readonly="readonly">
			                </td>
			                </c:if>
			                 <c:if test="${type.dispatchingObj=='1'}">
			                 <td style="border-top:1px #ccc solid;" class="personUnitVendorGid">
			                	<input type="text" style="border:none;" id="" name="personUnitVendorGid" class="listword" value="${type.aagroup.groupname}" readonly="readonly">
			                </td>
			                </c:if>
			                 <c:if test="${type.dispatchingObj=='2'}">
			                 <td style="border-top:1px #ccc solid;" class="personUnitVendorGid">
			                	<input type="text" style="border:none;" id="" name="personUnitVendorGid" class="listword" value="${type.aaprovidercustomer.pcName}" readonly="readonly">
			                </td>
			                </c:if>
			                 <td style="border-top:1px #ccc solid;" class="disNum">
			                	<input type="text" style="border:none;" id="" name="disNum" class="listword" value="${type.disNum}" readonly="readonly">
			                </td>
			                 <td style="border-top:1px #ccc solid;" class="reportOkNum">
			                	<input type="text" style="border:none;" id="" name="reportOkNum" class="listword" value="" readonly="readonly">
			                </td>
			                 <td style="border-top:1px #ccc solid;" class="reportNotOkNum">
			                	<input type="text" style="border:none;" id="" name="reportNotOkNum" class="listword" value="" readonly="readonly">
			                </td>
			                 <td style="border-top:1px #ccc solid;" class="reportProblemNum">
			                	<input type="text" style="border:none;" id="" name="reportProblemNum" class="listword" value="" readonly="readonly">
			                </td>
				 		    </tr>
				 	        </c:forEach>
				 			</tbody>
				 		</table> --%>
				 		<!-- <div class="addrow fl" style="margin-left:50px;margin-top:-23px;"></div> -->
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