package com.emi.wms.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 人员
 */
@EmiTable(name = "AA_Person")
public class AaPerson implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	@EmiColumn(name = "notes")
    private String notes;

	/**
	 * 人员编码
	 */
	@EmiColumn(name = "percode")
    private String percode;

	/**
	 * 人员姓名
	 */
	@EmiColumn(name = "pername")
    private String pername;

	/**
	 * 0：男   1：女
	 */
	@EmiColumn(name = "persex")
    private Integer persex;

	/**
	 * 出生年月
	 */
	@EmiColumn(name = "perbirthday")
	private Timestamp perbirthday;

	/**
	 * 入职日期
	 */
	@EmiColumn(name = "begindate")
    private Timestamp begindate;

	/**
	 * 离职日期
	 */
	@EmiColumn(name = "enddate")
    private Timestamp enddate;

	/**
	 * 1:在岗 0:离职
	 */
	@EmiColumn(name = "state")
    private Integer state;

	/**
	 * 对应用户UId
	 */
	@EmiColumn(name = "useruid")
    private String useruid;

	/**
	 * 启用时间
	 */
    private Date begintimes;

	/**
	 * 停用时间
	 */
    private Date endtimes;

	/**
	 * 条码
	 */
	@EmiColumn(name = "barcode")
    private String barcode;

	/**
	 * 部门编码
	 */
	@EmiColumn(name = "depGid")
    private String depGid;
	
	/**
	 * 是否删除
	 */
	@EmiColumn(name = "isDel",hasDefault=true)
	private Integer isDel;


	@EmiColumn(name = "orggid")
    private String orggid;//组织ID
	@EmiColumn(name = "sobgid")
    private String sobgid;//帐套ID
	
	
	
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

	public String getDepGid() {
		return depGid;
	}

	public void setDepGid(String depGid) {
		this.depGid = depGid;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	//部门名称
	private String depName;
	
    public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public void setPerbirthday(Timestamp perbirthday) {
		this.perbirthday = perbirthday;
	}

	public void setBegindate(Timestamp begindate) {
		this.begindate = begindate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
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

	public String getPercode() {
		return percode;
	}

	public void setPercode(String percode) {
		this.percode = percode;
	}

	public String getPername() {
		return pername;
	}

	public void setPername(String pername) {
		this.pername = pername;
	}

	public Integer getPersex() {
		return persex;
	}

	public void setPersex(Integer persex) {
		this.persex = persex;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUseruid() {
		return useruid;
	}

	public void setUseruid(String useruid) {
		this.useruid = useruid;
	}

	public Date getBegintimes() {
		return begintimes;
	}

	public void setBegintimes(Date begintimes) {
		this.begintimes = begintimes;
	}

	public Date getEndtimes() {
		return endtimes;
	}

	public void setEndtimes(Date endtimes) {
		this.endtimes = endtimes;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Timestamp getPerbirthday() {
		return perbirthday;
	}

	public Timestamp getBegindate() {
		return begindate;
	}

	public Timestamp getEnddate() {
		return enddate;
	}


  



    
}