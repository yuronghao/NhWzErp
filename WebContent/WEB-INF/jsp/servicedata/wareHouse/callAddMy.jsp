<%@page import="com.emi.common.bean.core.TreeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>调拨单</title>
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
		   if(document.getElementById('outwhUid').value==""){
				$.dialog.alert_w("出仓库不能为空!");
			  	return false;
			}
            if(document.getElementById('inwhUid').value==""){
                $.dialog.alert_w("入仓库不能为空!");
                return false;
            }
            var trs=$('.serialTr');
			if(trs.length<=0)
				{
					$.dialog.alert_w("出库明细不能为空!");
				  	return false;
				}
			
			for(var i=0;i<trs.length;i++){
			      if($('.numric').eq(i).val()==''){
			    	  $.dialog.alert('主数量不能为空');
			    	  return false;
			      }
			      if($('.outjjjnumric').eq(i).val()==''){
			    	  $.dialog.alert('出货位号不能为空');
			    	  return false;
			      }

                if($('.injjjnumric').eq(i).val()==''){
                    $.dialog.alert('入货位号不能为空');
                    return false;
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
				$("#outwhName").attr("onclick","selectmanagerout()");
                $("#inwhName").attr("onclick","selectmanagerin()");
				$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
				$("#customerName").attr("onclick","selectcustomer()");
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
					$("#outwhName").attr("onclick","selectmanagerout()");
                    $("#inwhName").attr("onclick","selectmanagerin()");
					$('#billDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
					$('.startDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
					$('.endDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
					$(".outjjjnumric").attr("onclick","clickFlagout(this)");
                    $(".injjjnumric").attr("onclick","clickFlagin(this)");
					$("#customerName").attr("onclick","selectcustomer()");
					revBtn();
				}
			});	
			//保存
			$('#saveBtn').click(function(){		
				if(checkForm()){
				if(isSave){//新增保存			
					$.ajax({
						url:'${ctx}/wms/wareHouse_addCall.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){
							var obj=eval("("+da+")");
							if(obj.success==1){
								window.location.href = '${ctx}/wms/wareHouse_toAddCall.emi';
							}else{
								$.dialog.alert_e(obj.failInfor);
							}					
						}				
					});
				}else{//修改保存			
					$.ajax({
						url:'${ctx}/wms/wareHouse_updateCall.emi',
						type:"post",
						data:$('#myform').serialize(),
						success:function(da){
							var obj=eval("("+da+")");
							if(obj.success==1){
								window.location.href = '${ctx}/wms/wareHouse_toAddCall.emi';
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
							url : '${ctx}/wms/wareHouse_deleteCall.emi',
							type : 'post',
							data : {gid : gid},
							success : function(da) {	
								
								var obj=eval("("+da+")");
								if(obj.success==1){
									window.location.href = '${ctx}/wms/wareHouse_toAddCall.emi';
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
			location.href = '${ctx}/wms/wareHouse_toAddCall.emi';
		}
		
		
		function getprocurearrivallist(){
			window.location.href = '${ctx}/wms/allocation_gtasksMygetAllocationList.emi';
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
				trs.eq(i).children('.outgoodsAllocationName').children().attr('id','outgoodsAllocationName'+(i+1));
				trs.eq(i).children('.outgoodsAllocationUid').children().attr('id','outgoodsAllocationUid'+(i+1));
				 trs.eq(i).children('.ingoodsAllocationName').children().attr('id','ingoodsAllocationName'+(i+1));
				 trs.eq(i).children('.ingoodsAllocationUid').children().attr('id','ingoodsAllocationUid'+(i+1));
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
					strs+='<td class="outgoodsAllocationName"><input type="text" id="" name="outgoodsAllocationName" class="listword outjjjnumric"  readonly="readonly" onclick="clickFlagout(this)"></td>'+
						'<td class="outgoodsAllocationUid" style="display:none"><input type="text" id="" name="outgoodsAllocationUid" class="listword "  readonly="readonly" onclick="clickFlagout(this)"></td>'+

                        '<td class="ingoodsAllocationName"><input type="text" id="" name="ingoodsAllocationName" class="listword injjjnumric"  readonly="readonly" onclick="clickFlagin(this)"></td>'+
                        '<td class="ingoodsAllocationUid" style="display:none"><input type="text" id="" name="ingoodsAllocationUid" class="listword "  readonly="readonly" onclick="clickFlagin(this)"></td>'+
						
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
			var outgoodsAllocationUid=$(obj).parent().parent().find("input[name='outgoodsAllocationUid']").val();

			var pwdWin = $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'选择批次物料',
			    width: '800px',
				height: '400px',
				zIndex:4004,
				content: 'url:${ctx}/plugin_selectAllocationStockList.emi?goodsUid='+goodsUid+'&goodsAllocationUid='+outgoodsAllocationUid,
				okVal:"确定",
				ok:function(){
					var usercheck = $("input:checked",this.content.document);
					$(obj).val(usercheck.attr("batch"));
				},
				cancelVal:"关闭",
				cancel:true
			});
		}
		
		
		function clickFlagout(obj)
		{
			var temp=$(obj).attr("id").substr(22);
			if($("#outwhUid").val()=="")
				{
				  $.dialog.alert('请选择出仓库！');
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
						content: 'url:${ctx}/wms/wareHouse_getGoodsAllocationHelp.emi?id='+$("#outwhUid").val(),
						okVal:"确定",
						ok:function(){
							
							var chek = $('.goodsSelected:checked',this.content.document); 
							document.getElementById('outgoodsAllocationName'+temp).value=chek.eq(0).attr("allocationName");
							document.getElementById('outgoodsAllocationUid'+temp).value=chek.eq(0).val();
						},
						cancelVal:"关闭",
						cancel:true
						});
				}
		}




        function clickFlagin(obj)
        {
            var temp=$(obj).attr("id").substr(21);
            if($("#inwhUid").val()=="")
            {
                $.dialog.alert('请选择入仓库！');
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
                    content: 'url:${ctx}/wms/wareHouse_getGoodsAllocationHelp.emi?id='+$("#inwhUid").val(),
                    okVal:"确定",
                    ok:function(){

                        var chek = $('.goodsSelected:checked',this.content.document);
                        document.getElementById('ingoodsAllocationName'+temp).value=chek.eq(0).attr("allocationName");
                        document.getElementById('ingoodsAllocationUid'+temp).value=chek.eq(0).val();
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
		function selectmanagerout(){
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
                        document.getElementById('outwhName').value=usercheck[0].whname;
                        document.getElementById('outwhUid').value=usercheck[0].gid;


					 var temp=document.getElementsByName("outgoodsAllocationName");
					 var temps=document.getElementsByName("outgoodsAllocationUid");
					 for(var i=0;i<temp.length;i++)
						 {
						 temp[i].value='';
						 temps[i].value='';
						 }
				},
				cancelVal:"关闭",
				cancel:true
			});	
		}




        function selectmanagerin(){
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
                    document.getElementById('inwhName').value=usercheck[0].whname;
                    document.getElementById('inwhUid').value=usercheck[0].gid;


                    var temp=document.getElementsByName("ingoodsAllocationName");
                    var temps=document.getElementsByName("ingoodsAllocationUid");
                    for(var i=0;i<temp.length;i++)
                    {
                        temp[i].value='';
                        temps[i].value='';
                    }
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
<input type="hidden" id="callgid" name="callgid" value="${call['gid']}" >
		 <div class="EMonecontent">
		 	<div style="width: 100%;height: 15px;"></div>
		 	<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<!--<li class="fl"><a href="AttributeProjectClass.html"><input type="button" class="backBtn" value="返回"></a></li>-->
		 			<%--<li class="fl"><input type="button" class="btns" value="新增" id="addBtn"> </li>--%>
		 			<%--<li class="fl"><input type="button" class="btns" value="修改" id="revBtn"> </li>--%>
		 			<%--<li class="fl"><input type="button" class="btns" value="删除" id="delBtn" onclick="deletesob('${call['gid']}')"> </li>--%>
		 			<%--<li class="fl"><input type="button" class="btns" value="保存" id="saveBtn"> </li>--%>
		 			<%--<li class="fl"><input type="button" class="btns" value="放弃" id="giveUpBtn" onclick="giveup()"> </li>--%>
		 			<%--<li class="fl" style="display:none"><input type="button" class="btns" value="审核"> </li>--%>
			 		<%--<li class="fl" style="display:none"><input type="button" class="btns" value="弃审"> </li>--%>
					<li class="fl"><input type="button"  style="background-color: #0e76a8" class="btns" value="同意" onclick="tongyi('${callgid}','${followmovinggid}')"> </li>
					<li class="fl"><input type="button" style="background-color: red" class="btns" value="驳回" onclick="bohui('${callgid}','${followmovinggid}')"> </li>
		 			<li class="fl"><input type="button" class="btns" value="列表" id="tableBtn" onclick="getprocurearrivallist()"></li>
					<%--<li class="fl"><input type="button" class="btns" value="参照" id="canzhao"></li>--%>
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
		 					initPageTurning('${ctx }/wms/wareHouse_toAddCall.emi','WM_Call','gid',"${call['gid']}",
		 							cond,'callgid');
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
		 		<div class="tabletitle" style="margin-top:6px;">调拨单</div>
		 		<div>
		 			<!--12-->
		 			<ul class="wordul">
		 				<li class="wordli fl">
							<div class="wordname fl">单据编号：</div>
							<div class="wordnameinput fl"><input type="text" value="${call['billCode']}" id="billCode" name="billCode" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">单据日期：</div>
							<div class="wordnameinput fl"><input type="text" value="${fn:substring(call['billDate'],0,10)}" id="billDate" name="billDate" readonly="readonly"> </div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">部门：</div>
							<div class="wordnameinput fl">
							<input type="text" name="depName" id="depName" class="toDealInput" value="${department.depname}" >
	 						<input type="hidden" id="depUid" name="depUid" value="${call['departmentUid']}">
							</div>
							<div class="cf"></div> 
		 				</li>
		 				<li class="wordli fl">
							<div class="wordname fl">调出仓库：</div>
							<div class="wordnameinput fl">
							<input type="text" name="outwhName" id="outwhName" class="toDealInput" value="${outwarehouse.whname}">
							<input type="hidden" value="${call['outWhUid']}" id="outwhUid" name="outwhUid">
							</div>
							<div class="cf"></div> 
		 				</li>

		 				<div class="cf"></div> 		 				
		 			</ul>
		 			<ul class="wordul">


		 				<li class="wordli fl">
							<div class="wordname fl">备注：</div>
							<div class="wordnameinput fl"><input type="text" value="${call['notes']}" id="notes" name="notes" class="toDealInput"> </div>
							<div class="cf"></div> 
		 				</li>


						<li class="wordli fl">
							<div class="wordname fl">类型：</div>
							<div class="wordnameinput fl">
								<select class="toDealSelect" id="businessTypeUid" name="businessTypeUid"  disabled="disabled">
									<c:forEach items="${rdstylelist}" var="rdstyle" varStatus="status">
										<option value="${rdstyle.gid}" <c:if test="${call['businessTypeUid'] == rdstyle.gid}">selected="selected"</c:if>>${rdstyle.crdName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="cf"></div>
						</li>


						<li class="wordli fl">
							<div class="wordname fl">调入仓库：</div>
							<div class="wordnameinput fl">
								<input type="text" name="inwhName" id="inwhName" class="toDealInput" value="${inwarehouse.whname}">
								<input type="hidden" value="${call['inWhUid']}" id="inwhUid" name="inwhUid">
							</div>
							<div class="cf"></div>
						</li>




						<c:if test="${sp == 1}">
							<li class="wordli fl">
								<div class="wordname fl">审批状态:</div>
								<div class="wordnameinput fl">
									<div class="wordnameinput fl">
										<c:if test="${call['status'] == 0}">
											<input style="color: #0e78c9" type="text" value="未审核" id="zhuangtai" name="zhuangtai" class="" disabled>
										</c:if>
										<c:if test="${call['status'] == 1}">
											<input style="color: #0E2D5F" type="text" value="审核中" id="zhuangtai" name="zhuangtai" class="" disabled>
										</c:if>
										<c:if test="${call['status'] == 2}">
											<input style="color: #00B83F" type="text" value="已通过" id="zhuangtai" name="zhuangtai" class="" disabled>
										</c:if>
										<c:if test="${call['status'] == 3}">
											<input style="color: red" type="text" value="被驳回" id="zhuangtai" name="zhuangtai" class="" disabled>
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
				 					<th>调出货位号</th>
									<th>调入货位号</th>
				 					<th>批次</th>
				 					<%--<th style="width: 12%">条形码</th>--%>
				 					<th>备注</th>
									<%--<th>生产订单号</th>--%>
									<%--<th>产品编号</th>--%>
				 				</tr>
				 			<c:forEach var="type" items="${wmCallC}" varStatus="stat">
				 		    <tr class="serialTr">
				 		    <td><div class="delrow" name = "deleteButton"  style="margin-left:15px;float: left;display: none" title="删除" id="${type.gid}"></div>
				 		    </td>
				 		     <td class="gid" style="display:none"><input type="hidden" id="" name="gid" class="listword" value="${type.gid}" /></td>
				 		
				 		    <td class="goodsUid" style="display:none"><input type="text" id="" name="goodsUid" class="listword" value="${type.goodsUid}"></td>
			                <td class="goodsCode"><input type="text" id="" name="goodsCode" class="listword" value="${type.goods.goodscode}" readonly="readonly" ></td>
			                <td class="goodsName"><input type="text" id="" name="goodsName" class="listword" value="${type.goods.goodsname}" readonly="readonly"></td>
			                <td class="goodsstandard"><input type="text" id="" name="goodsstandard" class="listword" value="${type.goods.goodsstandard}" readonly="readonly"></td>
			             
			                <td class="mainUnit" ><input type="text" id="" name="mainUnit" class="listword" value="${type.goods.unitName}" readonly="readonly"></td>
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
			                
			                <td class="outgoodsAllocationName"><input type="text" id="" name="outgoodsAllocationName" class="listword  outjjjnumric" value="${type.outAllocationName}" readonly="readonly" ></td>
			                <td class="outgoodsAllocationUid" style="display:none"><input type="text" id="" name="outgoodsAllocationUid" class="listword toDealInput" value="${type.outgoodsAllocationUid}" readonly="readonly"></td>

							<td class="ingoodsAllocationName"><input type="text" id="" name="ingoodsAllocationName" class="listword  injjjnumric" value="${type.inAllocationName}" readonly="readonly" ></td>
								<td class="ingoodsAllocationUid" style="display:none"><input type="text" id="" name="ingoodsAllocationUid" class="listword toDealInput" value="${type.ingoodsAllocationUid}" readonly="readonly"></td>


			             	<td class="batch"><input type="text" id="" name="batch" class="listword " value="${type.batch}" readonly="readonly"></td>
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
					<input type="text" value="${call['recordpersonName']}" id="recordPersonName" name="recordPersonName" readonly="readonly">
					<input type="hidden" id="recordPersonUid" name="recordPersonUid" value="${call['recordPersonUid']}">
					</div>
					<div class="cf"></div> 
 				</li>
 				<li class=" fl">
					<div class="wordname fl wordfoot">录入日期：</div>
					<div class="wordnameinput fl"><input type="text" value="${fn:substring(call['recordDate'],0,10)}" id="recordDate" name="recordDate" readonly="readonly"> </div>
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
    function tongyi(owhGid,followmovinggid) {
        if (confirm("是否确定通过?")) {
            $.ajax({
                data: {"followmovinggid":followmovinggid,"owhGid":owhGid,"type":1,"billtype":"call"},
                type: 'POST',
                async: false,
                url: '${ctx}/wms/wareHouse_updateFollowMovingStatus.emi',
                success: function(req){
                    var jsonO =  eval('(' + req + ')');
                    if(jsonO.success=='1'){

                        window.location.href = "${ctx}/wms/wareHouse_gtasksMygetAllocationList.emi";

                    }else{
                        $.dialog.alert_w("系统异常，请刷新界面");
                    }

                }
            });
        }

    }

    //驳回
    function bohui(owhGid,followmovinggid) {
        if (confirm("是否确定驳回?")) {
            $.ajax({
                data: {"followmovinggid":followmovinggid,"owhGid":owhGid,"type":2,"billtype":"call"},
                type: 'POST',
                async: false,
                url: '${ctx}/wms/wareHouse_updateFollowMovingStatus.emi',
                success: function(req){
                    var jsonO =  eval('(' + req + ')');
                    if(jsonO.success=='1'){
                        window.location.href = "${ctx}/wms/wareHouse_gtasksMygetAllocationList.emi";

                    }else{
                        $.dialog.alert_w("系统异常，请刷新界面");
                    }

                }
            });
        }

    }

</script>


