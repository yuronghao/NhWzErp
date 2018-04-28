package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.io.Serializable;
import java.util.Date;

@EmiTable(name = "WM_MaterialApply")
public class WmMaterialapply implements Serializable {

	private static final long serialVersionUID = -8848316127916729915L;

	@EmiColumn(name = "pk", increment = true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "notes")
    private String notes;
	
	@EmiColumn(name = "documenttypeuid")
    private String documenttypeuid;//单据类型
	
	@EmiColumn(name = "businesstypeuid")
    private String businesstypeuid;//业务类型

	@EmiColumn(name = "rdstylegid")
	private String rdstylegid;

	public String getRdstylegid() {
		return rdstylegid;
	}

	public void setRdstylegid(String rdstylegid) {
		this.rdstylegid = rdstylegid;
	}

	@EmiColumn(name = "whuid")
    private String whuid;//仓库uid
	
	@EmiColumn(name = "departmentuid")
    private String departmentuid;//部门uid
	
	@EmiColumn(name = "personuid")
    private String personuid;//业务员uid
	
	@EmiColumn(name = "billcode")
    private String billcode;//单据号
	
	@EmiColumn(name = "billstate")
    private String billstate;//单据状态
	
	@EmiColumn(name = "billdate")
    private Date billdate;//单据日期
	
	@EmiColumn(name = "recordperson")
    private String recordperson;//录入人uid
	
	@EmiColumn(name = "recorddate")
    private Date recorddate;//录入日期
	
	@EmiColumn(name = "recordtime")
    private Date recordtime;//录入时间
	
	@EmiColumn(name = "auditperson")
    private String auditperson;//审核人uid
	
	@EmiColumn(name = "auditdate")
    private Date auditdate;//审核日期
	
	@EmiColumn(name = "audittime")
    private Date audittime;//审核时间
	
	@EmiColumn(name = "barcode")
    private String barcode;//条码
	
	@EmiColumn(name = "sobgid")
    private String sobgid;//帐套id
	
	@EmiColumn(name = "orggid")
    private String orggid;//组织ID
	
    private Integer lock;//是否锁定
    private Integer accounting;//是否记账
    
    @EmiColumn(name = "badge")
    private Integer badge;//入库标记(蓝字单据1、红字单据0)
    
	private String recordPersonName;
	private String departName;
	private String wareHouseName;
	
    
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
	public String getDocumenttypeuid() {
		return documenttypeuid;
	}
	public void setDocumenttypeuid(String documenttypeuid) {
		this.documenttypeuid = documenttypeuid;
	}
	public String getBusinesstypeuid() {
		return businesstypeuid;
	}
	public void setBusinesstypeuid(String businesstypeuid) {
		this.businesstypeuid = businesstypeuid;
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
	public String getRecordperson() {
		return recordperson;
	}
	public void setRecordperson(String recordperson) {
		this.recordperson = recordperson;
	}
	public Date getRecorddate() {
		return recorddate;
	}
	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}
	public Date getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}
	public String getAuditperson() {
		return auditperson;
	}
	public void setAuditperson(String auditperson) {
		this.auditperson = auditperson;
	}
	public Date getAuditdate() {
		return auditdate;
	}
	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}
	public Date getAudittime() {
		return audittime;
	}
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
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