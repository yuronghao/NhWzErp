package com.emi.wms.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="MES_WM_CostFee")
public class MesWmCostFee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7596041191883436435L;
	
	@EmiColumn(increment=true,name="pk")
	private Integer pk;

	@EmiColumn(ID=true,name="gid")
    private String gid;

	@EmiColumn(name="costItemGid")
    private String costItemGid;

	@EmiColumn(name="iprice")
    private BigDecimal iprice;

	@EmiColumn(name="year")
    private Integer year;
	
	@EmiColumn(name="month")
    private Integer month;

	@EmiColumn(name="ctime")
	private Timestamp ctime;
	
	@EmiColumn(name="deptCode")
	private String deptCode;
	
	private String deptGid;
	
	private String deptName;//部门名称
	
	private String costItemName;//成本项目名称
	
	private String allotRateGid;//分配率成本要素 对应AA_PriorAttribute gid
	
	private String sourceGid;//来源成本项目  取自AA_PriorAttribute
	
	@EmiColumn(name="maingid")
	private String maingid;
	
	public String getMaingid(){
		return maingid;
	}
	
	public void setMaingid(String maingid){
		this.maingid=maingid;
	}
	
	public String getCostItemName() {
		return costItemName;
	}

	public void setCostItemName(String costItemName) {
		this.costItemName = costItemName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getDeptGid(){
		return deptGid;
	}
	
	public void setDeptGid(String deptGid){
		this.deptGid=deptGid;
	}
	
	public String getSourceGid(){
		return sourceGid;
	}
	
	public void setSourceGid(String sourceGid){
		this.sourceGid=sourceGid;
	}

	public String getAllotRateGid() {
		return allotRateGid;
	}

	public void setAllotRateGid(String allotRateGid) {
		this.allotRateGid = allotRateGid;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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

	public BigDecimal getIprice() {
		return iprice;
	}

	public void setIprice(BigDecimal iprice) {
		this.iprice = iprice;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Timestamp getCtime() {
		return ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	
	
}
