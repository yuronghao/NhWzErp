<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
<style type="text/css">

</style>
</head>

	<script type="text/javascript">
	    $(function() {
	       $("#checkAll").click(function() {
	    	   var allchecked = this.checked;
	            //$('input[name="my_chose"]').attr("checked",this.checked); 
	            $('input[name="my_chose"]').each(function(index, element) {
	        		this.checked=allchecked;
	        		//加入选中的值到数组
	        		pushJson($(this).attr('jsonv'),!allchecked); 
	            }); 
	        });
	        var $my_chose = $("input[name='my_chose']");
	        $my_chose.click(function(){
	        	if('${multi}'=='0'){
	        		//单选,清除所有选中，再选择目标
	        		var re_check=this.checked;
	        		clearJson();
	        		$('input[name="my_chose"]').each(function(index, element) {
	    	        	this.checked = false;
	                });
	        		//
	        		this.checked = re_check;
	        		if(re_check){
	        			pushJson($(this).attr('jsonv')); 
	        		}
	        	}else{
	        		$("#checkAll").attr("checked",$my_chose.length == $("input[name='my_chose']:checked").length ? true : false);
		            pushJson($(this).attr('jsonv'),!this.checked);
	        	}
	        });
	        
	        //初始化checkbox的选中状态
	        $('input[name="my_chose"]').each(function(index, element) {
	        	//如果有数据带过来，出发点击事件自动选中
	        	for(var i=0;i<parent.selectedArray.length;i++){
	        		if(parent.selectedArray[i]==$(this).val()){
	        			this.click();
	        			//选中后删除数组中的值
	        			parent.selectedArray.splice(i,1);
	        			break;
	        		}
	        	}
	        	for(var i=0;i<parent.jsonArray.length;i++){
	        		if('${idColumn}'!='' && parent.jsonArray[i]['${idColumn}']==$(this).val()){
	        			this.checked = true;
	        			break;
	        		}
	        	}
            });
	        
	        //初始化全选是否选中
	        if($("input[name='my_chose']:checked").length>0){
	        	$("#checkAll").attr("checked",$my_chose.length == $("input[name='my_chose']:checked").length ? true : false);
	        }
	        
	    });
	    
	    //把选择的值放入
	    function pushJson(jsonv_str,remove){
	    	jsonv = JSON.parse(jsonv_str);
	    	var hasv = false;
	    	for(var i=0;i<parent.jsonArray.length;i++){
	    		if('${idColumn}'!='' && parent.jsonArray[i]['${idColumn}']==jsonv['${idColumn}']){
	    			if(remove){
	    				parent.jsonArray.splice(i,1);
	    			}else{
	    				hasv = true;
		    			break;
	    			}
	    			
	    		}
	    	}
	    	if(!hasv && !remove){
	    		parent.jsonArray.push(jsonv);
	    	}
	    }
	    
	    function clearJson(){
	    	parent.jsonArray.length=0;
	    }
	</script>


<body>
	<form action="${ctx }/plugin_selectList.emi?treeType=${treeType}&multi=${multi}&showTree=${showTree}&showList=${showList}" id="myform" method="post">
		<input type="hidden" name="id" value="${id }">
		<div class="EMonecontent" style="">
			<div style="width: 100%;height: 15px;"></div>
		</div>
		<!--主体部分-->
	 	<div class="mainword">
	 		<!-- <div class="tabletitle">物料设置</div> -->	
	 			
	 			<div class="creattable" style="width:100%;margin-top: 5px;margin-bottom: 15px;">
	 			 <input type="text" name="keyWord" placeholder="请输入搜索关键字" class="write_input" value="${keyWord }"> <input class="searchBtn" type="button" value="查询" onclick="document.forms[0].submit()">
		 			<div style="margin-top: 5px">
			 			<table id="myTable">
				 			<tbody>
				 				<tr>
				 					<th style="width: 8%;"><input id="checkAll" type="checkbox" <c:if test="${multi=='0' }">disabled="disabled"</c:if> /></th>
				 					<th style="width: 8%;">序号</th>
				 					<c:forEach var="col" items="${columns }" >
				 						<c:if test="${col.check!='1' && col.hidde!='1'}">
				 							<th style="width: ${col.width};">${col.desc }</th>
				 						</c:if>
				 					</c:forEach>
				 				</tr>
				 				
				 				<c:forEach items="${data.list }" var="gs" varStatus="stat">
					 				<tr>
					 					<td >
						 				<c:forEach var="col" items="${columns }" >
					 						<c:if test="${col.check=='1'}">
					 							<input type="checkbox" id="check_${gs.gid }" name="my_chose" value="${gs.gid }" jsonv='{<c:forEach var="col" items="${columns }" varStatus="instat">"${col.name}":"${gs[col.name] }"<c:if test='${instat.count<fn:length(columns) }'>,</c:if></c:forEach>}'/> 
					 						</c:if>
					 					</c:forEach>
					 					</td>
					 					<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
					 					<c:forEach var="col" items="${columns }" >
				 						<c:if test="${col.check!='1' && col.hidde!='1'}">
				 							<td >${gs[col.name] }</td>
				 						</c:if>
				 					</c:forEach>
					 				</tr>
				 				
				 				</c:forEach>

				 						 				 
				 			</tbody>
			 			</table>
			 		</div>
			 		
					<!--表格部分 end-->
					<!--分页部分-->
					<div style="margin-left: -22px">
					<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
					</div>
					
					<!--分页部分 end-->
		
		 		</div>
	 			
	 	</div>
	</form>
</body>
</html>