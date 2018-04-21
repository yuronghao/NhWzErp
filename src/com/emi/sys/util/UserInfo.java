package com.emi.sys.util;

public class UserInfo {
	private String userId;
	private String userName;
	private String personId;
	private String personName;
	private String departmentId;
	private String departmentName;
	
	public UserInfo() {
	}
	
	public UserInfo(String userId, String userName, String personId,
			String personName, String departmentId, String departmentName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.personId = personId;
		this.personName = personName;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}



	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
