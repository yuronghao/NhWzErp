package com.emi.wms.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.emi.common.util.CommonUtil;
import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_AllocationStock")
public class WmAllocationstock {

	@EmiColumn(name="pk",increment=true)
    private Integer pk;
	
	@EmiColumn(name="gid",ID=true)
    private String gid;
    
	@EmiColumn(name="goodscode")
    private String goodscode;
    @EmiColumn(name="goodsUid")
    private String goodsuid;
    
    @EmiColumn(name="number")
    private BigDecimal number;
    @EmiColumn(name="assistNum")
    private BigDecimal assistnum;
    
    @EmiColumn(name="whCode")
    private String whCode;
    
    @EmiColumn(name="goodsallocationcode")
    private String goodsallocationcode;
    @EmiColumn(name="goodsAllocationUid")
    private String goodsallocationuid;
    
    @EmiColumn(name="batch")
    private String batch;
    
    @EmiColumn(name="sobgid" )
    private String sobgid;
    
    @EmiColumn(name="orggid" )
    private String orggid;
    
	@EmiColumn(name="cfree1" )
	private String cfree1;
	
	@EmiColumn(name="cfree2" )
	private String cfree2;
	
	private String goodname;
	private String whname;
	
	private String allocationname;
	private String classificationName;//主单位名称
	private String cstComUnitName;//辅计量单位名称
	
	@EmiColumn(name="cvMIVenCode" )
	private String cvMIVenCode;//代管商编码

	private String goodsStandard;//规格型号

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	private String goodsName;


	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCvMIVenCode() {
		return cvMIVenCode;
	}

	public void setCvMIVenCode(String cvMIVenCode) {
		this.cvMIVenCode = cvMIVenCode;
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




	public String getGoodname() {
		return goodname;
	}




	public void setGoodname(String goodname) {
		this.goodname = goodname;
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




	@Override
	public boolean equals(Object obj) {
		
		if(this==obj){
			return true;
		}
		
		if(obj instanceof WmAllocationstock){
			WmAllocationstock newObj=(WmAllocationstock) obj;
			
			if(
				//物品id
				((CommonUtil.isNullObject(this.getGoodsuid())?"":this.getGoodsuid()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getGoodsuid())?"":newObj.getGoodsuid()) )
				&&
				//货位id
				((CommonUtil.isNullObject(this.getGoodsallocationuid())?"":this.getGoodsallocationuid()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getGoodsallocationuid())?"":newObj.getGoodsallocationuid()))
				&&
				//批次
				((CommonUtil.isNullObject(this.getBatch())?"":this.getBatch()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getBatch())?"":newObj.getBatch()))
				&&
				//自由项1
				((CommonUtil.isNullObject(this.getCfree1())?"":this.getCfree1()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCfree1())?"":newObj.getCfree1()))
				&&
				//自由项2
				((CommonUtil.isNullObject(this.getCfree2())?"":this.getCfree2()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCfree2())?"":newObj.getCfree2()))
				&&
				//代管供应商编码
				((CommonUtil.isNullObject(this.getCvMIVenCode())?"":this.getCvMIVenCode()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCvMIVenCode())?"":newObj.getCvMIVenCode()))
				
				){
				
				return true;
			}else{

				
			}
			
		}
		
		return false;
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

	public String getGoodscode() {
		return goodscode;
	}

	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}

	public String getGoodsuid() {
		return goodsuid;
	}

	public void setGoodsuid(String goodsuid) {
		this.goodsuid = goodsuid;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getAssistnum() {
		return assistnum;
	}

	public void setAssistnum(BigDecimal assistnum) {
		this.assistnum = assistnum;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getGoodsallocationcode() {
		return goodsallocationcode;
	}

	public void setGoodsallocationcode(String goodsallocationcode) {
		this.goodsallocationcode = goodsallocationcode;
	}

	public String getGoodsallocationuid() {
		return goodsallocationuid;
	}

	public void setGoodsallocationuid(String goodsallocationuid) {
		this.goodsallocationuid = goodsallocationuid;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getSobgid() {
		return sobgid;
	}

	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
	}

	public String getOrggid() {
		return orggid;
	}

	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}




     
    
}