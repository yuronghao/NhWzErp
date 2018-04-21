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

$(function(){
	$("#costItemCode   option[value='${costItem.costItemGid}']").prop("selected","selected");
	$("#rdStyleGid   option[value='${costItem.rdStyleGid}']").prop("selected","selected");
	$("#sourceMode   option[value='${costItem.sourceMode}']").prop("selected","selected");
})

function saveProcess(){
	$.ajax({
		data: $("#myform").serialize(),
		type: 'POST',
		url: '${ctx}/wms/costItem_updatecostItemSourceSet.emi',
		success: function(req){
			if(req=='success'){
				$.dialog.alert_s('保存成功',function(){
					location.href="${ctx}/wms/costItem_costItemSourceSetList.emi";
				});
			}else if(req=='costItemCode'){
				$.dialog.alert_e('成本项目编码不得为空');
			}else if(req=='rdStyleGid'){
				$.dialog.alert_e('类别不得为空');
			}else if(req=='rdStyleGids'){
				$.dialog.alert_e('类别不可选');
			}else if(req=='depGid'){
				$.dialog.alert_e('部门不得为空');
			}else if(req=='subjectCode'){
				$.dialog.alert_e('科目编码不得为空');
			}else if(req=='subjectCodes'){
				$.dialog.alert_e('科目编码不可填');
			}else if(req=='sourceMode'){
				$.dialog.alert_e('来源设置方式不得为空');
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


function showRdStyle(){
	$.dialog({ 
	drag: true,
	lock: false,
	resize: false,
	title:'选择部门',
    width: '1000px',
	height: '500px',
	zIndex:3000,
	content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.RDSTYLE%>&&showTree=0',
	okVal:"确定",
	ok:function(){
		var returnArray = this.content.window.jsonArray;
		var rdStyleGid = "";
		var rdStyleName = "";
		for(var i=0;i<returnArray.length;i++){
			rdStyleName+=returnArray[i].crdName+",";
			rdStyleGid+=returnArray[i].gid+",";
		}
		if(rdStyleGid.length>0){
			rdStyleGid = rdStyleGid.substring(0, rdStyleGid.length-1);
			rdStyleName = rdStyleName.substring(0, rdStyleName.length-1);
		}
		
		$("#rdStyleName").val(rdStyleName);
		$("#rdStyleGid").val(rdStyleGid);
	},
	cancelVal:"关闭",
	cancel:true
});	
}

</script>
<body>
	<form action="" method="post"  class="myform" id="myform" onsubmit="return checkdata()">
		<input type="hidden" value="${costItem.gid }" name="costItemId" id="costItemId">
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
	 		<div class="tabletitle">修改成本项目取数</div>		 		
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
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>来源设置方式：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 					<select style="width:260px;height:25px;line-height: 25px;" id="sourceMode" name="sourceMode">
	 						<c:forEach items="${pAttributes }" var="item">	
								<option  value="${item.gid}" selected="selected">${item.name}</option>   //执行这个方法
							</c:forEach>
	 					</select>
						</div>
							<div class="cf"></div> 
		 			</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;"></span>出入库类别：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
		 					<textarea name="rdStyleName" id="rdStyleName"  style="width:260px; height: 80px;resize: none;" readonly="readonly" onclick="showRdStyle();">${costItem.rdStyleName }</textarea>
		                    <input type="hidden" name="rdStyleGid" id="rdStyleGid" value="${costItem.rdStyleGid }">
		 				</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;"></span>科目编码：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="subjectCode" name="subjectCode" class="toDealInput"  style="width:260px;height:25px;line-height: 25px;" value="${costItem.subjectCode }">
						</div>
							<div class="cf"></div> 
		 			</li>
		 			<li>
						<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;"></span>部门：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="depName" name="depName" class="toDealInput"  style="width:260px;height:25px;line-height: 25px;" value="${costItem.depName}" onclick="fn_chooseDEPT()" readOnly>
							<input type="hidden" id="depGid" name="depGid" value="${costItem.depGid }"> 
						</div>
							<div class="cf"></div> 
		 			</li>
		 			<li>
						<div style="width:45%;text-align:right;" class="fl">备注：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
							<input type="text" id="note" name="note"  style="width:260px;height:25px;line-height: 25px;" value="${costItem.notes }">
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