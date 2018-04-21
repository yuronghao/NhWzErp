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
	.wordnameinput{width:56%;}
</style>
</head>

<script type="text/javascript">

function checkdata()
{
	if(document.getElementById('percode').value==""){
		$.dialog.alert_w("人员编码不能为空!");
	  	return false;
	}else if(document.getElementById('pername').value==""){
		$.dialog.alert_w("人员名称不能为空!");
	  	return false;
	}
	else
	{ 
		return true;
	}
}
	
	//选择部门
	function selectDepartmet(){
		
	    $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'选择部门',
		    width: '400px',
			height: '300px',
			zIndex:2000,
			content: 'url:${ctx}/wms/org_getDepartmentTreeHelp.emi',
			okVal:"确定",
			ok:function(){
				var id = this.content.document.getElementById("id").value;
				var name = this.content.document.getElementById("name").value;
				
				document.getElementById('depparentname').value=name;
				document.getElementById('depparentuid').value=id;
				
			},
			cancelVal:"关闭",
			cancel:true
		});	

	}
	
	
</script>




<body>
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>

		<form action="" id="myform">
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="mainaddword" style="min-height: 350px; border:solid 1px white">
	 			<ul>
	 				<li>
	 					<div class="fl mainaddword_name">人员编码：</div>
	 					<div class="fl mainaddword_word">
	 						<input type="hidden" class="addwordinput" id="pergid" name="pergid" value="${map.gid }">
	 						<input type="text" class="addwordinput" id="percode" name="percode" value="${map.percode }">
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">人员名称：</div>
	 					<div class="fl mainaddword_word"><input type="text" class="addwordinput" id="pername" name="pername" value="${map.pername }"></div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">性别：</div>
	 					<div class="fl mainaddword_word">
	 						<select id="persex" name="persex">
	 							<c:if test="${map.persex==0 }">
	 								<option value="0" selected="selected">男</option>
	 								<option value="1">女</option>
	 							</c:if>
	 							
	 							<c:if test="${map.persex==1 }">
	 								<option value="0">男</option>
	 								<option value="1" selected="selected">女</option>
	 							</c:if>

	 						</select>
	 					</div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">部门名称：</div>
	 					<div class="fl mainaddword_word">
	 						<input type="hidden" id="depparentuid" name="depparentuid" value="${map.depGid }" class="addwordinput">
		 					<input type="text" id="depparentname" name="depparentname" value="${map.depName }" class="addwordinput">
	 					</div>
	 					<img src="${ctx}/img/sousuo.png" onclick="selectDepartmet()">
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">出生年月：</div>
	 					<div class="fl mainaddword_word"><input type="text" value="${fn:substring(map.perbirthday,0,10)} " class="addwordinput" id="perbirthday" name="perbirthday" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"></div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">入职日期：</div>
	 					<div class="fl mainaddword_word"><input type="text" value="${fn:substring(map.begindate,0,10)} " class="addwordinput" id="begindate" name="begindate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"> </div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">离职日期：</div>
	 					<div class="fl mainaddword_word"><input type="text" value="${fn:substring(map.enddate,0,10)}" class="addwordinput" id="enddate" name="enddate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"> </div>
	 					<div class="cf"></div>
	 				</li>
	 				<li>
	 					<div class="fl mainaddword_name">对应用户：</div>
	 					<div class="fl mainaddword_word"><input type="text" class="addwordinput" id="useruid" name="useruid"></div>
	 					<div class="cf"></div>
	 				</li>

	 				<li>
	 					<div class="fl mainaddword_name">备注：</div>
	 					<div class="fl mainaddword_word"><input type="text" value="${map.notes }" class="addwordinput" id="notes" name="notes"> </div>
	 					<div class="cf"></div>
	 				</li>
	 			</ul>
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	 	</form>
	</div>
</body>
</html>