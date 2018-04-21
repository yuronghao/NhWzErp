package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.emi.common.util.CommonUtil;

public class CurrentStock implements Serializable{

	private static final long serialVersionUID = 3568690942642981608L;

	private Integer autoID;
	
	private String cwhCode;//仓库编码
	
	private String cinvCode;//物品编码
	
	private String cbatch;//批次
	
	private BigDecimal iquantity;//结存数量
	
	private BigDecimal inum;//结存辅计量数量
	
	private String cfree1;//自由项1
	
	private String cfree2;//自由项2
	
	private BigDecimal foutQuantity;//待发货数量 
	private BigDecimal foutNum;//待发货辅计量数量
	
	
	private BigDecimal finQuantity;//待入库数量 
	private BigDecimal finNum;//待入库辅计量数量 
	
	private Timestamp dmDate;//生产日期
	private Timestamp dvDate;//失效日期
	private Integer imassDate;//有效期天数
	private Integer cmassUnit;//质保期单位 3标识天
	
	
	
	public Timestamp getDmDate() {
		return dmDate;
	}

	public void setDmDate(Timestamp dmDate) {
		this.dmDate = dmDate;
	}

	public Integer getImassDate() {
		return imassDate;
	}

	public void setImassDate(Integer imassDate) {
		this.imassDate = imassDate;
	}

	public Integer getCmassUnit() {
		return cmassUnit;
	}

	public void setCmassUnit(Integer cmassUnit) {
		this.cmassUnit = cmassUnit;
	}

	public Timestamp getDvDate() {
		return dvDate;
	}

	public void setDvDate(Timestamp dvDate) {
		this.dvDate = dvDate;
	}

	public BigDecimal getFinQuantity() {
		return finQuantity;
	}

	public void setFinQuantity(BigDecimal finQuantity) {
		this.finQuantity = finQuantity;
	}

	public BigDecimal getFinNum() {
		return finNum;
	}

	public void setFinNum(BigDecimal finNum) {
		this.finNum = finNum;
	}

	public Integer getAutoID() {
		return autoID;
	}

	public void setAutoID(Integer autoID) {
		this.autoID = autoID;
	}

	public String getCwhCode() {
		return cwhCode;
	}

	public void setCwhCode(String cwhCode) {
		this.cwhCode = cwhCode;
	}

	public String getCinvCode() {
		return cinvCode;
	}

	public void setCinvCode(String cinvCode) {
		this.cinvCode = cinvCode;
	}

	public String getCbatch() {
		return cbatch;
	}

	public void setCbatch(String cbatch) {
		this.cbatch = cbatch;
	}

	public BigDecimal getIquantity() {
		return iquantity;
	}

	public void setIquantity(BigDecimal iquantity) {
		this.iquantity = iquantity;
	}

	public BigDecimal getInum() {
		return inum;
	}

	public void setInum(BigDecimal inum) {
		this.inum = inum;
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

	public BigDecimal getFoutQuantity() {
		return foutQuantity;
	}

	public void setFoutQuantity(BigDecimal foutQuantity) {
		this.foutQuantity = foutQuantity;
	}

	public BigDecimal getFoutNum() {
		return foutNum;
	}

	public void setFoutNum(BigDecimal foutNum) {
		this.foutNum = foutNum;
	}

	
	
	
	@Override
	public boolean equals(Object obj) {
		
		if(this==obj){
			return true;
		}
		
		if(obj instanceof CurrentStock){
			CurrentStock newObj=(CurrentStock) obj;
			
			if(
				//物品id
				((CommonUtil.isNullObject(this.getCinvCode())?"":this.getCinvCode()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCinvCode())?"":newObj.getCinvCode()) )
				&&
				//仓库
				((CommonUtil.isNullObject(this.getCwhCode())?"":this.getCwhCode()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCwhCode())?"":newObj.getCwhCode()))
				&&
				//批次
				((CommonUtil.isNullObject(this.getCbatch())?"":this.getCbatch()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCbatch())?"":newObj.getCbatch()))
				&&
				//自由项1
				((CommonUtil.isNullObject(this.getCfree1())?"":this.getCfree1()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCfree1())?"":newObj.getCfree1()))
				&&
				//自由项2
				((CommonUtil.isNullObject(this.getCfree2())?"":this.getCfree2()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getCfree2())?"":newObj.getCfree2()))
				
				){
				
				return true;
			}else{

				
			}
			
		}
		
		return false;
	}
	
	
}
