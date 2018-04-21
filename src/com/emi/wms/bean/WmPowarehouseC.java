package com.emi.wms.bean;

import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_PoWarehouse_C")
public class WmPowarehouseC {
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	@EmiColumn(name = "notes")
    private String notes;
	@EmiColumn(name = "powhuid")
    private String powhuid;
	@EmiColumn(name = "materialuid")
    private String materialuid;
	@EmiColumn(name = "quantity")
    private BigDecimal quantity;
	
	@EmiColumn(name = "assistquantity")
	private BigDecimal assistquantity;
	
	@EmiColumn(name = "price")
    private BigDecimal price;
	@EmiColumn(name = "amount")
    private BigDecimal amount;
	@EmiColumn(name = "pocuid")
    private String pocuid;
	@EmiColumn(name = "poarrivalcuid")
    private String poarrivalcuid;
	@EmiColumn(name = "goodsallocationuid")
    private String goodsallocationuid;
	@EmiColumn(name = "batchcode")
    private String batchcode;
	
	@EmiColumn(name = "barcode")
	private String barcode;
	
	@EmiColumn(name="cfree1" )
	private String cfree1;
	
	@EmiColumn(name="cfree2" )
	private String cfree2;
	
	
	
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public BigDecimal getAssistquantity() {
		return assistquantity;
	}
	public void setAssistquantity(BigDecimal assistquantity) {
		this.assistquantity = assistquantity;
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
	public String getPowhuid() {
		return powhuid;
	}
	public void setPowhuid(String powhuid) {
		this.powhuid = powhuid;
	}
	public String getMaterialuid() {
		return materialuid;
	}
	public void setMaterialuid(String materialuid) {
		this.materialuid = materialuid;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
	public String getPocuid() {
		return pocuid;
	}
	public void setPocuid(String pocuid) {
		this.pocuid = pocuid;
	}
	public String getPoarrivalcuid() {
		return poarrivalcuid;
	}
	public void setPoarrivalcuid(String poarrivalcuid) {
		this.poarrivalcuid = poarrivalcuid;
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

    
}