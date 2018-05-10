package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_Call")
public class wmCall implements Serializable{

	
	/**
	 * 备注
调出仓库uid
单据号 
单据状态 0 
单据日期
录入人 id
录入日期
审核人
审核日期
条码
帐套ID
组织id
调入仓库uid
	 */
	private static final long serialVersionUID = -3379078558404280034L;
	
	
	@EmiColumn(increment=true,name="pk")
	private Integer pk;
	
	@EmiColumn(ID=true,name="gid")
	private String gid;
	
	@EmiColumn(name="notes")
	private String notes;
	
	@EmiColumn(name="outWhUid")
	private String outWhUid;
	
	@EmiColumn(name="billCode")
	private String billCode;
	
	@EmiColumn(name="billState")
	private String billState;
	
	@EmiColumn(name="billDate")
	private Date  billDate;
	
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
	
	@EmiColumn(name="inWhUid")
	private String inWhUid;

	@EmiColumn(name="rdStyleCode")
	private String rdStyleCode;//出库类别

	@EmiColumn(name="status")
	private Integer status;//审批状态 0：未审核  1:审核中  2：已通过 3:驳回


	@EmiColumn(name="businessTypeUid")
	private String businessTypeUid;

	@EmiColumn(name="departmentUid")
	private String departmentUid;

	public String getDepartmentUid() {
		return departmentUid;
	}

	public void setDepartmentUid(String departmentUid) {
		this.departmentUid = departmentUid;
	}

	public String getBusinessTypeUid() {
		return businessTypeUid;
	}

	public void setBusinessTypeUid(String businessTypeUid) {
		this.businessTypeUid = businessTypeUid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRdStyleCode() {
		return rdStyleCode;
	}

	public void setRdStyleCode(String rdStyleCode) {
		this.rdStyleCode = rdStyleCode;
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

	public String getOutWhUid() {
		return outWhUid;
	}

	public void setOutWhUid(String outWhUid) {
		this.outWhUid = outWhUid;
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

	public String getInWhUid() {
		return inWhUid;
	}

	public void setInWhUid(String inWhUid) {
		this.inWhUid = inWhUid;
	}

	
	
	
	
	
	
	
	
	
	
}
