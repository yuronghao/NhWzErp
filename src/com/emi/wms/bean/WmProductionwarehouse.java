package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_ProductionWarehouse")
public class WmProductionwarehouse implements Serializable {
	
	private static final long serialVersionUID = 66021377900138062L;
	
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;
	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "notes")
    private String notes;

	@EmiColumn(name = "documenttypeid")
    private String documenttypeid;

	@EmiColumn(name = "businesstypeid")
    private String businesstypeid;

	@EmiColumn(name = "whuid")
    private String whuid;

	@EmiColumn(name = "departmentuid")
    private String departmentuid;

	@EmiColumn(name = "personuid")
    private String personuid;

	@EmiColumn(name = "billcode")
    private String billcode;

	@EmiColumn(name = "billstate")
    private String billstate;

	@EmiColumn(name = "billdate")
    private Date billdate;

	@EmiColumn(name = "recordpersonid")
    private String recordpersonid;

	@EmiColumn(name = "recorddate")
    private Date recorddate;

	@EmiColumn(name = "auditpersonid")
    private String auditpersonid;

	@EmiColumn(name = "auditdate")
    private Date auditdate;

	@EmiColumn(name = "barcode")
    private String barcode;

	@EmiColumn(name = "orggid")
    private String orggid;

	@EmiColumn(name = "sobgid")
    private String sobgid;

	@EmiColumn(name = "accounting")
    private Integer accounting;

	@EmiColumn(name = "lock")
    private Integer lock;
	@EmiColumn(name = "badge")
    private Integer badge;
	
	private String recordPersonName;
	private String departName;
	private String wareHouseName;
	private String goodsGid;
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

	public Integer getBadge() {
		return badge;
	}

	public void setBadge(Integer badge) {
		this.badge = badge;
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

	public String getDocumenttypeid() {
		return documenttypeid;
	}

	public void setDocumenttypeid(String documenttypeid) {
		this.documenttypeid = documenttypeid;
	}

	public String getBusinesstypeid() {
		return businesstypeid;
	}

	public void setBusinesstypeid(String businesstypeid) {
		this.businesstypeid = businesstypeid;
	}

	public String getWhuid() {
		return whuid;
	}

	public void setWhuid(String whuid) {
		this.whuid = whuid;
	}

	public String getDepartmentuid() {
		return departmentuid;
	}

	public void setDepartmentuid(String departmentuid) {
		this.departmentuid = departmentuid;
	}

	public String getPersonuid() {
		return personuid;
	}

	public void setPersonuid(String personuid) {
		this.personuid = personuid;
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

	public String getRecordpersonid() {
		return recordpersonid;
	}

	public void setRecordpersonid(String recordpersonid) {
		this.recordpersonid = recordpersonid;
	}

	public Date getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}

	public String getAuditpersonid() {
		return auditpersonid;
	}

	public void setAuditpersonid(String auditpersonid) {
		this.auditpersonid = auditpersonid;
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

	public String getOrggid() {
		return orggid;
	}

	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}

	public String getSobgid() {
		return sobgid;
	}

	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
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

	public String getGoodsGid() {
		return goodsGid;
	}

	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

   
}