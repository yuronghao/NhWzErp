package com.emi.flow.main.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.flow.main.bean.FLOW_FlowDefine;
import com.emi.flow.main.bean.FLOW_FlowNode;
import com.emi.flow.main.service.FlowService;
import com.emi.flow.main.util.FlowNode;
import com.emi.rm.bean.RM_Role;
import com.emi.rm.service.RoleService;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;

@SuppressWarnings({"unchecked","rawtypes"})	
public class FlowAction extends BaseAction{
	private static final long serialVersionUID = 4920894976410036457L;
	private FlowService flowService;
	private RoleService roleService;
	private FLOW_FlowNode node;
	
	public FLOW_FlowNode getNode() {
		return node;
	}

	public void setNode(FLOW_FlowNode node) {
		this.node = node;
	}

	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void return_iframe_ajax(String ajax_return){
		//返回格式
		/*
			String ajax_return = "{'status':1,'msg':'保存成功','info':'',
		*/
		//回调页面的函数
		try {
			getResponse().getWriter().write("<script type=\"text/javascript\">parent.saveAttribute('"+ajax_return+"');</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @category 流程设计页面
	 * 2015年1月26日 下午3:41:33 
	 * @author 朱晓陈
	 */
	public String toDesignPage(){
		try {
			String flowId = this.getParameter("flowId");
			//获取主流程信息
			FLOW_FlowDefine flow = flowService.findFlowDefine(flowId);
			if(CommonUtil.isNullString(flow.getDesignJson())){
				flow.setDesignJson("[]");//如果没有设计表单,输出空array
			}
			int nodeTotal = JSONArray.fromObject(flow.getDesignJson()).size();
			this.setRequstAttribute("flow", flow);
			this.setRequstAttribute("nodeTotal", nodeTotal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "flow_design";
	}
	
	/**
	 * @category 流程列表
	 * 2015年1月26日 下午3:54:39 
	 * @author 朱晓陈
	 * @return
	 */
	public String toFlowList(){
		try {
			String keyWord = this.getParameter("keyWord");					//关键字
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = Config.PAGESIZE_WEB;							//每页总条数
			
			String columns = "";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
			PageBean pageBean = flowService.getFlowList(pageIndex,pageSize,condition);
			
			this.setRequstAttribute("data", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "flow_list";
	}
	
	/**
	 * @category 保存流程数据
	 * 2015年1月27日 下午1:09:40 
	 * @author 朱晓陈
	 */
	public void saveProcessData(){
		try {
			String flowId = this.getParameter("flow_id");					//流程定义id
			String process_info = this.getParameter("process_info");		//连接信息
			//流程节点信息
			List<FLOW_FlowNode> nodes = flowService.getAllNode(flowId);
			JSONObject process_json = JSONObject.fromObject(process_info);
			JSONArray designJson = new JSONArray();
			//根据连接信息和节点信息，转成流程信息
			for(FLOW_FlowNode node : nodes){
				Object obj = process_json.get(node.getGid());
				if(obj != null){
					JSONObject process_node = (JSONObject) obj;	//设计器返回的节点信息
					JSONObject design_node = new JSONObject();
					design_node.put("id", node.getGid());	//节点id
					design_node.put("flow_id", flowId);		//流程id
					design_node.put("process_name", node.getNodeName());//节点名称
					design_node.put("icon", node.getIcon());	//图标
					design_node.put("style", FlowNode.generateStyle(process_node.getInt("left"), process_node.getInt("top")));	//样式代码
					
					//下一步骤，可以是多个并列
					JSONArray nextNodes = process_node.getJSONArray("process_to");//得到的是array，转成string入库
					String nextNodes_str = "";
					for(int i=0;i<nextNodes.size();i++){
						nextNodes_str += nextNodes.get(i)+",";
					}
					if(nextNodes_str.length()>0){
						nextNodes_str = nextNodes_str.substring(0,nextNodes_str.length()-1);
					}
					design_node.put("process_to", nextNodes_str);
					
					//转好的节点json添加进需要入库的流程json里
					designJson.add(design_node);
					
					//后续节点id
					node.setNextNodeId(nextNodes_str);
				}
			}
			
			//保存节点之间关联信息
			boolean suc = flowService.updateNodes(nodes);
			
			if(suc){
				//保存流程信息
				FLOW_FlowDefine define = new FLOW_FlowDefine();
				define.setGid(flowId);
				define.setDesignJson(designJson.toString());
				suc = flowService.updateFlowDefine(define);
			}
			
			getResponse().getWriter().write(suc?"success":"error");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 添加节点
	 * 2015年1月27日 下午3:14:42 
	 * @author 朱晓陈
	 */
	public void addNode(){
		try{
			String flowId = this.getParameter("flow_id");
			String left = this.getParameter("left");	//坐标-距离左边框
			String top = this.getParameter("top");		//坐标-距离上边框
			
			String icon = FlowNode.DEFAULT_ICON;		//图标
			String style = FlowNode.DEFAULT_STYLE+"left:"+left+";top:"+top+";";		//样式 left:178px;top:442px;
			
			String nodeId = UUID.randomUUID().toString();
			FLOW_FlowNode node = new FLOW_FlowNode();
			node.setGid(nodeId);
			node.setFlowId(flowId);
			node.setNodeName(FlowNode.DEFAULT_NAME);	//名称
//			node.setNodeCode(nodeCode);
			node.setNodeType(FlowNode.DEFAULT_TYPE);	//节点类型
			node.setRouteType(FlowNode.DEFAULT_ROUTE);	//路由类型
			node.setPreNodeId(FlowNode.DEFAULT_NODEID);	//上一节点id
			node.setNextNodeId(FlowNode.DEFAULT_NODEID);//下一节点id
			node.setDoType(FlowNode.DEFAULT_DOTYPE);	//执行人类型 0：无  1:单人 2：角色 3：组
//			node.setForIds(forIds);						//执行对象id
			node.setIsFirst(0); 						//是否是开始节点
			node.setIcon(icon);
			node.setStyle(style);
			
			//已有节点数
			int count = flowService.getNodeCount(flowId);
			//如果这是第一个节点，样式设置成第一个节点的样式，设置isFirst=1
			if(count == 0){
				node.setIsFirst(1);
				icon = FlowNode.ICON_START;
			}
			
			//保存节点信息
			boolean suc = flowService.addNode(node);
			
			JSONObject info = new JSONObject();//节点信息
			if(suc){
				//将节点信息存入流程设计json
				info.put("id", nodeId);
				info.put("flow_id", flowId);
				info.put("process_name", node.getNodeName());
				info.put("process_to", "");
				info.put("icon", icon);
				info.put("style", style);
				
				FLOW_FlowDefine define = flowService.findFlowDefine(flowId);
				String designJson = define.getDesignJson();
				if(CommonUtil.isNullString(designJson)){
					designJson = "[]";
				}
				JSONArray array = JSONArray.fromObject(designJson);
				array.add(info);
				define.setDesignJson(array.toString());
				suc = flowService.updateFlowDefine(define);
			}
			
			JSONObject json = new JSONObject();
			if(suc){
				//保存成功后，返回节点信息
				json.put("status", 1);
				json.put("info", info);
			}else{
				json.put("status", 0);
			}
			getResponse().getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 节点属性页面
	 * 2015年1月27日 下午5:14:40 
	 * @author 朱晓陈
	 * @return
	 */
	public String attributePage(){
		try {
			String nodeId = this.getParameter("nodeId");	//节点id
			//获取此节点的信息
			FLOW_FlowNode node = flowService.getNode(nodeId);
			
			//获取已指定的人（或角色等）的名称
			String userNames = "";//指定人的名称
			String roleNames = "";//指定角色的名称
			if(node.getDoType().compareTo(FlowNode.DOTYPE_PERSON)==0){
				//TODO
			}else if(node.getDoType().compareTo(FlowNode.DOTYPE_ROLE)==0){
				String roleIds = node.getForIds();
				String condition = " and gid in('"+roleIds.replaceAll(",", "','")+"')";
				List<RM_Role> roles = roleService.getRoleList(-1, -1, condition).getList();
				for(RM_Role r : roles){
					roleNames += r.getRoleName()+",";
				}
				if(roleNames.length()>0){
					roleNames = roleNames.substring(0,roleNames.length()-1);
				}
			}
			//智能发送方式
			int smartType_1_select = 0;
			int smartType_2_select = 0;
			int smartType = node.getSmartSendType()==null?0:node.getSmartSendType();
			if((smartType | FlowNode.SMARTTYPE_PM) == smartType){//自动发给项目经理
				smartType_1_select = 1;
			}
			if((smartType | FlowNode.SMARTTYPE_SINGLE) == smartType){//对象只有一个时 自动选取发送
				smartType_2_select = 1;
			}
			this.setRequstAttribute("smartType_1_select", smartType_1_select);
			this.setRequstAttribute("smartType_2_select", smartType_2_select);
			this.setRequstAttribute("node", node);
			this.setRequstAttribute("userNames", userNames);
			this.setRequstAttribute("roleNames", roleNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "attribute";
	}
	
	/**
	 * @category 保存属性
	 * 2015年1月28日 上午8:19:07 
	 * @author 朱晓陈
	 */
	public void saveAttribute(){
		try {
			String flowId = this.getParameter("flow_id");	//流程id
			String nodeId = this.getParameter("process_id");//节点id
			
			/*----常规属性----*/
//			String nodeName = this.getParameter("process_name");//节点名称
			
			/*----权限属性----*/
			String auto_doType = this.getParameter("auto_person");//自动选人类型
			int doType = Integer.parseInt(auto_doType);
			String auto_role_ids = this.getParameter("auto_role_ids");//指定角色的id
//			String smartSendType = this.getParameter("smartSendType");//自动发送的方式 0:无 1：根据项目自动选择项目经理（从角色中选）
			String[] smartSendTypes = getRequest().getParameterValues("smartSendType");//自动发送的方式 0:无 1：根据项目自动选择项目经理（从角色中选）
			int smartType = 0;
			if(smartSendTypes!=null){
				//位运算 计算总值
				for(int i=0;i<smartSendTypes.length;i++){
					smartType |= Integer.parseInt(smartSendTypes[i]);
				}
			}
			/*保存属性*/
			node.setGid(nodeId);
			node.setDoType(doType);
			node.setSmartSendType(smartType);
			if(doType==FlowNode.DOTYPE_ROLE){	
				node.setForIds(auto_role_ids);	//设置成角色id
			}else if(doType==FlowNode.DOTYPE_PERSON){	
				//TODO
			}else if(doType==FlowNode.DOTYPE_GROUP){	
				
			}else if(doType==FlowNode.DOTYPE_DEPT){	
				
			}
			
			//更新节点属性信息
			boolean suc = flowService.updateNode(node);
			
			Map ret = new HashMap();
			if(suc){
				ret.put("status", 1);
				ret.put("msg", "保存成功");
				ret.put("info", "");
			}else{
				ret.put("status", 0);
				ret.put("msg", "保存失败");
				ret.put("info", "");
			}
			return_iframe_ajax(JSONObject.fromObject(ret).toString()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 删除节点
	 * 2015年1月28日 下午3:35:55 
	 * @author 朱晓陈
	 */
	public void deleteNode(){
		try {
			String flowId = this.getParameter("flow_id");		//流程id
			String nodeId = this.getParameter("process_id");	//节点id
			//删除节点
			boolean suc = flowService.deleteNode(nodeId);
			
			Map ret = new HashMap();
			if(suc){
				ret.put("status", 1);
				ret.put("msg", "删除成功");
				ret.put("info", "");
			}else{
				ret.put("status", 0);
				ret.put("msg", "删除失败");
				ret.put("info", "");
			}
			getResponse().getWriter().write(JSONObject.fromObject(ret).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setNodeFirst(){
		try {
			String nodeId = this.getParameter("process_id");//节点id
			String flowId = this.getParameter("flow_id");	//流程id
//			FLOW_FlowNode node = new FLOW_FlowNode();
//			node.setGid(nodeId);
//			node.setIsFirst(1);
//			node.setIcon(FlowNode.ICON_START);
//			//更新
//			boolean suc = flowService.updateNode(node);
			boolean suc = flowService.setNodeFirst(nodeId,flowId);
			
			Map ret = new HashMap();
			if(suc){
				ret.put("status", 1);
				ret.put("msg", "设置成功");
				ret.put("info", "");
			}else{
				ret.put("status", 0);
				ret.put("msg", "设置失败");
				ret.put("info", "");
			}
			getResponse().getWriter().write(JSONObject.fromObject(ret).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
