
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
    
  <title>流程设计器 </title>

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
            <li><a href="javascript:void(0);">正在设计【${flow.flowName }】</a></li>
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
<div id="attributeModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:800px;margin-left:-350px">
  <div class="modal-body" style="max-height:500px;"><!-- body --></div>
  <div class="modal-footer" style="padding:5px;">
    <!--a href="#" class="btn btn-danger" data-dismiss="modal" aria-hidden="true"><i class="icon-remove icon-white"></i></a-->
  </div>
</div>



<!--contextmenu div-->
<div id="processMenu" style="display:none;">
  <ul>
    <li id="pmBegin"><i class="icon-play"></i>&nbsp;<span class="_label">设为第一步</span></li>
    <!--li id="pm_addson"><i class="icon-plus"></i>&nbsp;<span class="_label">添加子步骤</span></li-->
    <!--li id="pm_copy"><i class="icon-check"></i>&nbsp;<span class="_label">复制</span></li-->
    <li id="pmAttribute"><i class="icon-cog"></i>&nbsp;<span class="_label">属性</span></li>
   <!--  <li id="pmForm"><i class="icon-th"></i>&nbsp;<span class="_label">表单字段</span></li> -->
   <!--  <li id="pmJudge"><i class="icon-share-alt"></i>&nbsp;<span class="_label">转出条件</span></li> -->
   <!--  <li id="pmSetting"><i class=" icon-wrench"></i>&nbsp;<span class="_label">样式</span></li> -->
    <li id="pmDelete"><i class="icon-trash"></i>&nbsp;<span class="_label">删除</span></li>
    
  </ul>
</div>
<div id="canvasMenu" style="display:none;">
  <ul>
    <li id="cmSave"><i class="icon-ok"></i>&nbsp;<span class="_label">保存设计</span></li>
    <li id="cmAdd"><i class="icon-plus"></i>&nbsp;<span class="_label">添加步骤</span></li>
     <li id="cmRefresh"><i class="icon-refresh"></i>&nbsp;<span class="_label">刷新 F5</span></li>  
    <!--li id="cmPaste"><i class="icon-share"></i>&nbsp;<span class="_label">粘贴</span></li-->
    <!-- <li id="cmHelp"><i class="icon-search"></i>&nbsp;<span class="_label">帮助</span></li> -->
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
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jsPlumb/jquery.jsPlumb-1.3.16-all-min.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery.contextmenu.r2.js?2025"></script>
<!--select 2-->
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery.multiselect2side/js/jquery.multiselect2side.js?2025" ></script>
<!--flowdesign-->
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/flowdesign/flowdesign.v3.js?2025"></script>
<script type="text/javascript">
var the_flow_id = '${flow.gid}';

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
    var alertModal = $('#alertModal'),attributeModal =  $("#attributeModal");
    //消息提示
    mAlert = function(messages,s)
    { 
        if(!messages) messages = "";
        if(!s) s = 2000;
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
    //var processData = {"total":6,"list":[{"id":"515","flow_id":"119","process_name":"俺的沙发","process_to":"516","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:305px;top:91px;"},{"id":"516","flow_id":"119","process_name":"asd","process_to":"520","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:1024px;top:181px;"},{"id":"517","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"520,521","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:178px;top:442px;"},{"id":"519","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"521","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:781px;top:409px;"},{"id":"520","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"519","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:459px;top:314px;"},{"id":"521","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:520px;top:523px;"}]};
    var designJson = ${flow.designJson};
    var nodeTotal = ${nodeTotal};
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
                            var url = "${ctx}/flow/flow_addNode.emi";
                            $.post(url,{"flow_id":the_flow_id,"left":mLeft,"top":mTop},function(data){
                            	
                            	if(data.status!=1)
                                {
                                    mAlert("服务器保存异常，添加失败");
                                }else if(!_canvas.addProcess(data.info))//添加
                               {
                                    mAlert("绘制节点异常，添加失败");
                               }
                               
                            },'json');
                        },
                        "cmSave": function(t) {
                        	saveFlow(true,false);
                        },
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
                              if(confirm("你确定删除步骤吗？"))
                              {
                                    var activeId = _canvas.getActiveId();//右键当前的ID
                                    var url = "${ctx}/flow/flow_deleteNode.emi";
                                    $.post(url,{"flow_id":the_flow_id,"process_id":activeId},function(data){
                                        if(data.status==1)
                                        {
                                            //清除步骤
                                            //_canvas.delProcess(activeId);
                                            //清除连接   暂时先保存设计 + 刷新 完成
                                        	saveFlow(false,true);
                                        }else{
                                        	mAlert("删除失败");
                                        	page_reload();
                                        }
                                        
                                    },'json');
                              }
                          },
                          "pmAttribute":function(t)
                          {
                              var activeId = _canvas.getActiveId();//右键当前的ID
                              var url = "${ctx}/flow/flow_attributePage.emi?nodeId="+activeId;
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
                        mAlert("步骤连接重复了，请重新连接");
                        
                      }
                      ,fnClick:function(){
                          var activeId = _canvas.getActiveId();
                          //mAlert("查看步骤信息 " + activeId);
                      }
                      ,fnDbClick:function(){
                          //和 pmAttribute 一样
                          var activeId = _canvas.getActiveId();//右键当前的ID
                              var url = "/Flowdesign/attribute/id/"+activeId+".html";
                              ajaxModal(url,function(){
                                //alert('加载完成执行')
                              });
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

//刷新页面
function page_reload()
{
    //location.reload();
    location.href="${ctx}/flow/flow_toDesignPage.emi?flowId=${flow.gid}";
}

//保存流程
function saveFlow(showTip,refresh){
	debugger;
	var processInfo = _canvas.getProcessInfo();//连接信息
    var url = "${ctx}/flow/flow_saveProcessData.emi";
    $.post(url,{"flow_id":the_flow_id,"process_info":processInfo},function(data){
        if(data=='success'){
        	if(showTip){
        		mAlert("保存成功");
        	}
        	if(refresh){
        		page_reload();
        	}
        }else{
        	mAlert("保存失败,请联系相关技术人员");
        }
    	
    },'text');
}
</script>

</body>
</html>