package com.emi.flow.mobile.action;

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
import com.emi.flow.model.service.EnumService;
import com.emi.flow.model.service.TableService;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class InstanceAction extends BaseAction{
	private InstanceService instanceService;
	private FlowService flowService;
	private FormService formService;
	private TableService tableService;
	private EnumService enumService;
//	private MS_PayApplyService ms_PayApplyService;
//	
//	public void setMs_PayApplyService(MS_PayApplyService ms_PayApplyService) {
//		this.ms_PayApplyService = ms_PayApplyService;
//	}

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
	 * @category 待办列表
	 * 2015年1月16日 下午4:45:49 
	 * @author 朱晓陈
	 * @return
	 */
	public void todoList(){
		try {
			JSONObject json = getJsonObject();
			String userId = json.getString("userId");		//用户id
			String keyWord = CommonUtil.Obj2String(json.get("keyWord"));   	//“keyWord”:”查询关键字”
			int pageIndex = json.get("pageIndex")==null?0:json.getInt("pageIndex");

			int pageSize = Config.PAGESIZE_MOB;				//每页总条数
			
			String columns = "i.instanceName";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
			
			PageBean pageBean = instanceService.getTodoList(pageIndex,pageSize,userId,condition);

			Map retMap = new HashMap();
			retMap.put("success", "1");
			retMap.put("list", pageBean.getList());
			getResponse().getWriter().write(EmiJsonObj.fromObject(retMap).toString());
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 已办列表
	 * 2015年1月16日 下午4:45:49 
	 * @author 朱晓陈
	 * @return
	 */
	public void doneList(){
		try {
			JSONObject json = getJsonObject();
			String userId = json.getString("userId");		//用户id
			String keyWord = CommonUtil.Obj2String(json.get("keyWord"));   	//“keyWord”:”查询关键字”
			int pageIndex = json.get("pageIndex")==null?0:json.getInt("pageIndex");

			int pageSize = Config.PAGESIZE_MOB;				//每页总条数
			
			String columns = "i.instanceName";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
			
			PageBean pageBean = instanceService.getDoneList(pageIndex,pageSize,userId,condition);

			Map retMap = new HashMap();
			retMap.put("success", "1");
			retMap.put("list", pageBean.getList());
			getResponse().getWriter().write(EmiJsonObj.fromObject(retMap).toString());
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 详情
	 * 2015年1月16日 下午4:45:49 
	 * @author 朱晓陈
	 * @return
	 */
	public void toDeal(){
		try {
			JSONObject json = getJsonObject();
			String instanceId = CommonUtil.Obj2String(json.get("instanceId")); 	//实例id
			String nodeId = CommonUtil.Obj2String(json.get("nodeId")); 			//节点id
			String historyId = CommonUtil.Obj2String(json.get("historyId")); 	//步骤记录id
			String isFirst = CommonUtil.Obj2String(json.get("isFirst")==null?"0":json.get("isFirst"));			//是否是第一步 0/1

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
				//#####注：如果移动端可以发起流程，则第一步需要将projectId传过来#####
				String projectId = CommonUtil.Obj2String(dataJson.get("projectId"));
				if("".equals(projectId)){
					projectId = CommonUtil.Obj2String(json.get("projectId"));
				}
				for(FLOW_FlowNode n_node:nextNodes){
					String autoSendUserId = instanceService.getAutoSendUserId(n_node,projectId);
					n_node.setAutoSendUserId(autoSendUserId);
				}
				
				//########### 特殊定制 ，获取付款历史 ###############
				/*PayDetail pay = new PayDetail();
				if(CommonUtil.notNullObject(dataJson.get("contractCode"))){
					String contractCode = dataJson.getString("contractCode");
					pay = ms_PayApplyService.getPayByContractCode(contractCode,historyId);
					//本次付从表单数据中读取
					pay.setPaidThis(dataJson.get("payAmount")==null?0d:dataJson.getDouble("payAmount"));
				}*/
				//###########################################
				
				Map retMap = new HashMap();
				retMap.put("success", "1");
				retMap.put("instance", instance);
				retMap.put("form", form);
				retMap.put("node", node);
				retMap.put("nextNodes", nextNodes);
				retMap.put("isFirst", isFirst);
				retMap.put("isLast", isLast);
				retMap.put("historyId", historyId);
				retMap.put("dataJson", dataJson);
				retMap.put("formDetailJson", formDetailJson);
				retMap.put("historyMain", historyMain);
//				retMap.put("payRecord", pay);
				getResponse().getWriter().write(EmiJsonObj.fromObject(retMap).toString());
			}else{
				writeError();
			}
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 表单提交
	 * 2015年1月15日 下午4:13:02 
	 * @author 朱晓陈
	 * @return
	 */
	public void submitForm(){
		try {
			JSONObject json = getJsonObject();
			String userId =CommonUtil.Obj2String(json.get("userId"));
			String sendToUserId = CommonUtil.Obj2String(json.get("sendToUserId"));//发送对象的userid，多个逗号隔开
			String formId = CommonUtil.Obj2String(json.get("formId"));	//表单id
			String nodeId = CommonUtil.Obj2String(json.get("nodeId"));	//节点id
			String nextNodeId = CommonUtil.Obj2String(json.get("nextNodeId"));	//下个发送的节点id
			String flowId = CommonUtil.Obj2String(json.get("flowId"));	//流程id
			String instanceId = CommonUtil.Obj2String(json.get("instanceId"));	//实例id
			String historyId = CommonUtil.Obj2String(json.get("historyId"));//流程流转历史id
			String isFirst = CommonUtil.Obj2String(json.get("isFirst"));	//是否第一个节点
			String isLast = CommonUtil.Obj2String(json.get("isLast"));	//是否是最后一个节点
			String isReject = CommonUtil.Obj2String(json.get("isReject"));	//是否驳回  1：驳回
			String dataGid = CommonUtil.Obj2String(json.get("dataGid"));//数据id
			String comment = CommonUtil.Obj2String(json.get("comment"));;//意见
			boolean first = "1".equals(isFirst);
			boolean last = "1".equals(isLast);
			boolean reject = "1".equals(isReject);
			
			JSONObject dataJson = json.getJSONObject("dataJson");
			//表单元素详情
			List<FLOW_FormDetail> formDetails = formService.getFormDetail(formId);
			/*
			 * 表单各值与数据库字段匹配
			 */
			if(first){
				//如果是第一步，生成id
				dataGid = UUID.randomUUID().toString();
			}
			Map<String, String> values = getColumnValues(formDetails,dataJson);
			values.put("gid", dataGid);//设置id
			
			int flag = 0;
			if((!reject) && CommonUtil.isNullString(sendToUserId)){
				flag = 2;
			}else{
				//保存表单数据（第一步提交则新增数据，否则更新数据）
				flag = instanceService.saveInstanceForm(userId,sendToUserId,first,last,values,formId,nodeId,flowId,instanceId,historyId,nextNodeId,comment,reject,null);
			}
			Map retMap = new HashMap();
			retMap.put("success", flag);
			getResponse().getWriter().write(EmiJsonObj.fromObject(retMap).toString());
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
	
	/*
	 * 表单各值与数据库字段匹配
	 */
	private Map getColumnValues(List<FLOW_FormDetail> formDetails,JSONObject dataJson){
		Map<String, String> values = new HashMap();
		for(FLOW_FormDetail detail : formDetails){
			if(CommonUtil.notNullString(detail.getColumnName())){
				String value = CommonUtil.Obj2String(dataJson.get(detail.getColumnName()));
				values.put(detail.getColumnName(), value);
			}
		}
		return values;
	}
	
	/**
	 * @category 流程记录历史列表
	 * 2015年1月23日 下午4:56:35 
	 * @author 朱晓陈
	 * @return
	 */
	public void historyList(){
		try {
			JSONObject json = getJsonObject();
			String instanceId =CommonUtil.Obj2String(json.get("instanceId"));
			String dataId =CommonUtil.Obj2String(json.get("dataId"));
			//已流转的历史
			List<FLOW_History> historys = instanceService.getHistoryList(instanceId,dataId);
			//根据最后一步，查找接下来的未流转的步骤
			FLOW_History lastHistory = historys.get(historys.size()-1);//最后一步
			List<FLOW_History> notDoneHistorys = instanceService.getNotDoneHistorys(lastHistory);
			historys.addAll(notDoneHistorys);
			
			Map retMap = new HashMap();
			retMap.put("success", "1");
			retMap.put("list", historys);
			getResponse().getWriter().write(EmiJsonObj.fromObject(retMap).toString());
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
}
