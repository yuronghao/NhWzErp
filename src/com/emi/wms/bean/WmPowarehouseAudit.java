package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.util.Date;

@EmiTable(name="WM_PoWarehouse_Audit")
public class WmPowarehouseAudit {
    @EmiColumn(name="id" ,increment=true)
    private Integer id;
    @EmiColumn(name = "userid")
    private String userid;

    @EmiColumn(name = "ctime")
    private Date ctime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
