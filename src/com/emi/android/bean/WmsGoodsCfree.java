package com.emi.android.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class WmsGoodsCfree implements Serializable {

	private static final long serialVersionUID = 2265543686697887553L;
	
	private String name;//pda 显示的名字
	
	private String value;//pda 显示的值
	
	private Integer isShow;// 是否在pda中显示 1显示 0未显示
	
	private String colName;//后台对应的表

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}
	
	
	
}
