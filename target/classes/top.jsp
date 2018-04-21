<!DOCTYPE html >
<%@page import="com.emi.sys.util.SysPropertites"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 
<c:set var="cg" value='<%=SysPropertites.getWmsCfg("taskFlowType.cg") %>'></c:set>
<c:set var="xs" value='<%=SysPropertites.getWmsCfg("taskFlowType.xs") %>'></c:set>
<c:set var="sc" value='<%=SysPropertites.getWmsCfg("taskFlowType.sc") %>'></c:set>
<c:set var="db" value='<%=SysPropertites.getWmsCfg("taskFlowType.db") %>'></c:set>
<c:set var="cgdd" value='<%=SysPropertites.getWmsCfg("taskStepCode.cgdd") %>'></c:set>
<c:set var="cgdh" value='<%=SysPropertites.getWmsCfg("taskStepCode.cgdh") %>'></c:set>
<c:set var="cgrk" value='<%=SysPropertites.getWmsCfg("taskStepCode.cgrk") %>'></c:set>
<c:set var="xsdd" value='<%=SysPropertites.getWmsCfg("taskStepCode.xsdd") %>'></c:set>
<c:set var="xsfh" value='<%=SysPropertites.getWmsCfg("taskStepCode.xsfh") %>'></c:set>
<c:set var="xsrk" value='<%=SysPropertites.getWmsCfg("taskStepCode.xsrk") %>'></c:set>
<c:set var="scdd" value='<%=SysPropertites.getWmsCfg("taskStepCode.scdd") %>'></c:set>
<c:set var="clck" value='<%=SysPropertites.getWmsCfg("taskStepCode.clck") %>'></c:set>
<c:set var="cprk" value='<%=SysPropertites.getWmsCfg("taskStepCode.cprk") %>'></c:set>
<c:set var="dbd" value='<%=SysPropertites.getWmsCfg("taskStepCode.dbd") %>'></c:set>
<c:set var="dbck" value='<%=SysPropertites.getWmsCfg("taskStepCode.dbck") %>'></c:set>
<c:set var="dbrk" value='<%=SysPropertites.getWmsCfg("taskStepCode.dbrk") %>'></c:set>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>一米移动ERP</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx }/emi.ico"/>
	<link rel="stylesheet" type="text/css" href="${ctx }/css/common/index_rm.css">
	<script type="text/javascript">
	</script>
	<!--自动调整宽度\高度-->
	<script type="text/javascript">

	//退出系统
	function exit(){
		parent.window.location.href="${ctx }/login_logout.emi";
	}
	
	//修改密码
	function modifyPassword(){
		var pwdWin = $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'修改密码',
		    width: '400px',
			height: '180px',
			content: 'url:login_toModifyPassword.emi',
			ok:function(){
				var oldPwd = this.content.document.getElementById('oldPassword').value;
				var newPwd = this.content.document.getElementById('newPassword').value;
				var confirmPwd = this.content.document.getElementById('confirmPassword').value;
				if(oldPwd==''){
					$.dialog.alert("请填写原密码");
				}else if(newPwd==''){
					$.dialog.alert("请填写新密码");
				}else if(confirmPwd!=newPwd){
					$.dialog.alert("新密码与确认密码不一致");
				}
				$.ajax({
					type: 'POST',
					url:'${ctx}/login_modifyPassword.emi',
					data:{oldPassword:oldPwd,newPassword:newPwd},
					dataType:'text',
					success: function (mes){
						var data = eval("("+mes+")");
						if(data.success=='0'){
							$.dialog.alert("修改失败");
							return false;
						}
						if(data.success=='1'){
							$.dialog.alert("修改成功,请重新登录",function(){parent.window.location.href="${ctx}/login_logout.emi";});
							return true;
						}
						if(data.success=='2'){
							$.dialog.alert("原密码错误");
							return false;
						}
					}
				});
				
			},
			cancel:true
		});	
	}
	
	//添加收藏
	function addFav(rightId,rightName){
		$.ajax({
			data:{rightId:rightId,rightName:rightName},
			type: 'POST',
			url:'${ctx}/right_addToMyFavorite.emi',
			dataType:'text',
			success: function (mes){
				if(mes=='0'){
					$.dialog.alert("收藏失败");
				}
				if(mes=='1'){
					$.dialog({
						title: '消息',
						time: 1,
					    content: '收藏成功'
					});
					parent.window.document.getElementById("leftMain").src="${ctx }/login_showLeft.emi";
				}
			}
		});
	}
		
	</script>
	 
</head>
<body id="mainbody">
	<div id="mainContainer" >
		<!--logo部分-->
		<div class="header" >
			<div class="logo fl" id="logoimg"><img src="${ctx }/images/common/people.png"></div>
			<div class="name fl" id="sysname">&nbsp;一米移动ERP</div>
			
			
			<div class="fl currentUser" style="margin-top: 35px;position:fixed;margin-left:78%;height:40px;">
				<div id="currentUser" class="fl" style="color: white;">当前用户：${Person.cPerName }&nbsp;( ${UserName} )</div>
				<div id="key" class="fl" onclick="modifyPassword();" title="修改密码">修改密码</div> 
				<div id="exit" class="fl" onclick="exit();" title="退出">退出</div>
				<div class="cf"></div>
			</div>
			<div class="cf"></div>
		</div>
		<!--导航部分
		<div class="navbar">
			<div class="fl company_name" id="compname">&nbsp;</div>
			<div class="nav" id="rightDiv">
			</div>
		</div>-->
	</div>
	

</body>
</html>