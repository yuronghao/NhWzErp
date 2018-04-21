package com.emi.cache.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "WM_AllocationStock") 
public class Stock  implements Serializable{
	private static final long serialVersionUID = -3482255156737060744L;
	//物料ID
	@EmiColumn(name = "gGoodsUid", ID = true)
	private String goodsGid;
	//物料现存量
	@EmiColumn(name="availableQuantity")
	private BigDecimal availableQuantity;
	
	public String getGoodsGid() {
		return goodsGid;
	}
	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
	}
	public BigDecimal getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(BigDecimal availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

}