package com.emi.wms.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_ProcureArrival")
public class WmProcurearrival {
	@EmiColumn(name="pk",increment=true)
    private Integer pk;
	@EmiColumn(name="gid",ID=true)
    private String gid;
	@EmiColumn(name="notes")
    private String notes;
	@EmiColumn(name="documenttypeuid")
    private String documenttypeuid;
	@EmiColumn(name="businesstypeuid")
    private String businesstypeuid;
	@EmiColumn(name="whuid")
    private String whuid;
	@EmiColumn(name="departmentuid")
    private String departmentuid;
	@EmiColumn(name="salesmanuid")
    private String salesmanuid;
	@EmiColumn(name="supplieruid")
    private String supplieruid;
	@EmiColumn(name="billcode")
    private String billcode;
	@EmiColumn(name="billstate")
    private String billstate;
	@EmiColumn(name="billdate")
    private Date billdate;
	@EmiColumn(name="recordpersonuid")
    private String recordpersonuid;
	@EmiColumn(name="recorddate")
    private Date recorddate;
	@EmiColumn(name="recordtime")
    private Date recordtime;
	@EmiColumn(name="auditpersonuid")
    private String auditpersonuid;
	@EmiColumn(name="auditdate")
    private Date auditdate;
	@EmiColumn(name="barcode")
    private String barcode;
	@EmiColumn(name="audittime")
    private Date audittime;
	@EmiColumn(name="isreturn")
    private Integer isreturn;
	@EmiColumn(name="sobgid")
    private String sobgid;
	@EmiColumn(name="orggid")
    private String orggid;
	
	@EmiColumn(name="define1")
	private String define1;//自定义项1 是否质检
	
	@EmiColumn(name="taxRate")
	private BigDecimal taxRate;//税率
	
	private String providercustomername;
	private String personName;//业务员
	
	
	
	
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getDefine1() {
		return define1;
	}
	public void setDefine1(String define1) {
		this.define1 = define1;
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
	public String getSalesmanuid() {
		return salesmanuid;
	}
	public void setSalesmanuid(String salesmanuid) {
		this.salesmanuid = salesmanuid;
	}
	public String getSupplieruid() {
		return supplieruid;
	}
	public void setSupplieruid(String supplieruid) {
		this.supplieruid = supplieruid;
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
	public Date getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
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
	public Date getAudittime() {
		return audittime;
	}
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}
	public Integer getIsreturn() {
		return isreturn;
	}
	public void setIsreturn(Integer isreturn) {
		this.isreturn = isreturn;
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
	

    
}