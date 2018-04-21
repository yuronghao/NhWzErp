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
@EmiTable(name = "MES_WM_CostGoodsBalance")
public class MESWMCostGoodsBalance implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	
	@EmiColumn(name = "goodsGid")
	private String goodsGid;
	
	@EmiColumn(name = "goodsCode")
	private String goodsCode;
	
	private String goodsName;//存货名称
	private String goodsStandard;//规格
	
	@EmiColumn(name = "cfree1")
	private String cfree1;
	
	
	@EmiColumn(name = "cwhGid")
	private String cwhGid;
	
	@EmiColumn(name = "cwhCode")
	private String cwhCode;
	

	private String cwhName;
	
	@EmiColumn(name = "iunitCost")
	private BigDecimal iunitCost;
	
	@EmiColumn(name = "number")
	private BigDecimal number;
	
	@EmiColumn(name = "iprice")
	private BigDecimal iprice;
	


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
	
	

	public String getCwhName() {
		return cwhName;
	}

	public void setCwhName(String cwhName) {
		this.cwhName = cwhName;
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

	public String getGoodsGid() {
		return goodsGid;
	}

	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
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

	public String getCfree1() {
		return cfree1;
	}

	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}

	public String getCwhGid() {
		return cwhGid;
	}

	public void setCwhGid(String cwhGid) {
		this.cwhGid = cwhGid;
	}

	public String getCwhCode() {
		return cwhCode;
	}

	public void setCwhCode(String cwhCode) {
		this.cwhCode = cwhCode;
	}

	public BigDecimal getIunitCost() {
		return iunitCost;
	}

	public void setIunitCost(BigDecimal iunitCost) {
		this.iunitCost = iunitCost;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getIprice() {
		return iprice;
	}

	public void setIprice(BigDecimal iprice) {
		this.iprice = iprice;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}