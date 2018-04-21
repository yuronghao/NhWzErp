package com.emi.flow.main.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.flow.form.bean.FLOW_Form;
import com.emi.flow.form.bean.FLOW_FormDetail;
import com.emi.flow.form.service.FormService;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.bean.FLOW_History;
import com.emi.flow.main.bean.FLOW_HistoryMain;
import com.emi.flow.main.bean.FLOW_Instance;
import com.emi.flow.main.service.FlowService;
import com.emi.flow.main.service.InstanceService;
import com.emi.flow.main.util.ElementType;
import com.emi.flow.model.service.EnumService;
import com.emi.flow.model.service.TableService;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.sys.util.UserInfo;

@SuppressWarnings({"rawtypes","unchecked"})
public class InstanceAction extends BaseAction{
	private static final long serialVersionUID = -5265103300551963099L;
	
	private InstanceService instanceService;
	private FlowService flowService;
	private FormService formService;
	private TableService tableService;
	private EnumService enumService;
	
	public void setInstanceService(InstanceService instanceService) {
		this.instanceService = instanceService;
	}
	
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}

	public void setEnumService(EnumService enumService) {
		this.enumService = enumService;
	}

	/**
	 * @category 实例列表
	 * 2015年1月15日 上午9:25:43 
	 * @author 朱晓陈
	 * @return
	 */
	public String getInstanceList(){
		try {
			String keyWord = this.getParameter("keyWord");					//关键字
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = Config.PAGESIZE_WEB;							//每页总条数
			
			String columns = "instanceName";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
			
			PageBean pageBean = instanceService.getInstanceList(condition,pageIndex,pageSize);
			
			this.setRequstAttribute("data", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "instance_runList";
	}
	
	/**
	 * @category 发起流程
	 * 2015年1月15日 下午2:02:53 
	 * @author 朱晓陈
	 * @return
	 */
//	public String startInstance(){
//		try {
//			String instanceId = this.getParameter("instanceId");			//实例id
//			//实例
//			FLOW_Instance instance = instanceService.findInstance(instanceId);
//			if(instance!=null){
//				//得到实例的表单，及流程第一步节点
//				FLOW_Form form = formService.findForm(instance.getFormId());
//				FLOW_FlowNode node = flowService.getFirstNode(instance.getFlowId());
//				FLOW_FlowNode nextNode = flowService.getNextNode(node.getGid());
//				
//				this.setRequstAttribute("instance", instance);
//				this.setRequstAttribute("form", form);
//				this.setRequstAttribute("node", node);
//				this.setRequstAttribute("nextNode", nextNode);
//				this.setRequstAttribute("isFirst", "true");
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "instance_form";
//	}
	
	/**
	 * @category 流程处理界面
	 * 2015年1月20日 上午8:45:05 
	 * @author 朱晓陈
	 * @return
	 */
	public String toDealPage(){
		try {
			String instanceId = this.getParameter("instanceId");	//实例id
			String isFirst = this.getParameter("isFirst");			//是否是第一步 0/1
			String nodeId = this.getParameter("nodeId");			//节点id
			String historyId = this.getParameter("historyId");		//流程记录id
			
			//兼容原wms，传入单据id
//			String orderId = "6f3ea4cf-bd12-4223-9068-7f6c8fcaddc9";//this.getParameter("orderId");
			
			String isLast = "0";	//是否是最后节点  0/1
			//实例
			FLOW_Instance instance = instanceService.findInstance(instanceId);
			JSONObject dataJson = new JSONObject();			//数据json
			JSONArray formDetailJson = new JSONArray();		//表单详情字段json
			if(instance!=null){
				
				/**-- 流程记录 --*/
				FLOW_History history = new FLOW_History();
				FLOW_HistoryMain historyMain = new FLOW_HistoryMain();
				if(CommonUtil.notNullString(historyId)){
					history = instanceService.findHistory(historyId);
					historyMain = instanceService.getHistoryMain(historyId);
				}
				
				/**-- 表单 --*/
				FLOW_Form form = formService.findForm(instance.getFormId());
				List<FLOW_FormDetail> formDetails = formService.getFormDetail(form.getGid());
				formDetailJson = EmiJsonArray.fromObject(formDetails);
				
				/*-----通过指定action返回表单值-----
				if(form.getDataFromAction() != null && form.getDataFromAction().compareTo(1)==0){
					ServletContext sc = getRequest().getSession().getServletContext();
					ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
					String actionName = form.getActionName();
					String[] actionSet = actionName.split("\\.");
					OrderAction oa = (OrderAction) ac.getBean(actionSet[0]);
					Method sAge = oa.getClass().getMethod(actionSet[1], new Class[] {});   
					//执行方法   
					sAge.invoke(oa , new Object[] {});   
				}*/
				
				/**-- 本节点 --*/
				FLOW_FlowNode node = new FLOW_FlowNode();
				if("1".equals(isFirst)){//第一步
					//流程第一步节点
					node = flowService.getFirstNode(instance.getFlowId());
				}else{
					node = flowService.getNode(nodeId);
					/**-- 表单数据 --*/
					Map data = instanceService.queryData(history.getDataId(),form.getTableId(),formDetails);
					dataJson = EmiJsonObj.fromObject(data);
				}
				
				
				/**-- 后续节点 --*/
				List<FLOW_FlowNode> nextNodes = new ArrayList<FLOW_FlowNode>();
				if(node!=null){
					if(CommonUtil.isNullString(node.getNextNodeId()) || "0".equals(node.getNextNodeId())){
						//没有后续节点
						isLast = "1";
					}else{
						nextNodes = flowService.getNextNodes(node.getGid());
					}
				}
				//查询该节点是否自动发送,并设置自动发送人
				String projectId = CommonUtil.Obj2String(dataJson.get("projectId"));
				if("".equals(projectId)){
					projectId = this.getParameter("projectId");
				}
				for(FLOW_FlowNode n_node:nextNodes){
					String autoSendUserId = instanceService.getAutoSendUserId(n_node,projectId);
					n_node.setAutoSendUserId(autoSendUserId);
				}
				
				this.setRequstAttribute("instance", instance);
				this.setRequstAttribute("form", form);
				this.setRequstAttribute("node", node);
				this.setRequstAttribute("nextNodes", nextNodes);
				this.setRequstAttribute("isFirst", isFirst);
				this.setRequstAttribute("isLast", isLast);
				this.setRequstAttribute("historyId", historyId);
				this.setRequstAttribute("dataId", CommonUtil.Obj2String(dataJson.get("gid")));
				this.setRequstAttribute("dataJson", dataJson);
				this.setRequstAttribute("formDetailJson", formDetailJson);
				this.setRequstAttribute("historyMain", historyMain);
				this.setRequstAttribute("dataGid", CommonUtil.Obj2String(dataJson.get("gid")));//数据id
				
				this.setRequstAttribute("onlyShow", this.getParameter("onlyShow"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "instance_form";
	}
	
	public String toDealPage4Print(){
		toDealPage();
		return "instance_print";
	}
	
	/**
	 * @category 表单提交
	 * 2015年1月15日 下午4:13:02 
	 * @author 朱晓陈
	 * @return
	 */
	public String submitForm(){
		try {
			String userId = CommonUtil.Obj2String(getSession().get("UserId"));
			String userName = CommonUtil.Obj2String(getSession().get("UserName"));
			String personId = CommonUtil.Obj2String(getSession().get("PersonId"));
			String personName = CommonUtil.Obj2String(getSession().get("PersonName"));
			String departmentId = CommonUtil.Obj2String(getSession().get("DepartmentId"));
			String departmentName = CommonUtil.Obj2String(getSession().get("DepartmentName"));
			UserInfo userInfo = new UserInfo(userId, userName, personId, personName, departmentId, departmentName);
			String sendToUserId = this.getParameter("sendToUserId");	//发送对象的userId
			String formId = this.getParameter("formId");	//表单id
			String nodeId = this.getParameter("nodeId");	//节点id
			String nextNodeId = this.getParameter("nextNodeId");	//下个发送的节点id
			String flowId = this.getParameter("flowId");	//流程id
			String instanceId = this.getParameter("instanceId");	//实例id
			String historyId = this.getParameter("historyId");//流程流转历史id
			String isFirst = this.getParameter("isFirst");	//是否第一个节点
			String isLast = this.getParameter("isLast");	//是否是最后一个节点
			String isReject = this.getParameter("isReject");	//是否驳回  1：驳回
			String dataGid = this.getParameter("dataGid");//数据id
			String comment = this.getParameter("comment");//意见
			boolean first = "1".equals(isFirst);
			boolean last = "1".equals(isLast);
			boolean reject = "1".equals(isReject);
			//表单元素详情
			List<FLOW_FormDetail> formDetails = formService.getFormDetail(formId);
			/*
			 * 表单各值与数据库字段匹配
			 */
			if(first){
				//如果是第一步，生成id
				dataGid = UUID.randomUUID().toString();
			}
			Map<String, String> values = getColumnValues(formDetails);
			values.put("gid", dataGid);//设置id
			
			int flag = 0;
			if((!reject) && !last && CommonUtil.isNullString(sendToUserId)){
				flag = 2;
			}else{
				//保存表单数据（第一步提交则新增数据，否则更新数据）
				flag = instanceService.saveInstanceForm(userId,sendToUserId,first,last,values,formId,nodeId,flowId,
						instanceId,historyId,nextNodeId,comment,reject,userInfo);
			}
			String msg = flag==1?"success":flag==2?"未找到接收人,或接收人没有登录权限":"提交错误";
			getResponse().getWriter().write(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 表单各值与数据库字段匹配
	 */
	private Map getColumnValues(List<FLOW_FormDetail> formDetails){
		Map<String, String> values = new HashMap();
		for(FLOW_FormDetail detail : formDetails){
			if(CommonUtil.notNullString(detail.getColumnName())){
				if(ElementType.CHECKBOX.equals(detail.getElementType())){
					//复选框多个值，合并并以逗号隔开
					String[]   ckvalues   =   getRequest().getParameterValues(detail.getColumnName());
					String value = "";
					if(ckvalues!=null){
						for(int i=0;i<ckvalues.length;i++){
							value += ckvalues[i]+",";
						}
					}
					if(value.length()>0){value=value.substring(0,value.length()-1);}
					values.put(detail.getColumnName(), value);
				}else{
					String value = this.getParameter(detail.getColumnName());
					values.put(detail.getColumnName(), value);
				}
			}
		}
		return values;
	}
	
	/**
	 * @category 待办列表
	 * 2015年1月16日 下午4:45:49 
	 * @author 朱晓陈
	 * @return
	 */
	public String todoList(){
		try {
			String userId = CommonUtil.Obj2String(getSession().get("UserId"));
			String keyWord = this.getParameter("keyWord");					//关键字
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = Config.PAGESIZE_WEB;							//每页总条数
			
			String columns = "";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
			
			PageBean pageBean = instanceService.getTodoList(pageIndex,pageSize,userId,condition);
			setRequstAttribute("data", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "instance_todoList";
	}
	
	/**
	 * @category 已办列表
	 * 2015年1月16日 下午4:46:00 
	 * @author 朱晓陈
	 * @return
	 */
	public String doneList(){
		try {
			String userId = CommonUtil.Obj2String(getSession().get("UserId"));
			String keyWord = this.getParameter("keyWord");					//关键字
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = Config.PAGESIZE_WEB;							//每页总条数
			
			String columns = "";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
			
			PageBean pageBean = instanceService.getDoneList(pageIndex,pageSize,userId,condition);
			setRequstAttribute("data", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "instance_doneList";
	}
	
	/**
	 * @category 已办结列表
	 * 2015年2月7日 下午4:46:00 
	 * @author 朱晓陈
	 * @return
	 */
	public String overList(){
		try {
//			String userId = CommonUtil.Obj2String(getSession().get("UserId"));
			String keyWord = this.getParameter("keyWord");					//关键字
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = Config.PAGESIZE_WEB;							//每页总条数
			
			String columns = "";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
			
			PageBean pageBean = instanceService.getOverList(pageIndex,pageSize,null,condition);
			setRequstAttribute("data", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "instance_doneList";
	}
	
	/**
	 * @category 流程记录历史列表
	 * 2015年1月23日 下午4:56:35 
	 * @author 朱晓陈
	 * @return
	 */
	public String showHistoryList(){
		try {
			String instanceId = this.getParameter("instanceId");
			String dataId = this.getParameter("dataId");
			//已流转的历史
			List<FLOW_History> historys = instanceService.getHistoryList(instanceId,dataId);
			//根据最后一步，查找接下来的未流转的步骤
			if(historys.size()>0){
				FLOW_History lastHistory = historys.get(historys.size()-1);//最后一步
				List<FLOW_History> notDoneHistorys = instanceService.getNotDoneHistorys(lastHistory);
				historys.addAll(notDoneHistorys);
			}
			
			this.setRequstAttribute("historys", historys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "instance_history";
	}
	
	/**
	 * @category 意见填写页
	 * 2015年1月26日 下午2:05:15 
	 * @author 朱晓陈
	 * @return
	 */
	public String toCommentJsp(){
		return "comment";
	}
	
	/**
	 * @category 作废
	 * 2015年2月28日 下午4:00:08 
	 * @author 朱晓陈
	 */
	public void cancelFlow(){
		try {
			String historyMainId = this.getParameter("historyMainId");
			//已流转的历史
			boolean suc = instanceService.cancelFlow(historyMainId);
			String msg = suc?"success":"error";
			getResponse().getWriter().write(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
