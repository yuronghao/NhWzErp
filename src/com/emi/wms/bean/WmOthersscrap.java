package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EmiTable(name="WM_OthersScrap")
public class WmOthersscrap implements Serializable{
	private static final long serialVersionUID = -6694037525355641124L;

	@EmiColumn(increment=true,name="pk")
	private Integer pk;
	
	@EmiColumn(ID=true,name="gid")
	private String gid;
	
	@EmiColumn(name="notes")
	private String notes;
	
	@EmiColumn(name="documentTypeUid")
	private String documentTypeUid;


	@EmiColumn(name="businessTypeUid")
	private String businessTypeUid;
	
	@EmiColumn(name="warehouseUid")
	private String warehouseUid;
	
	@EmiColumn(name="departmentUid")
	private String departmentUid;
	
	@EmiColumn(name="personUid")
	private String personUid;
	
	@EmiColumn(name="billCode")
	private String billCode;
	
	@EmiColumn(name="billState")
	private String billState;
	
	@EmiColumn(name="billDate")
	private Date billDate;
	
	@EmiColumn(name="recordPersonUid")
	private String recordPersonUid;
	
	@EmiColumn(name="recordDate")
	private Date recordDate;
	
	@EmiColumn(name="auditPersonUid")
	private String auditPersonUid;
	
	@EmiColumn(name="auditDate")
	private Date auditDate;
	
	@EmiColumn(name="barCode")
	private String barCode;
	
	@EmiColumn(name="sobGid")
	private String sobGid;
	
	@EmiColumn(name="orgGid")
	private String orgGid;
	
	@EmiColumn(name="lock")
	private Integer lock;
	
	@EmiColumn(name="accounting")
	private Integer accounting;

	@EmiColumn(name="status")
	private Integer status;

	private Integer followmovinggid;


	public Integer getFollowmovinggid() {
		return followmovinggid;
	}

	public void setFollowmovinggid(Integer followmovinggid) {
		this.followmovinggid = followmovinggid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private String recordPersonName;
	private String departName;
	private String wareHouseName;
	private String goodsUid;
	private String goodsCode;

	private BigDecimal number;


	private String unitName;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

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

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
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

	public String getDocumentTypeUid() {
		return documentTypeUid;
	}

	public void setDocumentTypeUid(String documentTypeUid) {
		this.documentTypeUid = documentTypeUid;
	}

	public String getBusinessTypeUid() {
		return businessTypeUid;
	}

	public void setBusinessTypeUid(String businessTypeUid) {
		this.businessTypeUid = businessTypeUid;
	}

	public String getWarehouseUid() {
		return warehouseUid;
	}

	public void setWarehouseUid(String warehouseUid) {
		this.warehouseUid = warehouseUid;
	}

	public String getDepartmentUid() {
		return departmentUid;
	}

	public void setDepartmentUid(String departmentUid) {
		this.departmentUid = departmentUid;
	}

	public String getPersonUid() {
		return personUid;
	}

	public void setPersonUid(String personUid) {
		this.personUid = personUid;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getRecordPersonUid() {
		return recordPersonUid;
	}

	public void setRecordPersonUid(String recordPersonUid) {
		this.recordPersonUid = recordPersonUid;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getAuditPersonUid() {
		return auditPersonUid;
	}

	public void setAuditPersonUid(String auditPersonUid) {
		this.auditPersonUid = auditPersonUid;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSobGid() {
		return sobGid;
	}

	public void setSobGid(String sobGid) {
		this.sobGid = sobGid;
	}

	public String getOrgGid() {
		return orgGid;
	}

	public void setOrgGid(String orgGid) {
		this.orgGid = orgGid;
	}

	public Integer getLock() {
		return lock;
	}

	public void setLock(Integer lock) {
		this.lock = lock;
	}

	public Integer getAccounting() {
		return accounting;
	}

	public void setAccounting(Integer accounting) {
		this.accounting = accounting;
	}
	
	
	

}