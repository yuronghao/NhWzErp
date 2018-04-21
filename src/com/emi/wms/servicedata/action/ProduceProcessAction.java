package com.emi.wms.servicedata.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.emi.android.bean.ProcessReportScanRsp;
import com.emi.android.bean.ProcessStartScanRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.MesWmDispatchingorder;
import com.emi.wms.bean.MesWmDispatchingorderc;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmReportorder;
import com.emi.wms.bean.MesWmReportorderc;
import com.emi.wms.bean.QMCheckCbill;
import com.emi.wms.servicedata.service.AllocationService;
import com.emi.wms.servicedata.service.ProduceOrderService;
import com.emi.wms.servicedata.service.ProduceProcessService;

public class ProduceProcessAction extends BaseAction {
	private static final long serialVersionUID = -7428530941033137884L;
	Logger logger = Logger.getLogger(ProduceProcessAction.class);
	private ProduceProcessService produceprocessService;
	private CacheCtrlService cacheCtrlService;
	private AllocationService allocationService;
	private ProduceOrderService produceOrderService;
	private BasicSettingService basicSettingService;

public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}
public AllocationService getAllocationService() {
	return allocationService;
}
public void setAllocationService(AllocationService allocationService) {
	this.allocationService = allocationService;
}
public ProduceProcessService getProduceprocessService() {
	return produceprocessService;
}
public void setProduceprocessService(ProduceProcessService produceprocessService) {
	this.produceprocessService = produceprocessService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}

public void setProduceOrderService(ProduceOrderService produceOrderService) {
	this.produceOrderService = produceOrderService;
}
public String toAddproduceprocess(){
	String produceprocessgid = getParameter("produceprocessgid");
	Map produceprocess = produceprocessService.findproduceprocess(produceprocessgid);
	if(!CommonUtil.isNullObject(produceprocess)){
		AaGoods good = cacheCtrlService.getGoods(CommonUtil.Obj2String(((Map)produceprocess).get("goodsUid")) );
		((Map)produceprocess).put("good", good);
		List produceprocessc = produceprocessService.getproduceprocessclist(produceprocess.get("produceprocessgid").toString());
		setRequstAttribute("produceprocessc", produceprocessc);
	}
	List columns = allocationService.getcolumns();
	setRequstAttribute("columns", columns);
	setRequstAttribute("produceprocess", produceprocess);
	
	return "produceprocessAdd";
	}

public String toAdddispatchingorder(){
	String produceprocessgid = getParameter("produceprocessgid");
	Map produceprocess = produceprocessService.findproduceprocess(produceprocessgid);
	if(!CommonUtil.isNullObject(produceprocess)){
		AaGoods good = cacheCtrlService.getGoods(CommonUtil.Obj2String(((Map)produceprocess).get("goodsUid")));
		((Map)produceprocess).put("good", good);
		List dispatchingorderc = produceprocessService.getdispatchingorderclist(produceprocess.get("produceprocessgid").toString());
		for(int i=0;i<dispatchingorderc.size();i++){
			if(!CommonUtil.isNullObject(((Map)dispatchingorderc.get(i)).get("dispatchingObj"))){
				if(((Map)dispatchingorderc.get(i)).get("dispatchingObj").toString().equals("0")){
					AaPerson aaperson = cacheCtrlService.getPerson(((Map)dispatchingorderc.get(i)).get("personUnitVendorGid").toString());
					((Map)dispatchingorderc.get(i)).put("aaperson", aaperson);
				}
				if(((Map)dispatchingorderc.get(i)).get("dispatchingObj").toString().equals("1")){
					AaGroup aagroup = cacheCtrlService.getAaGroup(((Map)dispatchingorderc.get(i)).get("personUnitVendorGid").toString());
					((Map)dispatchingorderc.get(i)).put("aagroup", aagroup);
				}
				if(((Map)dispatchingorderc.get(i)).get("dispatchingObj").toString().equals("2")){
					AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(((Map)dispatchingorderc.get(i)).get("personUnitVendorGid").toString());
					((Map)dispatchingorderc.get(i)).put("aaprovidercustomer", aaprovidercustomer);
				};
			}
		}
		setRequstAttribute("dispatchingorderc", dispatchingorderc);
	}
	
	List columns = allocationService.getcolumns();
	setRequstAttribute("columns", columns);
	setRequstAttribute("produceprocess", produceprocess);
	return "dispatchingorderAdd";
	}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void addproduceprocess(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			MesWmProduceProcessroute produceprocess = new MesWmProduceProcessroute();
			String uuid = UUID.randomUUID().toString();
			produceprocess.setGid(uuid);
			/*produceprocess.setBillcode(getParameter("billCode"));
			produceprocess.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			produceprocess.setDepartmentuid(getParameter("depName"));
			produceprocess.setSalesmanuid(getParameter("perName"));
			produceprocess.setPurchasetype(new Integer(getParameter("purchasetype")));
			produceprocess.setNotes(getParameter("notes"));
			produceprocess.setRecordpersonuid(getParameter("recordPersonUid"));
			produceprocess.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			produceprocess.setSobgid(getSession().get("SobId").toString());
			produceprocess.setOrggid(getSession().get("OrgId").toString());
			produceprocess.setFlag(0);*/
			
			List<QMCheckCbill> produceprocesscs = new ArrayList<QMCheckCbill>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] number = getRequest().getParameterValues("number");
			String[] amount = getRequest().getParameterValues("amount");
			String[] demandDate = getRequest().getParameterValues("demandDate");
			for(int i=0;i<goodsUid.length;i++){
			QMCheckCbill produceprocessc = new QMCheckCbill();
			/*produceprocessc.setPurchaseproduceprocessuid(uuid);
			produceprocessc.setGoodsuid(goodsUid[i]);
			produceprocessc.setNumber(new BigDecimal(number[i]));
			produceprocessc.setAmount(new BigDecimal(amount[i]));
			produceprocessc.setDemanddate(demandDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(demandDate[i], "yyyy-MM-dd").getTime()):null);*/
			produceprocesscs.add(produceprocessc);
			}
			}
			
			boolean suc = produceprocessService.addproduceprocess(produceprocess);
			boolean suc1 = produceprocessService.addproduceprocessc(produceprocesscs);
			if(suc&&suc1){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("保存失败");
			}
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void updateproduceprocess(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			MesWmProduceProcessroute produceprocess = new MesWmProduceProcessroute();
			produceprocess.setGid(getParameter("purchasegid"));
			/*produceprocess.setBillcode(getParameter("billCode"));
			produceprocess.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			produceprocess.setDepartmentuid(getParameter("depName"));
			produceprocess.setSalesmanuid(getParameter("perName"));
			produceprocess.setPurchasetype(new Integer(getParameter("purchasetype")));
			produceprocess.setNotes(getParameter("notes"));
			produceprocess.setRecordpersonuid(getParameter("recordPersonUid"));
			produceprocess.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			produceprocess.setSobgid(getSession().get("SobId").toString());
			produceprocess.setOrggid(getSession().get("OrgId").toString());
			produceprocess.setFlag(0);*/
			
			List<QMCheckCbill> produceprocesscs = new ArrayList<QMCheckCbill>();
			//produceprocessService.deletebooks(getParameter("purchasegid"));
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] gid = getRequest().getParameterValues("gid");
			String[] number = getRequest().getParameterValues("number");
			String[] amount = getRequest().getParameterValues("amount");
			String[] demandDate = getRequest().getParameterValues("demandDate");
			for(int i=0;i<goodsUid.length;i++){
			QMCheckCbill produceprocessc = new QMCheckCbill();
			produceprocessc.setGid(gid[i]);
			/*produceprocessc.setPurchaseproduceprocessuid(getParameter("purchasegid"));
			produceprocessc.setGoodsuid(goodsUid[i]);
			produceprocessc.setNumber(new BigDecimal(number[i]));
			produceprocessc.setAmount(new BigDecimal(amount[i]));
			produceprocessc.setDemanddate(demandDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(demandDate[i], "yyyy-MM-dd").getTime()):null);*/
			produceprocesscs.add(produceprocessc);
			}
			}
			
			boolean suc = produceprocessService.updateproduceprocess(produceprocess);
			boolean suc1 = produceprocessService.updateproduceprocessc(produceprocesscs);
			if(suc&&suc1){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("保存失败");
			}
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 
 * @category 生产订单流转卡列表
 * 2015年3月22日 上午8:18:30
 * @author 杨峥铖
 * @return
 */
public String getproduceprocesslist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String billkeyWord = getParameter("billkeyWord");//单据号关键字
	String goodskeyWord = getParameter("goodskeyWord");//物品关键字
	String barcodekeyWord = getParameter("barcodekeyWord");//条码关键字
	
	String condition = CommonUtil.combQuerySql("produceorder.billCode,produceorder.barCode", billkeyWord);
	
	if(CommonUtil.notNullString(goodskeyWord)){
		condition += " and produceorderc.goodsUid in (select gid from AA_Goods where goodscode like '%"+goodskeyWord+"%' or goodsname like '%"+goodskeyWord+"%' or goodsstandard like '%"+goodskeyWord+"%') ";
	}
	
	if(CommonUtil.notNullString(barcodekeyWord)){
		condition += " and produceprocess.gid in (SELECT  pprc.produceRouteGid from MES_WM_ProduceProcessRouteC pprc where barcode like '%"+barcodekeyWord+"%') ";
	}
	
	String orgId=getSession().get("OrgId").toString();//组织id
	String sobId=getSession().get("SobId").toString();//账套id
	
	//过滤物料（考虑到物料数据大，先查出来再匹配）
//	if(CommonUtil.notNullString(goodskeyWord)){
//		List<AaGoods> goodsList = basicSettingService.getGoodsPageBean(0, 0, "", orgId, sobId, CommonUtil.combQuerySql("goodscode,goodsname,goodsstandard", goodskeyWord)).getList();
//		String goodsIds = "";
//		for(AaGoods g : goodsList){
//			goodsIds += g.getGid()+',';
//		}
//		goodsIds = CommonUtil.cutLastString(goodsIds, ",");
//		String extsql = " and produceorderc.goodsUid in ('"+goodsIds.replaceAll(",", "','")+"')";
//		
//		condition = CommonUtil.combQuerySql("produceorder.billCode,produceorder.barCode", goodskeyWord,extsql);
//	}
	
	setRequstAttribute("billkeyWord",billkeyWord);
	setRequstAttribute("goodskeyWord",goodskeyWord);
	setRequstAttribute("barcodekeyWord",barcodekeyWord);
	
	PageBean produceprocesslist = produceprocessService.getproduceprocesslist(pageIndex,pageSize,condition);
	for(int i=0;i<produceprocesslist.getList().size();i++){
		if(!CommonUtil.isNullObject(((Map)produceprocesslist.getList().get(i)).get("goodsUid"))){
			AaGoods good = cacheCtrlService.getGoods(((Map)produceprocesslist.getList().get(i)).get("goodsUid").toString());
			((Map)produceprocesslist.getList().get(i)).put("good", good);
		}
		
	}
	setRequstAttribute("data", produceprocesslist);
	return "produceprocesslist";
}

public String getdispatchingorderlist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String keyWord = getParameter("keyWord");//搜索关键字
	String condition = CommonUtil.combQuerySql("produceorder.billCode,produceorder.barCode", keyWord);
//	if(CommonUtil.notNullString(keyWord)){
//		condition += " or produceorderc.goodsUid in (select gid from AA_Goods where goodscode like '%"+keyWord+"%' or goodsname like '%"+keyWord+"%' or goodsstandard like '%"+keyWord+"%')";
//	}
	String orgId=getSession().get("OrgId").toString();//组织id
	String sobId=getSession().get("SobId").toString();//账套id
	
	//过滤物料（考虑到物料数据大，先查出来再匹配）
	if(CommonUtil.notNullString(keyWord)){
		String conplus = "";
		List<AaGoods> goodsList = basicSettingService.getGoodsPageBean(0, 0, "", orgId, sobId, CommonUtil.combQuerySql("goodscode,goodsname,goodsstandard", keyWord), conplus).getList();
		String goodsIds = "";
		for(AaGoods g : goodsList){
			goodsIds += g.getGid()+',';
		}
		goodsIds = CommonUtil.cutLastString(goodsIds, ",");
		String extsql = " or produceorderc.goodsUid in ('"+goodsIds.replaceAll(",", "','")+"')";
		condition = CommonUtil.combQuerySql("produceorder.billCode,produceorder.barCode", keyWord,extsql);
	}
	setRequstAttribute("keyWord",keyWord);
	PageBean produceprocesslist = produceprocessService.getproduceprocesslist(pageIndex,pageSize,condition);
	for(int i=0;i<produceprocesslist.getList().size();i++){
		if(!CommonUtil.isNullObject(((Map)produceprocesslist.getList().get(i)).get("goodsUid"))){
			AaGoods good = cacheCtrlService.getGoods(((Map)produceprocesslist.getList().get(i)).get("goodsUid").toString());
			((Map)produceprocesslist.getList().get(i)).put("good", good);
		}
		
	}
	setRequstAttribute("data", produceprocesslist);
	return "dispatchingorderlist";
}

@SuppressWarnings("rawtypes")
public String dispatchingorderList(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String keyWord = getParameter("keyWord");//搜索关键字
	
//	String extsql = " or produceorderc.goodsUid in (select gid from AA_Goods where goodscode like '%"+keyWord+"%' or goodsname like '%"+keyWord+"%' or goodsstandard like '%"+keyWord+"%' )"
//			+ " or standardprocess.opcode like '%"+keyWord+"%' or standardprocess.opname like '%"+keyWord+"%' ";
	String condition = CommonUtil.combQuerySql("produceorder.billCode,produceorder.barCode", keyWord);
	String orgId=getSession().get("OrgId").toString();//组织id
	String sobId=getSession().get("SobId").toString();//账套id
	
	//过滤物料（考虑到物料数据大，先查出来再匹配）
	if(CommonUtil.notNullString(keyWord)){
		String conplus = "";
		List<AaGoods> goodsList = basicSettingService.getGoodsPageBean(0, 0, "", orgId, sobId, CommonUtil.combQuerySql("goodscode,goodsname,goodsstandard", keyWord), conplus).getList();
		String goodsIds = "";
		for(AaGoods g : goodsList){
			goodsIds += g.getGid()+',';
		}
		goodsIds = CommonUtil.cutLastString(goodsIds, ",");
		String extsql = " or produceorderc.goodsUid in ('"+goodsIds.replaceAll(",", "','")+"')";
		condition = CommonUtil.combQuerySql("produceorder.billCode,produceorder.barCode", keyWord,extsql);
	}
	setRequstAttribute("keyWord",keyWord);
	PageBean produceprocesslist = produceprocessService.dispatchingorderList(pageIndex,pageSize,condition);
	for(int i=0;i<produceprocesslist.getList().size();i++){
		AaGoods good = cacheCtrlService.getGoods(CommonUtil.Obj2String(((Map)produceprocesslist.getList().get(i)).get("goodsUid")));
		((Map)produceprocesslist.getList().get(i)).put("good", good);
		if(!CommonUtil.isNullObject(((Map)produceprocesslist.getList().get(i)).get("dispatchingObj"))){
			if(((Map)produceprocesslist.getList().get(i)).get("dispatchingObj").toString().equals("0")){
				AaPerson aaperson = cacheCtrlService.getPerson(((Map)produceprocesslist.getList().get(i)).get("personUnitVendorGid").toString());
				((Map)produceprocesslist.getList().get(i)).put("aaperson", aaperson);
			}
			if(((Map)produceprocesslist.getList().get(i)).get("dispatchingObj").toString().equals("1")){
				AaGroup aagroup = cacheCtrlService.getAaGroup(((Map)produceprocesslist.getList().get(i)).get("personUnitVendorGid").toString());
				((Map)produceprocesslist.getList().get(i)).put("aagroup", aagroup);
			}
			if(((Map)produceprocesslist.getList().get(i)).get("dispatchingObj").toString().equals("2")){
				AaProviderCustomer aaprovidercustomer = cacheCtrlService.getProviderCustomer(((Map)produceprocesslist.getList().get(i)).get("personUnitVendorGid").toString());
				((Map)produceprocesslist.getList().get(i)).put("aaprovidercustomer", aaprovidercustomer);
			};
		}
	}
	
	setRequstAttribute("data", produceprocesslist);
	return "dispatchingorderList";
}


	/**
	 * @category 跳转到开工页面
	 *2016 2016年7月11日上午10:48:31
	 *String
	 *宋银海
	 */
	public String toStartWork(){
		try{
			HttpServletRequest request=getRequest();
			Map map=produceprocessService.toStartWork(request);
			
			setRequstAttribute("map", map);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "toStartWork";
	}
	
	/**
	 * @category 跳转到报工页面
	 *2016 2016年7月11日上午10:48:31
	 *String
	 *宋银海
	 */
	public String toReportWork(){
		try{
			HttpServletRequest request=getRequest();
			Map map=produceprocessService.toReportWork(request);
			
			setRequstAttribute("map", map);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return "toReportWork";
	}
	

	/**
	 * @category 开工时，根据订单号获取相关信息
	 *2016 2016年7月11日下午1:27:52
	 *void
	 *宋银海
	 */
	public void getProduceProcessInforByOrder(){
		try{
			String produceCuid=getParameter("pocgid");
			List<Map> results=produceprocessService.getProduceProcessInforByOrder(produceCuid);
			
			getResponse().getWriter().write(EmiJsonArray.fromObject(results).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 开工时扫描条码（扫描人员、工位即设备、任务）
	 *2016 2016年4月12日下午5:36:44
	 *void
	 *宋银海
	 */
	public void getInfoByBarcodeStart(){
		try {
			String barcode = getParameter("barCode");
			String dispatchingObj=getParameter("dispatchingObj");//派工对象
			ProcessStartScanRsp processBarcodeScanRsp=produceOrderService.getInfoByBarcodeStart(barcode,dispatchingObj,null);
//			System.out.println(EmiJsonObj.fromObject(processBarcodeScanRsp).toString());
		    getResponse().getWriter().write(EmiJsonObj.fromObject(processBarcodeScanRsp).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	/**
	 * @category 报工时扫描条码（目前仅支持扫描任务）
	 *2016 2016年4月12日下午5:36:44
	 *void
	 *宋银海
	 */
	public void getInfoByBarcodeReport(){
		try {
			String barcode = getParameter("barCode");
			ProcessReportScanRsp processReportScanRsp=produceOrderService.getInfoByBarcodeReport(barcode);
//			System.out.println(EmiJsonObj.fromObject(processReportScanRsp).toString());
		    getResponse().getWriter().write(EmiJsonObj.fromObject(processReportScanRsp).toString());
		    
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
		
	}
	
	
	/**
	 * @category 提交开工
	 *2016 2016年4月15日上午11:20:16
	 *void
	 *宋银海
	 */
	public void startWork(){
		try {

			//防止重复提交
//			if(!TokenHelper.validToken()){
//				this.writeSuccess();
//				return;
//			}
			
			String[] dispatchingObjs=getRequest().getParameterValues("dispatchingObj");//派工对象
			String[] dispatchingObjValues=getRequest().getParameterValues("dispatchingObjValues");//派工数量
			String[] detailNotes=getRequest().getParameterValues("detailNotes");//备注
			
			MesWmDispatchingorder mo=new MesWmDispatchingorder();
			
			mo.setOrggid(getSession().get("OrgId").toString());
			mo.setSobgid(getSession().get("SobId").toString());
			mo.setGid(UUID.randomUUID().toString());
			mo.setDispatchingObj(Integer.valueOf(getParameter("dispatchingObjHidden")));//派工对象
			mo.setDisDate(new Timestamp(new Date().getTime()));
			mo.setNotes(CommonUtil.Obj2String(getParameter("mainNotes")));
//			mo.setStationGid(jsonObject.getString("stationGid"));
			
			MesWmReportorder mr=new MesWmReportorder();//报工单主
			mr.setOrgGid(getSession().get("OrgId").toString());
			mr.setSobGid(getSession().get("SobId").toString()); 
			mr.setGid(mo.getGid());
			mr.setDispatchingObj(Integer.valueOf(getParameter("dispatchingObjHidden")));
			mr.setBillDate(mo.getDisDate());
			
			List<MesWmDispatchingorderc> mesWmDispatchingordercs=new ArrayList<MesWmDispatchingorderc>();//派工单子表
			List<MesWmReportorderc> mesWmReportordercs=new ArrayList<MesWmReportorderc>();//报工单子表
			
			for(int i=0;i<dispatchingObjs.length;i++){
				if(!CommonUtil.isNullObject(dispatchingObjValues[i])){
					MesWmDispatchingorderc moc=new MesWmDispatchingorderc();
					
					moc.setGid(UUID.randomUUID().toString());
					moc.setDisGid(mo.getGid());
					moc.setDisNum(BigDecimal.valueOf(Double.parseDouble(dispatchingObjValues[i])));
					moc.setStartTime(new Timestamp(new Date().getTime()));
					moc.setPersonUnitVendorGid(dispatchingObjs[i]);
					moc.setProduceProcessRoutecGid(getParameter("produceProcessRoutecGid"));
					moc.setNotes(CommonUtil.Obj2String(detailNotes[i]));
					mesWmDispatchingordercs.add(moc);
					
					MesWmReportorderc mrc=new MesWmReportorderc();
					mrc.setRptgid(mr.getGid());
					mrc.setGid(moc.getGid());
					mrc.setDiscGid(moc.getGid());
					mrc.setPersonUnitVendorGid(dispatchingObjs[i]);
//					mrc.setReportNum(BigDecimal.valueOf(personJsonObject.getDouble("reportNum")));
					mrc.setReportOkNum(BigDecimal.valueOf(Double.parseDouble(dispatchingObjValues[i])));
//					mrc.setReportNotOkNum(BigDecimal.valueOf(personJsonObject.getDouble("reportNotOkNum")));
//					mrc.setReportProblemNum(BigDecimal.valueOf(personJsonObject.getDouble("reportProblemNum")));
					mrc.setProduceProcessRouteCGid(getParameter("produceProcessRoutecGid"));
					mrc.setEndTime(new Timestamp(new Date().getTime()));
					mrc.setNotes(CommonUtil.Obj2String(detailNotes[i]));
					mesWmReportordercs.add(mrc);
				}

			}
			
			produceOrderService.startWork(UUID.randomUUID().toString(),mo,mesWmDispatchingordercs,mr,mesWmReportordercs);
			
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "开工失败！");
		}
	}
	
	
	/**
	 * @category 修改开工
	 *2016 2016年4月15日上午11:20:16
	 *void
	 *宋银海
	 */
	public void updateWork(){
		try {

			String disorderGid=getParameter("disorderGid");//派工单主表gid
			String pprcGid=getParameter("pprcGid");//工艺路线子表gid
			String[] dispatchingObjs=getRequest().getParameterValues("dispatchingObj");//派工对象
			String[] dispatchingObjValues=getRequest().getParameterValues("dispatchingObjValues");//派工数量
			String[] detailNotes=getRequest().getParameterValues("detailNotes");//备注
			
			List<MesWmDispatchingorderc> mesWmDispatchingordercs=new ArrayList<MesWmDispatchingorderc>();//派工单子表
			
			for(int i=0;i<dispatchingObjs.length;i++){
				if(!CommonUtil.isNullObject(dispatchingObjValues[i])){
					MesWmDispatchingorderc moc=new MesWmDispatchingorderc();
					
					moc.setGid(dispatchingObjs[i]);
					moc.setProduceProcessRoutecGid(pprcGid);
					moc.setDisNum(BigDecimal.valueOf(Double.parseDouble(dispatchingObjValues[i])));
					moc.setNotes(CommonUtil.Obj2String(detailNotes[i]));
					
					mesWmDispatchingordercs.add(moc);
				}

			}
			
			JSONObject obj=produceOrderService.updateWork(disorderGid,mesWmDispatchingordercs);
			this.responseWrite(obj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "开工失败！");
		}
	}
	
	
	/**
	 * @category 仅调整人员，其他不修改
	 *2016 2016年4月15日上午11:20:16
	 *void
	 *宋银海
	 */
	public void updAdjustmentPeopleGroupStart(){
		try {

			String[] dispatchingObjs=getRequest().getParameterValues("dispatchingObj");//派工单子表gid
			String[] adjustmentPeopleGroupGid=getRequest().getParameterValues("adjustmentPeopleGroupGid");//调整人员或组gid
			
			List<MesWmDispatchingorderc> mesWmDispatchingordercs=new ArrayList<MesWmDispatchingorderc>();//派工单子表
			
			for(int i=0;i<dispatchingObjs.length;i++){
				MesWmDispatchingorderc moc=new MesWmDispatchingorderc();
				
				moc.setGid(dispatchingObjs[i]);
				moc.setPersonUnitVendorGid(adjustmentPeopleGroupGid[i]);
				
				mesWmDispatchingordercs.add(moc);

			}
			
			JSONObject obj=produceOrderService.updateWork(mesWmDispatchingordercs);
			this.responseWrite(obj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "调整人员失败！");
		}
	}
	
	
	/**
	 * @category 删除开工
	 *2016 2016年4月15日上午11:20:16
	 *void
	 *宋银海
	 */
	public void deleteWork(){
		try {

			String disOrderGid=getParameter("disOrderGid");//派工单主表gid
			String pprcGid=getParameter("pprcGid");//工艺路线子表gid
			JSONObject obj=produceOrderService.deleteWork(disOrderGid,pprcGid);
			
			this.responseWrite(obj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "开工失败！");
		}
	}
	
	/**
	 * @category 提交报工
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void reportWork(){
		try {
			
			//防止重复提交
//			if(!TokenHelper.validToken()){
//				System.out.println("1");
//				this.writeSuccess();
//				return;
//			}
			
			String[] personUnitVendorGids=getRequest().getParameterValues("personUnitVendorGid");//报工对象
			String[] discGids=getRequest().getParameterValues("discGid");//派工单子表gid
			String[] produceProcessRoutecGids=getRequest().getParameterValues("produceProcessRoutecGid");//工艺路线子表gid
			String[] reportOkNums=getRequest().getParameterValues("reportOkNum");//报工数量
			String[] reportNotOkNums=getRequest().getParameterValues("reportNotOkNum");//报工不合格数量
			String[] detailNotes=getRequest().getParameterValues("detailNotes");//备注
			
			MesWmReportorder mr=new MesWmReportorder();
			
//			mr.setStationGid(CommonUtil.Obj2String(jsonObject.get("stationGid")));无意义 应该在子表中添加字段体现
			mr.setOrgGid(getSession().get("OrgId").toString());
			mr.setSobGid(getSession().get("SobId").toString()); 
			mr.setGid(UUID.randomUUID().toString());
			mr.setDispatchingObj(Integer.valueOf(getParameter("dispatchingObjHidden")));
			mr.setBillDate(new Timestamp(new Date().getTime()));
			mr.setMemo(CommonUtil.Obj2String(getParameter("mainNotes")));
			
			List<MesWmReportorderc> mesWmReportordercs=new ArrayList<MesWmReportorderc>();//派工单子表
			
			
			for(int i=0;i<personUnitVendorGids.length;i++){
				if(!CommonUtil.isNullObject(reportOkNums[i])){
					MesWmReportorderc mrc=new MesWmReportorderc();
					mrc.setRptgid(mr.getGid());
					mrc.setGid(UUID.randomUUID().toString());
					mrc.setDiscGid(discGids[i]);
					mrc.setPersonUnitVendorGid(personUnitVendorGids[i]);
					mrc.setReportOkNum(CommonUtil.object2BigDecimal(reportOkNums[i]) );
					mrc.setReportNotOkNum(CommonUtil.object2BigDecimal(reportNotOkNums[i]) );
//					mrc.setReportProblemNum(BigDecimal.valueOf(personJsonObject.getDouble("reportProblemNum")));PC端暂不处理
					mrc.setProduceProcessRouteCGid(produceProcessRoutecGids[i]);
					mrc.setEndTime(new Timestamp(new Date().getTime()));
					mrc.setNotes(CommonUtil.Obj2String(detailNotes[i]));
					mesWmReportordercs.add(mrc);
				}

			}
			
			produceOrderService.reportWork(mr.getGid(),mr,mesWmReportordercs);
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "报工失败！");
		}
	}
	
	
	/**
	 * @category 修改报工 仅调整人员，其他不修改
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void updAdjustmentPeopleGroupReport(){
		try {
			String[] rptcGids=getRequest().getParameterValues("rptcGids");//报工单子表gid
			String[] adjustmentPeopleGroupGid=getRequest().getParameterValues("adjustmentPeopleGroupGid");//调整人员或组gid
			
			List<MesWmReportorderc> mesWmReportordercs=new ArrayList<MesWmReportorderc>();//派工单子表
			
			for(int i=0;i<rptcGids.length;i++){
					MesWmReportorderc mrc=new MesWmReportorderc();
					mrc.setGid(rptcGids[i]);
					mrc.setPersonUnitVendorGid(adjustmentPeopleGroupGid[i]);
					mesWmReportordercs.add(mrc);
			}
			
			JSONObject obj=produceOrderService.updateReportWork(mesWmReportordercs);
			this.responseWrite(obj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "调整人员失败！");
		}
	}
	
	
	
	
	/**
	 * @category 修改报工
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void updateReportWork(){
		try {
			String reportGid=getParameter("reportGid");//报工单主表gid
			String[] personUnitVendorGids=getRequest().getParameterValues("personUnitVendorGid");//报工对象
			String[] discGids=getRequest().getParameterValues("discGid");//派工单子表gid
			String[] rptcGids=getRequest().getParameterValues("rptcGids");//报工单子表gid
			String[] produceProcessRoutecGids=getRequest().getParameterValues("produceProcessRoutecGid");//工艺路线子表gid
			String[] reportOkNums=getRequest().getParameterValues("reportOkNum");//报工数量
			String[] reportNotOkNums=getRequest().getParameterValues("reportNotOkNum");//报工不合格数量
			String[] detailNotes=getRequest().getParameterValues("detailNotes");//备注
			
			List<MesWmReportorderc> mesWmReportordercs=new ArrayList<MesWmReportorderc>();//派工单子表
			
			for(int i=0;i<personUnitVendorGids.length;i++){
				if(!CommonUtil.isNullObject(reportOkNums[i])){
					MesWmReportorderc mrc=new MesWmReportorderc();
					mrc.setRptgid(reportGid);
					mrc.setGid(rptcGids[i]);
					mrc.setDiscGid(discGids[i]);
					mrc.setPersonUnitVendorGid(personUnitVendorGids[i]);
					mrc.setReportOkNum(CommonUtil.object2BigDecimal(reportOkNums[i]));
					mrc.setReportNotOkNum(CommonUtil.object2BigDecimal(reportNotOkNums[i]));
//					mrc.setReportProblemNum(BigDecimal.valueOf(personJsonObject.getDouble("reportProblemNum")));PC端暂不处理
					mrc.setProduceProcessRouteCGid(produceProcessRoutecGids[i]);
					mrc.setEndTime(new Timestamp(new Date().getTime()));
					mrc.setNotes(CommonUtil.Obj2String(detailNotes[i]));
					mesWmReportordercs.add(mrc);
				}

			}
			
			JSONObject obj=produceOrderService.updateReportWork(reportGid,mesWmReportordercs);
			this.responseWrite(obj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "报工失败！");
		}
	}
	
	
	
	/**
	 * @category 删除报工
	 *2016 2016年4月15日上午11:20:16
	 *void
	 *宋银海
	 */
	public void deleteReportWork(){
		try {

			String reportGid=getParameter("reportGid");//派工单主表gid
			String pprcGid=getParameter("pprcGid");//工艺路线子表gid
			JSONObject obj=produceOrderService.deleteReportWork(reportGid,pprcGid);
			
			this.responseWrite(obj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorOrSuccess(0, "删除失败！");
		}
	}
	
	
	/**
	 * @category 提交放行
	 *2016 2016年4月20日下午4:17:28
	 *void
	 *宋银海
	 */
	public void letPass(){
		try {
			String gid=getParameter("gid");
			produceprocessService.letPass(gid);
			this.writeSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	/**
	 * @category 开工单列表
	 * 2016年7月25日 上午10:32:27 
	 * @author zhuxiaochen
	 * @return
	 */
	public String dispatchingStartList(){
		try {
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			String goods = getParameter("goods");//物料名称或编码
			String startDate = getParameter("startDate");//开始时间
			String endDate = getParameter("endDate");//结束时间
			String process = getParameter("process");//工序名称或编码
			String billcode  = getParameter("billcode");
			String condition = "";
			if(CommonUtil.notNullString(billcode)){
				condition += " and po.billCode like '%"+billcode+"%'";
			}
			if(CommonUtil.notNullString(goods)){
				String conplus = "";
//				condition += " and poc.goodsUid in (select gid from AA_Goods where goodscode like '%"+goods+"%' or goodsname like '%"+goods+"%' or goodsstandard like '%"+goods+"%') ";
				List<AaGoods> goodsList = basicSettingService.getGoodsPageBean(0, 0, "", orgId, sobId, CommonUtil.combQuerySql("goodscode,goodsname,goodsstandard", goods), conplus).getList();
				String goodsIds = "";
				for(AaGoods g : goodsList){
					goodsIds += g.getGid()+',';
				}
				goodsIds = CommonUtil.cutLastString(goodsIds, ",");
				condition += " and poc.goodsUid in ('"+goodsIds.replaceAll(",", "','")+"')";
			}
			if(CommonUtil.notNullString(startDate)){
				condition += " and doc.startTime>='"+startDate+"'";
			}
			if(CommonUtil.notNullString(endDate)){
				condition += " and doc.startTime<='"+endDate+" 23:59:59'";
			}
			if(CommonUtil.notNullString(process)){
				condition += " and prc.opGid in (select gid from MES_WM_StandardProcess where opcode like '%"+process+"%' or opname like '%"+process+"%' ) ";
			}
			
			PageBean data = produceprocessService.dispatchingStartList(pageIndex ,pageSize,condition);
			
			setRequstAttribute("data", data);
			setRequstAttribute("goods", goods);
			setRequstAttribute("startDate", startDate);
			setRequstAttribute("endDate", endDate);
			setRequstAttribute("process", process);
			setRequstAttribute("billcode", billcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dispatchingStartList";
	}
	
	/**
	 * @category 报工单列表
	 * 2016年7月25日 上午10:32:27 
	 * @author zhuxiaochen
	 * @return
	 */
	public String dispatchingReportList(){
		try {
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			String goods = getParameter("goods");//物料名称或编码
			String startDate = getParameter("startDate");//开始时间
			String endDate = getParameter("endDate");//结束时间
			String process = getParameter("process");//工序名称或编码
			String billcode  = getParameter("billcode");
			String condition = "";
			if(CommonUtil.notNullString(billcode)){
				condition += " and po.billCode like '%"+billcode+"%'";
			}
			if(CommonUtil.notNullString(goods)){
				String conplus = "";
//				condition += " and poc.goodsUid in (select gid from AA_Goods where goodscode like '%"+goods+"%' or goodsname like '%"+goods+"%' or goodsstandard like '%"+goods+"%' ) ";
				List<AaGoods> goodsList = basicSettingService.getGoodsPageBean(0, 0, "", orgId, sobId, CommonUtil.combQuerySql("goodscode,goodsname,goodsstandard", goods), conplus).getList();
				String goodsIds = "";
				for(AaGoods g : goodsList){
					goodsIds += g.getGid()+',';
				}
				goodsIds = CommonUtil.cutLastString(goodsIds, ",");
				condition += " and poc.goodsUid in ('"+goodsIds.replaceAll(",", "','")+"')";
				
			}
			if(CommonUtil.notNullString(startDate)){
				condition += " and doc.endTime>='"+startDate+"'";
			}
			if(CommonUtil.notNullString(endDate)){
				condition += " and doc.endTime<='"+endDate+" 23:59:59'";
			}
			if(CommonUtil.notNullString(process)){
				condition += " and prc.opGid in (select gid from MES_WM_StandardProcess where opcode like '%"+process+"%' or opname like '%"+process+"%' ) ";
			}
			
			PageBean data = produceprocessService.dispatchingReportList(pageIndex ,pageSize,condition);
			
			setRequstAttribute("data", data);
			setRequstAttribute("goods", goods);
			setRequstAttribute("startDate", startDate);
			setRequstAttribute("endDate", endDate);
			setRequstAttribute("process", process);
			setRequstAttribute("billcode", billcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dispatchingReportList";
	}
}
