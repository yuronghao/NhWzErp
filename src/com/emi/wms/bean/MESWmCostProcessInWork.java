package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工序在制情况
 */
@EmiTable(name = "MES_WM_CostProcessInWork")
public class MESWmCostProcessInWork implements Serializable{
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
	
	@EmiColumn(name = "currentInWorkNum")
	private BigDecimal currentInWorkNum;
	
	@EmiColumn(name = "ctime")
	private Timestamp ctime;
	
	@EmiColumn(name = "cfree1")
	private String cfree1;
	

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
	
	
	@EmiColumn(name = "deptGid")
	private String deptGid;
	
	@EmiColumn(name = "deptCode")
	private String deptCode;
	
	private String deptName;


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
	
	
	
	
	@EmiColumn(name = "icostf001")
	private BigDecimal icostf001;
	
	@EmiColumn(name = "icostf002")
	private BigDecimal icostf002;
	
	@EmiColumn(name = "icostf003")
	private BigDecimal icostf003;
	
	@EmiColumn(name = "icostf004")
	private BigDecimal icostf004;
	
	@EmiColumn(name = "icostf005")
	private BigDecimal icostf005;
	
	@EmiColumn(name = "icostf006")
	private BigDecimal icostf006;
	
	@EmiColumn(name = "icostf007")
	private BigDecimal icostf007;
	
	@EmiColumn(name = "icostf008")
	private BigDecimal icostf008;
	
	@EmiColumn(name = "icostf009")
	private BigDecimal icostf009;
	
	@EmiColumn(name = "icostf010")
	private BigDecimal icostf010;
	
	
	
	

	public BigDecimal getIcostf001() {
		return icostf001;
	}

	public void setIcostf001(BigDecimal icostf001) {
		this.icostf001 = icostf001;
	}

	public BigDecimal getIcostf002() {
		return icostf002;
	}

	public void setIcostf002(BigDecimal icostf002) {
		this.icostf002 = icostf002;
	}

	public BigDecimal getIcostf003() {
		return icostf003;
	}

	public void setIcostf003(BigDecimal icostf003) {
		this.icostf003 = icostf003;
	}

	public BigDecimal getIcostf004() {
		return icostf004;
	}

	public void setIcostf004(BigDecimal icostf004) {
		this.icostf004 = icostf004;
	}

	public BigDecimal getIcostf005() {
		return icostf005;
	}

	public void setIcostf005(BigDecimal icostf005) {
		this.icostf005 = icostf005;
	}

	public BigDecimal getIcostf006() {
		return icostf006;
	}

	public void setIcostf006(BigDecimal icostf006) {
		this.icostf006 = icostf006;
	}

	public BigDecimal getIcostf007() {
		return icostf007;
	}

	public void setIcostf007(BigDecimal icostf007) {
		this.icostf007 = icostf007;
	}

	public BigDecimal getIcostf008() {
		return icostf008;
	}

	public void setIcostf008(BigDecimal icostf008) {
		this.icostf008 = icostf008;
	}

	public BigDecimal getIcostf009() {
		return icostf009;
	}

	public void setIcostf009(BigDecimal icostf009) {
		this.icostf009 = icostf009;
	}

	public BigDecimal getIcostf010() {
		return icostf010;
	}

	public void setIcostf010(BigDecimal icostf010) {
		this.icostf010 = icostf010;
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

	public BigDecimal getCurrentInWorkNum() {
		return currentInWorkNum;
	}

	public void setCurrentInWorkNum(BigDecimal currentInWorkNum) {
		this.currentInWorkNum = currentInWorkNum;
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

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

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
	
	
	
	
	
	
	
	
	
	
}