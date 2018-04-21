package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_WM_DispatchingOrder")
public class MesWmDispatchingorder implements Serializable{

	private static final long serialVersionUID = -663606363566555528L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "stationGid")
    private String stationGid;//工位uid
 
	@EmiColumn(name = "disDate")
    private Timestamp disDate;//派工日期
    
	@EmiColumn(name = "dispatchingObj")
    private Integer dispatchingObj;//派工对象 0：人  1：组
	
	@EmiColumn(name = "billCode")
	private String billCode;//单据编码
    
	@EmiColumn(name = "sobgid")
    private String sobgid;

	@EmiColumn(name = "orggid")
    private String orggid;
	
	@EmiColumn(name = "notes")
	private String notes;//备注
	@EmiColumn(name = "mouldGid")
	private String mouldGid;//模具gid
	
	@EmiColumn(name = "repeatGid")
	private String repeatGid;//防止重复提交的gid
	
	@EmiColumn(name = "workingTime")
	private Integer workingTime; //0白班 1夜班 默认白班
	
	

	
	public Integer getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(Integer workingTime) {
		this.workingTime = workingTime;
	}

	public String getRepeatGid() {
		return repeatGid;
	}

	public void setRepeatGid(String repeatGid) {
		this.repeatGid = repeatGid;
	}

	public String getMouldGid() {
		return mouldGid;
	}

	public void setMouldGid(String mouldGid) {
		this.mouldGid = mouldGid;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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


	public String getStationGid() {
		return stationGid;
	}

	public void setStationGid(String stationGid) {
		this.stationGid = stationGid;
	}

	public Timestamp getDisDate() {
		return disDate;
	}

	public void setDisDate(Timestamp disDate) {
		this.disDate = disDate;
	}

	public Integer getDispatchingObj() {
		return dispatchingObj;
	}

	public void setDispatchingObj(Integer dispatchingObj) {
		this.dispatchingObj = dispatchingObj;
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

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

    

   
}