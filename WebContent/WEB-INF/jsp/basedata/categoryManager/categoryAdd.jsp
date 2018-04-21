<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<title>Insert title here</title>
<script type="text/javascript">
/* 		$(function(){
			$('.saveBtn').click(function(){
				if(checkdata()){
					$.ajax({
		 				  data: $("#myform").serialize(),
		 				  type: 'POST',
		 				  url: '${ctx}/wms/prty_AddAA_Category.emi?name='+$("#name").val(),
		 				  dataType:'text',
		 				  success: function(req){
		 					  if(req=='success'){
		 						  $.dialog.alert_s('保存成功',function(){location.href="${ctx}/wms/prty_getAA_CategoryList.emi";});
		 					  }
		 					  else if(req=='fail'){
		 						  $.dialog.alert_e('该类别名称已存在！');
		 					  }else{
		 						  $.dialog.alert_e('保存失败');
		 					  }
		 				  },
		 				  error:function(){
		 					  $.dialog.alert_e("服务异常");
		 				  }
		 			});
				}
				
			});
		}); */
		function checkdata()
		{
			if(document.getElementById('name').value==""){
				$.dialog.alert_w("类别名称不能为空!");
			  	return false;
			}
			else
			{ 
				
				return true;
			}
		}
	</script>
</head>

<body>
		<div class="EMonecontent"><form name="myform" id="myform" method="post" action="" onsubmit="return checkdata()">
		<input type="hidden"  value="${bean.gid}" id="gid" name="gid" />
		
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
<!-- 		 	<div class="toolbar">
		 		<ul>
		 			<li class="fl"><a href="javascript:history.go(-1);"><input type="button" class="backBtn" value="返回"></a></li>
		 			<li class="fl"><input type="button" class="saveBtn" value="保存"> </li>
		 			<div class="cf"></div>
		 		</ul>
		 	</div>  -->
		 	<!--按钮部分 end-->
		 	<!--主体部分-->
		 	<div class="creattable">
		 		<div class="tabletitle">属性类别</div>		 		
		 		<div class="xz_attribute" style="border:0">
		 			<ul>
		 				<li>
		 					<div style="width:35%;text-align:right;" class="fl">类别名称：</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<input type="text" style="width:260px;height:25px;line-height: 25px;" value="${bean.name}" id="name" name="name"  />
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 			</ul>
		 			<div style="height:10px;"></div>
		 		</div>
		 	</div>
		 	</form>
		 	<!--主体部分 end-->	
		</div>
	</body>
</html>