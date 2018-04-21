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
@EmiTable(name = "MES_WM_ProduceProcessRouteC")
public class MesWmProduceProcessroutec implements Serializable{

	private static final long serialVersionUID = -2574470889262521861L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "produceRouteGid")
	private String produceRouteGid;	//工艺路线主表id
	
	@EmiColumn(name = "cindex")
	private String cindex;			//顺序号
	
	@EmiColumn(name = "opGid")
	private String opGid;			//工序uid
	
	@EmiColumn(name = "preGid")
	private String preGid;//上一步工序号(多个用逗号割开)
	
	@EmiColumn(name = "nextGid")
	private String nextGid;//下一步工序号(多个用逗号割开)
	
	@EmiColumn(name = "isCheck")
	private Integer isCheck;//是否质检
	
	@EmiColumn(name = "isOut")
	private Integer isOut;//是否委外
	
	@EmiColumn(name = "isStock")
	private Integer isStock;//是否入库
	
	@EmiColumn(name = "isSemi")
	private Integer isSemi;//是否半成品
	
	@EmiColumn(name = "stockGoodsId")
	private String stockGoodsId;	//入库物料id
	
	@EmiColumn(name = "semiGoodsId")
	private String semiGoodsId;	//半成品id
	
	@EmiColumn(name = "realPrice")
	private BigDecimal realPrice;//标准工价
	
	@EmiColumn(name = "barcode")
	private String barcode;//条码
	
	@EmiColumn(name = "number")
	private BigDecimal number;//订单数量
	
	@EmiColumn(name = "canDispatchNum")
	private BigDecimal canDispatchNum;//可派工数量
	
	@EmiColumn(name = "dispatchedNum")
	private BigDecimal dispatchedNum;//已派工数量
	
	@EmiColumn(name = "reportOkNum")
	private BigDecimal reportOkNum;//报工合格
	
	@EmiColumn(name = "reportNotOkNum")
	private BigDecimal reportNotOkNum;//报工不合格
	
	@EmiColumn(name = "reportProblemNum")
	private BigDecimal reportProblemNum;//报工问题数量
	
	
	@EmiColumn(name = "checkOkNum")
	private BigDecimal checkOkNum;//质检合格
	
	@EmiColumn(name = "checkNotOkNum")
	private BigDecimal checkNotOkNum;//质检不合格
	
	
	
	@EmiColumn(name = "randomCheckOkNum")
	private BigDecimal randomCheckOkNum;//抽检合格
	
	@EmiColumn(name = "randomCheckNotOkNum")
	private BigDecimal randomCheckNotOkNum;//抽检不合格
	
	
	
	@EmiColumn(name = "productInNum")
	private BigDecimal productInNum;//产品入库数量
	
	@EmiColumn(name = "productCheckOkNum")
	private BigDecimal productCheckOkNum;//产品质检合格数量
	
	@EmiColumn(name = "productCheckNotOkNum")
	private BigDecimal productCheckNotOkNum;//产品质检不合格数量
	
	@EmiColumn(name = "canTurnoutNum")
	private BigDecimal canTurnoutNum;//可转出数量
	
	@EmiColumn(name = "turnoutedNum")
	private BigDecimal turnoutedNum;//已转出数量
	
	
	@EmiColumn(name = "passRate")
	private BigDecimal passRate;//质检可通过的合格率
	
	@EmiColumn(name = "dispatchingType")
	private Integer dispatchingType;//派工对象 0：人  1：组
	
	@EmiColumn(name = "workCenterId")
	private String workCenterId;//工作中心id
	
	@EmiColumn(name = "updateTime")
	private Date updateTime;	//更新时间
	
	@EmiColumn(name = "standardHours")
	private BigDecimal standardHours;//标准工时
	
	@EmiColumn(name = "opdes")
	private String opdes;//工序说明
	
	@EmiColumn(name = "isMustScanMould")
	private Integer isMustScanMould;//是否必扫模具
	
	@EmiColumn(name = "mouldControlFetch")
	private Integer mouldControlFetch;//严格控制模具取数
	
	private List<MesWmProduceProcessRouteCPre> preList;//上道工序转入列表
	private List<MesWmProduceProcessroutecGoods> goodsList;//物料领用
	private List<MesWmProduceProcessRouteCDispatching> dispatchingList;//派工对象
	private List<MesWmProduceProcessRouteCEquipment> equipmentList;//设备
	private List<MesWmProduceProcessRouteCMould> mouldList;//模具
	
	@EmiColumn(name = "isPass")
	private Integer isPass;//是否放行 0否 1是
	
	
	
	
	public BigDecimal getRandomCheckOkNum() {
		return randomCheckOkNum;
	}

	public void setRandomCheckOkNum(BigDecimal randomCheckOkNum) {
		this.randomCheckOkNum = randomCheckOkNum;
	}

	public BigDecimal getRandomCheckNotOkNum() {
		return randomCheckNotOkNum;
	}

	public void setRandomCheckNotOkNum(BigDecimal randomCheckNotOkNum) {
		this.randomCheckNotOkNum = randomCheckNotOkNum;
	}

	public Integer getIsPass() {
		return isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}

	public BigDecimal getCanDispatchNum() {
		return canDispatchNum;
	}

	public void setCanDispatchNum(BigDecimal canDispatchNum) {
		this.canDispatchNum = canDispatchNum;
	}

	public BigDecimal getCanTurnoutNum() {
		return canTurnoutNum;
	}

	public void setCanTurnoutNum(BigDecimal canTurnoutNum) {
		this.canTurnoutNum = canTurnoutNum;
	}

	public BigDecimal getTurnoutedNum() {
		return turnoutedNum;
	}

	public void setTurnoutedNum(BigDecimal turnoutedNum) {
		this.turnoutedNum = turnoutedNum;
	}

	public BigDecimal getProductCheckOkNum() {
		return productCheckOkNum;
	}

	public void setProductCheckOkNum(BigDecimal productCheckOkNum) {
		this.productCheckOkNum = productCheckOkNum;
	}

	public BigDecimal getProductCheckNotOkNum() {
		return productCheckNotOkNum;
	}

	public void setProductCheckNotOkNum(BigDecimal productCheckNotOkNum) {
		this.productCheckNotOkNum = productCheckNotOkNum;
	}

	public BigDecimal getProductInNum() {
		return productInNum;
	}

	public void setProductInNum(BigDecimal productInNum) {
		this.productInNum = productInNum;
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

	public String getProduceRouteGid() {
		return produceRouteGid;
	}

	public void setProduceRouteGid(String produceRouteGid) {
		this.produceRouteGid = produceRouteGid;
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

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getDispatchedNum() {
		return dispatchedNum;
	}

	public void setDispatchedNum(BigDecimal dispatchedNum) {
		this.dispatchedNum = dispatchedNum;
	}

	public String getPreGid() {
		return preGid;
	}

	public void setPreGid(String preGid) {
		this.preGid = preGid;
	}

	public String getNextGid() {
		return nextGid;
	}

	public void setNextGid(String nextGid) {
		this.nextGid = nextGid;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getReportOkNum() {
		return reportOkNum;
	}

	public void setReportOkNum(BigDecimal reportOkNum) {
		this.reportOkNum = reportOkNum;
	}

	public BigDecimal getReportNotOkNum() {
		return reportNotOkNum;
	}

	public void setReportNotOkNum(BigDecimal reportNotOkNum) {
		this.reportNotOkNum = reportNotOkNum;
	}

	public BigDecimal getReportProblemNum() {
		return reportProblemNum;
	}

	public void setReportProblemNum(BigDecimal reportProblemNum) {
		this.reportProblemNum = reportProblemNum;
	}

	public BigDecimal getCheckOkNum() {
		return checkOkNum;
	}

	public void setCheckOkNum(BigDecimal checkOkNum) {
		this.checkOkNum = checkOkNum;
	}

	public BigDecimal getCheckNotOkNum() {
		return checkNotOkNum;
	}

	public void setCheckNotOkNum(BigDecimal checkNotOkNum) {
		this.checkNotOkNum = checkNotOkNum;
	}

	public BigDecimal getPassRate() {
		return passRate;
	}

	public void setPassRate(BigDecimal passRate) {
		this.passRate = passRate;
	}

	public Integer getDispatchingType() {
		return dispatchingType;
	}

	public void setDispatchingType(Integer dispatchingType) {
		this.dispatchingType = dispatchingType;
	}

	public String getWorkCenterId() {
		return workCenterId;
	}

	public void setWorkCenterId(String workCenterId) {
		this.workCenterId = workCenterId;
	}

	public List<MesWmProduceProcessRouteCPre> getPreList() {
		return preList;
	}

	public void setPreList(List<MesWmProduceProcessRouteCPre> preList) {
		this.preList = preList;
	}

	public List<MesWmProduceProcessroutecGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<MesWmProduceProcessroutecGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public List<MesWmProduceProcessRouteCDispatching> getDispatchingList() {
		return dispatchingList;
	}

	public void setDispatchingList(
			List<MesWmProduceProcessRouteCDispatching> dispatchingList) {
		this.dispatchingList = dispatchingList;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<MesWmProduceProcessRouteCEquipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<MesWmProduceProcessRouteCEquipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public String getStockGoodsId() {
		return stockGoodsId;
	}

	public void setStockGoodsId(String stockGoodsId) {
		this.stockGoodsId = stockGoodsId;
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

	public List<MesWmProduceProcessRouteCMould> getMouldList() {
		return mouldList;
	}

	public void setMouldList(List<MesWmProduceProcessRouteCMould> mouldList) {
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
