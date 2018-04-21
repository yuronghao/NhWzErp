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
@EmiTable(name = "MES_WM_CostDirectMaterialWork2")
public class MESWmCostDirectMaterialWork2 implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	
	@EmiColumn(name = "goodsGid")
	private String goodsGid;
	
	@EmiColumn(name = "cfree1")
	private String cfree1;
	
	private String goodsCode;//存货编码
	private String goodsName;//存货名称
	private String goodsStandard;//规格
	
	
	@EmiColumn(name = "productGoodsCode")
	private String productGoodsCode;//产品编码
	
	@EmiColumn(name = "productGoodsCfree1")
	private String productGoodsCfree1;
	
	@EmiColumn(name = "iyear")
	private Integer iyear;
	
	@EmiColumn(name = "imonth")
	private Integer imonth;
	
	
	@EmiColumn(name = "deptCode")
	private String deptCode;
	
	private String deptName;


	@EmiColumn(name = "currentInWorkNum")	
	private BigDecimal currentInWorkNum;
	
	@EmiColumn(name = "currentInWorkCost")	
	private BigDecimal currentInWorkCost;
	
	@EmiColumn(name = "productGoodsNum")	
	private BigDecimal productGoodsNum;
	
	
	@EmiColumn(name = "worktype")
	private Integer worktype;
	

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

	public String getProductGoodsCode() {
		return productGoodsCode;
	}

	public void setProductGoodsCode(String productGoodsCode) {
		this.productGoodsCode = productGoodsCode;
	}

	public String getProductGoodsCfree1() {
		return productGoodsCfree1;
	}

	public void setProductGoodsCfree1(String productGoodsCfree1) {
		this.productGoodsCfree1 = productGoodsCfree1;
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

	public BigDecimal getCurrentInWorkCost() {
		return currentInWorkCost;
	}

	public void setCurrentInWorkCost(BigDecimal currentInWorkCost) {
		this.currentInWorkCost = currentInWorkCost;
	}

	public BigDecimal getProductGoodsNum() {
		return productGoodsNum;
	}

	public void setProductGoodsNum(BigDecimal productGoodsNum) {
		this.productGoodsNum = productGoodsNum;
	}

	public Integer getWorktype() {
		return worktype;
	}

	public void setWorktype(Integer worktype) {
		this.worktype = worktype;
	}
	
	
	
	



	
	
	
	
	
	
	
	
}