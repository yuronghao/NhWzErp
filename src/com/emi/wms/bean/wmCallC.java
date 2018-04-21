package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_Call_C")
public class wmCallC implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7217318188531508832L;

	@EmiColumn(increment=true,name="pk")
	private Integer pk;

	@EmiColumn(ID=true,name="gid")
	private String gid;
	
	@EmiColumn(name="callUid")
	private String callUid;
	
	@EmiColumn(name="goodsUid")
	private String goodsUid;
	
	@EmiColumn(name="number")
	private BigDecimal number;
	
	@EmiColumn(name="outnumber")
	private BigDecimal outnumber;
	
	
	@EmiColumn(name="outgoodsAllocationUid")
	private String outgoodsAllocationUid;
	
	@EmiColumn(name="ingoodsAllocationUid")
	private String ingoodsAllocationUid;
	
	@EmiColumn(name="assistNumber")
	private BigDecimal assistNumber;//付计量单位
	
	@EmiColumn(name="outassistNumber")
	private BigDecimal outassistNumber;//付计量单位	
	
	@EmiColumn(name="cfree1")
	private String cfree1;		
	
	@EmiColumn(name="cfree2")
	private String cfree2;
	
	@EmiColumn(name="batch")
	private String batch;
	
	

	
	
	
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

	public String getCallUid() {
		return callUid;
	}

	public void setCallUid(String callUid) {
		this.callUid = callUid;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public String getOutgoodsAllocationUid() {
		return outgoodsAllocationUid;
	}

	public void setOutgoodsAllocationUid(String outgoodsAllocationUid) {
		this.outgoodsAllocationUid = outgoodsAllocationUid;
	}

	public String getIngoodsAllocationUid() {
		return ingoodsAllocationUid;
	}

	public void setIngoodsAllocationUid(String ingoodsAllocationUid) {
		this.ingoodsAllocationUid = ingoodsAllocationUid;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}

	public String getCfree1() {
		return cfree1;
	}

	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}

	public String getCfree2() {
		return cfree2;
	}

	public void setCfree2(String cfree2) {
		this.cfree2 = cfree2;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public BigDecimal getOutnumber() {
		return outnumber;
	}

	public void setOutnumber(BigDecimal outnumber) {
		this.outnumber = outnumber;
	}

	public BigDecimal getOutassistNumber() {
		return outassistNumber;
	}

	public void setOutassistNumber(BigDecimal outassistNumber) {
		this.outassistNumber = outassistNumber;
	}

	
	
	
	
	
}
