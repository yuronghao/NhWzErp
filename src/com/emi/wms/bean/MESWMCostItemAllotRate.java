package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/** 
* @author  作者 :张向龙    E-mail: 
* @date 创建时间：2017年6月6日 下午3:23:46 
* @version 1.0  
*/
@EmiTable(name="MES_WM_CostItemAllotRate")
public class MESWMCostItemAllotRate implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4731577920537178606L;
	
	
	@EmiColumn(increment=true,name="pk")
	private Integer pk;
	
	@EmiColumn(ID=true,name="gid")
    private String gid;
	
	@EmiColumn(name="costItemGid")
    private String costItemGid;
	
	@EmiColumn(name="notes")
    private String notes;
	
	@EmiColumn(name="orgGid")
    private String orgGid;
	
	@EmiColumn(name="sobGid")
    private String sobGid;
	
	/*@EmiColumn(name="isDelete")
    private Integer isDelete;*/
	
	@EmiColumn(name="goodsGid")
    private String goodsGid;
	
	@EmiColumn(name="cfree1")
    private String cfree1;
	
	@EmiColumn(name="cfree2")
    private String cfree2;
	
	@EmiColumn(name="depGid")
    private String depGid;
	
	@EmiColumn(name="ratio")
    private BigDecimal ratio;

    private String goodsName;
	
    private String goodsCode;
    
    private String depName;
	
    private String costItemName;
    
    private String goodsStandard;
    
    
    @EmiColumn(name="costItemCodeImport")
    private String costItemCodeImport;
    
    @EmiColumn(name="goodsCodeImport")
    private String goodsCodeImport;
    
    @EmiColumn(name="depCodeImport")
    private String depCodeImport;
    
    
    
    
	public String getCostItemCodeImport() {
		return costItemCodeImport;
	}

	public void setCostItemCodeImport(String costItemCodeImport) {
		this.costItemCodeImport = costItemCodeImport;
	}

	public String getGoodsCodeImport() {
		return goodsCodeImport;
	}

	public void setGoodsCodeImport(String goodsCodeImport) {
		this.goodsCodeImport = goodsCodeImport;
	}

	public String getDepCodeImport() {
		return depCodeImport;
	}

	public void setDepCodeImport(String depCodeImport) {
		this.depCodeImport = depCodeImport;
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

	public String getCostItemGid() {
		return costItemGid;
	}

	public void setCostItemGid(String costItemGid) {
		this.costItemGid = costItemGid;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrgGid() {
		return orgGid;
	}

	public void setOrgGid(String orgGid) {
		this.orgGid = orgGid;
	}

	public String getSobGid() {
		return sobGid;
	}

	public void setSobGid(String sobGid) {
		this.sobGid = sobGid;
	}

	/*public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
*/
	public String getGoodsGid() {
		return goodsGid;
	}

	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
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

	public String getDepGid() {
		return depGid;
	}

	public void setDepGid(String depGid) {
		this.depGid = depGid;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getCostItemName() {
		return costItemName;
	}

	public void setCostItemName(String costItemName) {
		this.costItemName = costItemName;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
