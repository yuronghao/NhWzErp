package com.emi.wms.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_WM_ReportOrderC")
public class MesWmReportorderc {

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "rptgid")
    private String rptgid;//报工单主表id

	@EmiColumn(name = "discGid")
    private String discGid;//派工单子表gid


	@EmiColumn(name = "reportOkNum")
    private BigDecimal reportOkNum;//合格数量

	@EmiColumn(name = "reportNotOkNum")
    private BigDecimal reportNotOkNum;//不合格数量

	@EmiColumn(name = "reportProblemNum")
    private BigDecimal reportProblemNum;//问题数量

	@EmiColumn(name = "endTime")
    private Date endTime;//结束时间

	@EmiColumn(name = "personUnitVendorGid")
    private String personUnitVendorGid;//人\组\供应商
	
	@EmiColumn(name = "produceProcessRouteCGid")
	private String produceProcessRouteCGid;//订单工艺路线子表gid
	
	@EmiColumn(name = "deptGid")
	private String deptGid;//部门gid
	
	@EmiColumn(name = "notes")
	private String notes;//备注
	
	
	private String billCode;//订单编号
	private Date billDate;//订单日期
	private String goodsId;//物料id
	private String goodsCode;//存货编码
	private String goodsName;//存货名称
	private String goodsStandard;//规格
	private BigDecimal produceNumber;//下达生产量
	private Date startDate;//计划开工日期
	private Date endDate;//计划完工日期
	private String opGid;//工序id
	private String opName;//工序名称
	private Integer objectType;//派工对象类型
	private String dispatchingObjName;//人/组的名称
	
	
	private String stationGid;//设备gid
	private String equipmentCode;
	private String equipmentName;
	private String equipmentDeptName;
	
	private String mouldGid;//模具gid
	private String mouldCode;//
	private String mouldName;//
	private String mouldRatio;//模比
	
	private Integer workingTime;//白班 夜班
	
	
	public String getStationGid() {
		return stationGid;
	}

	public void setStationGid(String stationGid) {
		this.stationGid = stationGid;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentDeptName() {
		return equipmentDeptName;
	}

	public void setEquipmentDeptName(String equipmentDeptName) {
		this.equipmentDeptName = equipmentDeptName;
	}

	public String getMouldGid() {
		return mouldGid;
	}

	public void setMouldGid(String mouldGid) {
		this.mouldGid = mouldGid;
	}

	public String getMouldCode() {
		return mouldCode;
	}

	public void setMouldCode(String mouldCode) {
		this.mouldCode = mouldCode;
	}

	public String getMouldName() {
		return mouldName;
	}

	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}

	public String getMouldRatio() {
		return mouldRatio;
	}

	public void setMouldRatio(String mouldRatio) {
		this.mouldRatio = mouldRatio;
	}

	public Integer getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(Integer workingTime) {
		this.workingTime = workingTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDeptGid() {
		return deptGid;
	}

	public void setDeptGid(String deptGid) {
		this.deptGid = deptGid;
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

	public String getRptgid() {
		return rptgid;
	}

	public void setRptgid(String rptgid) {
		this.rptgid = rptgid;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPersonUnitVendorGid() {
		return personUnitVendorGid;
	}

	public void setPersonUnitVendorGid(String personUnitVendorGid) {
		this.personUnitVendorGid = personUnitVendorGid;
	}

	public String getDiscGid() {
		return discGid;
	}

	public void setDiscGid(String discGid) {
		this.discGid = discGid;
	}

	public String getProduceProcessRouteCGid() {
		return produceProcessRouteCGid;
	}

	public void setProduceProcessRouteCGid(String produceProcessRouteCGid) {
		this.produceProcessRouteCGid = produceProcessRouteCGid;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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

	public BigDecimal getProduceNumber() {
		return produceNumber;
	}

	public void setProduceNumber(BigDecimal produceNumber) {
		this.produceNumber = produceNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOpGid() {
		return opGid;
	}

	public void setOpGid(String opGid) {
		this.opGid = opGid;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public String getDispatchingObjName() {
		return dispatchingObjName;
	}

	public void setDispatchingObjName(String dispatchingObjName) {
		this.dispatchingObjName = dispatchingObjName;
	}


  

}