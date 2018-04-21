
function isArray(obj){ 
	return (typeof obj=='object')&&obj.constructor==Array; 
}
function resetIndex(tdName){
	$('td[name="'+tdName+'"]').each(function(index, element) {
		 $(this).html(index+1);
	 });
}
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
//新增一行物料
function initGoodsSelect(goods_gid,goods_code,goods_name,goods_standard,goods_unitName,free1,baseUse,baseQuantity,useNumber){
	baseUse = baseUse || 1;
	baseQuantity = baseQuantity || 1;
	useNumber = useNumber || 0;
	var tr = '<tr >';
		tr += '<td name="goodsIndex"  style="text-align: center;vertical-align: middle;"></td>';
		tr += '<input type="hidden" name="goodsId" id="g_'+goods_gid+'" value="'+goods_gid+'">';
		tr += '<input type="hidden" name="goodscode_'+goods_gid+'" id="goodscode_'+goods_gid+'" value="'+goods_code+'">';
		tr += '<input type="hidden" name="goodsname_'+goods_gid+'" id="goodsname_'+goods_gid+'" value="'+goods_name+'">';
		tr += '<input type="hidden" name="goodsstandard_'+goods_gid+'" id="goodsstandard_'+goods_gid+'" value="'+(goods_standard || '')+'">';
		tr += '<input type="hidden" name="unitName_'+goods_gid+'" id="unitName_'+goods_gid+'" value="'+(goods_unitName || '')+'">';
		tr += '<input type="hidden" name="useNumber_'+goods_gid+'" id="useNumber_'+goods_gid+'" value="'+(useNumber || '')+'">';
		//tr += '<input type="hidden" name="free1_'+goods_gid+'" id="free1_'+goods_gid+'" value="'+(free1 || '')+'">';
		tr += '<td style="text-align: center;vertical-align: middle;">'+goods_code+'</td>';
		tr += '<td style="text-align: center;vertical-align: middle;">'+goods_name+'</td>';
		tr += '<td style="text-align: center;vertical-align: middle;">'+(goods_standard || '')+'</td>';	
		tr += '<td style="text-align: center;vertical-align: middle;">'+(goods_unitName || '')+'</td>';
		tr += '<td style="text-align: center;vertical-align: middle;"><select id="free1_'+goods_gid+'" name="free1_'+goods_gid+'" style="width:100px"></select></td>';
		/*if(isOrder=='1'){
			tr += '<td style="vertical-align: middle;"><input type="text" class="numberInput" style="text-align: center;width: 200px;height: 16px" id="useNumber_g_'+goods_gid+'" name="useNumber_g_'+goods_gid+'" value="'+useNumber+'"></td>';
		}else{*/
			tr += '<td style="vertical-align: middle;"><input type="text" class="numberInput" style="text-align: center;width: 80px;height: 16px" id="baseUse_g_'+goods_gid+'" name="baseUse_g_'+goods_gid+'" value="'+baseUse+'"></td>';
			tr += '<td style="vertical-align: middle;"><input type="text" class="numberInput" style="text-align: center;width: 80px;height: 16px" id="baseQuantity_g_'+goods_gid+'" name="baseQuantity_g_'+goods_gid+'" value="'+baseQuantity+'" ></td>';
		/*}*/
		tr += '<td style="text-align: center;vertical-align: middle;"><img src="'+getContextPath()+'/img/delete.png" style="cursor: pointer;" onclick="deleteTr(this,\'goodsIndex\');"></img></td>';
		tr += '</tr>';
	$('#goodsTable').append(tr);
	$(".numberInput").numeral(6);//数量限制只能数字输入
	//从用友库读取bas_part数据
	$.ajax({
		url:'${ctx}/wms/basepd_getGoodsBasPart.emi?goodsCode='+goods_code+'&elementId=free1_'+goods_gid+'&free1='+encodeURI(encodeURI(free1 || '')),
		type:"post",
		dataType:'json',
		success:function(data){		
			if(data.success=='1'){
				var basPartArray = data.data;
				for(var i=0;i<basPartArray.length;i++){
					var bas_v = basPartArray[i].free1;
					var opt = "<option value='"+bas_v+"'";
					if(data.free1==bas_v){
						opt += "selected='selected'";
					}
					opt += ">"+bas_v+"</option>";
					$('#'+data.elementId).append(opt);
				}
			}
		}		
	 });
}

//新增一行派工对象
function initDispatchingSelect(gid,discode,disname){
	var tr = '<tr >';
		tr += '<td  style="text-align: center;vertical-align: middle;" name="dispatchingIndex">';
		tr += '</td>';
		tr += '<input type="hidden" name="disObjId" id="disObjId_'+gid+'" value="'+gid+'">';
		tr += '<input type="hidden" name="disObjCode_'+gid+'" id="disObjCode_'+gid+'" value="'+discode+'">';
		tr += '<input type="hidden" name="disObjName_'+gid+'" id="disObjName_'+gid+'" value="'+disname+'">';
		tr += '<td style="text-align: center;vertical-align: middle;">'+discode+'</td>';
		tr += '<td style="text-align: center;vertical-align: middle;">'+disname+'</td>';
		tr += '<td style="text-align: center;vertical-align: middle;"><img src="'+getContextPath()+'/img/delete.png" style="cursor: pointer;" onclick="deleteTr(this,\'dispatchingIndex\');"></img></td>';
		tr += '</tr>';
	$('#dispatchingTable').append(tr);
}

//新增一行设备
function initEquipmentSelect(gid,equcode,equname){
	var tr = '<tr >';
		tr += '<td  style="text-align: center;vertical-align: middle;" name="equipmentIndex">';
		tr += '</td>';
		tr += '<input type="hidden" name="equipmentId" id="equipmentId_'+gid+'" value="'+gid+'">';
		tr += '<input type="hidden" name="equipmentCode_'+gid+'" id="equipmentCode_'+gid+'" value="'+equcode+'">';
		tr += '<input type="hidden" name="equipmentName_'+gid+'" id="equipmentName_'+gid+'" value="'+equname+'">';
		tr += '<td style="text-align: center;vertical-align: middle;">'+equcode+'</td>';
		tr += '<td style="text-align: center;vertical-align: middle;">'+equname+'</td>';
		tr += '<td style="text-align: center;vertical-align: middle;"><img src="'+getContextPath()+'/img/delete.png" style="cursor: pointer;" onclick="deleteTr(this,\'equipmentIndex\');"></img></td>';
		tr += '</tr>';
	$('#equipmentTable').append(tr);
	//$('#equipmentTable').html(tr);//只允许选一个设备
}

//新增一行模具
function initMouldSelect(gid,mouldcode,mouldname,goodsCode,grossWeight,netWeight,mouldRatio){
	var tr = '<tr >';
		tr += '<td  style="text-align: center;vertical-align: middle;" name="mouldIndex">';
		tr += '</td>';
		tr += '<input type="hidden" name="mouldId" id="mouldId_'+gid+'" value="'+gid+'">';
		tr += '<input type="hidden" name="mouldCode_'+gid+'" id="mouldCode_'+gid+'" value="'+mouldcode+'">';
		tr += '<input type="hidden" name="mouldName_'+gid+'" id="mouldName_'+gid+'" value="'+mouldname+'">';
		tr += '<td  style="text-align: center;vertical-align: middle;">'+mouldcode+'</td>';
		tr += '<td  style="text-align: center;vertical-align: middle;">'+mouldname+'</td>';
		tr += '<td  style="text-align: center;vertical-align: middle;">'+mouldRatio+'</td>';
		
		tr += '<td  style="text-align: center;vertical-align: middle;"><input name="goodsCode_'+gid+'" id="goodsCode_'+gid+'" style="width:100px;"  value="'+goodsCode+'"/></td>';
		tr += '<td  style="text-align: center;vertical-align: middle;"><input name="grossWeight_'+gid+'" id="grossWeight_'+gid+'" style="width:100px;" value="'+grossWeight+'"/></td>';
		tr += '<td  style="text-align: center;vertical-align: middle;"><input name="netWeight_'+gid+'" id="netWeight_'+gid+'" style="width:100px;" value="'+netWeight+'"/></td>';
		
		tr += '<td style="text-align: center;vertical-align: middle;"><img src="'+getContextPath()+'/img/delete.png" style="cursor: pointer;" onclick="deleteTr(this,\'mouldIndex\');"></img></td>';
		tr += '</tr>';
	$('#mouldTable').append(tr);
//		$('#mouldTable').html(tr);//只允许选一个模具
}

/* 初始化上道工序的html */
var preProcIndex = 1;
function initPreTrHtml(routecid,baseUse,baseQuantity,useNumber){
	var processName = process_objs[routecid]['base']['processName'];
	var processCode = process_objs[routecid]['base']['processCode'] || '';
	var preProcessId = process_objs[routecid]['base']['routeCid'] || '';
	var t_baseUse = baseUse==null?1:baseUse>0?baseUse:1;
	useNumber = useNumber || 0;
	var tr_html = "";
		tr_html += '<tr>';
		tr_html += '<td  style="text-align: center;vertical-align: middle;">'+preProcIndex;
		tr_html += '<input type="hidden" name="preProcessId" value="'+preProcessId+'">';
		tr_html += '</td>';
		tr_html += '<td style="text-align: center;vertical-align: middle;">'+processCode+'</td>';
		tr_html += '<td style="text-align: center;vertical-align: middle;">'+processName+'</td>';
		tr_html += '<td style="vertical-align: middle;"><input type="text" class="numberInput" name="baseUse_'+preProcessId+'" id="baseUse_'+preProcessId+'" style="width: 100px;height: 16px;text-align: center;" value="'+t_baseUse+'"> </td>';
		tr_html += '<td style="vertical-align: middle;"><input type="text" class="numberInput" name="baseQuantity_'+preProcessId+'" id="baseQuantity_'+preProcessId+'" style="width: 100px;height: 16px;text-align: center;" value="1" ></td>';
		
		tr_html += '</tr>';
	$('#tbody_preProc').append(tr_html); 
	$(".numberInput").numeral(6);//数量限制只能数字输入
	preProcIndex++;
}

function deleteTr(img,indexName){
	if(confirm("确定删除？")){
		img.parentNode.parentNode.parentNode.removeChild(img.parentNode.parentNode);
		resetIndex(indexName);
	}
}

function initGoodsSelects(goods_gid,goods_code,free1){
	$('#priorattributeName').empty();
	//从用友库读取bas_part数据
	$.ajax({
		url:'${ctx}/wms/basepd_getGoodsBasPart.emi?goodsCode='+goods_code+'&elementId=free1_'+goods_gid+'&free1='+encodeURI(encodeURI(free1 || '')),
		type:"post",
		dataType:'json',
		success:function(data){		
			if(data.success=='1'){
				var basPartArray = data.data;
				for(var i=0;i<basPartArray.length;i++){
					var bas_v = basPartArray[i].free1;
					var opt = "<option value='"+bas_v+"'";
					if(basPartArray[i].free1==bas_v){
						opt += "selected='selected'";
					}
					opt += ">"+bas_v+"</option>";
					$('#priorattributeName').append(opt);
				}
			}
		}		
	 });
}



