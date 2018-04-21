<!DOCTYPE html >
<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%><html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content=""/>
<meta name="keywords" content=""/>
<meta name="robots" content="index,follow" />
	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
	<script type="text/javascript" src="<%=contextPath %>/scripts/processDesign/basepd_attribute.js"></script>
</head>
<script type="text/javascript">

function saveProcess(){
	$.ajax({
		data: $("#myform").serialize(),
		type: 'POST',
		url: '${ctx}/wms/costItem_addcostItemAllotRate.emi',
		success: function(req){
			if(req=='success'){
				$.dialog.alert_s('保存成功',function(){
					location.href="${ctx}/wms/costItem_costItemAllotRateList.emi";
				});
				
			}else if(req=='costItemCode'){
				$.dialog.alert_e('成本项目编码不得为空');
			}else if(req=='goodsId'){
				$.dialog.alert_e('产品不得为空');
			}else if(req=='priorattributeName'){
				$.dialog.alert_e('自由项1不得为空');
			}else if(req=='depGid'){
				$.dialog.alert_e('部门不得为空');
			}else if(req=='ratio'){
				$.dialog.alert_e('系数不得为空');
			}else if(req=='ratios'){
				$.dialog.alert_e('系数请输入数字');
			}else if(req=='noNull'){
				$.dialog.alert_e('工序已存在');
			}else if(req=='error'){
				$.dialog.alert_e('保存失败');
			}
		},
		error:function(){
			$.dialog.alert_e('error');
		}
	});
}

function selectpriorattribute(){
	var pwdWin = $.dialog({ 
		drag: true,
		lock: true,
		resize: false,
		title:'选择成本项目类型',
	    width: '800px',
		height: '400px',
		zIndex:3004,
		content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PRIORATTRIBUTE%>&multi=0&showTree=0',
		okVal:"确定",
		ok:function(){
			var usercheck = this.content.window.jsonArray;
			document.getElementById('priorattributeName').value=usercheck[0].name;
			document.getElementById('priorattributeGid').value=usercheck[0].gid;
		},
		cancelVal:"关闭",
		cancel:true
	});	
}

function fn_chooseGoods(){
		$.dialog({ 
		drag: true,
		lock: false,
		resize: false,
		title:'选择产品',
	    width: '1000px',
		height: '500px',
		zIndex:3000,
		content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.GOODS%>',
		okVal:"确定",
		ok:function(){
			var returnArray = this.content.window.jsonArray;
			if(returnArray.length>1){
				$.dialog.alert_e('只能选择一个产品！');
			}else{
				for(var i=0;i<returnArray.length;i++){
					 $("#goodsName").val(returnArray[i].goodsname);
					$("#goodsId").val(returnArray[i].gid); 
					initGoodsSelects(returnArray[i].gid,returnArray[i].goodscode,returnArray[i].goodscode);
				}
			}
		},
		cancelVal:"关闭",
		cancel:true
	});	
	}
	
function fn_chooseDEPT(){
	$.dialog({ 
	drag: true,
	lock: false,
	resize: false,
	title:'选择部门',
    width: '1000px',
	height: '500px',
	zIndex:3000,
	content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.DEPT%>&&showList=0',
	okVal:"确定",
	ok:function(){
		var returnArray = this.content.window.jsonArray;
		for(var i=0;i<returnArray.length;i++){
			$("#depName").val(returnArray[i].name);
			$("#depGid").val(returnArray[i].id);
		}
	},
	cancelVal:"关闭",
	cancel:true
});	
}

</script>
<body>
	<form action="" method="post"  class="myform" id="myform" onsubmit="return checkdata()">
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
	 	<!--主体部分-->
	 	<div class="creattable">
	 		<div class="tabletitle">新增成本项目分配率</div>		 		
	 		<div class="xz_attribute">
	 			<ul>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>成本项目：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<select style="width:260px;height:25px;line-height: 25px;" name="costItemCode" id="costItemCode" maxlength="20">
	 						<c:forEach items="${costItems }" var="item">	
								<option  value="${item.gid}" selected="selected">${item.name}</option>   //执行这个方法
							</c:forEach>
	 						</select>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>产品：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 					
	 						<input type="text" class="inputxt" style="width:260px;height:25px;line-height: 25px;" readOnly name="goodsName" id="goodsName" maxlength="50"  onclick="fn_chooseGoods()"/>
	 						<input type="hidden" class="inputxt" name="goodsId" id="goodsId" maxlength="50" />
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>自由项1：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 					<select style="width:260px;height:25px;line-height: 25px;" name="priorattributeName" id="priorattributeName"></select>
						</div>
							<div class="cf"></div> 
		 			</li>
		 			<li>
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>部门：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="depName" name="depName" class="toDealInput"  style="width:260px;height:25px;line-height: 25px;" onclick="fn_chooseDEPT()" readOnly>
							<input type="hidden" id="depGid" name="depGid"> 
						</div>
							<div class="cf"></div> 
		 			</li>
		 			<li>
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>系数：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="ratio" name="ratio" class="toDealInput"  style="width:260px;height:25px;line-height: 25px;"   >
						</div>
							<div class="cf"></div> 
		 			</li>
		 			<li>
						<div style="width:45%;text-align:right;" class="fl">备注：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="note" name="note"  style="width:260px;height:25px;line-height: 25px;">
						</div>
							<div class="cf"></div> 
		 			</li>
	 			</ul>
	 			<div style="height:10px;"></div>
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	</div>
		
	</form>
              
</body>
<script type="text/javascript">


function checkdata(){
	
	for(var i=0;i<2;i++){
	      if($('.inputxt').eq(i).val()==''){
	    	  $.dialog.alert('内容填写不完整');
	    	  return false;
	      }
	}
	return true;
}


</script>
</html>