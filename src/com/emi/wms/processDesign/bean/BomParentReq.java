package com.emi.wms.processDesign.bean;

public class BomParentReq {
	private Integer bomId;
	private Integer parentId;
	public Integer getBomId() {
		return bomId;
	}
	public void setBomId(Integer bomId) {
		this.bomId = bomId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
