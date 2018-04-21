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
	<script type="text/javascript" src="<%=contextPath %>/scripts/ajaxfileupload.js"></script>
	<style type="text/css">
		.file{ 
			position:absolute; 
			top:0; 
			left:2px; 
			height:24px; 
			filter:alpha(opacity:0);
			opacity: 0;
			width:290px;
		}
		.file-box{ 
			position:relative;
			width:640px
		}  
		.txt{ 
			height:22px; 
			border:1px solid #cdcdcd;
			 min-width:180px;
		} 
		.importBtn{
			margin-left:5px;
		}
	</style>
</head>
<script type="text/javascript">

$(function() {

	var width = $('#createwords').width();
	$('#content').css('width', width + 10 + "px");

});

function doUpload(fileId,type){
	 var filevalue = document.getElementById(fileId).value;
		if (filevalue == "") {
			alert("请选择要导入的文件！");
			return;
		} else {
			var fileext = filevalue.substring(filevalue.lastIndexOf(".") + 1);
			
			if (fileext != "xls" && fileext != "xlsx") {
				alert("请选择正确格式的文件：Excel文件！");
				document.getElementById(fileId).value = "";
				return;
			}else {
				showTips('正在导入...');
				 $.ajaxFileUpload({
					   url: "${ctx}/wms/basepd_importRoute.emi?type="+type,
					   secureuri:false,//一般设置为false
		               fileElementId:fileId,//文件上传空间的id属性  <input type="file" id="file" name="file" />
		               dataType: 'text',//返回值类型 一般设置为json
		              // beforeSend:loading,
		               success: function(req){
		            	   if(req=='success'){
		            		   $.dialog.alert_s( "导入完成！");
		            	   }else if(req=='empty'){
		            		   $.dialog.alert_e( "未知文件！");
		            	   }else{
		            		   $.dialog.alert_s( "导入失败,请检查表中数据是否正确！");
		            	   }
		            	   document.getElementById(fileId).value = "";
		            	   hideTips();
		            	  // frameElement.api.close();
					   },
					   error: function (e){
						   $.dialog.alert_s( "导入失败请检查表中数据是否正确！");
						   document.getElementById(fileId).value = "";
						   hideTips();
						   //frameElement.api.close();   
					   }
					} ); 

			}
		
		}
}
function getExcel(name){
	name=encodeURI(name);
	var fileName=encodeURI(name);
	window.location.href="${ctx}/commonfile_getExcelFile.emi?fileName="+fileName;
}
</script>
<body>
	<form action="${ctx }/wms/basepd_saveBaseRoute.emi" method="post"  class="myform" id="myform" enctype="multipart/form-data">
		<div style="height: 20px"></div>
		<div style="height: 20px;margin-left: 20px;">1、导入工序：</div>
		<div class="file-box" style="margin-left: 20px;">
			<input type='text' name='textfield' id='textfield' class='txt' style="width: 330px"/>
			<input type='button' class='chakanBtn' value='浏览...'  style="width: 70px;min-width:70px;"/> 
			<input type="file" name="file" class="file" id="file1" style="width: 420px;cursor: pointer;"
				onchange="document.getElementById('textfield').value=this.value" />
			<input type='button' value='导入' class="importBtn" onclick="doUpload('file1',0);" style="width: 70px;min-width:70px;"/>
			<input type='button' value='下载模板' class="exportBtn" onclick="getExcel('标准工艺导入模板');"style="width: 95px;min-width:70px;"/>
		</div>
		<div style="margin-top: 20px;margin-left: 20px;">2、导入BOM：</div>
		<div class="file-box" style="margin-left: 20px;">
			<input type='text' name='textfield' id='textfield2' class='txt' style="width: 330px"/>
			<input type='button' class='chakanBtn' value='浏览...'  style="width: 70px;min-width:70px;"/> 
			<input type="file" name="file" class="file" id="file2" style="width: 420px;cursor: pointer;"
				onchange="document.getElementById('textfield2').value=this.value" />
			<input type='button' value='导入' class="importBtn" onclick="doUpload('file2',1);" style="width: 70px;min-width:70px;"/> 
			<input type='button' value='下载模板' class="exportBtn" onclick="getExcel('BOM导入模板');"style="width: 95px;min-width:70px;"/>
		</div>
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