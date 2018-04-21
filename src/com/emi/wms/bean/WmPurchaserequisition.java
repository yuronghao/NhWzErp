package com.emi.wms.bean;

import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="WM_PurchaseRequisition")
public class WmPurchaserequisition {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.pk
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.gid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name = "gid", ID = true)
    private String gid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.billCode
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="billcode" )
    private String billcode;//单据编号

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.billDate
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="billdate" )
    private Date billdate;//单据日期

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.departmentUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="departmentuid" )
    private String departmentuid;//部门id

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.salesmanUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="salesmanuid" )
    private String salesmanuid;//业务员id

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.purchaseType
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="purchasetype" )
    private Integer purchasetype;//采购类型

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.notes
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="notes" )
    private String notes;//备注

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.recordPersonUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="recordpersonuid" )
    private String recordpersonuid;//录入人

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.recordDate
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="recorddate" )
    private Date recorddate;//录入日期

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.auditPersonUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="auditpersonuid" )
    private String auditpersonuid;//审核人

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.auditTime
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="audittime" )
    private Date audittime;//审核日期

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.sobGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="sobgid" )
    private String sobgid;//账套id

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.orgGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="orggid" )
    private String orggid;//组织id

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.WM_PurchaseRequisition.flag
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="flag" )
    private Integer flag;//状态

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.pk
     *
     * @return the value of dbo.WM_PurchaseRequisition.pk
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public Integer getPk() {
        return pk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.pk
     *
     * @param pk the value for dbo.WM_PurchaseRequisition.pk
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setPk(Integer pk) {
        this.pk = pk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.gid
     *
     * @return the value of dbo.WM_PurchaseRequisition.gid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getGid() {
        return gid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.gid
     *
     * @param gid the value for dbo.WM_PurchaseRequisition.gid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setGid(String gid) {
        this.gid = gid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.billCode
     *
     * @return the value of dbo.WM_PurchaseRequisition.billCode
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getBillcode() {
        return billcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.billCode
     *
     * @param billcode the value for dbo.WM_PurchaseRequisition.billCode
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.billDate
     *
     * @return the value of dbo.WM_PurchaseRequisition.billDate
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public Date getBilldate() {
        return billdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.billDate
     *
     * @param billdate the value for dbo.WM_PurchaseRequisition.billDate
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setBilldate(Date billdate) {
        this.billdate = billdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.departmentUid
     *
     * @return the value of dbo.WM_PurchaseRequisition.departmentUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getDepartmentuid() {
        return departmentuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.departmentUid
     *
     * @param departmentuid the value for dbo.WM_PurchaseRequisition.departmentUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setDepartmentuid(String departmentuid) {
        this.departmentuid = departmentuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.salesmanUid
     *
     * @return the value of dbo.WM_PurchaseRequisition.salesmanUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getSalesmanuid() {
        return salesmanuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.salesmanUid
     *
     * @param salesmanuid the value for dbo.WM_PurchaseRequisition.salesmanUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setSalesmanuid(String salesmanuid) {
        this.salesmanuid = salesmanuid;
    }

    public Integer getPurchasetype() {
		return purchasetype;
	}

	public void setPurchasetype(Integer purchasetype) {
		this.purchasetype = purchasetype;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.notes
     *
     * @return the value of dbo.WM_PurchaseRequisition.notes
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getNotes() {
        return notes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.notes
     *
     * @param notes the value for dbo.WM_PurchaseRequisition.notes
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.recordPersonUid
     *
     * @return the value of dbo.WM_PurchaseRequisition.recordPersonUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getRecordpersonuid() {
        return recordpersonuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.recordPersonUid
     *
     * @param recordpersonuid the value for dbo.WM_PurchaseRequisition.recordPersonUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setRecordpersonuid(String recordpersonuid) {
        this.recordpersonuid = recordpersonuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.recordDate
     *
     * @return the value of dbo.WM_PurchaseRequisition.recordDate
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public Date getRecorddate() {
        return recorddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.recordDate
     *
     * @param recorddate the value for dbo.WM_PurchaseRequisition.recordDate
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setRecorddate(Date recorddate) {
        this.recorddate = recorddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.auditPersonUid
     *
     * @return the value of dbo.WM_PurchaseRequisition.auditPersonUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getAuditpersonuid() {
        return auditpersonuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.auditPersonUid
     *
     * @param auditpersonuid the value for dbo.WM_PurchaseRequisition.auditPersonUid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setAuditpersonuid(String auditpersonuid) {
        this.auditpersonuid = auditpersonuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.auditTime
     *
     * @return the value of dbo.WM_PurchaseRequisition.auditTime
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public Date getAudittime() {
        return audittime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.auditTime
     *
     * @param audittime the value for dbo.WM_PurchaseRequisition.auditTime
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.sobGid
     *
     * @return the value of dbo.WM_PurchaseRequisition.sobGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getSobgid() {
        return sobgid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.sobGid
     *
     * @param sobgid the value for dbo.WM_PurchaseRequisition.sobGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setSobgid(String sobgid) {
        this.sobgid = sobgid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.orgGid
     *
     * @return the value of dbo.WM_PurchaseRequisition.orgGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getOrggid() {
        return orggid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.orgGid
     *
     * @param orggid the value for dbo.WM_PurchaseRequisition.orgGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setOrggid(String orggid) {
        this.orggid = orggid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.WM_PurchaseRequisition.flag
     *
     * @return the value of dbo.WM_PurchaseRequisition.flag
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.WM_PurchaseRequisition.flag
     *
     * @param flag the value for dbo.WM_PurchaseRequisition.flag
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}