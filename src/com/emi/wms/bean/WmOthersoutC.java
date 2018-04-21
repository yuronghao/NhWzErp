package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_OthersOut_C")
public class WmOthersoutC implements Serializable{
    
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4341199660244903769L;
	
	
	@EmiColumn(increment=true,name="pk")
	private Integer pk;
	
	@EmiColumn(ID=true,name="gid")
	private String gid;
	
	@EmiColumn(name="notes")
	private String notes;
	
	@EmiColumn(name="othersOutUid")
	private String othersOutUid;
	
	@EmiColumn(name="goodsUid")
	private String goodsUid;
	
	@EmiColumn(name="number")
	private BigDecimal number;
	
	@EmiColumn(name="price")
	private BigDecimal price;
	
	@EmiColumn(name="amount")
	private BigDecimal amount;
	
	@EmiColumn(name="callCuid")
	private String callCuid;
	
	@EmiColumn(name="goodsAllocationUid")
	private String goodsAllocationUid;
	
	@EmiColumn(name="assistNumber")
	private BigDecimal assistNumber;
	
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOthersOutUid() {
		return othersOutUid;
	}

	public void setOthersOutUid(String othersOutUid) {
		this.othersOutUid = othersOutUid;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCallCuid() {
		return callCuid;
	}

	public void setCallCuid(String callCuid) {
		this.callCuid = callCuid;
	}

	public String getGoodsAllocationUid() {
		return goodsAllocationUid;
	}

	public void setGoodsAllocationUid(String goodsAllocationUid) {
		this.goodsAllocationUid = goodsAllocationUid;
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

	
	
}