package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工艺路线子表-
 */
@EmiTable(name = "MES_WM_ProduceProcessRouteCMould")
public class MesWmProduceProcessRouteCMould implements Serializable{
	private static final long serialVersionUID = 3851866667767024700L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "mouldGid")
	private String mouldGid;			//模具id
	
	@EmiColumn(name = "routeCGid")
	private String routeCGid;		//工艺路线子表id
	
	@EmiColumn(name = "goodsCode")
	private String goodsCode;		
	
	@EmiColumn(name = "grossWeight")
	private String grossWeight;		
	
	@EmiColumn(name = "netWeight")
	private String netWeight;		
	
	
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
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

	public String getRouteCGid() {
		return routeCGid;
	}

	public void setRouteCGid(String routeCGid) {
		this.routeCGid = routeCGid;
	}

	public String getMouldGid() {
		return mouldGid;
	}

	public void setMouldGid(String mouldGid) {
		this.mouldGid = mouldGid;
	}

}
