package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "AA_UserDefine")
public class AaUserDefine implements Serializable {
	private static final long serialVersionUID = -7437897589002370963L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//物品ID
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;//物品UID
	
	@EmiColumn(name = "code")
	private String code;//编号
	
	@EmiColumn(name = "value")
	private String value;//值
	
	@EmiColumn(name = "sobGid")
	private String sobGid;//帐套编码
	
	@EmiColumn(name = "orgGid")
	private String orgGid;//组织编码
	

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSobGid() {
		return sobGid;
	}

	public void setSobGid(String sobGid) {
		this.sobGid = sobGid;
	}

	public String getOrgGid() {
		return orgGid;
	}

	public void setOrgGid(String orgGid) {
		this.orgGid = orgGid;
	}
	
	
	
	
	


}