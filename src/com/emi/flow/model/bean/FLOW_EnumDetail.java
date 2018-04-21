package com.emi.flow.model.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-枚举
 */
@EmiTable(name = "FLOW_EnumDetail")
public class FLOW_EnumDetail implements Serializable{
	private static final long serialVersionUID = 334241444178061098L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "enumId")
	private String enumId;				//枚举主表id
	
	@EmiColumn(name = "enumKey")
	private String enumKey;				//关键字
	
	@EmiColumn(name = "enumDisplay")
	private String enumDisplay;			//显示值

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getEnumKey() {
		return enumKey;
	}

	public void setEnumKey(String enumKey) {
		this.enumKey = enumKey;
	}

	public String getEnumDisplay() {
		return enumDisplay;
	}

	public void setEnumDisplay(String enumDisplay) {
		this.enumDisplay = enumDisplay;
	}
	
	

}
