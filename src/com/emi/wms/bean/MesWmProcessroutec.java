package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工艺路线子表
 */
@EmiTable(name = "MES_WM_ProcessRouteC")
public class MesWmProcessroutec implements Serializable{

	private static final long serialVersionUID = -2574470889262521861L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "routGid")
	private String routGid;			//工艺路线主表id
	
	@EmiColumn(name = "cindex")
	private String cindex;			//顺序号
	
	@EmiColumn(name = "opGid")
	private String opGid;			//工序id
	
	@EmiColumn(name = "memo")
	private String memo;			//备注
	
	@EmiColumn(name = "nextGid")
	private String nextGid;		//后续节点id
	
	@EmiColumn(name = "preGid")
	private String preGid;		//前节点id
	
	@EmiColumn(name = "isCheck")
	private Integer isCheck;		//是否质检
	
	@EmiColumn(name = "isOut")
	private Integer isOut;		//是否委外
	
	@EmiColumn(name = "isStock")
	private Integer isStock;		//是否入库
	
	@EmiColumn(name = "isSemi")
	private Integer isSemi;		//是否半成品
	
	@EmiColumn(name = "stockGoodsId")
	private String stockGoodsId;	//入库物料id
	
	@EmiColumn(name = "semiGoodsId")
	private String semiGoodsId;	//半成品id
	
	@EmiColumn(name = "dispatchingType")
	private Integer dispatchingType;//派工对象类型 0：人  1：组
	
	@EmiColumn(name = "passRate")
	private BigDecimal passRate;	//质检可通过的合格率
	
	@EmiColumn(name = "workCenterId")
	private String workCenterId;//工作中心id
	
	@EmiColumn(name = "updateTime")
	private Date updateTime;	//更新时间
	
	@EmiColumn(name = "number")
	private BigDecimal number;//应完工数量
	
	@EmiColumn(name = "realPrice")
	private BigDecimal realPrice;//标准工价
	
	@EmiColumn(name = "standardHours")
	private BigDecimal standardHours;//标准工时
	
	@EmiColumn(name = "opdes")
	private String opdes;//工序说明
	
	@EmiColumn(name = "isMustScanMould")
	private Integer isMustScanMould;	//是否必扫模具
	
	@EmiColumn(name = "mouldControlFetch")
	private Integer mouldControlFetch;	//严格控制模具取数 
	
	private List<MesWmProcessRouteCPre> preList;//上道工序转入列表
	private List<MesWmProcessRoutecGoods> goodsList;//物料领用
	private List<MesWmProcessRouteCDispatching> dispatchingList;//派工对象
	private List<MesWmProcessRouteCEquipment> equipmentList;//设备
	private List<MesWmProcessRouteCMould> mouldList;//模具

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

	public String getRoutGid() {
		return routGid;
	}

	public void setRoutGid(String routGid) {
		this.routGid = routGid;
	}

	public String getCindex() {
		return cindex;
	}

	public void setCindex(String cindex) {
		this.cindex = cindex;
	}

	public String getOpGid() {
		return opGid;
	}

	public void setOpGid(String opGid) {
		this.opGid = opGid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNextGid() {
		return nextGid;
	}

	public void setNextGid(String nextGid) {
		this.nextGid = nextGid;
	}

	public String getPreGid() {
		return preGid;
	}

	public void setPreGid(String preGid) {
		this.preGid = preGid;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public Integer getIsOut() {
		return isOut;
	}

	public void setIsOut(Integer isOut) {
		this.isOut = isOut;
	}

	public Integer getIsStock() {
		return isStock;
	}

	public void setIsStock(Integer isStock) {
		this.isStock = isStock;
	}

	public Integer getDispatchingType() {
		return dispatchingType;
	}

	public void setDispatchingType(Integer dispatchingType) {
		this.dispatchingType = dispatchingType;
	}

	public List<MesWmProcessRouteCPre> getPreList() {
		return preList;
	}

	public void setPreList(List<MesWmProcessRouteCPre> preList) {
		this.preList = preList;
	}

	public List<MesWmProcessRoutecGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<MesWmProcessRoutecGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public List<MesWmProcessRouteCDispatching> getDispatchingList() {
		return dispatchingList;
	}

	public void setDispatchingList(
			List<MesWmProcessRouteCDispatching> dispatchingList) {
		this.dispatchingList = dispatchingList;
	}

	public BigDecimal getPassRate() {
		return passRate;
	}

	public void setPassRate(BigDecimal passRate) {
		this.passRate = passRate;
	}

	public String getWorkCenterId() {
		return workCenterId;
	}

	public void setWorkCenterId(String workCenterId) {
		this.workCenterId = workCenterId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<MesWmProcessRouteCEquipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<MesWmProcessRouteCEquipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public String getStockGoodsId() {
		return stockGoodsId;
	}

	public void setStockGoodsId(String stockGoodsId) {
		this.stockGoodsId = stockGoodsId;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public Integer getIsSemi() {
		return isSemi;
	}

	public void setIsSemi(Integer isSemi) {
		this.isSemi = isSemi;
	}

	public String getSemiGoodsId() {
		return semiGoodsId;
	}

	public void setSemiGoodsId(String semiGoodsId) {
		this.semiGoodsId = semiGoodsId;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public List<MesWmProcessRouteCMould> getMouldList() {
		return mouldList;
	}

	public void setMouldList(List<MesWmProcessRouteCMould> mouldList) {
		this.mouldList = mouldList;
	}

	public BigDecimal getStandardHours() {
		return standardHours;
	}

	public void setStandardHours(BigDecimal standardHours) {
		this.standardHours = standardHours;
	}

	public String getOpdes() {
		return opdes;
	}

	public void setOpdes(String opdes) {
		this.opdes = opdes;
	}

	public Integer getIsMustScanMould() {
		return isMustScanMould;
	}

	public void setIsMustScanMould(Integer isMustScanMould) {
		this.isMustScanMould = isMustScanMould;
	}

	public Integer getMouldControlFetch() {
		return mouldControlFetch;
	}

	public void setMouldControlFetch(Integer mouldControlFetch) {
		this.mouldControlFetch = mouldControlFetch;
	}



	
	
}
