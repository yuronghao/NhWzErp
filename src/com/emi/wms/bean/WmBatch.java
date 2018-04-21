package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="wm_batch")
public class WmBatch implements Serializable{

	private static final long serialVersionUID = -8023016312124501929L;

	@EmiColumn(name = "pk", increment = true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "goodsUid")
	private String goodsUid;//物品uid
	
	@EmiColumn(name = "goodsAllocationUid")
	private String goodsAllocationUid;//货位uid
	
	@EmiColumn(name = "batch")
	private String batch;//批次
	
	@EmiColumn(name = "number")
	private BigDecimal number;//数量(入库为正数，出库为负数)
	
	@EmiColumn(name = "assistNum")
	private BigDecimal assistNum;//辅助数量
	
	@EmiColumn(name = "redBlueFlag")
	private Integer redBlueFlag;//红蓝标志         1、蓝字单据(数量>0)，0、红字单据(数量<0)
	
	@EmiColumn(name = "recordDate")
	private Timestamp recordDate;//生成日期
	
	@EmiColumn(name="cfree1" )
	private String cfree1;
	
	@EmiColumn(name="cfree2" )
	private String cfree2;
	
	

	public String getCfree1() {
		return cfree1;
	}

	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}

	public String getCfree2() {
		return cfree2;
	}

	public void setCfree2(String cfree2) {
		this.cfree2 = cfree2;
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

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public String getGoodsAllocationUid() {
		return goodsAllocationUid;
	}

	public void setGoodsAllocationUid(String goodsAllocationUid) {
		this.goodsAllocationUid = goodsAllocationUid;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getAssistNum() {
		return assistNum;
	}

	public void setAssistNum(BigDecimal assistNum) {
		this.assistNum = assistNum;
	}

	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getRedBlueFlag() {
		return redBlueFlag;
	}

	public void setRedBlueFlag(Integer redBlueFlag) {
		this.redBlueFlag = redBlueFlag;
	}
	
	
}
