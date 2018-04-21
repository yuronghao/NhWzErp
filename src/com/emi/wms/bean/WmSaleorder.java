package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_SaleOrder")
public class WmSaleorder implements Serializable{

	private static final long serialVersionUID = 8706892428818206766L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	//gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;

	//备注 
	@EmiColumn(name="notes" )
    private String notes;

	//客户uid
	@EmiColumn(name="customeruid" )
    private String customeruid;

	//销售类型 
	@EmiColumn(name="saletype" )
    private String saletype;

	//币种
	@EmiColumn(name="currency" )
    private String currency;

	//汇率
	@EmiColumn(name="exchangerate" )
    private BigDecimal exchangerate;

	//税率 
	@EmiColumn(name="rate" )
    private BigDecimal rate;

	//运输方式
	@EmiColumn(name="transportation" )
    private String transportation;

	//单据号
	@EmiColumn(name="billcode" )
    private String billcode;

	//单据状态 
	@EmiColumn(name="billstate" )
    private String billstate;

	//单据日期
	@EmiColumn(name="billdate" )
    private Date billdate;

	//录入人
	@EmiColumn(name="recordpersonuid" )
    private String recordpersonuid;

	//录入日期 
	@EmiColumn(name="recorddate" )
    private Date recorddate;

	//审核人
	@EmiColumn(name="auditpersonuid" )
    private String auditpersonuid;

	//审核日期
	@EmiColumn(name="auditdate" )
    private Date auditdate;

	//条码
	@EmiColumn(name="barcode" )
    private String barcode;

	//审核状态
	@EmiColumn(name="auditstate" )
    private Integer auditstate;

	//部门id
	@EmiColumn(name="deptGid" )
    private String deptGid;

	//业务员id 
	@EmiColumn(name="personuid" )
    private String personuid;

	//仓库id
	@EmiColumn(name="whuid" )
    private String whuid;

	//帐套ID
	@EmiColumn(name="sobgid")
    private String sobgid;

	//组织id
	@EmiColumn(name="orggid")
    private String orggid;

	private String providercustomername;
	private String goodsUid;
	private BigDecimal number;
	private BigDecimal assistNumber;
	private String note;
	private AaGoods aagoods;
	
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

	public String getCustomeruid() {
		return customeruid;
	}

	public void setCustomeruid(String customeruid) {
		this.customeruid = customeruid;
	}

	public String getSaletype() {
		return saletype;
	}

	public void setSaletype(String saletype) {
		this.saletype = saletype;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(BigDecimal exchangerate) {
		this.exchangerate = exchangerate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
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

	public Integer getAuditstate() {
		return auditstate;
	}

	public void setAuditstate(Integer auditstate) {
		this.auditstate = auditstate;
	}

	public String getDeptGid() {
		return deptGid;
	}

	public void setDeptGid(String deptGid) {
		this.deptGid = deptGid;
	}

	public String getPersonuid() {
		return personuid;
	}

	public void setPersonuid(String personuid) {
		this.personuid = personuid;
	}

	public String getWhuid() {
		return whuid;
	}

	public void setWhuid(String whuid) {
		this.whuid = whuid;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public AaGoods getAagoods() {
		return aagoods;
	}

	public void setAagoods(AaGoods aagoods) {
		this.aagoods = aagoods;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}             

}