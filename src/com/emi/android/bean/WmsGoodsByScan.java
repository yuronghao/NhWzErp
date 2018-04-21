package com.emi.android.bean;

import com.emi.wms.bean.WmAllocationstock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class WmsGoodsByScan implements Serializable {

	private static final long serialVersionUID = 2265543686697887553L;
	
	
	private Integer success;//0失败 1成功
	private String failInfor;//失败时消息
	
	private String goodsBarCode; //物品条码
	private String goodsCode; //物品编码
	private String goodsName; //物品名称
	private String goodsStandard; //规格型号
	private String goodsUid;//物品uid
	private String goodsUnitMainName;   //物品单位主名称
	private String goodsUnitAssistName; //物品单位辅名称
	private Integer invBatch; //是否批次管理
	private Integer isInvQuality; //是否保质期管理
	private Integer massDate; //保质期天数

	private String goodsAllocationUid;//货位Uid
	private String tempAllocationCode;//货位Code
	private String tempAllocationName;//货位Name


	public String getGoodsAllocationUid() {
		return goodsAllocationUid;
	}

	public void setGoodsAllocationUid(String goodsAllocationUid) {
		this.goodsAllocationUid = goodsAllocationUid;
	}

	public String getTempAllocationCode() {
		return tempAllocationCode;
	}

	public void setTempAllocationCode(String tempAllocationCode) {
		this.tempAllocationCode = tempAllocationCode;
	}

	public String getTempAllocationName() {
		return tempAllocationName;
	}

	public void setTempAllocationName(String tempAllocationName) {
		this.tempAllocationName = tempAllocationName;
	}

	private List<WmsGoodsCfree> cfree;//自由项

	private List<WmAllocationstock>  wmAllocationstocks;//自由项


	public List<WmAllocationstock> getWmAllocationstocks() {
		return wmAllocationstocks;
	}

	public void setWmAllocationstocks(List<WmAllocationstock> wmAllocationstocks) {
		this.wmAllocationstocks = wmAllocationstocks;
	}

	public String getGoodsBarCode() {
		return goodsBarCode;
	}

	public void setGoodsBarCode(String goodsBarCode) {
		this.goodsBarCode = goodsBarCode;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public String getGoodsUnitMainName() {
		return goodsUnitMainName;
	}

	public void setGoodsUnitMainName(String goodsUnitMainName) {
		this.goodsUnitMainName = goodsUnitMainName;
	}

	public String getGoodsUnitAssistName() {
		return goodsUnitAssistName;
	}

	public void setGoodsUnitAssistName(String goodsUnitAssistName) {
		this.goodsUnitAssistName = goodsUnitAssistName;
	}

	public Integer getInvBatch() {
		return invBatch;
	}

	public void setInvBatch(Integer invBatch) {
		this.invBatch = invBatch;
	}

	public Integer getIsInvQuality() {
		return isInvQuality;
	}

	public void setIsInvQuality(Integer isInvQuality) {
		this.isInvQuality = isInvQuality;
	}

	public Integer getMassDate() {
		return massDate;
	}

	public void setMassDate(Integer massDate) {
		this.massDate = massDate;
	}

	public List<WmsGoodsCfree> getCfree() {
		return cfree;
	}

	public void setCfree(List<WmsGoodsCfree> cfree) {
		this.cfree = cfree;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getFailInfor() {
		return failInfor;
	}

	public void setFailInfor(String failInfor) {
		this.failInfor = failInfor;
	}
	
	
	
}
