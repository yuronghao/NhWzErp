package com.emi.android.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//出库时扫描物品、货位条码
public class GoodsOutforRsp implements Serializable{

	private static final long serialVersionUID = 7418598198289657547L;
	
	private Integer success;//0失败 1成功
	private String failInfor;//失败时消息
	private String goodsCode;//物品编码
	private String cfree1;//自由项1
	private String cfree2;//自由项2

	private List<GoodsAllocationInfor> goodsAllocationInfor;//物品信息

	
	
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

	public List<GoodsAllocationInfor> getGoodsAllocationInfor() {
		return goodsAllocationInfor;
	}

	public void setGoodsAllocationInfor(
			List<GoodsAllocationInfor> goodsAllocationInfor) {
		this.goodsAllocationInfor = goodsAllocationInfor;
	}

	public String getFailInfor() {
		return failInfor;
	}

	public void setFailInfor(String failInfor) {
		this.failInfor = failInfor;
	}

	
	
	
	
}
