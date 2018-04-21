package com.emi.wms.bean;

import java.sql.Timestamp;

public class Task {
	
	private String taskGid;//任务gid
	private String taskName;//任务名称
	private String billCode;//单据编号
	private String processName;//工序名称
	private String maker;//制单人名称
	private Timestamp shoudCompleteTime;//应完成时间
	private Timestamp dispatchTime;//派发时间
	

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public Timestamp getShoudCompleteTime() {
		return shoudCompleteTime;
	}

	public void setShoudCompleteTime(Timestamp shoudCompleteTime) {
		this.shoudCompleteTime = shoudCompleteTime;
	}

	public String getTaskGid() {
		return taskGid;
	}

	public void setTaskGid(String taskGid) {
		this.taskGid = taskGid;
	}

	public Timestamp getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(Timestamp dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	
	
	
	
	
	
}
