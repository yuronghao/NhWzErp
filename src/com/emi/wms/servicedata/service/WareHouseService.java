package com.emi.wms.servicedata.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.emi.android.action.Submit;
import com.emi.android.bean.GoodsAllocationInfor;
import com.emi.android.bean.GoodsInforRsp;
import com.emi.android.bean.GoodsOutforRsp;
import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsGoodsByScan;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.android.bean.WmsTaskDetailRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.wms.bean.*;
import com.emi.wms.servicedata.dao.ProduceOrderDao;
import com.emi.wms.servicedata.dao.PurchaseArrivalDao;
import com.emi.wms.servicedata.dao.SaleDao;
import com.emi.wms.servicedata.dao.TaskDao;
import com.emi.wms.servicedata.dao.WareHouseDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class WareHouseService extends EmiPluginService {

    private CacheCtrlService cacheCtrlService;
    private WareHouseDao wareHouseDao;
    private SaleDao saleDao;
    private ProduceOrderDao produceOrderDao;
    private TaskDao taskDao;
    private PurchaseArrivalService purchaseArrivalService;
    private PurchaseArrivalDao purchaseArrivalDao;

    @Override
    public CacheCtrlService getCacheCtrlService() {
        return cacheCtrlService;
    }

    public WareHouseDao getWareHouseDao() {
        return wareHouseDao;
    }

    public SaleDao getSaleDao() {
        return saleDao;
    }

    public ProduceOrderDao getProduceOrderDao() {
        return produceOrderDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public PurchaseArrivalService getPurchaseArrivalService() {
        return purchaseArrivalService;
    }

    public PurchaseArrivalDao getPurchaseArrivalDao() {
        return purchaseArrivalDao;
    }

    public void setPurchaseArrivalDao(PurchaseArrivalDao purchaseArrivalDao) {
        this.purchaseArrivalDao = purchaseArrivalDao;
    }

    public void setProduceOrderDao(ProduceOrderDao produceOrderDao) {
        this.produceOrderDao = produceOrderDao;
    }

    public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
        this.cacheCtrlService = cacheCtrlService;
    }

    public void setWareHouseDao(WareHouseDao wareHouseDao) {
        this.wareHouseDao = wareHouseDao;
    }

    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void setPurchaseArrivalService(
            PurchaseArrivalService purchaseArrivalService) {
        this.purchaseArrivalService = purchaseArrivalService;
    }

    /**
     * @category 添加工序领料 2016 2016年4月11日上午11:16:38 boolean 宋银海
     */
    public boolean addProcessMaterialOut(WmsTaskDetailRsp msTaskDetailRsp,
                                         JSONObject jsonObject, String billCode) throws Exception {

        WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService
                    .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(aaGoodsallocation.getWhuid());
        }

        Iterator<String> it = warehouse.iterator();

        // 判断新建 出库单 出库单中要注意货位和仓库 不同仓库 不同主表
        List<WmMaterialout> wolist = new ArrayList<WmMaterialout>();
        List<WmMaterialoutC> woclist = new ArrayList<WmMaterialoutC>();

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;

        while (it.hasNext()) {
            String ckGid = it.next();

            // 添加材料出库主表 子表
            WmMaterialout wmMaterialout = new WmMaterialout();// 出库单主表
            String gid = UUID.randomUUID().toString();
            wmMaterialout.setGid(gid);
            wmMaterialout.setDocumenttypeuid("B65B212E-3C78-44CB-8F3E-43913F3664B7");// WM_BillType
//			String billcode = this.getBillId(Constants.TASKTYPE_CLCK);
            wmMaterialout.setBillcode(billCode);
            wmMaterialout.setBilldate(new Date());
            wmMaterialout.setRecordperson(msTaskDetailRsp.getUserGid());
            wmMaterialout.setSobgid(msTaskDetailRsp.getSobgid());
            wmMaterialout.setOrggid(msTaskDetailRsp.getOrggid());
            wolist.add(wmMaterialout);

            for (WmsGoods good : goods) {
                AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    WmMaterialoutC wmMaterialoutC = new WmMaterialoutC();
                    wmMaterialoutC.setGid(good.getGid());
                    wmMaterialoutC.setMaterialoutuid(gid);
                    wmMaterialoutC.setGoodsuid(good.getGoodsUid());
                    wmMaterialoutC.setNumber(good.getSubmitNum());
                    wmMaterialoutC.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmMaterialoutC.setBatchcode(good.getBatch());
                    wmMaterialoutC.setProcessRouteCGoodsUid(good.getProduceRouteCGoodsUid());
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmMaterialoutC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmMaterialoutC.setCfree2(wgc.getValue());
                        }
                    }

                    if ((CommonUtil.isNullObject(good.getIsInvQuality()) ? 0 : good.getIsInvQuality().intValue()) == 1) {// 有效期

                        String condition = " cinvcode='" + good.getGoodsCode()
                                + "' and cwhcode='" + aaWarehouse.getWhcode()
                                + "' and cbatch='" + good.getBatch() + "'";
                        if (!CommonUtil.isNullObject(wmMaterialoutC.getCfree1())) {
                            condition = condition + " and cfree1='"
                                    + wmMaterialoutC.getCfree1() + "'";
                        }
                        if (!CommonUtil.isNullObject(wmMaterialoutC.getCfree2())) {
                            condition = condition + " and cfree2='"
                                    + wmMaterialoutC.getCfree2() + "'";
                        }
                        //此处需要加用友类型判断
                        List<CurrentStock> cs = wareHouseDao.getAllocationStockYonYou(condition);
                        if (cs.size() == 1) {
                            //wmMaterialoutC.setDvdate(CommonUtil.isNullObject(cs.get(0).getDvDate()) ? Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00") : cs.get(0).getDvDate());
                            wmMaterialoutC.setDvdate(cs.get(0).getDvDate());
                            wmMaterialoutC.setDmadeDate(cs.get(0).getDmDate());
                            wmMaterialoutC.setImassDate(cs.get(0).getImassDate());
                        } else {
                            System.out.println("存在多个有效期的库存");
                            return false;
                        }
                    }

                    woclist.add(wmMaterialoutC);

                    AaGoods aaGoods = cacheCtrlService.getGoods(good
                            .getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum().negate());// 相反
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity()
                            .negate());// 相反
                    wmAllocationstock.setCfree1(wmMaterialoutC.getCfree1());
                    wmAllocationstock.setCfree2(wmMaterialoutC.getCfree2());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    wmAllocationstock.setCvMIVenCode(good.getCvMIVenCode());
                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        wmb.setCfree1(wmMaterialoutC.getCfree1());
                        wmb.setCfree2(wmMaterialoutC.getCfree2());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmBatch.add(wmb);
                    }
                }

            }

        }

        wareHouseDao.addMaterialOut(wolist);// 增加主表
        wareHouseDao.addMaterialoutCs(woclist);// 增加子表

        if (msTaskDetailRsp.getIsfinish() == 1) {
            String sql = "update WM_Task set state=2 where gid = '"
                    + msTaskDetailRsp.getTaskGid() + "'";
            wareHouseDao.update(sql);
        }

        // 反填生产订单工艺材料表已出库数量
        updateSourceBillWhenSubmitWareHouse(goods,
                Constants.BILLGIDSOURCE_SCDDGY, "out");

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }


        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8

        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addProcessMaterialOut.emi";

            String condition = " pprc.gid ='" + wmtask.getBillgid() + "'";
            Map orderInfor = produceOrderDao
                    .getOrderInforByProduceProcessroutec(condition);

            jsonObject.put("isBack", false);
            jsonObject.put("mainTableIdentity", orderInfor.get("autoidForSynchro"));// 将json中赋值// mom_orderdetail MoDId

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else if (Config.INTERFACETYPE
                .equalsIgnoreCase(Constants.INTERFACE_TPLUSE)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/tplus/wareHouse_addProcessMaterialOut.emi";

            String condition = " pprc.gid ='" + wmtask.getBillgid() + "'";
            Map orderInfor = produceOrderDao
                    .getOrderInforByProduceProcessroutec(condition);

            jsonObject.put("isBack", false);
            jsonObject.put("mainTableIdentity",
                    orderInfor.get("autoidForSynchro"));// 将json中赋值
            // mom_orderdetail MoDId

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

        return true;
    }

    /**
     * @category 当提交库存时修改来源单据(到货单 或者 发货单 或者 生产订单相关字段) 2016 2016年4月11日下午5:14:34
     * boolean 宋银海
     */
    public boolean updateSourceBillWhenSubmitWareHouse(List<WmsGoods> goods,
                                                       String billSource, String type) {// type in入库 out出库
        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_SCDDGY)
                && type.equalsIgnoreCase("out")) {// 反填生产订单工艺材料表已领料数量
            wareHouseDao.backFillProcessRouteCGoods(goods);
        }
        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_SCDDGY)
                && type.equalsIgnoreCase("in")) {// 反填生产订单工艺子表已入库数量
            wareHouseDao.backFillProcessRouteC(goods);
        }
        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_CGDH)) {// 反填采购到货单子表已入库数量
            wareHouseDao.backFillProcureArrivalC(goods);
        }

        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_CGD)) {// 反填采购订单子表已入库数量
            wareHouseDao.backFillProcureOrderC(goods);
        }


        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_XSFH)) {// 反填销售发货单子表已出库数量
            wareHouseDao.backFillSaleSendC(goods);
        }
        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_XSZJ)) {// 反填质检单子表销售已出库数量
            wareHouseDao.backFillSaleCheckC(goods);
        }
        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_CGZJ)) {// 反填质检单子表采购已入库数量
            wareHouseDao.backFillProcureCheckC(goods);
        }
        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_WWZJ)) {// 反填质检单子表委外已入库数量
            wareHouseDao.backFillProcureCheckC(goods);
        }

        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_CPZJ)) {// 反填质检单子表成品已入库数量
            wareHouseDao.backFillProductCheckC(goods);
        }
        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_WWDD)) {// 反填委外材料出库子二表已出库库数量
            wareHouseDao.backFillMoMaterialsC(goods);
        }

        if (billSource.equalsIgnoreCase(Constants.BILLGIDSOURCE_BGD)) {// 反填报工单子表已入库数量
            wareHouseDao.backFillReportOrderC(goods);
        }


        return true;
    }

    // 反填生产订单已入库数量
    public boolean updateProduceBillIn(List<WmsGoods> goods, String producecGid) {
        wareHouseDao.updateProduceBillIn(goods, producecGid);
        return true;
    }

    /**
     * @category 修改货位现存量 2016 2016年4月7日下午4:21:51 boolean 宋银海
     */
    public boolean updateStocksEntity(List<WmAllocationstock> asList) {

        StringBuffer sbf = new StringBuffer();
        for (WmAllocationstock vi : asList) {
            sbf.append("'" + vi.getGoodsuid() + "',");
        }

        String condition = sbf.toString();
        if (condition.length() > 0) {
            condition = condition.substring(0, condition.length() - 1);
            condition = " and goodsUid in (" + condition + ")";
        }
        String conplus = "";
        // 查询
        List<WmAllocationstock> currentStock = wareHouseDao
                .getAllocationStock(condition, conplus);

        List<WmAllocationstock> toADD = new ArrayList<WmAllocationstock>();
        List<WmAllocationstock> toUpdate = new ArrayList<WmAllocationstock>();

        for (WmAllocationstock as : asList) {
            boolean toA = true;
            for (WmAllocationstock asnow : currentStock) {
                if (as.equals(asnow)) {// 方法重写

                    as.setGid(asnow.getGid());
                    toUpdate.add(as);

                    toA = false;
                    break;
                }
            }

            if (toA) {
                toADD.add(as);
            }

        }

        // 增加
        if (toADD.size() > 0) {
            wareHouseDao.addAllocationStock(toADD);
        }

        // 修改
        if (toUpdate.size() > 0) {
            wareHouseDao.batchUptAllocationStock(toUpdate);
        }

        return true;
    }

    //出库时是否满足库存
    public boolean isEnoughStock(List<WmAllocationstock> asList) {

        StringBuffer sbf = new StringBuffer();
        for (WmAllocationstock vi : asList) {
            sbf.append("'" + vi.getGoodsuid() + "',");
        }

        String condition = sbf.toString();
        if (condition.length() > 0) {
            condition = condition.substring(0, condition.length() - 1);
            condition = " and goodsUid in (" + condition + ")";
        }
        String conplus = "";
        // 查询
        List<WmAllocationstock> currentStock = wareHouseDao.getAllocationStock(condition, conplus);

        for (WmAllocationstock as : asList) {
            boolean isEnough = false;//库存是否足够，初始值 =不够

            if (as.getNumber().compareTo(BigDecimal.valueOf(0)) >= 0 &&
                    CommonUtil.bigDecimal2BigDecimal(as.getAssistnum()).compareTo(BigDecimal.valueOf(0)) >= 0
                    ) {
                isEnough = true;
            }

            for (WmAllocationstock asnow : currentStock) {
                if (as.equals(asnow)) {// 方法重写

                    if (as.getNumber().add(asnow.getNumber()).compareTo(BigDecimal.valueOf(0)) >= 0 &&
                            CommonUtil.bigDecimal2BigDecimal(as.getAssistnum()).add(CommonUtil.bigDecimal2BigDecimal(asnow.getAssistnum())).compareTo(BigDecimal.valueOf(0)) >= 0
                            ) {
                        isEnough = true;
                    }

                    break;
                }
            }

            if (!isEnough) {
                return isEnough;
            }

        }

        return true;
    }


    /**
     * @throws Exception
     * @category 出库时扫描物品、货位条码 2016 2016年4月14日下午2:55:55 GoodsOutInforRsp 宋银海
     */
    public GoodsOutforRsp getOutInforByBarcode(JSONObject jsonObject) throws Exception {

        String conbarcode = jsonObject.getString("conbarcode");// 仓库跟货位结合的编码
        String barcode = jsonObject.getString("barcode");// 条码 // 可能是货位条码、简单物品条码、动态物品条码
        String barcodeType = jsonObject.getString("barcodeType");// 条码类型 // g(物料)，a(货位)，b(批次)

        String goodsUid = jsonObject.getString("goodsUid");// 不是通过扫描得出，是通过详情得出
        String batch = jsonObject.getString("batch");// 不是通过扫描得出，是通过详情得出
        String whCode = jsonObject.getString("whCode");// 不是通过扫描得出，是通过详情得出
        JSONArray cfreeJsonArray = jsonObject.getJSONArray("cfree");// 不是通过扫描得出，是通过详情得出

        GoodsOutforRsp goodsOutInforRsp = new GoodsOutforRsp();

        if (barcodeType.equalsIgnoreCase("g")) {// 物料

            AaGoods aaGoods = null;
            String[] barcodes = null;
            if (barcode.contains("&")) {// 物品启用了自由项
                barcodes = barcode.split("&");
                aaGoods = this.getAaGoods(barcodes[0]);
            } else {// 物品未启用自由项
                aaGoods = this.getAaGoods(barcode);
            }

            if (CommonUtil.isNullObject(aaGoods)) {
                goodsOutInforRsp.setSuccess(0);
                goodsOutInforRsp.setFailInfor("物料编码不存在！");

            } else {

                goodsOutInforRsp.setSuccess(1);
                // TODO 后期可以做反射
                if (barcodes != null) {
                    AaUserDefine ad = this.getAaUserDefine(barcodes[1]);
                    goodsOutInforRsp.setCfree1(ad.getValue());//返回给PDA做定位物料使用
                }

                goodsOutInforRsp.setGoodsCode(aaGoods.getGoodscode());
            }

        } else if (barcodeType.equalsIgnoreCase("a")) {// 货位

            AaGoodsallocation aaGoodsallocation = null;

            aaGoodsallocation = this.getAaGoodsallocation(barcode);// 检测是否是货位条码
            if (CommonUtil.isNullObject(aaGoodsallocation)) {
                aaGoodsallocation = this.getAaGoodsallocation(conbarcode);
            }

            if (CommonUtil.isNullObject(aaGoodsallocation)) {
                goodsOutInforRsp.setSuccess(0);
                goodsOutInforRsp.setFailInfor("所选货位不存在！");
                return goodsOutInforRsp;
            }

            if (whCode.length() > 0 && !whCode.equalsIgnoreCase(aaGoodsallocation.getWhcode())) {
                goodsOutInforRsp.setSuccess(0);
                goodsOutInforRsp.setFailInfor("所选仓库与指定仓库不一致！");
                return goodsOutInforRsp;
            } else {
                goodsOutInforRsp.setSuccess(1);
            }

            // 查询
            AaGoods goods = cacheCtrlService.getGoods(goodsUid);

            String condition = "";

            if (goods.getBinvbach().intValue() == 1) {// 如果批次管理
                condition = "and number>0 and wa.goodsuid='" + goodsUid
                        + "' and wa.goodsallocationuid='"
                        + aaGoodsallocation.getGid() + "' ";

                if (!CommonUtil.isNullObject(batch)) {
                    condition = condition + " and wa.batch='" + batch + "'";
                }
                if (!CommonUtil.isNullObject(whCode)) {
                    condition = condition + " and wa.whCode='" + whCode + "'";
                }

            } else {
                condition = "and number>0 and wa.goodsuid='" + goodsUid
                        + "' and wa.goodsallocationuid='"
                        + aaGoodsallocation.getGid() + "' ";
                if (!CommonUtil.isNullObject(whCode)) {
                    condition = condition + " and wa.whCode='" + whCode + "'";
                }
            }

            for (Object o : cfreeJsonArray) {
                JSONObject cfreeJson = (JSONObject) o;
                String colName = CommonUtil.Obj2String(cfreeJson.get("colName"));
                String value = CommonUtil.Obj2String(cfreeJson.get("value"));

                if (!CommonUtil.isNullObject(colName) && !CommonUtil.isNullObject(value)) {
                    condition += " and wa." + colName + "='" + value + "'";
                }
            }


            List<WmAllocationstock> currentStock = wareHouseDao.getAllocationStockBatch(condition);

            List<GoodsAllocationInfor> goodsAllocationInfor = new ArrayList<GoodsAllocationInfor>();
            GoodsAllocationInfor ga = null;
            AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
            for (WmAllocationstock w : currentStock) {

                ga = new GoodsAllocationInfor();

                ga.setNumber(w.getNumber());
                ga.setAssistNumber(w.getAssistnum());
                ga.setBatch(w.getBatch());

                AaGoodsallocation goodsallocation = cacheCtrlService.getGoodsAllocation(w.getGoodsallocationuid());
                ga.setGoodsAllocationCode(goodsallocation.getCode());// 货位编码
                ga.setGoodsAllocationName(goodsallocation.getName());// 货位名称
                ga.setGoodsAllocationUid(goodsallocation.getGid());// 货位uid
                ga.setWhcode(aaWarehouse.getWhcode());// 仓库编码
                ga.setWareHouseName(CommonUtil.isNullObject(aaWarehouse) ? "" : aaWarehouse.getWhname());
                ga.setCvMIVenCode(CommonUtil.Obj2String(w.getCvMIVenCode()));
                goodsAllocationInfor.add(ga);
            }

            goodsOutInforRsp.setGoodsAllocationInfor(goodsAllocationInfor);

        } else if (barcodeType.equalsIgnoreCase("b")) {// 批次

            // 查询
            AaGoods goods = cacheCtrlService.getGoods(goodsUid);
            String condition = "";
            if (goods.getBinvbach().intValue() == 1) {// 如果批次管理
                condition = " and wa.goodsuid='" + goodsUid + "'";

                if (!CommonUtil.isNullObject(batch) && !barcode.equalsIgnoreCase(batch)) {
                    goodsOutInforRsp.setSuccess(0);
                    goodsOutInforRsp.setFailInfor("所选批次与指定批次不一致！");
                    return goodsOutInforRsp;
                }

                condition = condition + " and wa.batch='" + barcode + "'";

                if (!CommonUtil.isNullObject(whCode)) {
                    condition = condition + " and wa.whCode='" + whCode + "'";
                }

            }

            for (Object o : cfreeJsonArray) {
                JSONObject cfreeJson = (JSONObject) o;
                String colName = CommonUtil.Obj2String(cfreeJson.get("colName"));
                String value = CommonUtil.Obj2String(cfreeJson.get("value"));

                if (!CommonUtil.isNullObject(colName) && !CommonUtil.isNullObject(value)) {
                    condition += " and wa." + colName + "='" + value + "'";
                }
            }

            List<WmAllocationstock> currentStock = wareHouseDao.getAllocationStockBatch(condition);

            List<GoodsAllocationInfor> goodsAllocationInfor = new ArrayList<GoodsAllocationInfor>();

            GoodsAllocationInfor ga = null;
            for (WmAllocationstock w : currentStock) {

                ga = new GoodsAllocationInfor();

                ga.setNumber(w.getNumber());
                ga.setAssistNumber(w.getAssistnum());
                ga.setBatch(w.getBatch());

                AaGoodsallocation goodsallocation = cacheCtrlService.getGoodsAllocation(w.getGoodsallocationuid());
                ga.setGoodsAllocationCode(goodsallocation.getCode());// 货位编码
                ga.setGoodsAllocationName(goodsallocation.getName());// 货位名称
                ga.setGoodsAllocationUid(goodsallocation.getGid());// 货位uid

                AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(goodsallocation.getWhuid());
                ga.setWhcode(aaWarehouse.getWhcode());
                ga.setWareHouseName(CommonUtil.isNullObject(aaWarehouse) ? "" : aaWarehouse.getWhname());
                ga.setCvMIVenCode(CommonUtil.Obj2String(w.getCvMIVenCode()));
                goodsAllocationInfor.add(ga);
            }

            goodsOutInforRsp.setSuccess(1);
            goodsOutInforRsp.setGoodsAllocationInfor(goodsAllocationInfor);

        }

        return goodsOutInforRsp;
    }

    /**
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @category 入库时扫描物品、货位条码 2016 2016年4月14日下午2:55:55 GoodsOutInforRsp 宋银海
     */
    public GoodsInforRsp getInforByBarcode(JSONObject jsonObject)
            throws Exception {

        String barcode = jsonObject.getString("barcode");
        String conbarcode = jsonObject.getString("conbarcode");// 仓库+货位编码的组合
        String barcodeType = jsonObject.getString("barcodeType");// 条码类型
        // g(物料)，a(货位)，b(批次)

        String whCode = jsonObject.getString("whCode");// 不是通过扫描得出，是通过详情得出

        GoodsInforRsp goodsInforRsp = new GoodsInforRsp();// 返回的对象

        if (barcodeType.equalsIgnoreCase("g")) {

			/*
             * 存在如下bug
			 * 物品1启用了自由项，物料条码+自由项=abc01;物品2没有启用自由项，物料条码=abc01。此时如果扫码abc01
			 * ,是无法解析获取的是物料1还是物料2。 跟实施确定如果启用了自由项，中间加&符，即物料1=abc&01 数据表
			 * aa_barcoderule 暂时不用
			 *
			 * List<AaBarcodeRule> rules=this.getAaBarcodeRule("g");//条码规则
			 * List<AaUserDefine> aud=this.getAaUserDefineList();//自定义项属性值 int
			 * i=0; for(AaBarcodeRule br:rules){ i+=br.getLength(); }
			 *
			 * AaGoods aaGoods=null;
			 *
			 * boolean simple=false;//是否是简单的物品(启用了条码规则，但是物料本身没有启用自由项目的)
			 * if(aaGoods==null){ aaGoods=this.getAaGoods(barcode);
			 *
			 * if(aaGoods!=null){ simple=true; } }
			 *
			 * if(aaGoods==null){ aaGoods=this.getAaGoods(barcode.substring(0,
			 * barcode.length()-i));//检测是否是物品编码 }
			 */

            AaGoods aaGoods = null;
            String[] barcodes = null;
            if (barcode.contains("&")) {// 物品启用了自由项
                barcodes = barcode.split("&");
                aaGoods = this.getAaGoods(barcodes[0]);
            } else {// 物品未启用自由项
                aaGoods = this.getAaGoods(barcode);
            }

            if (CommonUtil.isNullObject(aaGoods)) {
                goodsInforRsp.setSuccess(0);
                goodsInforRsp.setFailInfor("物料编码不存在！");

            } else {

				/*
                 * 存在如下bug
				 * 物品1启用了自由项，物料条码+自由项=abc01;物品2没有启用自由项，物料条码=abc01。此时如果扫码abc01
				 * ,是无法解析获取的是物料1还是物料2。 跟实施确定如果启用了自由项，中间加&符，即物料1=abc&01 数据表
				 * aa_barcoderule 暂时不用 Class<?> gif=goodsInforRsp.getClass();
				 * Field[] field=gif.getDeclaredFields();
				 *
				 * int goodCodeLength=aaGoods.getGoodscode().length();
				 * for(AaBarcodeRule br:rules){ int start=0; int end=0; String
				 * cfree=""; for(Field f:field){
				 * if(f.getName().equalsIgnoreCase(br.getBarcodeName())){ String
				 * setName="set"+f.getName().substring(0,
				 * 1).toUpperCase()+f.getName().substring(1,
				 * f.getName().length()); Method method=gif.getMethod(setName,
				 * f.getType());
				 *
				 * end+=br.getLength();
				 * cfree=barcode.substring(goodCodeLength+start,
				 * goodCodeLength+end); start+=br.getLength();
				 *
				 * for(AaUserDefine d:aud){
				 * if(cfree.equalsIgnoreCase(d.getCode())){ cfree=d.getValue();
				 * break; } }
				 *
				 * method.invoke(goodsInforRsp, cfree);
				 *
				 * break; } }
				 *
				 * if(simple){ break; } }
				 */

                goodsInforRsp.setSuccess(1);
                // TODO 后期可以做反射
                if (barcodes != null) {
                    AaUserDefine ad = this.getAaUserDefine(barcodes[1]);
                    goodsInforRsp.setCfree1(ad.getValue());
                }

                goodsInforRsp.setGoodsCode(aaGoods.getGoodscode());
            }

        } else if (barcodeType.equalsIgnoreCase("a")) {
            AaGoodsallocation aaGoodsallocation = null;

            aaGoodsallocation = this.getAaGoodsallocation(barcode);// 检测是否是货位条码
            if (CommonUtil.isNullObject(aaGoodsallocation)) {
                aaGoodsallocation = this.getAaGoodsallocation(conbarcode);
            }

            if (CommonUtil.isNullObject(aaGoodsallocation)) {
                goodsInforRsp.setSuccess(0);
                goodsInforRsp.setFailInfor("所选货位不存在！");
                return goodsInforRsp;
            }

            if (!aaGoodsallocation.getWhcode().equalsIgnoreCase(whCode)
                    && !CommonUtil.isNullObject(whCode)) {
                goodsInforRsp.setSuccess(0);
                goodsInforRsp.setFailInfor("所选仓库与指定仓库不一致！");
                return goodsInforRsp;
            }
            goodsInforRsp.setSuccess(1);
            goodsInforRsp.setAllocationbarcode(aaGoodsallocation.getAllocationbarcode());// 货位条码
            goodsInforRsp.setGoodsAllocationCode(aaGoodsallocation.getCode());// 货位编码
            goodsInforRsp.setGoodsAllocationName(aaGoodsallocation.getName());// 货位名称
            goodsInforRsp.setGoodsAllocationUid(aaGoodsallocation.getGid());// 货位uid
            goodsInforRsp.setWhcode(aaGoodsallocation.getWhcode());// 仓库编码

        }

        return goodsInforRsp;
    }


    /**
     * @category 扫描物品条码 获得商品信息
     * 2016 2016年12月29日上午9:45:05
     * void
     * 宋银海
     */

    public WmsGoodsByScan getGoodsByBarcode(JSONObject jsonObject) throws Exception {

        String barcode = jsonObject.getString("barCode");
        String whCode = jsonObject.getString("whCode");//仓库编码

//		String conbarcode = jsonObject.getString("conbarcode");// 仓库+货位编码的组合
//		String barcodeType = jsonObject.getString("barcodeType");// 条码类型 // g(物料)，a(货位)，b(批次)

        WmsGoodsByScan goodsByScan = new WmsGoodsByScan();// 返回的对象

        AaGoods aaGoods = null;
        String[] barcodes = null;
        if (barcode.contains("&")) {// 物品启用了自由项
            barcodes = barcode.split("&");
            aaGoods = this.getAaGoods(barcodes[0]);
        } else {// 物品未启用自由项
            aaGoods = this.getAaGoods(barcode);
        }

        if (CommonUtil.isNullObject(aaGoods)) {
            goodsByScan.setSuccess(0);
            goodsByScan.setFailInfor("物料编码不存在！");

        } else {

            goodsByScan.setSuccess(1);

            AaGoods goods = cacheCtrlService.getGoods(aaGoods.getGid());


            AaGoodsallocation aaGoodsallocation = wareHouseDao.getAAGoodsallocationInfoByWhcode(whCode);

            List<WmAllocationstock> currentStock = wareHouseDao.getWareHouseAllocation(barcode, whCode, aaGoodsallocation.getCode());


            goodsByScan.setGoodsCode(goods.getGoodscode());
            goodsByScan.setGoodsName(goods.getGoodsname());
            goodsByScan.setGoodsStandard(goods.getGoodsstandard());
            goodsByScan.setGoodsUid(goods.getGid());
            goodsByScan.setGoodsUnitMainName(goods.getUnitName());
            goodsByScan.setGoodsUnitAssistName(goods.getCstComUnitName());
            goodsByScan.setInvBatch(goods.getBinvbach());
            goodsByScan.setIsInvQuality(goods.getIsInvQuality());
            goodsByScan.setMassDate(goods.getMassDate());
            goodsByScan.setWmAllocationstocks(currentStock);
            goodsByScan.setGoodsAllocationUid(aaGoodsallocation.getGid());
            goodsByScan.setTempAllocationCode(aaGoodsallocation.getCode());
            goodsByScan.setTempAllocationName(aaGoodsallocation.getName());

        }

        return goodsByScan;
    }


    public List getwarehouseList() {
        return wareHouseDao.getwarehouseList();
    }

    /**
     * @category 根据物品获得物品所在的所有仓库
     * 2016 2016年9月9日下午2:14:09
     * void
     * 宋银海
     */
    public List<Map> getwarehouseListByGoodsCode(String goodsUid) {
        List<Map> maps = wareHouseDao.getwarehouseListByGoodsCode(goodsUid);

        List<Map> resultMap = new ArrayList<Map>();

        for (Map map : maps) {
            AaGoodsallocation ag = cacheCtrlService.getGoodsAllocation(map.get("goodsAllocationUid").toString());

            Map m = new HashMap();
            m.put("whgid", ag.getWhuid());
            m.put("whcode", ag.getWhcode());
            m.put("whname", ag.getWhName());

            if (resultMap.size() == 0) {//仓库去重
                resultMap.add(m);
            } else {
                boolean toAdd = true;
                for (Map rmap : resultMap) {
                    if (ag.getWhuid().equalsIgnoreCase(rmap.get("whgid").toString())) {
                        toAdd = false;
                        break;
                    }
                }

                if (toAdd) {
                    resultMap.add(m);
                }

            }
        }
        return resultMap;
    }

    public List getGoodsLocationList(String condition) {
        return wareHouseDao.getGoodsLocationList(condition);
    }

    /**
     * @category 查询货位分页 2016 2016年6月20日下午5:14:20 PageBean 宋银海
     */
    public PageBean getGoodsLocationPage(int pageIndex, int pageSize,
                                         String whgid, String searchKey) {

        String condition = " whUid='" + whgid + "' and posend=1 ";
        if (!CommonUtil.isNullObject(searchKey)) {
            condition = condition + " and (code like '%" + searchKey
                    + "%' or name like '%" + searchKey + "%') ";
        }
        return wareHouseDao.getGoodsLocationPage(pageIndex, pageSize, condition);
    }

    //根据物品和仓库获得物品所在的所有货位
    public PageBean getGoodsLocationPage(int pageIndex, int pageSize, String whCode, String searchKey, String goodsUid) {

        String condition = " whCode='" + whCode + "' and goodsUid='" + goodsUid + "' and number>0 ";

        if (!CommonUtil.isNullObject(searchKey)) {
            condition = condition + " and goodsAllocationUid in (select gid from AA_GoodsAllocation where code like '%" + searchKey
                    + "%' or name like '%" + searchKey + "%') ";
        }

        PageBean pageBean = wareHouseDao.getwarehouseListByGoodsCode(pageIndex, pageSize, condition);

        List<Map> resultMap = new ArrayList<Map>();
        List<Map> maps = pageBean.getList();

        for (Map map : maps) {
            AaGoodsallocation ag = cacheCtrlService.getGoodsAllocation(map.get("goodsAllocationUid").toString());

            Map m = new HashMap();
            m.put("gid", ag.getGid());
            m.put("code", ag.getCode());
            m.put("name", ag.getName());
            m.put("allocationBarCode", ag.getAllocationbarcode());

            if (resultMap.size() == 0) {//仓库去重
                resultMap.add(m);
            } else {
                boolean toAdd = true;
                for (Map rmap : resultMap) {
                    if (ag.getGid().equalsIgnoreCase(rmap.get("gid").toString())) {
                        toAdd = false;
                        break;
                    }
                }

                if (toAdd) {
                    resultMap.add(m);
                }

            }
        }

        pageBean.setList(resultMap);

        return pageBean;
    }

    /**
     * @param msTaskDetailRsp
     * @return
     * @category 采购入库 2016年4月15日 下午2:54:28
     * @author 杨峥铖
     */
    public boolean addprocurehouse(WmsTaskDetailRsp msTaskDetailRsp,
                                   JSONObject jsonObject, String billCode) throws Exception {
        WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        WmProcureorder puorder = new WmProcureorder();
        if (wmtask != null) {
            if (Constants.BILLGIDSOURCE_CGD.equals(wmtask.getBillGidSource())) {
                ////单据来源：采购订单
                puorder = wareHouseDao.getBillDetail(wmtask.getBillgid());//根据单据billgid查单据
            }
        }


        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();// 仓库信息
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            if (aaGoodsallocation != null) {
                warehouse.add(aaGoodsallocation.getWhuid());
            }


        }

        // 添加采购入库 主表 子表
        List<WmPowarehouse> wmPowarehouses = new ArrayList<WmPowarehouse>();// 入库单主表
        List<WmPowarehouseC> wmPowarehouseCs = new ArrayList<WmPowarehouseC>();// 入库单子表
        WmPowarehouseC wmPowarehouseC = null;

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;
        StringBuffer sbuffer = new StringBuffer();

        String cvMIVenCode = "";//代管供应商
        Iterator<String> it = warehouse.iterator();
        while (it.hasNext()) {
            String ckGid = it.next();
            WmPowarehouse wmPowarehouse = new WmPowarehouse();// 入库单主表
            String gid = UUID.randomUUID().toString();
            wmPowarehouse.setGid(gid);
            wmPowarehouse
                    .setDocumenttypeuid("B89815B2-21B4-4CF5-9D02-B67BFF507C8E");// WM_BillType 单据类型  此gid是采购入库
//			String billcode = this.getBillId(Constants.TASKTYPE_CGRK);
            wmPowarehouse.setBillcode(billCode);
            wmPowarehouse.setBillstate("0");
            wmPowarehouse.setBilldate(new Timestamp(new Date().getTime()));
            wmPowarehouse.setRecordpersonid(msTaskDetailRsp.getUserGid());
            wmPowarehouse.setRecorddate(new Timestamp(new Date().getTime()));
            wmPowarehouse.setSobgid(msTaskDetailRsp.getSobgid());
            wmPowarehouse.setOrggid(msTaskDetailRsp.getOrggid());
            wmPowarehouse.setWhuid(ckGid);
            wmPowarehouse.setDepartmentuid(msTaskDetailRsp.getDptGid());//部门uid
            wmPowarehouse.setPersonuid(puorder.getPersonuid());//业务员uid
            wmPowarehouse.setProvideruid(puorder.getSupplieruid());//供应商uid
            wmPowarehouses.add(wmPowarehouse);

            for (WmsGoods good : goods) {

                AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    WmProcureorderC wpc = wareHouseDao.getProcureorderCDetail(good.getProcureOrderCuid());
                    wmPowarehouseC = new WmPowarehouseC();// 入库单子表
                    wmPowarehouseC.setGid(good.getGid());
                    wmPowarehouseC.setPowhuid(gid);
                    wmPowarehouseC.setMaterialuid(good.getGoodsUid());
                    wmPowarehouseC.setQuantity(good.getSubmitNum());
                    wmPowarehouseC.setAssistquantity(good.getSubmitQuantity());//辅助数量
                    wmPowarehouseC.setPrice(wpc.getOriginalnotaxprice());//不含税单价
                    wmPowarehouseC.setAmount(wpc.getOriginalnotaxprice().multiply(good.getSubmitNum()));//金额

                    //区分单据来源处理
                    if (wmtask.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_CGZJ)) {//质检单
                        wmPowarehouseC.setPoarrivalcuid(good.getProcureArrivalCuid());
                        sbuffer.append("'" + good.getProcureArrivalCuid() + "',");
                    } else if (wmtask.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_CGDH)) {//到货单
                        wmPowarehouseC.setPoarrivalcuid(good.getProcureArrivalCuid());
                        sbuffer.append("'" + good.getProcureArrivalCuid() + "',");
                    } else if (wmtask.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_CGD)) {//采购订单
                        wmPowarehouseC.setPocuid(good.getProcureOrderCuid());
                        sbuffer.append("'" + good.getProcureOrderCuid() + "',");

                    }


                    wmPowarehouseC.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmPowarehouseC.setBatchcode(good.getBatch());

                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmPowarehouseC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmPowarehouseC.setCfree2(wgc.getValue());
                        }
                    }

                    wmPowarehouseCs.add(wmPowarehouseC);

                    AaGoods aaGoods = cacheCtrlService.getGoods(good
                            .getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum());
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    wmAllocationstock.setCfree1(wmPowarehouseC.getCfree1());
                    wmAllocationstock.setCfree2(wmPowarehouseC.getCfree2());


                    if (aaWarehouse.getBproxyWh().intValue() == 1) {//为代管仓库

                        if (cvMIVenCode.equals("")) {
                            Map map = purchaseArrivalDao.getProcureArrivalByCgid(good.getProcureArrivalCuid());
                            AaProviderCustomer aaProviderCustomer = cacheCtrlService.getProviderCustomer(map.get("supplierUid").toString());
                            cvMIVenCode = aaProviderCustomer.getPccode();
                        }

                        wmAllocationstock.setCvMIVenCode(cvMIVenCode);
                    }

                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmb.setCfree1(wmPowarehouseC.getCfree1());
                        wmb.setCfree2(wmPowarehouseC.getCfree2());
                        wmBatch.add(wmb);
                    }

                }

            }

        }

        wareHouseDao.addprocureIn(wmPowarehouses);// 增加主表
        wareHouseDao.addprocureIns(wmPowarehouseCs);// 增加子表
        if (msTaskDetailRsp.getIsfinish() == 1) {
            String sql = "update WM_Task set state=2 where gid = '"
                    + msTaskDetailRsp.getTaskGid() + "'";
            wareHouseDao.update(sql);
        }
        //区分单据来源处理
        if (wmtask.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_CGZJ)) {
            // 反填质检单已入库数量
            updateSourceBillWhenSubmitWareHouse(goods, Constants.BILLGIDSOURCE_CGZJ, "in");
        } else if (wmtask.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_CGDH)) {
            // 反填到货单已入库数量
            updateSourceBillWhenSubmitWareHouse(goods, Constants.BILLGIDSOURCE_CGDH, "in");
        } else if (wmtask.getBillGidSource().equalsIgnoreCase(Constants.BILLGIDSOURCE_CGD)) {
            // 反填采购订单已入库数量
            updateSourceBillWhenSubmitWareHouse(goods, Constants.BILLGIDSOURCE_CGD, "in");
        }


        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addPoWareHouse.emi";

            String poarrivalcuid = sbuffer.toString();
            if (poarrivalcuid.length() > 0) {
                poarrivalcuid = "("
                        + poarrivalcuid
                        .substring(0, poarrivalcuid.length() - 1) + ")";
            }
            String condition = " gid in " + poarrivalcuid;
            List<WmProcurearrivalC> procurearrivalC = purchaseArrivalService
                    .getProcurearrivalCList(condition);
            jsonObject.put("mainTableIdentity", procurearrivalC.get(0)
                    .getIdForSynchro());// 将json中赋值 mainTableIdentity

            JSONArray jsonArray = jsonObject.getJSONArray("wmsGoodsLists");

            for (Object obj : jsonArray) {
                JSONObject prowctJson = (JSONObject) obj;
                for (WmProcurearrivalC pc : procurearrivalC) {
                    if (prowctJson.getString("procureArrivalCuid")
                            .equalsIgnoreCase(pc.getGid())) {
                        prowctJson.put("childTableIdentity",
                                pc.getAutoidForSynchro());
                        ;// 将json中赋值 childTableIdentity
                        break;
                    }
                }

            }
            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U8101)) {
            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u8101/wareHouse_addPoWareHouse.emi";

            String poordercuid = sbuffer.toString();
            if (poordercuid.length() > 0) {
                poordercuid = "("
                        + poordercuid
                        .substring(0, poordercuid.length() - 1) + ")";
            }
            String condition = " gid in " + poordercuid;
            List<WmProcureorderC> procureorderC = purchaseArrivalService.getProcureorderCList(condition);
            jsonObject.put("mainTableIdentity", procureorderC.get(0).getIdForSynchro());// 将json中赋值 mainTableIdentity

            JSONArray jsonArray = jsonObject.getJSONArray("wmsGoodsLists");

            for (Object obj : jsonArray) {
                JSONObject prowctJson = (JSONObject) obj;
                for (WmProcureorderC pc : procureorderC) {
                    if (prowctJson.getString("procureOrderCuid")
                            .equalsIgnoreCase(pc.getGid())) {
                        prowctJson.put("childTableIdentity",
                                pc.getAutoidForSynchro());
                        ;// 将json中赋值 childTableIdentity
                        break;
                    }
                }

            }
            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

        return true;
    }

    // 添加销售出库
    public boolean subSaleOut(WmsTaskDetailRsp wdp, JSONObject jsonObject)
            throws Exception {
        WmTask wmtask = taskDao.getTask(wdp.getTaskGid());

        List<WmsGoods> goods = wdp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService
                    .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(aaGoodsallocation.getWhuid());
        }

        Iterator<String> it = warehouse.iterator();
        WmSalesend wss = new WmSalesend();
        if (wmtask.getBillGidSource().equalsIgnoreCase(
                Constants.BILLGIDSOURCE_XSZJ)) {
            String condition = "c.gid='" + wdp.getBillUid() + "'";
            List<Map> maps = saleDao.getSaleSendCbyCheck(condition);
            wss = saleDao.SaleSendDetail(maps.get(0).get("ssgid").toString());
        } else {
            wss = saleDao.SaleSendDetail(wdp.getBillUid());
        }

        // 判断新建 出库单 出库单中要注意货位和仓库 不同仓库 不同主表
        List<WmSaleout> wsolist = new ArrayList<WmSaleout>();
        List<WmSaleoutC> wsoclist = new ArrayList<WmSaleoutC>();

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;

        StringBuffer sbuffer = new StringBuffer();
        while (it.hasNext()) {
            String ckGid = it.next();

            WmSaleout wo = new WmSaleout();
            String muid = UUID.randomUUID().toString();
            wo.setGid(muid);
            wo.setDocumenttypeuid("7A41D669-9F38-442F-AB4C-A9937D02434F");
            wo.setWhuid(ckGid);
            wo.setCustomeruid(wss.getCustomeruid());
            wo.setBillcode(this.getBillId(Constants.TASKTYPE_XSCK));
            wo.setBillstate("0");
            wo.setBilldate(new Date());
            wo.setRecordperson(wdp.getUserGid());
            wo.setRecorddate(new Date());
            wo.setRecordtime(new Date());
            wo.setIsreturn(wss.getIsreturn());
            wo.setOrggid(wdp.getOrggid());
            wo.setSobgid(wdp.getSobgid());
            wsolist.add(wo);

            for (WmsGoods good : goods) {
                AaGoodsallocation aaGoodsallocation = cacheCtrlService
                        .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService
                        .getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    List<WmsGoodsCfree> cfrees = good.getCfree();

                    WmSaleoutC wmsc = new WmSaleoutC();
                    wmsc.setGid(good.getGid());
                    wmsc.setSaleoutuid(muid);
                    wmsc.setGoodsuid(good.getGoodsUid());
                    wmsc.setNumber(good.getSubmitNum());
                    wmsc.setAssistNumber(good.getSubmitQuantity());
                    wmsc.setSalesendcuid(good.getSaleSendCuid());
                    wmsc.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmsc.setBatchcode(good.getBatch());
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmsc.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmsc.setCfree2(wgc.getValue());
                        }
                    }

                    wsoclist.add(wmsc);

                    sbuffer.append("'" + good.getSaleSendCuid() + "',");
                    AaGoods aaGoods = cacheCtrlService.getGoods(good
                            .getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum().negate());// 相反
                    wmAllocationstock.setAssistnum(CommonUtil.isNullObject(good
                            .getSubmitQuantity()) ? new BigDecimal(0) : good
                            .getSubmitQuantity().negate());// 相反
                    wmAllocationstock.setCfree1(wmsc.getCfree1());
                    wmAllocationstock.setCfree2(wmsc.getCfree2());
                    wmAllocationstock.setSobgid(wdp.getSobgid());
                    wmAllocationstock.setOrggid(wdp.getOrggid());
                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        wmb.setCfree1(wmsc.getCfree1());
                        wmb.setCfree2(wmsc.getCfree2());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmBatch.add(wmb);
                    }
                }

            }

        }

        saleDao.emiInsert(wsolist);
        saleDao.emiInsert(wsoclist);

        if (wdp.getIsfinish() == 1) {
            String sql = "update WM_Task set state=2 where gid = '"
                    + wdp.getTaskGid() + "'";
            wareHouseDao.update(sql);
        }
        // 反填到货单已出库数量
        updateSourceBillWhenSubmitWareHouse(wdp.getWmsGoodsLists(),
                Constants.BILLGIDSOURCE_XSFH, "out");

        // 反填质检单已出库数量
        if (wmtask.getBillGidSource().equalsIgnoreCase(
                Constants.BILLGIDSOURCE_XSZJ)) {
            updateSourceBillWhenSubmitWareHouse(goods,
                    Constants.BILLGIDSOURCE_XSZJ, "out");
        }
        ;

        // //更新货位现存量
        // for(WmAllocationstock wa:asList){
        // wa.setNumber(BigDecimal.valueOf(0).subtract(wa.getNumber()));
        // wa.setAssistnum(BigDecimal.valueOf(0).subtract(CommonUtil.isNullObject(wa.getAssistnum())?BigDecimal.valueOf(0):wa.getAssistnum()));
        // }

        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addSaleOut.emi";

            String saleSendcuid = sbuffer.toString();
            if (saleSendcuid.length() > 0) {
                saleSendcuid = "("
                        + saleSendcuid.substring(0, saleSendcuid.length() - 1)
                        + ")";
            }
            String condition = " gid in " + saleSendcuid;
            List<WmSalesendC> salesendC = saleDao
                    .getSaleSendCbySaleSendGid(condition);

            jsonObject.put("mainTableIdentity", salesendC.get(0)
                    .getIdForSynchro());// 将json中赋值 mainTableIdentity

            JSONArray jsonArray = jsonObject.getJSONArray("wmsGoodsLists");

            for (Object obj : jsonArray) {
                JSONObject prowctJson = (JSONObject) obj;
                for (WmSalesendC sc : salesendC) {
                    if (prowctJson.getString("saleSendCuid").equalsIgnoreCase(
                            sc.getGid())) {
                        prowctJson.put("childTableIdentity",
                                sc.getAutoidForSynchro());
                        ;// 将json中赋值 childTableIdentity
                        break;
                    }
                }

            }

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }

        } else if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_TPLUSE)) {

            String urls = "http://" + Config.INTERFACEADDRESS + "/tplus/wareHouse_addSaleOut.emi";

//			String saleSendcuid = sbuffer.toString();
//			if (saleSendcuid.length() > 0) {
//				saleSendcuid = "("+ saleSendcuid.substring(0, saleSendcuid.length() - 1)+ ")";
//			}
//			String condition = " gid in " + saleSendcuid;
//			List<WmSalesendC> salesendC = saleDao.getSaleSendCbySaleSendGid(condition);
//
//			jsonObject.put("mainTableIdentity", salesendC.get(0).getIdForSynchro());// 将json中赋值 mainTableIdentity
//
//			JSONArray jsonArray = jsonObject.getJSONArray("wmsGoodsLists");
//
//			for (Object obj : jsonArray) {
//				JSONObject prowctJson = (JSONObject) obj;
//				for (WmSalesendC sc : salesendC) {
//					if (prowctJson.getString("saleSendCuid").equalsIgnoreCase(sc.getGid())) {
//						prowctJson.put("childTableIdentity",sc.getAutoidForSynchro());// 将json中赋值 childTableIdentity
//						break;
//					}
//				}
//
//			}

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }


        }

        return true;

    }

    // 合并相同的物料，返回新的货位现存量表
    public List<WmAllocationstock> getNewCurrentStockList(
            List<WmAllocationstock> css) {
        List<WmAllocationstock> csnew = new ArrayList<WmAllocationstock>();

        for (WmAllocationstock csold : css) {
            boolean toAdd = true;
            for (WmAllocationstock csn : csnew) {
                if (csold.equals(csn)) {
                    csn.setNumber(CommonUtil.bigDecimal2BigDecimal(
                            csn.getNumber())
                            .add(CommonUtil.bigDecimal2BigDecimal(csold
                                    .getNumber())));
                    csn.setAssistnum(CommonUtil.bigDecimal2BigDecimal(
                            csn.getAssistnum()).add(
                            CommonUtil.bigDecimal2BigDecimal(csold
                                    .getAssistnum())));

                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                csnew.add(csold);
            }
        }

        return csnew;
    }

    /**
     * @category 成品入库 2016 2016年5月12日上午9:24:48 boolean 宋银海
     */
    public boolean addProductionWarehouse(WmsTaskDetailRsp msTaskDetailRsp,
                                          JSONObject jsonObject, String billCode) throws Exception {
        WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();// 仓库信息
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService
                    .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(aaGoodsallocation.getWhuid());
        }

        // 添加成品入库 主表 子表
        List<WmProductionwarehouse> wmPowarehouses = new ArrayList<WmProductionwarehouse>();// 入库单主表
        List<WmProductionwarehouseC> wmPowarehouseCs = new ArrayList<WmProductionwarehouseC>();// 入库单子表
        WmProductionwarehouseC wmProductionwarehouseC = null;

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;

        Iterator<String> it = warehouse.iterator();
        while (it.hasNext()) {
            String ckGid = it.next();
            WmProductionwarehouse wmProductionwarehouse = new WmProductionwarehouse();// 入库单主表
            String gid = UUID.randomUUID().toString();
            wmProductionwarehouse.setGid(gid);
            wmProductionwarehouse.setDocumenttypeid("C8A7F95F-4098-411B-92B4-5426E55D8A60");// WM_BillType
//			String billcode = this.getBillId(Constants.TASKTYPE_CPRK);
            wmProductionwarehouse.setBillcode(billCode);
            wmProductionwarehouse.setBillstate("0");
            wmProductionwarehouse.setBilldate(new Timestamp(new Date().getTime()));
            wmProductionwarehouse.setRecordpersonid(msTaskDetailRsp.getUserGid());
            wmProductionwarehouse.setRecorddate(new Timestamp(new Date().getTime()));
            wmProductionwarehouse.setSobgid(msTaskDetailRsp.getSobgid());
            wmProductionwarehouse.setOrggid(msTaskDetailRsp.getOrggid());
            wmProductionwarehouse.setWhuid(ckGid);
            wmPowarehouses.add(wmProductionwarehouse);

            for (WmsGoods good : goods) {

                AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    List<WmsGoodsCfree> cfrees = good.getCfree();

                    wmProductionwarehouseC = new WmProductionwarehouseC();// 入库单子表
                    wmProductionwarehouseC.setGid(good.getGid());
                    wmProductionwarehouseC.setProUid(gid);
                    wmProductionwarehouseC.setGoodsUid(good.getGoodsUid());
                    wmProductionwarehouseC.setNumber(good.getSubmitNum());
                    wmProductionwarehouseC.setAssistNumber(good.getSubmitQuantity());
                    wmProductionwarehouseC.setProduceProcessRouteCGid(good.getProduceRouteCUid());
                    wmProductionwarehouseC.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmProductionwarehouseC.setBatch(good.getBatch());
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmProductionwarehouseC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmProductionwarehouseC.setCfree2(wgc.getValue());
                        }
                    }
                    wmPowarehouseCs.add(wmProductionwarehouseC);

                    AaGoods aaGoods = cacheCtrlService.getGoods(good
                            .getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation.getCode());
                    wmAllocationstock.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum());
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity());
                    wmAllocationstock.setCfree1(wmProductionwarehouseC.getCfree1());
                    wmAllocationstock.setCfree2(wmProductionwarehouseC.getCfree2());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmb.setCfree1(wmProductionwarehouseC.getCfree1());
                        wmb.setCfree2(wmProductionwarehouseC.getCfree2());
                        wmBatch.add(wmb);
                    }

                }

            }

        }

        wareHouseDao.addProductionWarehouse(wmPowarehouses);// 增加主表
        wareHouseDao.addProductionWarehousec(wmPowarehouseCs);// 增加子表

        if (msTaskDetailRsp.getIsfinish() == 1) {
            String sql = "update WM_Task set state=2 where gid = '"
                    + msTaskDetailRsp.getTaskGid() + "'";
            wareHouseDao.update(sql);
        }
        // 反填订单工艺路线已入库数量
        updateSourceBillWhenSubmitWareHouse(goods, Constants.BILLGIDSOURCE_SCDDGY, "in");

        //反填报工单子表已入库数量
        updateSourceBillWhenSubmitWareHouse(goods, Constants.BILLGIDSOURCE_BGD, "");

        // 反填订单完工数量
        String con = " gid='" + goods.get(0).getProduceRouteCUid() + "'";
        MesWmProduceProcessroutec mppc = produceOrderDao.getMesWmProduceProcessroutec(con);
        con = " gid='" + mppc.getProduceRouteGid() + "'";
        MesWmProduceProcessroute mwpp = produceOrderDao.getMesWmProduceProcessroute(con);// 工艺路线主表
        updateProduceBillIn(goods, mwpp.getProduceCuid());

        // 反填质检单已入库数量
        if (!CommonUtil.isNullObject(goods.get(0).getCheckCuid())) {
            updateSourceBillWhenSubmitWareHouse(goods, Constants.BILLGIDSOURCE_CPZJ, "");
        }

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addProductionWarehouse.emi";

            String condition = " pprc.gid ='"
                    + goods.get(0).getProduceRouteCUid() + "'";
            Map orderInfor = produceOrderDao
                    .getOrderInforByProduceProcessroutec(condition);

            jsonObject.put("mainTableIdentity",
                    orderInfor.get("autoidForSynchro"));// 将json中赋值
            // mom_orderdetail MoDId

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else if (Config.INTERFACETYPE
                .equalsIgnoreCase(Constants.INTERFACE_TPLUSE)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/tplus/wareHouse_addProductionWarehouse.emi";

            String condition = " pprc.gid ='"
                    + goods.get(0).getProduceRouteCUid() + "'";
            Map orderInfor = produceOrderDao
                    .getOrderInforByProduceProcessroutec(condition);

            jsonObject.put("mainTableIdentity",
                    orderInfor.get("autoidForSynchro"));// 将json中赋值
            // mom_orderdetail MoDId

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

        return true;
    }


    /**
     * @category 成品入库（直接入库 无上游单据） 2016 2016年5月12日上午9:24:48 boolean 宋银海
     */
    public boolean addProductionWarehouseDirect(WmsTaskDetailRsp msTaskDetailRsp,
                                                JSONObject jsonObject) throws Exception {
        WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();// 仓库信息
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService
                    .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(aaGoodsallocation.getWhuid());
        }

        // 添加成品入库 主表 子表
        List<WmProductionwarehouse> wmPowarehouses = new ArrayList<WmProductionwarehouse>();// 入库单主表
        List<WmProductionwarehouseC> wmPowarehouseCs = new ArrayList<WmProductionwarehouseC>();// 入库单子表
        WmProductionwarehouseC wmProductionwarehouseC = null;

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;

        Iterator<String> it = warehouse.iterator();
        while (it.hasNext()) {
            String ckGid = it.next();
            WmProductionwarehouse wmProductionwarehouse = new WmProductionwarehouse();// 入库单主表
            String gid = UUID.randomUUID().toString();
            wmProductionwarehouse.setGid(gid);
            wmProductionwarehouse.setDocumenttypeid("C8A7F95F-4098-411B-92B4-5426E55D8A60");// WM_BillType
            String billcode = this.getBillId(Constants.TASKTYPE_CPRK);
            wmProductionwarehouse.setBillcode(billcode);
            wmProductionwarehouse.setBillstate("0");
            wmProductionwarehouse.setBilldate(new Timestamp(new Date().getTime()));
            wmProductionwarehouse.setRecordpersonid(msTaskDetailRsp.getUserGid());
            wmProductionwarehouse.setRecorddate(new Timestamp(new Date().getTime()));
            wmProductionwarehouse.setSobgid(msTaskDetailRsp.getSobgid());
            wmProductionwarehouse.setOrggid(msTaskDetailRsp.getOrggid());
            wmProductionwarehouse.setWhuid(ckGid);
            wmPowarehouses.add(wmProductionwarehouse);

            for (WmsGoods good : goods) {

                AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    List<WmsGoodsCfree> cfrees = good.getCfree();

                    wmProductionwarehouseC = new WmProductionwarehouseC();// 入库单子表
                    wmProductionwarehouseC.setGid(good.getGid());
                    wmProductionwarehouseC.setProUid(gid);
                    wmProductionwarehouseC.setGoodsUid(good.getGoodsUid());
                    wmProductionwarehouseC.setNumber(good.getSubmitNum());
                    wmProductionwarehouseC.setAssistNumber(good.getSubmitQuantity());
                    wmProductionwarehouseC.setProduceProcessRouteCGid(good.getProcureArrivalCuid());
                    wmProductionwarehouseC.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmProductionwarehouseC.setBatch(good.getBatch());
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmProductionwarehouseC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmProductionwarehouseC.setCfree2(wgc.getValue());
                        }
                    }
                    wmPowarehouseCs.add(wmProductionwarehouseC);

                    AaGoods aaGoods = cacheCtrlService.getGoods(good
                            .getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation.getCode());
                    wmAllocationstock.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum());
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity());
                    wmAllocationstock.setCfree1(wmProductionwarehouseC.getCfree1());
                    wmAllocationstock.setCfree2(wmProductionwarehouseC.getCfree2());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmb.setCfree1(wmProductionwarehouseC.getCfree1());
                        wmb.setCfree2(wmProductionwarehouseC.getCfree2());
                        wmBatch.add(wmb);
                    }

                }

            }

        }

        wareHouseDao.addProductionWarehouse(wmPowarehouses);// 增加主表
        wareHouseDao.addProductionWarehousec(wmPowarehouseCs);// 增加子表

        // 反填订单工艺路线已入库数量

        //反填报工单子表已入库数量

        // 反填订单完工数量

        // 反填质检单已入库数量

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addProductionWarehouse.emi";

            String condition = " pprc.gid ='"
                    + goods.get(0).getProduceRouteCUid() + "'";
            Map orderInfor = produceOrderDao
                    .getOrderInforByProduceProcessroutec(condition);

            jsonObject.put("mainTableIdentity",
                    orderInfor.get("autoidForSynchro"));// 将json中赋值
            // mom_orderdetail MoDId

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else if (Config.INTERFACETYPE
                .equalsIgnoreCase(Constants.INTERFACE_TPLUSE)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/tplus/wareHouse_addProductionWarehouseDirect.emi";

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

        return true;
    }


    /**
     * @param msTaskDetailRsp
     * @return
     * @category 采购入库 2016年4月15日 下午2:54:28
     * @author 杨峥铖
     */
    public boolean addcheckbill(WmsTaskDetailRsp msTaskDetailRsp) {

        // //添加采购入库 主表 子表
        // WmPowarehouse wmPowarehouse=new WmPowarehouse();//入库单主表
        // String gid=UUID.randomUUID().toString();
        // wmPowarehouse.setGid(gid);
        // wmPowarehouse.setDocumenttypeuid("B89815B2-21B4-4CF5-9D02-B67BFF507C8E");//WM_BillType
        // String billcode=this.getBillId(Constants.TASKTYPE_CGRK);
        // wmPowarehouse.setBillcode(billcode);
        // wmPowarehouse.setBillstate("0");
        // //wmPowarehouse.setBilldate(new Date());
        // wmPowarehouse.setRecordpersonid(msTaskDetailRsp.getUserGid());
        // //wmPowarehouse.setRecorddate(new Date());
        // //wmPowarehouse.setSobgid(sobgid);
        // //wmPowarehouse.setOrggid(orggid);
        //
        // List<WmsGoods> goods=msTaskDetailRsp.getWmsGoodsLists();
        // List<WmPowarehouseC> wmPowarehouseCs=new
        // ArrayList<WmPowarehouseC>();//入库单子表
        // WmPowarehouseC wmPowarehouseC=null;
        // String goodsAllocationUid=null;
        //
        // List<WmAllocationstock> asList=new
        // ArrayList<WmAllocationstock>();//货位现存量
        // WmAllocationstock wmAllocationstock=null;
        //
        // List<WmBatch> wmBatch=new ArrayList<WmBatch>();//批次
        // WmBatch wmb=null;
        //
        // for(WmsGoods good:goods){
        // wmPowarehouseC=new WmPowarehouseC();
        // wmPowarehouseC.setGid(UUID.randomUUID().toString());
        // wmPowarehouseC.setPowhuid(gid);;
        // wmPowarehouseC.setMaterialuid(good.getGoodsUid());
        // wmPowarehouseC.setQuantity(good.getSubmitNum());
        // wmPowarehouseC.setPoarrivalcuid(good.getProcureArrivalCuid());
        // wmPowarehouseC.setGoodsallocationuid(good.getGoodsAllocationUid());
        // wmPowarehouseC.setBatchcode(good.getBatch());
        // wmPowarehouseCs.add(wmPowarehouseC);
        //
        // if(goodsAllocationUid==null){
        // goodsAllocationUid=good.getGoodsAllocationUid();
        // }
        //
        // wmAllocationstock=new WmAllocationstock();
        // wmAllocationstock.setGoodsuid(good.getGoodsUid());
        // wmAllocationstock.setGoodsallocationuid(good.getGoodsAllocationUid());
        // wmAllocationstock.setBatch(good.getBatch());
        // asList.add(wmAllocationstock);
        //
        // if(!CommonUtil.isNullObject(good.getBatch())){
        // wmb=new WmBatch();
        // wmb.setGid(UUID.randomUUID().toString());
        // wmb.setGoodsUid(good.getGoodsUid());
        // wmb.setGoodsAllocationUid(goodsAllocationUid);
        // wmb.setBatch(good.getBatch());
        // if(good.getSubmitNum().doubleValue()>0){
        // wmb.setRedBlueFlag(0);
        // }else{
        // wmb.setRedBlueFlag(0);
        // }
        // wmb.setRecordDate(new Timestamp(new Date().getTime()));
        // wmBatch.add(wmb);
        // }
        //
        // }
        //
        // AaWarehouse aaWarehouse =this.getAaWarehouse(goodsAllocationUid);
        // wmPowarehouse.setWhuid(aaWarehouse.getGid());
        // wareHouseDao.addprocureIn(wmPowarehouse);//增加主表
        // wareHouseDao.addprocureIns(wmPowarehouseCs);//增加子表
        // if(msTaskDetailRsp.getIsfinish()==1){
        // String sql =
        // "update WM_Task set state=2 where gid = '"+msTaskDetailRsp.getTaskGid()+"'";
        // wareHouseDao.update(sql);
        // }
        // //反填生产订单工艺材料表已出库数量
        // updateSourceBillWhenSubmitWareHouse(goods,
        // Constants.BILLGIDSOURCE_CGDH);
        //
        // //更新货位现存量
        // updateStocksEntity(asList);
        //
        // //增加批次
        // if(wmBatch.size()>0){
        // wareHouseDao.addWmBatch(wmBatch);
        // }
        //
        return true;
    }

    /**
     * @category 出入库类别 2016 2016年4月22日下午2:08:01 List<YmRdStyle> 宋银海
     */
    public List<YmRdStyle> getRdstyleEntity(String condition, String taskType) {

        if (taskType.equalsIgnoreCase(Constants.TASKTYPE_WWCLCK)) {//委外材料出库
            condition += " and usedInWWCLCK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_CGRK) || taskType.equalsIgnoreCase(Constants.TASKTYPE_CGTHCK)) {//采购入库
            condition += " and usedInCGRK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_DBD)) {//调拨出库
            condition += " and usedInDBCK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_DBRK)) {//调拨入库
            condition += " and usedInDBRK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_GXTL) || taskType.equalsIgnoreCase(Constants.TASKTYPE_GXLL) || taskType.equalsIgnoreCase(Constants.TASKTYPE_CLCK)) {//生产材料出
            condition += " and usedInCLCK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_XSCK) || taskType.equalsIgnoreCase(Constants.TASKTYPE_XSTHRK)) {//销售出库
            condition += " and usedInXSCK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_CPRK)) {//成品入库
            condition += " and usedInCPRK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_WWCPRK)) {//委外成品入库
            condition += " and usedInWWCPRK=1 ";
        } else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_LYSQ)) {//领用申请单类型
            condition += " and usedInLYSQ=1 ";
        }else if (taskType.equalsIgnoreCase(Constants.TASKTYPE_BFD)) {//报废单类型
            condition += " and usedInBFD=1 ";
        }


        return wareHouseDao.getRdstyleEntity(condition);
    }

    public Map getUserWareHouse(String userId, String flag, String findtype) {
        String findsql = "SELECT ";
        String aafindsql = "select";
        if (findtype.equals("W")) {
            findsql += CommonUtil.colsFromBean(AaWarehouse.class);
        } else {
            findsql += CommonUtil.colsFromBean(AaGoods.class);
        }
        findsql += " from AA_WareHouse";

        Map reqmap = new HashMap();
        if (!flag.equals("all")) {
            // is user
            findsql += " where  gid in ( select dataGid from  RM_RoleData where roleId in (SELECT roleId from RM_RoleUser where userId='"
                    + userId + "') ";
            findsql += " and dataType='" + findtype + "')";
        }

        if (findtype.equals("A")) {
            findsql = "select "
                    + CommonUtil.colsFromBean(AaGoodsallocation.class)
                    + " from AA_GoodsAllocation ";
            findsql += " where  whUid in ( select dataGid from  RM_RoleData where roleId in (SELECT roleId from RM_RoleUser where userId='"
                    + userId + "') ";
            findsql += " and dataType='W')";
        }

        findsql += " order by whcode ";

        List relist = wareHouseDao.getWareHouse(findsql, findtype);
        reqmap.put("data", relist);
        return reqmap;
    }

    public Map getWareHouseAllocation(String barcode, String whcode, String allocationBarcode) {

        Map reqmap = new HashMap();
        AaGoods aaGoods = null;
        String[] barcodes = null;
        if (barcode.contains("&")) {// 物品启用了自由项
            barcodes = barcode.split("&");
            aaGoods = this.getAaGoods(barcodes[0]);
        } else {// 物品未启用自由项
            aaGoods = this.getAaGoods(barcode);
        }

        String condition = "";
        if (CommonUtil.isNullObject(aaGoods)) {
            reqmap.put("success", "0");
            reqmap.put("failInfor", "物料不存在！");
            return reqmap;
        } else if (!CommonUtil.isNullObject(aaGoods)) {
            condition = " and wa.goodsuid='" + aaGoods.getGid() + "' and wa.whCode='" + whcode + "' and wa.number>0 ";

            if (!CommonUtil.isNullObject(allocationBarcode)) {

                AaGoodsallocation aaGoodsallocation = this.getAaGoodsallocation(allocationBarcode);// 检测是否是货位条码
                if (CommonUtil.isNullObject(aaGoodsallocation)) {
                    aaGoodsallocation = this.getAaGoodsallocation(whcode + allocationBarcode);
                }

                if (!CommonUtil.isNullObject(aaGoodsallocation)) {
                    condition += " and wa.goodsAllocationCode='" + aaGoodsallocation.getCode() + "' ";
                }

            }

        }

        if (barcodes != null) {
            AaUserDefine ad = this.getAaUserDefine(barcodes[1]);
            condition += " and wa.cfree1='" + ad.getValue() + "'";
        }

//		List<WmAllocationstock> list = wareHouseDao.getWareHouseAllocation(barcode, wid, allocationcode);
        List<WmAllocationstock> list = wareHouseDao.getAllocationStockBatch(condition);

        List outlist = new ArrayList();
        for (WmAllocationstock wm : list) {
            String goodsgid = wm.getGoodsuid();
            String aid = wm.getGoodsallocationuid();
            AaGoods ag = cacheCtrlService.getGoods(goodsgid);
            AaGoodsallocation wma = cacheCtrlService.getGoodsAllocation(aid);
            JSONObject jso = JSONObject.fromObject(wm);
            WmsGoodsCfree wgc = new WmsGoodsCfree();
            wgc.setIsShow(1);
            wgc.setName("工序");
            wgc.setValue(wm.getCfree1());
            wgc.setColName("cfree1");

            List<WmsGoodsCfree> cfree = new ArrayList();
            cfree.add(wgc);
            jso.put("cfree", cfree);
            jso.put("goodsname", ag.getGoodsname());
            jso.put("allocationname", wma.getName());
            jso.put("goodsUnitMainName", ag.getUnitName());
            jso.put("goodsstandard", ag.getGoodsstandard());

            if (CommonUtil.isNullObject(ag.getCstComUnitName())) {
                jso.put("goodsUnitAssistName", "");
            } else {
                jso.put("goodsUnitAssistName", ag.getCstComUnitName());
            }

            outlist.add(jso);
        }
        reqmap.put("data", outlist);
        reqmap.put("success", "1");
        reqmap.put("failInfor", "");
        return reqmap;
    }

    //根据仓库条码查询仓库
    public Map getWareHouseByBarcode(String barcode) {
        return wareHouseDao.getWareHouseByBarcode(barcode);
    }


    public void subCallOrder(String inwid, String outwid, String orgGid,
                             String sobGid, JSONArray jsa, String userId, String note, String rdStyle) {

        wmCall wmc = new wmCall();
        String wmcuid = UUID.randomUUID().toString();
        wmc.setGid(wmcuid);
        wmc.setBillCode(this.getBillId(Constants.TASKTYPE_DBD));
        wmc.setBillState("0");
        wmc.setOutWhUid(outwid);
        wmc.setInWhUid(inwid);
        wmc.setOrgGid(orgGid);
        wmc.setSobGid(sobGid);
        wmc.setRecordPersonUid(userId);
        wmc.setRecordDate(new Date());
        wmc.setNotes(note);
        wmc.setRdStyleCode(rdStyle);
        wareHouseDao.emiInsert(wmc);
        List<wmCallC> clist = new ArrayList();
        for (int i = 0; i < jsa.size(); i++) {
            JSONObject jso = jsa.getJSONObject(i);
            wmCallC wmcc = new wmCallC();
            wmcc.setCallUid(wmcuid);
            wmcc.setGid(UUID.randomUUID().toString());
            wmcc.setBatch(jso.getString("batch"));

            JSONArray cjsa = jso.getJSONArray("cfree");
            for (int j = 0; j < cjsa.size(); j++) {
                JSONObject cjso = cjsa.getJSONObject(j);
                if (cjso.getString("colName").equalsIgnoreCase("cfree1")) {
                    wmcc.setCfree1(cjso.getString("value"));
                } else if (jso.getString("colName").equalsIgnoreCase("cfree2")) {
                    wmcc.setCfree2(cjso.getString("value"));
                }
            }
            wmcc.setGoodsUid(jso.getString("goodsuid"));
            wmcc.setOutgoodsAllocationUid(jso.getString("goodsallocationuid"));
            wmcc.setNumber(new BigDecimal(jso.getString("subnumber")));
            wmcc.setAssistNumber(new BigDecimal(jso.getString("subassistnum")));
            wmcc.setOutnumber(new BigDecimal(0));
            wmcc.setOutassistNumber(new BigDecimal(0));
            clist.add(wmcc);
        }
        wareHouseDao.emiInsert(clist);
    }

    public Map getWmCallList(String userId, int pageIndex, String whBarcode) {
        PageBean pg = wareHouseDao.getWmCallList(userId, pageIndex, whBarcode);
        Map outmap = new HashMap();
        List<wmCall> wmclist = pg.getList();
        List outlist = new ArrayList();
        for (wmCall wmc : wmclist) {
            JSONObject jso = EmiJsonObj.fromObject(wmc);
            AaWarehouse inaaw = cacheCtrlService.getWareHouse(wmc.getInWhUid());
            AaWarehouse outaaw = cacheCtrlService.getWareHouse(wmc.getOutWhUid());
            YmUser aap = cacheCtrlService.getUser(wmc.getRecordPersonUid());
            jso.put("inWhCode", inaaw.getWhcode());
            jso.put("inWhName", inaaw.getWhname());
            jso.put("outWhName", outaaw.getWhname());
            jso.put("recPersonName", aap.getUserName());
            outlist.add(jso);
        }
        Map page = new HashMap();
        page.put("pageCount", pg.getPageCount());
        page.put("totalCount", pg.getTotalCount());

        outmap.put("data", outlist);
        return outmap;
    }

    public List<Map> getWmCallDetail(String gid) {
        List<wmCallC> wmclist = wareHouseDao.getWmCallDetail(gid);
        List outlist = new ArrayList();
        for (wmCallC wmc : wmclist) {
            JSONObject jso = EmiJsonObj.fromObject(wmc);
            AaGoods ag = cacheCtrlService.getGoods(wmc.getGoodsUid());
            WmsGoodsCfree wgc = new WmsGoodsCfree();
            wgc.setIsShow(1);
            wgc.setName("工序");
            wgc.setValue(wmc.getCfree1());
            wgc.setColName("cfree1");
            List<WmsGoodsCfree> cfree = new ArrayList();
            cfree.add(wgc);
            jso.put("cfree", cfree);
            jso.put("goodsname", ag.getGoodsname());
            jso.put("goodsUnitMainName", ag.getUnitName());
            jso.put("goodsstandard", ag.getGoodsstandard());
            jso.put("goodscode", ag.getGoodscode());
            if (CommonUtil.isNullObject(ag.getCassComUnitName())) {
                jso.put("goodsUnitAssistName", "");
            } else {
                jso.put("goodsUnitAssistName", ag.getCassComUnitName());
            }
            outlist.add(jso);
        }
        return outlist;
    }

    public void subCallOrderReshep(JSONArray jsa, String isend, String billUid,
                                   String userId, String note, String orgGid, String sobGid,
                                   JSONObject jobj) throws Exception {
        wmCall wmc = wareHouseDao.getWmCallByGid(billUid);
        String jobjString = jobj.toString();
        boolean overflag = true;
        // 其他出主表
        String wmoouid = UUID.randomUUID().toString();
        WmOthersout wmoo = new WmOthersout();
        List<WmOthersoutC> wmooclist = new ArrayList();
        wmoo.setDocumentTypeUid("34B17996-5285-48EE-864B-FB3DEB218036");
        wmoo.setWarehouseUid(wmc.getOutWhUid());
        wmoo.setBillCode(this.getBillId(Constants.TASKTYPE_QTCK));
        wmoo.setBillState("0");
        wmoo.setBillDate(new Date());
        wmoo.setRecordPersonUid(userId);
        wmoo.setRecordDate(new Date());
        wmoo.setSobGid(sobGid);
        wmoo.setOrgGid(orgGid);
        wmoo.setNotes(note);

        // 其他入主表
        String wmohuid = UUID.randomUUID().toString();
        WmOtherwarehouse wmoh = new WmOtherwarehouse();
        List<WmOtherwarehouseC> wmohclist = new ArrayList();
        wmoh.setDocumentTypeId("24AD0F1F-6D94-4EE1-8728-896472A3E0C6");
        wmoh.setWhUid(wmc.getInWhUid());
        wmoh.setBillCode(this.getBillId(Constants.TASKTYPE_QTRK));
        wmoh.setBillState("0");
        wmoh.setBillDate(new Date());
        wmoh.setRecordDate(new Date());
        wmoh.setRecordPersonId(userId);
        wmoh.setSobGid(sobGid);
        wmoh.setOrgGid(orgGid);

        List<WmAllocationstock> asList = new ArrayList();
        String[] sqls = new String[jsa.size()];

        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();// 批次记录表

        for (int i = 0; i < jsa.size(); i++) {
            JSONObject jso = jsa.getJSONObject(i);
            JSONArray jsacfree = jso.getJSONArray("cfree");
            jso.discard("cfree");

            wmCallC wmcc = (wmCallC) JSONObject.toBean(jso, wmCallC.class);
            if (wmcc.getNumber().compareTo(
                    new BigDecimal(jso.getString("suboutnum"))) == 1) {
                overflag = false;// 未完成的订单
            }

            String goodsuid = wmcc.getGoodsUid();

            WmOthersoutC wmooc = new WmOthersoutC();// 其它出库子表
            wmooc.setGid(UUID.randomUUID().toString());
            wmooc.setNotes(note);
            wmooc.setOthersOutUid(wmoouid);
            wmooc.setGoodsUid(goodsuid);
            wmooc.setNumber(new BigDecimal(jso.getString("suboutnum")));
            wmooc.setCallCuid(wmcc.getGid());
            wmooc.setGoodsAllocationUid(wmcc.getOutgoodsAllocationUid());
            wmooc.setAssistNumber(new BigDecimal(jso.getString("suboutassnum")));
            wmooc.setBatch(CommonUtil.Obj2String(wmcc.getBatch()));

            WmOtherwarehouseC wmohc = new WmOtherwarehouseC();// 其它入库子表
            wmohc.setGid(UUID.randomUUID().toString());
            wmohc.setNotes(note);
            wmohc.setOtherInUid(wmohuid);
            wmohc.setGoodsUid(goodsuid);
            wmohc.setNumber(new BigDecimal(jso.getString("suboutnum")));
            wmohc.setCallCuid(wmcc.getGid());
            wmohc.setGoodsAllocationUid(wmcc.getIngoodsAllocationUid());
            wmohc.setBatch(CommonUtil.Obj2String(wmcc.getBatch()));
            wmohc.setAssistNumber(new BigDecimal(jso.getString("suboutassnum")));

            WmAllocationstock wmcat = new WmAllocationstock();// 货位现存量出
            wmcat.setBatch(wmcc.getBatch());
            AaGoodsallocation gaOut = cacheCtrlService.getGoodsAllocation(wmcc
                    .getOutgoodsAllocationUid());
            wmcat.setGoodsallocationcode(gaOut.getCode());
            wmcat.setGoodsallocationuid(wmcc.getOutgoodsAllocationUid());
            wmcat.setWhCode(gaOut.getWhcode());
            AaGoods goods = cacheCtrlService.getGoods(goodsuid);
            wmcat.setGoodscode(goods.getGoodscode());
            wmcat.setGoodsuid(goodsuid);
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(jso
                    .getString("suboutnum"))));
            wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(jso
                    .getString("suboutassnum"))));
            wmcat.setOrggid(orgGid);
            wmcat.setSobgid(sobGid);

            WmAllocationstock wmcat2 = new WmAllocationstock();// //货位现存量入
            wmcat2.setBatch(wmcc.getBatch());
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(wmcc
                    .getIngoodsAllocationUid());
            wmcat2.setGoodsallocationcode(gaIn.getCode());
            wmcat2.setGoodsallocationuid(wmcc.getIngoodsAllocationUid());
            wmcat2.setWhCode(gaIn.getWhcode());
            wmcat2.setGoodsuid(goodsuid);
            wmcat2.setGoodscode(goods.getGoodscode());
            wmcat2.setNumber(new BigDecimal(jso.getString("suboutnum")));
            wmcat2.setAssistnum(new BigDecimal(jso.getString("suboutassnum")));
            wmcat2.setOrggid(orgGid);
            wmcat2.setSobgid(sobGid);

            for (int j = 0; j < jsacfree.size(); j++) {
                JSONObject cjso = jsacfree.getJSONObject(j);
                if (cjso.getString("colName").equalsIgnoreCase("cfree1")) {
                    wmooc.setCfree1(cjso.getString("value"));
                    wmohc.setCfree1(cjso.getString("value"));
                    wmcat.setCfree1(cjso.getString("value"));
                    wmcat2.setCfree1(cjso.getString("value"));
                } else if (jso.getString("colName").equalsIgnoreCase("cfree2")) {
                    wmooc.setCfree2(cjso.getString("value"));
                    wmohc.setCfree2(cjso.getString("value"));
                    wmcat.setCfree2(cjso.getString("value"));
                    wmcat2.setCfree2(cjso.getString("value"));
                }
            }

            if (!CommonUtil.isNullObject(wmcc.getBatch())) {
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(goodsuid);
                wmb.setGoodsAllocationUid(wmcc.getOutgoodsAllocationUid());
                wmb.setBatch(wmcc.getBatch());
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(jso
                        .getString("suboutnum"))));
                wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(jso
                        .getString("suboutassnum"))));
                wmb.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
                wmb.setCfree1(wmooc.getCfree1());
                wmb.setCfree2(wmooc.getCfree2());
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);

                WmBatch wmb2 = new WmBatch();
                wmb2.setGid(UUID.randomUUID().toString());
                wmb2.setGoodsUid(goodsuid);
                wmb2.setGoodsAllocationUid(wmcc.getIngoodsAllocationUid());
                wmb2.setBatch(wmcc.getBatch());
                wmb2.setNumber(new BigDecimal(jso.getString("suboutnum")));
                wmb2.setAssistNum(new BigDecimal(jso.getString("suboutassnum")));
                wmb2.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                wmb2.setCfree1(wmooc.getCfree1());
                wmb2.setCfree2(wmooc.getCfree2());
                wmb2.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb2);

            }

            wmooclist.add(wmooc);
            wmohclist.add(wmohc);

            sqls[i] = "update WM_Call_C set outnumber='"
                    + new BigDecimal(jso.getString("suboutnum"))
                    + "',outassistNumber='"
                    + new BigDecimal(jso.getString("suboutassnum"))
                    + "' where gid='" + wmcc.getGid() + "'   ";
            asList.add(wmcat);
            asList.add(wmcat2);
        }

        if (overflag) {// 更改调拨单状态
            wareHouseDao.updateCallState(wmc.getGid());
        }
        wareHouseDao.emiInsert(wmoo);// 插入其他入主表
        wareHouseDao.emiInsert(wmooclist);// 插入其他入子表
        wareHouseDao.emiInsert(wmoh);// 插入其他出主表
        wareHouseDao.emiInsert(wmohclist);// 插入其他出子表
        wareHouseDao.batchUpdate(sqls);// 回填调拨单已出库数量

        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);// 修改货位现存量
        // 增加批次
        if (wmBatchs.size() > 0) {
            wareHouseDao.addWmBatch(wmBatchs);
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addTransVouch.emi";

            String data = "json=" + jobjString;
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jreturn = JSONObject.fromObject(s);
            if (jreturn.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }

        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

    }

    public static void main(String[] args) {

        Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.FORMAT_ONE));

        System.out.println(11);
    }

    /**
     * @category 提交委外材料出库 2016 2016年5月19日下午2:47:13 void 宋银海
     */
    public boolean subOMmain(JSONObject jboj, String billCode) {

        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("wmsGoodsLists", WmsGoods.class);
        classMap.put("cfree", WmsGoodsCfree.class);
        WmsTaskDetailRsp msTaskDetailRsp = (WmsTaskDetailRsp) JSONObject
                .toBean(jboj, WmsTaskDetailRsp.class, classMap);
        // List<OM_MOMaterials>
        // momalist=wareHouseDao.findMoMaterialsListByBillUid(wtdr.getBillUid());

        WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService
                    .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(aaGoodsallocation.getWhuid());
        }

        Iterator<String> it = warehouse.iterator();

        // 判断新建 出库单 出库单中要注意货位和仓库 不同仓库 不同主表
        List<WmMaterialout> wolist = new ArrayList<WmMaterialout>();
        List<WmMaterialoutC> woclist = new ArrayList<WmMaterialoutC>();

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;

        while (it.hasNext()) {
            String ckGid = it.next();

            // 添加材料出库主表 子表
            WmMaterialout wmMaterialout = new WmMaterialout();// 出库单主表
            String gid = UUID.randomUUID().toString();
            wmMaterialout.setGid(gid);
            wmMaterialout
                    .setDocumenttypeuid("DBA172DD-EB74-4D18-91E0-A17F90D75002");// WM_BillType
//			String billcode = this.getBillId(Constants.TASKTYPE_CLCK);// 委外材料出库
            wmMaterialout.setBillcode(billCode);
            wmMaterialout.setBilldate(new Date());
            wmMaterialout.setRecordperson(msTaskDetailRsp.getUserGid());
            wmMaterialout.setSobgid(msTaskDetailRsp.getSobgid());
            wmMaterialout.setOrggid(msTaskDetailRsp.getOrggid());
            wolist.add(wmMaterialout);

            for (WmsGoods good : goods) {
                AaGoodsallocation aaGoodsallocation = cacheCtrlService
                        .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService
                        .getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    WmMaterialoutC wmMaterialoutC = new WmMaterialoutC();
                    wmMaterialoutC.setGid(good.getGid());
                    wmMaterialoutC.setMaterialoutuid(gid);
                    wmMaterialoutC.setGoodsuid(good.getGoodsUid());
                    wmMaterialoutC.setNumber(good.getSubmitNum());
                    wmMaterialoutC.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmMaterialoutC.setBatchcode(good.getBatch());
                    wmMaterialoutC.setOmMaterialuid(good.getOmMaterialsUid());
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmMaterialoutC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmMaterialoutC.setCfree2(wgc.getValue());
                        }
                    }

                    if ((CommonUtil.isNullObject(good.getIsInvQuality()) ? 0
                            : good.getIsInvQuality().intValue()) == 1) {// 有效期

                        String condition = " cinvcode='" + good.getGoodsCode()
                                + "' and cwhcode='" + aaWarehouse.getWhcode()
                                + "' and cbatch='" + good.getBatch() + "'";
                        if (!CommonUtil
                                .isNullObject(wmMaterialoutC.getCfree1())) {
                            condition = condition + " and cfree1='"
                                    + wmMaterialoutC.getCfree1() + "'";
                        }
                        if (!CommonUtil
                                .isNullObject(wmMaterialoutC.getCfree2())) {
                            condition = condition + " and cfree2='"
                                    + wmMaterialoutC.getCfree2() + "'";
                        }
                        List<CurrentStock> cs = wareHouseDao.getAllocationStockYonYou(condition);
                        if (cs.size() == 1) {
                            //wmMaterialoutC.setDvdate(CommonUtil.isNullObject(cs.get(0).getDvDate()) ? Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00") : cs.get(0).getDvDate());
                            wmMaterialoutC.setDvdate(cs.get(0).getDvDate());
                            wmMaterialoutC.setDmadeDate(cs.get(0).getDmDate());
                            wmMaterialoutC.setImassDate(cs.get(0).getImassDate());
                        } else {
                            System.out.println("存在多个有效期的库存");
                            return false;
                        }
                    }

                    woclist.add(wmMaterialoutC);

                    AaGoods aaGoods = cacheCtrlService.getGoods(good.getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum().negate());// 相反
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity()
                            .negate());// 相反
                    wmAllocationstock.setCfree1(wmMaterialoutC.getCfree1());
                    wmAllocationstock.setCfree2(wmMaterialoutC.getCfree2());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    wmAllocationstock.setCvMIVenCode(good.getCvMIVenCode());
                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        wmb.setCfree1(wmMaterialoutC.getCfree1());
                        wmb.setCfree2(wmMaterialoutC.getCfree2());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmBatch.add(wmb);
                    }
                }

            }

        }

        wareHouseDao.addMaterialOut(wolist);// 增加主表
        wareHouseDao.addMaterialoutCs(woclist);// 增加子表

        if (msTaskDetailRsp.getIsfinish() == 1) {
            String sql = "update WM_Task set state=2 where gid = '"
                    + msTaskDetailRsp.getTaskGid() + "'";
            wareHouseDao.update(sql);
        }

        // 反填委外订单材料表已出库数量
        updateSourceBillWhenSubmitWareHouse(goods,
                Constants.BILLGIDSOURCE_WWDD, "out"); //不需要

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////回填u8

        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addOmMeterialOut.emi";

            OM_MOMain ommain = purchaseArrivalDao.getOmMainById(wmtask
                    .getBillgid());
            List<OM_MOMaterials> aaglist = purchaseArrivalDao
                    .getmOMaterialsGoods(wmtask.getBillgid());

            jboj.put("mainTableIdentity", ommain.getMoidForSynchro());// 将json中赋值
            // mainTableIdentity

            JSONArray jsonArray = jboj.getJSONArray("wmsGoodsLists");

            for (Object obj : jsonArray) {
                JSONObject prowctJson = (JSONObject) obj;
                for (OM_MOMaterials mm : aaglist) {
                    if (prowctJson.getString("omMaterialsUid")
                            .equalsIgnoreCase(mm.getGid())) {
                        prowctJson.put("childTableIdentity",
                                mm.getMoMaterialsIDForSynchro());
                        ;// 将json中赋值 childTableIdentity
                        break;
                    }
                }

            }
            String data = "json=" + jboj.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }

        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

        return true;
    }

    /**
     * @category 提交委外入库 2016年5月19日 上午10:47:37
     * @author Nixer wujinbo
     */
    public void subOMproductIn(JSONObject jsonObject, String billCode) {
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("wmsGoodsLists", WmsGoods.class);
        classMap.put("cfree", WmsGoodsCfree.class);
        WmsTaskDetailRsp msTaskDetailRsp = (WmsTaskDetailRsp) JSONObject
                .toBean(jsonObject, WmsTaskDetailRsp.class, classMap);

        WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();// 仓库信息
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService
                    .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(aaGoodsallocation.getWhuid());
        }

        // 添加采购入库 主表 子表
        List<WmPowarehouse> wmPowarehouses = new ArrayList<WmPowarehouse>();// 入库单主表
        List<WmPowarehouseC> wmPowarehouseCs = new ArrayList<WmPowarehouseC>();// 入库单子表
        WmPowarehouseC wmPowarehouseC = null;

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;
        StringBuffer sbuffer = new StringBuffer();

        String cvMIVenCode = "";
        Iterator<String> it = warehouse.iterator();
        while (it.hasNext()) {
            String ckGid = it.next();
            WmPowarehouse wmPowarehouse = new WmPowarehouse();// 入库单主表
            String gid = UUID.randomUUID().toString();
            wmPowarehouse.setGid(gid);
            wmPowarehouse
                    .setDocumenttypeuid("3CC911AD-046A-4A61-953E-329E8F3319E3");// WM_BillType
//			String billcode = this.getBillId(Constants.TASKTYPE_CGRK);
            wmPowarehouse.setBillcode(billCode);
            wmPowarehouse.setBillstate("0");
            wmPowarehouse.setBilldate(new Timestamp(new Date().getTime()));
            wmPowarehouse.setRecordpersonid(msTaskDetailRsp.getUserGid());
            wmPowarehouse.setRecorddate(new Timestamp(new Date().getTime()));
            wmPowarehouse.setSobgid(msTaskDetailRsp.getSobgid());
            wmPowarehouse.setOrggid(msTaskDetailRsp.getOrggid());
            wmPowarehouse.setWhuid(ckGid);
            wmPowarehouses.add(wmPowarehouse);

            for (WmsGoods good : goods) {

                AaGoodsallocation aaGoodsallocation = cacheCtrlService
                        .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService
                        .getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    wmPowarehouseC = new WmPowarehouseC();// 入库单子表
                    wmPowarehouseC.setGid(good.getGid());
                    wmPowarehouseC.setPowhuid(gid);
                    wmPowarehouseC.setMaterialuid(good.getGoodsUid());
                    wmPowarehouseC.setQuantity(good.getSubmitNum());
                    wmPowarehouseC.setAssistquantity(good.getSubmitQuantity());
                    wmPowarehouseC.setPoarrivalcuid(good
                            .getProcureArrivalCuid());
                    sbuffer.append("'" + good.getProcureArrivalCuid() + "',");
                    wmPowarehouseC.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmPowarehouseC.setBatchcode(good.getBatch());

                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmPowarehouseC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmPowarehouseC.setCfree2(wgc.getValue());
                        }
                    }

                    wmPowarehouseCs.add(wmPowarehouseC);

                    AaGoods aaGoods = cacheCtrlService.getGoods(good
                            .getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum());
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    wmAllocationstock.setCfree1(wmPowarehouseC.getCfree1());
                    wmAllocationstock.setCfree2(wmPowarehouseC.getCfree2());

                    if (aaWarehouse.getBproxyWh().intValue() == 1) {//为代管仓库

                        if (cvMIVenCode.equals("")) {
                            Map map = purchaseArrivalDao.getProcureArrivalByCgid(good.getProcureArrivalCuid());
                            AaProviderCustomer aaProviderCustomer = cacheCtrlService.getProviderCustomer(map.get("supplierUid").toString());
                            cvMIVenCode = aaProviderCustomer.getPccode();
                        }

                        wmAllocationstock.setCvMIVenCode(cvMIVenCode);
                    }

                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmb.setCfree1(wmPowarehouseC.getCfree1());
                        wmb.setCfree2(wmPowarehouseC.getCfree2());
                        wmBatch.add(wmb);
                    }

                }

            }

        }

        wareHouseDao.addprocureIn(wmPowarehouses);// 增加主表
        wareHouseDao.addprocureIns(wmPowarehouseCs);// 增加子表
        if (msTaskDetailRsp.getIsfinish() == 1) {
            String sql = "update WM_Task set state=2 where gid = '"
                    + msTaskDetailRsp.getTaskGid() + "'";
            wareHouseDao.update(sql);
        }
        // 反填到货单已入库数量
        updateSourceBillWhenSubmitWareHouse(goods,
                Constants.BILLGIDSOURCE_CGDH, "in");

        // 反填质检单已入库数量
        if (wmtask.getBillGidSource().equalsIgnoreCase(
                Constants.BILLGIDSOURCE_WWZJ)) {
            updateSourceBillWhenSubmitWareHouse(goods,
                    Constants.BILLGIDSOURCE_WWZJ, "in");
        }
        ;

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addOmProductIn.emi";

            String poarrivalcuid = sbuffer.toString();
            if (poarrivalcuid.length() > 0) {
                poarrivalcuid = "("
                        + poarrivalcuid
                        .substring(0, poarrivalcuid.length() - 1) + ")";
            }
            String condition = " gid in " + poarrivalcuid;
            List<WmProcurearrivalC> procurearrivalC = purchaseArrivalService
                    .getProcurearrivalCList(condition);
            jsonObject.put("mainTableIdentity", procurearrivalC.get(0)
                    .getIdForSynchro());// 将json中赋值 mainTableIdentity
            JSONArray jsonArray = jsonObject.getJSONArray("wmsGoodsLists");

            for (Object obj : jsonArray) {
                JSONObject prowctJson = (JSONObject) obj;
                for (WmProcurearrivalC pc : procurearrivalC) {
                    if (prowctJson.getString("procureArrivalCuid")
                            .equalsIgnoreCase(pc.getGid())) {
                        prowctJson.put("childTableIdentity",
                                pc.getAutoidForSynchro());
                        ;// 将json中赋值 childTableIdentity
                        break;
                    }
                }

            }
            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

    }

    /**
     * @param billcode
     * @return
     * @category 退料详情 2016年5月24日 下午1:15:28
     * @author zhuxiaochen
     */
    public List<WmsGoods> materialDetail(String billcode) {
        // 查询物料
        List<WmsGoods> materialList = wareHouseDao.materialDetail(billcode);

        // 从缓存中取物料\工序，补全属性
        String[] goodsId = new String[materialList.size()];
        String[] processId = new String[materialList.size()];
        for (int i = 0; i < materialList.size(); i++) {
            WmsGoods g = materialList.get(i);
            goodsId[i] = Constants.CACHE_GOODS + "_" + g.getGoodsUid();
            processId[i] = Constants.CACHE_MESSTANDARDPROCESS + "_"
                    + g.getProcessId();
        }
        List<AaGoods> goodsList = cacheCtrlService.getGoodsList(goodsId);
        List<MesWmStandardprocess> sp = cacheCtrlService
                .getMESStandardProcessList(processId);
        for (WmsGoods material : materialList) {
            for (AaGoods goods : goodsList) {
                if (goods.getGid().equals(material.getGoodsUid())) {
                    material.setGoodsBarCode(goods.getGoodsbarcode());
                    material.setGoodsCode(goods.getGoodscode());
                    material.setGoodsName(goods.getGoodsname());
                    material.setGoodsStandard(goods.getGoodsstandard());
                    material.setGoodsUnitMainName(goods.getUnitName());
                    material.setGoodsUnitAssistName(goods.getCstComUnitName());
                    material.setInvBatch(goods.getBinvbach());
                    material.setIsInvQuality(goods.getIsInvQuality());
//					material.setMassDate(goods.getMassDate());
                    break;
                }
            }
            for (MesWmStandardprocess p : sp) {
                if (p.getGid().equals(material.getProcessId())) {
                    material.setProcessName(p.getOpname());
                    break;
                }
            }
        }
        return materialList;
    }

    /**
     * @param wmsTaskDetailRsp
     * @param jsonObject
     * @category 退料 android 提交生产退料 数量为+ 2016年5月24日 下午5:12:20
     * @author zhuxiaochen
     */
    public boolean addProcessMaterialBack(WmsTaskDetailRsp msTaskDetailRsp,
                                          JSONObject jsonObject) {

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();
        for (WmsGoods good : goods) {
            AaGoodsallocation aaGoodsallocation = cacheCtrlService
                    .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(aaGoodsallocation.getWhuid());
        }

        Iterator<String> it = warehouse.iterator();

        // 判断新建 出库单 出库单中要注意货位和仓库 不同仓库 不同主表
        List<WmMaterialout> wolist = new ArrayList<WmMaterialout>();
        List<WmMaterialoutC> woclist = new ArrayList<WmMaterialoutC>();
        // 需要反填已退数量
        List<WmMaterialoutC> updWoclist = new ArrayList<WmMaterialoutC>();

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;

        while (it.hasNext()) {
            String ckGid = it.next();

            // 添加材料出库主表 子表
            WmMaterialout wmMaterialout = new WmMaterialout();// 出库单主表
            String gid = UUID.randomUUID().toString();
            wmMaterialout.setGid(gid);
            wmMaterialout
                    .setDocumenttypeuid("B65B212E-3C78-44CB-8F3E-43913F3664B7");// WM_BillType
            String billcode = this.getBillId(Constants.TASKTYPE_CLCK);
            wmMaterialout.setBillcode(billcode);
            wmMaterialout.setBilldate(new Date());
            wmMaterialout.setRecordperson(msTaskDetailRsp.getUserGid());
            wmMaterialout.setSobgid(msTaskDetailRsp.getSobgid());
            wmMaterialout.setOrggid(msTaskDetailRsp.getOrggid());
            wolist.add(wmMaterialout);

            for (WmsGoods good : goods) {
                AaGoodsallocation aaGoodsallocation = cacheCtrlService
                        .getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
                AaWarehouse aaWarehouse = cacheCtrlService
                        .getWareHouse(aaGoodsallocation.getWhuid());

                if (ckGid.equalsIgnoreCase(aaWarehouse.getGid())) {

                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    WmMaterialoutC wmMaterialoutC = new WmMaterialoutC();
                    wmMaterialoutC.setGid(good.getGid());
                    wmMaterialoutC.setMaterialoutuid(gid);
                    wmMaterialoutC.setGoodsuid(good.getGoodsUid());
                    wmMaterialoutC
                            .setNumber(good.getSubmitNum() == null ? new BigDecimal(
                                    0) : good.getSubmitNum().negate());
                    wmMaterialoutC
                            .setAssistNumber(good.getSubmitQuantity() == null ? new BigDecimal(
                                    0) : good.getSubmitQuantity().negate());
                    wmMaterialoutC.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmMaterialoutC.setBatchcode(good.getBatch());
                    wmMaterialoutC.setProcessRouteCGoodsUid(good
                            .getProduceRouteCGoodsUid());
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmMaterialoutC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmMaterialoutC.setCfree2(wgc.getValue());
                        }
                    }

                    woclist.add(wmMaterialoutC);

                    // 退料数量
                    WmMaterialoutC updMaterialoutC = new WmMaterialoutC();
                    updMaterialoutC.setGid(good.getMaterialOutCuid());
                    updMaterialoutC.setBackNumber(good.getSubmitNum());
                    updMaterialoutC.setBackAssistNumber(good
                            .getSubmitQuantity());

                    updWoclist.add(updMaterialoutC);

                    AaGoods aaGoods = cacheCtrlService.getGoods(good
                            .getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(aaGoodsallocation.getWhcode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock
                            .setNumber(good.getSubmitNum() == null ? new BigDecimal(
                                    0) : good.getSubmitNum());
                    wmAllocationstock
                            .setAssistnum(good.getSubmitQuantity() == null ? new BigDecimal(
                                    0) : good.getSubmitQuantity());
                    wmAllocationstock.setCfree1(wmMaterialoutC.getCfree1());
                    wmAllocationstock.setCfree2(wmMaterialoutC.getCfree2());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        wmb.setCfree1(wmMaterialoutC.getCfree1());
                        wmb.setCfree2(wmMaterialoutC.getCfree2());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum() == null ? new BigDecimal(
                                0) : good.getSubmitNum().negate());
                        wmb.setAssistNum(good.getSubmitQuantity() == null ? new BigDecimal(
                                0) : good.getSubmitQuantity().negate());
                        wmBatch.add(wmb);
                    }
                }

            }

        }

        wareHouseDao.addMaterialOut(wolist);// 增加主表
        wareHouseDao.addMaterialoutCs(woclist);// 增加子表

        // 反填生产订单工艺材料表已出库数量
        for (WmsGoods wg : goods) {
            wg.setSubmitNum(wg.getSubmitNum().negate());
            wg.setSubmitQuantity(wg.getSubmitQuantity().negate());
        }
        updateSourceBillWhenSubmitWareHouse(goods,
                Constants.BILLGIDSOURCE_SCDDGY, "out");

        // 反填出库子表已退库数量
        wareHouseDao.updateMaterialoutCs(updWoclist);

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }
        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
        // TODO

        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U890)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u890/wareHouse_addProcessMaterialOut.emi";

            String condition = " pprc.gid ='"
                    + goods.get(0).getProduceRouteCUid() + "'";
            Map orderInfor = produceOrderDao
                    .getOrderInforByProduceProcessroutec(condition);

            jsonObject.put("isBack", true);// 是否退料
            jsonObject.put("mainTableIdentity",
                    orderInfor.get("autoidForSynchro"));// 将json中赋值
            // mom_orderdetail MoDId

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else if (Config.INTERFACETYPE
                .equalsIgnoreCase(Constants.INTERFACE_TPLUSE)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/tplus/wareHouse_addProcessMaterialOut.emi";

            jsonObject.put("isBack", true);

            String data = "json=" + jsonObject.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }
        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

        return true;

    }

    // ...................................................其他入库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
    // 查询最后一条记录
    @SuppressWarnings("rawtypes")
    public Map findOtherWarehouse(String gid, String orgId, String sobId) {
        return wareHouseDao.findOtherWarehouse(gid, orgId, sobId);
    }

    // 根据主其他入库表GID查询其他入库字表
    @SuppressWarnings("rawtypes")
    public List getOtherWarehouseClist(String gid) {
        return wareHouseDao.getOtherWarehouseClist(gid);
    }

    // 添加其他入库信息
    public JSONObject addOtherWarehouse(WmOtherwarehouse wmoh,
                                        List<WmOtherwarehouseC> wmohclist, List<WmBatch> wmBatchs,
                                        List<WmAllocationstock> asList) {

        for (WmOtherwarehouseC oc : wmohclist) {
            AaGoods goods = cacheCtrlService.getGoods(oc.getGoodsUid());
            oc.setBarCode(goods.getGoodscode() + getYearMonthDay() + getSerialNumber("BARCODE"));
        }


        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        //当前没有判断有没有审批，所以直接存入审批时间为单据日期
        wmoh.setAuditDate(wmoh.getBillDate());
        boolean suc = wareHouseDao.emiInsert(wmoh);// 插入其他入主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入其他入子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }


    /**
     * @Desc 添加其他出库信息
     * @author yurh
     * @create 2018-03-19 10:50:09
     **/
    public JSONObject addOthersOut(WmOthersout wmoh, List<WmOthersoutC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList) {


        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

//当前没有判断有没有审批，所以直接存入审批时间为单据日期、
        wmoh.setAuditDate(wmoh.getBillDate());
        boolean suc = wareHouseDao.emiInsert(wmoh);// 插入其他出主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入其他出子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 更新其他入库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject updateOtherWarehouse(WmOtherwarehouse wmoh,
                                           List<WmOtherwarehouseC> wmohclist, List<WmBatch> wmBatchs,
                                           List<WmAllocationstock> asList) {
        List otherWarehouseC = wareHouseDao.getOtherWarehouseClist(wmoh
                .getGid());
        for (int i = 0; i < otherWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) otherWarehouseC.get(i)).get("goodsUid").toString());
            ((Map) otherWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batch").toString()));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) otherWarehouseC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));

            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
            }
            wmcat.setOrggid(wmoh.getOrgGid());
            wmcat.setSobgid(wmoh.getSobGid());
            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("batch"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batch").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));
                if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
                }
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()))).compareTo(new BigDecimal(0)) >= 0) {
                    wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deleteOtherWarehouseC(wmoh.getGid());
        boolean suc = wareHouseDao.emiUpdate(wmoh);// 更新其他入主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入其他入子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }


    /**
     * @Desc 修改其他入库信息
     * @author yurh
     * @create 2018-03-19 11:04:08
     **/
    public JSONObject updateOthersOut(WmOthersout wmoh,
                                      List<WmOthersoutC> wmohclist, List<WmBatch> wmBatchs,
                                      List<WmAllocationstock> asList) {
        List othersOutC = wareHouseDao.getOthersOutClist(wmoh.getGid());
        for (int i = 0; i < othersOutC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) othersOutC.get(i)).get("goodsUid").toString());
            ((Map) othersOutC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) othersOutC.get(i)).get("batch").toString()));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) othersOutC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((Map) othersOutC.get(i)).get("number").toString()));

//			if (!CommonUtil.isNullObject(((Map) othersOutC.get(i)).get("assistNumber"))) {
//				wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) othersOutC.get(i)).get("assistNumber").toString())));
//			}
            wmcat.setOrggid(wmoh.getOrgGid());
            wmcat.setSobgid(wmoh.getSobGid());
            if (!CommonUtil.isNullObject(((Map) othersOutC.get(i)).get("batch"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) othersOutC.get(i)).get("batch").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) othersOutC.get(i)).get("number").toString())));
//				if (!CommonUtil.isNullObject(((Map) othersOutC.get(i)).get("assistNumber"))) {
//					wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) othersOutC.get(i)).get("assistNumber").toString())));
//				}
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) othersOutC.get(i)).get("number").toString()))).compareTo(new BigDecimal(0)) >= 0) {
                    wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deleteOthersOutC(wmoh.getGid());

        boolean suc = wareHouseDao.emiUpdate(wmoh);// 更新其他入主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入其他入子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }


    // 删除其他入库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject deleteOtherWarehouse(WmOtherwarehouse wmoh) {
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List otherWarehouseC = wareHouseDao.getOtherWarehouseClist(wmoh
                .getGid());
        for (int i = 0; i < otherWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) otherWarehouseC
                    .get(i)).get("goodsUid").toString());
            ((Map) otherWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i))
                    .get("batch").toString()));
            AaGoodsallocation gaIn = cacheCtrlService
                    .getGoodsAllocation(((Map) otherWarehouseC.get(i)).get(
                            "goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));

            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
            }
            wmcat.setOrggid(wmoh.getOrgGid());
            wmcat.setSobgid(wmoh.getSobGid());
            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("batch"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batch").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));
                if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
                }
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()))).compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deleteOtherWarehouseC(wmoh.getGid());// 删除其他字表
        wareHouseDao.deleteOtherWarehouse(wmoh.getGid());// 删除其他入库主表
//		List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        boolean suc = updateStocksEntity(newAsList);// 修改货位现存量
        if (suc) {
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 其他入库列表
    public PageBean getOtherWarehouseList(int pageIndex, int pageSize,
                                          String condition) {
        return wareHouseDao.getOtherWarehouseList(pageIndex, pageSize,
                condition);
    }

    // ...................................................生产入库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
    // 查询最后一条记录
    @SuppressWarnings("rawtypes")
    public Map findProduceWarehouse(String gid, String orgId, String sobId) {
        return wareHouseDao.findProduceWarehouse(gid, orgId, sobId);
    }

    // 根据主生产入库表GID查询生产入库字表
    @SuppressWarnings("rawtypes")
    public List getProduceWarehouseClist(String gid) {
        return wareHouseDao.getProduceWarehouseClist(gid);
    }

    // 添加生产入库信息
    public JSONObject addProduceWarehouse(WmProductionwarehouse wmoh,
                                          List<WmProductionwarehouseC> wmohclist, List<WmBatch> wmBatchs,
                                          List<WmAllocationstock> asList) {

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }


        boolean suc = wareHouseDao.emiInsert(wmoh);// 插入生产入库主表

        for (WmProductionwarehouseC oc : wmohclist) {
            AaGoods goods = cacheCtrlService.getGoods(oc.getGoodsUid());
            oc.setBarcode(goods.getGoodscode() + getYearMonthDay() + getSerialNumber("BARCODE"));
        }

        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入生产入库子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    public WmProductionwarehouseC findWmProductionwarehouseC(String gid) {
        return wareHouseDao.findWmProductionwarehouseC(gid);
    }

    // 更新生产入库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject updateProduceWarehouse(WmProductionwarehouse wmoh,
                                             List<WmProductionwarehouseC> wmohclist, List<WmBatch> wmBatchs,
                                             List<WmAllocationstock> asList) {
        List otherWarehouseC = wareHouseDao.getProduceWarehouseClist(wmoh
                .getGid());
        for (int i = 0; i < otherWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) otherWarehouseC
                    .get(i)).get("goodsUid").toString());
            ((Map) otherWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.isNullObject(((Map) otherWarehouseC
                    .get(i)).get("batch")) ? null : ((Map) otherWarehouseC
                    .get(i)).get("batch").toString());
            AaGoodsallocation gaIn = cacheCtrlService
                    .getGoodsAllocation(((Map) otherWarehouseC.get(i)).get(
                            "goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));

            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
            }
            wmcat.setOrggid(wmoh.getOrggid());
            wmcat.setSobgid(wmoh.getSobgid());
            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("batch"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batch").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));
                if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
                }
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()))).compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }


        wareHouseDao.deleteProduceWarehouseC(wmoh.getGid());
        boolean suc = wareHouseDao.emiUpdate(wmoh);// 更新生产主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入生产子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 删除生产入库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject deleteProduceWarehouse(WmProductionwarehouse wmoh) {
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List otherWarehouseC = wareHouseDao.getProduceWarehouseClist(wmoh
                .getGid());
        for (int i = 0; i < otherWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) otherWarehouseC
                    .get(i)).get("goodsUid").toString());
            ((Map) otherWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batch")));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) otherWarehouseC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));

            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
            }
            wmcat.setOrggid(wmoh.getOrggid());
            wmcat.setSobgid(wmoh.getSobgid());
            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("batch"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batch").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString())));
                if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
                }
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()))).compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deleteProduceWarehouseC(wmoh.getGid());// 删除生产字表
        wareHouseDao.deleteProduceWarehouse(wmoh.getGid());// 删除生产入库主表
//		List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        boolean suc = updateStocksEntity(newAsList);// 修改货位现存量
        if (suc) {
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 生产分页
    public PageBean getProduceWarehouseList(int pageIndex, int pageSize,
                                            String condition) {
        return wareHouseDao.getProduceWarehouseList(pageIndex, pageSize,
                condition);
    }

    // ...................................................采购入库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
    // 查询最后一条记录
    @SuppressWarnings("rawtypes")
    public Map findPoWarehouse(String gid, String orgId, String sobId) {
        return wareHouseDao.findPoWarehouse(gid, orgId, sobId);
    }

    // 根据主采购入库表GID查询生产入库字表
    @SuppressWarnings("rawtypes")
    public List getPoWarehouseClist(String gid) {
        return wareHouseDao.getPoWarehouseClist(gid);
    }


    // 添加采购入库信息
    public JSONObject addPoWarehouse(WmPowarehouse whouse, List<WmPowarehouseC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList) {

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        //当前没有判断有没有审批，所以直接存入审批时间为单据日期
        whouse.setAuditdate(whouse.getBilldate());
        boolean suc = wareHouseDao.emiInsert(whouse);// 插入生产入库主表

        for (WmPowarehouseC oc : wmohclist) {
            AaGoods goods = cacheCtrlService.getGoods(oc.getMaterialuid());
            oc.setBarcode(goods.getGoodscode() + getYearMonthDay() + getSerialNumber("BARCODE"));
        }

        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入生产入库子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }


    // 更新采购入库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject updatePoWarehouse(WmPowarehouse whouse, List<WmPowarehouseC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList) {
        List poWarehouseC = wareHouseDao.getPoWarehouseClist(whouse.getGid());

        for (int i = 0; i < poWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) poWarehouseC.get(i)).get("materialUid").toString());
            ((Map) poWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.isNullObject(((Map) poWarehouseC.get(i)).get("batchCode")) ? null : ((Map) poWarehouseC.get(i)).get("batchCode").toString());
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) poWarehouseC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("quantity").toString())));

            if (!CommonUtil.isNullObject(((Map) poWarehouseC.get(i)).get("assistquantity"))) {
                wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("assistquantity").toString())));
            }
            wmcat.setOrggid(whouse.getOrggid());
            wmcat.setSobgid(whouse.getSobgid());
            if (!CommonUtil.isNullObject(((Map) poWarehouseC.get(i)).get("batchCode"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) poWarehouseC.get(i)).get("batchCode").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("quantity").toString())));
                if (!CommonUtil.isNullObject(((Map) poWarehouseC.get(i)).get("assistquantity"))) {
                    wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("assistquantity").toString())));
                }
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("quantity").toString()))).compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deletePoWarehouseC(whouse.getGid());
        boolean suc = wareHouseDao.emiUpdate(whouse);// 更新生产主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入生产子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }


    // 删除采购入库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject deleteWmPowarehouse(WmPowarehouse wmoh) {
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List poWarehouseC = wareHouseDao.getPoWarehouseClist(wmoh.getGid());
        for (int i = 0; i < poWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) poWarehouseC.get(i)).get("materialUid").toString());
            ((Map) poWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) poWarehouseC.get(i)).get("batchCode")));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) poWarehouseC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("quantity").toString())));

            if (!CommonUtil.isNullObject(((Map) poWarehouseC.get(i)).get("assistquantity"))) {
                wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("assistquantity").toString())));
            }
            wmcat.setOrggid(wmoh.getOrggid());
            wmcat.setSobgid(wmoh.getSobgid());
            if (!CommonUtil.isNullObject(((Map) poWarehouseC.get(i)).get("batchCode"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) poWarehouseC.get(i)).get("batchCode").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("quantity").toString())));
                if (!CommonUtil.isNullObject(((Map) poWarehouseC.get(i)).get("assistquantity"))) {
                    wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("assistquantity").toString())));
                }
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) poWarehouseC.get(i)).get("quantity").toString()))).compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deletePoWarehouseC(wmoh.getGid());// 删除生产字表
        wareHouseDao.deletePoWarehouse(wmoh.getGid());// 删除生产入库主表
//		List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        boolean suc = updateStocksEntity(newAsList);// 修改货位现存量
        if (suc) {
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }


    // 采购入库分页
    public PageBean getPoWarehouseList(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getPoWarehouseList(pageIndex, pageSize, condition);
    }


    // ...................................................销售出库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
    // 查询最后一条记录
    @SuppressWarnings("rawtypes")
    public Map findSaleOutWarehouse(String gid, String orgId, String sobId) {
        return wareHouseDao.findSaleOutWarehouse(gid, orgId, sobId);
    }

    // 根据销售出库主表GID查询销售出库子表
    @SuppressWarnings("rawtypes")
    public List getSaleOutWarehouseClist(String gid) {
        return wareHouseDao.getSaleOutWarehouseClist(gid);
    }

    // 添加销售出库信息
    public JSONObject addSaleOutWarehouse(WmSaleout wmoh, List<WmSaleoutC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList) {

        JSONObject jobj = new JSONObject();

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        boolean suc = wareHouseDao.emiInsert(wmoh);// 插入销售出库主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入销售出库子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    public WmSaleoutC findWmSaleOutwarehouseC(String gid) {
        return wareHouseDao.findWmSaleOutWarehouseC(gid);
    }

    // 更新销售出库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject updateSaleOutWarehouse(WmSaleout wmoh,
                                             List<WmSaleoutC> wmohclist, List<WmBatch> wmBatchs,
                                             List<WmAllocationstock> asList) {
        List otherWarehouseC = wareHouseDao.getSaleOutWarehouseClist(wmoh.getGid());
        for (int i = 0; i < otherWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) otherWarehouseC.get(i)).get("goodsUid").toString());
            ((Map) otherWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("batchCode")) ? null : ((Map) otherWarehouseC.get(i)).get("batchCode").toString());
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) otherWarehouseC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()));

            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString()));
            }
            wmcat.setOrggid(wmoh.getOrggid());
            wmcat.setSobgid(wmoh.getSobgid());
            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("batchCode"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batchCode").toString()));
                wmb.setNumber(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()));
                if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString()));
                }
                if (wmb.getNumber().compareTo(new BigDecimal(0)) >= 0) {
                    wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        JSONObject jobj = new JSONObject();

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deleteSaleOutWarehouseC(wmoh.getGid());
        boolean suc = wareHouseDao.emiUpdate(wmoh);// 更新销售出库主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入销售出库子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 删除销售出库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject deleteSaleOutWarehouse(WmSaleout wmoh) {
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List otherWarehouseC = wareHouseDao.getSaleOutWarehouseClist(wmoh.getGid());
        for (int i = 0; i < otherWarehouseC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) otherWarehouseC.get(i)).get("goodsUid").toString());
            ((Map) otherWarehouseC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batchCode")));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) otherWarehouseC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()));

            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString()));
            }
            wmcat.setOrggid(wmoh.getOrggid());
            wmcat.setSobgid(wmoh.getSobgid());
            if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("batchCode"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) otherWarehouseC.get(i)).get("batchCode").toString()));
                wmb.setNumber(new BigDecimal(((Map) otherWarehouseC.get(i)).get("number").toString()));
                if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString()));
                }
                if (wmb.getNumber().compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        JSONObject jobj = new JSONObject();

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deleteSaleOutWarehouseC(wmoh.getGid());// 删除销售出库字表
        wareHouseDao.deleteSaleOutWarehouse(wmoh.getGid());// 删除销售出库主表
//		List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        boolean suc = updateStocksEntity(newAsList);// 修改货位现存量
        if (suc) {
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 销售出库分页
    public PageBean getSaleOutWarehouseList(int pageIndex, int pageSize,
                                            String condition) {
        return wareHouseDao.getSaleOutWarehouseList(pageIndex, pageSize,
                condition);
    }

    // ...................................................材料出库操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
    // 查询最后一条记录
    @SuppressWarnings("rawtypes")
    public Map findMaterialOutWarehouse(String gid, String orgId, String sobId) {
        return wareHouseDao.findMaterialOut(gid, orgId, sobId);//findSaleOutWarehouse(gid, orgId, sobId);
    }

    // 根据销售出库主表GID查询销售出库子表
    @SuppressWarnings("rawtypes")
    public List getMaterialOutClist(String gid) {
        return wareHouseDao.getMaterialOutClist(gid);
    }

    // 根据销售出库主表GID查询销售出库子表(额外查询订单号和产品编号)
    @SuppressWarnings("rawtypes")
    public List getMaterList(String gid) {
        return wareHouseDao.getMaterList(gid);
    }

    // 添加材料出库信息
    public JSONObject addMaterialOutWarehouse(WmMaterialout wmMaterialout, List<WmMaterialoutC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList) {

        JSONObject jobj = new JSONObject();

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        boolean suc = wareHouseDao.emiInsert(wmMaterialout);// 插入销售出库主表
        suc = wareHouseDao.emiInsert(wmohclist);// 插入销售出库子表


        int invoicestype = 2; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
        String rdstylegid = wmMaterialout.getBusinesstypeuid();
        String billgid =wmMaterialout.getGid();
        boolean flag = this.postAudit(invoicestype,rdstylegid,billgid);
        if(!flag){
            //不存在审批规则，则执行后续操作
            String tablename = "WM_MaterialOut";
            wareHouseDao.updateauditDateBygidAndTablename(wmMaterialout.getGid(),tablename);
            suc = updateStocksEntity(newAsList);// 修改货位现存量
            if (wmBatchs.size() > 0) { // 增加批次
                wareHouseDao.addWmBatch(wmBatchs);
            }

        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;

    }

    // 更新销售出库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject updateMaterialOutWarehouse(WmMaterialout wmMaterialout, List<WmMaterialoutC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList) {

        JSONObject jobj = new JSONObject();

        List wmMaterialoutC = wareHouseDao.getMaterialOutClist(wmMaterialout.getGid());
        for (int i = 0; i < wmMaterialoutC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) wmMaterialoutC.get(i)).get("goodsUid").toString());
            ((Map) wmMaterialoutC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.isNullObject(((Map) wmMaterialoutC.get(i)).get("batchCode")) ? null : ((Map) wmMaterialoutC.get(i)).get("batchCode").toString());
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) wmMaterialoutC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("number").toString()));

            if (!CommonUtil.isNullObject(((Map) wmMaterialoutC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("assistNumber").toString()));
            }
            wmcat.setOrggid(wmMaterialout.getOrggid());
            wmcat.setSobgid(wmMaterialout.getSobgid());
            if (!CommonUtil.isNullObject(((Map) wmMaterialoutC.get(i)).get("batchCode"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) wmMaterialoutC.get(i)).get("batchCode").toString()));
                wmb.setNumber(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("number").toString()));
                if (!CommonUtil.isNullObject(((Map) wmMaterialoutC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("assistNumber").toString()));
                }
                if (wmb.getNumber().compareTo(new BigDecimal(0)) >= 0) {
                    wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        //判断是否需要重新审批  0：未审核  1:审核中  2：已通过 3:驳回
        if(wmMaterialout.getStatus()!= null && wmMaterialout.getStatus() == 3){
            int invoicestype = 2; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
            String rdstylegid = wmMaterialout.getBusinesstypeuid();
            String billgid =wmMaterialout.getGid();
            boolean flag = this.postAudit(invoicestype,rdstylegid,billgid);
            wmMaterialout.setStatus(0);
        }


        wareHouseDao.deleteMaterialOutC(wmMaterialout.getGid());
        boolean suc = wareHouseDao.emiUpdate(wmMaterialout);// 更新销售出库主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入销售出库子表
            if (suc) {
//				List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
                suc = updateStocksEntity(newAsList);// 修改货位现存量
                if (suc) {
                    // 增加批次
                    if (wmBatchs.size() > 0) {
                        wareHouseDao.addWmBatch(wmBatchs);
                    }
                }
            }
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 删除销售出库信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public JSONObject deleteMaterialOutWarehouse(WmMaterialout wmMaterialout) {
        JSONObject jobj = new JSONObject();


        //判断是否存在审批记录，如果存在，则无法删除
        List list = wareHouseDao.getFollowInfoMovingByBillgid(wmMaterialout.getGid());
        if (list != null && list.size() >0){
            jobj.put("success", 0);
            jobj.put("failInfor", "已提交审批，无法删除");
            return jobj;
        }



        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List wmMaterialoutC = wareHouseDao.getMaterialOutClist(wmMaterialout.getGid());
        for (int i = 0; i < wmMaterialoutC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((Map) wmMaterialoutC.get(i)).get("goodsUid").toString());
            ((Map) wmMaterialoutC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) wmMaterialoutC.get(i)).get("batchCode")));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((Map) wmMaterialoutC.get(i)).get("goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("number").toString()));

            if (!CommonUtil.isNullObject(((Map) wmMaterialoutC.get(i)).get("assistNumber"))) {
                wmcat.setAssistnum(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("assistNumber").toString()));
            }
            wmcat.setOrggid(wmMaterialout.getOrggid());
            wmcat.setSobgid(wmMaterialout.getSobgid());
            if (!CommonUtil.isNullObject(((Map) wmMaterialoutC.get(i)).get("batchCode"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) wmMaterialoutC.get(i)).get("batchCode").toString()));
                wmb.setNumber(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("number").toString()));
                if (!CommonUtil.isNullObject(((Map) wmMaterialoutC.get(i)).get("assistNumber"))) {
                    wmb.setAssistNum(new BigDecimal(((Map) wmMaterialoutC.get(i)).get("assistNumber").toString()));
                }
                if (wmb.getNumber().compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);



        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        //更新对应订单工艺路线子表中出库数量
        //获取对应表的相关信息
        wareHouseDao.updateMesProduceGoods(wmMaterialoutC);

        wareHouseDao.deleteMaterialOutC(wmMaterialout.getGid());// 删除领料出库子表
        wareHouseDao.deleteMaterialOutWarehouse(wmMaterialout.getGid());// 删除领料出库主表


//		List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        boolean suc = updateStocksEntity(newAsList);// 修改货位现存量
        if (suc) {
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    // 材料出库分页
    public PageBean getMaterialoutWarehouseList(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getMaterialOutWarehouseList(pageIndex, pageSize, condition);
    }

    //材料出库主表list查询

    public PageBean getAllListMaterialout(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getAllListMaterialout(pageIndex, pageSize, condition);
    }


    //................................................... 库存操作。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。//
//库存列表
    public PageBean getAllocationStockList(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getAllocationStockList(pageIndex, pageSize, condition);
    }

    public boolean subMaterialOut(JSONObject jboj, String billcode) {
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("wmsGoodsLists", WmsGoods.class);
        classMap.put("cfree", WmsGoodsCfree.class);
        WmsTaskDetailRsp msTaskDetailRsp = (WmsTaskDetailRsp) JSONObject
                .toBean(jboj, WmsTaskDetailRsp.class, classMap);
        // List<OM_MOMaterials>
        // momalist=wareHouseDao.findMoMaterialsListByBillUid(wtdr.getBillUid());

//		WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();
        ;
        Map map = wareHouseDao.getWareHouseByBarcode(msTaskDetailRsp.getWhCode());
        for (WmsGoods good : goods) {
//			AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息 （货位固定只有一个）
            warehouse.add(String.valueOf(map.get("gid")));
        }

        Iterator<String> it = warehouse.iterator();

        // 判断新建 出库单 出库单中要注意货位和仓库 不同仓库 不同主表
        List<WmMaterialout> wolist = new ArrayList<WmMaterialout>();
        List<WmMaterialoutC> woclist = new ArrayList<WmMaterialoutC>();

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;
        Map map2 = wareHouseDao.getWareHouseByBarcode(msTaskDetailRsp.getWhCode());
        while (it.hasNext()) {
            String ckGid = it.next();

            // 添加材料出库主表 子表
            WmMaterialout wmMaterialout = new WmMaterialout();// 出库单主表
            String gid = UUID.randomUUID().toString();
            wmMaterialout.setGid(gid);
            wmMaterialout
                    .setDocumenttypeuid("B65B212E-3C78-44CB-8F3E-43913F3664B7");// WM_BillType  材料出库 billcode：0050
//			String billcode = this.getBillId(Constants.TASKTYPE_CLCK);// 委外材料出库
            wmMaterialout.setBillcode(billcode);
            wmMaterialout.setBilldate(new Date());
            wmMaterialout.setRecordperson(msTaskDetailRsp.getUserGid());
            wmMaterialout.setSobgid(msTaskDetailRsp.getSobgid());
            wmMaterialout.setOrggid(msTaskDetailRsp.getOrggid());
            wmMaterialout.setWhuid(String.valueOf(map2.get("gid")));
            wmMaterialout.setRecorddate(new Timestamp(new Date().getTime()));
            wmMaterialout.setNotes(msTaskDetailRsp.getNotes());//备注改为使用部门gid
            wolist.add(wmMaterialout);

            for (WmsGoods good : goods) {
//				AaGoodsallocation aaGoodsallocation = cacheCtrlService
//						.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
//				AaWarehouse aaWarehouse = cacheCtrlService
//						.getWareHouse(aaGoodsallocation.getWhuid());


                if (ckGid.equalsIgnoreCase(String.valueOf(map2.get("gid")))) {

                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    WmMaterialoutC wmMaterialoutC = new WmMaterialoutC();
                    wmMaterialoutC.setGid(good.getGid());
                    wmMaterialoutC.setMaterialoutuid(gid);
                    wmMaterialoutC.setGoodsuid(good.getGoodsUid());
                    wmMaterialoutC.setNumber(good.getSubmitNum());
                    wmMaterialoutC.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmMaterialoutC.setBatchcode(good.getBatch());
                    wmMaterialoutC.setOmMaterialuid(good.getOmMaterialsUid());
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmMaterialoutC.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmMaterialoutC.setCfree2(wgc.getValue());
                        }
                    }

                    if ((CommonUtil.isNullObject(good.getIsInvQuality()) ? 0
                            : good.getIsInvQuality().intValue()) == 1) {// 有效期

                        String condition = " cinvcode='" + good.getGoodsCode()
                                + "' and cwhcode='" + msTaskDetailRsp.getWhCode()
                                + "' and cbatch='" + good.getBatch() + "'";
                        if (!CommonUtil
                                .isNullObject(wmMaterialoutC.getCfree1())) {
                            condition = condition + " and cfree1='"
                                    + wmMaterialoutC.getCfree1() + "'";
                        }
                        if (!CommonUtil
                                .isNullObject(wmMaterialoutC.getCfree2())) {
                            condition = condition + " and cfree2='"
                                    + wmMaterialoutC.getCfree2() + "'";
                        }
                        List<CurrentStock> cs = wareHouseDao.getAllocationStockYonYou(condition);
                        if (cs.size() == 1) {
                            //wmMaterialoutC.setDvdate(CommonUtil.isNullObject(cs.get(0).getDvDate()) ? Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00") : cs.get(0).getDvDate());
                            wmMaterialoutC.setDvdate(cs.get(0).getDvDate());
                            wmMaterialoutC.setDmadeDate(cs.get(0).getDmDate());
                            wmMaterialoutC.setImassDate(cs.get(0).getImassDate());
                        } else {
                            System.out.println("存在多个有效期的库存");
                            return false;
                        }
                    }

                    woclist.add(wmMaterialoutC);
                    AaGoodsallocation aaGoodsallocation = wareHouseDao.getAAGoodsallocationInfoByWhcode(msTaskDetailRsp.getWhCode());
                    AaGoods aaGoods = cacheCtrlService.getGoods(good.getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good.getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(msTaskDetailRsp.getWhCode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum().negate());// 相反
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity().negate());// 相反
                    wmAllocationstock.setCfree1(wmMaterialoutC.getCfree1());
                    wmAllocationstock.setCfree2(wmMaterialoutC.getCfree2());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
                    wmAllocationstock.setCvMIVenCode(good.getCvMIVenCode());
                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        wmb.setCfree1(wmMaterialoutC.getCfree1());
                        wmb.setCfree2(wmMaterialoutC.getCfree2());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
                        wmBatch.add(wmb);
                    }
                }

            }

        }

        wareHouseDao.addMaterialOut(wolist);// 增加主表
        wareHouseDao.addMaterialoutCs(woclist);// 增加子表

//		if (msTaskDetailRsp.getIsfinish() == 1) {
//			String sql = "update WM_Task set state=2 where gid = '"
//					+ msTaskDetailRsp.getTaskGid() + "'";
//			wareHouseDao.update(sql);
//		}

        // 反填委外订单材料表已出库数量
//		updateSourceBillWhenSubmitWareHouse(goods,
//				Constants.BILLGIDSOURCE_WWDD, "out"); //不需要

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////回填u8

        if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U8101)) {

            String urls = "http://" + Config.INTERFACEADDRESS
                    + "/u8101/wareHouse_addMeterialOut.emi";

//			OM_MOMain ommain = purchaseArrivalDao.getOmMainById(wmtask.getBillgid());
//			List<OM_MOMaterials> aaglist = purchaseArrivalDao
//					.getmOMaterialsGoods(wmtask.getBillgid());

//			jboj.put("mainTableIdentity", ommain.getMoidForSynchro());// 将json中赋值
            // mainTableIdentity

            JSONArray jsonArray = jboj.getJSONArray("wmsGoodsLists");

//			for (Object obj : jsonArray) {
//				JSONObject prowctJson = (JSONObject) obj;
//				for (OM_MOMaterials mm : aaglist) {
//					if (prowctJson.getString("omMaterialsUid")
//							.equalsIgnoreCase(mm.getGid())) {
//						prowctJson.put("childTableIdentity",
//								mm.getMoMaterialsIDForSynchro());
//						;// 将json中赋值 childTableIdentity
//						break;
//					}
//				}
//
//			}
            String data = "json=" + jboj.toString();
            StringBuffer sb = Submit.sendPostRequest(data, urls);
            String s = sb.toString();
            JSONObject jobj = JSONObject.fromObject(s);
            if (jobj.getString("success").equalsIgnoreCase("0")) {
                rollBackWhenError();// 故意犯错 回滚
            }

        } else {
            rollBackWhenError();// 故意犯错 回滚
        }

        return true;
    }


    /**
     * @Desc 其他入库
     * @author yurh
     * @create 2018-03-12 20:20:41
     **/
    public void subOtherIn(JSONObject jsonObject, String billcode) {
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("wmsGoodsLists", WmsGoods.class);
        classMap.put("cfree", WmsGoodsCfree.class);
        WmsTaskDetailRsp msTaskDetailRsp = (WmsTaskDetailRsp) JSONObject
                .toBean(jsonObject, WmsTaskDetailRsp.class, classMap);

//		WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();// 仓库信息
        Map map = wareHouseDao.getWareHouseByBarcode(msTaskDetailRsp.getWhCode());
        for (WmsGoods good : goods) {
//			AaGoodsallocation aaGoodsallocation = cacheCtrlService
//					.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            warehouse.add(String.valueOf(map.get("gid")));
        }

        // 添加其他入库 主表 子表
        List<WmOtherwarehouse> wmOtherwarehouses = new ArrayList<WmOtherwarehouse>();// 入库单主表
        List<WmOtherwarehouseC> wmOtherwarehouseCs = new ArrayList<WmOtherwarehouseC>();// 入库单子表
//		WmPowarehouseC wmPowarehouseC = null;

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;
        StringBuffer sbuffer = new StringBuffer();

        String cvMIVenCode = "";
        Iterator<String> it = warehouse.iterator();
        while (it.hasNext()) {
            String ckGid = it.next();
            String gid = UUID.randomUUID().toString();     //主其他入库Gid


            WmOtherwarehouse wmoh = new WmOtherwarehouse();
            wmoh.setDocumentTypeId("24AD0F1F-6D94-4EE1-8728-896472A3E0C6");// WM_BillType
            wmoh.setGid(gid);
            wmoh.setWhUid(ckGid);
            wmoh.setDepUid(msTaskDetailRsp.getDptGid());
            wmoh.setNotes("");
            wmoh.setBillState("0");
            wmoh.setBillDate(new Timestamp(new Date().getTime()));
            wmoh.setRecordDate(new Timestamp(new Date().getTime()));
            wmoh.setRecordPersonId(msTaskDetailRsp.getUserGid());
            wmoh.setSobGid(msTaskDetailRsp.getSobgid());
            wmoh.setOrgGid(msTaskDetailRsp.getOrggid());
            wmoh.setBillCode(billcode);

            wmOtherwarehouses.add(wmoh);

            for (WmsGoods good : goods) {

//				AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
//				AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
                Map map2 = wareHouseDao.getWareHouseByBarcode(msTaskDetailRsp.getWhCode());
                if (ckGid.equalsIgnoreCase(String.valueOf(map2.get("gid")))) {

                    WmOtherwarehouseC wmohc = new WmOtherwarehouseC();//其它入库子表
                    wmohc.setGid(UUID.randomUUID().toString());
                    wmohc.setNotes("");
                    wmohc.setOtherInUid(gid);
                    wmohc.setGoodsUid(good.getGoodsUid());
                    wmohc.setNumber(good.getSubmitNum());
                    wmohc.setBarCode(good.getGoodsBarCode());
                    wmohc.setGoodsAllocationUid(good.getGoodsAllocationUid());
                    wmohc.setBatch(good.getBatch());


                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmohc.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmohc.setCfree2(wgc.getValue());
                        }
                    }

                    wmOtherwarehouseCs.add(wmohc);
                    AaGoodsallocation aaGoodsallocation = wareHouseDao.getAAGoodsallocationInfoByWhcode(msTaskDetailRsp.getWhCode());
                    AaGoods aaGoods = cacheCtrlService.getGoods(good.getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(msTaskDetailRsp.getWhCode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum());
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
//					wmAllocationstock.setCfree1(wmPowarehouseC.getCfree1());
//					wmAllocationstock.setCfree2(wmPowarehouseC.getCfree2());


                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
//						wmb.setCfree1(wmPowarehouseC.getCfree1());
//						wmb.setCfree2(wmPowarehouseC.getCfree2());
                        wmBatch.add(wmb);
                    }

                }

            }

        }

        wareHouseDao.addOtherWarehouseIn(wmOtherwarehouses);// 增加主表
        wareHouseDao.addOtherWarehouseIns(wmOtherwarehouseCs);// 增加子表

//		// 反填到货单已入库数量
//		updateSourceBillWhenSubmitWareHouse(goods,
//				Constants.BILLGIDSOURCE_CGDH, "in");

//		// 反填质检单已入库数量
//		if (wmtask.getBillGidSource().equalsIgnoreCase(
//				Constants.BILLGIDSOURCE_WWZJ)) {
//			updateSourceBillWhenSubmitWareHouse(goods,
//					Constants.BILLGIDSOURCE_WWZJ, "in");
//		}
//		;

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }

        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////回填u8
//		if (Config.INTERFACETYPE.equalsIgnoreCase(Constants.INTERFACE_U8101)) {
//
//			String urls = "http://" + Config.INTERFACEADDRESS
//					+ "/u8101/wareHouse_addsubOtherIn.emi";

//			String poarrivalcuid = sbuffer.toString();
//			if (poarrivalcuid.length() > 0) {
//				poarrivalcuid = "("
//						+ poarrivalcuid
//						.substring(0, poarrivalcuid.length() - 1) + ")";
//			}
//			String condition = " gid in " + poarrivalcuid;
//			List<WmProcurearrivalC> procurearrivalC = purchaseArrivalService
//					.getProcurearrivalCList(condition);
//			jsonObject.put("mainTableIdentity", procurearrivalC.get(0)
//					.getIdForSynchro());// 将json中赋值 mainTableIdentity
//			JSONArray jsonArray = jsonObject.getJSONArray("wmsGoodsLists");

//			for (Object obj : jsonArray) {
//				JSONObject prowctJson = (JSONObject) obj;
//				for (WmProcurearrivalC pc : procurearrivalC) {
//					if (prowctJson.getString("procureArrivalCuid")
//							.equalsIgnoreCase(pc.getGid())) {
//						prowctJson.put("childTableIdentity",
//								pc.getAutoidForSynchro());
//						;// 将json中赋值 childTableIdentity
//						break;
//					}
//				}
//
//			}
//			String data = "json=" + jsonObject.toString();
//			StringBuffer sb = Submit.sendPostRequest(data, urls);
//			String s = sb.toString();
//			JSONObject jobj = JSONObject.fromObject(s);
//			if (jobj.getString("success").equalsIgnoreCase("0")) {
//				rollBackWhenError();// 故意犯错 回滚
//			}
//		} else {
//			rollBackWhenError();// 故意犯错 回滚
//		}


    }


    public void subOtherOut(JSONObject jsonObject, String billcode) {
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("wmsGoodsLists", WmsGoods.class);
        classMap.put("cfree", WmsGoodsCfree.class);
        WmsTaskDetailRsp msTaskDetailRsp = (WmsTaskDetailRsp) JSONObject
                .toBean(jsonObject, WmsTaskDetailRsp.class, classMap);

//		WmTask wmtask = taskDao.getTask(msTaskDetailRsp.getTaskGid());

        List<WmsGoods> goods = msTaskDetailRsp.getWmsGoodsLists();
        Set<String> warehouse = new HashSet<String>();// 仓库信息
        for (WmsGoods good : goods) {
//			AaGoodsallocation aaGoodsallocation = cacheCtrlService
//					.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
            Map map = wareHouseDao.getWareHouseByBarcode(msTaskDetailRsp.getWhCode());
            warehouse.add(String.valueOf(map.get("gid")));
        }

        // 添加其他出库 主表 子表
        List<WmOthersout> wmOthersouts = new ArrayList<WmOthersout>();// 出库单主表
        List<WmOthersoutC> wmOthersoutCS = new ArrayList<WmOthersoutC>();// 出库单子表
//		WmPowarehouseC wmPowarehouseC = null;

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();// 货位现存量
        WmAllocationstock wmAllocationstock = null;

        List<WmBatch> wmBatch = new ArrayList<WmBatch>();// 批次
        WmBatch wmb = null;

        String cvMIVenCode = "";
        Iterator<String> it = warehouse.iterator();
        while (it.hasNext()) {
            String ckGid = it.next();
            String gid = UUID.randomUUID().toString();     //主其他入库Gid


            WmOthersout wmoh = new WmOthersout();
            wmoh.setDocumentTypeUid("34B17996-5285-48EE-864B-FB3DEB218036");// WM_BillType
            wmoh.setGid(gid);
            wmoh.setWarehouseUid(ckGid);
            wmoh.setDepartmentUid(msTaskDetailRsp.getDptGid());
            wmoh.setNotes("");
            wmoh.setBillState("0");
            wmoh.setBillDate(new Timestamp(new Date().getTime()));
            wmoh.setRecordDate(new Timestamp(new Date().getTime()));
            wmoh.setPersonUid(msTaskDetailRsp.getUserGid());
            wmoh.setSobGid(msTaskDetailRsp.getSobgid());
            wmoh.setOrgGid(msTaskDetailRsp.getOrggid());
            wmoh.setBillCode(billcode);
            wmoh.setRecordPersonUid(msTaskDetailRsp.getUserGid());

            wmOthersouts.add(wmoh);

            for (WmsGoods good : goods) {

//				AaGoodsallocation aaGoodsallocation = cacheCtrlService.getGoodsAllocation(good.getGoodsAllocationUid());// 货位信息
//				AaWarehouse aaWarehouse = cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
                Map map2 = wareHouseDao.getWareHouseByBarcode(msTaskDetailRsp.getWhCode());
                if (ckGid.equalsIgnoreCase(String.valueOf(map2.get("gid")))) {

                    WmOthersoutC wmohc = new WmOthersoutC();//其它出库子表
                    wmohc.setGid(UUID.randomUUID().toString());
                    wmohc.setNotes("");
                    wmohc.setOthersOutUid(gid);
                    wmohc.setGoodsUid(good.getGoodsUid());
                    wmohc.setNumber(good.getSubmitNum());
//					wmohc.setBarCode(good.getGoodsBarCode());
                    wmohc.setGoodsAllocationUid(good.getGoodsAllocationUid());
                    wmohc.setBatch(good.getBatch());


                    List<WmsGoodsCfree> cfrees = good.getCfree();
                    for (WmsGoodsCfree wgc : cfrees) {
                        if (wgc.getColName().equalsIgnoreCase("cfree1")) {
                            wmohc.setCfree1(wgc.getValue());
                        } else if (wgc.getColName().equalsIgnoreCase("cfree2")) {
                            wmohc.setCfree2(wgc.getValue());
                        }
                    }

                    wmOthersoutCS.add(wmohc);
                    AaGoodsallocation aaGoodsallocation = wareHouseDao.getAAGoodsallocationInfoByWhcode(msTaskDetailRsp.getWhCode());
                    AaGoods aaGoods = cacheCtrlService.getGoods(good.getGoodsUid());
                    wmAllocationstock = new WmAllocationstock();
                    wmAllocationstock.setGoodscode(aaGoods.getGoodscode());
                    wmAllocationstock.setGoodsuid(good.getGoodsUid());
                    wmAllocationstock.setGoodsallocationcode(aaGoodsallocation
                            .getCode());
                    wmAllocationstock.setGoodsallocationuid(good
                            .getGoodsAllocationUid());
                    wmAllocationstock.setWhCode(msTaskDetailRsp.getWhCode());
                    wmAllocationstock.setBatch(good.getBatch());
                    wmAllocationstock.setNumber(good.getSubmitNum().negate());
                    wmAllocationstock.setAssistnum(good.getSubmitQuantity());
                    wmAllocationstock.setSobgid(msTaskDetailRsp.getSobgid());
                    wmAllocationstock.setOrggid(msTaskDetailRsp.getOrggid());
//					wmAllocationstock.setCfree1(wmPowarehouseC.getCfree1());
//					wmAllocationstock.setCfree2(wmPowarehouseC.getCfree2());


                    asList.add(wmAllocationstock);

                    if (!CommonUtil.isNullObject(good.getBatch())) {
                        wmb = new WmBatch();
                        wmb.setGid(UUID.randomUUID().toString());
                        wmb.setGoodsUid(good.getGoodsUid());
                        wmb.setGoodsAllocationUid(good.getGoodsAllocationUid());
                        wmb.setBatch(good.getBatch());
                        if (good.getSubmitNum().doubleValue() > 0) {//1、蓝字单据，0、红字单据
                            wmb.setRedBlueFlag(1);
                        } else {
                            wmb.setRedBlueFlag(0);
                        }
                        wmb.setRecordDate(new Timestamp(new Date().getTime()));
                        wmb.setNumber(good.getSubmitNum());
                        wmb.setAssistNum(good.getSubmitQuantity());
//						wmb.setCfree1(wmPowarehouseC.getCfree1());
//						wmb.setCfree2(wmPowarehouseC.getCfree2());
                        wmBatch.add(wmb);
                    }

                }

            }

        }

        wareHouseDao.addOthersOutIn(wmOthersouts);// 增加主表
        wareHouseDao.addOthersOutIns(wmOthersoutCS);// 增加子表

        // 更新货位现存量
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        updateStocksEntity(newAsList);

        // 增加批次
        if (wmBatch.size() > 0) {
            wareHouseDao.addWmBatch(wmBatch);
        }


    }

    public Map findOtherOut(String otherOutgid, String orgId, String sobId) {
        return wareHouseDao.findOtherOut(otherOutgid, orgId, sobId);
    }

    public List getOthersOutClist(String gid) {
        return wareHouseDao.getOthersOutClist(gid);

    }

    public PageBean getOthersOutList(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getOthersOutList(pageIndex, pageSize,
                condition);


    }


    public JSONObject deleteOthersOut(WmOthersout wmoh) {
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List othersOutC = wareHouseDao.getOthersOutClist(wmoh.getGid());
        for (int i = 0; i < othersOutC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(String.valueOf(((Map) othersOutC.get(i)).get("goodsUid")));
            ((Map) othersOutC.get(i)).put("good", good);
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) othersOutC.get(i))
                    .get("batch").toString()));
            AaGoodsallocation gaIn = cacheCtrlService
                    .getGoodsAllocation(((Map) othersOutC.get(i)).get(
                            "goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((Map) othersOutC.get(i)).get("number").toString()));

//			if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
//				wmcat.setAssistnum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
//			}
            wmcat.setOrggid(wmoh.getOrgGid());
            wmcat.setSobgid(wmoh.getSobGid());
            if (!CommonUtil.isNullObject(((Map) othersOutC.get(i)).get("batch"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) othersOutC.get(i)).get("batch").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) othersOutC.get(i)).get("number").toString())));
//				if (!CommonUtil.isNullObject(((Map) otherWarehouseC.get(i)).get("assistNumber"))) {
//					wmb.setAssistNum(new BigDecimal(0).subtract(new BigDecimal(((Map) otherWarehouseC.get(i)).get("assistNumber").toString())));
//				}
                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) othersOutC.get(i)).get("number").toString()))).compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        wareHouseDao.deleteOthersOutC(wmoh.getGid());// 删除其他字表
        wareHouseDao.deleteOthersOut(wmoh.getGid());// 删除其他入库主表
        boolean suc = updateStocksEntity(newAsList);// 修改货位现存量
        if (suc) {
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    public List<Map> getReportList(int pageIndex, int pageSize, String serachEm, String condition) {
        List<Map>  map   = wareHouseDao.getAllocationStockListForPrint(pageIndex, pageSize, condition);
        return map;

    }

    public Map findMaterialApplyWarehouse(String materialApplygid, String orgId, String sobId) {
        return wareHouseDao.findMaterialApply(materialApplygid, orgId, sobId);//findSaleOutWarehouse(gid, orgId, sobId);
    }

    public List getMaterApplyList(String gid) {
        return wareHouseDao.getMaterApplyList(gid);
    }

    public PageBean getAllListMaterialapply(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getAllListMaterialapply(pageIndex, pageSize, condition);
    }

    public JSONObject addMaterialApplyWarehouse(WmMaterialapply wmMaterialapply, List<WmMaterialapplyC> wmohclist) {

        JSONObject jobj = new JSONObject();



        boolean suc = wareHouseDao.emiInsert(wmMaterialapply);// 插入领用申请表主表
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);// 插入领用申请表子表
        }
        int invoicestype = 1; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
        String rdstylegid = wmMaterialapply.getBusinesstypeuid();
        String billgid =wmMaterialapply.getGid();
        boolean flag = this.postAudit(invoicestype,rdstylegid,billgid);
        //.....
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    public JSONObject updateMaterialApplyWarehouse(WmMaterialapply wmMaterialapply, List<WmMaterialapplyC> wmohclist) {

        JSONObject jobj = new JSONObject();


        //判断是否存在审批记录，如果存在，则无法修改
        List list = wareHouseDao.getFollowInfoMovingByBillgid(wmMaterialapply.getGid());
        if (list != null && list.size() >0){
            jobj.put("success", 0);
            jobj.put("failInfor", "已提交审批，无法修改");
            return jobj;
        }

        //判断是否需要重新审批  0：未审核  1:审核中  2：已通过 3:驳回
        if(wmMaterialapply.getStatus() != null && wmMaterialapply.getStatus() == 3){
            int invoicestype = 1; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
            String rdstylegid = wmMaterialapply.getBusinesstypeuid();
            String billgid =wmMaterialapply.getGid();
            boolean flag = this.postAudit(invoicestype,rdstylegid,billgid);
//            wareHouseDao.updateBillStatus(0,billgid,"WM_MaterialApply");
            wmMaterialapply.setStatus(0);
        }




//        List wmMaterialapplyC = wareHouseDao.getMaterialOutClist(wmMaterialapply.getGid());

        wareHouseDao.deleteMaterialOutC(wmMaterialapply.getGid());
        boolean suc = wareHouseDao.emiUpdate(wmMaterialapply);//
        if (suc) {
            suc = wareHouseDao.emiInsert(wmohclist);//
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    public JSONObject deleteMaterialApplyWarehouse(WmMaterialapply wmMaterialapply) {

        JSONObject jobj = new JSONObject();

        //判断是否存在审批记录，如果存在，则无法删除
        List list = wareHouseDao.getFollowInfoMovingByBillgid(wmMaterialapply.getGid());
        if (list != null && list.size() >0){
            jobj.put("success", 0);
            jobj.put("failInfor", "已提交审批，无法删除");
            return jobj;
        }

        wareHouseDao.deleteMaterialApplyC(wmMaterialapply.getGid());// 删除领用
        wareHouseDao.deleteMaterialApplyWarehouse(wmMaterialapply.getGid());// 删除领用
//
        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;



    }

    public PageBean getAllListMaterialapplyMy(int pageIndex, int pageSize, String condition, String userid) {


        return wareHouseDao.getAllListMaterialapplyMy(pageIndex, pageSize, condition,userid);
    }

    public void updateFollowMovingStatus(String type, String followmovinggid, String owhGid, String billtype, Map<String, Object> session) {
        int state = 0;//最终要修改的状态
        String billtablename ="";//要修改的单据表名

        if(Integer.parseInt(type) == 1){
            //同意  修改当前节点的审批状态
            wareHouseDao.updateFollowMovingStatus(type,followmovinggid);
            //判断当前节点是不是最后一个节点
            FollowInfoMoving followInfoMoving = wareHouseDao.getLastFollowMovingNode(owhGid);
            if (followInfoMoving.getId() == Integer.parseInt(followmovinggid)){
                state = 2;//是最后一个节点
                //WM_MaterialApply 领用申请单
                //WM_MaterialOut 材料出库单
                //处理单据业务：
                if(billtype != null && !"".equals(billtype)){
                    String tablename = "";
                    switch (billtype){
                        case "materialapply"://领用申请单
                            tablename = "WM_MaterialApply";
                            this.updateAuditDateByBillgid(tablename,owhGid,new Date());//修改主表单据审核时间
                            break;
                        case "materialout": //材料出库单
                            System.out.println("开始处理 材料出库单审核通过业务");
                            tablename = "WM_MaterialOut";
                            this.updateAuditDateByBillgid(tablename,owhGid,new Date());
                            this.doMaterialOutAuditOk(followmovinggid,owhGid,session);
                            break;
                        case "call"://调拨单
                            System.out.println("开始处理 调拨单审核通过业务");
                            tablename = "WM_Call";
                            wareHouseDao.updateOutNumberBynumberCall(owhGid,"WM_Call_C");//修改已调出数量，为应调出数量
                            this.updateAuditDateByBillgid(tablename,owhGid,new Date());
                            this.doCallAuditOk(followmovinggid,owhGid,session);
                        case "othersscrap"://报废单
                            System.out.println("开始处理 报废单 审核通过业务");
                            tablename = "WM_OthersScrap";
                            this.updateAuditDateByBillgid(tablename,owhGid,new Date());
                            this.doOthersScrapAuditOk(followmovinggid,owhGid,session);

                    }
                }


            }else {
                //不是最后一个节点不处理业务
                state = 1;

            }

        }else if(Integer.parseInt(type) == 2){
            //驳回
            state =3;
            wareHouseDao.updateFollowMovingStatus(type,followmovinggid);
            //把该所有审核节点 审核状态为0的都设置为驳回2
            wareHouseDao.updateFollowMovingStatusBohui(owhGid);
            //驳回，就设置改单据的isused为1 废弃
            wareHouseDao.updateIsUsedByBillGid(owhGid);

        }


        System.out.println("修改单据 主表的审批状态 ");
        switch (billtype){
            case "materialapply"://领用申请单
                billtablename = "WM_MaterialApply";
                wareHouseDao.updateBillStatus(state,owhGid,billtablename);
                break;
            case "materialout": //材料出库单
                billtablename = "WM_MaterialOut";
                wareHouseDao.updateBillStatus(state,owhGid,billtablename);
                break;
            case "call": //调拨单
                billtablename = "WM_Call";
                wareHouseDao.updateBillStatus(state,owhGid,billtablename);
                break;
            case "othersscrap": //报废单
                billtablename = "WM_OthersScrap";
                wareHouseDao.updateBillStatus(state,owhGid,billtablename);
                break;

        }


    }

    private void updateAuditDateByBillgid(String tablename,String gid,Date date){
        wareHouseDao.updateAuditDateByBillgid(tablename,gid,date);
    }

    /**
    * @Desc 报废单   审批最终通过处理业务
    * @author yurh
    * @create 2018-05-11 17:07:58
    **/
    private JSONObject doOthersScrapAuditOk(String followmovinggid, String owhGid, Map<String,Object> session) {
        JSONObject jobj = new JSONObject();

        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();

        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        WmOthersscrap wmOthersscrap = wareHouseDao.getOthersScrapByGid(owhGid);
        List<WmOthersscrapC> list = wareHouseDao.getOthersScrapClist(owhGid);
        for(int i = 0 ;i<list.size();i++){
            WmOthersscrapC wmOthersscrapC = list.get(i);

            AaGoods good = cacheCtrlService.getGoods(wmOthersscrapC.getGoodsUid().toString());
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(wmOthersscrapC.getBatch().toString()));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(wmOthersscrapC.getGoodsAllocationUid().toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(0).subtract(new BigDecimal(wmOthersscrapC.getNumber().toString())));

            wmcat.setOrggid(wmOthersscrap.getOrgGid());
            wmcat.setSobgid(wmOthersscrap.getSobGid());
            if (!CommonUtil.isNullObject(wmOthersscrapC.getBatch())) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(wmOthersscrapC.getBatch().toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(wmOthersscrapC.getNumber().toString())));
                if ((new BigDecimal(0).subtract(new BigDecimal(wmOthersscrapC.getNumber().toString()))).compareTo(new BigDecimal(0)) >= 0) {
                    wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }


        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }



        updateStocksEntity(newAsList);// 修改货位现存量
        if (wmBatchs.size() > 0) { // 增加批次
            wareHouseDao.addWmBatch(wmBatchs);
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;


    }

    /**
    * @Desc 调拨单   审批最终通过处理业务
    * @author yurh
    * @create 2018-05-10 17:30:53
    **/
    private JSONObject doCallAuditOk(String followmovinggid, String owhGid, Map<String,Object> session) {
        JSONObject jobj = new JSONObject();
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List<WmOtherwarehouseC> wmohclist = new ArrayList(); //其他入子表集合
        List<WmOthersoutC> wmooclist = new ArrayList(); //其他出子表集合


        wmCall wmc = wareHouseDao.getWmCallByGid(owhGid);
        List<wmCallC> list = wareHouseDao.getWmCallDetailByWMS(owhGid);
        String[] sqls = new String[list.size()];



        // 其他出主表
        String wmoouid = UUID.randomUUID().toString();
        WmOthersout wmoo = new WmOthersout();
        wmoo.setDocumentTypeUid("34B17996-5285-48EE-864B-FB3DEB218036");
        wmoo.setWarehouseUid(wmc.getOutWhUid());
        wmoo.setBillCode(this.getBillId(Constants.TASKTYPE_QTCK));
        wmoo.setBillState("0");
        wmoo.setBillDate(new Date());
        wmoo.setRecordPersonUid(wmc.getRecordPersonUid());
        wmoo.setRecordDate(new Date());
        wmoo.setSobGid(wmc.getSobGid());
        wmoo.setOrgGid(wmc.getOrgGid());
        wmoo.setNotes(wmc.getNotes());
        wmoo.setAuditDate(new Date());//2018-5-12 这个字段重要，判断其实际影响的库存的时间

        // 其他入主表
        String wmohuid = UUID.randomUUID().toString();
        WmOtherwarehouse wmoh = new WmOtherwarehouse();
        wmoh.setDocumentTypeId("24AD0F1F-6D94-4EE1-8728-896472A3E0C6");
        wmoh.setWhUid(wmc.getInWhUid());
        wmoh.setBillCode(this.getBillId(Constants.TASKTYPE_QTRK));
        wmoh.setBillState("0");
        wmoh.setBillDate(new Date());
        wmoh.setRecordDate(new Date());
        wmoh.setRecordPersonId(wmc.getRecordPersonUid());
        wmoh.setSobGid(wmc.getSobGid());
        wmoh.setOrgGid(wmc.getOrgGid());
        wmoh.setAuditDate(new Date()); //2018-5-12 这个字段重要，判断其实际影响的库存的时间




        for(int i = 0 ;i<list.size();i++){
            wmCallC wmcc = list.get(i);
            WmAllocationstock wmcat=new WmAllocationstock();////货位现存量出
            wmcat.setBatch(wmcc.getBatch());
            AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(wmcc.getOutgoodsAllocationUid());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(wmcc.getGoodsUid());
            AaGoods aaGoods =  cacheCtrlService.getGoods(wmcc.getGoodsUid());
            wmcat.setGoodscode(aaGoods.getGoodscode());
            wmcat.setNumber(wmcc.getNumber().negate());//取相反数
            if(!CommonUtil.isNullObject(CommonUtil.Obj2String(wmcc.getBatch()))){
                wmcat.setBatch(wmcc.getBatch());
            }
            wmcat.setOrggid(wmc.getOrgGid());
            wmcat.setSobgid(wmc.getSobGid());

            asList.add(wmcat);


            WmAllocationstock wmcat2=new WmAllocationstock();////货位现存量入
            wmcat2.setBatch(CommonUtil.Obj2String(wmcc.getBatch()));
            AaGoodsallocation gaIn2=cacheCtrlService.getGoodsAllocation(wmcc.getIngoodsAllocationUid());
            wmcat2.setGoodsallocationcode(gaIn2.getCode());
            wmcat2.setGoodsallocationuid(gaIn2.getGid());
            wmcat2.setWhCode(gaIn2.getWhcode());
            wmcat2.setGoodsuid(wmcc.getGoodsUid());
            wmcat2.setGoodscode(aaGoods.getGoodscode());
            wmcat2.setNumber(wmcc.getNumber());//取正数
            if(!CommonUtil.isNullObject(CommonUtil.Obj2String(wmcc.getBatch()))){
                wmcat2.setBatch(wmcc.getBatch());
            }
            wmcat2.setOrggid(wmc.getOrgGid());
            wmcat2.setSobgid(wmc.getSobGid());

            asList.add(wmcat2);




            WmOthersoutC wmooc = new WmOthersoutC();// 其它出库子表
            wmooc.setGid(UUID.randomUUID().toString());
            wmooc.setNotes(wmcc.getNotes());
            wmooc.setOthersOutUid(wmoouid);
            wmooc.setGoodsUid(wmcc.getGoodsUid());
            wmooc.setNumber(wmcc.getNumber());
            wmooc.setCallCuid(wmcc.getGid());
            wmooc.setGoodsAllocationUid(wmcc.getOutgoodsAllocationUid());
            wmooc.setBatch(CommonUtil.Obj2String(wmcc.getBatch()));

            WmOtherwarehouseC wmohc = new WmOtherwarehouseC();// 其它入库子表
            wmohc.setGid(UUID.randomUUID().toString());
            wmohc.setNotes(wmcc.getNotes());
            wmohc.setOtherInUid(wmohuid);
            wmohc.setGoodsUid(wmcc.getGoodsUid());
            wmohc.setNumber(wmcc.getNumber());
            wmohc.setCallCuid(wmcc.getGid());
            wmohc.setGoodsAllocationUid(wmcc.getIngoodsAllocationUid());
            wmohc.setBatch(CommonUtil.Obj2String(wmcc.getBatch()));

            wmooclist.add(wmooc);
            wmohclist.add(wmohc);

            if(!CommonUtil.isNullObject(CommonUtil.Obj2String(wmcc.getBatch()))){                //判断是否有批次，有则添加到批次表
                WmBatch wmb=new WmBatch();//批次
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(wmcc.getGoodsUid());
                wmb.setGoodsAllocationUid(wmcc.getOutgoodsAllocationUid());
                wmb.setBatch(CommonUtil.Obj2String(wmcc.getBatch()));
                wmb.setNumber(wmcc.getNumber().negate());//取相反数
                wmb.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
                wmb.setRecordDate(new Timestamp(new Date().getTime()));


                WmBatch wmb2=new WmBatch();//批次
                wmb2.setGid(UUID.randomUUID().toString());
                wmb2.setGoodsUid(wmcc.getGoodsUid());
                wmb2.setGoodsAllocationUid(wmcc.getIngoodsAllocationUid());
                wmb2.setBatch(CommonUtil.Obj2String(wmcc.getBatch()));
                wmb2.setNumber(wmcc.getNumber());//取正数
                wmb2.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                wmb2.setRecordDate(new Timestamp(new Date().getTime()));

                wmBatchs.add(wmb);
                wmBatchs.add(wmb2);
            }

            sqls[i] = "update WM_Call_C set outnumber='"
                    + wmcc.getNumber()
                    + "' where gid='" + wmcc.getGid() + "'   ";

        }


        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }



        wareHouseDao.updateCallState(owhGid);
        wareHouseDao.emiInsert(wmoo);// 插入其他入主表
        wareHouseDao.emiInsert(wmooclist);// 插入其他入子表
        wareHouseDao.emiInsert(wmoh);// 插入其他出主表
        wareHouseDao.emiInsert(wmohclist);// 插入其他出子表
        wareHouseDao.batchUpdate(sqls);// 回填调拨单已出库数量

        updateStocksEntity(newAsList);// 修改货位现存量
        if (wmBatchs.size() > 0) { // 增加批次
            wareHouseDao.addWmBatch(wmBatchs);
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;
    }

    /**
    * @Desc 材料出库   审批最终通过处理业务
    * @author yurh
    * @create 2018-05-05 10:36:12
    **/
    private JSONObject doMaterialOutAuditOk(String followmovinggid, String owhGid, Map<String, Object> session) {
        JSONObject jobj = new JSONObject();
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List list = wareHouseDao.getMaterialOutClist(owhGid);
        List<WmMaterialapplyC> applyList = new ArrayList<WmMaterialapplyC>();//修改领用申请表数量
        for(int i = 0;i<list.size();i++){
            Map map = (Map) list.get(i);
            WmMaterialoutC wm = JSON.parseObject(JSON.toJSONString(map),WmMaterialoutC.class);
            //审核最终通过处理材料出库单数据
            WmAllocationstock wmcat=new WmAllocationstock();////货位现存量入
            wmcat.setBatch(wm.getBatchcode());
            AaGoodsallocation gaIn=cacheCtrlService.getGoodsAllocation(wm.getGoodsallocationuid());//goodsAllocationUid[i]
            AaGoods aaGoods = cacheCtrlService.getGoods(wm.getGoodsuid());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(wm.getGoodsuid());//goodsUid[i]
            if(aaGoods != null){
                wmcat.setGoodscode(aaGoods.getGoodscode());//goodsCode[i]
            }
            wmcat.setNumber(wm.getNumber().negate());//取相反数mainNumber[i]
            if(!CommonUtil.isNullObject(CommonUtil.Obj2String(wm.getBatchcode()))){//batch[i]
                wmcat.setBatch(wm.getBatchcode());
            }

            wmcat.setOrggid(session.get("OrgId").toString());
            wmcat.setSobgid(session.get("SobId").toString());
            if(!CommonUtil.isNullObject(CommonUtil.Obj2String(wm.getBatchcode()))){                //判断是否有批次，有则添加到批次表
                WmBatch wmb=new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(wm.getGoodsuid());
                wmb.setGoodsAllocationUid(wm.getGoodsallocationuid());
                wmb.setBatch(wm.getBatchcode());
                wmb.setNumber(wm.getNumber().negate());
                if(wmb.getNumber().compareTo(BigDecimal.valueOf(0))>=0){
                    wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                }else{
                    wmb.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }
            asList.add(wmcat);

            WmMaterialapplyC wmMaterialapplyC = new WmMaterialapplyC();
            wmMaterialapplyC.setGid(wm.getMaterialapplycgid());
            wmMaterialapplyC.setReceivednumber(wm.getNumber());
            applyList.add(wmMaterialapplyC);//需要更新已出库数量到申请表中


        }



        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }

        updateStocksEntity(newAsList);// 修改货位现存量
        if (wmBatchs.size() > 0) { // 增加批次
            wareHouseDao.addWmBatch(wmBatchs);
        }


        //修改领用申请表实际领用数量
        wareHouseDao.updateReciveNumberByApplyCGid(applyList);

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;

    }

    public PageBean getAllListMaterialapplyMain(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getAllListMaterialapplyMain(pageIndex, pageSize, condition);
    }


    /**
    * @Desc 提交审核 公用方法
    * @author yurh
     * @param  invoicestype 单据类型
     * @param  rdstylegid 审批类型 （单据里面选择，例：出库类型）
     * @param  billgid 单据id
    * @create 2018-05-04 10:54:25
    **/
    public boolean postAudit(int invoicestype,String rdstylegid,String billgid){
        //提交审核：

        boolean flag = false;
        FollowRule followRule = wareHouseDao.getFollowRule(invoicestype,rdstylegid);
        wareHouseDao.updateIsUsedByBillGid(billgid);//所有该单据的是否使用设置为1：不使用


        if(followRule != null){
            List<Map>  followRuleNodeUserList= wareHouseDao.getFollowRuleNodeUserList(followRule.getId());
            if(followRuleNodeUserList != null && followRuleNodeUserList.size() >0){
                //插入审核节点数据
                List<FollowInfoMoving> list = new ArrayList<FollowInfoMoving>();
                for(int  i = 0 ;i<followRuleNodeUserList.size();i++){
                    Map map  = followRuleNodeUserList.get(i);
                    FollowRuleNodeUser followRuleNodeUser = JSON.parseObject(JSON.toJSONString(map),FollowRuleNodeUser.class);
                    FollowInfoMoving followInfoMoving = new FollowInfoMoving();
                    followInfoMoving.setBillsgid(billgid);//单据id
                    followInfoMoving.setCurrentnodeid(followRuleNodeUser.getId());
                    followInfoMoving.setCurrentnodeindex(followRuleNodeUser.getNodeindex());
                    followInfoMoving.setApprovaluser(followRuleNodeUser.getUserid());
                    followInfoMoving.setCtime(new Date());
                    list.add(followInfoMoving);
                }
                wareHouseDao.emiInsert(list);
                flag = true;
                return flag;
            }
        }
        return flag;

    }


    public PageBean getAllListMaterialoutMy(int pageIndex, int pageSize, String condition, String userid) {
        return wareHouseDao.getAllListMaterialoutMy(pageIndex, pageSize, condition,userid);
    }

    public List<FollowInfoMoving> getFollowMovingListByBillid(String materialOutgid) {
        return wareHouseDao.getFollowMovingListByBillid(materialOutgid);

    }

    public Map findCall(String callgid, String orgId, String sobId) {
        //findSaleOutWarehouse
        return wareHouseDao.findCall(callgid, orgId, sobId);

    }

    public List getWmCallDetailByWMS(String gid) {
        List<wmCallC> wmclist = wareHouseDao.getWmCallDetailByWMS(gid);
        List outlist = new ArrayList();
//        for (wmCallC wmc : wmclist) {
//            JSONObject jso = EmiJsonObj.fromObject(wmc);
//            AaGoods ag = cacheCtrlService.getGoods(wmc.getGoodsUid());
//            WmsGoodsCfree wgc = new WmsGoodsCfree();
//            wgc.setIsShow(1);
//            wgc.setName("工序");
//            wgc.setValue(wmc.getCfree1());
//            wgc.setColName("cfree1");
//            List<WmsGoodsCfree> cfree = new ArrayList();
//            cfree.add(wgc);
//            jso.put("cfree", cfree);
//            jso.put("goodsname", ag.getGoodsname());
//            jso.put("goodsUnitMainName", ag.getUnitName());
//            jso.put("goodsstandard", ag.getGoodsstandard());
//            jso.put("goodscode", ag.getGoodscode());
//            if (CommonUtil.isNullObject(ag.getCassComUnitName())) {
//                jso.put("goodsUnitAssistName", "");
//            } else {
//                jso.put("goodsUnitAssistName", ag.getCassComUnitName());
//            }
//            outlist.add(jso);
//        }
        return wmclist;
    }

    /**
    * @Desc 新增调拨单
    * @author yurh
    * @create 2018-05-08 08:57:43
    **/
    public JSONObject addCall(wmCall wmc, List<wmCallC> clist, WmOthersout wmoo, WmOtherwarehouse wmoh, List<WmOthersoutC> wmooclist, List<WmOtherwarehouseC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList, String[] sqls) {
        JSONObject jobj = new JSONObject();


        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);
        boolean isEnough = isEnoughStock(newAsList);
        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }
        wareHouseDao.emiInsert(wmc);//调拨单主表
        wareHouseDao.emiInsert(clist);//调拨单子表


        int invoicestype = 3; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
        String rdstylegid = wmc.getBusinessTypeUid();
        String billgid =wmc.getGid();
        boolean flag = this.postAudit(invoicestype,rdstylegid,billgid);
        if(!flag){
            //不存在审批规则，则执行后续操作
            String tablename = "WM_Call";
            wareHouseDao.updateauditDateBygidAndTablename(wmc.getGid(),tablename);

            wareHouseDao.updateOutNumberBynumberCall(wmc.getGid(),"WM_Call_C");//修改已调出数量，为应调出数量
            wareHouseDao.updateCallState(wmc.getGid());// 更改调拨单状态
            wareHouseDao.emiInsert(wmoo);// 插入其他入主表
            wareHouseDao.emiInsert(wmooclist);// 插入其他入子表
            wareHouseDao.emiInsert(wmoh);// 插入其他出主表
            wareHouseDao.emiInsert(wmohclist);// 插入其他出子表
            wareHouseDao.batchUpdate(sqls);// 回填调拨单已出库数量

            updateStocksEntity(newAsList);// 修改货位现存量
            if (wmBatchs.size() > 0) { // 增加批次
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;

    }



    /**
    * @Desc 修改调拨单
    * @author yurh
    * @create 2018-05-08 15:42:49
    **/
    public JSONObject updateCall(wmCall wmc, List<wmCallC> clist, WmOthersout wmoo, WmOtherwarehouse wmoh, List<WmOthersoutC> wmooclist, List<WmOtherwarehouseC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList, String[] sqls, boolean flag) {
        JSONObject jobj = new JSONObject();
        List<wmCallC> wmCallClist = wareHouseDao.getWmCallDetailByWMS(wmc.getGid());//处理之前保存的货位现存量 做合并
        for (int i = 0; i < wmCallClist.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((wmCallC) wmCallClist.get(i)).getGoodsUid().toString());

            WmAllocationstock wmcat = new WmAllocationstock();// //出仓库的 -》货位现存量入
            wmcat.setBatch(CommonUtil.isNullObject(((wmCallC) wmCallClist.get(i)).getBatch()) ? null : ((wmCallC) wmCallClist.get(i)).getBatch().toString());
            AaGoodsallocation gaOut = cacheCtrlService.getGoodsAllocation(((wmCallC) wmCallClist.get(i)).getOutgoodsAllocationUid().toString());
            wmcat.setGoodsallocationcode(gaOut.getCode());
            wmcat.setGoodsallocationuid(gaOut.getGid());
            wmcat.setWhCode(gaOut.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()));
            wmcat.setOrggid(wmc.getOrgGid());
            wmcat.setSobgid(wmc.getSobGid());


            WmAllocationstock wmcat2 = new WmAllocationstock();// //入仓库的 -》货位现存量出
            wmcat2.setBatch(CommonUtil.isNullObject(((wmCallC) wmCallClist.get(i)).getBatch()) ? null : ((wmCallC) wmCallClist.get(i)).getBatch().toString());
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((wmCallC) wmCallClist.get(i)).getIngoodsAllocationUid().toString());
            wmcat2.setGoodsallocationcode(gaIn.getCode());
            wmcat2.setGoodsallocationuid(gaIn.getGid());
            wmcat2.setWhCode(gaIn.getWhcode());
            wmcat2.setGoodsuid(good.getGid());
            wmcat2.setGoodscode(good.getGoodscode());
            wmcat2.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()).negate());//取相反数
            wmcat2.setOrggid(wmc.getOrgGid());
            wmcat2.setSobgid(wmc.getSobGid());



            if (!CommonUtil.isNullObject(((wmCallC) wmCallClist.get(i)).getBatch())) { // 判断是否有批次，有则添加到批次表

                WmBatch wmb=new WmBatch();// 出仓库批次  入
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaOut.getGid());
                wmb.setBatch(wmcat.getBatch());
                wmb.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()));
                wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                wmb.setRecordDate(new Timestamp(new Date().getTime()));


                WmBatch wmb2=new WmBatch();// 入仓库批次  出
                wmb2.setGid(UUID.randomUUID().toString());
                wmb2.setGoodsUid(good.getGid());
                wmb2.setGoodsAllocationUid(gaIn.getGid());
                wmb2.setBatch(wmcat.getBatch());
                wmb2.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()).negate());
                wmb2.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
                wmb2.setRecordDate(new Timestamp(new Date().getTime()));


                wmBatchs.add(wmb);
                wmBatchs.add(wmb2);
            }
            asList.add(wmcat);
            asList.add(wmcat2);

        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);


        if(flag){
            //存在审批的情况
            //判断是否存在已审批记录，如果存在，则无法删除
            List list = wareHouseDao.getFollowInfoMovingByBillgid(wmc.getGid());
            int statustemp = 1;
            if(list != null && list.size() > 0 ){
                Map map = (Map) list.get(0);
                 statustemp = Integer.parseInt(map.get("status").toString());
            }


            if (list != null && list.size() >0 && statustemp != 2){
                jobj.put("success", 0);
                jobj.put("failInfor", "已提交审批，无法修改");
                return jobj;
            }else{
                //因为存在审批，审批后的数据没有到其他表中，所以不用处理

                if(wmc.getStatus() != null && wmc.getStatus() == 3){
                    int invoicestype = 3; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
                    String rdstylegid = wmc.getBusinessTypeUid();
                    String billgid =wmc.getGid();
                    this.postAudit(invoicestype,rdstylegid,billgid);
                    wmc.setStatus(0);
                }
                wareHouseDao.deleteCallC(wmc.getGid());//删除调拨单子表
                wareHouseDao.emiInsert(clist);//新增调拨单子表
                wareHouseDao.emiUpdate(wmc);// 更新调拨单主表
            }




        }else{
            //不存在审批的情况
            if (!isEnough) {
                jobj.put("success", 0);
                jobj.put("failInfor", "存在库存不满足的记录");
                return jobj;
            }
            wareHouseDao.deleteCallC(wmc.getGid());//删除调拨单子表
            wareHouseDao.deleteOthersOutByDBD(wmc.getGid());//删除其他出库单主表
            wareHouseDao.deleteOthersOutCByDBD(wmc.getGid());//删除其他出库单子表
            wareHouseDao.deleteOtherWarehouseByDBD(wmc.getGid());//删除其他入库单主表
            wareHouseDao.deleteOtherWarehouseCByDBD(wmc.getGid());//删除其他入库单子表

            wareHouseDao.emiUpdate(wmc);// 更新调拨单主表
            wareHouseDao.emiInsert(clist);//新增调拨单子表
            wareHouseDao.emiInsert(wmoo);//新增调拨单子表
            wareHouseDao.emiInsert(wmooclist);// 插入其他入子表
            wareHouseDao.emiInsert(wmoh);// 插入其他出主表
            wareHouseDao.emiInsert(wmohclist);// 插入其他出子表
            updateStocksEntity(newAsList);// 修改货位现存量
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;


    }

    /**
    * @Desc 删除调拨单
    * @author yurh
    * @create 2018-05-09 09:31:53
    **/
    public JSONObject deleteCall(wmCall wmc, boolean flag) {
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        JSONObject jobj = new JSONObject();
        List<wmCallC> wmCallClist = wareHouseDao.getWmCallDetailByWMS(wmc.getGid());
        for (int i = 0; i < wmCallClist.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(((wmCallC) wmCallClist.get(i)).getGoodsUid().toString());

            WmAllocationstock wmcat = new WmAllocationstock();// //出仓库的 -》货位现存量入
            wmcat.setBatch(CommonUtil.isNullObject(((wmCallC) wmCallClist.get(i)).getBatch()) ? null : ((wmCallC) wmCallClist.get(i)).getBatch().toString());
            AaGoodsallocation gaOut = cacheCtrlService.getGoodsAllocation(((wmCallC) wmCallClist.get(i)).getOutgoodsAllocationUid().toString());
            wmcat.setGoodsallocationcode(gaOut.getCode());
            wmcat.setGoodsallocationuid(gaOut.getGid());
            wmcat.setWhCode(gaOut.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()));
            wmcat.setOrggid(wmc.getOrgGid());
            wmcat.setSobgid(wmc.getSobGid());


            WmAllocationstock wmcat2 = new WmAllocationstock();// //入仓库的 -》货位现存量出
            wmcat2.setBatch(CommonUtil.isNullObject(((wmCallC) wmCallClist.get(i)).getBatch()) ? null : ((wmCallC) wmCallClist.get(i)).getBatch().toString());
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(((wmCallC) wmCallClist.get(i)).getIngoodsAllocationUid().toString());
            wmcat2.setGoodsallocationcode(gaIn.getCode());
            wmcat2.setGoodsallocationuid(gaIn.getGid());
            wmcat2.setWhCode(gaIn.getWhcode());
            wmcat2.setGoodsuid(good.getGid());
            wmcat2.setGoodscode(good.getGoodscode());
            wmcat2.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()).negate());//取相反数
            wmcat2.setOrggid(wmc.getOrgGid());
            wmcat2.setSobgid(wmc.getSobGid());



            if (!CommonUtil.isNullObject(((wmCallC) wmCallClist.get(i)).getBatch())) { // 判断是否有批次，有则添加到批次表

                WmBatch wmb=new WmBatch();// 出仓库批次  入
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaOut.getGid());
                wmb.setBatch(wmcat.getBatch());
                wmb.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()));
                wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                wmb.setRecordDate(new Timestamp(new Date().getTime()));


                WmBatch wmb2=new WmBatch();// 入仓库批次  出
                wmb2.setGid(UUID.randomUUID().toString());
                wmb2.setGoodsUid(good.getGid());
                wmb2.setGoodsAllocationUid(gaIn.getGid());
                wmb2.setBatch(wmcat.getBatch());
                wmb2.setNumber(new BigDecimal(((wmCallC) wmCallClist.get(i)).getNumber().toString()).negate());
                wmb2.setRedBlueFlag(0);//1、蓝字单据，0、红字单据
                wmb2.setRecordDate(new Timestamp(new Date().getTime()));


                wmBatchs.add(wmb);
                wmBatchs.add(wmb2);
            }
            asList.add(wmcat);
            asList.add(wmcat2);

        }




        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);




        if(flag){
            //存在审批的情况

            //判断是否存在已审批记录，如果存在，则无法删除
            List list = wareHouseDao.getFollowInfoMovingByBillgid(wmc.getGid());
            if (list != null && list.size() >0){
                jobj.put("success", 0);
                jobj.put("failInfor", "已提交审批，无法删除");
                return jobj;
            }else{
                wareHouseDao.deleteCallC(wmc.getGid());//删除调拨单子表
                wareHouseDao.deleteCall(wmc.getGid());//删除调拨单主表
            }


        }else{
            //不存在审批的情况
            if (!isEnough) {
                jobj.put("success", 0);
                jobj.put("failInfor", "存在库存不满足的记录");
                return jobj;
            }
            wareHouseDao.deleteOthersOutByDBD(wmc.getGid());//删除其他出库单主表
            wareHouseDao.deleteOthersOutCByDBD(wmc.getGid());//删除其他出库单子表
            wareHouseDao.deleteOtherWarehouseByDBD(wmc.getGid());//删除其他入库单主表
            wareHouseDao.deleteOtherWarehouseCByDBD(wmc.getGid());//删除其他入库单子表
            wareHouseDao.deleteCallC(wmc.getGid());//删除调拨单子表
            wareHouseDao.deleteCall(wmc.getGid());//删除调拨单主表
            updateStocksEntity(newAsList);// 修改货位现存量
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }



        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;

    }

    public PageBean getallocationlist(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getallocationlist(pageIndex,pageSize,condition);
    }

    public List getcolumns() {
        return wareHouseDao.getcolumns();
    }

    public PageBean getallocationlistMy(int pageIndex, int pageSize, String condition, String userid) {
        return wareHouseDao.getallocationlistMy(pageIndex,pageSize,condition,userid);
    }

    public PageBean getOthersScrapList(int pageIndex, int pageSize, String condition) {
        return wareHouseDao.getOthersScrapList(pageIndex, pageSize,
                condition);
    }

    public List getOthersScrapClist(String gid) {
        return wareHouseDao.getOthersScrapClist(gid);
    }

    public Map findOtherScrap(String otherScrapgid, String orgId, String sobId) {
        return wareHouseDao.findOtherScrap(otherScrapgid, orgId, sobId);


    }

    public JSONObject deleteOthersScrap(WmOthersscrap wmoh, boolean flag) {
        List<WmAllocationstock> asList = new ArrayList<WmAllocationstock>();
        List<WmBatch> wmBatchs = new ArrayList<WmBatch>();
        List othersScrapC = wareHouseDao.getOthersScrapClist(wmoh.getGid());
        for (int i = 0; i < othersScrapC.size(); i++) {
            AaGoods good = cacheCtrlService.getGoods(String.valueOf(((Map) othersScrapC.get(i)).get("goodsUid")));
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(((Map) othersScrapC.get(i)).get("batch").toString()));
            AaGoodsallocation gaIn = cacheCtrlService
                    .getGoodsAllocation(((Map) othersScrapC.get(i)).get(
                            "goodsAllocationUid").toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(((Map) othersScrapC.get(i)).get("number").toString()));

            wmcat.setOrggid(wmoh.getOrgGid());
            wmcat.setSobgid(wmoh.getSobGid());
            if (!CommonUtil.isNullObject(((Map) othersScrapC.get(i)).get("batch"))) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(((Map) othersScrapC.get(i)).get("batch").toString()));
                wmb.setNumber(new BigDecimal(0).subtract(new BigDecimal(((Map) othersScrapC.get(i)).get("number").toString())));

                if ((new BigDecimal(0).subtract(new BigDecimal(((Map) othersScrapC.get(i)).get("number").toString()))).compareTo(new BigDecimal(0)) >= 0) {//1、蓝字单据，0、红字单据
                    wmb.setRedBlueFlag(1);
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);



        JSONObject jobj = new JSONObject();
        if(flag){
            //存在审批的情况

            //判断是否存在已审批记录，如果存在，则无法删除
            List list = wareHouseDao.getFollowInfoMovingByBillgid(wmoh.getGid());
            if (list != null && list.size() >0){
                jobj.put("success", 0);
                jobj.put("failInfor", "已提交审批，无法删除");
                return jobj;
            }else{
                wareHouseDao.deleteOthersScrapC(wmoh.getGid());// 删除报废单子表
                wareHouseDao.deleteOthersScrap(wmoh.getGid());// 删除报废单主表
            }


        }else{
            //不存在审批的情况
            if (!isEnough) {
                jobj.put("success", 0);
                jobj.put("failInfor", "存在库存不满足的记录");
                return jobj;
            }
            wareHouseDao.deleteOthersScrapC(wmoh.getGid());// 删除报废单子表
            wareHouseDao.deleteOthersScrap(wmoh.getGid());// 删除报废单主表
            updateStocksEntity(newAsList);// 修改货位现存量
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }






        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;

    }

    public JSONObject updateOthersScrap(WmOthersscrap wmoh, List<WmOthersscrapC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList, boolean flag) {
        List<WmOthersscrapC> othersScrapC = wareHouseDao.getOthersScrapClist(wmoh.getGid());
        for (int i = 0; i < othersScrapC.size(); i++) {
            WmOthersscrapC wmOthersscrapC  = othersScrapC.get(i);
            AaGoods good = cacheCtrlService.getGoods(wmOthersscrapC.getGoodsUid().toString());
            WmAllocationstock wmcat = new WmAllocationstock();// //货位现存量入
            wmcat.setBatch(CommonUtil.Obj2String(wmOthersscrapC.getBatch().toString()));
            AaGoodsallocation gaIn = cacheCtrlService.getGoodsAllocation(wmOthersscrapC.getGoodsAllocationUid().toString());
            wmcat.setGoodsallocationcode(gaIn.getCode());
            wmcat.setGoodsallocationuid(gaIn.getGid());
            wmcat.setWhCode(gaIn.getWhcode());
            wmcat.setGoodsuid(good.getGid());
            wmcat.setGoodscode(good.getGoodscode());
            wmcat.setNumber(new BigDecimal(wmOthersscrapC.getNumber().toString()));

            wmcat.setOrggid(wmoh.getOrgGid());
            wmcat.setSobgid(wmoh.getSobGid());
            if (!CommonUtil.isNullObject(wmOthersscrapC.getBatch())) { // 判断是否有批次，有则添加到批次表
                WmBatch wmb = new WmBatch();
                wmb.setGid(UUID.randomUUID().toString());
                wmb.setGoodsUid(good.getGid());
                wmb.setGoodsAllocationUid(gaIn.getGid());
                wmb.setBatch(CommonUtil.Obj2String(wmOthersscrapC.getBatch().toString()));
                wmb.setNumber(new BigDecimal(wmOthersscrapC.getNumber().toString()));
                if ((new BigDecimal(0).subtract(new BigDecimal(wmOthersscrapC.getNumber().toString()))).compareTo(new BigDecimal(0)) >= 0) {
                    wmb.setRedBlueFlag(1);//1、蓝字单据，0、红字单据
                } else {
                    wmb.setRedBlueFlag(0);
                }
                wmb.setRecordDate(new Timestamp(new Date().getTime()));
                wmBatchs.add(wmb);
            }

            asList.add(wmcat);
        }

        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if(flag){
            //存在审批的情况
            //判断是否存在已审批记录，如果存在，则无法删除
            List list = wareHouseDao.getFollowInfoMovingByBillgid(wmoh.getGid());
            int statustemp = 1;
            if(list != null && list.size() > 0 ){
                Map map = (Map) list.get(0);
                statustemp = Integer.parseInt(map.get("status").toString());
            }

            if (list != null && list.size() >0 && statustemp != 2){
                jobj.put("success", 0);
                jobj.put("failInfor", "已提交审批，无法修改");
                return jobj;
            }
            else{
                //因为存在审批，审批后的数据没有到其他表中，所以不用处理
                if(wmoh.getStatus() != null && wmoh.getStatus() == 3){
                    int invoicestype = 4; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
                    String rdstylegid = wmoh.getBusinessTypeUid();
                    String billgid =wmoh.getGid();
                    this.postAudit(invoicestype,rdstylegid,billgid);
                    wmoh.setStatus(0);
                }
                wareHouseDao.deleteOthersScrapC(wmoh.getGid());
                boolean suc = wareHouseDao.emiUpdate(wmoh);// 更新其他入主表
                wareHouseDao.emiInsert(wmohclist);// 插入其他入子表

            }
        }else{
            //不存在审批的情况
            if (!isEnough) {
                jobj.put("success", 0);
                jobj.put("failInfor", "存在库存不满足的记录");
                return jobj;
            }
            wareHouseDao.deleteOthersScrapC(wmoh.getGid());
            boolean suc = wareHouseDao.emiUpdate(wmoh);// 更新其他入主表
            wareHouseDao.emiInsert(wmohclist);// 插入其他入子表
            updateStocksEntity(newAsList);// 修改货位现存量
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;

    }

    public JSONObject addOthersScrap(WmOthersscrap wmoh, List<WmOthersscrapC> wmohclist, List<WmBatch> wmBatchs, List<WmAllocationstock> asList) {
        //出库时判断库存是否满足
        List<WmAllocationstock> newAsList = getNewCurrentStockList(asList);//去重
        boolean isEnough = isEnoughStock(newAsList);

        JSONObject jobj = new JSONObject();

        if (!isEnough) {
            jobj.put("success", 0);
            jobj.put("failInfor", "存在库存不满足的记录");
            return jobj;
        }


        boolean suc = wareHouseDao.emiInsert(wmoh);
        suc = wareHouseDao.emiInsert(wmohclist);//报废单


        int invoicestype = 4; //1：领用申请单  2：材料出库单     3：调拨单  4：报废单
        String rdstylegid = wmoh.getBusinessTypeUid();
        String billgid =wmoh.getGid();
        boolean flag = this.postAudit(invoicestype,rdstylegid,billgid);
        if(!flag){
            //不存在审批规则，则执行后续操作
            //更新主表单据的审批时间为单据时间 billDate->auditDate
            String tablename = "WM_OthersScrap";//报废单
            wareHouseDao.updateauditDateBygidAndTablename(wmoh.getGid(),tablename);
            updateStocksEntity(newAsList);// 修改货位现存量
            // 增加批次
            if (wmBatchs.size() > 0) {
                wareHouseDao.addWmBatch(wmBatchs);
            }
        }

        jobj.put("success", 1);
        jobj.put("failInfor", "");
        return jobj;

    }

    public PageBean getOthersScrapListMy(int pageIndex, int pageSize, String condition, String userid) {
        return wareHouseDao.getOthersScrapListMy(pageIndex, pageSize,
                condition,userid);
    }


    /**
     * 获取当前计算机的mac地址
     * @param request
     * @return
     */
        /**
         * 获取Ip地址
         * @param request
         * @return
         */
        private  String getIpAdrress(HttpServletRequest request) {
            try {
                Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                while (enumeration.hasMoreElements()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    NetworkInterface networkInterface = enumeration.nextElement();
                    if (networkInterface != null) {
                        byte[] bytes = networkInterface.getHardwareAddress();
                        if (bytes != null) {
                            for (int i = 0; i < bytes.length; i++) {
                                if (i != 0) {
                                    stringBuffer.append("-");
                                }
                                int tmp = bytes[i] & 0xff; // 字节转换为整数
                                String str = Integer.toHexString(tmp);
                                if (str.length() == 1) {
                                    stringBuffer.append("0" + str);
                                } else {
                                    stringBuffer.append(str);
                                }
                            }
                            String mac = stringBuffer.toString().toUpperCase();
                            System.out.println(mac);
                            return mac;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    public PageBean getTransceiversList(int pageIndex, int pageSize, String condition, String startMouth, String endMouth, String whUid, String[] warehouses, HttpServletRequest request) {

        String ip = this.getIpAdrress(request);
        System.out.println(ip);

        String sql = "";
        sql+=" DECLARE @MyTable TypeWhUidList ";
        sql+=" INSERT INTO @MyTable(whUid) ";
        sql+="     VALUES ('-1')";
        if(warehouses != null && warehouses.length >0 && !"".equals(warehouses[0])){

            for(int i = 0;i<warehouses.length;i++){
                String tempcode = warehouses[i];
                sql += " ,('"+tempcode+"') ";
            }

        }
        sql+=" EXEC getTransceiversRealToPage '"+startMouth+"','"+endMouth+"',@MyTable, '"+ip+"'";


        wareHouseDao.execute(sql);

        PageBean pageBean =  wareHouseDao.getTransceiversList(pageIndex, pageSize, condition);
        List list = pageBean.getList();
        if(list != null && list.size()>0){
            for(int i = 0 ;i<list.size();i++){
                WmTransceiversPage wmTransceiversPage = (WmTransceiversPage) list.get(i);
                AaGoods good = cacheCtrlService.getGoods(wmTransceiversPage.getGoodsuid());
                if(good!=null){
                    wmTransceiversPage.setGoodsStandard(good.getGoodsstandard());
                    wmTransceiversPage.setGoodscode(good.getGoodscode());
                    Unit unit = cacheCtrlService.getUnit(good.getGoodsunit());
                    wmTransceiversPage.setClassificationName(unit.getUnitname());
                }
            }


//        List list = pageBean.getList();
//        List<String> goodsUidList = new ArrayList<String>();//定义一个goodsUid集合
//        if(list != null && list.size()>0){
//            for(int i = 0 ;i<list.size();i++){
//                WmTransceivers wmTransceivers = (WmTransceivers) list.get(i);
//                String goodsUid = wmTransceivers.getGoodsuid();
//                if(!goodsUidList.contains(goodsUid)){
//                    goodsUidList.add(goodsUid);
//                }
//
//            }
//
//
//            List<WmTransceivers> realWmTList = new ArrayList<WmTransceivers>();
//            for(int i = 0 ;i<goodsUidList.size();i++){
//                String realGoodsuid = goodsUidList.get(i);
//                WmTransceivers wmTransceivers1 = new WmTransceivers();//放在循环外面，说明下面只循环的是realGoodsuid当前物料id的东西，变相更新
//                BigDecimal mouthInNumber = new BigDecimal(0);
//                BigDecimal mouthOutNumber = new BigDecimal(0);
//                for(int j = 0 ;j<list.size();j++){
//                    WmTransceivers wmTransceivers = (WmTransceivers) list.get(j);
//                    if(realGoodsuid.equals(wmTransceivers.getGoodsuid())){
//                        AaGoods good = cacheCtrlService.getGoods(realGoodsuid);
//                        if(good!=null){
//                            Unit unit  = cacheCtrlService.getUnit(good.getGoodsunit());
//                            wmTransceivers1.setClassificationName(unit.getUnitname());//主单位名称
//                            wmTransceivers1.setGoodsName(good.getGoodsname());
//                            wmTransceivers1.setGoodscode(good.getGoodscode());
//                            wmTransceivers1.setGoodsStandard(good.getGoodsstandard());
//
//                        }
//                        if(startMouth.equals(wmTransceivers.getSearchDate())){
//                            wmTransceivers1.setTopTotalNumber(wmTransceivers.getTopTotalNumber());
//                        }
//                        if(endMouth.equals(wmTransceivers.getSearchDate())){
//                            wmTransceivers1.setEndTotalNumber(wmTransceivers.getEndTotalNumber());
//                        }
//
//                        //当月入库数量相加
//                        BigDecimal mouthInNumber1 = wmTransceivers.getMouthInNumber();
//                        mouthInNumber =  mouthInNumber.add(mouthInNumber1);
//                        //当月出库数量相加
//                        BigDecimal mouthOutNumber1 = wmTransceivers.getMouthInNumber();
//                        mouthOutNumber =  mouthOutNumber.add(mouthOutNumber1);
//
//
//                    }
//                }
//                wmTransceivers1.setMouthInNumber(mouthInNumber);
//                wmTransceivers1.setMouthOutNumber(mouthOutNumber);
//
//                realWmTList.add(wmTransceivers1);
//
//            }


            //realWmTList是最终的数据List


    }
    return pageBean;
    }

    public List<Map> getReportListTransceivers(int aa, int bb, String serachEm, String condition, HttpServletRequest request) {
          String macString =  this.getIpAdrress(request);
          String condition1  = " and owh.ip = '"+macString+"' ";
        List list  =  wareHouseDao.getTransceiversListForAll(condition1);
//        if(list != null && list.size()>0) {
//            for (int i = 0; i < list.size(); i++) {
//                Map map  = (Map) list.get(i);
//                AaGoods good = cacheCtrlService.getGoods(wmTransceiversPage.getGoodsuid());
//                if (good != null) {
//                    wmTransceiversPage.setGoodsStandard(good.getGoodsstandard());
//                    wmTransceiversPage.setGoodscode(good.getGoodscode());
//                    Unit unit = cacheCtrlService.getUnit(good.getGoodsunit());
//                    wmTransceiversPage.setClassificationName(unit.getUnitname());
//                }
//            }
//        }
        return list;
    }


}
