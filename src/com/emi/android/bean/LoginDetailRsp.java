package com.emi.android.bean;

public class LoginDetailRsp {

	private Integer success;//0失败 1成功
	private String failInfor;//失败时的信息
	private String taskType;//任务类型编码 
	private String uerUid;//用户gid
	private String userName;//用户名称
	private String orggid;//组织
	private String sobgid;//帐套


	
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getFailInfor() {
		return failInfor;
	}
	public void setFailInfor(String failInfor) {
		this.failInfor = failInfor;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getUerUid() {
		return uerUid;
	}
	public void setUerUid(String uerUid) {
		this.uerUid = uerUid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrggid() {
		return orggid;
	}
	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}
	public String getSobgid() {
		return sobgid;
	}
	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
	}
	
	
}
