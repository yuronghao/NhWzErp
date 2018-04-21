package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="MES_WM_StandardProcess")
public class MesWmStandardprocess implements Serializable{
	
	private static final long serialVersionUID = 51845106833969432L;

	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name="opcode" )
    private String opcode;
	
	@EmiColumn(name="opname" )
    private String opname;

	@EmiColumn(name="sobGId" )
    private String sobGId;//帐套ID

	@EmiColumn(name="orgGId" )
    private String orgGId;//组织ID
	
	@EmiColumn(name="isCheck" )
    private Integer isCheck;//是否质检 0：否 1：是
	
	@EmiColumn(name="standardPrice" )
    private BigDecimal standardPrice;//标准工价
	
	@EmiColumn(name="checkRate" )
    private BigDecimal checkRate;//检验合格率
	
	@EmiColumn(name="isDelete" )
    private Integer isDelete;//是否删除
	
	@EmiColumn(name="isStock" )
	private Integer isStock;	//是否入库
	
	@EmiColumn(name="isMustScanMould" )
	private Integer isMustScanMould;	//是否必扫模具
	
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

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	public String getSobGId() {
		return sobGId;
	}

	public void setSobGId(String sobGId) {
		this.sobGId = sobGId;
	}

	public String getOrgGId() {
		return orgGId;
	}

	public void setOrgGId(String orgGId) {
		this.orgGId = orgGId;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public BigDecimal getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(BigDecimal standardPrice) {
		this.standardPrice = standardPrice;
	}

	public BigDecimal getCheckRate() {
		return checkRate;
	}

	public void setCheckRate(BigDecimal checkRate) {
		this.checkRate = checkRate;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsStock() {
		return isStock;
	}

	public void setIsStock(Integer isStock) {
		this.isStock = isStock;
	}

	public Integer getIsMustScanMould() {
		return isMustScanMould;
	}

	public void setIsMustScanMould(Integer isMustScanMould) {
		this.isMustScanMould = isMustScanMould;
	}

	
	
    
}