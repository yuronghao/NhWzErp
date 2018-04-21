<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>属性方案</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript"	src="${ctx }/scripts/emiwms.js"></script> 
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
						url:'${ctx}/wms/purchaseArrival_addpurchaseArrival.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){				
							if(da=='success'){
								window.location.href = '${ctx}/wms/purchaseArrival_toAddpurchaseArrival.emi';
							}					
						}				
					});
				}else{//修改保存			
					$.ajax({
						url:'${ctx}/wms/purchaseArrival_updatepurchaseArrival.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){					
							if(da=='success'){
								window.location.href = '${ctx}/wms/purchaseArrival_toAddpurchaseArrival.emi';
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
			location.href = '${ctx}/wms/purchaseArrival_toAddpurchaseArrival.emi';
		}
		
		function openprintpage(){
			    $.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'打印服务',
			    width: '300px',
				height: '150px',
				content: 'url:wms/purchaseArrival_toopenprintpage.emi',
				okVal:"打印",
				ok:function(){
					var printmodel = this.content.document.getElementById('printmodel').value;
					var printservice = this.content.document.getElementById('printservice').value;
					/* if(!this.content.checkdata()){
						return false;
					} */
					if(printmodel=='0'){
						$.ajax({
							url:'${ctx}/wms/purchaseArrival_printbarcode.emi?printservice='+printservice,
							type:"post",
							data:$('#myform').serialize(),
							success:function(da){				
								if(da=='success'){
									alert("打印成功");
								}					
							}				
						});
					}else if(printmodel=='1'){
						$.ajax({
							url:'${ctx}/wms/purchaseArrival_printbarcode1.emi?printservice='+printservice,
							type:"post",
							data:$('#myform').serialize(),
							success:function(da){				
								if(da=='success'){
									alert("打印成功");
								}					
							}				
						});
					}else if(printmodel=='2'){
						$.ajax({
							url:'${ctx}/wms/purchaseArrival_printbarcode2.emi?printservice='+printservice,
							type:"post",
							data:$('#myform').serialize(),
							success:function(da){				
								if(da=='success'){
									alert("打印成功");
								}					
							}				
						});
					}else if(printmodel=='3'){
						$.ajax({
							url:'${ctx}/wms/purchaseArrival_printbarcode3.emi?printservice='+printservice,
							type:"post",
							data:$('#myform').serialize(),
							success:function(da){				
								if(da=='success'){
									alert("打印成功");
								}					
							}				
						});
					}else if(printmodel=='4'){
						$.ajax({
							url:'${ctx}/wms/purchaseArrival_printbarcode4.emi?printservice='+printservice,
							type:"post",
							data:$('#myform').serialize(),
							success:function(da){				
								if(da=='success'){
									alert("打印成功");
								}					
							}				
						});
					}
				},
				cancelVal:"关闭",
				cancel:true
			});	
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
			window.location.href = '${ctx}/wms/purchaseArrival_purchaseArrivalList.emi';
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
				trs.eq(i).children('.cfree').children().attr('id','cfree'+(i+1));
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
		
		function printbarcode(value){
			if(value=='0'){
				$.ajax({
					url:'${ctx}/wms/purchaseArrival_printbarcode.emi',
					type:"post",
					data:$('#myform').serialize(),
					success:function(da){				
						if(da=='success'){
							alert("打印成功");
						}					
					}				
				});
			}else if(value=='1'){
				$.ajax({
					url:'${ctx}/wms/purchaseArrival_printbarcode1.emi',
					type:"post",
					data:$('#myform').serialize(),
					success:function(da){				
						if(da=='success'){
							alert("打印成功");
						}					
					}				
				});
			}else if(value=='2'){
				$.ajax({
					url:'${ctx}/wms/purchaseArrival_printbarcode2.emi',
					type:"post",
					data:$('#myform').serialize(),
					success:function(da){				
						if(da=='success'){
							alert("打印成功");
						}					
					}				
				});
			}
			
		}
	</script>
</head>
<body style="background-color: #FFFFFF;">
<form id="myform">
<input type="hidden" id="purchaseArrivalgid" name="purchaseArrivalgid" value="${purchaseArrival['purchaseArrivalgid']}">
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<%-- <privilege:permission rightCode="erp_01_01_01_01">
		 			<li class="fl"><input type="button" class="btns" value="新增" id="addBtn"> </li>
		 			</privilege:permission>
		 			<privilege:permission rightCode="erp_01_01_01_02">
		 			<li class="fl"><input type="button" class="btns" value="修改" id="revBtn"> </li>
		 			</privilege:permission> --%>
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
		 					initPageTurning('${ctx }/wms/purchaseArrival_toAddpurchaseArrival.emi','WM_ProcureArrival','gid',"${purchaseArrival['purchaseArrivalgid']}",
		 							'','purchaseArrivalgid');
		 				});
		 			</script>
		 			<!-- 单据翻页end -->
	 			</li>
	 			<li class="fl"><input type="button" class="btns" value="定位"> </li>
				<li class="fl"><input type="button" class="btns" value="打印"> </li>
				<li class="fl"><input type="button" class="btns" value="打印条码" onclick="openprintpage()"></li>
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle" style="margin-top:6px;">采购到货单</div>		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">单据编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${purchaseArrival['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(purchaseArrival['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">采购类型：</div>
							<div class="wordnameinput fl">
								<select id="procureType" name="procureType" disabled="disabled">
									<option value="0"<c:if test="${purchaseArrival['procureType']==0}">selected="selected"</c:if>>采购类型</option>
									<option value="1"<c:if test="${purchaseArrival['procureType']==1}">selected="selected"</c:if>>固定资产采购</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl"><input type="text" value="${purchaseArrival['depName']}" id="depName" name="depName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
		 			<li class="wordli fl">
							<div class="wordname fl">税率%：</div>
							<div class="wordnameinput fl"><input type="text" id="rate" name="rate" class="toDealInput" value="${purchaseArrival['taxRate']}"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">仓库：</div>
							<div class="wordnameinput fl"><input type="text" value="${purchaseArrival['transportation']}" id="transportation" name="transportation" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">供应商：</div>
							<div class="wordnameinput fl"><input type="text" value="${purchaseArrival['pcName']}" id="supplierUid" name="supplierUid" placeholder="单击选择" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">业务员：</div>
							<div class="wordnameinput fl"><input type="text" value="${purchaseArrival['perName']}" id="perName" name="perName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl"><input type="text" value="${purchaseArrival['notes']}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 
		 			</ul>
		 			<div style="height: 30px;"></div>
		 			<!--end-->
		 			<div style="max-height:300px;overflow:auto;">
			 			<table>
				 			<tbody id="contr">
				 				<tr class="NO">
				 					<!-- <th style="width:80px;">操作</th> -->
				 					<th>物料编号</th>
				 					<th>物料名称</th>
				 					<th>规格型号</th>
				 					<th>应到货数量</th>
				 					<th>原币含税单价</th>
				 					<th>原币含税金额</th>
				 					<th>原币不含税单价</th>
				 					<th>原币不含税金额</th>
				 					<th>原币税额</th>
				 					<th>本币含税单价</th>
				 					<th>本币含税金额</th>
				 					<th>本币不含税单价</th>
				 					<th>本币不含税金额</th>
				 					<th>本币税额</th>
				 					<th>是否报检</th>
				 					<th>已入库数量</th>
				 					<th>批次</th>
				 					<th>工序</th>
				 					<th>最小包装量</th>
				 					<th>打印份数</th>
				 				</tr>
				 			<c:forEach var="type" items="${purchaseArrivalc}" varStatus="stat">
				 		    <tr class="serialTr">
				 		    <!-- <td><div class="delrow" name = "deleteButton" value=""  style="margin-left:15px;"></div></td> -->
				 		    <input type="hidden" id="" name="gid" class="listword" value="${type.gid}">
				 		    <td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="${type.goodsuid}"></td>
			                <td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="${type.good.goodscode}" readonly="readonly"></td>
			                <td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="${type.good.goodsname}" readonly="readonly"></td>
			                <td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="${type.good.goodsstandard}" readonly="readonly"></td>
			                <td class="number"><input type="text" id="" name="number" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.number}" minFractionDigits="2"/>"></td>
			                <td class="originalTaxPrice"><input type="text" id="" name="originalTaxPrice" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.originalTaxPrice}" minFractionDigits="2"/>"></td>
			                <td class="originalTaxMoney"><input type="text" id="" name="originalTaxMoney" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.originalTaxMoney}" minFractionDigits="2"/>"></td>
			                <td class="originalNotaxPrice"><input type="text" id="" name="originalNotaxPrice" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.originalNotaxPrice}" minFractionDigits="2"/>"></td>
			                <td class="originalNotaxMoney"><input type="text" id="" name="originalNotaxMoney" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.originalNotaxMoney}" minFractionDigits="2"/>"></td>
			                <td class="originalTax"><input type="text" id="" name="originalTax" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.originalTax}" minFractionDigits="2"/>"></td>
			                <td class="localTaxPrice"><input type="text" id="" name="localTaxPrice" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.localTaxPrice}" minFractionDigits="2"/>"></td>
			                <td class="localTaxMoney"><input type="text" id="" name="localTaxMoney" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.localTaxMoney}" minFractionDigits="2"/>"></td>
			                <td class="localNotaxPrice"><input type="text" id="" name="localNotaxPrice" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.localNotaxPrice}" minFractionDigits="2"/>"></td>
			                <td class="localNotaxMoney"><input type="text" id="" name="localNotaxMoney" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.localNotaxMoney}" minFractionDigits="2"/>"></td>
			                <td class="localTax"><input type="text" id="" name="localTax" class="listword toDealInput" value="<fmt:formatNumber type="number" value="${type.localTax}" minFractionDigits="2"/>"></td>
			                <td class="receiveNumber"><input type="text" id="" name="receiveNumber" class="listword" value="${type.needCheck}" readonly="readonly"></td>
			                <td class="putinNumber"><input type="text" id="" name="putinNumber" class="listword" value="<fmt:formatNumber type="number" value="${type.putinNumber}" minFractionDigits="2"/>" readonly="readonly"></td>
			                <td class="batch"><input type="text" id="" name="batch" class="listword" value="${type.batch}" readonly="readonly"></td>
			                <td class="cfree"><input type="text" id="" name="cfree" class="listword" value="${type.cfree1}" readonly="readonly"></td>
			                <td class="smallamount"><input type="text" id="" name="smallamount" class="listword" value="<fmt:formatNumber type="number" value="${type.number}" minFractionDigits="2"/>"></td>
			                <td class="printamount"><input type="text" id="" name="printamount" class="listword" value="1"></td>
			                <input type="hidden" id="" name="process" class="listword" value="${type.code}">
				 		    </tr>
				 	        </c:forEach>
				 			</tbody>
				 		</table>
				 		<!-- 添加按钮 -->
				 		<!-- <div class="addrow fl" style="margin-left:50px;margin-top:-23px;"> -->
					</div>
			 		</div>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		 	<ul class="wordul fr" style="width: 80%;">
 				<li class="wordli fl">
					<div class="wordname fl">录入人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${purchaseArrival['recordpersonName']}" id="recordPersonName" name="recordPersonName" readonly="readonly">
					<input type="hidden" id="recordPersonUid" name="recordPersonUid" value="${purchaseArrival['recordPersonUid']}">
					</div>
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">录入日期：</div>
					<div class="wordnameinput fl"><input type="text" value="${fn:substring(purchaseArrival['recordDate'],0,10)}" id="recordDate" name="recordDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">审核人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${purchaseArrival['auditpersonName']}" id="auditpersonName" name="auditpersonName" readonly="readonly"> </div>
					<input type="hidden" id="auditPersonUid" name="auditPersonUid" value="${purchaseArrival['auditPersonUid']}">
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">审核日期：</div>
					<div class="wordnameinput fl"><input type="text" <c:if test="${purchaseArrival['auditDate']!=null}">value="${fn:substring(purchaseArrival['auditDate'],0,10)}"</c:if> id="auditDate" name="auditDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li>
 				<div class="cf"></div> 
 			</ul>
		 	<div class="cf"></div> 
		</div>
		</form>
	</body>
</html>