package com.emi.android.bean;

import java.io.Serializable;

//出库时扫描物品、货位条码
public class GoodsInforRsp implements Serializable{


	private static final long serialVersionUID = -6742707249077238937L;
	
	private Integer success;//0失败 1成功
	private String failInfor;//失败时消息
	private String goodsCode;//物品编码
	private String cfree1;//自由项1
	private String cfree2;//自由项2
	private String	goodsAllocationCode;//货位编码
	private String	allocationbarcode;//货位编码
	private String	goodsAllocationName;//货位名称
	private String goodsAllocationUid;//货位uid
	private String whcode;//仓库编码

	
	
	
	public String getAllocationbarcode() {
		return allocationbarcode;
	}

	public void setAllocationbarcode(String allocationbarcode) {
		this.allocationbarcode = allocationbarcode;
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

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getFailInfor() {
		return failInfor;
	}

	public void setFailInfor(String failInfor) {
		this.failInfor = failInfor;
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
