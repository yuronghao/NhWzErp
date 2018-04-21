package com.emi.android.bean;

import java.io.Serializable;

public class DepartmentRsp implements Serializable{

	private static final long serialVersionUID = 6800637126079529904L;

	private String gid;
	
	private String depCode;//部门编码
	
	private String depName;//部门名称

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	
}
