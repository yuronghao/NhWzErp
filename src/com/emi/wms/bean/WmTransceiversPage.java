package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.math.BigDecimal;

@EmiTable(name="WM_Transceivers_Page")
public class WmTransceiversPage {

	@EmiColumn(name="pk",increment=true)
    private Integer pk;
	

    @EmiColumn(name="goodsUid")
    private String goodsuid;

	@EmiColumn(name="serchDate")
	private String serchDate;

	@EmiColumn(name="whUid")
	private String whUid;

	private String goodscode;

	public String getGoodscode() {
		return goodscode;
	}

	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}

	@EmiColumn(name="topTotalNumber")
    private BigDecimal topTotalNumber;

	@EmiColumn(name="mouthInNumber")
	private BigDecimal mouthInNumber;

	@EmiColumn(name="mouthOutNumber")
	private BigDecimal mouthOutNumber;

	@EmiColumn(name="endTotalNumber")
	private BigDecimal endTotalNumber;

	@EmiColumn(name="ip")
	private String  ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSerchDate() {
		return serchDate;
	}

	public void setSerchDate(String serchDate) {
		this.serchDate = serchDate;
	}

	public String getWhUid() {
		return whUid;
	}

	public void setWhUid(String whUid) {
		this.whUid = whUid;
	}

	public BigDecimal getTopTotalNumber() {
		return topTotalNumber;
	}

	public void setTopTotalNumber(BigDecimal topTotalNumber) {
		this.topTotalNumber = topTotalNumber;
	}

	public BigDecimal getMouthInNumber() {
		return mouthInNumber;
	}

	public void setMouthInNumber(BigDecimal mouthInNumber) {
		this.mouthInNumber = mouthInNumber;
	}

	public BigDecimal getMouthOutNumber() {
		return mouthOutNumber;
	}

	public void setMouthOutNumber(BigDecimal mouthOutNumber) {
		this.mouthOutNumber = mouthOutNumber;
	}

	public BigDecimal getEndTotalNumber() {
		return endTotalNumber;
	}

	public void setEndTotalNumber(BigDecimal endTotalNumber) {
		this.endTotalNumber = endTotalNumber;
	}

	private String whname;
	
	private String allocationname;
	private String classificationName;//主单位名称
	private String cstComUnitName;//辅计量单位名称


	private String goodsStandard;//规格型号

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	@EmiColumn(name="goodsName")
	private String goodsName;


	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}



	public String getClassificationName() {
		return classificationName;
	}


	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}


	
//    private Date recordDate;//批次时间
    
    
	public String getCstComUnitName() {
		return cstComUnitName;
	}




	public void setCstComUnitName(String cstComUnitName) {
		this.cstComUnitName = cstComUnitName;
	}







	public String getWhname() {
		return whname;
	}




	public void setWhname(String whname) {
		this.whname = whname;
	}




	public String getAllocationname() {
		return allocationname;
	}




	public void setAllocationname(String allocationname) {
		this.allocationname = allocationname;
	}





    
    
    
    

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}


	public String getGoodsuid() {
		return goodsuid;
	}

	public void setGoodsuid(String goodsuid) {
		this.goodsuid = goodsuid;
	}





     
    
}