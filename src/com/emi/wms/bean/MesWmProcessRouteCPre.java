package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 工艺路线子表-上道工序转入
 */
@EmiTable(name = "MES_WM_ProcessRouteCPre")
public class MesWmProcessRouteCPre implements Serializable{
	private static final long serialVersionUID = 3851866667767024700L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;			//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;				//uuid
	
	@EmiColumn(name = "routeCGid")
	private String routeCGid;			//工艺路线子表id
	
	@EmiColumn(name = "baseUse")
	private BigDecimal baseUse;			//基本用量
	
	@EmiColumn(name = "baseQuantity")
	private BigDecimal baseQuantity;		//基础用量
	
//	@EmiColumn(name = "standardUse")
	private BigDecimal standardUse;			//标准用量
	
	@EmiColumn(name = "preRouteCGId")
	private String preRouteCGId;	//上道工艺路线子表id

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

	public BigDecimal getStandardUse() {
		return standardUse;
	}

	public void setStandardUse(BigDecimal standardUse) {
		this.standardUse = standardUse;
	}

	public String getPreRouteCGId() {
		return preRouteCGId;
	}

	public void setPreRouteCGId(String preRouteCGId) {
		this.preRouteCGId = preRouteCGId;
	}

}
