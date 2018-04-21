package com.emi.wms.bean;

import java.io.Serializable;


import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name = "OM_Materials")
public class OM_MOMaterials implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1405459399844108348L;

	@EmiColumn(name = "pk", increment=true)
	private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	
	@EmiColumn(name = "moDetailsGid")
    private String moDetailsGid;
	
	
	@EmiColumn(name = "goodsGid")
    private String goodsGid;

	@EmiColumn(name = "number")
    private BigDecimal number;
    
	@EmiColumn(name = "assistNumber")
    private BigDecimal assistNumber;
	
	@EmiColumn(name = "cfree1")
    private String cfree1;
    
	@EmiColumn(name = "cfree2")
    private String cfree2;
    
	@EmiColumn(name = "receivedNum")
    private BigDecimal receivedNum;
    
	@EmiColumn(name = "receivedAssistNum")
    private BigDecimal receivedAssistNum;

	@EmiColumn(name = "moMaterialsIDForSynchro")
	private String moMaterialsIDForSynchro;
	
	

	public String getMoMaterialsIDForSynchro() {
		return moMaterialsIDForSynchro;
	}

	public void setMoMaterialsIDForSynchro(String moMaterialsIDForSynchro) {
		this.moMaterialsIDForSynchro = moMaterialsIDForSynchro;
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

	public String getMoDetailsGid() {
		return moDetailsGid;
	}

	public void setMoDetailsGid(String moDetailsGid) {
		this.moDetailsGid = moDetailsGid;
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

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
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

	public BigDecimal getReceivedNum() {
		return receivedNum;
	}

	public void setReceivedNum(BigDecimal receivedNum) {
		this.receivedNum = receivedNum;
	}

	public BigDecimal getReceivedAssistNum() {
		return receivedAssistNum;
	}

	public void setReceivedAssistNum(BigDecimal receivedAssistNum) {
		this.receivedAssistNum = receivedAssistNum;
	}
    
    
    
}