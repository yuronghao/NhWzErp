package com.emi.rm.bean;

import java.io.Serializable;

public class RightSystem implements Serializable{

	private static final long serialVersionUID = -3462595288738051959L;
	
	private String shortName;	//系统简称
	
	private String fullName;	//系统全称

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
