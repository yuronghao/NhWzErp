package com.emi.android.bean;


/**
* @Desc 该类打印使用，转换实体 方法：printbarcode
* @author yurh
* @create 2018-03-15 16:19:42
**/
public class PrintGood {
//    [{"goodsUid":"32768B7E-E7D6-47AF-980F-40705D28F21F","goodsCode":"QJ-01401","goodsName":"定位销","goodsstandard":"24111729","printamount":"1"}]

    private String goodsUid;

    private String goodsCode;

    private String goodsName;

    private String goodsstandard;

    private Integer printamount;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsstandard() {
        return goodsstandard;
    }

    public void setGoodsstandard(String goodsstandard) {
        this.goodsstandard = goodsstandard;
    }

    public Integer getPrintamount() {
        return printamount;
    }

    public void setPrintamount(Integer printamount) {
        this.printamount = printamount;
    }

    public String getGoodsUid() {
        return goodsUid;
    }

    public void setGoodsUid(String goodsUid) {
        this.goodsUid = goodsUid;
    }
}
