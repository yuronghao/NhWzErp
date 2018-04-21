<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>


	<script type="text/javascript">
		
	$(function(){
		$('tr > td.toolTd').click(function(){
			$(this).children().show();
			$('.tools').children().mouseover(function(){
				$(this).addClass('liEffect');
			});

			$('.tools').children().mouseleave(function(){
					$(this).removeClass('liEffect');
			});		
		});
		
		$('tr > td.toolTd').mouseleave(function(){
			$(this).children('.tools').hide();
		});				

	});

	//新增
	function addRoute(){
		location.href = "${ctx}/wms/mould_toAddmould.emi";
	}
	
	//修改模具信息
	function editRoute(){
		var chek = document.getElementById("rtframe").contentDocument.getElementsByName("lyzj_chose");
		var cutomerGid =$(window.frames["rtframe"].document).find("#cutomerGid").val();
		var currentDeptGid =$(window.frames["rtframe"].document).find("#currentDeptGid").val();
		var providerGid =$(window.frames["rtframe"].document).find("#providerGid").val();
		var mouldStyleId =$(window.frames["rtframe"].document).find("#mouldStyleId").val();
		var total = 0;
		var gid = '';
		for (var i = 0; i < chek.length; i++) {
			if (chek[i].checked) {
				total += 1;
				gid = chek[i].value;
			}
		}
		if (total > 1) {
			$.dialog.alert(tip_dontMultEdit);
			return false;
		} else if (total < 1) {
			$.dialog.alert(tip_editSelect);
			return false;
		}
		location.href = "${ctx}/wms/mould_toUpdatemould.emi?mouldId="+gid+"&cutomerGid="+cutomerGid+"&currentDeptGid="+currentDeptGid+"&providerGid="+providerGid+"&mouldStyleId="+mouldStyleId;
	}
	
	//删除
	function deleteRoute(){
		var chek = document.getElementById("rtframe").contentDocument.getElementsByName("lyzj_chose");
		var total = 0;
		var gid = '';
		for (var i = 0; i < chek.length; i++) {
			if (chek[i].checked) {
				total += 1;
				gid = chek[i].value;
			}
		}
		if (total > 1) {
			$.dialog.alert(tip_dontMultEdit);
			return false;
		} else if (total < 1) {
			$.dialog.alert(tip_editSelect);
			return false;
		}
		if (gid!=''){
			$.dialog.confirm(tip_confirmDelete,function(){
				$.ajax({
					data: $("#myform").serialize(),
					type: 'POST',
					url: "${ctx}/wms/mould_deletemould.emi?mouldId="+gid,
					success: function(req){
						if(req=='success'){
							$.dialog.alert_s('删除成功',function(){
								location.href="${ctx}/wms/mould_getmouldList.emi";
							});
							
						}else if(req=='error'){
							$.dialog.alert_e('删除失败');
						}
					},
					error:function(){
						$.dialog.alert_e('error');
					}
				});
			});
		}
	}
	
	//搜索
	function searchRoute(){
		document.forms[0].submit();
	}
	
	/* //checkbox点击事件
	function clickCheck(){
		var allchecked = true;
		var uck = document.getElementsByName('lyzj_chose');
		alert(uck)
		var allck = document.getElementById('all');
		
		for(var i=0;i<uck.length;i++){
			if(!uck[i].checked){
				allchecked = false;
			}
		}
		if(allchecked){
			allck.checked=true;
		}else{
			allck.checked=false;
		}
		
	}
	
	//全选按钮
	function selectAll(){
		var ckAll = document.getElementById('all');
		var isck = ckAll.checked;
		var uck = document.getElementsByName('lyzj_chose');
		for(var i=0;i<uck.length;i++){
			uck[i].checked=isck;
		}
	} */
		
	</script>
	

<body>
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>
	 	<div class="toolBar">
			<ul>
	 			<li class="fl">
						&nbsp;&nbsp;
						<input class="addBtn" type="button" value="新增" onclick="addRoute()">
						<input class="editBtn" type="button" value="修改" onclick="editRoute()">
						<input class="delBtn" type="button" value="删除" onclick="deleteRoute()">
						&nbsp;&nbsp;
				</li>
	 		</ul>
		</div>

	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="tabletitle">模具列表</div>	
	 		<div class="xz_attribute"  >
	 			<div class="tree_div fl" style="width:17%;height:70%">
	 				<iframe src="${ctx}/wms/cuspro_getClassifyList.emi?classtype='04'"   id="" name="" frameborder="0" width="100%"  height="100%"></iframe>
	 			</div>
	 			
	 			<div class="fl" style="width:81%;height:70%;">
	 				<iframe src="${ctx }/wms/mould_getRightMould.emi" frameborder="0" id="rtframe" name="rtframe" width="100%" height="100%"></iframe>
	 			</div>
	 			
	 			<div class="cf"></div>
	 			
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	 	 
	</div>

<script>


</script>
</body>
</html>