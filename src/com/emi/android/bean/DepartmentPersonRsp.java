package com.emi.android.bean;

import java.io.Serializable;

public class DepartmentPersonRsp implements Serializable{

	private static final long serialVersionUID = 6800637126079529904L;

	private String gid;
	
	private String personCode;//部门编码
	
	private String personName;//部门名称

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}


	
	
	
}
