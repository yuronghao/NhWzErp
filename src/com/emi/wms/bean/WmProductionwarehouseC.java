package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_ProductionWarehouse_C")
public class WmProductionwarehouseC implements Serializable{
	
	private static final long serialVersionUID = -590550957374808262L;

	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "notes")
    private String notes;

	@EmiColumn(name = "proUid")
    private String proUid;

	@EmiColumn(name = "goodsUid")
    private String goodsUid;

	@EmiColumn(name = "number")
    private BigDecimal number;

	@EmiColumn(name = "price")
    private BigDecimal price;

	@EmiColumn(name = "amount")
    private BigDecimal amount;

	@EmiColumn(name = "batch")
    private String batch;

	@EmiColumn(name = "goodsallocationuid")
    private String goodsallocationuid;
	
	@EmiColumn(name = "assistNumber")
	private BigDecimal assistNumber;
	
	@EmiColumn(name = "cfree1")
	private String cfree1;
	
	@EmiColumn(name = "cfree2")
	private String cfree2;
	
	@EmiColumn(name = "produceProcessRouteCGid")
	private String produceProcessRouteCGid;
	
	@EmiColumn(name = "define22")
	private String define22;
	
	@EmiColumn(name = "define23")
	private String define23;
	
	@EmiColumn(name = "define24")
	private String define24;
	

	
	@EmiColumn(name = "barcode")
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

	public String getProUid() {
		return proUid;
	}

	public void setProUid(String proUid) {
		this.proUid = proUid;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getGoodsallocationuid() {
		return goodsallocationuid;
	}

	public void setGoodsallocationuid(String goodsallocationuid) {
		this.goodsallocationuid = goodsallocationuid;
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

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}

	public String getProduceProcessRouteCGid() {
		return produceProcessRouteCGid;
	}

	public void setProduceProcessRouteCGid(String produceProcessRouteCGid) {
		this.produceProcessRouteCGid = produceProcessRouteCGid;
	}
	
	

	
  
}