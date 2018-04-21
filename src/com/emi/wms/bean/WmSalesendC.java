package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_SaleSend_C")
public class WmSalesendC implements Serializable{

	private static final long serialVersionUID = 9028146607589501651L;

	//pk 
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

	//gid 
	@EmiColumn(name="gid" )
    private String gid;

	//备注
	@EmiColumn(name="notes" )
    private String notes;

	//销售发货单主表uid 
	@EmiColumn(name="salesenduid" )
    private String salesenduid;

	//物品uid 
	@EmiColumn(name="goodsuid" )
    private String goodsuid;

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

	//销售订单子表uid 
	@EmiColumn(name="saleordercuid" )
    private String saleordercuid;

	//仓库编码 
	@EmiColumn(name="whcode" )
    private String whcode;

	//批次 
	@EmiColumn(name="batch" )
    private String batch;

	@EmiColumn(name="idForSynchro" )
	private String idForSynchro;//销售发货单主表标识，同步u8数据使用，临时存储(主子表关联用)
	
	@EmiColumn(name="autoidForSynchro" )
	private String autoidForSynchro;//发货单子表id，同步u8数据使用，临时存储
	
	//数量 
	@EmiColumn(name="number" )
    private BigDecimal number;
	
	//已出库数量 
	@EmiColumn(name="putoutnum" )
    private BigDecimal putoutnum;
	
	//数量(辅)
	@EmiColumn(name="assistNumber" )
    private BigDecimal assistNumber;
	
	//已出库数量（辅）
	@EmiColumn(name="putoutAssistNum" )
    private BigDecimal putoutAssistNum;
	
	@EmiColumn(name="checkOkNumber" )
	private BigDecimal checkOkNumber;//检验合格数量
	
	@EmiColumn(name="checkNotOkNumber" )
	private BigDecimal checkNotOkNumber;//检验不合格数量
	
	@EmiColumn(name="checkOkAssistNumber" )
	private BigDecimal checkOkAssistNumber;//检验合格辅数量
	
	@EmiColumn(name="checkNotOkAssistNumber" )
	private BigDecimal checkNotOkAssistNumber;//检验不合格辅数量
	
	
	
	
	public BigDecimal getCheckOkNumber() {
		return checkOkNumber;
	}

	public void setCheckOkNumber(BigDecimal checkOkNumber) {
		this.checkOkNumber = checkOkNumber;
	}

	public BigDecimal getCheckNotOkNumber() {
		return checkNotOkNumber;
	}

	public void setCheckNotOkNumber(BigDecimal checkNotOkNumber) {
		this.checkNotOkNumber = checkNotOkNumber;
	}

	public BigDecimal getCheckOkAssistNumber() {
		return checkOkAssistNumber;
	}

	public void setCheckOkAssistNumber(BigDecimal checkOkAssistNumber) {
		this.checkOkAssistNumber = checkOkAssistNumber;
	}

	public BigDecimal getCheckNotOkAssistNumber() {
		return checkNotOkAssistNumber;
	}

	public void setCheckNotOkAssistNumber(BigDecimal checkNotOkAssistNumber) {
		this.checkNotOkAssistNumber = checkNotOkAssistNumber;
	}

	@EmiColumn(name="cfree1" )
	private String cfree1;//自由项1
	
	@EmiColumn(name="cfree2" )
	private String cfree2;//自由项2

	
	
	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}

	public BigDecimal getPutoutAssistNum() {
		return putoutAssistNum;
	}

	public void setPutoutAssistNum(BigDecimal putoutAssistNum) {
		this.putoutAssistNum = putoutAssistNum;
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

	public String getSalesenduid() {
		return salesenduid;
	}

	public void setSalesenduid(String salesenduid) {
		this.salesenduid = salesenduid;
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

	public BigDecimal getPutoutnum() {
		return putoutnum;
	}

	public void setPutoutnum(BigDecimal putoutnum) {
		this.putoutnum = putoutnum;
	}

	public String getSaleordercuid() {
		return saleordercuid;
	}

	public void setSaleordercuid(String saleordercuid) {
		this.saleordercuid = saleordercuid;
	}

	public String getWhcode() {
		return whcode;
	}

	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getIdForSynchro() {
		return idForSynchro;
	}

	public void setIdForSynchro(String idForSynchro) {
		this.idForSynchro = idForSynchro;
	}

	public String getAutoidForSynchro() {
		return autoidForSynchro;
	}

	public void setAutoidForSynchro(String autoidForSynchro) {
		this.autoidForSynchro = autoidForSynchro;
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