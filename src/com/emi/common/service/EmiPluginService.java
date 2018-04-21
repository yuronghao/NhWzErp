package com.emi.common.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.bean.core.ITreeQuery;
import com.emi.common.bean.core.TreeType;
import com.emi.common.dao.EmiPluginDao;
import com.emi.common.service.tree.BatchStockTreeQuery;
import com.emi.common.service.tree.DepartmentTreeQuery;
import com.emi.common.service.tree.EquipmentTreeQuery;
import com.emi.common.service.tree.GoodsTreeQuery;
import com.emi.common.service.tree.GroupTreeQuery;
import com.emi.common.service.tree.MouldTreeQuery;
import com.emi.common.service.tree.PersonTreeQuery;
import com.emi.common.service.tree.PriorAttributeTreeQuery;
import com.emi.common.service.tree.ProcessTreeQuery;
import com.emi.common.service.tree.ProduceOrderListTreeQuery;
import com.emi.common.service.tree.ProviderCustomerTreeQuery;
import com.emi.common.service.tree.RdStyleTreeQuery;
import com.emi.common.service.tree.UserTreeQuery;
import com.emi.common.service.tree.WareHouseTreeQuery;
import com.emi.common.service.tree.WorkCenterTreeQuery;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaBarcodeRule;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaUserDefine;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesAaStation;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.WmBillType;

public class EmiPluginService {
	private EmiPluginDao emiPluginDao;
	private CacheCtrlService cacheCtrlService;

	public EmiPluginDao getEmiPluginDao() {
		return emiPluginDao;
	}

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public void setEmiPluginDao(EmiPluginDao emiPluginDao) {
		this.emiPluginDao = emiPluginDao;
	}

	public String getPrePageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getPrePageGid(tableName,idColumn,thisGid,condition);
	}

	public String getNextPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getNextPageGid(tableName, idColumn,thisGid,condition);
	}

	public String getFirstPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getFirstPageGid(tableName,idColumn,thisGid,condition);
	}

	public String getLastPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getLastPageGid(tableName,idColumn,thisGid,condition);
	}
	
	public Mould getMouldByBarcode(String barcode,String produceProcessRoutecGid){
		return emiPluginDao.getMouldByBarcode(barcode, produceProcessRoutecGid);
	}
	
	public void test() {
		emiPluginDao.test();
	}
	/**
	 * @category 调用本地存储过程 返回编码
	 *2016 2016年4月11日下午3:30:20
	 *String
	 *宋银海
	 */
	public String getBillId(String billType){

		int year=DateUtil.getToYear();
		int month=DateUtil.getToMonth();
		String strMonth;
		if(String.valueOf(month).length()==1){
			strMonth="0"+String.valueOf(month);
		}else{
			strMonth=String.valueOf(month);
		}

		String currentId=emiPluginDao.getBillId(billType, year+strMonth);

		String billId=billType+year+strMonth+currentId;

		return billId;
	}
	
	//获得流水号
	public String getSerialNumber(String billType){
		
		int year=DateUtil.getToYear();
		int month=DateUtil.getToMonth();
		int day=DateUtil.getToday();
		String strMonth;
		String strDay;
		if(String.valueOf(month).length()==1){
			strMonth="0"+String.valueOf(month);
		}else{
			strMonth=String.valueOf(month);
		}
		
		if(String.valueOf(day).length()==1){
			strDay="0"+String.valueOf(day);
		}else{
			strDay=String.valueOf(day);
		}
		
		String currentId=emiPluginDao.getBillId(billType, year+strMonth+strDay);
		
		return currentId;
	}
	
	//获得年月日
	public String getYearMonthDay(){
		
		int year=DateUtil.getToYear();
		int month=DateUtil.getToMonth();
		int day=DateUtil.getToday();
		String strYear;
		String strMonth;
		String strDay;
		
		strYear=String.valueOf(year).substring(2, 4);
		
		if(String.valueOf(month).length()==1){
			strMonth="0"+String.valueOf(month);
		}else{
			strMonth=String.valueOf(month);
		}
		
		if(String.valueOf(day).length()==1){
			strDay="0"+String.valueOf(day);
		}else{
			strDay=String.valueOf(day);
		}
		
		return strYear+strMonth+strDay;
	}
	
	/**
	 * @category 根据货位gid 查询仓库
	 *2016 2016年4月11日下午3:50:19
	 *AaWarehouse
	 *宋银海
	 */
	public AaWarehouse getAaWarehouse(String goodsAllocationUid){
		String condition=" and ga.gid='"+goodsAllocationUid+"'";
		return emiPluginDao.getAaWarehouse(condition);
	}

	/**
	 * @category 树插件的初始化
	 * 2016年4月13日 下午4:38:12 
	 * @author zhuxiaochen
	 * @param type
	 * @return
	 */
	public ITreeQuery getTreeQuery(String type,HttpServletRequest request) {
		ITreeQuery treeQuery = null; 
		if(TreeType.GOODS.equals(type)){
			//物料选择
			treeQuery = new GoodsTreeQuery(request);
			
		}else if(TreeType.PROCESS.equals(type)){
			//工序选择
			treeQuery = new ProcessTreeQuery(request);
		}else if(TreeType.WORKCENTER.equals(type)){
			//工作中心选择
			treeQuery = new WorkCenterTreeQuery(request);
		}else if(TreeType.PROVIDERCUSTOMER.equals(type)){
			//
			treeQuery = new ProviderCustomerTreeQuery(request);
		}else if(TreeType.PRIORATTRIBUTE.equals(type)){
			//
			treeQuery = new PriorAttributeTreeQuery(request);
		}else if(TreeType.PERSON.equals(type)){
			//人员选择
			treeQuery = new PersonTreeQuery(request);
		}else if(TreeType.GROUP.equals(type)){
			//组选择
			treeQuery = new GroupTreeQuery(request);
		}else if(TreeType.DEPT.equals(type)){
			//部门选择
			treeQuery = new DepartmentTreeQuery(request);
		}else if(TreeType.EQUIPMENT.equals(type)){
			//设备选择
			treeQuery = new EquipmentTreeQuery(request);
		}else if(TreeType.WAREHOUSE.equals(type)){
			//仓库选择
			treeQuery = new WareHouseTreeQuery(request);
		}else if(TreeType.MOULD.equals(type)){
			//模具选择
			treeQuery = new MouldTreeQuery(request);
		}else if(TreeType.PRODUCEORDERLIST.equals(type)){
			//选择订单
			treeQuery = new ProduceOrderListTreeQuery(request);
		}else if(TreeType.BATCHSTOCK.equals(type)){
			//选择批次库存
			treeQuery = new BatchStockTreeQuery(request);
		}else if(TreeType.USER.equals(type)){
			//用户
			treeQuery = new UserTreeQuery(request);
		}else if(TreeType.RDSTYLE.equals(type)){
			//出入库类别
			treeQuery = new RdStyleTreeQuery(request);
		}
		// TODO 其他
		else{
			try {
				throw new Exception("未找到对应的实现类，请检查【EmiPluginService.getTreeQuery()】！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return treeQuery;
	}
	
	
	/**
	 * @category 根据物品条码获得物品信息
	 *2016 2016年4月14日下午3:10:15
	 *AaGoods
	 *宋银海
	 */
	public AaGoods getAaGoods(String barcode) {
		String condition="  goodsbarcode='"+barcode+"'";
		return emiPluginDao.getAaGoods(condition);
	}
	
	/**
	 * @category 根据条码查询货位
	 *2016 2016年4月15日上午9:05:41
	 *AaGoodsallocation
	 *宋银海
	 */
	public AaGoodsallocation getAaGoodsallocation(String barcode) {
		String condition="  allocationBarCode='"+barcode+"'";
		return emiPluginDao.getAaGoodsallocation(condition);
	}
	
	
	/**
	 * @category 获得条码规则
	 *2016 2016年5月24日下午2:15:45
	 *List<AaBarcodeRule>
	 *宋银海
	 */
	public List<AaBarcodeRule> getAaBarcodeRule(String type){
		String condition=" type='"+type+"' and isUse=1 ";
		return emiPluginDao.getAaBarcodeRule(condition);
	}
	
	/**
	 * @category 获得用户自定义
	 *2016 2016年5月24日下午2:15:45
	 *List<AaBarcodeRule>
	 *宋银海
	 */
	public List<AaUserDefine> getAaUserDefineList(){
		String condition=" 1=1 ";
		return emiPluginDao.getAaUserDefineList(condition);
	}
	
	/**
	 * @category 获得用户自定义
	 *2016 2016年5月24日下午2:15:45
	 *List<AaBarcodeRule>
	 *宋银海
	 */
	public AaUserDefine getAaUserDefine(String code){
		String condition=" 1=1 and code='"+code+"'";
		return emiPluginDao.getAaUserDefine(condition);
	}
	
	
	/**
	 * @category 根据条码获得人员
	 *2016 2016年4月15日上午9:06:53
	 *AaPerson
	 *宋银海
	 */
	public AaPerson getAaPerson(String barcode){
		String condition=" barcode='"+barcode+"'";
		return emiPluginDao.getAaPerson(condition);
	}
	
	
	/**
	 * @category 根据条码获得工作中心
	 *2016 2016年4月15日上午10:52:02
	 *MesAaWorkcenter
	 *宋银海
	 */
	public MesAaWorkcenter getMesAaWorkcenter(String barcode){
		String condition=" barcode='"+barcode+"'";
		return emiPluginDao.getMesAaWorkcenter(condition);
	}
	
	
	/**
	 * @category 根据条码获得工作组
	 *2016 2016年4月15日上午10:52:02
	 *MesAaWorkcenter
	 *宋银海
	 */
	public AaGroup getMesAaGroup(String barcode){
		String condition=" barcode='"+barcode+"'";
		return emiPluginDao.getMesAaGroup(condition);
	}
	
	/**
	 * @category 根据条码获得工位(不用)
	 *2016 2016年4月15日上午9:28:43
	 *MesAaStation
	 *宋银海
	 */
	public MesAaStation getMesAaStation(String barcode){
		String condition=" barcode='"+barcode+"'";
		return emiPluginDao.getMesAaStation(condition);
	}
	
	
	/**
	 * 查询设备即工位
	 *2016 2016年7月8日下午3:29:17
	 *Equipment
	 *Administrator
	 */
	public Equipment getEquipment(String barcode){
		String condition=" barcode='"+barcode+"'";
		return emiPluginDao.getEquipment(condition);
	}
	
	/**
	 * @category 获得类型表
	 *2016 2016年4月19日下午2:31:51
	 *List<WmBillType>
	 *宋银海
	 */
	public List<WmBillType> getWmBillType(){
		return emiPluginDao.getWmBillType(" 1=1 ");
	}

	
	/**
	 * @category 故意错误回滚
	 *2016 2016年4月22日下午3:06:55
	 *void
	 *宋银海
	 */
	public void rollBackWhenError() {
			emiPluginDao.rollBackWhenError();
	}
	
	
	/**
	 * @category 获得参数
	 *2016 2016年4月23日上午9:27:26
	 *Map
	 *宋银海
	 */
	public Map getymsetting(String setName){
		String condition=" setName='"+setName+"'";
		return emiPluginDao.getymsetting(condition);
	}
	
	
	/**
	 * @category 根据单据类型获取默认类别信息
	 * 宋银海
	 * @return
	 */
	public Map getDefaultRdstyleByBillType(String billTypeCode){
		return emiPluginDao.getDefaultRdstyleByBillType(billTypeCode);
	}
	
	
	/**
	 * @category 获得库存信息
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean getAllocationStock(int pageIndex, int pageSize,String goodsUid,String goodsAllocationUid){
		String condition=" 1=1 ";
		if(!CommonUtil.isNullObject(goodsUid)){
			condition+=" and goodsUid='"+goodsUid+"'";
		}
		
		if(!CommonUtil.isNullObject(goodsAllocationUid)){
			condition+=" and goodsAllocationUid='"+goodsAllocationUid+"'";
		}
		
		
		PageBean pagebean=new PageBean();
		pagebean=emiPluginDao.getAllocationStock(condition, pageIndex, pageSize);
		
		List<Map> results=pagebean.getList();
		for(Map map:results){
			AaGoods aaGoods=cacheCtrlService.getGoods(map.get("goodsUid").toString());
			map.put("goodsCode", aaGoods.getGoodscode());
			map.put("goodsName", aaGoods.getGoodsname());
			map.put("goodsStandand", aaGoods.getGoodsstandard());
		}
		
		return pagebean;
	}
	
	
	public void printWithCodeSoft(String printerName,String template,JSONObject jobj){
		StringBuffer sb=new StringBuffer("");
		sb.append(printerName).append("|").append(template).append("|");
		
		Iterator it=jobj.keys();
		while(it.hasNext()){
			String key=(String)it.next();
			String value=jobj.get(key).toString();
			sb.append(key+"=").append(value).append("|");
		}
		
		String tmp=sb.toString();
		//System.out.println(sb.toString()+"==============CodeSoftDll.labpat==="+CodeSoftDll.labpath);
		boolean bool=createFile(tmp, Config.PRINTFILE+UUID.randomUUID()+".txt");
		System.out.println("文件生成结果："+bool);
		//String ss22="name=羊排33|weight=21.12kg|sncode=12345678901234567890|pdate=2015年09月16日|ptype=羊骨架";
		//CodeSoftDll.INSTANCE.PrintLabel(printerName, "E:\\kfcz.Lab", ss22);
		//CodeSoftDll.INSTANCE.PrintLabel(printerName, CodeSoftDll.labpath, java.net.URLEncoder.encode(tmp));
	}
	
	
	public  boolean createFile(String content,String filepath){
		BufferedWriter bw = null;
		FileWriter fw =null;
		String filename=System.currentTimeMillis()+".txt";
		File file=new File(filepath+filename);
		try{
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			bw.write(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				 if(bw!=null){
				   bw.close();
				 }
				 if(fw!=null){
					 fw.close();
				 }
				} catch (IOException e) {
				    e.printStackTrace();
			 }
		}
		if(file!=null&&file.exists()){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param uuid
	 * @return
	 * 获得uuid信息  防止重复提交
	 * 2017年4月10日
	 * Map
	 */
	public Map getUuidInfor(String uuid){
		return emiPluginDao.getUuidInfor(uuid);
	}
	
	
	public void insertUuidInfor(String uuid){
		 emiPluginDao.insertUuidInfor(uuid);
	}
	
}