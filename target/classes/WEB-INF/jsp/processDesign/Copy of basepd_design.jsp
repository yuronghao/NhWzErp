
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
    <meta name="author" content="leipi.org">
    <link href="${ctx }/scripts/plugins/flowDesign/css/bootstrap/css/bootstrap.css?2025" rel="stylesheet" type="text/css" />
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

 </head>
<body>


	


<!-- fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <div class="pull-right">
        <button class="btn btn-info" type="button" id="flow_save">保存设计</button>
        <button class="btn btn-danger" type="button" id="flow_clear">清空连接</button>
      </div>

      <div class="nav-collapse collapse">
        <ul class="nav">
           <!-- <a class="brand" href="/demo.html" target="_blank">DEMO</a> -->
            <li><a href="javascript:void(0);">正在设计【${routeName }】</a></li>
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



<!--contextmenu div-->
<div id="processMenu" style="display:none;">
  <ul>
   <!--  <li id="pmBegin"><i class="icon-play"></i>&nbsp;<span class="_label">设为第一步</span></li> -->
    <li id="pmAttribute"><i class="icon-cog"></i>&nbsp;<span class="_label">工序设置</span></li>
    <li id="pmDelete"><i class="icon-trash"></i>&nbsp;<span class="_label">删除</span></li>
    
  </ul>
</div>
<div id="canvasMenu" style="display:none;">
  <ul>
  	<li id="cmAdd"><i class="icon-plus"></i>&nbsp;<span  title="增加一道工序" class="_label">添加工序</span></li>
<!--   	<li id="cmSetRoute"><i class="icon-cog"></i>&nbsp;<span title="设置工艺路线的基本信息" class="_label">基本信息</span></li> -->
  	<li id="cmGencode"><i class="icon-th-large"></i>&nbsp;<span title="自动生成编号、上道工序转入" class="_label">生成序号</span></li>
    <li id="cmSave" ><i class="icon-ok"></i>&nbsp;<span title="保存工艺路线的设计" class="_label">保存设计</span></li>
    
     <!-- <li id="cmRefresh"><i class="icon-refresh"></i>&nbsp;<span class="_label">刷新 F5</span></li>   -->
  </ul>
</div>
<!--end div--> 

<div class="container mini-layout" id="flowdesign_canvas">
    <!--div class="process-step btn" style="left: 189px; top: 340px;"><span class="process-num badge badge-inverse"><i class="icon-star icon-white"></i>3</span> 步骤3</div-->
</div> <!-- /container -->

    
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

var arr_taskProcess = new Array();//变更订单工艺路线用，删除节点时涉及到的上一道正在进行的工序

var process_objs = {};//工序属性详情 对象
if('${process_objs}'!=''){
	process_objs = eval('(${process_objs})');
}
var process_codeJson = {};//存放工序编码，在自动生成或手动修改时发生变化

var the_flow_id = '${route.gid}';
var the_flow_pk = '${route.pk}';
var isOrder = '${isOrder}';	//是否是订单
var orderId = '${orderId}';	//订单id
var changeOrder = '${changeOrder}';//是否是改制订单
var produceNumber = '${number}'; //生产数量

var the_productId="${product.gid}";//产品id
var the_productCode="${product.goodscode}" ;//产品编码
var the_productName="${product.goodsname}";//产品名称
var the_productStandard="${product.goodsstandard}";//产品规格
var the_productUnit="${product.unitName}";//产品单位

var _canvas;
var canSave = true;//是否可以点击保存，默认可以

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

function resetBindEvent(){
	//重新绑定连线点击删除事件
    jsPlumb.unbind("click");
    jsPlumb.bind("click", function(c) {
        if(confirm("你确定取消连接吗?")){
        	//连线的工序id
        	var srcId = c.sourceId.replace('window','');
        	var tarId = c.targetId.replace('window','');
        	//根据工序id检查是否已有任务在进行
        	$.ajax({
					url:'${ctx}/wms/orderpd_checkProcessTask.emi?routeCid='+tarId,
					type:"post",
					dataType:'text',
					success:function(data){			
						if(data=='has'){//有任务，不允许操作
							mAlert("连线目标工序节点已有任务，不允许删除");
						}else{
							//删除前如果上道工序有任务，存一下其id  arr_taskProcess
							//上道id
							var processJson = _canvas.getProcessJson();//连接信息
							for(routecId in  processJson){
								var process_to = processJson[routecId]['process_to'];
								for(var i=0;i<process_to.length;i++){
									if(process_to[i]==tarId){
										//找到上道工序id
										//根据工序id检查是否已有任务在进行
								    	$.ajax({
												url:'${ctx}/wms/orderpd_checkProcessTask.emi?routeCid='+routecId,
												type:"post",
												dataType:'text',
												success:function(data){			
													if(data=='has'){//有任务，不允许操作
														arr_taskProcess.push(routecId);
													}
												}		
										 });
									}
								}
							}
							jsPlumb.detach(c);
						}
					}		
			 });
        	//如果删除连线的源节点有任务，说明目标节点可能触发了领料任务，需要删除并按需要重新生成新连接的节点的领料任务
        	
        	
        }
    });
    
 	//绑定添加连接操作。画线-input text值  拒绝重复连接
 	jsPlumb.unbind("jsPlumbConnection");
    jsPlumb.bind("jsPlumbConnection", function(info) {
    	//连线的工序id
    	var srcId = info.sourceId.replace('window','');
    	var tarId = info.targetId.replace('window','');
    	//根据工序id检查是否已有任务在进行
    	$.ajax({
				url:'${ctx}/wms/orderpd_checkProcessTask.emi?routeCid='+tarId,
				type:"post",
				dataType:'text',
				success:function(data){			
					if(data=='has'){//有任务，不允许操作
						jsPlumb.detach(info);
				    	mAlert("目标工序节点已有任务，不允许连接");
					}else{
						setConnections(info.connection);
					}
				}		
		 });
    });
}

//工序删除（订单工艺，需要校验是否有任务、上道是否有任务）
function checkToDelete(activeId){
	$.ajax({
			url:'${ctx}/wms/orderpd_checkProcessTask.emi?routeCid='+activeId,
			type:"post",
			dataType:'text',
			success:function(data){			
				if(data=='has'){//有任务，不允许操作
					mAlert("该工序节点已有任务，不允许删除");
				}else{
					//删除前如果上道工序有任务，存一下其id  arr_taskProcess
					//上道id
					var processJson = _canvas.getProcessJson();//连接信息
					for(routecId in  processJson){
						var process_to = processJson[routecId]['process_to'];
						for(var i=0;i<process_to.length;i++){
							if(process_to[i]==activeId){
								//找到上道工序id
								//根据工序id检查是否已有任务在进行
						    	$.ajax({
										url:'${ctx}/wms/orderpd_checkProcessTask.emi?routeCid='+routecId,
										type:"post",
										dataType:'text',
										success:function(data){			
											if(data=='has'){//有任务，不允许操作
												arr_taskProcess.push(routecId);
											}
										}		
								 });
							}
						}
					}
	                if(_canvas.delProcess(activeId)){
	                	arr_delProcess.push(activeId);
	                	delete process_objs[activeId];
	                }
				}
				
			}		
	 });
}

$(function(){
	
	//检测是否已有进行的任务,有则隐藏清空连接按钮、重新绑定连线事件/* 隐藏保存按钮 */
	if(isOrder=='1'){
		$.ajax({
			url:'${ctx}/wms/orderpd_checkDoingTask.emi?orderId='+orderId+'&type=0',
			type:"post",
			dataType:'text',
			success:function(da){				
				if(da=='1'){
					//隐藏按钮
					//$('#flow_save').hide();
					//$('#cmSave').remove();
					$('#cmGencode').remove();
					$('#flow_clear').hide();
					//重置某些事件
					resetBindEvent();
				}		
			}				
		});
	}
	
	
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
                        	if(canSave){
                        		saveFlow(true,false);
                        	}else{
                        		mAlert("保存中，请稍后...");
                        	}
                        	
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
                        	  //判断
                              if(confirm("你确定删除工序吗？"))
                              {
                            	//如果是订单工艺，检查是否已经有任务在进行
                            	var activeId = _canvas.getActiveId();//右键当前的ID
                           		 if(isOrder=='1'){
                           			 checkToDelete(activeId);
                           		 }else{
                                     if(_canvas.delProcess(activeId)){
                                     	arr_delProcess.push(activeId);
                                     	delete process_objs[activeId];
                                     }
                           		 }
                                    
                              }
                          },
                          "pmAttribute":function(t)
                          {
                              var activeId = _canvas.getActiveId();//右键当前的ID
                              //打开页面
                              showAttributePage(activeId);
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
                    	  var activeId = _canvas.getActiveId();//右键当前的ID
                    	  //打开页面
                    	  showAttributePage(activeId);
                      }
                  });

    

    
    /*保存*/
    $("#flow_save").bind('click',function(){
    	saveFlow(true,false);
    });
    
    
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

/**格式化数字为一个定长的字符串，前面补０ 
*参数: 
* Source 待格式化的字符串 
* Length 需要得到的字符串的长度 
*/ 
function formatNum(Source,Length){ 
	var strTemp=""; 
	for(i=1;i<=Length-Source.length;i++){ 
		strTemp+="0"; 
	} 
	return strTemp+Source; 
} 
//刷新页面
function page_reload()
{
    //location.reload();
    location.href="${ctx}/flow/flow_toDesignPage.emi?flowId=${flow.gid}";
}

//打开属性设置页
function showAttributePage(activeId){
	 var process = "";//工序对象的json字符串
     if(process_objs[activeId]){
   	  	process = 1;//2016-4-21注释：10个月前写的，干嘛的？先不动它。
     }
	 //如果是订单工艺，检查节点是否已经有任务在进行
	 if(isOrder=='1'){
		 $.ajax({
				url:'${ctx}/wms/orderpd_checkProcessTask.emi?routeCid='+activeId,
				type:"post",
				dataType:'text',
				success:function(data){			
					var noEdit = 1;
					if(data=='none'){//没有任务，可以正常操作
						noEdit = 0;
					}else if(data=='has'){//有任务，不允许操作
						noEdit = 1;
					}else{
						noEdit = 1;
					}
					//由于编码可能自动生成，  所以传
				     var code = process_codeJson[activeId] || ''; 
				     var url = "${ctx}/wms/basepd_attributePage.emi?routecId="+activeId+"&process="+process+"&processCode="+code+"&isOrder="+isOrder+"&noEdit="+noEdit;
				     ajaxModal(url,function(){
				       	//alert('加载完成执行')
				     });
				}		
		 });
	 }else{
		//由于编码可能自动生成，  所以传
	     var code = process_codeJson[activeId] || ''; 
	     var url = "${ctx}/wms/basepd_attributePage.emi?routecId="+activeId+"&process="+process+"&processCode="+code+"&isOrder="+isOrder+"&noEdit=0";
	     ajaxModal(url,function(){
	       	//alert('加载完成执行')
	     });
	 }
     
}

//禁用保存按钮
function disabledSave(){
	$('#flow_save').html('保存中...');
	$('#flow_save').attr("disabled","disabled");
	canSave = false;
}
//启用保存按钮
function enabledSave(){
	$('#flow_save').html('保存设计');
	$('#flow_save').removeAttr("disabled");
	canSave = true;
}
//保存流程
function saveFlow(showTip,refresh){
	disabledSave();
	var processInfo = _canvas.getProcessInfo();//连接信息
	var proc_json = eval('('+processInfo+')');
	//校验数据
	var msg = checkFlowData();
	if(msg!='success'){
		mAlert(msg);
		enabledSave();
		return;
	}
	
	
	/*
	*修正上道工序的数据
	*/
	for(routecId in  proc_json){
		//可视化插件解析的上道工序id
		var canvas_pre_id = new Array();
		for(procId in proc_json){
			for(var n=0;n<proc_json[procId].process_to.length;n++){
				var to_id = proc_json[procId].process_to[n];
				if(to_id==routecId){
					canvas_pre_id.push(procId);
					break;
				}
			}
		}
		//json中的上道工序
		var attr_pre = process_objs[routecId]['attrPreProc'] || [];
		var needUpdate = false;
		for(var i=0;i<canvas_pre_id.length;i++){
			var hasJson = false;
			for(var j=0;j<attr_pre.length;j++){
				if(attr_pre[j]['routeCid']==canvas_pre_id[i]){
					//原json中有值的，则忽略
					hasJson = true;
					break;
				}
			}
			if(!hasJson){
				//原json没有值，则插入
				var preObj = {};
	           	 var preId = canvas_pre_id[i];
	           	 preObj['routeCid'] = preId;
	           	 preObj['baseUse'] = 1;
	           	 preObj['baseQuantity'] = 1;
	           	 preObj['number'] = 1;
	           	attr_pre.push(preObj);
	           	needUpdate = true;
			}
		}
		if(needUpdate){
			push2updProcess(routecId,true);
		}
		process_objs[routecId]['attrPreProc'] = attr_pre;
	}
	
	
	 var url = "${ctx}/wms/basepd_saveProcessData.emi";
	if(isOrder=='1'){
		url = "${ctx}/wms/orderpd_saveProcessData.emi";
	}
   alert(JSON.stringify(arr_taskProcess));
   return;
    $.post(url,{
	    	"flow_id":the_flow_id,
	    	"process_info":processInfo,
	    	"productId":the_productId,
	    	"number":produceNumber,
	    	"arr_updProcess":JSON.stringify(arr_updProcess),
	    	"arr_addProcess":JSON.stringify(arr_addProcess),
	    	"arr_delProcess":JSON.stringify(arr_delProcess),
	    	"process_objs":JSON.stringify(process_objs),
	    	"process_codeJson":JSON.stringify(process_codeJson),
	    	"arr_taskProcess":JSON.stringify(arr_taskProcess)
    	},function(data){
    		if(data=='success'){
	        	//保存完之后，把arr_addProcess中的值清空
	        	arr_addProcess.length = 0;
	        	
	        	if(showTip){
	        		if(isOrder=='1'){
	        			mAlert("保存成功");
	        			enabledSave();
	        		}else{
	        			//生成BOM到U8
	    	        	$.post('${ctx}/wms/basepd_saveBomToU8.emi',{
	    	    	    	"process_objs":JSON.stringify(process_objs),
	    	    	    	"process_info":processInfo,
	    	    	    	"productId":the_productId,
	    	    	    	"productCode":the_productCode
	    	        	},function(data2){
	    		            if(data2=='success'){
	    		            	if(showTip){
	    		            		mAlert("保存成功");
	    		            		enabledSave();
	    		            	}
	    		            }else{
	    		            	mAlert("本地数据保存成功，但生成用友BOM数据失败");
	    		            	enabledSave();
	    		            }
	    		        	
	    		        },'text');
	        		}
	        	} 
	        	if(refresh){
	        		page_reload();
	        	}
	        }else{
	        	mAlert("保存失败,请联系相关技术人员");
	        	enabledSave();
	        }
	    	
	    },'text');
}

function checkFlowData(){
	var msg = "success";
	var processJson = _canvas.getProcessJson();//连接信息
	/*
	 * 1 检测是否有未设置工序的节点
	 */
	var count = 0;
	for(var pid in process_objs){
		var standardProcessId = process_objs[pid]['base']['standardProcessId'];
		if(!standardProcessId || standardProcessId==''){
			msg = "警告：存在未设置工序的节点！";
			return msg;
		}
		count++;
	}
	/*
	 *	2没有节点
	 */
	if(count==0){
		msg = "警告：没有工序节点！";
		return msg;
	}
	/*
	 * 3检测是否有为连线的节点、或下级是多个节点
	 */
	var exi_nolink = false;//是否有未连线的节点
	var exi_multinext = false;//是否有下级是多个节点的
	$('.process-step').each(function(i){
        var sourceId = $(this).attr('process_id');
        var toLen = processJson[sourceId]['process_to'].length;//后续节点长度
       	var pre_e = false;
       	$('.process-step').each(function(j){
       		var sourceId_in = $(this).attr('process_id');
       		if(sourceId!=sourceId_in && processJson[sourceId_in]['process_to'].indexOf(sourceId)>=0){
       			pre_e = true; 
       		}
       	});
       	if(toLen>1){
       		exi_multinext = true;
       	}
       	if(!pre_e){//没有上级节点
       		if(toLen==0 ){//后续没有节点时，说明没有连线
       			exi_nolink = true;
           	}
       	}
	});
	if(exi_multinext && count>1){
		msg = "警告：不允许将一个工序分支到多个工序！";
		return msg;
	}
	if(exi_nolink && count>1){
		msg = "警告：存在未连线的工序节点！";
		return msg;
	}
	/*
	 * 4检测是否有死循环
	 */
	 var endless = false;
	 $('.process-step').each(function(i){
	        var sourceId = $(this).attr('process_id');
	        var toProcessId = processJson[sourceId]['process_to'];
	        var toLen = processJson[sourceId]['process_to'].length;//后续节点长度
	       	if(toLen>0){
	       		for(var i=0;i<toLen;i++){
	       			endless = endless || checkEndlessLoop(sourceId,toProcessId[i],processJson);
	       			if(endless){
	       				break;
	       			}
	       		}
	       	}
	});
	if(endless){ 
		msg = "警告：存在无限循环连接！";
		return msg;
	}
	/*
	 * 5检测订单工艺路线实际工价是否已填
	 */
	 if(isOrder=='1'){
		 for(procId in process_objs){
			 if(!process_objs[procId]['base']['realPrice'] || process_objs[procId]['base']['realPrice']=='' || parseInt(process_objs[procId]['base']['realPrice'])==0){
				 msg = "警告：存在未设置实际工价的工序节点！";
				 return msg;
			 }
		 }
	}
	 
	return msg;
}

//检测是否有死循环
function checkEndlessLoop(srcNodeId,nextNodeId,processJson){
	var endless = false;
	if(processJson[nextNodeId])	{
		var toProcessId = processJson[nextNodeId]['process_to'];
	    var toLen = toProcessId.length;//后续节点长度
	   	if(toLen>0){
	   		for(var i=0;i<toLen;i++){
	   			if(srcNodeId==toProcessId[i]){
	   				endless = true;
	   				break;
	   			}else{
	   				endless = checkEndlessLoop(srcNodeId,toProcessId[i],processJson);
	   			}
	   		}
	   	}
	}
    return endless;
}

//当是新增还是修改不明确时，可以条用此方法，vague传true
function push2updProcess(routecId,vague){
	var checkUpd = false;
	if(vague){
		var add_e = false;	//新增id中是否已存在
		for(var i=0;i<arr_addProcess.length;i++){
		 	 if(arr_addProcess[i] == routecId){
		 		add_e = true; 
				break;
		 	 }
		}
		if(!add_e){
			//不存在与新增id中，则需要检查更新id再加入其中
			checkUpd = true;
		}
	}else{
		//明确是更新时
		checkUpd = true;
	}
	 //将有修改的工序id加入数组
	 if(checkUpd){
		 var upd_e = false;//是否已存在
		 for(var i=0;i<arr_updProcess.length;i++){
		  	 if(arr_updProcess[i] == routecId){
					upd_e = true; 
					break;
		  	 }
		 }
		 if(!upd_e){ arr_updProcess.push(routecId);}
	 }
  
}
function push2addProcess(routecId){
	 //将有修改的工序id加入数组
  var add_e = false;	//是否已存在
  for(var i=0;i<arr_addProcess.length;i++){
 	 if(arr_addProcess[i] == routecId){
 		add_e = true; 
			break;
 	 }
  }
  if(!add_e){ arr_addProcess.push(routecId);}
}
function push2delProcess(routecId){
	 //将有修改的工序id加入数组
  var del_e = false;	//是否已存在
  for(var i=0;i<arr_delProcess.length;i++){
 	 if(arr_delProcess[i] == routecId){
 		del_e = true; 
			break;
 	 }
  }
  if(!del_e){ arr_delProcess.push(routecId);}
}

function gencode(){
	var processJson = _canvas.getProcessJson();//连接信息
	console.log(_canvas.getProcessInfo());
	//1：检测是否有未连线的节点  2：找到源头节点id(可能多个)  3：依次生成编码
	var exi_nolink = false;//是否有未连线的节点
	var head_p = new Array();//源头节点数组
	$('.process-step').each(function(i){
        var sourceId = $(this).attr('process_id');
        var toLen = processJson[sourceId]['process_to'].length;//后续节点长度
        /* var step_top = processJson[sourceId]['top'];
        var step_left = processJson[sourceId]['left']; && step_top!=0 && step_left!=0*/
       	var pre_e = false;
       	$('.process-step').each(function(j){
       		var sourceId_in = $(this).attr('process_id');
       		if(sourceId!=sourceId_in && processJson[sourceId_in]['process_to'].indexOf(sourceId)>=0){
       			pre_e = true; 
       		}
       	});
       	
       	if(!pre_e){//没有上级节点
       		if(toLen==0 ){//后续没有节点时，说明没有连线
       			exi_nolink = true;
           	}
       		if(toLen>0){//后续有节点，说明是源头节点
       			head_p.push(sourceId);
       		}
       	}
	});
	
	if(exi_nolink){mAlert("警告：存在未连接的节点");return;}//存在未连接的节点，中断执行
	
	//从源头开始，依次生成编码
	var subcode_start = 1;
	genProcessCode(head_p,1,subcode_start);
	function genProcessCode(pro_ids,f_code,start){
		var subStart = start;
    	for(var i=0;i<pro_ids.length;i++){
    		var code_str = formatNum(f_code+"",2);
    		var subcode_str = "";
    		if(pro_ids.length>1){
    			subcode_str = formatNum((i+subStart)+"",2);
    		}
    		var finalCode = code_str + subcode_str;
    		process_codeJson[pro_ids[i]] = finalCode;
    		var finalCode_str = ''+finalCode+'';
    		_canvas.updateProcessCode(pro_ids[i],finalCode_str);
    		if(process_objs[pro_ids[i]]){
    			//更新节点对象
    			process_objs[pro_ids[i]]['base']['processIndex']=finalCode_str;
    		}
    		//迭代，生成后续节点的代码
    		genProcessCode(processJson[pro_ids[i]]['process_to'],f_code+1,start);
    		start = start + processJson[pro_ids[i]]['process_to'].length;
    		push2updProcess(pro_ids[i],true);//更新了的id(不明确是否是更新的id)
    	}
	}
}
 
/* 
{
    "7C04CFF9-6E91-42BF-AB05-16E2BBA43B90": {
        "base": {
            "processName": "工序1",
            "processCode": "000001",
            "processIndex": "0102",
            "routeCid": "子表id",
            "standardProcessId": "标准工序id",

        },
        "attrPreProc": [
            {
                "routeCid": "",
                "baseUse": "3",
                "baseQuantity": "2",
                "standardUse": "1.5"
            }
        ],
        "attrGoods": [
            {
                "goodsId": "",
                "goodscode": "",
                "goodsname": "",
                "goodsstandard": "",
                "unitName": "",
                "baseUse": "2",
                "baseQuantity": "1"
            }
        ],
        "attrDispatching": [
            {
                "workCenterId": "",
                "wccode": "",
                "wcname": "",
                "depCode": "",
                "depName": "",
				"objType": "1",
            }
        ]
    }
}
 */

</script>

</body>
</html>