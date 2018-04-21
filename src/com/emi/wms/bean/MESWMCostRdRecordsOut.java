package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 领料情况
 */
@EmiTable(name = "MES_WM_CostRdRecordsOut")
public class MESWMCostRdRecordsOut implements Serializable{
	private static final long serialVersionUID = 3414996533379115948L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "goodsGid")
	private String goodsGid;
	
	@EmiColumn(name = "inum")
	private BigDecimal inum;
	
	@EmiColumn(name = "iunitCost")
	private BigDecimal iunitCost;
	
	@EmiColumn(name = "iprice")
	private BigDecimal iprice;
	
	@EmiColumn(name = "imPoIds")
	private Integer imPoIds;
	
	@EmiColumn(name = "cbaccounter")
	private String cbaccounter;
	
	@EmiColumn(name = "ctime")
	private Timestamp ctime;
	
	@EmiColumn(name = "cfree1")
	private String cfree1;
	
	@EmiColumn(name = "cwhCode")
	private String cwhCode;
	
	private String cwhName;
	
	@EmiColumn(name = "goodsCode")
	private String goodsCode;
	
	@EmiColumn(name = "produceOrderCode")
	private String produceOrderCode;
	
	@EmiColumn(name = "billdate")
	private Timestamp billdate;
	
	@EmiColumn(name = "cdefine30")
	private String cdefine30;
	
	@EmiColumn(name = "iyear")
	private Integer iyear;
	
	@EmiColumn(name = "imonth")
	private Integer imonth;
	
	@EmiColumn(name = "cwhGid")
	private String cwhGid;
	
	@EmiColumn(name = "state")
	private Integer state;
	
	@EmiColumn(name = "routeCgid")
	private String routeCgid;
	
	@EmiColumn(name = "costSourceName")
	private String costSourceName;
	
	@EmiColumn(name = "productCfree1")
	private String productCfree1;
	
	@EmiColumn(name = "productCode")
	private String productCode;
	
	private String goodsName;//存货名称
	private String goodsStandard;//规格
	
	
	public String getCwhName() {
		return cwhName;
	}

	public void setCwhName(String cwhName) {
		this.cwhName = cwhName;
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

	public BigDecimal getInum() {
		return inum;
	}

	public void setInum(BigDecimal inum) {
		this.inum = inum;
	}

	public BigDecimal getIunitCost() {
		return iunitCost;
	}

	public void setIunitCost(BigDecimal iunitCost) {
		this.iunitCost = iunitCost;
	}

	public BigDecimal getIprice() {
		return iprice;
	}

	public void setIprice(BigDecimal iprice) {
		this.iprice = iprice;
	}

	public Integer getImPoIds() {
		return imPoIds;
	}

	public void setImPoIds(Integer imPoIds) {
		this.imPoIds = imPoIds;
	}

	public String getCbaccounter() {
		return cbaccounter;
	}

	public void setCbaccounter(String cbaccounter) {
		this.cbaccounter = cbaccounter;
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

	public String getProduceOrderCode() {
		return produceOrderCode;
	}

	public void setProduceOrderCode(String produceOrderCode) {
		this.produceOrderCode = produceOrderCode;
	}

	public Timestamp getBilldate() {
		return billdate;
	}

	public void setBilldate(Timestamp billdate) {
		this.billdate = billdate;
	}

	public String getCdefine30() {
		return cdefine30;
	}

	public void setCdefine30(String cdefine30) {
		this.cdefine30 = cdefine30;
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

	public String getRouteCgid() {
		return routeCgid;
	}

	public void setRouteCgid(String routeCgid) {
		this.routeCgid = routeCgid;
	}

	public String getCostSourceName() {
		return costSourceName;
	}

	public void setCostSourceName(String costSourceName) {
		this.costSourceName = costSourceName;
	}

	public String getProductCfree1() {
		return productCfree1;
	}

	public void setProductCfree1(String productCfree1) {
		this.productCfree1 = productCfree1;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}