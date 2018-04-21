package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 组
 */
@EmiTable(name = "MES_WM_CostItem")
public class MESWMCostItem implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "code")
    private String code;		//编码
	
	@EmiColumn(name = "name")
    private String name;		//条码
	
	@EmiColumn(name = "sourceGid")
    private String sourceGid;	//名称
	
	@EmiColumn(name = "notes")
    private String notes;	//删除
	
	private String priorname;
	
	@EmiColumn(name = "orgGid")
	private String orgGid;
	
	@EmiColumn(name = "sobGid")
	private String sobGid;
	
	@EmiColumn(name = "allotRateGid")
	private String allotRateGid;
	
	private String allotRateName;
	
//	private BigDecimal costPrice;//当前成本要素的成本价
//	
//	
//	public BigDecimal getCostPrice() {
//		return costPrice;
//	}
//
//	public void setCostPrice(BigDecimal costPrice) {
//		this.costPrice = costPrice;
//	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceGid() {
		return sourceGid;
	}

	public void setSourceGid(String sourceGid) {
		this.sourceGid = sourceGid;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPriorname() {
		return priorname;
	}

	public void setPriorname(String priorname) {
		this.priorname = priorname;
	}

	public String getOrgGid() {
		return orgGid;
	}

	public void setOrgGid(String orgGid) {
		this.orgGid = orgGid;
	}

	public String getSobGid() {
		return sobGid;
	}

	public void setSobGid(String sobGid) {
		this.sobGid = sobGid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAllotRateGid() {
		return allotRateGid;
	}

	public void setAllotRateGid(String allotRateGid) {
		this.allotRateGid = allotRateGid;
	}

	public String getAllotRateName() {
		return allotRateName;
	}

	public void setAllotRateName(String allotRateName) {
		this.allotRateName = allotRateName;
	}

	
	
}