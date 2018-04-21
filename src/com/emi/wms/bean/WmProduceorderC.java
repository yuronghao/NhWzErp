package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "WM_ProduceOrder_C")
public class WmProduceorderC implements Serializable{

	private static final long serialVersionUID = -9005574295045066256L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "notes")
	private String notes;				//备注
	
	@EmiColumn(name = "produceOrderUid")
    private String produceOrderUid;			//生产订单主表uid
	
	@EmiColumn(name = "goodsUid")	
    private String goodsUid;			//成品uid
    
	@EmiColumn(name = "depUid")
    private String depUid;				//部门gid
    
	@EmiColumn(name = "number")
    private BigDecimal number;			//数量
    
	@EmiColumn(name = "completedNum")
    private BigDecimal completedNum;			//已完工数量
    
	@EmiColumn(name = "planComplete")
    private Date planComplete;			//计划完成日期
    
	@EmiColumn(name = "isCheck")
    private Integer isCheck;			//是否质检
    
	@EmiColumn(name = "startDate")
    private Date startDate;			//计划开工日期
    
	@EmiColumn(name = "endDate")
    private Date endDate;			//计划完工日期
    
	@EmiColumn(name = "processId")
    private String processId;			//标准工艺路线ID
    
	@EmiColumn(name = "saleId")
    private String saleId;			//销售订单ID
    
	@EmiColumn(name = "assistNumber")
    private BigDecimal assistNumber;			//
    
	@EmiColumn(name = "completedAssistNumber")
    private BigDecimal completedAssistNumber;			//
    
	@EmiColumn(name = "checkOkNumber")
    private BigDecimal checkOkNumber;			//
    
	@EmiColumn(name = "checkNotOkNumber")
    private BigDecimal checkNotOkNumber;			//
    
	@EmiColumn(name = "checkOkAssistNumber")
    private BigDecimal checkOkAssistNumber;			//
    
	@EmiColumn(name = "checkNotOkAssistNumber")
    private BigDecimal checkNotOkAssistNumber;			//
	
	@EmiColumn(name = "cfree1")
	private String cfree1;//自由项1
	
	@EmiColumn(name = "cfree2")
	private String cfree2;//自由项2
	
	@EmiColumn(name="turnoutNum")
	private BigDecimal turnoutNum;
	
	@EmiColumn(name="productType")
	private Integer productType;   //标准/非标准生产订单(1/2) 
	
	
	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
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

	public String getProduceOrderUid() {
		return produceOrderUid;
	}

	public void setProduceOrderUid(String produceOrderUid) {
		this.produceOrderUid = produceOrderUid;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public String getDepUid() {
		return depUid;
	}

	public void setDepUid(String depUid) {
		this.depUid = depUid;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getCompletedNum() {
		return completedNum;
	}

	public void setCompletedNum(BigDecimal completedNum) {
		this.completedNum = completedNum;
	}

	public Date getPlanComplete() {
		return planComplete;
	}

	public void setPlanComplete(Date planComplete) {
		this.planComplete = planComplete;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}

	public BigDecimal getCompletedAssistNumber() {
		return completedAssistNumber;
	}

	public void setCompletedAssistNumber(BigDecimal completedAssistNumber) {
		this.completedAssistNumber = completedAssistNumber;
	}

	public BigDecimal getCheckOkNumber() {
		return checkOkNumber;
	}

	public void setCheckOkNumber(BigDecimal checkOkNumber) {
		this.checkOkNumber = checkOkNumber;
	}

	public BigDecimal getCheckNotOkNumber() {
		return checkNotOkNumber;
	}

	public void setCheckNotOkNumber(BigDecimal checkNotOkNumber) {
		this.checkNotOkNumber = checkNotOkNumber;
	}

	public BigDecimal getCheckOkAssistNumber() {
		return checkOkAssistNumber;
	}

	public void setCheckOkAssistNumber(BigDecimal checkOkAssistNumber) {
		this.checkOkAssistNumber = checkOkAssistNumber;
	}

	public BigDecimal getCheckNotOkAssistNumber() {
		return checkNotOkAssistNumber;
	}

	public void setCheckNotOkAssistNumber(BigDecimal checkNotOkAssistNumber) {
		this.checkNotOkAssistNumber = checkNotOkAssistNumber;
	}

	public BigDecimal getTurnoutNum() {
		return turnoutNum;
	}

	public void setTurnoutNum(BigDecimal turnoutNum) {
		this.turnoutNum = turnoutNum;
	}

   
}