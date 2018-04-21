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
		<script type="text/javascript">
		$(function(){
			$("tr:even").addClass("oddbg");
			$('.strtoal').click(function() {
				
				if($(this).prop('checked')){
					var temp=$(this).attr("idsname");
					var p=$(this).attr("gid");
					var gid=$(this).val();
				 	 var temps=$(this).attr("vgid")-0;
				    $.ajax({
				        url: '${ctx}/wms/prty_findAaBaseAttrDetail.emi',
				        data: {gid:gid},
						type: 'POST',
						dataType:'json',
				        success: function (data) {
				        	debugger;
							var strs='';
							strs=strs+'<div class="EMattr" id="'+p+'">'
					 		+'<span style="margin-left:1%;margin-top:20px;line-height:30px;">'+temp+'</span>'
					 		+'<div class="divAttr"><ul class="disdiv">';						
									for (var i = 0; i < data.length; i++) {
										strs=strs+'<li><input type="checkbox" class="attrvalueclass" value="'+data[i].gid+'" ugid="'+gid+'" name="detailName">&nbsp;&nbsp;'+data[i].name+'</li>';
									}
							strs=strs+'<div style="clear:both"></div></ul>'						
								+'<div class="autoattribute autoattr fr" style="margin-right:1%;" id="aa">更多>></div>'
								+'<div style="clear:both"></div>'
					 		+'</div>'
					 	  +'</div>';
					 	
/* 					 		$('.EMattr').each(function(index, elem){
					 		  if(index==0)
					 			  {
					 				 $("#showBase").append(strs);
					 			  }
					 		  else if(index>temps)
					 			  {
					 				 $("#baseID"+(temps+1)).before(strs);
					 			  }
					 		  else if(index<temps)
					 			  {
					 			 $("#baseID"+(temps+1)).after(strs);
					 			  }
					 			   
					 		
					 		}); */
					 		 $("#showBase").append(strs);
				        },
				        error:function()
				        {
				         }
				});

				}
				else{
					var tem=$(this).attr("gid");
					$("#"+tem).remove();
					
				}
			});
		});
	</script>
	<script type="text/javascript">
		$(function(){
			$('.saveBtn').click(function(){
				
				if(checkdata()){
					var attrGid='';
					var detailUGid='';
					var detailGid='';
					$('input[name="attrName"]:checked').each(function(){ 
						attrGid+=$(this).val()+',';
						});
					$('input[name="detailName"]:checked').each(function(){ 
						detailUGid+=$(this).attr("ugid")+',';						
						detailGid+=$(this).val()+',';
						});
					$("#bigModel").val(attrGid.substring(0, attrGid.length-1));
					$("#bsmallModel").val(detailUGid.substring(0, detailUGid.length-1));
					$("#smallModel").val(detailGid.substring(0, detailGid.length-1));
					$.ajax({
		 				  data: $("#myform").serialize(),
		 				  type: 'POST',
		 				  url: '${ctx}/wms/prty_AddAA_Soulation.emi',
		 				  dataType:'text',
		 				  success: function(req){
		 					  if(req=='success'){
		 						  $.dialog.alert_s('保存成功',function(){location.href="${ctx}/wms/prty_getAA_SoulationList.emi";});
		 					  }
		 					  else if(req=='fail'){
		 						  $.dialog.alert_e('该方案名称已存在！');
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
		});
		function checkdata()
		{
			if(document.getElementById('name').value==""){
				$.dialog.alert_w("方案名称不能为空!");
			  	return false;
			}
			else
			{ 
				
				return true;
			}
		}
	</script>
	</head>
	<body><form name="myform" id="myform" method="post" action="" onsubmit="return checkdata()">
		<input type="hidden" name="gid" id="gid" value="${gid}">
	<input type="hidden" name="bigModel" id="bigModel">
	<input type="hidden" name="bsmallModel" id="bsmallModel">
	<input type="hidden" name="smallModel" id="smallModel">
		<div class="EMonecontent">
			<div style="width: 100%;height: 15px;"></div>
			<!--按钮部分-->
		 	<div class="toolbar">
		 		<ul>
		 			<li class="fl"><a href="javascript:history.go(-1);"><input type="button" class="backBtn" value="返回"></a></li>
		 			<li class="fl"><input type="button" class="saveBtn" value="保存"> </li>		 	
		 			<div class="cf"></div>
		 		</ul>
		 	</div>
		 	<!--按钮部分 end-->
		 	<div class="creattable">
		 		<div class="tabletitle">属性方案</div>		 		
		 		<div class="xz_attribute" style="border:1px #ccc solid;">
		 			<div style="height:10px;"></div>
		 			<ul>
		 				<li style="width:30%;" class="fl">
		 					<div style="width:150px;text-align:right;" class="fl">方案名称:</div>
		 					<div style="width:50%;text-align:left;" class="fl">
		 						<input type="text" style="width:260px;height:25px;line-height: 25px;" value="${bean.souname}" id="name" name="name"  />
		 					</div>
		 					<div class="cl"></div>
		 				</li>
		 				<li style="width:30%;" class="fl">
		 					<div style="width:150px;text-align:right;" class="fl">所属类别:</div>
		 					<div style="width:50%;text-align:left;" class="fl">
								<div class="wordnameinput fl">
									<select id="categid" name="categid">
										<c:forEach var="b" items="${cate}">
											<option  value="${b.gid}" <c:if test="${b.gid==bean.categorygid }">selected="selected"</c:if>>${b.name}</option>
										</c:forEach>
									</select>
							 </div>
		 					</div>
		 					<div class="cl"></div>
		 				</li>

		 				<li style="width:30%;" class="fl">
		 					<div style="width:45%;text-align:right;" class="fl">是否启用:</div>
		 					<div style="width:50%;text-align:left;" class="fl">
							<input type="radio" name="flag" id="flag" value="1" checked="checked"/>是&nbsp;
							<input type="radio" name="flag" id="flag" value="0"/>否
		 					</div>
		 					<div class="cl"></div>
		 				</li>
		 				 <li style="width:100%;" class="fl">
		 					<div style="width:150px;text-align:right;" class="fl">选择属性:</div>
		 					<div style="width:80%;text-align:left;" class="fl">
		 						 <ul class="shuzingzhi" id="attrlistid">
						             <c:forEach var="item" items="${list}" varStatus="status0">
						             	 <li  style="float:left;margin-left:20px">
						             	 <input type="checkbox" class="strtoal" <c:if test="${item.checkFlag eq 1}">checked</c:if>  gid="baseID${status0.index}" value="${item.gid}" idsname="${item.name}" name="attrName" vgid="${status0.index}"/>&nbsp;&nbsp;${item.name }
						             	 </li>
						             </c:forEach>
   					                <div style="clear:both"></div>
						
						        </ul> 
		 					</div>
		 					<div class="cl"></div>
		 				</li>
		 				<div class="cl"></div>
		 			</ul>
		 			<div style="height:10px;"></div>
		 		</div>
		 		
		 	</div>
		 	<!--表格部分-->

		 	<!--表格部分 end-->	
		 	<div id="showBase">
			 	<c:forEach var="item" items="${list}" varStatus="stat">
			 		<c:if test="${item.checkFlag eq 1}">
					 	<div class="EMattr" id="baseID${stat.index}">
					 		<span style="margin-left:1%;margin-top:20px;line-height:30px;">${item.name}</span>
					 		<div class="divAttr">
		
								
									<ul class="disdiv" >
									   <c:forEach var="ite" items="${item.detailList}" varStatus="stat">
										  <li><input type="checkbox" class="attrvalueclass" <c:if test="${ite.checkFlag eq 1}">checked</c:if> value="${ite.gid}" ugid="${ite.base_attr_Uid}" name="detailName">&nbsp;&nbsp;${ite.name}</li>
										</c:forEach>
										<div style="clear:both"></div>
									</ul>
								
								<div class="autoattribute autoattr fr" style="margin-right:1%;" id="aa">更多>></div>
								<div style="clear:both"></div>
					 		</div>
					 	</div>
				 	</c:if>
			 	</c:forEach>
			</div>

		 	<!--分页部分-->
		 	<!--<div class="EMpage" style="	border: 1px solid red;">
		 				
		 	</div>-->
		 	<!--分页部分 end-->
		</div>
		</form>
	</body>
	<!--全选-->

	<script type="text/javascript">
	$(function(){
		$('.autoattr').click(function(){$('.disdiv').toggle();})
	})
	$(function(){
		$('.autoattr1').click(function(){$('.disdiv1').toggle();})
	})
	$(document).ready(function(){
		$('.autoattr2').click(function(){

			$('.disdiv2').toggle();
			$('autoattr2').value="11";

		});
	});
</script>
</html>