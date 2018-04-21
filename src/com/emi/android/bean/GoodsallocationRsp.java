package com.emi.android.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

public class GoodsallocationRsp {
	private String locationgid;
    private String code;//编码
    private String name;//名称
    private String allocationBarCode;//货位编码
    
    
	public String getAllocationBarCode() {
		return allocationBarCode;
	}
	public void setAllocationBarCode(String allocationBarCode) {
		this.allocationBarCode = allocationBarCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocationgid() {
		return locationgid;
	}
	public void setLocationgid(String locationgid) {
		this.locationgid = locationgid;
	}
	
	
}