package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;


@EmiTable(name = "OM_Details")
public class OM_MODetails implements Serializable {
	
	private static final long serialVersionUID = 8843061577173325185L;

	@EmiColumn(name = "pk", increment=true)
	private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "goodsGid")
    private String goodsGid;

	@EmiColumn(name = "number")
    private BigDecimal number;
    
	@EmiColumn(name = "originalTaxPrice")
    private BigDecimal originalTaxPrice;//原币含税单价
    
	@EmiColumn(name = "originalTaxMoney")
    private BigDecimal originalTaxMoney;//原币含税金额
    
	@EmiColumn(name = "originalNotaxPrice")
    private BigDecimal originalNotaxPrice;//原币不含税单价
    
	@EmiColumn(name = "originalNotaxMoney")
    private BigDecimal originalNotaxMoney;//原币不含税金额
    
	@EmiColumn(name = "originalTax")
    private BigDecimal originalTax;//原币税额
    
	@EmiColumn(name = "assistNumber")
    private BigDecimal assistNumber;
	
	
	@EmiColumn(name = "localTaxPrice")
    private BigDecimal localTaxPrice;//本币含税单价
    
	@EmiColumn(name = "localTaxMoney")
    private BigDecimal localTaxMoney;//本币含税金额
    
	@EmiColumn(name = "localNotaxPrice")
    private BigDecimal localNotaxPrice;//本币不含税单价
    
	@EmiColumn(name = "localNotaxMoney")
    private BigDecimal localNotaxMoney;//本币不含税金额
    
	
	@EmiColumn(name = "localTax")
    private BigDecimal localTax;//本币税额
    
	@EmiColumn(name = "lock")
    private Integer lock;
	
	@EmiColumn(name = "taxRate")
    private BigDecimal taxRate;
	
	@EmiColumn(name = "cfree1")
    private String cfree1;
    
	@EmiColumn(name = "cfree2")
    private String cfree2;
    
	@EmiColumn(name = "moMainGid")
    private String moMainGid;//委外订单总表Gid

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

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getOriginalTaxPrice() {
		return originalTaxPrice;
	}

	public void setOriginalTaxPrice(BigDecimal originalTaxPrice) {
		this.originalTaxPrice = originalTaxPrice;
	}

	public BigDecimal getOriginalTaxMoney() {
		return originalTaxMoney;
	}

	public void setOriginalTaxMoney(BigDecimal originalTaxMoney) {
		this.originalTaxMoney = originalTaxMoney;
	}

	public BigDecimal getOriginalNotaxPrice() {
		return originalNotaxPrice;
	}

	public void setOriginalNotaxPrice(BigDecimal originalNotaxPrice) {
		this.originalNotaxPrice = originalNotaxPrice;
	}

	public BigDecimal getOriginalNotaxMoney() {
		return originalNotaxMoney;
	}

	public void setOriginalNotaxMoney(BigDecimal originalNotaxMoney) {
		this.originalNotaxMoney = originalNotaxMoney;
	}

	public BigDecimal getOriginalTax() {
		return originalTax;
	}

	public void setOriginalTax(BigDecimal originalTax) {
		this.originalTax = originalTax;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}

	public BigDecimal getLocalTaxPrice() {
		return localTaxPrice;
	}

	public void setLocalTaxPrice(BigDecimal localTaxPrice) {
		this.localTaxPrice = localTaxPrice;
	}

	public BigDecimal getLocalTaxMoney() {
		return localTaxMoney;
	}

	public void setLocalTaxMoney(BigDecimal localTaxMoney) {
		this.localTaxMoney = localTaxMoney;
	}

	public BigDecimal getLocalNotaxPrice() {
		return localNotaxPrice;
	}

	public void setLocalNotaxPrice(BigDecimal localNotaxPrice) {
		this.localNotaxPrice = localNotaxPrice;
	}

	public BigDecimal getLocalNotaxMoney() {
		return localNotaxMoney;
	}

	public void setLocalNotaxMoney(BigDecimal localNotaxMoney) {
		this.localNotaxMoney = localNotaxMoney;
	}

	public BigDecimal getLocalTax() {
		return localTax;
	}

	public void setLocalTax(BigDecimal localTax) {
		this.localTax = localTax;
	}

	public Integer getLock() {
		return lock;
	}

	public void setLock(Integer lock) {
		this.lock = lock;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
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

	public String getMoMainGid() {
		return moMainGid;
	}

	public void setMoMainGid(String moMainGid) {
		this.moMainGid = moMainGid;
	}
    
    
    
   
}