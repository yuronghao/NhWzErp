package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "QM_CheckCbill")
public class QMCheckCbill implements Serializable{

	private static final long serialVersionUID = 2372154934728190290L;

	@EmiColumn(name = "pk", increment=true)
    private Integer pk;

	@EmiColumn(name = "gid", ID = true)
    private String gid;

	@EmiColumn(name = "checkGid")
    private String checkGid;//质检单主表gid
	
	@EmiColumn(name = "goodsUid")
	private String goodsUid;//物品uid
	
	@EmiColumn(name = "notes")
    private String notes;//备注
	
	@EmiColumn(name = "checkNum")
	private BigDecimal checkNum;//本次质检数量

	@EmiColumn(name = "okNum")
    private BigDecimal okNum;//质检合格数量

	@EmiColumn(name = "notOkNum")
    private BigDecimal notOkNum;//质检不合格数量

	@EmiColumn(name = "assistOkNum")
    private BigDecimal assistOkNum;//质检合格辅数量

	@EmiColumn(name = "assistNotOkNum")
    private BigDecimal assistNotOkNum;//质检不合格辅数量
	
	@EmiColumn(name = "saleSendCuid")
    private String saleSendCuid;//发货单子表gid
	
	@EmiColumn(name = "procureArrivalCuid")
    private String procureArrivalCuid;//到货单子表gid

	@EmiColumn(name = "rptcGid")
	private String rptcGid;//报工单子表gid
	
	@EmiColumn(name = "produceProcessRouteCGid")
	private String produceProcessRouteCGid;//订单工艺路线子表gid
	
	@EmiColumn(name = "produceCuid")
	private String produceCuid;//生产订单产品表gid

	@EmiColumn(name = "batch")
	private String batch;//批次
	
	
	
	
	public BigDecimal getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(BigDecimal checkNum) {
		this.checkNum = checkNum;
	}

	public String getProduceCuid() {
		return produceCuid;
	}

	public void setProduceCuid(String produceCuid) {
		this.produceCuid = produceCuid;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getGoodsUid() {
		return goodsUid;
	}

	public void setGoodsUid(String goodsUid) {
		this.goodsUid = goodsUid;
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


	public String getCheckGid() {
		return checkGid;
	}

	public void setCheckGid(String checkGid) {
		this.checkGid = checkGid;
	}

	public String getRptcGid() {
		return rptcGid;
	}

	public void setRptcGid(String rptcGid) {
		this.rptcGid = rptcGid;
	}

	public BigDecimal getOkNum() {
		return okNum;
	}

	public void setOkNum(BigDecimal okNum) {
		this.okNum = okNum;
	}

	public BigDecimal getNotOkNum() {
		return notOkNum;
	}

	public void setNotOkNum(BigDecimal notOkNum) {
		this.notOkNum = notOkNum;
	}

	public BigDecimal getAssistOkNum() {
		return assistOkNum;
	}

	public void setAssistOkNum(BigDecimal assistOkNum) {
		this.assistOkNum = assistOkNum;
	}

	public BigDecimal getAssistNotOkNum() {
		return assistNotOkNum;
	}

	public void setAssistNotOkNum(BigDecimal assistNotOkNum) {
		this.assistNotOkNum = assistNotOkNum;
	}

	public String getProduceProcessRouteCGid() {
		return produceProcessRouteCGid;
	}

	public void setProduceProcessRouteCGid(String produceProcessRouteCGid) {
		this.produceProcessRouteCGid = produceProcessRouteCGid;
	}

	public String getSaleSendCuid() {
		return saleSendCuid;
	}

	public void setSaleSendCuid(String saleSendCuid) {
		this.saleSendCuid = saleSendCuid;
	}

	public String getProcureArrivalCuid() {
		return procureArrivalCuid;
	}

	public void setProcureArrivalCuid(String procureArrivalCuid) {
		this.procureArrivalCuid = procureArrivalCuid;
	}


	
	
	

}