package com.emi.wms.processDesign.bean;

public class StockRouteC {
	private String routecId;
	private Integer isStock;
	private Integer isSemi;
	private String opname;
	public StockRouteC() {
		// TODO Auto-generated constructor stub
	}
	public StockRouteC(String routecId, Integer isStock, Integer isSemi,String opname) {
		super();
		this.routecId = routecId;
		this.isStock = isStock;
		this.isSemi = isSemi;
		this.opname = opname;
	}
	public String getRoutecId() {
		return routecId;
	}
	public void setRoutecId(String routecId) {
		this.routecId = routecId;
	}
	public Integer getIsStock() {
		return isStock;
	}
	public void setIsStock(Integer isStock) {
		this.isStock = isStock;
	}
	public Integer getIsSemi() {
		return isSemi;
	}
	public void setIsSemi(Integer isSemi) {
		this.isSemi = isSemi;
	}
	public String getOpname() {
		return opname;
	}
	public void setOpname(String opname) {
		this.opname = opname;
	}
	
}
