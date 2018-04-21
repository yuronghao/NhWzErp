<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<style>
	.wordname{
		width:125px;
	}
	.wordli{width:32%;}
	.wordnameinput{width:50%;}
</style>
</head>

<script type="text/javascript">

function checkdata()
{
	if(document.getElementById('goodsSortUid').value==""){
		$.dialog.alert_w("物料分类不能为空!");
	  	return false;
	}
	else
	{ 
		
		return true;
	}
}
	//选择模具类别
	function selectcuspro(id){
	    $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'选择模具类别',
		    width: '800px',
			height: '400px',
			zIndex:2000,
			content: 'url:${ctx}/wms/cuspro_ProCusSelect.emi?id='+id,
			okVal:"确定",
			ok:function(){
				var id = this.content.document.getElementById("id").value;
				var name = this.content.document.getElementById("name").value;
				var typeid = this.content.document.getElementById("typeid").value;
				$("#mouldStyle").val(name);
				document.getElementById('mouldStyleId').value=id;
				
			},
			cancelVal:"关闭",
			cancel:true
		});	
	}
	
	//选择所在部门
	function selectCurrentDept(){
		  $.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'选择目前所在部门',
			    width: '800px',
				height: '400px',
				zIndex:2000,
				content: 'url:${ctx}/wms/org_getDepartmentTreeHelp.emi',
				okVal:"确定",
				ok:function(){
					var id = this.content.document.getElementById("id").value;
					var name = this.content.document.getElementById("name").value;
					$("#currentDeptName").val(name);
					document.getElementById('currentDeptGid').value=id;
					
				},
				cancelVal:"关闭",
				cancel:true
			});	
	}
	
	//选择客户
	function selectCustomer(){
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
					$("#cutomerName").val(returnArray[0].pcname);
					$("#cutomerGid").val(returnArray[0].gid);
					
				}
			},
			cancelVal:"关闭",
			cancel:true
		});
 	}
	
	//选择供应商
	function selectProviderName(){
	 		$.dialog({ 
				drag: true,
				lock: false,
				resize: false,
				title:'选择供应商',
			    width: '800px',
				height: '500px',
				zIndex:3000,
				content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PROVIDERCUSTOMER%>&multi=0&showTree=0', 
				okVal:"确定",
				ok:function(){
					var returnArray = this.content.window.jsonArray;
					if(returnArray.length>0){
						$('#providerGid').val(returnArray[0].gid);
						$('#providerName').val(returnArray[0].pcname);
					}
				},
				cancelVal:"关闭",
				cancel:true
			});
	 	}
	
	//保存
	function saveProcess(){
		$.ajax({
			data: $("#myform").serialize(),
			type: 'POST',
			url: '${ctx}/wms/mould_addmould.emi',
			async: true,
			success: function(req){
				if(req=='success'){
					$.dialog.alert_s('保存成功',function(){
						location.href="${ctx}/wms/mould_getmouldList.emi";
					});
				}else if(req=='error'){
					$.dialog.alert_e('保存失败');
				}else if(req=='mouldStyleId'){
					$.dialog.alert_e('模具分类不得为空');
				}else if(req=='mouldCode'){
					$.dialog.alert_e('模具编码不得为空');
				}else if(req=='mouldName'){
					$.dialog.alert_e('模具名称不得为空');
				}else if(req=='barcode'){
					$.dialog.alert_e('模具条码不得为空');
				}else if(req=='cost'){
					$.dialog.alert_e('模具成本格式错误');
				}else if(req=='mbarcode'){
					$.dialog.alert_e('模具条码第一位M请大写');
				}else if(req=='erroBarcode'){
					$.dialog.alert_e('模具条码格式错误');
				}else if(req=='multimodeOrder'){
					$.dialog.alert_e('多模序号格式错误');
				}else if(req=='life'){
					$.dialog.alert_e('模具寿命格式错误');
				}else if(req=='addlife'){
					$.dialog.alert_e('增加寿命格式错误');
				}else if(req=='usedlife'){
					$.dialog.alert_e('可用寿命格式错误');
				}else if(req=='openCost'){
					$.dialog.alert_e('开模费格式错误');
				}
			},
			error:function(){
				$.dialog.alert_e('error');
			}
		});
	}
	
</script>




<body>
	<div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<!--按钮部分-->
	 	<div class="toolbar">
	 		<ul>
	 			<li class="fl"><input type="button" class="saveBtn" value="保存" onclick="saveProcess()"> </li>
	 			<li class="fl"><input type="button" class="backBtn" value="返回" onclick="window.history.go(-1)"></li>
	 			<div class="cf"></div>
	 		</ul>
	 	</div>
	 	<!--按钮部分 end-->

		<form id="myform" method="post" action="" onsubmit="return checkdata()">
		 	<!--主体部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">新增模具</div>		 		
		 		<div class="createdList" style="height:80%;">
		 			<!--12-->
		 			<ul class="wordul">
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">模具分类：</div>
							<div class="wordnameinput fl">
							<input type="text" value="" id="mouldStyle" name="mouldStyle" readonly="readonly"> 
							<input type="hidden" value="" id="mouldStyleId" name="mouldStyleId" > 
							</div>
							 <div class="fl">
							 <img src="${ctx}/img/sousuo.png" onclick="selectcuspro(3)">
							 </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">模具编码：</div>
							<div class="wordnameinput fl">
							<input type="text" value="" id="mouldCode" name="mouldCode"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">模具名称：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="mouldName" name="mouldName" >
							 </div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">模具条码：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="barcode" name="barcode"> 
							</div>
							
							<div class="cf"></div> 
		 				</li>
			 				
			 				
			 			<li class="wordli fl">
							<div class="wordname fl">多模序号：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="multimodeOrder" name="multimodeOrder">
							</div>
							<div class="cf"></div> 
		 				</li>
		 					
		 			
		 					<li class="wordli fl">
							<div class="wordname fl">骆氏号：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="cdefine1" name="cdefine1">
							</div>
							<div class="cf"></div> 
		 				</li>
		 			
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">零件号：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="partNumber" name="partNumber">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">零件名称：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="partName" name="partName">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">模比：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="mouldRatio" name="mouldRatio">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				
		 				<!-- 
		 				 -->
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">型腔号：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="cavity" name="cavity">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">档案位置：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="fileLocation" name="fileLocation">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">模具库位：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="position" name="position">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">置料工装：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="placingTooling" name="placingTooling">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">顶出工装：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="preTooling" name="preTooling">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">模具流向：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="mouldFlow" name="mouldFlow">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 			
		 			<li class="wordli fl">
							<div class="wordname fl">模具入库时间：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="storageTime" name="storageTime" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">模具资料编号：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="dataCode" name="dataCode">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">目前所在部门：</div>
							<div class="wordnameinput fl">
								<input type="hidden" value="" id="currentDeptGid" name="currentDeptGid">
								<input type="text" value="" id="currentDeptName" name="currentDeptName" readonly="readonly">
							</div>
							<div class="fl">
							 <img src="${ctx}/img/sousuo.png" onclick="selectCurrentDept()">
							 </div>
							<div class="cf"></div> 
		 				</li> 
		 				
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">目前状态：</div>
							<div class="wordnameinput fl">
							<select id="mouldstatus" name="mouldstatus">
									<option value="0">正常</option>
									<option value="1">维修</option>
									<option value="2">报废</option>
									<option value="3">试模</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="notes" name="notes">
							</div>
							<div class="cf"></div> 
		 				</li>
		 			
		 				<li class="wordli fl">
							<div class="wordname fl">开模时间：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="mouldBeginTime" name="mouldBeginTime" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"> 
							</div>
							<div class="cf"></div> 
		 				</li> 
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">材质：</div>
							<div class="wordnameinput fl">
							<input type="text" value="" id="texture" name="texture"> 
							</div>
							<div class="cf"></div> 
		 				</li> 
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">尺寸：</div>
							<div class="wordnameinput fl">
							<input type="text" value="" id="size" name="size" >
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">模具成本：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="cost" name="cost">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">加工单位：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="processingUnit" name="processingUnit">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">报废时间：</div>
							<div class="wordnameinput fl">
							<input type="text" value="" id="mouldScrapTime" name="mouldScrapTime" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">报废原因：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="mouldScrapReason" name="mouldScrapReason">
							</div>
							<div class="cf"></div> 
		 				</li>
		 			
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				
		 					
		 				<li class="wordli fl">
							<div class="wordname fl">模具寿命：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="life" name="life">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">增加寿命：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="addlife" name="addlife">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">已用寿命：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="usedlife" name="usedlife">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">可用寿命：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="canuselife" name="canuselife">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">模具出库时间：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="outTime" name="outTime" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">客户编码：</div>
							<div class="wordnameinput fl">
								<input type="hidden" value="" id="cutomerGid" name="cutomerGid">
								<input type="text" value="" id="cutomerName" name="cutomerName" readonly="readonly">
							</div>
							<div class="fl">
							 <img src="${ctx}/img/sousuo.png" onclick="selectCustomer()">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">供应商编码：</div>
							<div class="wordnameinput fl">
							<input type="hidden" value="" id="providerGid" name="providerGid">
							<input type="text" value="" id="providerName" name="providerName" readonly="readonly">	
							</div>
							<div class="fl">
							 <img src="${ctx}/img/sousuo.png" onclick="selectProviderName()">
							</div>
							
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">开模费：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="openCost" name="openCost">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">是否返还：</div>
							<div class="wordnameinput fl">
								<select id="isreturn" name="isreturn">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">返还时间：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="returnTime" name="returnTime"  readonly="readonly"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">是否分摊结束：</div>
							<div class="wordnameinput fl">
								<select id="isShareOrver" name="isShareOrver">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">分摊结束时间：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="shareOrverTime" name="shareOrverTime" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			
		 			<ul class="wordul">
		 				
		 				<li class="wordli fl">
							<div class="wordname fl">供货单位：</div>
							<div class="wordnameinput fl">
								<input type="text" value="" id="supplyunit" name="supplyunit">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				
		 				<div class="cf"></div> 
		 			</ul>
		 			
		 			<div style="height: 30px;"></div>
		 		</div>		 		
		 	</div>
		 	<!--主体部分 end-->	
	 	</form>
	 	
	</div>
</body>
</html>