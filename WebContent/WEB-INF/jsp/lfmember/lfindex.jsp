<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript">
				function gopage(type){
					if(type==1){
						window.location.href="${ctx}/lfmember/lfmember_showConsumption.emi";
					}
					if(type==2){
						window.location.href="${ctx}/lfmember/lfmember_showRecharge.emi";
					}
					if(type==3){
						window.location.href="${ctx}/lfmember/lfmember_memberlist.emi";
					}
					if(type==4){
						window.location.href="${ctx}/lfmember/lfmember_consumptionList.emi";
					}
					if(type==5){
						window.location.href="${ctx}/lfmember/lfmember_rechargeList.emi";
					}
				}
		</script>
		<style>
			*{font-size: 12px;
			font-family: "微软雅黑";
			list-style: none;
			margin: auto;
			}
			.fl{float: left;}
			.fr{float: right;}
			.cl{clear: both;}
			.center{
				position: absolute;
				left: 50%;
				top: 50%;
				width:645px;
				margin-left:-325px;
				margin-top:-225px;
				}
			.i-c-item-outer{
				width:200px;
				height: 200px;
				margin-left:10px;
				margin-top:10px;
				text-align: center;
				color: #fff;
				font-size: 20px;
				}
			.fixie{
				width:0;
				height:100%;
				display:inline-block;
				vertical-align:middle;
				}
		</style>
	</head>
	<body style="background-color:#fff;">
		<div class="center">
			<div class="i-c-item-outer fl" style="width:307px;background-color: #e8b875;" onclick="gopage(1)">
				<span class="fixie"></span>
				消费
			</div>
			<div class="i-c-item-outer fl" style="width:307px;background-color: #baa1e2;" onclick="gopage(2)">
				<span class="fixie"></span>
				充值
			</div>
			<div class="i-c-item-outer fl" style="background-color: #6581c3;" onclick="gopage(3)">
				<span class="fixie"></span>
				会员列表
			</div>
			<div class="i-c-item-outer fl" style="background-color: #f97b39;" onclick="gopage(4)">
				<span class="fixie"></span>
				消费记录
			</div>
			<div class="i-c-item-outer fl" style="background-color: #68bcff;" onclick="gopage(5)">
				<span class="fixie"></span>
				充值记录
			</div>
			<div class="cl"></div>
		</div>
	</body>
</html>

