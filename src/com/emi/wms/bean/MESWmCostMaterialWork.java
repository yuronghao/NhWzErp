package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 材料在制情况
 */
@EmiTable(name = "MES_WM_CostMaterialWork")
public class MESWmCostMaterialWork implements Serializable{
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
	
	
	@EmiColumn(name = "ctime")
	private Timestamp ctime;
	
	@EmiColumn(name = "cfree1")
	private String cfree1;
	
	private String goodsCode;//存货编码
	private String goodsName;//存货名称
	private String goodsStandard;//规格
	
	@EmiColumn(name = "productGoodsGid")
	private String productGoodsGid;
	
	@EmiColumn(name = "productGoodsCfree1")
	private String productGoodsCfree1;
	
	private String productGoodsCode;//产品编码
	private String productGoodsName;//产品名称
	private String productGoodsStandard;//产品规格
	
	
	@EmiColumn(name = "iyear")
	private Integer iyear;
	
	@EmiColumn(name = "imonth")
	private Integer imonth;
	
	
	
	@EmiColumn(name = "deptGid")
	private String deptGid;
	
	@EmiColumn(name = "deptCode")
	private String deptCode;
	
	private String deptName;


	@EmiColumn(name = "currentInWorkNum")	
	private BigDecimal currentInWorkNum;
	
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
	
	
	

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getProductGoodsCode() {
		return productGoodsCode;
	}

	public void setProductGoodsCode(String productGoodsCode) {
		this.productGoodsCode = productGoodsCode;
	}

	public String getProductGoodsName() {
		return productGoodsName;
	}

	public void setProductGoodsName(String productGoodsName) {
		this.productGoodsName = productGoodsName;
	}

	public String getProductGoodsStandard() {
		return productGoodsStandard;
	}

	public void setProductGoodsStandard(String productGoodsStandard) {
		this.productGoodsStandard = productGoodsStandard;
	}

	public String getProductGoodsGid() {
		return productGoodsGid;
	}

	public void setProductGoodsGid(String productGoodsGid) {
		this.productGoodsGid = productGoodsGid;
	}

	public String getProductGoodsCfree1() {
		return productGoodsCfree1;
	}

	public void setProductGoodsCfree1(String productGoodsCfree1) {
		this.productGoodsCfree1 = productGoodsCfree1;
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

	public BigDecimal getCurrentInWorkNum() {
		return currentInWorkNum;
	}

	public void setCurrentInWorkNum(BigDecimal currentInWorkNum) {
		this.currentInWorkNum = currentInWorkNum;
	}

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


	
	
	
	
	
	
	
	
}