package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.util.Date;

@EmiTable(name = "FollowRule")
public class FollowRule {

    @EmiColumn(increment=true,name ="id")
    private int id;

    @EmiColumn(name="followname" )
    private String followname;

    @EmiColumn(name="invoicestype" )
    private int invoicestype;

    @EmiColumn(name="ctime" )
    private Date ctime;

    @EmiColumn(name="isdel" )
    private int isdel;

    @EmiColumn(name="totalnodenum" )
    private int totalnodenum;

    @EmiColumn(name="rdstylegid" )
    private String rdstylegid;

    @EmiColumn(name="coutwarehousegid" )
    private String coutwarehousegid;

    @EmiColumn(name="cinwarehousegid" )
    private String cinwarehousegid;


    public String getCoutwarehousegid() {
        return coutwarehousegid;
    }

    public void setCoutwarehousegid(String coutwarehousegid) {
        this.coutwarehousegid = coutwarehousegid;
    }

    public String getCinwarehousegid() {
        return cinwarehousegid;
    }

    public void setCinwarehousegid(String cinwarehousegid) {
        this.cinwarehousegid = cinwarehousegid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollowname() {
        return followname;
    }

    public void setFollowname(String followname) {
        this.followname = followname;
    }

    public int getInvoicestype() {
        return invoicestype;
    }

    public void setInvoicestype(int invoicestype) {
        this.invoicestype = invoicestype;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public int getTotalnodenum() {
        return totalnodenum;
    }

    public void setTotalnodenum(int totalnodenum) {
        this.totalnodenum = totalnodenum;
    }

    public String getRdstylegid() {
        return rdstylegid;
    }

    public void setRdstylegid(String rdstylegid) {
        this.rdstylegid = rdstylegid;
    }
}
