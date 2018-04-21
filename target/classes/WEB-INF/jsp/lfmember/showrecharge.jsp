<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri = "/struts-tags" prefix = "s" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>充值</title>
		<script type="text/javascript" >
		window.onload = function () {
			$("#cardno").focus();
		}
		$('#cardno').bind('keyup', function(event) {
			if (event.keyCode == "13") {
				//回车执行查询
				swingcard();
			}
		});
		function cardnofocus(){
			$("#cardno").val("");
			$("#cardno").focus();
		}
		function phonefocus(){
			$("#phome").val("");
			$("#phome").focus();
		}
		function numberfocus(){
			$("#number").val("");
			$("#number").focus();
		}
		function querybynumber(){
			if(jQuery.trim($("#number").val())==''){
				$.dialog.alert_e("请输入卡编号",numberfocus);
				return false;
			}
			$.ajax({
				data:{number:jQuery.trim($("#number").val())},
				type: 'POST',
				url: '${ctx}/lfmember/lfmember_getMemberByNumber.emi',
				dataType:'json',
				success: function(req){
					if(req!=null){
						$("#cardno").val(req.cardno);
						$("#name").val(req.name);
						$("#number").val(req.number);
						$("#phone").val(req.phone);
						$("#blance").val(req.blance);
						$("#memberid").val(req.id);
						if(req.state==0){
							$("#cardstate").attr("color","green");
							$("#cardstate").text("正常");
						}
						if(req.state==1){
							$("#cardstate").text("暂停");
						}
						if(req.state==2){
							$("#cardstate").attr("color","red");
							$("#cardstate").text("挂失");
						}
					}else{
						$.dialog.alert_e("不存在此卡编号会员,请重新查询",numberfocus);
					}
				}
				
			});
			
		}
		function querybyphone(){
			if(jQuery.trim($("#phone").val())==''){
				$.dialog.alert_e("请输入手机号",phonefocus);
				return false;
			}
			$("#memberid").val("");
			$.ajax({
				data:{phone:jQuery.trim($("#phone").val())},
				type: 'POST',
				url: '${ctx}/lfmember/lfmember_getMemberByPhone.emi',
				dataType:'json',
				success: function(req){
					if(req!=null){
						$("#cardno").val(req.cardno);
						$("#name").val(req.name);
						$("#number").val(req.number);
						$("#phone").val(req.phone);
						$("#memberid").val(req.id);
						if(req.state==0){
							$("#cardstate").attr("color","green");
							$("#cardstate").text("正常");
						}
						if(req.state==1){
							$("#cardstate").text("暂停");
						}
						if(req.state==2){
							$("#cardstate").attr("color","red");
							$("#cardstate").text("挂失");
						}
					}else{
						$.dialog.alert_e("不存在此手机号会员,请重新刷卡",phonefocus);
					}
				}
				
			});
		}
			function swingcard(){
				if($("#cardno").val()==''){
					$.dialog.alert_e("请刷卡",cardnofocus);
					return;
				}
				$("#memberid").val("");
				$.ajax({
					data:{cardno:$("#cardno").val()},
					type: 'POST',
					url: '${ctx}/lfmember/lfmember_getMemberByCardno.emi',
					dataType:'json',
					success: function(req){
						if(req!=null){
							$("#name").val(req.name);
							$("#phone").val(req.phone);
							$("#number").val(req.number);
							$("#memberid").val(req.id);
							if(req.state==0){
								$("#cardstate").attr("color","green");
								$("#cardstate").text("正常");
							}
							if(req.state==1){
								$("#cardstate").text("暂停");
							}
							if(req.state==2){
								$("#cardstate").attr("color","red");
								$("#cardstate").text("挂失");
							}
						}else{
							$.dialog.alert_e("不存在此卡号会员,请重新刷卡",cardnofocus);
						}
					}
					
				});
			}
			function recharge(){
				var state=$("#state").val();
				if(state!=0){
					$.dialog.alert_e("该卡状态不正常，不能充值");
					return  false;
				}
				var mid=$("#memberid").val();
				if(mid<0||mid==''){
					$.dialog.alert_e("请刷有效卡");
					return  false;
				}
				var charge=$("#charge").val();
				if(isNaN(charge)){
					$.dialog.alert_e("请正确填写充值金额");
					return  false;
				}
				if(charge<=0){
					$.dialog.alert_e("请正确填写充值金额");
					return  false;
				}
				$("#submitform").submit();
			}
		</script>
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
		.ipt_only{background-color: #eee;
			border: 1px #ccc solid;
			width: 100%;height: 35px;line-height: 35px;
		}
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
	</style>
	</head>
	<body style="background-color:#fff;">
	<form  id="submitform" method="post" action="${ctx}/lfmember/lfmember_addRecharge.emi">
		<div class="Entirety">
			<div class="Info">
				<div class="Info_head">
					<span style="padding-left: 10px;">会员充值</span>
				</div>
				<div>
					<div style="width:50%;margin:0 auto;">
						<ul>
							<li class="fl li_text">请刷会员卡号：</li>
							<li class="fl li_ipt">
								<input class="ipt" type="text" name="recharge.cardno" id="cardno" onchange="swingcard()" />
								<input type="hidden" name="recharge.memberid" id="memberid" value="0" />
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">卡编号：</li>
							<li class="fl li_ipt">
								<input class="ipt" type="text"  name="recharge.number" id="number" />
							</li>
							<li class="fl"><input type="button" style="margin-left:5px;padding:5px 20px;" value="查询" onclick="querybynumber()"></li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">姓名：</li>
							<li class="fl li_ipt">
								<input class="ipt_only" type="text" readonly="readonly" name="recharge.name" id="name" />
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">手机号：</li>
							<li class="fl li_ipt">
								<input class="ipt" type="text"  name="recharge.phone" id="phone" />
							</li>
							<li class="fl"><input type="button" style="margin-left:5px;padding:5px 20px;" value="查询" onclick="querybyphone()"></li>
							<div class="cl"></div>
						</ul>
							<ul>
							<li class="fl li_text">充值金额：</li>
							<li class="fl li_ipt">
								<input class="ipt" type="text" name="recharge.charge" id="charge" value="0.00" />
							</li>
							<div class="cl"></div>
						</ul>
						<ul>
							<li class="fl li_text">卡状态：</li>
							<li class="fl li_ipt">
														<input type="hidden" id="state" value="">
								<font id="cardstate">无</font>
							</li>
							<div class="cl"></div>
						</ul>
					</div>
				</div>
			</div>
			<div style="height: 10px;"></div>
			<div style="height: 10px;"></div>
			<div class="Info" style="border: none;">
				<ul>
					<li class="fl" style="width: 48%;text-align: right;">
					<s:token ></s:token > 
						<input type="button" class="btn" value="充值"  onclick="recharge()"/>
					</li>
					<li class="fr" style="width: 48%;text-align: left;">
						<input type="button" class="btn" value="返回" onclick="window.history.go(-1)" />
					</li>
					<div class="cl"></div>
				</ul>
			</div>
		</div>
		</form>
	</body>
</html>
