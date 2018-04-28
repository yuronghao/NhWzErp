package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;

import java.util.Date;

public class FollowInfoMoving {

    @EmiColumn(increment=true,ID=true,name ="id")
    private int id;

    @EmiColumn(name ="billsgid")
    private String  billsgid;
    @EmiColumn(name ="currentnodeid")
    private int currentnodeid;
    @EmiColumn(name ="currentnodeindex")
    private int currentnodeindex;
    @EmiColumn(name ="approvaluser")
    private int approvaluser;
    @EmiColumn(name ="approvaltime")
    private Date approvaltime;
    @EmiColumn(name ="status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillsgid() {
        return billsgid;
    }

    public void setBillsgid(String billsgid) {
        this.billsgid = billsgid;
    }

    public int getCurrentnodeid() {
        return currentnodeid;
    }

    public void setCurrentnodeid(int currentnodeid) {
        this.currentnodeid = currentnodeid;
    }

    public int getCurrentnodeindex() {
        return currentnodeindex;
    }

    public void setCurrentnodeindex(int currentnodeindex) {
        this.currentnodeindex = currentnodeindex;
    }

    public int getApprovaluser() {
        return approvaluser;
    }

    public void setApprovaluser(int approvaluser) {
        this.approvaluser = approvaluser;
    }

    public Date getApprovaltime() {
        return approvaltime;
    }

    public void setApprovaltime(Date approvaltime) {
        this.approvaltime = approvaltime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
