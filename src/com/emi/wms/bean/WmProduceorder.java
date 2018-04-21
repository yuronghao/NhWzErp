package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "WM_ProduceOrder")
public class WmProduceorder implements Serializable {

	private static final long serialVersionUID = 1746221614728247805L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "notes")
    private String notes;
	
	@EmiColumn(name = "billcode")
    private String billcode;//单据号
	
	@EmiColumn(name = "billstate")
    private String billstate;//单据状态
	
	@EmiColumn(name = "billdate")
    private Date billdate;//单据日期
	
	@EmiColumn(name = "recordpersonuid")
    private String recordpersonuid;
	
	@EmiColumn(name = "recorddate")
    private Date recorddate;

	@EmiColumn(name = "departmentUid")
	private  String departmentUid;

	
	@EmiColumn(name = "auditpersonuid")
    private String auditpersonuid;
	
	@EmiColumn(name = "auditdate")
    private Date auditdate;
	
	@EmiColumn(name = "barcode")
    private String barcode;
	
	@EmiColumn(name = "sobgid")
    private String sobgid;
	
	@EmiColumn(name = "orggid")
    private String orggid;
	
	@EmiColumn(name = "deptGid")
    private String deptGid;
	
	@EmiColumn(name = "managerGid")
    private String managerGid;
	
	@EmiColumn(name = "changeOrder")
	private Integer changeOrder;//是否是改制订单

	public String getDepartmentUid() {
		return departmentUid;
	}

	public void setDepartmentUid(String departmentUid) {
		this.departmentUid = departmentUid;
	}

	private String recordPersonName;
	private String departName;
	private String wareHouseName;
	private String goodsCode;

	public String getRecordPersonName() {
		return recordPersonName;
	}

	public void setRecordPersonName(String recordPersonName) {
		this.recordPersonName = recordPersonName;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	private String providercustomername;
	private String goodsUid;
	private BigDecimal number;
	private BigDecimal completedNum;
	private Date startDate;
	private Date endDate;
	private BigDecimal turnoutNum;
	private String note;
	private AaGoods aagoods;
	private String sourcebillCade;//旧生产订单编码
    
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
	public String getBillcode() {
		return billcode;
	}
	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}
	public String getBillstate() {
		return billstate;
	}
	public void setBillstate(String billstate) {
		this.billstate = billstate;
	}
	public Date getBilldate() {
		return billdate;
	}
	public void setBilldate(Date billdate) {
		this.billdate = billdate;
	}
	public String getRecordpersonuid() {
		return recordpersonuid;
	}
	public void setRecordpersonuid(String recordpersonuid) {
		this.recordpersonuid = recordpersonuid;
	}
	public Date getRecorddate() {
		return recorddate;
	}
	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}
	public String getAuditpersonuid() {
		return auditpersonuid;
	}
	public void setAuditpersonuid(String auditpersonuid) {
		this.auditpersonuid = auditpersonuid;
	}
	public Date getAuditdate() {
		return auditdate;
	}
	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getSobgid() {
		return sobgid;
	}
	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
	}
	public String getOrggid() {
		return orggid;
	}
	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}
	public String getProvidercustomername() {
		return providercustomername;
	}
	public void setProvidercustomername(String providercustomername) {
		this.providercustomername = providercustomername;
	}
	public String getDeptGid() {
		return deptGid;
	}
	public void setDeptGid(String deptGid) {
		this.deptGid = deptGid;
	}
	public String getManagerGid() {
		return managerGid;
	}
	public void setManagerGid(String managerGid) {
		this.managerGid = managerGid;
	}
	public Integer getChangeOrder() {
		return changeOrder;
	}
	public void setChangeOrder(Integer changeOrder) {
		this.changeOrder = changeOrder;
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
	public BigDecimal getCompletedNum() {
		return completedNum;
	}
	public void setCompletedNum(BigDecimal completedNum) {
		this.completedNum = completedNum;
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
	public BigDecimal getTurnoutNum() {
		return turnoutNum;
	}
	public void setTurnoutNum(BigDecimal turnoutNum) {
		this.turnoutNum = turnoutNum;
	}
	public AaGoods getAagoods() {
		return aagoods;
	}
	public void setAagoods(AaGoods aagoods) {
		this.aagoods = aagoods;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSourcebillCade() {
		return sourcebillCade;
	}
	public void setSourcebillCade(String sourcebillCade) {
		this.sourcebillCade = sourcebillCade;
	}
	
    
   
}