package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;

public class FollowRuleNodeUser {

    @EmiColumn(increment=true,ID=true,name ="id")
    private  int id;

    @EmiColumn(name ="followruleid")
    private  int followruleid;
    @EmiColumn(name ="nodename")
    private  String  nodename;
    @EmiColumn(name ="nodeindex")
    private  int nodeindex;
    @EmiColumn(name ="parentcodeid")
    private  int parentcodeid;
    @EmiColumn(name ="nextcodeid")
    private  int nextcodeid;
    @EmiColumn(name ="userid")
    private  int userid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollowruleid() {
        return followruleid;
    }

    public void setFollowruleid(int followruleid) {
        this.followruleid = followruleid;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public int getNodeindex() {
        return nodeindex;
    }

    public void setNodeindex(int nodeindex) {
        this.nodeindex = nodeindex;
    }

    public int getParentcodeid() {
        return parentcodeid;
    }

    public void setParentcodeid(int parentcodeid) {
        this.parentcodeid = parentcodeid;
    }

    public int getNextcodeid() {
        return nextcodeid;
    }

    public void setNextcodeid(int nextcodeid) {
        this.nextcodeid = nextcodeid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
