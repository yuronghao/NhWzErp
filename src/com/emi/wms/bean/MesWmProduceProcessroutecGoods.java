package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工艺路线子表
 */
@EmiTable(name = "MES_WM_ProduceProcessRouteCGoods")
public class MesWmProduceProcessroutecGoods implements Serializable{

	private static final long serialVersionUID = -2574470889262521861L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "produceRouteCGid")
	private String produceRouteCGid;			//订单工艺路线子表id
	
	@EmiColumn(name = "goodsGid")
	private String goodsGid;			//物料gid 
	
	@EmiColumn(name = "number")
	private BigDecimal number;			//数量
	
	@EmiColumn(name = "receivedNum")
	private BigDecimal receivedNum;			//已领数量
	
	@EmiColumn(name = "baseUse")
	private BigDecimal baseUse;			//基本用亮
	
	@EmiColumn(name = "baseQuantity")
	private BigDecimal baseQuantity;	//基础数量

	@EmiColumn(name = "free1")
	private String free1;	//自由项1
	
	@EmiColumn(name = "whUid")
	private String whUid;//触发工序领料任务是否按仓库来触发 1是 0否
	

	public String getWhUid() {
		return whUid;
	}

	public void setWhUid(String whUid) {
		this.whUid = whUid;
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

	public String getProduceRouteCGid() {
		return produceRouteCGid;
	}

	public void setProduceRouteCGid(String produceRouteCGid) {
		this.produceRouteCGid = produceRouteCGid;
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

	public BigDecimal getReceivedNum() {
		return receivedNum;
	}

	public void setReceivedNum(BigDecimal receivedNum) {
		this.receivedNum = receivedNum;
	}

	public BigDecimal getBaseUse() {
		return baseUse;
	}

	public void setBaseUse(BigDecimal baseUse) {
		this.baseUse = baseUse;
	}

	public BigDecimal getBaseQuantity() {
		return baseQuantity;
	}

	public void setBaseQuantity(BigDecimal baseQuantity) {
		this.baseQuantity = baseQuantity;
	}

	public String getFree1() {
		return free1;
	}

	public void setFree1(String free1) {
		this.free1 = free1;
	}

	

}
