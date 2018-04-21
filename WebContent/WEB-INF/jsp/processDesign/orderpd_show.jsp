
<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%String contextPath = request.getContextPath();%> 
<c:set var="ctx" value="<%=contextPath %>"/>
<html>
 <head>
    
  <title>工艺路线设计</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9,chrome=1">
    <meta name="author" content="">
    <link href="${ctx }/scripts/plugins/flowDesign/css/bootstrap/css/bootstrap.css?2025" rel="stylesheet" type="text/css" />
    	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
    <!--[if lte IE 6]>
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/css/bootstrap/css/bootstrap-ie6.css?2025">
    <![endif]-->
    <!--[if lte IE 7]>
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/css/bootstrap/css/ie.css?2025">
    <![endif]-->
    <link href="${ctx }/scripts/plugins/flowDesign/css/site.css?2025" rel="stylesheet" type="text/css" />
	
<!--[if IE 10]> 
		<script type="text/javascript">
	       $(function () {
	            jsPlumb.bind("ready", function () {
	                jsPlumb.setRenderMode(jsPlumb.CANVAS);
	                jsPlumb.setMouseEventsEnabled(true);
	                //jsPlumb.Defaults.Connector = ["Flowchart", { stub: 40}]; jsPlumb.Defaults.PaintStyle = { lineWidth: 2, strokeStyle: "#888888", joinstyle: "round" };
	
	             }
	         });
	    </script> 
	<![endif]--> 
<link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/js/flowdesign/flowdesign.css"/>

<!--select 2-->
<link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/js/jquery.multiselect2side/css/jquery.multiselect2side.css"/>
<style type="text/css">
.mytr td{
	text-align: center;
}
</style>
 </head>
<body style="text-align: center;">

<!-- fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top" style="width:auto;margin-left: 5px;margin-right: 5px"> 
  <div class="navbar-inner">
    <div class="container">

      <div class="nav-collapse collapse">
        <ul class="nav">
           <!-- <a class="brand" href="/demo.html" target="_blank">DEMO</a> -->
            <li><a href="javascript:void(0);">名称：【${routeName }】</a></li>
            <li><a href="javascript:void(0);"><font color="#5bb75b" >▉</font>已完成</a></li>
            <li><a href="javascript:void(0);" ><font color="#faa732">▉</font>进行中</a></li>
            <li><a href="javascript:void(0);"><font color="white">▉</font>未开始</a> </li>
        </ul>
      </div>
      
    </div><!-- container -->
  </div>
</div> 
<!-- end fixed navbar -->



<!-- Modal -->
<div id="alertModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3>消息提示</h3>
  </div>
  <div class="modal-body">
    <p>提示内容</p>
  </div>
  <div class="modal-footer">
    <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">确定</button>
  </div>
</div>

<!-- attributeModal -->
<div id="attributeModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:900px;margin-left:-450px">
  <div class="modal-body" style="max-height:500px;"><!-- body --></div>
  <div class="modal-footer" style="padding:5px;">
    <!--a href="#" class="btn btn-danger" data-dismiss="modal" aria-hidden="true"><i class="icon-remove icon-white"></i></a-->
  </div>
</div>


<div class="container " id="flowdesign_canvas" style="position:relative; width:auto; height: 300px;margin-left: 5px;margin-right: 5px;border: 1px #cccccc solid;background: url(${ctx}/img/bg_1.png) repeat;">
    <!--div class="process-step btn" style="left: 189px; top: 340px;"><span class="process-num badge badge-inverse"><i class="icon-star icon-white"></i>3</span> 步骤3</div-->
</div> <!-- /container -->

<ul class="nav nav-tabs" id="attributeTab" style="margin-top: 10px;margin-left: 5px">
  <li class="active"><a href="#tab_process">工序加工情况</a></li>
  <li><a href="#tab_goods">领料情况</a></li>
</ul>

<div class="tab-content">
    <div class="tab-pane active" id="tab_process">
		<table class="table table-bordered table-condensed" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="5%" style="text-align: center;">序号</th>
					<th style="text-align: center;">工序名称</th>
					<th style="text-align: center;">订单数量</th>
					<th style="text-align: center;">派工数量</th>
					<th style="text-align: center;">已报工数量</th>
					<th style="text-align: center;">未报工数量</th>
					<th style="text-align: center;">质检合格数量</th>
					<th style="text-align: center;">质检不合格数量</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="step" items="${stepSituation }" varStatus="stat">
				<tr class="mytr">
					<td nowrap class="longText" title="">${stat.count}</td>
					<td nowrap class="longText" title="">${step.opName }</td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${step.number }"></fmt:formatNumber> </td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${step.dispatchedNum }"></fmt:formatNumber></td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${step.reportOkNum }"></fmt:formatNumber></td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${step.reportNotOkNum }"></fmt:formatNumber></td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${step.checkOkNum }"></fmt:formatNumber></td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${step.checkNotOkNum }"></fmt:formatNumber></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </div>
    
    <div class="tab-pane" id="tab_goods">
		<table class="table table-bordered table-condensed" contenteditable="false">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="5%" style="text-align: center;">序号</th>
					<th style="text-align: center;">工序名称</th>
					<th style="text-align: center;">物料名称</th>
					<th style="text-align: center;">物料编码</th>
					<th style="text-align: center;">规格型号</th>
					<th style="text-align: center;">应领数量</th>
					<th style="text-align: center;">已领数量</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="meterial" items="${meterialOut }" varStatus="stat">
				<tr class="mytr">
					<td nowrap class="longText" title="">${stat.count}</td>
					<td nowrap class="longText" title="${meterial.opName }">${meterial.opName }</td>
					<td nowrap class="longText" title="${meterial.goodsName }">${meterial.goodsName } </td>
					<td nowrap class="longText" title="${meterial.goodsCode }">${meterial.goodsCode } </td>
					<td nowrap class="longText" title="${meterial.goodsStandard }">${meterial.goodsStandard }</td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${meterial.number }"></fmt:formatNumber></td>
					<td nowrap class="longText" title=""><fmt:formatNumber type="number" minFractionDigits="2" value="${meterial.receivedNum }"></fmt:formatNumber></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </div>
</div>


<div class="navbar navbar-fixed-bottom" style="color:#666;text-align:right;padding-right:10px">
</div>

   

<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery-1.7.2.min.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/css/bootstrap/js/bootstrap.min.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery-ui/jquery-ui-1.9.2-min.js?2025" ></script>
<%-- <script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jsPlumb/jquery.jsPlumb-1.3.16-all-min.js?2025"></script> --%>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jsPlumb/bak/jquery.jsPlumb-1.4.0-all.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery.contextmenu.r2.js?2025"></script>
<!--select 2-->
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery.multiselect2side/js/jquery.multiselect2side.js?2025" ></script>
<!--flowdesign-->
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/flowdesign/flowdesign.v3.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/Math.uuid.js"></script>
<script type="text/javascript">
//默认图标
var DEFAULT_ICON = "icon-cog";
// 图标-开始节点
var ICON_START = "icon-play";
//默认样式
var DEFAULT_STYLE = "width:120px;height:30px;line-height:30px;color:#0e76a8;";
//默认新建节点的名字
var default_nodeName = "【未设置】";


var arr_updProcess = new Array();//更新的工序，存id
var arr_addProcess = new Array();//新增的工序，存节点id
var arr_delProcess = new Array();//删除的工序，存id

var process_objs = {};//工序属性详情 对象
if('${process_objs}'!=''){
	process_objs = eval('(${process_objs})');
}
var process_codeJson = {};//存放工序编码，在自动生成或手动修改时发生变化

var the_flow_id = '${route.gid}';
var the_flow_pk = '${route.pk}';
var isOrder = '${isOrder}';
var produceNumber = '${number}'; //生产数量

var the_productId="${product.gid}";//产品id
var the_productCode="${product.goodscode}" ;//产品编码
var the_productName="${product.goodsname}";//产品名称
var the_productStandard="${product.goodsstandard}";//产品规格
var the_productUnit="${product.unitName}";//产品单位
/*页面回调执行    callbackSuperDialog
    if(window.ActiveXObject){ //IE  
        window.returnValue = globalValue
    }else{ //非IE  
        if(window.opener) {  
            window.opener.callbackSuperDialog(globalValue) ;  
        }
    }  
    window.close();
*/
function callbackSuperDialog(selectValue){
     var aResult = selectValue.split('@leipi@');
     $('#'+window._viewField).val(aResult[0]);
     $('#'+window._hidField).val(aResult[1]);
    //document.getElementById(window._hidField).value = aResult[1];
    
}
/**
 * 弹出窗选择用户部门角色
 * showModalDialog 方式选择用户
 * URL 选择器地址
 * viewField 用来显示数据的ID
 * hidField 隐藏域数据ID
 * isOnly 是否只能选一条数据
 * dialogWidth * dialogHeight 弹出的窗口大小
 */
function superDialog(URL,viewField,hidField,isOnly,dialogWidth,dialogHeight)
{
    dialogWidth || (dialogWidth = 620)
    ,dialogHeight || (dialogHeight = 520)
    ,loc_x = 500
    ,loc_y = 40
    ,window._viewField = viewField
    ,window._hidField= hidField;
    // loc_x = document.body.scrollLeft+event.clientX-event.offsetX;
    //loc_y = document.body.scrollTop+event.clientY-event.offsetY;
    if(window.ActiveXObject){ //IE  
        var selectValue = window.showModalDialog(URL,self,"edge:raised;scroll:1;status:0;help:0;resizable:1;dialogWidth:"+dialogWidth+"px;dialogHeight:"+dialogHeight+"px;dialogTop:"+loc_y+"px;dialogLeft:"+loc_x+"px");
        if(selectValue){
            callbackSuperDialog(selectValue);
        }
    }else{  //非IE 
        var selectValue = window.open(URL, 'newwindow','height='+dialogHeight+',width='+dialogWidth+',top='+loc_y+',left='+loc_x+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');  
    
    }
}


var _canvas;
$(function(){

	$('#flowdesign_canvas').css('background',null);
	
    var alertModal = $('#alertModal'),attributeModal =  $("#attributeModal");
    //消息提示
    mAlert = function(messages,s)
    { 
        if(!messages) messages = "";
        if(!s) s = 30000;
        alertModal.find(".modal-body").html(messages);
        alertModal.modal('toggle');
        setTimeout(function(){alertModal.modal("hide")},s);
    }

    //属性设置
    attributeModal.on("hidden", function() {
        $(this).removeData("modal");//移除数据，防止缓存
    });
    ajaxModal = function(url,fn)
    {
        url += url.indexOf('?') ? '&' : '?';
        url += '_t='+ new Date().getTime();
        attributeModal.find(".modal-body").html('<img src="${ctx }/scripts/plugins/flowDesign/images/loading.gif"/>');
        attributeModal.modal({
            remote:url
        });
        
        //加载完成执行
        if(fn)
        {
            attributeModal.on('shown',fn);
        }

      
    }

 
    
    /*
    js 命名习惯：首字母小写 + 其它首字线大写
    */
    /*步骤数据*/
    //var processData = {"total":6,"list":[{"id":"515","flow_id":"119","process_name":"俺的沙发","process_code":"01","process_to":"516","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:305px;top:91px;"},{"id":"516","flow_id":"119","process_name":"asd","process_to":"520","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:1024px;top:181px;"},{"id":"517","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"520,521","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:178px;top:442px;"},{"id":"519","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"521","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:781px;top:409px;"},{"id":"520","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"519","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:459px;top:314px;"},{"id":"521","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:520px;top:523px;"}]};
    var designJson ;
    if('${route.designJson}'!=''){
    	designJson = eval('(${route.designJson})');
    }
    if('${nodeTotal}'!=''){
    	nodeTotal = eval('(${nodeTotal})');
    }
    
    var processData = {"total":nodeTotal,"list":designJson};

    /*创建流程设计器*/
    _canvas = $("#flowdesign_canvas").Flowdesign({
    				"hideDraggable":true,
                      "processData":processData
                      /*,mtAfterDrop:function(params)
                      {
                          //alert("连接："+params.sourceId +" -> "+ params.targetId);
                      }*/
                      /*画面右键*/
                      ,canvasMenus:{
                        "cmAdd": function(t) {
                            var mLeft = $("#jqContextMenu").css("left"),mTop = $("#jqContextMenu").css("top");
                        	var style = DEFAULT_STYLE+"left:"+mLeft+";top:"+mTop+";";	
                            var p_obj = {};
                            var uid = Math.uuid();
                            p_obj['id']=uid;//这里在js生成uuid
                            p_obj['flow_id']=the_flow_id;
                            p_obj['process_name']=default_nodeName;
                            p_obj['process_code']="";
                            p_obj['process_to']="";
                            p_obj['icon']=DEFAULT_ICON;
                            p_obj['style']=style;
                            if(!_canvas.addProcess(p_obj))//添加
                            {
                                mAlert("绘制节点异常，添加失败");
                            }else{
                            	arr_addProcess.push(uid);
                            	var add_o = {};
                            	add_o['base']={'processCode':'','processName':p_obj['process_name'],'routeCid':uid,'standardProcessId':'','processIndex':'','process_to':[]};
                            	process_objs[uid]=add_o;
                            	//_canvas.updateProcessName('0','ddd');
                            }
                        },
                        "cmSave": function(t) {
                        	saveFlow(true,false);
                        },
                        "cmGencode": function(t) {
                        	gencode();//生成工序编号
                        	
                        },
                     /*    "cmSetRoute": function(t) {
                        	//设置工艺路线
                        	var url = "${ctx}/wms/basepd_toSetRouteInfo.emi?routeId="+the_flow_id;
                        	ajaxModal(url,function(){
                            	//alert('加载完成执行')
                          	});
                        	
                        }, */
                         //刷新
                        "cmRefresh":function(t){
                        	page_reload();
                        	//location.reload();
                            // _canvas.refresh();
                        },
                        /*"cmPaste": function(t) {
                            var pasteId = _canvas.paste();//右键当前的ID
                            if(pasteId<=0)
                            {
                              alert("你未复制任何步骤");
                              return ;
                            }
                            alert("粘贴:" + pasteId);
                        },*/
                        "cmHelp": function(t) {
                            mAlert('<ul><li><a href="" target="_blank">流程设计器 </a></li></ul>',20000);
                        }
                       
                      }
                      /*步骤右键*/
                      ,processMenus: {
                          
                          "pmBegin":function(t)
                          {
                              var activeId = _canvas.getActiveId();//右键当前的ID
                              var url = "${ctx}/flow/flow_setNodeFirst.emi";
                              $.post(url,{"flow_id":the_flow_id,"process_id":activeId},function(data){
                                  if(data.status==1)
                                  {
                                      //清除步骤
                                      //_canvas.delProcess(activeId);
                                      //清除连接   暂时先保存设计 + 刷新 完成
                                  	  saveFlow(false,true);
                                  }else{
                                  	  mAlert("设置失败");
                                  	  page_reload();
                                  }
                                  
                              },'json');
                          },
                          /*"pmAddson":function(t)//添加子步骤
                          {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                          },
                          "pmCopy":function(t)
                          {
                              //var activeId = _canvas.getActiveId();//右键当前的ID
                              _canvas.copy();//右键当前的ID
                              alert("复制成功");
                          },*/
                          "pmDelete":function(t)
                          {
                              if(confirm("你确定删除工序吗？"))
                              {
                                    var activeId = _canvas.getActiveId();//右键当前的ID
                                    if(_canvas.delProcess(activeId)){
                                    	arr_delProcess.push(activeId);
                                    	delete process_objs[activeId];
                                    }
                              }
                          },
                          "pmAttribute":function(t)
                          {
                              var activeId = _canvas.getActiveId();//右键当前的ID
                              var process = "";//工序对象的json字符串
                              if(process_objs[activeId]){
                            	  process = 1;//2016-4-21注释：10个月前写的，干嘛的？先不动它。
                              }
                              
                              //由于编码可能自动生成，  所以传
                              var code = process_codeJson[activeId] || ''; 
                              var url = "${ctx}/wms/basepd_attributePage.emi?routecId="+activeId+"&process="+process+"&processCode="+code+"&isOrder="+isOrder;
                              ajaxModal(url,function(){
                                	//alert('加载完成执行')
                              });
                          },
                          "pmForm": function(t) {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                                var url = "/Flowdesign/attribute/op/form/id/"+activeId+".html";
                                ajaxModal(url,function(){
                                    //alert('加载完成执行')
                                });
                          },
                          "pmJudge": function(t) {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                                var url = "/Flowdesign/attribute/op/judge/id/"+activeId+".html";
                                ajaxModal(url,function(){
                                    //alert('加载完成执行')
                                });
                          },
                          "pmSetting": function(t) {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                                var url = "/Flowdesign/attribute/op/style/id/"+activeId+".html";
                                ajaxModal(url,function(){
                                    //alert('加载完成执行')
                                });
                          } 
                      }
                      ,fnRepeat:function(){
                        //alert("步骤连接重复1");//可使用 jquery ui 或其它方式提示
                        mAlert("工序连接重复了，请重新连接");
                        
                      }
                      ,fnClick:function(){
                          var activeId = _canvas.getActiveId();
                          //mAlert("查看步骤信息 " + activeId);
                      }
                      ,fnDbClick:function(){
                          //和 pmAttribute 一样
                    	 /*  var activeId = _canvas.getActiveId();//右键当前的ID
                          var process = "";//工序对象的json字符串
                          if(process_objs[activeId]){
                        	  process = 1;//2016-4-21注释：10个月前写的，干嘛的？先不动它。
                          }
                          
                          //由于编码可能自动生成，  所以传
                          var code = process_codeJson[activeId] || ''; 
                          var url = "${ctx}/wms/basepd_attributePage.emi?routecId="+activeId+"&process="+process+"&processCode="+code+"&isOrder="+isOrder;
                          ajaxModal(url,function(){
                            	//alert('加载完成执行')
                          }); */
                      }
                  });

    
    var page_processJson = _canvas.getProcessJson();//连接信息
    var maxTop = 0;
    var minTop = 9999;
    for(key in page_processJson){
    	var obj = page_processJson[key];
    	if(obj.top>maxTop){
    		maxTop = obj.top;
    	}
    	if(obj.top<minTop){
    		minTop = obj.top;
    	}
    }
    $('#flowdesign_canvas').css('height',(maxTop+80)+'px');
    $('#flowdesign_canvas').css('margin-top',(-minTop+80)+'px');
    
    // 按钮颜色
    $('.process-step').removeClass('btn');
    var statusJson = '${stats}';
    var statusArray = [];
    if(statusJson!=''){
    	statusArray = eval('('+statusJson+')');
    }
    for(var i=0;i<statusArray.length;i++){
    	var thisGid = statusArray[i].gid;
    	var thisStatus = statusArray[i].stats;
    	if(thisStatus=='0'){
    		$('#window'+thisGid).addClass('btn');
    		$('#window'+thisGid).children("span").children("i").removeClass("icon-cog").addClass("icon-remove");//未完成显示图标为叉
    	}
		if(thisStatus=='1'){
			$('#window'+thisGid).addClass('btn-warning');
			$('#code_window'+thisGid).css('color','white');
            $('#name_window'+thisGid).css('color','white');
    	}
		if(thisStatus=='5'){
			$('#window'+thisGid).addClass('btn-success');
			$('#window'+thisGid).children("span").children("i").removeClass("icon-cog").addClass("icon-ok");//已完成显示图标为对勾
			$('#code_window'+thisGid).css('color','white');
            $('#name_window'+thisGid).css('color','white');
		}
    }
/*    $('.process-step').each(function(i){
    	var stepid = this.id;
    	if(i==0){
    		$(this).addClass('btn-success');
    		$('#code_'+stepid).css('color','white');
            $('#name_'+stepid).css('color','white');
    	}
    	if(i==1){
    		$(this).addClass('btn-warning');
    		$('#code_'+stepid).css('color','white');
            $('#name_'+stepid).css('color','white');
    	}
    	if(i==2){
    		$(this).addClass('btn');
    	}
        
    }); */
    
    /*保存*/
    $("#flow_save").bind('click',function(){
    	saveFlow(true,false);
    });
    
  	//取消绑定删除确认操作
    jsPlumb.unbind("click");
    
    /*清除*/
    $("#flow_clear").bind('click',function(){
        if(_canvas.clear())
        {
            //alert("清空连接成功");
            mAlert("清空连接成功，你可以重新连接");
        }else
        {
            //alert("清空连接失败");
            mAlert("清空连接失败");
        }
    });


  
});



</script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/flowdesign/attribute.js"></script>
</body>
</html>