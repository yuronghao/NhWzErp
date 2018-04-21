package com.emi.android.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProcessTaskDetailRsp implements Serializable {

	private static final long serialVersionUID = 7122786953650204813L;
	
	private String goodsCode;//物料编号
	private String goodsName;//生产产品名称
	private String goodsStandard;//规格型号
	private String billCode;//派工单据编号(单据编号)
	private String processName;//工序名称
	private Date startTime;//计划开工日期
	private Date endTime;//计划完工日期
	private Integer isCheck;//是否质检
	private Integer isReportExceedDis;//首道工序是否允许超派工单报工 0不允许 1允许
	private BigDecimal canDisnum;//可开工数量
	private BigDecimal canReprotNum;//可报工数量
	private BigDecimal canCheckNum;//可质检数量
	private Integer dispatchingObj;//派工对象 0：人  1：组
	private String produceProcessRoutecGid;//生产订单工艺路线子表gid
	private String notes;//主体备注
	private String currentDate;//当前日期
	
	
	
	
	
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getIsReportExceedDis() {
		return isReportExceedDis;
	}
	public void setIsReportExceedDis(Integer isReportExceedDis) {
		this.isReportExceedDis = isReportExceedDis;
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
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
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
	public Integer getDispatchingObj() {
		return dispatchingObj;
	}
	public void setDispatchingObj(Integer dispatchingObj) {
		this.dispatchingObj = dispatchingObj;
	}
	public BigDecimal getCanDisnum() {
		return canDisnum;
	}
	public void setCanDisnum(BigDecimal canDisnum) {
		this.canDisnum = canDisnum;
	}
	public BigDecimal getCanReprotNum() {
		return canReprotNum;
	}
	public void setCanReprotNum(BigDecimal canReprotNum) {
		this.canReprotNum = canReprotNum;
	}
	public String getProduceProcessRoutecGid() {
		return produceProcessRoutecGid;
	}
	public void setProduceProcessRoutecGid(String produceProcessRoutecGid) {
		this.produceProcessRoutecGid = produceProcessRoutecGid;
	}
	public BigDecimal getCanCheckNum() {
		return canCheckNum;
	}
	public void setCanCheckNum(BigDecimal canCheckNum) {
		this.canCheckNum = canCheckNum;
	}
	public Integer getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	
	

	
	
	
	
	
	
}
