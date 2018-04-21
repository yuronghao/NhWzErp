package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "QM_CheckCReasonbill")
public class QMCheckCReasonBill implements Serializable{

	private static final long serialVersionUID = 2372154934728190290L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "checkcGid")
    private String checkcGid;//质检单子表gid
	
	@EmiColumn(name = "num")
    private BigDecimal num;//数量

	@EmiColumn(name = "assistNum")
    private BigDecimal assistNum;//辅数量

	@EmiColumn(name = "reasonGid")
    private String reasonGid;//原因gid

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

	public String getCheckcGid() {
		return checkcGid;
	}

	public void setCheckcGid(String checkcGid) {
		this.checkcGid = checkcGid;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getAssistNum() {
		return assistNum;
	}

	public void setAssistNum(BigDecimal assistNum) {
		this.assistNum = assistNum;
	}

	public String getReasonGid() {
		return reasonGid;
	}

	public void setReasonGid(String reasonGid) {
		this.reasonGid = reasonGid;
	}
	
	

	
	
	
	

}