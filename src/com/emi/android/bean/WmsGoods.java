package com.emi.android.bean;

import com.emi.wms.bean.WmAllocationstock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class WmsGoods implements Serializable {

	private static final long serialVersionUID = 2265543686697887553L;
	
	private String goodsBarCode; //物品条码
	private String goodsCode; //物品编码
	private String goodsName; //物品名称
	private String goodsStandard; //规格型号
	private String goodsUid;//物品uid
	private String goodsUnitMainName; //物品单位主名称
	private String goodsUnitAssistName; //物品单位辅名称
	private Integer invBatch; //是否批次管理
	private Integer isInvQuality; //是否保质期管理
	private Integer massDate; //保质期天数
	private String dvdate;//失效日期
	private String dmdate;//生产日期
	
	private String processId;//工序id
	private String processName;//工序名称
	private String batch;//批次
	private String cvMIVenCode;//代管商编码
	private String whCode;//仓库编码
	private String goodsAllocationUid;//货位Uid
	private String procureArrivalCuid;//采购到货单子表uid
	private String procureOrderCuid;//采购订单子表uid

	private String checkCuid;//质检单子表uid
	private String saleSendCuid;//销售发货单子表uid
//	private String produceCuid; //生产订单产品表uid
//	private String produceC2uid; //生产订单材料表uid
	private String produceRouteCUid;//生产订单工艺子表gid
	private String reportOrderCUid;//报工单子表gid
	private String produceRouteCGoodsUid;//生产订单工艺材料表gid
	private String materialOutCuid;	//材料出库单子表gid
	private String omMaterialsUid;//委外订单材料表gid
	private BigDecimal remainNum; //剩余应出数量(对应主计量单位)
	private BigDecimal remainQuantity; //剩余应出数量(对应辅计量单位)
	private BigDecimal submitNum;//提交时数量(对应主计量单位)
	private BigDecimal submitQuantity;//提交时数量(对应辅计量单位)
	
	private List<WmsGoodsCfree> cfree;//自由项
	private String gid;//wms存子表时对应的gid（安卓提交使用）

	private List<WmAllocationstock> wmAllocationstocks;

	public List<WmAllocationstock> getWmAllocationstocks() {
		return wmAllocationstocks;
	}

	public void setWmAllocationstocks(List<WmAllocationstock> wmAllocationstocks) {
		this.wmAllocationstocks = wmAllocationstocks;
	}

	public String getProcureOrderCuid() {
		return procureOrderCuid;
	}

	public void setProcureOrderCuid(String procureOrderCuid) {
		this.procureOrderCuid = procureOrderCuid;
	}

	public String getCvMIVenCode() {
		return cvMIVenCode;
	}
	public void setCvMIVenCode(String cvMIVenCode) {
		this.cvMIVenCode = cvMIVenCode;
	}
	public String getDmdate() {
		return dmdate;
	}
	public void setDmdate(String dmdate) {
		this.dmdate = dmdate;
	}
	public String getReportOrderCUid() {
		return reportOrderCUid;
	}
	public void setReportOrderCUid(String reportOrderCUid) {
		this.reportOrderCUid = reportOrderCUid;
	}
	public String getDvdate() {
		return dvdate;
	}
	public void setDvdate(String dvdate) {
		this.dvdate = dvdate;
	}
	public Integer getMassDate() {
		return massDate;
	}
	public void setMassDate(Integer massDate) {
		this.massDate = massDate;
	}
	public Integer getIsInvQuality() {
		return isInvQuality;
	}
	public void setIsInvQuality(Integer isInvQuality) {
		this.isInvQuality = isInvQuality;
	}
	public String getProduceRouteCUid() {
		return produceRouteCUid;
	}
	public void setProduceRouteCUid(String produceRouteCUid) {
		this.produceRouteCUid = produceRouteCUid;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public List<WmsGoodsCfree> getCfree() {
		return cfree;
	}
	public void setCfree(List<WmsGoodsCfree> cfree) {
		this.cfree = cfree;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
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
	public String getProcureArrivalCuid() {
		return procureArrivalCuid;
	}
	public void setProcureArrivalCuid(String procureArrivalCuid) {
		this.procureArrivalCuid = procureArrivalCuid;
	}
	public String getSaleSendCuid() {
		return saleSendCuid;
	}
	public void setSaleSendCuid(String saleSendCuid) {
		this.saleSendCuid = saleSendCuid;
	}
//	public String getProduceCuid() {
//		return produceCuid;
//	}
//	public void setProduceCuid(String produceCuid) {
//		this.produceCuid = produceCuid;
//	}
//	public String getProduceC2uid() {
//		return produceC2uid;
//	}
//	public void setProduceC2uid(String produceC2uid) {
//		this.produceC2uid = produceC2uid;
//	}
	public BigDecimal getRemainNum() {
		return remainNum;
	}
	public void setRemainNum(BigDecimal remainNum) {
		this.remainNum = remainNum;
	}
	public BigDecimal getRemainQuantity() {
		return remainQuantity;
	}
	public void setRemainQuantity(BigDecimal remainQuantity) {
		this.remainQuantity = remainQuantity;
	}
	public String getGoodsAllocationUid() {
		return goodsAllocationUid;
	}
	public void setGoodsAllocationUid(String goodsAllocationUid) {
		this.goodsAllocationUid = goodsAllocationUid;
	}
	public String getProduceRouteCGoodsUid() {
		return produceRouteCGoodsUid;
	}
	public void setProduceRouteCGoodsUid(String produceRouteCGoodsUid) {
		this.produceRouteCGoodsUid = produceRouteCGoodsUid;
	}
	public BigDecimal getSubmitNum() {
		return submitNum;
	}
	public void setSubmitNum(BigDecimal submitNum) {
		this.submitNum = submitNum;
	}
	public BigDecimal getSubmitQuantity() {
		return submitQuantity;
	}
	public void setSubmitQuantity(BigDecimal submitQuantity) {
		this.submitQuantity = submitQuantity;
	}
	public String getCheckCuid() {
		return checkCuid;
	}
	public void setCheckCuid(String checkCuid) {
		this.checkCuid = checkCuid;
	}
	public String getOmMaterialsUid() {
		return omMaterialsUid;
	}
	public void setOmMaterialsUid(String omMaterialsUid) {
		this.omMaterialsUid = omMaterialsUid;
	}
	public String getMaterialOutCuid() {
		return materialOutCuid;
	}
	public void setMaterialOutCuid(String materialOutCuid) {
		this.materialOutCuid = materialOutCuid;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	
	
	
	
}
