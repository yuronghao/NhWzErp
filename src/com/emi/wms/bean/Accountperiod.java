package com.emi.wms.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="accountPeriod")
public class Accountperiod {

	@EmiColumn(name = "pk",increment=true)
    private Integer pk;

	@EmiColumn(name = "gid",ID=true)
    private String gid;

	@EmiColumn(name = "apyear")
    private Integer apyear;

	@EmiColumn(name = "ap")
    private Integer ap;

	@EmiColumn(name = "begintime")
    private Timestamp begintime;

	@EmiColumn(name = "endtime")
    private Timestamp endtime;

	@EmiColumn(name = "accounting")
    private Integer accounting;

	@EmiColumn(name = "sobid")
    private String sobid;

	@EmiColumn(name = "orgid")
    private String orgid;


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


    public Integer getApyear() {
        return apyear;
    }


    public void setApyear(Integer apyear) {
        this.apyear = apyear;
    }


    public Integer getAp() {
        return ap;
    }


    public void setAp(Integer ap) {
        this.ap = ap;
    }


    public Date getBegintime() {
        return begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public Integer getAccounting() {
        return accounting;
    }


    public void setAccounting(Integer accounting) {
        this.accounting = accounting;
    }


    public String getSobid() {
        return sobid;
    }


    public void setSobid(String sobid) {
        this.sobid = sobid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }


	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}


	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
    
    
    
    
    
}