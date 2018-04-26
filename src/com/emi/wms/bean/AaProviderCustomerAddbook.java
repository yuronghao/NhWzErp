package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="AA_Provider_Customer_AddBook")
public class AaProviderCustomerAddbook {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.AA_Provider_Customer_AddBook.pk
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;//

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.AA_Provider_Customer_AddBook.gid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name = "gid", ID = true)
    private String gid;//客商所属通讯Gid

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.AA_Provider_Customer_AddBook.pcGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="pcGid" )
    private String pcgid;//客商Uid

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.AA_Provider_Customer_AddBook.deliverPerson
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="deliverPerson" )
    private String deliverperson;//送货联系人

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.AA_Provider_Customer_AddBook.deliverAddr
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="deliverAddr" )
    private String deliveraddr;//送货地址

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dbo.AA_Provider_Customer_AddBook.deliverTel
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
	@EmiColumn(name="deliverTel" )
    private String delivertel;//联系电话

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.AA_Provider_Customer_AddBook.pk
     *
     * @return the value of dbo.AA_Provider_Customer_AddBook.pk
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public Integer getPk() {
        return pk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.AA_Provider_Customer_AddBook.pk
     *
     * @param pk the value for dbo.AA_Provider_Customer_AddBook.pk
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setPk(Integer pk) {
        this.pk = pk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.AA_Provider_Customer_AddBook.gid
     *
     * @return the value of dbo.AA_Provider_Customer_AddBook.gid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getGid() {
        return gid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.AA_Provider_Customer_AddBook.gid
     *
     * @param gid the value for dbo.AA_Provider_Customer_AddBook.gid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setGid(String gid) {
        this.gid = gid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.AA_Provider_Customer_AddBook.pcGid
     *
     * @return the value of dbo.AA_Provider_Customer_AddBook.pcGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getPcgid() {
        return pcgid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.AA_Provider_Customer_AddBook.pcGid
     *
     * @param pcgid the value for dbo.AA_Provider_Customer_AddBook.pcGid
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setPcgid(String pcgid) {
        this.pcgid = pcgid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.AA_Provider_Customer_AddBook.deliverPerson
     *
     * @return the value of dbo.AA_Provider_Customer_AddBook.deliverPerson
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getDeliverperson() {
        return deliverperson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.AA_Provider_Customer_AddBook.deliverPerson
     *
     * @param deliverperson the value for dbo.AA_Provider_Customer_AddBook.deliverPerson
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setDeliverperson(String deliverperson) {
        this.deliverperson = deliverperson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.AA_Provider_Customer_AddBook.deliverAddr
     *
     * @return the value of dbo.AA_Provider_Customer_AddBook.deliverAddr
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getDeliveraddr() {
        return deliveraddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.AA_Provider_Customer_AddBook.deliverAddr
     *
     * @param deliveraddr the value for dbo.AA_Provider_Customer_AddBook.deliverAddr
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setDeliveraddr(String deliveraddr) {
        this.deliveraddr = deliveraddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dbo.AA_Provider_Customer_AddBook.deliverTel
     *
     * @return the value of dbo.AA_Provider_Customer_AddBook.deliverTel
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public String getDelivertel() {
        return delivertel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dbo.AA_Provider_Customer_AddBook.deliverTel
     *
     * @param delivertel the value for dbo.AA_Provider_Customer_AddBook.deliverTel
     *
     * @mbggenerated Wed Dec 02 15:26:31 CST 2015
     */
    public void setDelivertel(String delivertel) {
        this.delivertel = delivertel;
    }
}