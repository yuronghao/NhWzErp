package com.emi.flow.main.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-步骤历史主表
 */
@EmiTable(name = "FLOW_HistoryMain")
public class FLOW_HistoryMain implements Serializable{
	private static final long serialVersionUID = 2781935412524315131L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "status")
	private Integer status;			//状态 0：办理中 1：已办结 2：已作废
	
	@EmiColumn(name = "ownerId")
	private String ownerId;			//发起人id
	
	@EmiColumn(name = "createTime")
	private Timestamp createTime;	//创建时间
	
	@EmiColumn(name = "completeTime")
	private Timestamp completeTime;	//完成时间
	
	@EmiColumn(name = "deleteTime")
	private Timestamp deleteTime;	//作废时间
	
	@EmiColumn(name = "titleValue")
	private String titleValue;		//标题值

	public FLOW_HistoryMain() {
	}

	public FLOW_HistoryMain(Integer status, String ownerId ,String titleValue) {
		super();
		this.titleValue = titleValue;
		this.status = status;
		this.ownerId = ownerId;
		this.createTime = new Timestamp(System.currentTimeMillis());
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}
	public Timestamp getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getTitleValue() {
		return titleValue;
	}

	public void setTitleValue(String titleValue) {
		this.titleValue = titleValue;
	}


}
