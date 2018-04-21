

//产品序号
function getOrder(){
	var trs=$('.contentTr');
	$('#cSize').val(trs.length);
	for(var i=0;i<trs.length;i++){

		trs.eq(i).children().first().children().val(i+1);
		trs.eq(i).children('.gGoodsUid').children().attr('id','gGoodsUid'+(i+1));
		trs.eq(i).children('.gGoodsCode').children().attr('id','gGoodsCode'+(i+1));
		trs.eq(i).children('.gGoodsName').children().attr('id','gGoodsName'+(i+1));
		trs.eq(i).children('.gGoodsType').children().attr('id','gGoodsType'+(i+1));
		trs.eq(i).children('.nNumber').children().attr('id','nNumber'+(i+1));
		trs.eq(i).children('.gGoodsUnit').children().attr('id','gGoodsUnit'+(i+1));
		trs.eq(i).children('.dPlanBegin').children().attr('id','dPlanBegin'+(i+1));
		trs.eq(i).children('.dPlanComplete').children().attr('id','dPlanComplete'+(i+1));
		trs.eq(i).children('.createCaiLiao').children().attr('id','createCaiLiao'+(i+1));
		
		trs.eq(i).children('.gGoodsUid').children().attr('name','gGoodsUid'+(i+1));
		trs.eq(i).children('.gGoodsCode').children().attr('name','gGoodsCode'+(i+1));
		trs.eq(i).children('.gGoodsName').children().attr('name','gGoodsName'+(i+1));
		trs.eq(i).children('.gGoodsType').children().attr('name','gGoodsType'+(i+1));
		trs.eq(i).children('.nNumber').children().attr('name','nNumber'+(i+1));
		trs.eq(i).children('.gGoodsUnit').children().attr('name','gGoodsUnit'+(i+1));
		trs.eq(i).children('.dPlanBegin').children().attr('name','dPlanBegin'+(i+1));
		trs.eq(i).children('.dPlanComplete').children().attr('name','dPlanComplete'+(i+1));
		trs.eq(i).children('.createCaiLiao').children().attr('name','createCaiLiao'+(i+1));

	}
}



//材料序号
function getMeterialOrder(){
	var trs=$('.meterialBody');
	for(var i=0;i<trs.length;i++){
		var crruenttrs=trs.eq(i).children('.caiLiaoTr');
		
		//存放每个产品对应材料的总行数
		trs.eq(i).parent().prevAll('.caiLiaoCount').attr('id','allocation'+(i+1));
		trs.eq(i).parent().prevAll('.caiLiaoCount').attr('name','allocation'+(i+1));
		trs.eq(i).parent().prevAll('.caiLiaoCount').val(crruenttrs.length);
		
		trs.eq(i).parent().prevAll('.caiLiaoTableIndex').attr('id','caiLiaoTableIndex'+(i+1));
		trs.eq(i).parent().prevAll('.caiLiaoTableIndex').attr('name','caiLiaoTableIndex'+(i+1));
		
		trs.eq(i).parent().prevAll('.caiLiaoFocus').attr('id','caiLiaoFocus'+(i+1));
		trs.eq(i).parent().prevAll('.caiLiaoFocus').attr('name','caiLiaoFocus'+(i+1));
		
		for(var j=0;j<crruenttrs.length;j++){
			crruenttrs.eq(j).children().first().children().val(j+1);
			crruenttrs.eq(j).children('.gMaterialUid').children().attr('id','gMaterialUid'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialCode').children().attr('id','gMaterialCode'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialName').children().attr('id','gMaterialName'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialType').children().attr('id','gMaterialType'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.caiLiaoNumber').children().attr('id','nNumber'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialUnit').children().attr('id','gMaterialUnit'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.dPlanReceive').children().attr('id','dPlanReceive'+(i+1)+'-'+(j));
			
			crruenttrs.eq(j).children('.gMaterialUid').children().attr('name','gMaterialUid'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialCode').children().attr('name','gMaterialCode'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialName').children().attr('name','gMaterialName'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialType').children().attr('name','gMaterialType'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.caiLiaoNumber').children().attr('name','nNumber'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.gMaterialUnit').children().attr('name','gMaterialUnit'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.dPlanReceive').children().attr('name','dPlanReceive'+(i+1)+'-'+(j));
			crruenttrs.eq(j).children('.dPlanReceive').children().attr('id','dPlanReceive'+(i+1)+'-'+(j));
		}
	};
};


//改变指定行的flag值
function changeFlag(obj,val){
	
	alert("ddd");
	
	var index = $("#"+obj.id).parent().parent().children().first().children().val();
	document.getElementById("flag"+index).value=val;
	
}




//各种事件
$(function(){


	
	$('body').on('click','.fenLuTd',function(){
		
		$(this).children('.fenLuTool').show();
		$('.fenLuTd').mouseleave(function(){
			$(this).children('.fenLuTool').hide();
		});
		
	});
	
	$('body').on('click','.createCaiLiao',function(){	
		if($(this).prevAll('.gGoodsUid').children().val()==''){
			alert('请先选择产品编码');
		}else{
			$(this).children('.dialog').show();
			$('#caiLiaoTableIndex').val($(this).children('.dialog').children('.caiLiaoTableIndex').val());
		}
	});
	
	$('body').on('click','.shut',function(event){
		
		$(this).parent().parent().hide();
		event.stopPropagation(); 
	});
	
	$('body').on('click','.add',function(){
		var trHtml='<tr class="produceOrderTr contentTr">'+
		'<td class="billBodyTd NO"><input class="center" type="text" name="" id="" value=""/></td>'+
		'<td class="billBodyTd fenLuTd">'+
			'操作'+
			'<div class="fenLuTool" style="display:none;">'+
				'<div class="add">增加</div>'+
				'<div class="delete">删除</div>'+
				'<div class="createMeterial">材料</div>'+
			'</div>'+
		'</td>'+
		'<td class="billBodyTd gGoodsUid" style="display:none;"><input type="text" name="" id=""  value=""/></td>'+
		'<td class="billBodyTd gGoodsCode"><input type="text" name="" id="" class="wlHelp center" value=""/></td>'+
		'<td class="billBodyTd gGoodsName"><input class="center" type="text" name="" id="" readonly="readonly"/></td>'+
		'<td class="billBodyTd gGoodsType"><input class="center" type="text" name="" id="" readonly="readonly"/></td>'+
		'<td class="billBodyTd nNumber"><input class="right" type="text" name="" id="" /></td>'+
		'<td class="billBodyTd gGoodsUnit"><input class="center" type="text" name="" id="" readonly="readonly"/></td>'+
		'<td class="billBodyTd dPlanBegin"><input class="center" type="text" name="" id="" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})"/></td>'+
		'<td class="billBodyTd dPlanComplete"><input class="center" type="text" name="" id="" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})"/></td>'+
		'<th class="billBodyTd processRouteGid"><input class="right" type="text" name="" id="" /></th>'
	'</tr>';

				
		$(this).parent('.fenLuTool').parent('.fenLuTd').parent().after(trHtml);
		getOrder();
		getMeterialOrder();
	});
	
	
	$('body').on('click','.delete',function(){
		$(this).parent('.fenLuTool').parent('.fenLuTd').parent().remove();
		getOrder();
		getMeterialOrder();
	});
	
	$('body').on('focus','.wlHelp',function(){
		var order=$(this).parent().parent().children().first().children().val();
		$('#saveOrder').val(order);
		$(this).parent().nextAll(".createCaiLiao").children().children('.caiLiaoTableIndex').val(order);
	});
	
	$('body').on('click','.wlHelp',function(e){
		
		$.dialog({ 
		drag: false,
		lock: true,
		resize: false,
		title:'物料查询',
	    width: '700px',
		height: '500px',
		content: 'url:goods!toGoodsHelp.action',
		});
		
		
	});
	
	
	
	//点击部门 弹出事件
	$('body').on('click','.departmentName',function(e){
		
		$.dialog({ 
			drag: true,
			lock: true,
			resize: false,
			title:'部门查询',
		    width: '700px',
			height: '500px',
			content: 'url:org!toDeptHelp.action',
		});				
	

	});
	
	
	
	//点击添加材料，弹出事件
	$('body').on('click','.createMeterial',function(e){		
		
		$.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'材料管理',
		    width: '900px',
			height: '600px',
			content: 'url:order!toMeterialAdd.action',
			zIndex:1976,
		});
		
	});
	
})




//检查表单数据的完整性

function checkForm(){
	var flag = true;
	
	flag=getValueNull();
	
	if(keyCode==13){
		flag=false;
	}
	
	var len = document.getElementById("cSize").value;
	for(var i=1;i<=len;i++){
		if(document.getElementById("dPlanComplete"+i).value==''){
			alert("计划完工日期不能为空!");
			flag = false;
			break;
		}else if(document.getElementById("gGoodsUid"+i).value==''){
			alert("产品编码不能为空!");
			flag = false;
			break;
		}
		
		var caiLiaoCount=document.getElementById("allocation"+i).value;
		
		
		
		for(var j=0;j<caiLiaoCount;j++){
			if(document.getElementById("gMaterialCode"+i+"-"+j).value==''){
				alert("材料编码不能为空!");
				flag = false;
				break;
			}else if(document.getElementById("dPlanReceive"+i+"-"+j).value==''){
				alert("材料计划领用不能为空!");
				flag = false;
				break;
			}else if(document.getElementById("nNumber"+i+"-"+j).value==''){
				alert("数量不能为空!");
				flag = false;
				break;
			}
		}
		
	}	
				
	
	
	return flag;
}


//按钮事件
$(function(){
	
	var isSave=true;//新增保存、修改保存标识
	
	//新增 内容全部清除
	$('#addBtn').click(function(){
		
		getOrder();
		
		
		$('.billHead').children('input').val("");//头内容全清
		$('tr').children().not($('.NO')).children().val("");//表格内容全清
		$('.polastHead').children('input').val("");//尾内容全清
		
		
		var toDeal=$('.toDeal');
		inputCanUse(toDeal);
		
		//部门事件，操作分录
		$('#departmentName').addClass("departmentName");
		$('.fenLuTh').removeAttr("style");
		$('.fenLuTd').removeAttr("style");
		
		//日期 事件
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanBegin').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanComplete').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('#dRecordDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		
		//物品事件
		//$('.gGoodsCode').children().addClass("wlHelp");
		
	})
	
	
	//修改
	$('#revBtn').click(function(){
		getOrder();
		
		isSave=false;
		
		var toDeal=$('.toDeal');
		inputCanUse(toDeal);
		
		//部门事件，操作分录
		$('#departmentName').addClass("departmentName");
		$('.fenLuTh').removeAttr("style");
		$('.fenLuTd').removeAttr("style");
		
		//日期 事件
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanBegin').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanComplete').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		

		//物品事件
		$('.gGoodsCode').children().addClass("wlHelp");
		
	})
	
	
	
	
	
	//保存订单
	$('#saveBtn').click(function(){
		
		
		if(isSave){//新增保存
			
			$.ajax({
				url:'${ctx}/order!addProduceOrder.action',
				type:"post",
				data:$('#myForm').serialize(),
				dataType:"json",
				success:function(da){
					
					if(da.error){
						alert(da.error);
					}
					
					if(da.suc){
						alert("保存成功");
						api.reload();
					}
					
				}
				
			});
		}else{//修改保存
			
			$.ajax({
				url:'${ctx}/order!addProduceOrder.action',
				type:"post",
				data:$('#myForm').serialize(),
				dataType:"json",
				success:function(da){
					
					if(da.error){
						alert(da.error);
					}
					
					if(da.suc){
						alert("保存成功");
						api.reload();
					}
					
				}
				
			});
			
			
			
		}
		


			
	});
	
	
	
})







