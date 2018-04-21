package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "MES_WM_CostFeeMain")
public class MESWMCostFeeMain implements Serializable{
	
	@EmiColumn(name = "pk", increment=true)
    private Integer pk;
	
	@EmiColumn(name = "gid", ID = true)
    private String gid;
	
	@EmiColumn(name = "iyear", ID = true)
    private Integer iyear;
	
	@EmiColumn(name = "imonth", ID = true)
    private Integer imonth;
	
	@EmiColumn(name = "ctime", ID = true)
    private Date ctime;

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

	public Integer getIyear() {
		return iyear;
	}

	public void setIyear(Integer iyear) {
		this.iyear = iyear;
	}

	public Integer getImonth() {
		return imonth;
	}

	public void setImonth(Integer imonth) {
		this.imonth = imonth;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	
	
}
