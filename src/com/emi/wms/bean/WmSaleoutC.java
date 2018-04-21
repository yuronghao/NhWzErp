package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_SaleOut_C")
public class WmSaleoutC implements Serializable{

	private static final long serialVersionUID = -3504528745519044885L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	//gid 
	
	@EmiColumn(name="gid" ,ID=true)
    private String gid;

	//备注 
	@EmiColumn(name="notes" )
    private String notes;

	//销售出库主表uid 
	@EmiColumn(name="saleoutuid" )
    private String saleoutuid;

	//物品uid 
	@EmiColumn(name="goodsuid" )
    private String goodsuid;

	//数量 
	@EmiColumn(name="number" )
    private BigDecimal number;
	
	//辅助数量
	@EmiColumn(name="assistNumber" )
	private BigDecimal assistNumber;

	//单价 
	@EmiColumn(name="unitprice" )
    private BigDecimal unitprice;

	//金额 
	@EmiColumn(name="totalprice" )
    private BigDecimal totalprice;

	//销售订单子表uid 
	@EmiColumn(name="saleordercuid" )
    private String saleordercuid;

	//销售发货单子表uid 
	@EmiColumn(name="salesendcuid" )
    private String salesendcuid;

	//货位uid 
	@EmiColumn(name="goodsallocationuid" )
    private String goodsallocationuid;

	//批次 
	@EmiColumn(name="batchcode" )
    private String batchcode;

	//箱码 
	@EmiColumn(name="boxcode" )
    private String boxcode;

	//箱子条码 
	@EmiColumn(name="boxbarcode" )
    private String boxbarcode;
	
	@EmiColumn(name="cfree1" )
	private String cfree1;
	
	@EmiColumn(name="cfree2" )
	private String cfree2;
	
	@EmiColumn(name="define22" )
	private String define22;
	
	@EmiColumn(name="define23" )
	private String define23;
	
	@EmiColumn(name="define24" )
	private String define24;
	
	
	@EmiColumn(name="barcode" )
	private String barcode;
	
	
	
	public String getDefine22() {
		return define22;
	}

	public void setDefine22(String define22) {
		this.define22 = define22;
	}

	public String getDefine23() {
		return define23;
	}

	public void setDefine23(String define23) {
		this.define23 = define23;
	}

	public String getDefine24() {
		return define24;
	}

	public void setDefine24(String define24) {
		this.define24 = define24;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSaleoutuid() {
		return saleoutuid;
	}

	public void setSaleoutuid(String saleoutuid) {
		this.saleoutuid = saleoutuid;
	}

	public String getGoodsuid() {
		return goodsuid;
	}

	public void setGoodsuid(String goodsuid) {
		this.goodsuid = goodsuid;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}

	public BigDecimal getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getSaleordercuid() {
		return saleordercuid;
	}

	public void setSaleordercuid(String saleordercuid) {
		this.saleordercuid = saleordercuid;
	}

	public String getSalesendcuid() {
		return salesendcuid;
	}

	public void setSalesendcuid(String salesendcuid) {
		this.salesendcuid = salesendcuid;
	}

	public String getGoodsallocationuid() {
		return goodsallocationuid;
	}

	public void setGoodsallocationuid(String goodsallocationuid) {
		this.goodsallocationuid = goodsallocationuid;
	}

	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}

	public String getBoxcode() {
		return boxcode;
	}

	public void setBoxcode(String boxcode) {
		this.boxcode = boxcode;
	}

	public String getBoxbarcode() {
		return boxbarcode;
	}

	public void setBoxbarcode(String boxbarcode) {
		this.boxbarcode = boxbarcode;
	}

	public String getCfree1() {
		return cfree1;
	}

	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}

	public String getCfree2() {
		return cfree2;
	}

	public void setCfree2(String cfree2) {
		this.cfree2 = cfree2;
	}


  
}