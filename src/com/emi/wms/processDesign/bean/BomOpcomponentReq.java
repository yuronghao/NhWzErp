package com.emi.wms.processDesign.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BomOpcomponentReq {
	private Integer bomId;
	private Integer sortSeq;
	private String opSeq;
	private Integer componentId;
	private Date effBegDate;
	private Date effEndDate;
	private BigDecimal baseQtyN;//分子
	private BigDecimal baseQtyD;
	private Integer productType;
	public Integer getBomId() {
		return bomId;
	}
	public void setBomId(Integer bomId) {
		this.bomId = bomId;
	}
	public Integer getSortSeq() {
		return sortSeq;
	}
	public void setSortSeq(Integer sortSeq) {
		this.sortSeq = sortSeq;
	}
	public String getOpSeq() {
		return opSeq;
	}
	public void setOpSeq(String opSeq) {
		this.opSeq = opSeq;
	}
	public Integer getComponentId() {
		return componentId;
	}
	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}
	public Date getEffBegDate() {
		return effBegDate;
	}
	public void setEffBegDate(Date effBegDate) {
		this.effBegDate = effBegDate;
	}
	public Date getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(Date effEndDate) {
		this.effEndDate = effEndDate;
	}
	public BigDecimal getBaseQtyN() {
		return baseQtyN;
	}
	public void setBaseQtyN(BigDecimal baseQtyN) {
		this.baseQtyN = baseQtyN;
	}
	public BigDecimal getBaseQtyD() {
		return baseQtyD;
	}
	public void setBaseQtyD(BigDecimal baseQtyD) {
		this.baseQtyD = baseQtyD;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
}
