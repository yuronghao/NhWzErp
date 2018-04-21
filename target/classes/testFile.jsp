
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">

</head>
<script type="text/javascript">



$(function() {
	emiUpload.uploadify({
		showType:'div',			//显示方式 'div'：嵌入在页面 ; 'dialog':弹出框   默认dialog
		targetId:'file_upload_div',	//【必填】 目标元素id（需要在页面哪个div里面显示插件）
		width : "100%",			//宽度 默认800px【showType是div时建议100%】
		height : "350px",		//高度  默认350px
		tableName : 'announcement',	//【必填】表名  
		idColumnName : 'announcementid',//【必填】id字段名  
		recordId  : '10019',		//【必填】数据id 
		fileTypeExts: '*.*',	//可上传的文件类型,默认'*.*'  例： 全部类型->'*.*'   指定类型->'*.gif; *.jpg; *.png'  
		editable:true,			//是否可编辑 默认为true
	});
	
	emiUpload.uploadify({
		showType:'dialog',	
		targetId:'file_upload_btn',
		width : "800px",			
		height : "380px",		
		tableName : 'table_name',	
		idColumnName : 'columnid',
		recordId  : '102',		
		editable:true,			
		reloadParent: true,		//是否刷新父页面（仅对dialog可用）
	});
});
</script>
<body>
<form action="${ctx }/os/notice_getNoticeList.action" name="myform" id="myform" method="post">
	一、div方式<input type="button" id="btn" value="获取数量" onclick="alert(emiUpload.fileCount('file_upload_div'))">
	(获取数量：一般只在新增页面需要调用，用来判断是否有文件，从而确定doc字段的值) <br>
	<div id="file_upload_div"></div> 
	二、弹出窗口<br>
	<input type="button" id="file_upload_btn" value="打开上传界面"> 
	
	<br>
</form>	

</body>
</html>
