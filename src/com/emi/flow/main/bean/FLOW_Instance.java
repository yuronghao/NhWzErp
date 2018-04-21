package com.emi.flow.main.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-流程实例
 */
@EmiTable(name = "FLOW_Instance")
public class FLOW_Instance implements Serializable{
	private static final long serialVersionUID = -8640644188106173041L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "instanceName")
	private String instanceName;		//实例名称

	@EmiColumn(name = "flowId")
	private String flowId;		//流程id
	
	@EmiColumn(name = "formId")
	private String formId;				//表单id
	
	@EmiColumn(name = "createUserId")
	private String createUserId;		//创建人
	
	@EmiColumn(name = "createTime")
	private Timestamp createTime;		//创建时间
	
	@EmiColumn(name = "isSys")
	private Integer isSys;			//是否是系统预置实例  1：是
	
	@EmiColumn(name = "notes")
	private String notes;			//表单调用的页面url
	
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

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
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

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
