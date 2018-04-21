package com.emi.android.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

public class WarehouseRsp {
	
    private String whgid;
    private String whcode;//仓库编码
    private String whname;//仓库名称
	public String getWhcode() {
		return whcode;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}
	public String getWhname() {
		return whname;
	}
	public void setWhname(String whname) {
		this.whname = whname;
	}
	public String getWhgid() {
		return whgid;
	}
	public void setWhgid(String whgid) {
		this.whgid = whgid;
	}
	
    
}