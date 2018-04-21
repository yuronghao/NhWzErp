package com.emi.android.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProcessTaskPersonRsp implements Serializable{

	private static final long serialVersionUID = -3365274282880746681L;
	
	private String personUnitVendorGid;//人员或者工作组或者委外商gid
	private String personUnitVendorBarcode;//人员或者工作组或者委外商条码
	private String personUnitVendorCode;//人员或者工作组或者委外商编码
	private String personUnitVendorName;//人员或者工作组或者委外商名称
	private BigDecimal canDisnum;//可开工数量
	private BigDecimal canReprotNum;//可报工数量
	private BigDecimal canCheckNum;//可质检数量
	private String produceProcessRoutecGid;//订单工艺路线子表gid
	private String discGid;//派工单子表gid
	private String rptcGid;//报工单子表gid
	private String notes;//备注
	private String disTime;//派工时间
	private Integer workingTime;//0白班 1夜班 
	
	
	
	
	public Integer getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(Integer workingTime) {
		this.workingTime = workingTime;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getRptcGid() {
		return rptcGid;
	}
	public void setRptcGid(String rptcGid) {
		this.rptcGid = rptcGid;
	}
	public String getProduceProcessRoutecGid() {
		return produceProcessRoutecGid;
	}
	public void setProduceProcessRoutecGid(String produceProcessRoutecGid) {
		this.produceProcessRoutecGid = produceProcessRoutecGid;
	}
	public String getDiscGid() {
		return discGid;
	}
	public void setDiscGid(String discGid) {
		this.discGid = discGid;
	}
	public String getPersonUnitVendorBarcode() {
		return personUnitVendorBarcode;
	}
	public void setPersonUnitVendorBarcode(String personUnitVendorBarcode) {
		this.personUnitVendorBarcode = personUnitVendorBarcode;
	}
	public String getPersonUnitVendorGid() {
		return personUnitVendorGid;
	}
	public void setPersonUnitVendorGid(String personUnitVendorGid) {
		this.personUnitVendorGid = personUnitVendorGid;
	}
	public String getPersonUnitVendorCode() {
		return personUnitVendorCode;
	}
	public void setPersonUnitVendorCode(String personUnitVendorCode) {
		this.personUnitVendorCode = personUnitVendorCode;
	}
	public String getPersonUnitVendorName() {
		return personUnitVendorName;
	}
	public void setPersonUnitVendorName(String personUnitVendorName) {
		this.personUnitVendorName = personUnitVendorName;
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
	public BigDecimal getCanCheckNum() {
		return canCheckNum;
	}
	public void setCanCheckNum(BigDecimal canCheckNum) {
		this.canCheckNum = canCheckNum;
	}
	public String getDisTime() {
		return disTime;
	}
	public void setDisTime(String disTime) {
		this.disTime = disTime;
	}
	




	
	

}
