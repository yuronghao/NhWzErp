package com.emi.flow.main.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-步骤历史
 */
@EmiTable(name = "FLOW_History")
public class FLOW_History implements Serializable{
	private static final long serialVersionUID = -8640644188106173041L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "instanceId")
	private String instanceId;			//实例id

	@EmiColumn(name = "flowId")
	private String flowId;				//流程id
	
	@EmiColumn(name = "formId")
	private String formId;				//表单id
	
	@EmiColumn(name = "nodeId")
	private String nodeId;				//节点id
	
	@EmiColumn(name = "tableId")
	private String tableId;				//表id
	
	@EmiColumn(name = "dataId")
	private String dataId;				//数据id
	
	@EmiColumn(name = "fromUserId")
	private String fromUserId;			//发送人id
	
	@EmiColumn(name = "fromTime")
	private Timestamp fromTime;			//发送时间
	
	@EmiColumn(name = "acceptUserId")
	private String acceptUserId;		//接收人id
	
	@EmiColumn(name = "acceptStatus")
	private Integer acceptStatus;		//接收状态	0：未接收 1：已接收
	
	@EmiColumn(name = "acceptTime")
	private Timestamp acceptTime;		//接收时间
	
	@EmiColumn(name = "completeStatus")
	private Integer completeStatus;		//完成状态	0：未完成 1：已完成(通过) 2：已完成(驳回)
	
	@EmiColumn(name = "completeTime")
	private Timestamp completeTime;		//完成时间
	
	@EmiColumn(name = "comment")
	private String comment;				//意见
	
	@EmiColumn(name = "historyMainId")
	private String historyMainId;		//主表id
	
	private String instanceName;	//流程实例名
	private String fromUserName;	//发送人名称
	private String acceptUserName;	//接收人名称
	private String nodeName;		//本步骤节点名
	private String title;			//标题
	private Integer status;			//办结状态  0：办理中 1：已办结 2：已作废
	
	private Integer doType;		//默认执行人类型  0：无  1:单人 2：角色 3：组
	private String forIds;		//默认执行对象id，多个用逗号隔开

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

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Timestamp getFromTime() {
		return fromTime;
	}

	public void setFromTime(Timestamp fromTime) {
		this.fromTime = fromTime;
	}

	public String getAcceptUserId() {
		return acceptUserId;
	}

	public void setAcceptUserId(String acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	public Integer getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(Integer acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public Timestamp getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Timestamp acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Integer getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(Integer completeStatus) {
		this.completeStatus = completeStatus;
	}

	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getHistoryMainId() {
		return historyMainId;
	}

	public void setHistoryMainId(String historyMainId) {
		this.historyMainId = historyMainId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
