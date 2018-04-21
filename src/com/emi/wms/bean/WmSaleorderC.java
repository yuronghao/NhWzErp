package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_SaleOrder_C")
public class WmSaleorderC implements Serializable{
	private static final long serialVersionUID = -5986365774060451359L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	//gid 
	@EmiColumn(name = "gid", ID = true)
    private String gid;

	//备注 
	@EmiColumn(name="notes" )
    private String notes;

	//销售订单主表uid 
	@EmiColumn(name="saleOrderUid" )
    private String saleOrderUid;

	//物品uid 
	@EmiColumn(name="goodsUid" )
    private String goodsUid;

	//数量 
	@EmiColumn(name="number" )
    private BigDecimal number;

	//报价（本币） 
	@EmiColumn(name="localprice" )
    private BigDecimal localprice;

	//原币含税单价 
	@EmiColumn(name="originaltaxprice" )
    private BigDecimal originaltaxprice;

	//原币含税金额 
	@EmiColumn(name="originaltaxmoney" )
    private BigDecimal originaltaxmoney;

	//原币不含税单价 
	@EmiColumn(name="originalnotaxprice" )
    private BigDecimal originalnotaxprice;

	//原币不含税金额 
	@EmiColumn(name="originalnotaxmoney" )
    private BigDecimal originalnotaxmoney;

	//原币税额 
	@EmiColumn(name="originaltax" )
    private BigDecimal originaltax;

	//本币含税单价 
	@EmiColumn(name="localtaxprice" )
    private BigDecimal localtaxprice;

	//本币含税金额 
	@EmiColumn(name="localtaxmoney" )
    private BigDecimal localtaxmoney;

	//本币不含税单价 
	@EmiColumn(name="localnotaxprice" )
    private BigDecimal localnotaxprice;

	//本币不含税金额 
	@EmiColumn(name="localnotaxmoney" )
    private BigDecimal localnotaxmoney;

	//本币税额 
	@EmiColumn(name="localtax" )
    private BigDecimal localtax;

	//原币折扣额 
	@EmiColumn(name="originaldeduction" )
    private BigDecimal originaldeduction;

	//本币折扣额 
	@EmiColumn(name="localdeduction" )
    private BigDecimal localdeduction;

	//扣率 
	@EmiColumn(name="discount" )
    private BigDecimal discount;

	//已发货数量 
	@EmiColumn(name="sendNumber" )
    private BigDecimal sendNumber;

	//已出库数量 
	@EmiColumn(name="putoutNumber" )
    private BigDecimal putoutNumber;

	//计划发货日期 
	@EmiColumn(name="planDG" )
    private Date planDG;

	//
	@EmiColumn(name="amount" )
    private BigDecimal amount;

	//
	@EmiColumn(name="putoutAssistNumber" )
    private BigDecimal putoutAssistNumber;
	
	//辅计量
	@EmiColumn(name="assistNumber" )
    private BigDecimal assistNumber;

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

	public String getSaleOrderUid() {
		return saleOrderUid;
	}

	public void setSaleOrderUid(String saleOrderUid) {
		this.saleOrderUid = saleOrderUid;
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

	public BigDecimal getLocalprice() {
		return localprice;
	}

	public void setLocalprice(BigDecimal localprice) {
		this.localprice = localprice;
	}

	public BigDecimal getOriginaltaxprice() {
		return originaltaxprice;
	}

	public void setOriginaltaxprice(BigDecimal originaltaxprice) {
		this.originaltaxprice = originaltaxprice;
	}

	public BigDecimal getOriginaltaxmoney() {
		return originaltaxmoney;
	}

	public void setOriginaltaxmoney(BigDecimal originaltaxmoney) {
		this.originaltaxmoney = originaltaxmoney;
	}

	public BigDecimal getOriginalnotaxprice() {
		return originalnotaxprice;
	}

	public void setOriginalnotaxprice(BigDecimal originalnotaxprice) {
		this.originalnotaxprice = originalnotaxprice;
	}

	public BigDecimal getOriginalnotaxmoney() {
		return originalnotaxmoney;
	}

	public void setOriginalnotaxmoney(BigDecimal originalnotaxmoney) {
		this.originalnotaxmoney = originalnotaxmoney;
	}

	public BigDecimal getOriginaltax() {
		return originaltax;
	}

	public void setOriginaltax(BigDecimal originaltax) {
		this.originaltax = originaltax;
	}

	public BigDecimal getLocaltaxprice() {
		return localtaxprice;
	}

	public void setLocaltaxprice(BigDecimal localtaxprice) {
		this.localtaxprice = localtaxprice;
	}

	public BigDecimal getLocaltaxmoney() {
		return localtaxmoney;
	}

	public void setLocaltaxmoney(BigDecimal localtaxmoney) {
		this.localtaxmoney = localtaxmoney;
	}

	public BigDecimal getLocalnotaxprice() {
		return localnotaxprice;
	}

	public void setLocalnotaxprice(BigDecimal localnotaxprice) {
		this.localnotaxprice = localnotaxprice;
	}

	public BigDecimal getLocalnotaxmoney() {
		return localnotaxmoney;
	}

	public void setLocalnotaxmoney(BigDecimal localnotaxmoney) {
		this.localnotaxmoney = localnotaxmoney;
	}

	public BigDecimal getLocaltax() {
		return localtax;
	}

	public void setLocaltax(BigDecimal localtax) {
		this.localtax = localtax;
	}

	public BigDecimal getOriginaldeduction() {
		return originaldeduction;
	}

	public void setOriginaldeduction(BigDecimal originaldeduction) {
		this.originaldeduction = originaldeduction;
	}

	public BigDecimal getLocaldeduction() {
		return localdeduction;
	}

	public void setLocaldeduction(BigDecimal localdeduction) {
		this.localdeduction = localdeduction;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(BigDecimal sendNumber) {
		this.sendNumber = sendNumber;
	}

	public BigDecimal getPutoutNumber() {
		return putoutNumber;
	}

	public void setPutoutNumber(BigDecimal putoutNumber) {
		this.putoutNumber = putoutNumber;
	}

	public Date getPlanDG() {
		return planDG;
	}

	public void setPlanDG(Date planDG) {
		this.planDG = planDG;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPutoutAssistNumber() {
		return putoutAssistNumber;
	}

	public void setPutoutAssistNumber(BigDecimal putoutAssistNumber) {
		this.putoutAssistNumber = putoutAssistNumber;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}
   
}