package com.emi.wms.bean;

import java.io.Serializable;
import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="YM_RdStyle")
public class YmRdStyle implements Serializable {

	private static final long serialVersionUID = 5243757508997520426L;

	@EmiColumn(name="pk",increment=true)
	private Integer pk;
	
	@EmiColumn(name="gid",ID=true)
	private String gid;
	
	@EmiColumn(name="crdCode")
	private String crdCode;
	
	@EmiColumn(name="crdName")
	private String crdName;
	
	@EmiColumn(name="irdFlag")
	private Integer irdFlag;
	
	@EmiColumn(name="irdGrade")
	private Integer irdGrade;
	
	@EmiColumn(name="brdEnd")
	private Integer brdEnd;
	
	@EmiColumn(name="csupCode")
	private String csupCode;

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

	public String getCrdCode() {
		return crdCode;
	}

	public void setCrdCode(String crdCode) {
		this.crdCode = crdCode;
	}

	public String getCrdName() {
		return crdName;
	}

	public void setCrdName(String crdName) {
		this.crdName = crdName;
	}

	public Integer getIrdFlag() {
		return irdFlag;
	}

	public void setIrdFlag(Integer irdFlag) {
		this.irdFlag = irdFlag;
	}

	public Integer getIrdGrade() {
		return irdGrade;
	}

	public void setIrdGrade(Integer irdGrade) {
		this.irdGrade = irdGrade;
	}

	public Integer getBrdEnd() {
		return brdEnd;
	}

	public void setBrdEnd(Integer brdEnd) {
		this.brdEnd = brdEnd;
	}

	public String getCsupCode() {
		return csupCode;
	}

	public void setCsupCode(String csupCode) {
		this.csupCode = csupCode;
	}
	

	
	
	
}
