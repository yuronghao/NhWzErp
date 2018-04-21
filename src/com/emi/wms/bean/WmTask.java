package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="wm_task")
public class WmTask implements Serializable {
	
	private static final long serialVersionUID = -5240441616630471331L;
	
	@EmiColumn(name="pk",increment=true)
    private Integer pk;
    @EmiColumn(name="gid",ID=true)
    private String gid;
    @EmiColumn(name="notes")
    private String notes;
    @EmiColumn(name="taskname")
    private String taskname;
    @EmiColumn(name="state")
    private Integer state;
    @EmiColumn(name="locked")
    private Integer locked;
    @EmiColumn(name="completetime")
    private Date completetime;
    @EmiColumn(name="distributetime")
    private Date distributetime;
    @EmiColumn(name="billgid")
    private String billgid;
    @EmiColumn(name="tasktypeuid")
    private String tasktypeuid;
    @EmiColumn(name="sobgid")
    private String sobgid;
    @EmiColumn(name="orggid")
    private String orggid;
    @EmiColumn(name="billGidSource")
    private String billGidSource;
    @EmiColumn(name="billCode")
    private String billCode;//订单号
    
    @EmiColumn(name="whUid")
    private String whUid;//仓库uid 触发工序领料任务是否按仓库来触发
    
    @EmiColumn(name="isMeterialTaskByWhouse")
    private Integer isMeterialTaskByWhouse;//触发工序领料任务是否按仓库来触发   1是0否
    
    private String taskTypeCode;//任务类型编码
    private String orderCode;//生产订单编号
    private String goodsGid;//物品gid
    private String goodsName;//物品名称
    private String goodsStandard;//规格型号
    private String whName;//仓库名称
    
    private String batch;//批次
    private String route;//工艺路线
    private String waitNum;//待处理数量
    
    
    
    
    public String getWaitNum() {
		return waitNum;
	}


	public void setWaitNum(String waitNum) {
		this.waitNum = waitNum;
	}


	@EmiColumn(name="acceptUserGid")
    private String acceptUserGid;
    
    
    public WmTask(){
    	
    }
    
    
    public WmTask(String billgid,String billCode,String billGidSource,String tasktypeuid){
    	this.billgid=billgid;
    	this.billCode=billCode;
    	this.billGidSource=billGidSource;
    	this.tasktypeuid=tasktypeuid;
    }
    
    public WmTask(String billgid,String billCode,String billGidSource,String tasktypeuid,String whUid,int isMeterialTaskByWhouse){
    	this.billgid=billgid;
    	this.billCode=billCode;
    	this.billGidSource=billGidSource;
    	this.tasktypeuid=tasktypeuid;
    	this.whUid=whUid;
    	this.isMeterialTaskByWhouse=isMeterialTaskByWhouse;
    }
    
    
    
    
	public String getWhName() {
		return whName;
	}


	public void setWhName(String whName) {
		this.whName = whName;
	}


	public String getWhUid() {
		return whUid;
	}


	public void setWhUid(String whUid) {
		this.whUid = whUid;
	}

	public Integer getIsMeterialTaskByWhouse() {
		return isMeterialTaskByWhouse;
	}


	public void setIsMeterialTaskByWhouse(Integer isMeterialTaskByWhouse) {
		this.isMeterialTaskByWhouse = isMeterialTaskByWhouse;
	}


	public String getGoodsStandard() {
		return goodsStandard;
	}


	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}


	public String getGoodsGid() {
		return goodsGid;
	}


	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public String getAcceptUserGid() {
		return acceptUserGid;
	}


	public void setAcceptUserGid(String acceptUserGid) {
		this.acceptUserGid = acceptUserGid;
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
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public Date getCompletetime() {
		return completetime;
	}
	public void setCompletetime(Date completetime) {
		this.completetime = completetime;
	}
	public String getBillgid() {
		return billgid;
	}
	public void setBillgid(String billgid) {
		this.billgid = billgid;
	}
	public String getTasktypeuid() {
		return tasktypeuid;
	}
	public void setTasktypeuid(String tasktypeuid) {
		this.tasktypeuid = tasktypeuid;
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
	public Date getDistributetime() {
		return distributetime;
	}
	public void setDistributetime(Date distributetime) {
		this.distributetime = distributetime;
	}
	public String getBillGidSource() {
		return billGidSource;
	}
	public void setBillGidSource(String billGidSource) {
		this.billGidSource = billGidSource;
	}
	public String getTaskTypeCode() {
		return taskTypeCode;
	}
	public void setTaskTypeCode(String taskTypeCode) {
		this.taskTypeCode = taskTypeCode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((billCode == null) ? 0 : billCode.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WmTask other = (WmTask) obj;
		if (billCode == null) {
			if (other.billCode != null)
				return false;
		} else if (!billCode.equals(other.billCode))
			return false;
		return true;
	}


	public String getBatch() {
		return batch;
	}


	public void setBatch(String batch) {
		this.batch = batch;
	}


	public String getRoute() {
		return route;
	}


	public void setRoute(String route) {
		this.route = route;
	}

    
}