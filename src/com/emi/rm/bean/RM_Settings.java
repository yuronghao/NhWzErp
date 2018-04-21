package com.emi.rm.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 系统设置
 */
@EmiTable(name = "RM_Settings")
public class RM_Settings implements Serializable{
	private static final long serialVersionUID = -2727532236522373173L;

	@EmiColumn(name = "gid", ID = true)
	private String gid;
	
	@EmiColumn(name = "setName")
	private String setName;				//名称
	
	@EmiColumn(name = "paramValue")
	private String paramValue;			//值
	
	@EmiColumn(name = "notes")
	private String notes;				//备注

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
