package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_OtherWarehouse")
public class WmOtherwarehouse implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5300075804316546413L;

	
	@EmiColumn(increment=true,name="pk")
	private Integer pk;
	
	@EmiColumn(ID=true,name="gid")
	private String gid;
	
	@EmiColumn(name="notes")
	private String notes;
	
	@EmiColumn(name="documentTypeId")
	private String documentTypeId;
	
	@EmiColumn(name="businessTypeId")
	private String businessTypeId;
	
	@EmiColumn(name="whUid")
	private String whUid;
	
	@EmiColumn(name="depUid")
	private String depUid;
	
	@EmiColumn(name="personUid")
	private String personUid;
	
	@EmiColumn(name="billCode")
	private String billCode;
	
	@EmiColumn(name="billState")
	private String billState;
	
	@EmiColumn(name="billDate")
	private Date billDate;
	
	@EmiColumn(name="recordPersonId")
	private String recordPersonId;
	
	@EmiColumn(name="recordDate")
	private Date recordDate;
	
	@EmiColumn(name="auditPerson")
	private String auditPerson;
	
	@EmiColumn(name="auditDate")
	private Date auditDate;
	
	@EmiColumn(name="barCode")
	private String barCode;
	
	@EmiColumn(name="orgGid")
	private String orgGid;
	
	@EmiColumn(name="sobGid")
	private String sobGid;
	
	@EmiColumn(name="accounting")
	private Integer accounting;
	
	@EmiColumn(name="lock")
	private Integer lock;

	@EmiColumn(name = "badge")
    private Integer badge;
	
	private String recordPersonName;
	private String departName;
	private String wareHouseName;
	private String goodsUid;
	private String goodsCode;

	private BigDecimal number;

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	private String unitName;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getBadge() {
		return badge;
	}

	public void setBadge(Integer badge) {
		this.badge = badge;
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

	public String getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(String documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getWhUid() {
		return whUid;
	}

	public void setWhUid(String whUid) {
		this.whUid = whUid;
	}

	public String getDepUid() {
		return depUid;
	}

	public void setDepUid(String depUid) {
		this.depUid = depUid;
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

	public String getRecordPersonId() {
		return recordPersonId;
	}

	public void setRecordPersonId(String recordPersonId) {
		this.recordPersonId = recordPersonId;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
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

	public String getOrgGid() {
		return orgGid;
	}

	public void setOrgGid(String orgGid) {
		this.orgGid = orgGid;
	}

	public String getSobGid() {
		return sobGid;
	}

	public void setSobGid(String sobGid) {
		this.sobGid = sobGid;
	}

	public Integer getAccounting() {
		return accounting;
	}

	public void setAccounting(Integer accounting) {
		this.accounting = accounting;
	}

	public Integer getLock() {
		return lock;
	}

	public void setLock(Integer lock) {
		this.lock = lock;
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

	
	
	
}