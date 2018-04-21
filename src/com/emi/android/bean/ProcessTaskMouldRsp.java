package com.emi.android.bean;

import java.io.Serializable;

public class ProcessTaskMouldRsp implements Serializable{

	private static final long serialVersionUID = -4969836270945022660L;

	private String mouldGid;//模具gid
	
	private String mouldName;//模具名称

	public String getMouldGid() {
		return mouldGid;
	}

	public void setMouldGid(String mouldGid) {
		this.mouldGid = mouldGid;
	}

	public String getMouldName() {
		return mouldName;
	}

	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}
	
	
	
	


	
	
	
}
