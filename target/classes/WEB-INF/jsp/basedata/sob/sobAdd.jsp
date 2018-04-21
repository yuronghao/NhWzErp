<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>属性方案</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript"	src="${ctx }/scripts/emiwms.js"></script> 
<script type="text/javascript">
		function checkForm()
		{
			if(document.getElementById('sobCode').value==""){
				$.dialog.alert_w("帐套编码不能为空!");
			  	return false;
			}
			else if(document.getElementById('sobName').value==""){
				$.dialog.alert_w("帐套名称不能为空!");
			  	return false;
			}
			else if(document.getElementById('orgId').value==""){
				$.dialog.alert_w("组织不能为空!");
			  	return false;
			}
			else if(document.getElementById('userName').value==""){
				$.dialog.alert_w("用户名称不能为空!");
			  	return false;
			}
			else if(document.getElementById('passWord').value==""){
				$.dialog.alert_w("用户密码不能为空!");
			  	return false;
			}else{
				return true;
			}
		}
		
		//按钮事件
		$(function(){
			var toDeal=$('.toDealInput');
			inputCannotUse(toDeal);
			var isSave=true;//新增保存、修改保存标识
			//新增 
			$('#addBtn').click(function(){					
				$('.wordul').children().children().children('input').not('.state').val("");//清理文本框内容
				//$('tr').children().not($('.NO')).children().val("");//表格内容全清
		        //getOrder();	
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				var states = $(":radio");
				//$(states[0]).removeAttr("disabled");
				//$(states[1]).removeAttr("disabled");
				$(states[0]).removeAttr("onclick");
				$(states[1]).removeAttr("onclick");
				$(states[0]).attr("checked","checked");
				$("#orgName").attr("onclick","selectorg()");
				//部门事件，操作分录
				//$('#departmentName').addClass("departmentName");
				//$('#customerName').addClass("customerName");
				//$('#personName').addClass("personName");
				//日期 事件
				
				//$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				//$('.planDG').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				/* $.ajax({
					url:'${ctx}/wms/sale_getBillId.emi',
					type:"post",
					dataType:"json",
					success:function(da){					
						$('#billDate').val(da.nowDate);
						$('#recordDate').val(da.nowDate);
						$('#billCode').val(da.billId);
						$('#recordPersonName').val(da.recordname);
						$('#recordPersonUid').val(da.gRecordPersonUid);
					}				
				}); */
				addBtn();

			});
			//修改
			$('#revBtn').click(function(){	
					isSave=false;	
					var toDeal=$('.toDealInput');
					inputCanUse(toDeal);			
					var states = $(":radio");
					$(states[0]).removeAttr("onclick");
					$(states[1]).removeAttr("onclick");
					$("#orgName").attr("onclick","selectorg()");
					revBtn();
			});	
			//保存
			$('#saveBtn').click(function(){		
				if(checkForm()){
				if(isSave){//新增保存			
					$.ajax({
						url:'${ctx}/wms/sob_addsob.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){				
							if(da=='success'){
								window.location.href = '${ctx}/wms/sob_toAddsob.emi';
							}					
						}				
					});
				}else{//修改保存			
					$.ajax({
						url:'${ctx}/wms/sob_updatesob.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){					
							if(da=='success'){
								window.location.href = '${ctx}/wms/sob_toAddsob.emi';
							}					
						}				
					});			
				}
				}
				
			});
		});
		
		//删除订单
		function deletesob(gid) {
			if (confirm("是否确定删除?")) {
				$.ajax({
							url : 'order!delSaleOrder.action',
							type : 'post',
							data : {gId : gid},
							dataType : 'json',
							success : function(data) {						
								if (data.success == "1") {
									window.location.href = "${ctx}/order!toEditSaleOrder.action";
								} else {
									alert(data.msg);
								}
							},
							error : function() {

								alert("服务器异常");
							}
						});
			}
		}
		
		//放弃
		function giveup() {
			location.href = '${ctx}/wms/sob_toAddsob.emi';
		}
		
		function setEnable(enable,id){
			$.ajax({
				url:"${ctx}/wms/sob_sobEnable.emi?enable="+enable+"&id="+id,
				type:"post",
				dataType:"json",
				success:function(da){
					
				}
			});
		}
		
		
		function selectorg(){
			    $.dialog({ 
				drag: false,
				lock: true,
				resize: false,
				title:'选择组织',
			    width: '800px',
				height: '400px',
				zIndex:2000,
				content: 'url:${ctx}/wms/sob_orgSelect.emi',
				okVal:"确定",
				ok:function(){
					var id = this.content.document.getElementById("id").value;
					var name = this.content.document.getElementById("name").value;
					document.getElementById('orgName').value=name;
					document.getElementById('orgId').value=id;
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}
		
		function getsoblist(){
			window.location.href = '${ctx}/wms/sob_getsoblist.emi';
		}
	</script>
</head>
<body>
<form id="myform">
<input type="hidden" id="sobgid" name="sobgid" value="${sob['sobgid']}">
<input type="hidden" id="ymusergid" name="ymusergid" value="${sob['ymusergid']}">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<li class="fl"><input type="button" class="addBtn" value="新增" id="addBtn"> </li>
		 			<li class="fl"><input type="button" class="editBtn" value="修改" id="revBtn"> </li>
		 			<%-- <li class="fl"><input type="button" class="delBtn" value="删除" id="delBtn" onclick="deletesob('${sob['sobgid']}')"> </li> --%> 
		 			<li class="fl"><input type="button" class="saveBtn" value="保存" id="saveBtn"> </li>
		 			<li class="fl"><input type="button" class="giveupBtn" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>
		 			<li class="fl"><input type="button" class="listBtn" value="列表" id="tableBtn" onclick="getsoblist()"></li>
		 			<!-- <li class="fl">
	 				单据翻页begin
		 			<div id="emi_page_turning" ></div>
		 			<script>
		 				$(function() {
		 					/*
							 * 初始化单据翻页 
							 * @param action 表单请求地址，不需要在此url中传gid，如有其他参数，可以传
							 * @param table_name 表名
							 * @param id_column gid字段名(一般是gid不用改)
							 * @param gid	当前数据的gid值
							 * @param condition	数据的过滤条件sql
							 * @param [el_id_column] 【非必填】后台接取gid使用的参数名(默认叫gid，如有需要可以修改)
							 * @param [div_id] 【非必填】翻页按钮所在的div的id(默认叫emi_page_turning,如有需要可以修改)
							 */
		 					initPageTurning('${ctx }/wms/sob_toAddsob.emi','MES_WM_AccountingInform','gid',"${sob['sobgid']}",
		 							'','sobgid');
		 				});
		 			</script>
		 			单据翻页end
	 			</li> -->
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--主体部分-->
		 	<div class="creattable" style="">
		 		<div class="tabletitle">帐套信息</div>	
		 		<div class="xz_attribute">
		 			<ul class="wordul">
		 				<li style="margin-top:10px;">
		 					<div class="fl div_text">帐套编码：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="sobCode" name="sobCode" value="${sob['sobCode']}" class="toDealInput" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">账套名称：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="sobName" name="sobName" value="${sob['sobName']}" class="toDealInput" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">所属组织：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="orgName" name="orgName" value="${sob['orgName']}" class="toDealInput">
		 						<input type="hidden" id="orgId" name="orgId" value="${sob['aaorggid']}">
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">是否启用：</div>
		 					<div class="fl div_ipt">
		 					<input type="radio" class="state" name="state" value="0" onclick="setEnable(0,'${sob['sobgid']}')" <c:if test="${sob['state']!=1}">checked="checked"</c:if>>是
							<input type="radio" class="state" name="state" value="1" onclick="setEnable(1,'${sob['sobgid']}')" <c:if test="${sob['state']==1}">checked="checked"</c:if>>否
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li style="margin-top:10px;">
		 					<div class="fl div_text">用户编码：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="userCode" name="userCode" value="${sob['userCode']}" class="toDealInput" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li>
		 					<div class="fl div_text">用户名称：</div>
		 					<div class="fl div_ipt">
		 						<input type="text" id="userName" name="userName" value="${sob['userName']}" class="toDealInput" >
		 					</div>
		 					<div class="cf"></div>
		 				</li>
		 				<li style="margin-top:10px;">
		 					<div class="fl div_text">密码：</div>
		 					<div class="fl div_ipt">
		 						<input type="password" id="passWord" name="passWord" value="${sob['passWord']}" class="toDealInput" >
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
</html>