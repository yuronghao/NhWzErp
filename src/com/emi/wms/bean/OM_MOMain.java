package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "OM_Main")
public class OM_MOMain implements Serializable{
	
	private static final long serialVersionUID = -9210793170433856044L;

	
	@EmiColumn(name = "pk", increment=true)
	private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "billCode")
    private String billCode;

	@EmiColumn(name = "billDate")
    private Date billDate;
    
	@EmiColumn(name = "recordPersonUid")
    private String recordPersonUid;
    
	@EmiColumn(name = "departmentUid")
    private String departmentUid;
    
	@EmiColumn(name = "personUid")
    private String personUid;
    
	@EmiColumn(name = "supplierUid")
    private String supplierUid;
    
	@EmiColumn(name = "billState")
    private Integer billState;
    
	@EmiColumn(name = "notes")
    private String notes;
    
	@EmiColumn(name = "sobGid")
    private String sobGid;
    
	@EmiColumn(name = "orgGid")
    private String orgGid;

	@EmiColumn(name = "moidForSynchro")
	private String moidForSynchro;
	
	
	
	public String getMoidForSynchro() {
		return moidForSynchro;
	}

	public void setMoidForSynchro(String moidForSynchro) {
		this.moidForSynchro = moidForSynchro;
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

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
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

	public String getSupplierUid() {
		return supplierUid;
	}

	public void setSupplierUid(String supplierUid) {
		this.supplierUid = supplierUid;
	}

	public Integer getBillState() {
		return billState;
	}

	public void setBillState(Integer billState) {
		this.billState = billState;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
    
    
    
    
}