package com.emi.wms.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_ProcureArrival_C")
public class WmProcurearrivalC {
	@EmiColumn(name="pk",increment=true)
    private Integer pk;
	@EmiColumn(name="gid",ID=true)
    private String gid;
	@EmiColumn(name="notes")
    private String notes;
	@EmiColumn(name="procurearrivaluid")
    private String procurearrivaluid;
	@EmiColumn(name="goodsuid")
    private String goodsuid;
	@EmiColumn(name="number")
    private BigDecimal number;
	@EmiColumn(name="unitprice")
    private BigDecimal unitprice;
	@EmiColumn(name="totalprice")
    private BigDecimal totalprice;
	@EmiColumn(name="procureorderuid")
    private String procureorderuid;
	@EmiColumn(name="needcheck")
    private Integer needcheck;
	@EmiColumn(name="originaltaxprice")
    private BigDecimal originaltaxprice;
	@EmiColumn(name="originaltaxmoney")
    private BigDecimal originaltaxmoney;
	@EmiColumn(name="originalnotaxprice")
    private BigDecimal originalnotaxprice;
	@EmiColumn(name="originalnotaxmoney")
    private BigDecimal originalnotaxmoney;
	@EmiColumn(name="originaltax")
    private BigDecimal originaltax;
	@EmiColumn(name="localtaxprice")
    private BigDecimal localtaxprice;
	@EmiColumn(name="localtaxmoney")
    private BigDecimal localtaxmoney;
	@EmiColumn(name="localnotaxprice")
    private BigDecimal localnotaxprice;
	@EmiColumn(name="localnotaxmoney")
    private BigDecimal localnotaxmoney;
	@EmiColumn(name="localtax")
    private BigDecimal localtax;
	@EmiColumn(name="putinnumber")
    private BigDecimal putinnumber;
	@EmiColumn(name="planin")
    private Date planin;
	@EmiColumn(name="assistNumber")
    private BigDecimal assistNumber;
	
	@EmiColumn(name="checkOkNumber")
    private BigDecimal checkOkNumber;
	@EmiColumn(name="checkNotOkNumber")
    private BigDecimal checkNotOkNumber;
	@EmiColumn(name="batch")
    private String batch;
	
	@EmiColumn(name="idForSynchro")
	private String idForSynchro;
	
	@EmiColumn(name="autoidForSynchro")
	private String autoidForSynchro;
	
	
	
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
	public String getProcurearrivaluid() {
		return procurearrivaluid;
	}
	public void setProcurearrivaluid(String procurearrivaluid) {
		this.procurearrivaluid = procurearrivaluid;
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
	public String getProcureorderuid() {
		return procureorderuid;
	}
	public void setProcureorderuid(String procureorderuid) {
		this.procureorderuid = procureorderuid;
	}
	public Integer getNeedcheck() {
		return needcheck;
	}
	public void setNeedcheck(Integer needcheck) {
		this.needcheck = needcheck;
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
	public BigDecimal getPutinnumber() {
		return putinnumber;
	}
	public void setPutinnumber(BigDecimal putinnumber) {
		this.putinnumber = putinnumber;
	}
	public Date getPlanin() {
		return planin;
	}
	public void setPlanin(Date planin) {
		this.planin = planin;
	}
	public BigDecimal getAssistNumber() {
		return assistNumber;
	}
	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}
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

    
}