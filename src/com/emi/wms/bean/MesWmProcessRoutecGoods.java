package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工艺路线子表
 */
@EmiTable(name = "MES_WM_ProcessRouteCGoods")
public class MesWmProcessRoutecGoods implements Serializable{

	private static final long serialVersionUID = -2574470889262521861L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "routeCGid")
	private String routeCGid;			//工艺路线子表id
	
	@EmiColumn(name = "goodsGid")
	private String goodsGid;			//物料gid 
	
	@EmiColumn(name = "baseUse")
	private BigDecimal baseUse;			//基本用量
	
	@EmiColumn(name = "baseQuantity")
	private BigDecimal baseQuantity;			//基础数量

	@EmiColumn(name = "number")
	private BigDecimal number;			//应领数量
	
	@EmiColumn(name = "free1")
	private String free1;	//自由项1
	
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

	public String getRouteCGid() {
		return routeCGid;
	}

	public void setRouteCGid(String routeCGid) {
		this.routeCGid = routeCGid;
	}

	public String getGoodsGid() {
		return goodsGid;
	}

	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
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

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public String getFree1() {
		return free1;
	}

	public void setFree1(String free1) {
		this.free1 = free1;
	}


}
