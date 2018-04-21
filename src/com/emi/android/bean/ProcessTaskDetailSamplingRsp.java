package com.emi.android.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProcessTaskDetailSamplingRsp implements Serializable {

	private static final long serialVersionUID = 7122786953650204813L;
	
	private Integer success;//0失败 1成功
	private String failInfor;//失败时消息
	
	private String goodsCode;
	private String goodsName;//生产产品名称
	private String goodsStandard;
	private String billCode;//生产订单号
	private Date startTime;//计划开工日期
	private Date endTime;//计划完工日期
	private BigDecimal canSamplingNum;//可抽检数量
	private String produceCuid;//生产订单产品表gid
	
	
	
	
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsStandard() {
		return goodsStandard;
	}
	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getFailInfor() {
		return failInfor;
	}
	public void setFailInfor(String failInfor) {
		this.failInfor = failInfor;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getCanSamplingNum() {
		return canSamplingNum;
	}
	public void setCanSamplingNum(BigDecimal canSamplingNum) {
		this.canSamplingNum = canSamplingNum;
	}
	public String getProduceCuid() {
		return produceCuid;
	}
	public void setProduceCuid(String produceCuid) {
		this.produceCuid = produceCuid;
	}
	
	
	
	
	
	

	
	
	
	
	
	
}
