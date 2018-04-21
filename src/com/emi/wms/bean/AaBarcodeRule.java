package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "AA_BarcodeRule")
public class AaBarcodeRule implements Serializable {
	private static final long serialVersionUID = -7437897589002370963L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//物品ID
	@EmiColumn(name = "gid", ID = true)
	private String gid;//物品UID
	
	@EmiColumn(name = "type")
	private String type;//条码类型 g物料
	
	@EmiColumn(name = "barcodeName")
	private String barcodeName;//字段名称
	
	@EmiColumn(name = "length")
	private Integer length;//长度
	
	@EmiColumn(name = "isUse")
	private Integer isUse;//是否启用 1启用 0未启用

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBarcodeName() {
		return barcodeName;
	}

	public void setBarcodeName(String barcodeName) {
		this.barcodeName = barcodeName;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getIsUse() {
		return isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	
	
	
	


}