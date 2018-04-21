<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri = "/struts-tags" prefix = "s" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html>
  <head>
    
    <title>新增修改会员</title>
	
	<style>
	*{font-family: "微软雅黑";font-size: 16px;list-style: none;}
		.Entirety{
			width: 100%;
			height: 100%;
		}
		.Info{
			width:92%;
			margin-left: 4%;
			border: 1px #d8e7f6 solid;
		}
		.Info_head{
			width: 100%;
			height: 35px;
			line-height: 35px;
			background-color: #f5faff;
			border-bottom: 1px #D8E7F6 solid;
			color: #4d9af2;
			font-weight: bold;
		}
		ul li{height: 35px;line-height: 35px;}
		.li_text{
			width: 27%;
			text-align: right;
			color:#4d4d4d;
		}
		.li_ipt{width: 50%;text-align: left;}
		.ipt{
			border: 1px #ccc solid;
			width: 100%;height: 35px;line-height: 35px;
		}
		.btn{
			padding: 5px 25px;
			border:1px solid #E1E1E1;
			background-color: #f5faff;
			border-radius: 3px;
		}
		.cl{clear: both;}
		.fl{float: left;}
		.fr{float: right;}
		.ipt_only{background-color: #eee;
			border: 1px #ccc solid;
			width: 100%;height: 35px;line-height: 35px;
		}
	</style>
	<script type="text/javascript" >
		window.onload = function () {
			$("#cardno").focus();
		}
		function cardnofocus(){
			$("#cardno").focus();
		}
		function phonefocus(){
			$("#phone").focus();
		}
		function numberfocus(){
			$("#number").focus();
		}
		function checkisexists(type){
			var datavalue=null;
			if(type==1){
				datavalue=$("#cardno").val();
			}else if(type==3){
				datavalue=$("#number").val();
			}else{
				datavalue=$("#phone").val();
			}
			var dataparm={};
			if('${member}'.length>0){
				dataparm={datatype:type,datavalue:datavalue,memberid:'${member.id}'};
			}else{
				dataparm={datatype:type,datavalue:datavalue};
			}
			$.ajax({
				data:dataparm,
				type: 'POST',
				url: '${ctx}/lfmember/lfmember_checkMemberIsExists.emi',
				dataType:'text',
				success: function(req){
					if(req=='error'){
						if(type==1){
							$.dialog.alert_e("该卡号已存在",cardnofocus);
						}
						if(type==2){
							$.dialog.alert_e("该手机号已存在",phonefocus);
						}
						if(type==3){
							$.dialog.alert_e("该编号已存在",numberfocus);
						}
					}
				}
				
			});
		}
	 	function formsubmit(){
	 		 var name=jQuery.trim($("#name").val());
	 		 if(name==''){
	 				 $.dialog.alert_e("姓名不能为空");
	 				 return false;
	 		 }
	 		 var phone=jQuery.trim($("#phone").val());
	 		 if(phone==''){
	 				 $.dialog.alert_e("手机号不能为空");
	 				 return false;
	 		 }
	 		 var cardno=jQuery.trim($("#cardno").val());
	 		 if(cardno==''){
	 				 $.dialog.alert_e("请绑定会员卡");
	 				 return false;
	 		 }
	 		 var cardno=jQuery.trim($("#cardno").val());
	 		 if(cardno==''){
	 				 $.dialog.alert_e("请绑定会员卡");
	 				 return false;
	 		 }
	 		 var blance=jQuery.trim($("#blance").val());
	 		 if(isNaN(blance)){
	 			 $.dialog.alert_e("请正确填写金额");
 				 return false;
	 		 }
	 		 $(".stateradio").each(function(){
	 			 if($(this).prop("checked")){
	 				 $("#state").val($(this).val());
	 			 }
	 		 });
	 		$("#submitform").submit();
	 	}
	</script>
  </head>
  
  <body style="background-color:#fff;">
  <form action="${ctx}/lfmember/lfmember_addmodifyMember.emi" method="post" id="submitform">
		<div class="Entirety">
			<div class="Info">
				<div class="Info_head">
					<span style="padding-left: 10px;">会员基本资料信息</span>
				</div>
				<div>
					<div style="width:50%;margin:0 auto;">
						<ul>
							<li class="fl li_text">卡号：</li>
							<li class="fl li_ipt">
								<input class="ipt" type="text" name="lfmember.cardno" id="cardno" <c:if test="${member!=null }"> readonly</c:if> value="${member.cardno}"  onblur="checkisexists(1)"    />
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">编号：</li>
							<li class="fl li_ipt">
								<input class="ipt" type="text" name="lfmember.number" id="number" <c:if test="${member!=null }"> readonly</c:if> value="${member.number}"  onblur="checkisexists(3)"    />
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">姓名：</li>
							<li class="fl li_ipt">
							<c:if test="${member!=null }"><input type="hidden" name="lfmember.id" value="${member.id }"></c:if>
								<input class="ipt" type="text" name="lfmember.name" id="name" value="${member.name}" />
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">手机号：</li>
							<li class="fl li_ipt">
								<input class="ipt" type="text" name="lfmember.phone" id="phone" value="${member.phone}" onblur="checkisexists(2)"  />
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">余额：</li>
							<li class="fl li_ipt">
							<c:if test="${member!=null }">
								<input class="ipt_only" type="text" name="lfmember.blance" id="blance"  readonly value="${member.blance}"/>
							</c:if>
							<c:if test="${member==null }">
								<input class="ipt_only" type="text" name="lfmember.blance" id="blance" readonly  value="0.00"/>
							</c:if>
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">状态：</li>
							<li class="fl li_ipt">
							<c:if test="${member==null }">
								<input type="radio" class="stateradio" name="stateradio" value="0" checked>正常
								<input type="radio" class="stateradio" name="stateradio" value="1">停用
								<input type="radio" class="stateradio" name="stateradio" value="2">挂失
								<input type="hidden" id="state" name="lfmember.state" value="0"> 
							</c:if>
							<c:if test="${member!=null }">
								<input type="radio" class="stateradio" name="stateradio" value="0" <c:if test='${member.state==0 }'>checked</c:if>>正常
								<input type="radio" class="stateradio" name="stateradio" value="1" <c:if test='${member.state==1 }'>checked</c:if>>停用
								<input type="radio" class="stateradio" name="stateradio" value="2" <c:if test='${member.state==2 }'>checked</c:if>>挂失
							<input type="hidden" id="state" name="lfmember.state" value="${member.state }"> 
							</c:if>
							</li>
							<div class="cl"></div>
						</ul>
					</div>
				</div>
			</div>
			<div style="height: 10px;"></div>
			<div class="Info" style="border: none;">
				<ul>
					<li class="fl" style="width: 48%;text-align: right;">
					<s:token ></s:token > 
						<input type="button" class="btn" value="保存" onclick="formsubmit()" />
					</li>
					<li class="fr" style="width: 48%;text-align: left;">
						<input type="button" class="btn" value="取消" onclick="window.history.go(-1)"/>
					</li>
					<div class="cl"></div>
				</ul>
			</div>
		</div>
		</form>
  </body>
</html>
