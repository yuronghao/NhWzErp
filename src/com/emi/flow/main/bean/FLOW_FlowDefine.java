package com.emi.flow.main.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-流程定义
 */
@EmiTable(name = "FLOW_FlowDefine")
public class FLOW_FlowDefine implements Serializable{
	private static final long serialVersionUID = -936617641875502719L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "flowName")
	private String flowName;			//流程名称
	
	@EmiColumn(name = "description")
	private String description;			//描述
	
	@EmiColumn(name = "createUserId")
	private String createUserId;		//创建人id
	
	@EmiColumn(name = "createTime")
	private Timestamp createTime;		//创建时间
	
	@EmiColumn(name = "version")
	private Integer version;				//版本
	
	@EmiColumn(name = "designJson")
	private String designJson;				//流程图设计连线的json
	
	@EmiColumn(name = "isSys")
	private Integer isSys;					//是否是系统预设流程 1：是

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

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDesignJson() {
		return designJson;
	}

	public void setDesignJson(String designJson) {
		this.designJson = designJson;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

}
