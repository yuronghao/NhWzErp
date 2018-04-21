package com.emi.flow.main.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-流程节点
 */
@EmiTable(name = "FLOW_FlowNode")
public class FLOW_FlowNode implements Serializable{
	private static final long serialVersionUID = 6467876605135848898L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "flowId")
	private String flowId;				//所属流程id
	
	@EmiColumn(name = "nodeName")
	private String nodeName;			//节点名称
	
	@EmiColumn(name = "nodeCode")
	private String nodeCode;			//节点代号
	
	@EmiColumn(name = "nodeType")
	private Integer nodeType;			//节点类型  0:普通节点 1:条件判断 2：…
	
	@EmiColumn(name = "routeType")
	private Integer routeType;			//路由类型  0:并行 1：竞争
	
	@EmiColumn(name = "preNodeId")
	private String preNodeId;			//上一节点id
	
	@EmiColumn(name = "nextNodeId")
	private String nextNodeId;			//下一节点id
	
	@EmiColumn(name = "doType")
	private Integer doType;				//默认执行人类型  0-无    1-单人   2-角色   3-组   4-部门
	
	@EmiColumn(name = "forIds")
	private String forIds;				//默认执行对象id，多个用逗号隔开
	
	@EmiColumn(name = "icon")
	private String icon;				//图标
	
	@EmiColumn(name = "style")
	private String style;				//样式代码
	
	@EmiColumn(name = "isFirst")
	private Integer isFirst;			//是否是开始节点
	
	@EmiColumn(name = "smartSendType")
	private Integer smartSendType;		//自动发送方式 (结果采用位运算)  0:无 1:自动发送给项目的项目经理 2:角色中只有一个人时自动发送

	@EmiColumn(name = "allowEdit")
	private Integer allowEdit;			//是否允许修改数据 0：不允许 1：允许
	
	private String autoSendUserId;//自动发送 1:自动发送
	
	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getRouteType() {
		return routeType;
	}

	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}

	public String getPreNodeId() {
		return preNodeId;
	}

	public void setPreNodeId(String preNodeId) {
		this.preNodeId = preNodeId;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public Integer getDoType() {
		return doType;
	}

	public void setDoType(Integer doType) {
		this.doType = doType;
	}

	public String getForIds() {
		return forIds;
	}

	public void setForIds(String forIds) {
		this.forIds = forIds;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public Integer getSmartSendType() {
		return smartSendType;
	}

	public void setSmartSendType(Integer smartSendType) {
		this.smartSendType = smartSendType;
	}

	public String getAutoSendUserId() {
		return autoSendUserId;
	}

	public void setAutoSendUserId(String autoSendUserId) {
		this.autoSendUserId = autoSendUserId;
	}

	public Integer getAllowEdit() {
		return allowEdit;
	}

	public void setAllowEdit(Integer allowEdit) {
		this.allowEdit = allowEdit;
	}

	

}
