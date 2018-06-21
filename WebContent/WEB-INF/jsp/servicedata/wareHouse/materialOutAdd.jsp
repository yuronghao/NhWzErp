<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>生产订单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<script type="text/javascript"	src="${ctx }/scripts/emiwms.js"></script> 
<script type="text/javascript" src="<%=contextPath %>/scripts/plugins/jquery.numeral.js"></script>
<script type="text/javascript">
		function checkForm()
		{
			if(document.getElementById('billDate').value==""){
				$.dialog.alert_w("单据日期不能为空!");
			  	return false;
			}
		   if(document.getElementById('depName').value==""){
				$.dialog.alert_w("部门不能为空!");
			  	return false;
			} 
		   if(document.getElementById('whUid').value==""){
				$.dialog.alert_w("仓库不能为空!");
			  	return false;
			} 
			var trs=$('.serialTr');
			if(trs.length<=0)
				{
					$.dialog.alert_w("材料出库明细不能为空!");
				  	return false;
				}
			
			for(var i=0;i<trs.length;i++){
			      if($('.numric').eq(i).val()==''){
			    	  $.dialog.alert('主数量不能为空');
			    	  return false;
			      }
			      if($('.jjjnumric').eq(i).val()==''){
			    	  $.dialog.alert('货位号不能为空');
			    	  return false;
			      }
			      if(typeof($('.jnumric').eq(i).val())!= "undefined"  && $('.jnumric').eq(i).val()!='' )
			       {
			    	  console.log($('.jjnumric').eq(i))
			    	  if(typeof($('.jjnumric').eq(i).val())!= "undefined" && $('.jjnumric').eq(i).val()==''){
				    	  $.dialog.alert('辅单位存在,辅助数量不能为空');
				    	  return false;
				      }
			      }
			}
			
			var batchs=$('.batchInput');
			for(var i=0;i<batchs.length;i++){
				
				if(batchs.eq(i).attr("isbatch")==1 && batchs.eq(i).val()==""){
					$.dialog.alert('存在批次不能为空的记录！');
			    	return false;
				}
			}
			
			return true;
		}
		
		//按钮事件
		$(function(){
			var toDeal=$('.toDealInput');
			inputCannotUse(toDeal);
			var isSave=true;//新增保存、修改保存标识
			//新增 
			$('#addBtn').click(function(){					
				$('.wordul').children().children().children('input').val("");//清理文本框内容
				$('tr').not($('.NO')).remove();//表格内容全清
				$('.freeset').remove();
				$('.dwrow').remove();
				$('.addrow').show();
				$('#genRoute').hide();
				$('#genTask').hide();
				var toDeal=$('.toDealInput');
				inputCanUse(toDeal);	
				$("select").removeAttr("disabled");
				$("select option").removeAttr("selected");
				$(".addrow").attr("onclick","insertRow()");
				$("#depName").attr("onclick","selectdep()");
				$("#whName").attr("onclick","selectmanager()");
				$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				$("#customerName").attr("onclick","selectcustomer()");
                $("#zhuangtai").parent().parent().parent().remove();
				$.ajax({
					data:{billType:'0050'},
					url:'${ctx}/wms/saleOrder_getBillId.emi',
					type:"post",
					dataType:"json",
					success:function(da){					
						$('#billDate').val(da.nowDate);
						$('#recordDate').val(da.nowDate);
					
						$('#billCode').val(da.billId);
						$('#recordPersonName').val(da.recordname);
						$('#recordPersonUid').val(da.gRecordPersonUid);
					}				
				});
				addBtn();

			});
			//修改
			$('#revBtn').click(function(){
			    var status = '${saleOutWarehouse['status']}';
			    if(status >0  && status <3){
                    $.dialog.alert_e("已提交审核,无法修改");
                    return false;
                }
				if($('#produceWarehousegid').val()==""){
					return false;
				}
				else{
					isSave=false;	
					getSerial();
					var toDeal=$('.toDealInput');
					inputCanUse(toDeal);	
					$('.freeset').remove();
					$('.dwrow').remove();
					$('.delrow').show();
					$('.addrow').show();
					$('#genRoute').hide();
					$('#genTask').hide();
					$("select").removeAttr("disabled");
					/* $("select option").removeAttr("selected"); */
					$(".delrow").addClass("delno");
					$(".addrow").attr("onclick","insertRow()");
					$("#depName").attr("onclick","selectdep()");
					$("#whName").attr("onclick","selectmanager()");
					$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
					$('.startDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
					$('.endDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
					$(".jjjnumric").attr("onclick","clickFlag(this)");
					$("#customerName").attr("onclick","selectcustomer()");
                    $("#businessTypeUid").attr("disabled","disabled");
					revBtn();
				}
			});	
			//保存
			$('#saveBtn').click(function(){		
				if(checkForm()){
				if(isSave){//新增保存			
					$.ajax({
						url:'${ctx}/wms/wareHouse_addMaterialOut.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){
							var obj=eval("("+da+")");
							if(obj.success==1){
								window.location.href = '${ctx}/wms/wareHouse_toAddMaterialOut.emi';
							}else{
								$.dialog.alert_e(obj.failInfor);
							}					
						}				
					});
				}else{//修改保存			
					$.ajax({
						url:'${ctx}/wms/wareHouse_updateMaterialOutWarehouse.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){					
							var obj=eval("("+da+")");
							if(obj.success==1){
								window.location.href = '${ctx}/wms/wareHouse_toAddMaterialOut.emi';
							}else{
								$.dialog.alert_e(obj.failInfor);
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
							url : '${ctx}/wms/wareHouse_deleteMaterialOutWarehouse.emi',
							type : 'post',
							data : {gid : gid},
							success : function(da) {	
								
								var obj=eval("("+da+")");
								if(obj.success==1){
									window.location.href = '${ctx}/wms/wareHouse_toAddMaterialOut.emi';
								}else{
									$.dialog.alert_e(obj.failInfor);
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
			location.href = '${ctx}/wms/wareHouse_toAddMaterialOut.emi';
		}
		
		
		function getprocurearrivallist(){
			window.location.href = '${ctx}/wms/wareHouse_materialOutWarehouseList.emi';
		}
		
		function getSerial(){
			
			var trs=$('.serialTr');
			 for(var i=0;i<trs.length;i++){
				//trs.eq(i).children().first().children().val(i+1);
				//trs.eq(i).children('.trid').children().val(i+1);
				trs.eq(i).children('.gid').children().attr('id','gid'+(i+1));
				trs.eq(i).children('.goodsUid').children().attr('id','goodsUid'+(i+1));
				trs.eq(i).children('.goodsCode').children().attr('id','goodsCode'+(i+1));
				trs.eq(i).children('.goodsName').children().attr('id','goodsName'+(i+1));
				trs.eq(i).children('.goodsstandard').children().attr('id','goodsstandard'+(i+1));
				trs.eq(i).children('.mainUnit').children().attr('id','mainUnit'+(i+1));
				trs.eq(i).children('.mainNumber').children().attr('id','mainNumber'+(i+1));
//				trs.eq(i).children('.assistUnit').children().attr('id','assistUnit'+(i+1));
//				trs.eq(i).children('.assistNumber').children().attr('id','assistNumber'+(i+1));
				trs.eq(i).children('.goodsAllocationName').children().attr('id','goodsAllocationName'+(i+1));
				trs.eq(i).children('.goodsAllocationUid').children().attr('id','goodsAllocationUid'+(i+1));
				trs.eq(i).children('.batch').children().attr('id','batch'+(i+1));
//				trs.eq(i).children('.barCode').children().attr('id','barCode'+(i+1));
				trs.eq(i).children('.note').children().attr('id','note'+(i+1));
//				trs.eq(i).children('.assistUnitcode').children().attr('id','assistUnitcode'+(i+1));
				
			} 
		}
		
		function insertRow(){
		    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'物料档案',
			    width: '1100px',
				height: '590px',
				content: 'url:${ctx}/wms/goods_getGoodsHelp.emi',
				okVal:"确定",
				ok:function(){
					
					var chek = $('.goodsSelected:checked',this.content.document.getElementById("rtframe").contentDocument); 
					for (var i = 0; i < chek.length; i++) {
						var strs='<tr class="serialTr">'+
						'<td><div class="delrow delno" name = "deleteButton" value=""  style="margin-left:15px;" gid=""></div></td>'+
						'<td class="gid" style="display:none"><input type="hidden" id="" name="gid" class="listword" value=""></td>'+
						'<td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="'+chek.eq(i).val()+'"></td>'+
						'<td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="'+chek.eq(i).attr("goodsCode")+'" readonly="readonly"></td>'+
						'<td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="'+chek.eq(i).attr("goodsName")+'" readonly="readonly"></td>'+
						'<td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="'+chek.eq(i).attr("goodsstandard")+'" readonly="readonly"></td>'+
						'<td class="mainUnit"><input type="text" id="" name="mainUnit" class="listword" value="'+chek.eq(i).attr("unitName")+'" readonly="readonly"></td>'+
						'<td class="mainNumber"><input type="text" id="" name="mainNumber" class="listword numric" value="" onchange="changeFlag(this)"></td>';
						
//						'<td class="assistUnit"><input type="text" id="" name="assistUnit" class="listword" value="'+chek.eq(i).attr("cstComUnitName")+'" readonly="readonly"></td>';

						var binvbach=chek.eq(i).attr("binvbach");
						
//						if(chek.eq(i).attr("cstcomunitcode")!='')
//							{
//							strs+='<td class="assistUnitcode" style="display:none"><input type="text" id="" name="assistUnitcode" class="listword jnumric" value="'+chek.eq(i).attr("cstcomunitcode")+'" readonly="readonly"></td>'+
//							'<td class="assistNumber"><input type="text" id="" name="assistNumber" class="listword jjnumric" value="" onchange="changeFlag(this)"></td>';
//							}
//						else
//							{
//							strs+='<td class="assistUnitcode" style="display:none"><input type="text" id="" name="assistUnitcode" class="listword" value="'+chek.eq(i).attr("cstcomunitcode")+'" readonly="readonly"></td>'+
//							'<td class="assistNumber"><input type="text" id="" name="assistNumber" class="listword" value="" readonly="readonly"></td>';
//							}
					strs+='<td class="goodsAllocationName"><input type="text" id="" name="goodsAllocationName" class="listword jjjnumric"  value="'+golbalalloctionname+'" readonly="readonly" onclick="clickFlag(this)"></td>'+
						'<td class="goodsAllocationUid" style="display:none"><input type="text" id="" name="goodsAllocationUid" class="listword " value="'+golbalalloctionid+'"  readonly="readonly" onclick="clickFlag(this)"></td>'+
						
						'<td class="batch"><input type="text" id="" name="batch" class="listword batchInput" value="" isbatch="'+binvbach+'"></td>'+
						
//						'<td class="barCode"><input type="text" id="" name="barCode" class="listword "  readonly="readonly"></td>'+
						'<td class="note"><input type="text" id="" name="note" class="listword" value="" ></td>'+
//						'<td class="produceCode"><input type="text" id="" name="produceCode" class="listword "  readonly="readonly"></td>'+
//						'<td class="goodName"><input type="text" id="" name="goodName" class="listword" value="" readonly="readonly"></td>'+
						'</tr>';
					
						$("#contr").append(strs);
						getSerial();
						$(".numric").numeral(6);
						setIsbatch();
					}
				},
				cancelVal:"关闭",
				cancel:true
				
				});
			
		}


		//参照

		
		
		function setIsbatch(){
			var batchs=$('.batchInput');

			for(var i=0;i<batchs.length;i++){
				if(batchs.eq(i).attr("isbatch")!=1){
					batchs.eq(i).attr('readonly','readonly');
				}
				if(batchs.eq(i).attr("isbatch")==1){

					batchs.eq(i).attr('onclick','getBatch(this)');
				}
			}
		}
		
		/*获得批次信息*/
		function getBatch(obj){
			
			if($('#badge').val()==0){//红字时不弹出批次框
				return;
			}
			
			if($(obj).parent().prev().children().val()=="" || $(obj).parent().prev().children().val() == null){
				$.dialog.alert_w("货位号不能为空！");
				return;
			}

            var goodsUid = $(obj).parent().parent().find("input[name='goodsUid']").val();
			var goodsAllocationUid=$(obj).parent().parent().find("input[name='goodsAllocationUid']").val();

			var pwdWin = $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'选择批次物料',
			    width: '800px',
				height: '400px',
				zIndex:4004,
				content: 'url:${ctx}/plugin_selectAllocationStockList.emi?goodsUid='+goodsUid+'&goodsAllocationUid='+goodsAllocationUid,
				okVal:"确定",
				ok:function(){
					var usercheck = $("input:checked",this.content.document);
					$(obj).val(usercheck.attr("batch"));
				},
				cancelVal:"关闭",
				cancel:true
			});
		}
		
		
		function clickFlag(obj)
		{
			var temp=$(obj).attr("id").substr(19);
			if($("#whUid").val()=="")
				{
				  $.dialog.alert('请选择仓库！');
				 return false;
				}
			else
				{
				 $.dialog({
						drag: true,
						lock: true,
						resize: false,
						title:'选择仓位号',
					    width: '1100px',
						height: '590px',
						content: 'url:${ctx}/wms/wareHouse_getGoodsAllocationHelp.emi?id='+$("#whUid").val(),
						okVal:"确定",
						ok:function(){
							
							var chek = $('.goodsSelected:checked',this.content.document);
                            if(chek.eq(0).val() != null && chek.eq(0).val() != "undefined"){
                                document.getElementById('goodsAllocationName'+temp).value=chek.eq(0).attr("allocationName");
                                document.getElementById('goodsAllocationUid'+temp).value=chek.eq(0).val();
                            }

						},
						cancelVal:"关闭",
						cancel:true
						});
				}
		}
		function changeFlag(obj)
		{
			if(isNaN(obj.value)){
				alert("请输入数字");
				obj.value="";
				return;
			}
			 var reg = /^\d+(?=\.{0,1}\d+$|$)/;
			 if(obj.value!=''){
				if($("#badge").val()=='0'){
					if(reg.test(obj.value))
						{
						obj.value=-obj.value;
						}
				}
				else
					{
						if(!reg.test(obj.value))
						{
							obj.value=-obj.value;
						}
					}
			}
		}
		function getSelect(obj)
		{
			
			var tem=document.getElementsByName("mainNumber");
//			var tems=document.getElementsByName("assistNumber");
			 var reg = /^\d+(?=\.{0,1}\d+$|$)/;
			for(var i=0;i<tem.length;i++)
			 {
				if(tem[i].value!=''){
				if(obj.value=='0'){
					if(reg.test(tem[i].value))
						{
							 tem[i].value=-tem[i].value;
						}
				}
				else
					{
						if(!reg.test(tem[i].value))
						{
							 tem[i].value=-tem[i].value;
						}
					}
				}
			 }

		}
 		$(function(){
			$('body').on('click','.delno',function(){
				var deleteGids = $('#deleteGids').val();
				var obj=$(this);
				$.dialog.confirm("确定是否删除？",function(){
					var delId = obj.attr("gid");

					if(delId !=null && delId!=''){
						deleteGids+=obj.attr("gid")+",";
					}
					$('#deleteGids').val(deleteGids);	
					obj.parent().parent().remove();},function(){});
			});
			
			$(".numric").numeral(6);
		});
		
		function printbarcode(){
			$.ajax({
				url:'${ctx}/wms/produceOrder_printbarcode.emi',
				type:"post",
				data:$('#myform').serialize(),
				success:function(da){				
					if(da=='success'){
						alert("打印成功");
					}					
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
	 	function selectcustomer(){
	 		$.dialog({ 
				drag: true,
				lock: false,
				resize: false,
				title:'选择客户',
			    width: '800px',
				height: '500px',
				zIndex:3000,
				content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.PROVIDERCUSTOMER%>&multi=0&showTree=0', 
				okVal:"确定",
				ok:function(){
					var returnArray = this.content.window.jsonArray;
					if(returnArray.length>0){
						$('#customerUid').val(returnArray[0].gid);
						$('#customerName').val(returnArray[0].pcname);
					}
				},
				cancelVal:"关闭",
				cancel:true
			});
	 	}
        var golbalalloctionname; //定义全局的默认货位名称
        var golbalalloctionid; //定义全局的默认货位名称
		function selectmanager(){
			var pwdWin = $.dialog({ 
				drag: true,
				lock: true,
				resize: false,
				title:'选择仓库',
			    width: '800px',
				height: '400px',
				zIndex:3004,
				content: 'url:${ctx}/plugin_selectMain.emi?treeType=<%=TreeType.WAREHOUSE%>&multi=0&showTree=0',
				okVal:"确定",
				ok:function(){
					$('.batchInput').val("");
					var usercheck = this.content.window.jsonArray;
					
					document.getElementById('whName').value=usercheck[0].whname;
					document.getElementById('whUid').value=usercheck[0].gid;
					 var temp=document.getElementsByName("goodsAllocationName");
					 var temps=document.getElementsByName("goodsAllocationUid");
					 for(var i=0;i<temp.length;i++)
						 {
						 temp[i].value='';
						 temps[i].value='';
						 }


                    //ajax传值
                    $.ajax({
                        data: {"whuid":usercheck[0].gid},
                        type: 'POST',
                        async: false,
                        url: '${ctx}/wms/wareHouse_getGolBalDefualtAllocation.emi',
                        success: function(req){
                            var jsonO =  eval('(' + req + ')');
                            if(jsonO.success=='1'){
                                // alert(jsonO.data.name);
                                // alert(jsonO.data.whuid);
                                golbalalloctionname =jsonO.data.name;
                                golbalalloctionid =jsonO.data.gid;
                            }else{
                                alert("系统异常");
                            }

                        }
                    });
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}
		
	</script>
</head>
<body style="background-color: #FFFFFF;">
<form id="myform" name="myform" action="" method="post">
<input type="hidden" name="time" id="time" value="${time }" />
<input type="hidden" id="materialOutgid" name="materialOutgid" value="${saleOutWarehouse['gid']}" >
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<li class="fl"><input type="button" class="btns" value="新增" id="addBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="修改" id="revBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="删除" id="delBtn" onclick="deletesob('${saleOutWarehouse['gid']}')"> </li>
		 			<li class="fl"><input type="button" class="btns" value="保存" id="saveBtn"> </li>
		 			<li class="fl"><input type="button" class="btns" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>
		 			<li class="fl" style="display:none"><input type="button" class="btns" value="审核"> </li>
			 		<li class="fl" style="display:none"><input type="button" class="btns" value="弃审"> </li>
		 			<li class="fl"><input type="button" class="btns" value="列表" id="tableBtn" onclick="getprocurearrivallist()"></li>
					<li class="fl"><input type="button" class="btns" value="参照" id="canzhao"></li>
		 			<li class="fl">
	 				<!-- 单据翻页begin -->
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
							 var cond = "";
		 					initPageTurning('${ctx }/wms/wareHouse_toAddMaterialOut.emi','WM_MaterialOut','gid',"${saleOutWarehouse['gid']}",
		 							cond,'materialOutgid');
		 				});
		 			</script>
		 			<!-- 单据翻页end -->
	 			</li>
	 			<!-- <li class="fl"><input type="button" class="btns" value="定位"> </li>
				<li class="fl"><input type="button" class="btns" value="打印"> </li> -->
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<!--表格部分-->
		 	<div class="creattable">
		 		<div class="tabletitle" style="margin-top:6px;">材料出库单</div>		 		
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">单据编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${saleOutWarehouse['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(saleOutWarehouse['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl">
							<input type="text" name="depName" id="depName" class="toDealInput" value="${department.depname}" >
	 						<input type="hidden" id="depUid" name="depUid" value="${saleOutWarehouse['departmentUid']}">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">仓库：</div>
							<div class="wordnameinput fl">
							<input type="text" name="whName" id="whName" class="toDealInput" value="${warehouse.whname}">
							<input type="hidden" value="${saleOutWarehouse['whUid']}" id="whUid" name="whUid"> 
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">
					<li class="wordli fl">
						<div class="wordname fl">出库标志：</div>
						<div class="wordnameinput fl">
							<select class="toDealSelect" id="badge" name="badge" onchange="getSelect(this)" disabled="disabled">
								<option value="1" <c:if test="${saleOutWarehouse['badge'] == 1}">selected="selected"</c:if>>蓝字</option>
								<option value="0" <c:if test="${saleOutWarehouse['badge'] == 0}">selected="selected"</c:if>>红字</option>
							</select>
						</div>
						<div class="cf"></div>
					  </li>
		 				<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl"><input type="text" value="${saleOutWarehouse['notes']}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>
                        <input type="hidden" name="status" value="${saleOutWarehouse['status']}"/>
                        <input  type="hidden" name="businessTypeUid2" value="${saleOutWarehouse.businessTypeUid}"/>

						<li class="wordli fl">
							<div class="wordname fl">类型：</div>
							<div class="wordnameinput fl">
								<select class="toDealSelect" id="businessTypeUid" name="businessTypeUid"  disabled="disabled">
									<option value="" >-- 请选择 --</option>
									<c:forEach items="${rdstylelist}" var="rdstyle" varStatus="status">
										<option value="${rdstyle.gid}" <c:if test="${saleOutWarehouse['businessTypeUid'] == rdstyle.gid}">selected="selected"</c:if>>${rdstyle.crdName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="cf"></div>
						</li>


						<c:if test="${sp == 1}">
							<li class="wordli fl">
								<div class="wordname fl">审批状态:</div>
								<div class="wordnameinput fl">
									<div class="wordnameinput fl">
										<c:if test="${saleOutWarehouse['status'] == 0}">
											<input style="color: #0e78c9" type="text" value="未审核(${followInfoMoving.userName})" id="zhuangtai" name="zhuangtai" class="" disabled>
										</c:if>
										<c:if test="${saleOutWarehouse['status'] == 1}">
											<input style="color: #0E2D5F" type="text" value="审核中(${followInfoMoving.userName})" id="zhuangtai" name="zhuangtai" class="" disabled>
										</c:if>
										<c:if test="${saleOutWarehouse['status'] == 2}">
											<input style="color: #00B83F" type="text" value="已通过(${followInfoMoving.userName})" id="zhuangtai" name="zhuangtai" class="" disabled>
										</c:if>
										<c:if test="${saleOutWarehouse['status'] == 3}">
											<input style="color: red" type="text" value="被驳回(${followInfoMoving.userName})" id="zhuangtai" name="zhuangtai" class="" disabled>
										</c:if>


									</div>
								</div>
								<div class="cf"></div>
							</li>
						</c:if>




		 			</ul>
		 			<!-- <ul class="wordul">
		 				
		 				<div class="cf"></div> 
		 			</ul> -->
		 			<div style="height: 30px;"></div>
		 			<!--end-->
		 			<div style="max-height:300px;overflow:auto;">
			 			<table>
				 			<tbody id="contr">
				 				<tr class="NO">
				 					<th style="width:50px;">操作</th>
				 					<th>物料编号</th>
				 					<th>物料名称</th>
				 					<th>规格型号</th>
				 					<th>主单位</th>
				 					<th>主数量</th>
				 					<%--<th>辅单位</th>--%>
				 					<%--<th>辅数量</th>--%>
				 					<th>货位号</th>
				 					<th>批次</th>
				 					<%--<th style="width: 12%">条形码</th>--%>
				 					<th>备注</th>
									<%--<th>生产订单号</th>--%>
									<%--<th>产品编号</th>--%>
				 				</tr>
				 			<c:forEach var="type" items="${saleOutWarehouseC}" varStatus="stat">
				 		    <tr class="serialTr">
				 		    <td><div class="delrow" name = "deleteButton"  style="margin-left:15px;float: left;display: none" title="删除" id="${type.gid}"></div>
				 		    </td>
				 		     <td class="gid" style="display:none"><input type="hidden" id="" name="gid" class="listword" value="${type.gid}" /></td>
				 		
				 		    <td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="${type.goodsuid}"></td>
			                <td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="${type.good.goodscode}" readonly="readonly" ></td>
			                <td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="${type.good.goodsname}" readonly="readonly"></td>
			                <td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="${type.good.goodsstandard}" readonly="readonly"></td>
			             
			                <td class="mainUnit" ><input type="text" id="" name="mainUnit" class="listword" value="${type.good.unitName}" readonly="readonly"></td>
			                <td class="mainNumber"><input type="text" id="" name="mainNumber" class="listword toDealInput numric" value="<fmt:formatNumber type="number" value="${type.number}" minFractionDigits="2" groupingUsed="false" />" onchange="changeFlag(this)"></td>		             
			                <%--<td class="assistUnit"><input type="text" id="" name="assistUnit" class="listword" value="${type.good.cstComUnitName}" readonly="readonly"></td>--%>
			               <%--  <td class="assistUnitcode" style="display:none"><input type="text" id="" name="assistUnitcode" class="listword jnumric" value="${type.good.cstcomunitcode}" readonly="readonly"></td>
			                <td class="assistNumber"><input type="text" id="" name="assistNumber" class="listword jjnumric" value="<fmt:formatNumber type="number" value="${type.assistNumber}" minFractionDigits="2"/>" readonly="readonly"></td> --%>
			               <%--<c:if test="${not empty type.good.cstcomunitcode}">			          --%>
							<%--<td class="assistUnitcode" style="display:none"><input type="text" id="" name="assistUnitcode" class="listword jnumric" value="${type.good.cstcomunitcode}" readonly="readonly"></td>--%>
							<%--<td class="assistNumber"><input type="text" id="" name="assistNumber" class="listword jjnumric" value="<fmt:formatNumber type="number" value="${type.assistNumber}" minFractionDigits="2"/>" onchange="changeFlag(this)"></td>--%>
							<%--</c:if>--%>
						    <%--<c:if test="${empty type.good.cstcomunitcode}">--%>
							<%--<td class="assistUnitcode" style="display:none"><input type="text" id="" name="assistUnitcode" class="listword" value="${type.good.cstcomunitcode}" readonly="readonly"></td>--%>
							<%--<td class="assistNumber"><input type="text" id="" name="assistNumber" class="listword" value="" readonly="readonly"></td>--%>
							<%--</c:if>--%>
			                
			                <td class="goodsAllocationName"><input type="text" id="" name="goodsAllocationName" class="listword  jjjnumric" value="${type.alocation}" readonly="readonly" ></td>
			                <td class="goodsAllocationUid" style="display:none"><input type="text" id="" name="goodsAllocationUid" class="listword toDealInput" value="${type.goodsallocationuid}" readonly="readonly"></td>
			             	<td class="batch"><input type="text" id="" name="batch" class="listword " value="${type.batchcode}" readonly="readonly"></td>
			                <%--<td class="barCode"><input type="text" id="" name="barCode" class="listword " value="${type.barCode}" readonly="readonly"></td>--%>
			                <td class="note"><input type="text" id="" name="note" class="listword toDealInput" value="${type.notes}" readonly="readonly"></td>
				 		    <%--<td class="produceCode"><input type="text" id="" name="produceCode" class="listword toDealInput" value="${type.produceCode}" readonly="readonly"></td>--%>
				 		    <%--<td class="goodName"><input type="text" id="" name="goodName" class="listword toDealInput" value="${type.goodName}" readonly="readonly"></td>--%>
				 		    </tr>
				 	        </c:forEach>
				 			</tbody>
				 		</table>
				 		<!-- 添加按钮 -->
				 		<div class="addrow fl" style="margin-left:35px;margin-top:-23px;display: none">
					</div>
			 		</div>
		 		</div>		 		
		 	</div>
		 	<!--表格部分 end-->	
		 	<ul class="wordul fr" style="width: 80%;">
 				<li class=" fl">
					<div class="wordname fl">录入人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${saleOutWarehouse['recordpersonName']}" id="recordPersonName" name="recordPersonName" readonly="readonly">
					<input type="hidden" id="recordPersonUid" name="recordPersonUid" value="${saleOutWarehouse['recordPersonId']}">
					</div>
					<div class="cf"></div> 
 				</li>
 				<li class=" fl">
					<div class="wordname fl wordfoot">录入日期：</div>
					<div class="wordnameinput fl"><input type="text" value="${fn:substring(saleOutWarehouse['recordDate'],0,10)}" id="recordDate" name="recordDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li>
 				<%-- <li class="wordli fl">
					<div class="wordname fl">审核人：</div>
					<div class="wordnameinput fl">
					<input type="text" value="${produceOrder['auditpersonName']}" id="auditpersonName" name="auditpersonName" readonly="readonly"> </div>
					<input type="hidden" id="auditPersonUid" name="auditPersonUid" value="${produceOrder['auditPersonUid']}">
					<div class="cf"></div> 
 				</li>
 				<li class="wordli fl">
					<div class="wordname fl">审核日期：</div>
					<div class="wordnameinput fl"><input type="text" <c:if test="${produceOrder['auditDate']!=null}">value="${fn:substring(produceOrder['auditDate'],0,10)}"</c:if> id="auditDate" name="auditDate" readonly="readonly"> </div>
					<div class="cf"></div> 
 				</li> --%>
 				<div class="cf"></div> 
 			</ul>
		 	<div class="cf"></div> 
		</div>
		</form>



	</body>
</html>
<script>
    $('#canzhao')[0].onclick = canzhao;
    var magid;//定义一个全局的参照单据id，全局只能有一个相同的参照单据id，如果不同，则不可以新增不同的单据id

    function canzhao(){
        //判断列表里存不存在手动新增出库明细的数据。如果有，则不可以参照
        var trs=$('.serialTr');
        if(trs.length > 0)
        {
            trs.eq(0).children('.needoutnum').children().val();
            var length = trs.eq(0).children('.needoutnum').length;
            if(length == 0){
                $.dialog.alert_w("存在物料，不能参照领料申请单出库");
                return false;
			}

        }


        $('.addrow').hide();
        $.dialog({
            drag: true,
            lock: true,
            resize: false,
            title:'领料申请列表',
            width: '1100px',
            height: '590px',
            content: 'url:${ctx}/wms/wareHouse_getApplyHelp.emi',
            okVal:"确定",
            ok:function(){
                var trs=$('.serialTr');

                var chek = $('.goodsSelected:checked',this.content.document.getElementById("rtframe").contentDocument);
                if(trs.length == 0){
                    magid = chek.eq(0).attr("materialapplygid");
                }
                if(trs.length > 0){
                    var aid =  chek.eq(0).attr("materialapplygid");
                    if(aid != magid){
                        $.dialog.alert_w("同一个出库单，参照单据只能选择一个");
                        return false;
                    }
                }

                for (var i = 0; i < chek.length; i++) {
                    var strs='<tr class="serialTr">'+
                        '<td><div class="delrow delno" name = "deleteButton" value=""  style="margin-left:15px;" gid=""></div></td>'+
                        '<td class="gid" style="display:none"><input type="hidden" id="" name="gid" class="listword" value=""></td>'+
                        '<td class="materialapplygid" style="display:none"><input type="hidden" id="" name="materialapplygid" class="listword" value="'+chek.eq(i).attr("materialapplygid")+'"></td>'+
                        '<td class="materialapplycgid" style="display:none"><input type="hidden" id="" name="materialapplycgid" class="listword" value="'+chek.eq(i).attr("materialapplycgid")+'"></td>'+

                        '<td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="'+chek.eq(i).val()+'"></td>'+
                        '<td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="'+chek.eq(i).attr("goodsCode")+'" readonly="readonly"></td>'+
                        '<td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="'+chek.eq(i).attr("goodsName")+'" readonly="readonly"></td>'+
                        '<td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="'+chek.eq(i).attr("goodsstandard")+'" readonly="readonly"></td>'+
                        '<td class="mainUnit"><input type="text" id="" name="mainUnit" class="listword" value="'+chek.eq(i).attr("unitName")+'" readonly="readonly"></td>'+
                        '<td class="needoutnum" style="display:none"><input type="hidden" id="" name="needoutnum" class="listword" value="'+chek.eq(i).attr("needoutnum")+'"></td>'+
						'<td class="mainNumber"><input type="text" id="" name="mainNumber" class="listword numric" value="'+chek.eq(i).attr("needoutnum")+'" onchange="changeneednum(this)"></td>';
                    var binvbach=chek.eq(i).attr("binvbach");
                    strs+='<td class="goodsAllocationName"><input type="text" id="" name="goodsAllocationName" class="listword jjjnumric"  readonly="readonly" onclick="clickFlag(this)"></td>'+
                        '<td class="goodsAllocationUid" style="display:none"><input type="text" id="" name="goodsAllocationUid" class="listword "  readonly="readonly" onclick="clickFlag(this)"></td>'+
                        '<td class="batch"><input type="text" id="" name="batch" class="listword batchInput" value="" isbatch="'+binvbach+'"></td>'+
                        '<td class="note"><input type="text" id="" name="note" class="listword" value="" ></td>'+
                        '</tr>';
                    var macgid = chek.eq(i).attr("materialapplycgid");
					var gmaclist = $('.serialTr').find(".materialapplycgid").children();
					var flag =1;
					gmaclist.each(function(i,t){
						var tempgmacgid = $(t).val();
						if(tempgmacgid == macgid){
                            flag = 2;
						}
					})

					if(flag == 2){
					    //不新增该行，继续执行下一行循环
						continue;
                    }

                    $("#contr").append(strs);
                    getSerial();
                    $(".numric").numeral(6);
                    setIsbatch();
                }
            },
            cancelVal:"关闭",
            cancel:true

        });
    }


    function changeneednum(obj){
		var num = $(obj).val();
		var neednum = $(obj).parent().parent().find("input[name='needoutnum']").val();
		if(parseFloat(num) > parseFloat(neednum)){
            $.dialog.alert_w("出库数量不能大于申请数量");
            $(obj).val(neednum);
            return false;
		}
		return true;

	}

</script>


