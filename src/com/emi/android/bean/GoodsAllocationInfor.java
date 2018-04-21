package com.emi.android.bean;

import java.math.BigDecimal;

public class GoodsAllocationInfor {

	private String	goodsAllocationCode;//货位编码
	private String	goodsAllocationName;//货位名称
	private String goodsAllocationUid;//货位uid
	private String whcode;//仓库编码
	private String wareHouseName;//仓库名称
	private BigDecimal	number;//数量
	private BigDecimal	assistNumber;//辅助数量
	private String batch;//批次
	private String cvMIVenCode;//代管供应商编码
	
	
	public String getCvMIVenCode() {
		return cvMIVenCode;
	}
	public void setCvMIVenCode(String cvMIVenCode) {
		this.cvMIVenCode = cvMIVenCode;
	}
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public BigDecimal getNumber() {
		return number;
	}
	public void setNumber(BigDecimal number) {
		this.number = number;
	}
	public BigDecimal getAssistNumber() {
		return assistNumber;
	}
	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getGoodsAllocationCode() {
		return goodsAllocationCode;
	}
	public void setGoodsAllocationCode(String goodsAllocationCode) {
		this.goodsAllocationCode = goodsAllocationCode;
	}
	public String getGoodsAllocationName() {
		return goodsAllocationName;
	}
	public void setGoodsAllocationName(String goodsAllocationName) {
		this.goodsAllocationName = goodsAllocationName;
	}
	public String getGoodsAllocationUid() {
		return goodsAllocationUid;
	}
	public void setGoodsAllocationUid(String goodsAllocationUid) {
		this.goodsAllocationUid = goodsAllocationUid;
	}
	public String getWhcode() {
		return whcode;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}

	
	
}
