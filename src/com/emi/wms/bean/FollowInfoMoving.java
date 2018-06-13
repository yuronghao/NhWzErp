package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.util.Date;

@EmiTable(name = "FollowInfoMoving")
public class FollowInfoMoving {

    @EmiColumn(increment=true,name ="id")
    private int id;

    @EmiColumn(name ="billsgid")
    private String  billsgid;
    @EmiColumn(name ="currentnodeid")
    private int currentnodeid;
    @EmiColumn(name ="currentnodeindex")
    private int currentnodeindex;
    @EmiColumn(name ="approvaluser")
    private String  approvaluser;
    @EmiColumn(name ="approvaltime")
    private Date approvaltime;
    @EmiColumn(name ="status")
    private int status;
    @EmiColumn(name ="ctime")
    private Date ctime;


    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

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

    public String  getApprovaluser() {
        return approvaluser;
    }

    public void setApprovaluser(String  approvaluser) {
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
