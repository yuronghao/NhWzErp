

//产品序号
function getOrder(){
	var trs=$('.contentTr');
	$('#cSize').val(trs.length);
	for(var i=0;i<trs.length;i++){

		trs.eq(i).children().first().children().val(i+1);
		trs.eq(i).children('.flag').children().attr('id','flag'+(i+1));
		trs.eq(i).children('.pdoCGid').children().attr('id','pdoCGid'+(i+1));
		trs.eq(i).children('.gGoodsUid').children().attr('id','gGoodsUid'+(i+1));
		trs.eq(i).children('.gGoodsCode').children().attr('id','gGoodsCode'+(i+1));
		trs.eq(i).children('.gGoodsName').children().attr('id','gGoodsName'+(i+1));
		trs.eq(i).children('.gGoodsType').children().attr('id','gGoodsType'+(i+1));
		trs.eq(i).children('.nNumber').children().attr('id','nNumber'+(i+1));
		trs.eq(i).children('.gGoodsUnit').children().attr('id','gGoodsUnit'+(i+1));
		trs.eq(i).children('.dPlanBegin').children().attr('id','dPlanBegin'+(i+1));
		trs.eq(i).children('.dPlanComplete').children().attr('id','dPlanComplete'+(i+1));
		trs.eq(i).children('.createCaiLiao').children().attr('id','createCaiLiao'+(i+1));
		
		trs.eq(i).children('.flag').children().attr('name','flag'+(i+1));
		trs.eq(i).children('.pdoCGid').children().attr('name','pdoCGid'+(i+1));
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





// 修改指定行的flag值 flag=0原始数据, flag=1修改后的数据,flag=2新添加的数据
function changeFlag(obj,val){
	
	var index = $("#"+obj.id).parent().parent().children().first().children().val();
	document.getElementById("flag"+index).value=val;
	
}



//记录被删除的明细gid，存在$('#deleteGids')标签中
var deleteGids="";
//各种事件
$(function(){

	$('body').on('click','.fenLuTd',function(){
		
		$(this).children('.fenLuTool').show();
		$('.fenLuTd').mouseleave(function(){
			$(this).children('.fenLuTool').hide();
		});
		
	});

	
	
	//操作 添加
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
		'<td class="billBodyTd flag" style="display: none;"><input class="center" type="text" name="" id=""  value="2"/></td>'+
		'<td class="billBodyTd pdoCGid" style="display:none;"><input class="center" type="text" name="" id=""  value="${orderC.gid }"/></td>'+
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
	});
	
	
	//操作 删除
	$('body').on('click','.delete',function(){
		
		var flag = true;
		flag=confirm("确定是否删除？");

		var trs=$('.contentTr');
		if(flag && trs.length>1){
			
			var delId = $(this).attr('gid');
			deleteGids+=delId+",";
			
			$(this).parent('.fenLuTool').parent('.fenLuTd').parent().remove();
			getOrder();
		}else{
			alert("不允许删除");
			return;
		}
		
		
	});
	
	
	$('body').on('click','.createMeterial',function(){
		$('#orderCgid').val($(this).attr('gid'));	
	});
	
	
	
	
	//行记录物料编码获得焦点事件
	$('body').on('focus','.wlHelp',function(){
		var order=$(this).parent().parent().children().first().children().val();
		$('#saveOrder').val(order);
	});
	
	//行记录物料编码点击事件
	$('body').on('click','.wlHelp',function(e){
		var index=$(this).parent().parent().children().first().children().val();
		
		$.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'物料查询',
		    width: '1000px',
			height: '500px',
			content: 'url:goods!toGoodsHelp.action',
		});
		
		var theFlag =$('#flag'+index).val();
		if(theFlag=='0'){
			document.getElementById("flag"+index).value='1';
		}
		
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
	
	
	
	//操作添加材料，弹出事件
	$('body').on('click','.createMeterial',function(e){		
		
		var gid=$(this).attr('gid');
		$.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'材料管理',
		    width: '900px',
			height: '600px',
			content: 'url:order!toMeterialAdd.action?gid='+gid,
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
		
		//激活部门事件，操作事件
		$('#departmentName').addClass("departmentName");
		$('.fenLuTh').removeAttr("style");
		$('.fenLuTd').removeAttr("style");
		
		//激活日期 事件
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanBegin').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanComplete').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('#dRecordDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		
		//激活物品事件
		$('.gGoodsCode').children().addClass("wlHelp");
		
		
		$.ajax({
			url:'${ctx}/order!toAddProduceOrderWhenAddbtn.action',
			type:'post',
			data:{},
			dataType:'json',
			success:function(da){
				
				$('#cBillCode').val(da.billId);
				$('#dBillDate').val(da.nowDate);
				$('#gRecordPersonUid').val(da.userId);
				$('#recordPersonName').val(da.userName);
				$('#dRecordDate').val(da.nowDate);
				
			}
			
			
		})
		
		addBtn();
		
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
		
		//激活日期 事件
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('#dBillDate').attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanBegin').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		$('.dPlanComplete').children().attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd'})");
		

		//激活物品事件
		$('.gGoodsCode').children().addClass("wlHelp");
		
		revBtn();
		
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
					
					if(da.error==0){
						alert("保存失败");
					}
					
					if(da.suc){
						saveBtn();
						alert("保存成功");
						window.location.href="${ctx}/order!toAddProduceOrder.action"
					}
					
				},

				
			});
		}else{//修改保存
			
			if(deleteGids!=''){
				deleteGids = deleteGids.substring(0, deleteGids.length-1);//需要删除的数据gid，去掉最后的逗号
				document.getElementById("deleteGids").value=deleteGids;
			}
			
			$.ajax({
				url:'${ctx}/order!doEditProduceOrder.action',
				type:"post",
				data:$('#myForm').serialize(),
				dataType:"json",
				success:function(da){
					
					if(da.error==0){
						alert("保存失败");
					}
					
					if(da.suc){
						saveBtn()
						alert("保存成功");
						
					}
					
				}
				
			});
			
		}
				
	});
	
	//删除
	$('#delBtn').click(function(){
		
		if(confirm("确定删除吗？")){
			$.ajax({
				url:'${ctx}/order!doDeleteProduceOrder.action',
				type:"post",
				data:{gId:$('#gid').val()},
				dataType:"json",
				success:function(da){

					if(da.success==1){
						alert("删除成功");
						window.location.href="${ctx}/order!toAddProduceOrder.action"
					}
					
					if(da.success==0){
						alert("删除失败");
						
					}
					
				}
				
			});
		}
		
	})
	
	
	
	$('#giveUpBtn').click(function(){
		giveUpBtn();
	})
	
	
	
	
	
})







