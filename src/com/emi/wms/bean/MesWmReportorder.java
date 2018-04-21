package com.emi.wms.bean;

import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_WM_ReportOrder")
public class MesWmReportorder {

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "rptcode")
    private String rptcode;//报工单号

	@EmiColumn(name = "auditTime")
    private Date auditTime;//审核时间

	@EmiColumn(name = "auditPersonUid")
    private String auditPersonUid;//审核人

	@EmiColumn(name = "billDate")
    private Date billDate;//单据时间

	@EmiColumn(name = "recordPersonUid")
    private String recordPersonUid;//键入人
	
	@EmiColumn(name = "dispatchingObj")
	private Integer dispatchingObj;//派工对象 0：人  1：组 3委外
	
	@EmiColumn(name = "memo")
    private String memo;

	@EmiColumn(name = "sobGid")
    private String sobGid;

	@EmiColumn(name = "orgGid")
    private String orgGid;
	
	@EmiColumn(name = "stationGid")
	private String stationGid;//
	
	@EmiColumn(name = "batch")
	private String batch;//批次

	@EmiColumn(name = "repeatGid")
	private String repeatGid;//防止重复提交的gid
	
	
	public String getRepeatGid() {
		return repeatGid;
	}

	public void setRepeatGid(String repeatGid) {
		this.repeatGid = repeatGid;
	}

	public String getStationGid() {
		return stationGid;
	}

	public void setStationGid(String stationGid) {
		this.stationGid = stationGid;
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

	public String getRptcode() {
		return rptcode;
	}

	public void setRptcode(String rptcode) {
		this.rptcode = rptcode;
	}

	public String getAuditPersonUid() {
		return auditPersonUid;
	}

	public void setAuditPersonUid(String auditPersonUid) {
		this.auditPersonUid = auditPersonUid;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Integer getDispatchingObj() {
		return dispatchingObj;
	}

	public void setDispatchingObj(Integer dispatchingObj) {
		this.dispatchingObj = dispatchingObj;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	

  
}