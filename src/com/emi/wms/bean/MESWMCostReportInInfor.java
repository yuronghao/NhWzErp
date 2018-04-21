package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 入库情况表
 */
@EmiTable(name = "MES_WM_CostReportInInfor")
public class MESWMCostReportInInfor implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "produceOrderCode")
	private String produceOrderCode;
	
	@EmiColumn(name = "goodsGid")
	private String goodsGid;
	
	@EmiColumn(name = "rptGid")
	private String rptGid;
	
	@EmiColumn(name = "discGid")
	private String discGid;
	
	@EmiColumn(name = "reportOkNum")
	private BigDecimal reportOkNum;
	
	@EmiColumn(name = "reportNotOkNum")
	private BigDecimal reportNotOkNum;
	
	@EmiColumn(name = "reportProblemNum")
	private BigDecimal reportProblemNum;
	
	@EmiColumn(name = "produceInNum")
	private BigDecimal produceInNum;
	
	@EmiColumn(name = "ctime")
	private Timestamp ctime;
	
	@EmiColumn(name = "cfree1")
	private String cfree1;
	
	@EmiColumn(name = "cwhCode")
	private String cwhCode;

	@EmiColumn(name = "goodsCode")
	private String goodsCode;
	
	private String goodsName;//存货名称
	private String goodsStandard;//规格
	
	@EmiColumn(name = "opname")
	private String opname;
	
	@EmiColumn(name = "billdate")
	private Timestamp billdate;
	
	@EmiColumn(name = "iyear")
	private Integer iyear;
	
	@EmiColumn(name = "imonth")
	private Integer imonth;
	
	@EmiColumn(name = "cwhGid")
	private String cwhGid;
	
	@EmiColumn(name = "state")
	private Integer state;
	
	private Integer bySelfFlag;//自制件标志
	
	@EmiColumn(name = "deptGid")
	private String deptGid;
	
	@EmiColumn(name = "deptCode")
	private String deptCode;
	
	private String deptName;

	@EmiColumn(name = "mainLowOrderCode")
	private Integer mainLowOrderCode;
	
	@EmiColumn(name = "secondLowOrderCode")	
	private Integer secondLowOrderCode;
	
	@EmiColumn(name = "f001")	
	private BigDecimal f001;
	
	@EmiColumn(name = "f002")	
	private BigDecimal f002;
	
	@EmiColumn(name = "f003")	
	private BigDecimal f003;
	
	@EmiColumn(name = "f004")	
	private BigDecimal f004;
	
	@EmiColumn(name = "f005")	
	private BigDecimal f005;
	
	@EmiColumn(name = "f006")	
	private BigDecimal f006;
	
	@EmiColumn(name = "f007")	
	private BigDecimal f007;
	
	@EmiColumn(name = "f008")	
	private BigDecimal f008;
	
	@EmiColumn(name = "f009")	
	private BigDecimal f009;
	
	@EmiColumn(name = "f010")	
	private BigDecimal f010;
	
	
	@EmiColumn(name = "pricePref001")	
	private BigDecimal pricePref001;
	
	@EmiColumn(name = "pricePref002")	
	private BigDecimal pricePref002;
	
	@EmiColumn(name = "pricePref003")	
	private BigDecimal pricePref003;

	@EmiColumn(name = "pricePref004")	
	private BigDecimal pricePref004;
	
	@EmiColumn(name = "pricePref005")	
	private BigDecimal pricePref005;
	
	@EmiColumn(name = "pricePref006")	
	private BigDecimal pricePref006;
	
	@EmiColumn(name = "pricePref007")	
	private BigDecimal pricePref007;
	
	@EmiColumn(name = "pricePref008")	
	private BigDecimal pricePref008;
	
	@EmiColumn(name = "pricePref009")	
	private BigDecimal pricePref009;
	
	@EmiColumn(name = "pricePref010")	
	private BigDecimal pricePref010;
	
	
	@EmiColumn(name = "iunitCostf001")	
	private BigDecimal iunitCostf001;
	
	@EmiColumn(name = "iunitCostf002")	
	private BigDecimal iunitCostf002;
	
	@EmiColumn(name = "iunitCostf003")	
	private BigDecimal iunitCostf003;
	
	@EmiColumn(name = "iunitCostf004")	
	private BigDecimal iunitCostf004;
	
	@EmiColumn(name = "iunitCostf005")	
	private BigDecimal iunitCostf005;
	
	@EmiColumn(name = "iunitCostf006")	
	private BigDecimal iunitCostf006;
	
	@EmiColumn(name = "iunitCostf007")	
	private BigDecimal iunitCostf007;
	
	@EmiColumn(name = "iunitCostf008")	
	private BigDecimal iunitCostf008;
	
	@EmiColumn(name = "iunitCostf009")	
	private BigDecimal iunitCostf009;
	
	@EmiColumn(name = "iunitCostf010")	
	private BigDecimal iunitCostf010;
	
	
	
	
	
	public BigDecimal getIunitCostf001() {
		return iunitCostf001;
	}

	public void setIunitCostf001(BigDecimal iunitCostf001) {
		this.iunitCostf001 = iunitCostf001;
	}

	public BigDecimal getIunitCostf002() {
		return iunitCostf002;
	}

	public void setIunitCostf002(BigDecimal iunitCostf002) {
		this.iunitCostf002 = iunitCostf002;
	}

	public BigDecimal getIunitCostf003() {
		return iunitCostf003;
	}

	public void setIunitCostf003(BigDecimal iunitCostf003) {
		this.iunitCostf003 = iunitCostf003;
	}

	public BigDecimal getIunitCostf004() {
		return iunitCostf004;
	}

	public void setIunitCostf004(BigDecimal iunitCostf004) {
		this.iunitCostf004 = iunitCostf004;
	}

	public BigDecimal getIunitCostf005() {
		return iunitCostf005;
	}

	public void setIunitCostf005(BigDecimal iunitCostf005) {
		this.iunitCostf005 = iunitCostf005;
	}

	public BigDecimal getIunitCostf006() {
		return iunitCostf006;
	}

	public void setIunitCostf006(BigDecimal iunitCostf006) {
		this.iunitCostf006 = iunitCostf006;
	}

	public BigDecimal getIunitCostf007() {
		return iunitCostf007;
	}

	public void setIunitCostf007(BigDecimal iunitCostf007) {
		this.iunitCostf007 = iunitCostf007;
	}

	public BigDecimal getIunitCostf008() {
		return iunitCostf008;
	}

	public void setIunitCostf008(BigDecimal iunitCostf008) {
		this.iunitCostf008 = iunitCostf008;
	}

	public BigDecimal getIunitCostf009() {
		return iunitCostf009;
	}

	public void setIunitCostf009(BigDecimal iunitCostf009) {
		this.iunitCostf009 = iunitCostf009;
	}

	public BigDecimal getIunitCostf010() {
		return iunitCostf010;
	}

	public void setIunitCostf010(BigDecimal iunitCostf010) {
		this.iunitCostf010 = iunitCostf010;
	}

	public BigDecimal getPricePref001() {
		return pricePref001;
	}

	public void setPricePref001(BigDecimal pricePref001) {
		this.pricePref001 = pricePref001;
	}

	public BigDecimal getPricePref002() {
		return pricePref002;
	}

	public void setPricePref002(BigDecimal pricePref002) {
		this.pricePref002 = pricePref002;
	}

	public BigDecimal getPricePref003() {
		return pricePref003;
	}

	public void setPricePref003(BigDecimal pricePref003) {
		this.pricePref003 = pricePref003;
	}

	public BigDecimal getPricePref004() {
		return pricePref004;
	}

	public void setPricePref004(BigDecimal pricePref004) {
		this.pricePref004 = pricePref004;
	}

	public BigDecimal getPricePref005() {
		return pricePref005;
	}

	public void setPricePref005(BigDecimal pricePref005) {
		this.pricePref005 = pricePref005;
	}

	public BigDecimal getPricePref006() {
		return pricePref006;
	}

	public void setPricePref006(BigDecimal pricePref006) {
		this.pricePref006 = pricePref006;
	}

	public BigDecimal getPricePref007() {
		return pricePref007;
	}

	public void setPricePref007(BigDecimal pricePref007) {
		this.pricePref007 = pricePref007;
	}

	public BigDecimal getPricePref008() {
		return pricePref008;
	}

	public void setPricePref008(BigDecimal pricePref008) {
		this.pricePref008 = pricePref008;
	}

	public BigDecimal getPricePref009() {
		return pricePref009;
	}

	public void setPricePref009(BigDecimal pricePref009) {
		this.pricePref009 = pricePref009;
	}

	public BigDecimal getPricePref010() {
		return pricePref010;
	}

	public void setPricePref010(BigDecimal pricePref010) {
		this.pricePref010 = pricePref010;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOpname(){
		return opname;
	}
	
	public void setOpname(String opname){
		this.opname=opname;
	}
	
	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String goodsName){
		this.goodsName=goodsName;
	}
	
	
	public String getGoodsStandard(){
		return goodsStandard;
	}
	
	public void setGoodsStandard(String goodsStandard){
		this.goodsStandard=goodsStandard;
	}
	
	
	public Integer getMainLowOrderCode() {
		return mainLowOrderCode;
	}

	public void setMainLowOrderCode(Integer mainLowOrderCode) {
		this.mainLowOrderCode = mainLowOrderCode;
	}

	public Integer getSecondLowOrderCode() {
		return secondLowOrderCode;
	}

	public void setSecondLowOrderCode(Integer secondLowOrderCode) {
		this.secondLowOrderCode = secondLowOrderCode;
	}

	public BigDecimal getF001() {
		return f001;
	}

	public void setF001(BigDecimal f001) {
		this.f001 = f001;
	}

	public BigDecimal getF002() {
		return f002;
	}

	public void setF002(BigDecimal f002) {
		this.f002 = f002;
	}

	public BigDecimal getF003() {
		return f003;
	}

	public void setF003(BigDecimal f003) {
		this.f003 = f003;
	}

	public BigDecimal getF004() {
		return f004;
	}

	public void setF004(BigDecimal f004) {
		this.f004 = f004;
	}

	public BigDecimal getF005() {
		return f005;
	}

	public void setF005(BigDecimal f005) {
		this.f005 = f005;
	}

	public BigDecimal getF006() {
		return f006;
	}

	public void setF006(BigDecimal f006) {
		this.f006 = f006;
	}

	public BigDecimal getF007() {
		return f007;
	}

	public void setF007(BigDecimal f007) {
		this.f007 = f007;
	}

	public BigDecimal getF008() {
		return f008;
	}

	public void setF008(BigDecimal f008) {
		this.f008 = f008;
	}

	public BigDecimal getF009() {
		return f009;
	}

	public void setF009(BigDecimal f009) {
		this.f009 = f009;
	}

	public BigDecimal getF010() {
		return f010;
	}

	public void setF010(BigDecimal f010) {
		this.f010 = f010;
	}

	public Integer getBySelfFlag() {
		return bySelfFlag;
	}

	public void setBySelfFlag(Integer bySelfFlag) {
		this.bySelfFlag = bySelfFlag;
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

	public String getProduceOrderCode() {
		return produceOrderCode;
	}

	public void setProduceOrderCode(String produceOrderCode) {
		this.produceOrderCode = produceOrderCode;
	}

	public String getGoodsGid() {
		return goodsGid;
	}

	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
	}

	public String getRptGid() {
		return rptGid;
	}

	public void setRptGid(String rptGid) {
		this.rptGid = rptGid;
	}

	public String getDiscGid() {
		return discGid;
	}

	public void setDiscGid(String discGid) {
		this.discGid = discGid;
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

	public BigDecimal getProduceInNum() {
		return produceInNum;
	}

	public void setProduceInNum(BigDecimal produceInNum) {
		this.produceInNum = produceInNum;
	}

	public Timestamp getCtime() {
		return ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

	public String getCfree1() {
		return cfree1;
	}

	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}

	public String getCwhCode() {
		return cwhCode;
	}

	public void setCwhCode(String cwhCode) {
		this.cwhCode = cwhCode;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public Timestamp getBilldate() {
		return billdate;
	}

	public void setBilldate(Timestamp billdate) {
		this.billdate = billdate;
	}

	public Integer getIyear() {
		return iyear;
	}

	public void setIyear(Integer iyear) {
		this.iyear = iyear;
	}

	public Integer getImonth() {
		return imonth;
	}

	public void setImonth(Integer imonth) {
		this.imonth = imonth;
	}

	public String getCwhGid() {
		return cwhGid;
	}

	public void setCwhGid(String cwhGid) {
		this.cwhGid = cwhGid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDeptGid() {
		return deptGid;
	}

	public void setDeptGid(String deptGid) {
		this.deptGid = deptGid;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	
	
	
	
	
}