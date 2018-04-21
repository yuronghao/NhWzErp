<!DOCTYPE html >
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
</head>
<script type="text/javascript">

function saveProcess(){
	$.ajax({
		data: $("#myform").serialize(),
		type: 'POST',
		url: '${ctx}/wms/group_addgroup.emi',
		success: function(req){
			if(req=='success'){
				$.dialog.alert_s('保存成功',function(){
					location.href="${ctx}/wms/group_getgroupList.emi";
				});
				
			}else if(req=='error'){
				$.dialog.alert_e('保存失败');
			}
		},
		error:function(){
			$.dialog.alert_e('error');
		}
	});
}

function selectdep(){
	var pwdWin = $.dialog({ 
		drag: true,
		lock: true,
		resize: false,
		title:'选择部门',
	    width: '400px',
		height: '400px',
		zIndex:3004,
		content: 'url:${ctx}/wms/basepd_getselectdep.emi',
		okVal:"确定",
		ok:function(){
			var name = this.content.document.getElementById("name").value;
			var id = this.content.document.getElementById("id").value;
			document.getElementById('depName').value=name;
			document.getElementById('depUid').value=id;
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
	 	<!--主体部分-->
	 	<div class="creattable">
	 		<div class="tabletitle">新增原因</div>		 		
	 		<div class="xz_attribute">
	 			<ul>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl"><span  style="color: red;">*</span>原因名称：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 					<select id="reasonGid" name="reasonGid">
								<option value="">请选择</option>
					                 <c:forEach var="pe" items="${aareasonlist}">
						             <option  value="${pe['gid']}" >${pe['reasonname']}</option>
					                 </c:forEach>
					                 </select>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div style="width:45%;text-align:right;" class="fl">数量：</div>
	 					<div style="width:50%;text-align:left;" class="fl">
	 						<input type="text" style="width:260px;height:25px;line-height: 25px;" name="num" id="num" maxlength="50">
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