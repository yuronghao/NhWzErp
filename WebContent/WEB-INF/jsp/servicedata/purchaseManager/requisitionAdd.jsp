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
			else if(document.getElementById('purchasetype').value==""){
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
					$('.demandDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
					revBtn();
			});	
			//保存
			$('#saveBtn').click(function(){		
				if(checkForm()){
				if(isSave){//新增保存			
					$.ajax({
						url:'${ctx}/wms/requisition_addrequisition.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){				
							if(da=='success'){
								window.location.href = '${ctx}/wms/requisition_toAddrequisition.emi';
							}					
						}				
					});
				}else{//修改保存			
					$.ajax({
						url:'${ctx}/wms/requisition_updaterequisition.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){					
							if(da=='success'){
								window.location.href = '${ctx}/wms/requisition_toAddrequisition.emi';
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
			location.href = '${ctx}/wms/requisition_toAddrequisition.emi';
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
		
		function getsoblist(){
			window.location.href = '${ctx}/wms/sob_getsoblist.emi';
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
				trs.eq(i).children('.demandDate').children().attr('id','demandDate'+(i+1));
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
						'<td class="demandDate"><input type="text" id="" name="demandDate" class="listword" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" readonly="readonly"></td>'+
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
		
	</script>
</head>
<body style="background-color: #FFFFFF;">
<form id="myform">
<input type="hidden" id="purchasegid" name="purchasegid" value="${requisition['purchasegid']}">
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<li class="fl"><input type="button" class="addBtn" value="新增" id="addBtn"> </li>
		 			<li class="fl"><input type="button" class="editBtn" value="修改" id="revBtn"> </li>
		 			<li class="fl"><input type="button" class="delBtn" value="删除" id="delBtn" onclick="deletesob('')"> </li>
		 			<li class="fl"><input type="button" class="saveBtn" value="保存" id="saveBtn"> </li>
		 			<li class="fl"><input type="button" class="giveupBtn" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>
		 			<li class="fl"><input type="button" class="shenheBtn" value="审核"> </li>
			 		<li class="fl"><input type="button" class="qishenBtn" value="弃审"> </li>
		 			<li class="fl"><input type="button" class="listBtn" value="列表" id="tableBtn" onclick="getsoblist()"></li>
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
		 					initPageTurning('${ctx }/wms/requisition_toAddrequisition.emi','WM_PurchaseRequisition','gid',"${requisition['purchasegid']}",
		 							'','requisitiongid');
		 				});
		 			</script>
		 			<!-- 单据翻页end -->
	 			</li>
	 			<li class="fl"><input type="button" class="dwBtn" value="定位"> </li>
	 			<li class="fl"><input type="button" class="printBtn" value="打印"> </li>
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle" style="margin-top:6px;">请购单</div>		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">单据编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${requisition['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(requisition['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">采购类型：</div>
							<div class="wordnameinput fl">
								<select id="purchasetype" name="purchasetype" disabled="disabled">
									<option value="0"<c:if test="${requisition['purchaseType']==0}">selected="selected"</c:if>>采购类型</option>
									<option value="1"<c:if test="${requisition['purchaseType']==1}">selected="selected"</c:if>>固定资产采购</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl"><input type="text" value="${requisition['depName']}" id="depName" name="depName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">业务员：</div>
							<div class="wordnameinput fl"><input type="text" value="${requisition['perName']}" id="perName" name="perName" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl"><input type="text" value="${requisition['notes']}" id="notes" name="notes" class="toDealInput"> </div>
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
				 					<th style="width:80px;">操作</th>
				 					<th>物料编号</th>
				 					<th>物料名称</th>
				 					<th>规格型号</th>
				 					<th>数量</th>
				 					<th>主计量</th>
				 					<th>辅计量</th>
				 					<th>辅计量数量</th>
				 					<th>需求日期</th>
				 				</tr>
				 			<c:forEach var="type" items="${requisitionc}" varStatus="stat">
				 		    <tr  class="serialTr">
				 		    <td><div class="delrow" name = "deleteButton" value=""  style="margin-left:15px;"></div></td>
				 		    <input type="hidden" id="" name="gid" class="listword" value="${type.gid}">
				 		    <td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="${type.goodsuid}"></td>
			                <td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="${type.good.goodscode}" readonly="readonly"></td>
			                <td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="${type.good.goodsname}" readonly="readonly"></td>
			                <td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="${type.good.goodsstandard}" readonly="readonly"></td>
			                <td class="number"><input type="text" id="" name="number" class="listword toDealInput" value="${type.number}"></td>
			                <td class="unitName"><input type="text" id="" name="unitName" class="listword" value="${type.good.goodsunit}" readonly="readonly"></td>
			                <td class="cassComUnitName"><input type="text" id="" name="cassComUnitName" class="listword" value="${type.good.cassComUnitName}" readonly="readonly"></td>
			                <td class="amount"><input type="text" id="" name="amount" class="listword toDealInput" value="${type.amount}"></td>
			                <td class="demandDate"><input type="text" id="" name="demandDate" class="listword" value="${fn:substring(type.demanddate,0,10)}" readonly="readonly"></td>
				 		    </tr>
				 	        </c:forEach>
				 			</tbody>
				 		</table>
				 		<div class="addrow fl" style="margin-left:50px;margin-top:-23px;">
					</div>
			 		</div>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		 	<ul class="wordul fr" style="width: 80%;">
 				<li class="wordli fl">
					<div class="wordname fl">录入人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${requisition['recordpersonName']}" id="recordPersonName" name="recordPersonName" readonly="readonly">
					<input type="hidden" id="recordPersonUid" name="recordPersonUid" value="${requisition['recordPersonUid']}">
					</div>
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">录入日期：</div>
					<div class="wordnameinput fl"><input type="text" value="${fn:substring(requisition['recordDate'],0,10)}" id="recordDate" name="recordDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">审核人：</div>
					<div class="wordnameinput fl"><input type="text" value="${requisition['auditpersonName']}" id="" readonly="readonly"> </div>
					<input type="hidden" id="recordPersonUid" name="recordPersonUid" value="${requisition['auditPersonUid']}">
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">审核日期：</div>
					<div class="wordnameinput fl"><input type="text" <c:if test="${requisition['auditTime']!=null}">value="${fn:substring(requisition['auditTime'],0,10)}"</c:if> id="audittime" name="audittime" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li>
 				<div class="cf"></div> 
 			</ul>
		 	<div class="cf"></div> 
		</div>
		</form>
	</body>
</html>