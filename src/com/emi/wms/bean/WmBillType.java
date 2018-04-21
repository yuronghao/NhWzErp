package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_BillType")
public class WmBillType implements Serializable{
	private static final long serialVersionUID = 5972185498993605129L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	//gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;

	//备注 
	@EmiColumn(name="notes" )
    private String notes;

	//单据类型编号
	@EmiColumn(name="billCode" )
    private String billCode;
	
	//单据名称
	@EmiColumn(name="billName" )
    private String billName;

	//开始时间
	@EmiColumn(name="beginTimes" )
    private Date beginTimes;
	
	//结束时间
	@EmiColumn(name="endTimes" )
    private Date endTimes;

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

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public Date getBeginTimes() {
		return beginTimes;
	}

	public void setBeginTimes(Date beginTimes) {
		this.beginTimes = beginTimes;
	}

	public Date getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(Date endTimes) {
		this.endTimes = endTimes;
	}
	
	
}
