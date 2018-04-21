<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<script type="text/javascript" src="${ctx }/scripts/plugins/uploadify/jquery.uploadify.min.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/uploadify/uploadify.css">
<style type="text/css">
	#fileList{list-style-type: none;}
	#fileList li{
		line-height: 25px;
	}
	#fileList li:hover{
		background-color:#edf6fb;
	}
	#fileList li .lld{
		width: 96%;
		float: left;
	}
	img{
		cursor: pointer;
		margin-top:5px;
	}
</style>
</head>
<body style="width:99%;">
	<form action="" method="post" enctype="multipart/form-data" style="padding-left:1%;">
		<!-- <div id="queue"></div> -->
		<c:if test="${editable eq true}">
		<input id="uploadify" name="uploadify" type="file" multiple="true">
		<!-- <hr style="border:0.5px #ccc solid"> -->
		</c:if>
		<ul id="fileList" style="width: 100%;">
			<c:forEach var="file" items="${files }" varStatus="stat">
				<li >
					<div class="lld"><span class="file_idx" style="width:25px">${stat.count}、</span><a href="${ctx}/commonfile_download.action?fileId=${file.docid}">${file.docname }</a></div> 
					<div class="fl" >
					<c:if test="${editable eq true }">
						<img src="${ctx}/scripts/plugins/uploadify/close2.png" onclick="deleteFile('${file.docid}',this)"></img>
					</c:if>
					</div>
					<div class="cl"></div>
				</li>
			</c:forEach>
		</ul>
	</form>

	<script type="text/javascript">
	var file_len = '${fn:length(files)}'-0;
	//页面添加一行文件数据
	function addLi(fileId,fileName){
		var fileList = document.getElementById('fileList');
		var li= document.createElement("li");
		var txt = '<div class="lld"><span class="file_idx" style="width:25px">'+(file_len+1)+'、</span><a href="${ctx}/commonfile_download.action?fileId='+fileId+'">'+fileName+'</a></div>';
		txt += '<div class="fl">';
		if("${editable}"=="true"){
			txt += '<img src="${ctx}/scripts/plugins/uploadify/uploadify-cancel.png" onclick="deleteFile('+fileId+',this)"></img>';
		}
		txt += '</div><div class="cl"></div>';
		li.innerHTML=txt;
		fileList.appendChild(li);
		changeCount("${targetId}",1);
	}
	
	function changeCount(targetId,count){
		if(parent.parent.document.getElementById('${targetId}_hiddenCount')){
			var oldcount = parent.parent.document.getElementById('${targetId}_hiddenCount').value-0;
			parent.parent.document.getElementById('${targetId}_hiddenCount').value = oldcount + count;
		}
	}
	//删除文件
	function deleteFile(fileId,imgobj){
		$.dialog.confirm("确定删除？", function(){
			//执行删除,先删除数据再删除页面元素
			$.ajax({ 		
 				  data: {fileId:fileId,recordId:'${recordId}',tableName:'${tableName}',idColumnName:'${idColumnName}'},
 				  type: 'POST',
 				  url: '${ctx}/commonfile_deleteFile.action',
 				  dataType:'json',
 				  success: function(req){
 					  if(req.success=='success'){
 						  var ul = document.getElementById('fileList');
 						  ul.removeChild(imgobj.parentNode.parentNode);
 						 changeCount("${targetId}",-1);
 						 //重置排序号
  						$(".file_idx").each(function(i){
 						   this.innerHTML=(i+1)+'、';
 						  file_len--;
 						});
 					  }else{
 						  $.dialog.alert_e('删除失败');
 					  }
 				  },
 				  error:function(){
 					  $.dialog.alert_e("服务异常");
 				  }
 			});
		}, function(){});
		
	}
	
	$(function() {
		if("${editable}"=="true"){
			var fileTypeExts = '${fileTypeExts}';
			if(fileTypeExts=='*.*' || fileTypeExts==''){
				//fileTypeExts = "*.rar;*.zip;*.doc;*.docx;*.xls;*.xlsx;*.txt;*.jpg;*.jpeg;*.png;*.gif;*.bmp;*.pdf";
			} 
			$('#uploadify').uploadify({
				'formData'     : {
					'tableName' : '${tableName}',
					'recordId'     : '${recordId}',
					'idColumnName'   : '${idColumnName}',
					'folderId':'${folderId}'
				},
				'swf'      : '${ctx }/scripts/plugins/uploadify/uploadify.swf',// 上传使用的 Flash  
				'buttonText': "${buttonText}",                 				// 按钮上的文字  
				'uploader' : '${ctx }/commonfile_upload.action',			// 服务器端处理地址  
				'fileObjName': 'uploadify',								// 上传参数名称 后台action里面的属性uploadify
				'fileTypeExts' : fileTypeExts,//可上传的文件类型  如：*.gif; *.jpg; *.png
				'width':85,
				'auto':true,//是否自动上传
				'removeTimeout':1,//上传完成隐藏延时
				'fileSizeLimit':'5MB',//文件大小限制 如5MB ,1GB
				'uploadLimit':50,//最大上传文件数量，如果达到或超出此限制将会触发onUploadError事件
				'onUploadSuccess' : function(file, data, response) {
					if(response){
						var res = eval("("+data+")");
						if(res.success=="success"){
							//上传成功，页面增加一行
							addLi(res.file.docid,res.file.docname);
						}
					}
		            //alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
		        }
			});
		}
	});
	</script>
</body>
</html>