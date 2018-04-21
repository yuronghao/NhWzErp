package com.emi.android.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class WmsTaskDetailRsp implements Serializable {

	private static final long serialVersionUID = 7122786953650204813L;
	
	private Integer success;//0失败 1成功
	private String billCode;//单据号码 可以是采购到货单号、销售发货单号、生产订单号等
	private String billUid;//单据uid,可以是采购到货单uid、销售发货单uid 生产订单主表uid
	private String providercustomerName;//客商名称
	private String billDate;//单据日期
	private String processName;//工序名称（工序领料使用）
	private String userGid;//用户gid
	private String rdStyle;//出入库类别编号
	private String rdStyleName;//出入库类别名称
	private String dptGid;//部门gid
	private String dptName;//部门编码
	private String taskGid;//任务gid
	private Integer isfinish;//任务是否结束
	private Integer isReturn;//是否退货
	private String gid;//安卓提交使用
	private String orggid;//组织（安卓提交使用）
	private String sobgid;//帐套（安卓提交使用）

	private String notes;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	private String whCode;

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	private List<WmsGoods> wmsGoodsLists;//物品详情
	
	
	public Integer getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(Integer isReturn) {
		this.isReturn = isReturn;
	}
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
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getBillUid() {
		return billUid;
	}
	public void setBillUid(String billUid) {
		this.billUid = billUid;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public List<WmsGoods> getWmsGoodsLists() {
		return wmsGoodsLists;
	}
	public void setWmsGoodsLists(List<WmsGoods> wmsGoodsLists) {
		this.wmsGoodsLists = wmsGoodsLists;
	}
	public String getProvidercustomerName() {
		return providercustomerName;
	}
	public void setProvidercustomerName(String providercustomerName) {
		this.providercustomerName = providercustomerName;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getUserGid() {
		return userGid;
	}
	public void setUserGid(String userGid) {
		this.userGid = userGid;
	}
	public String getRdStyle() {
		return rdStyle;
	}
	public void setRdStyle(String rdStyle) {
		this.rdStyle = rdStyle;
	}
	public Integer getIsfinish() {
		return isfinish;
	}
	public void setIsfinish(Integer isfinish) {
		this.isfinish = isfinish;
	}
	public String getTaskGid() {
		return taskGid;
	}
	public void setTaskGid(String taskGid) {
		this.taskGid = taskGid;
	}
	public String getDptGid() {
		return dptGid;
	}
	public void setDptGid(String dptGid) {
		this.dptGid = dptGid;
	}
	public String getRdStyleName() {
		return rdStyleName;
	}
	public void setRdStyleName(String rdStyleName) {
		this.rdStyleName = rdStyleName;
	}
	public String getDptName() {
		return dptName;
	}
	public void setDptName(String dptName) {
		this.dptName = dptName;
	}
	
	
	
}
