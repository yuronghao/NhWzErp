package com.emi.wms.servicedata.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.util.TokenHelper;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.WmProcurearrival;
import com.emi.wms.bean.WmProcureorder;
import com.emi.wms.bean.WmProcureorderC;
import com.emi.wms.bean.WmProduceorder;
import com.emi.wms.bean.WmProduceorderC;
import com.emi.wms.bean.YmUser;
import com.emi.wms.processDesign.service.BasePDService;
import com.emi.wms.servicedata.service.ProduceOrderService;

public class ProduceOrderAction extends BaseAction {
private static final long serialVersionUID = 892882949145541033L;
private ProduceOrderService produceOrderService;
private BasicSettingService basicSettingService;
private CacheCtrlService cacheCtrlService;
private BasePDService basePDService;

public BasePDService getBasePDService() {
	return basePDService;
}
public void setBasePDService(BasePDService basePDService) {
	this.basePDService = basePDService;
}
public ProduceOrderService getProduceOrderService() {
	return produceOrderService;
}
public void setProduceOrderService(ProduceOrderService produceOrderService) {
	this.produceOrderService = produceOrderService;
}
public CacheCtrlService getCacheCtrlService() {
	return cacheCtrlService;
}
public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}

public void setBasicSettingService(BasicSettingService basicSettingService) {
	this.basicSettingService = basicSettingService;
}
/**
 * 
 * @category 生产订单列表
 * 2016年4月27日 下午2:01:52
 * @author 杨峥铖
 * @return
 */
	public String produceOrderList(){
		try {
			String changeOrder = getParameter("changeOrder");//是否是改制订单
			int pageIndex = getPageIndex();
			int pageSize = getPageSize();
			String billkeyWord = getParameter("billkeyWord");//搜索单据编号关键字
			String goodskeyWord = getParameter("goodskeyWord");//搜索物品关键字
			String condition = "" ;
			if(CommonUtil.notNullString(billkeyWord)){
				condition += " and (WmProduceorder.billCode like '%"+billkeyWord+"%' or WmProduceorder.barCode like '%"+billkeyWord+"%')"; //CommonUtil.combQuerySql("WmProduceorder.billCode,WmProduceorder.barCode", keyWord);
			}
			
			if(CommonUtil.notNullString(goodskeyWord)){
				condition += " and WMProduceOrderC.goodsUid in (select gid from AA_Goods where goodsname like '%"+goodskeyWord+"%' or goodscode like '%"+goodskeyWord+"%'  )";
			}

			setRequstAttribute("billkeyWord",billkeyWord);
			setRequstAttribute("goodskeyWord",goodskeyWord);
			String orgId=getSession().get("OrgId").toString();
			String sobId=getSession().get("SobId").toString();
			condition+=" and WmProduceorder.sobGid='"+sobId+"' and WmProduceorder.orgGid='"+orgId+"'";
			PageBean produceOrderlist = new PageBean();
			if("1".equals(changeOrder)){//改制订单和普通订单分开
				condition +="";//" and changeOrder=1 ";
				produceOrderlist=produceOrderService.getProduceOrderListWithOldBillCode(pageIndex, pageSize, condition);
			}else{
				condition += " and (changeOrder is null or changeOrder=0)";
				produceOrderlist=produceOrderService.getproduceOrderlist(pageIndex, pageSize, condition);
			}
			
			//PageBean produceOrderlist = produceOrderService.getproduceOrderlist(pageIndex,pageSize,condition);
			for(int i=0;i<produceOrderlist.getList().size();i++){
				if(!CommonUtil.isNullString(((WmProduceorder)produceOrderlist.getList().get(i)).getGoodsUid())){
					AaGoods good = cacheCtrlService.getGoods(((WmProduceorder)produceOrderlist.getList().get(i)).getGoodsUid().toString());
					((WmProduceorder)produceOrderlist.getList().get(i)).setAagoods(good);
				}
				if(!CommonUtil.isNullString(((WmProduceorder)produceOrderlist.getList().get(i)).getRecordpersonuid())){
					YmUser ymuser = cacheCtrlService.getUser(((WmProduceorder)produceOrderlist.getList().get(i)).getRecordpersonuid().toString());
					if(!CommonUtil.isNullObject(ymuser)){
						((WmProduceorder)produceOrderlist.getList().get(i)).setProvidercustomername(ymuser.getUserName());
					}
				}
			}
			setRequstAttribute("data", produceOrderlist);
			setRequstAttribute("changeOrder", changeOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "produceOrderList";
	}
	
	/**
	 * 
	 * @category 生产订单详情
	 * 2016年4月27日 下午2:01:58
	 * @author 杨峥铖
	 * @return
	 */
    public String toAddproduceOrder(){
	try {
		String produceOrdergid = getParameter("produceOrdergid");
		String changeOrder = getParameter("changeOrder");//是否是改制订单 1：是
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		Map produceOrder = produceOrderService.findproduceOrder(produceOrdergid,changeOrder,orgId,sobId);
		if(!CommonUtil.isNullObject(produceOrder)){
			if(!CommonUtil.isNullObject(produceOrder.get("producedeptGid"))){
				AaDepartment department = basePDService.findDepartment(produceOrder.get("producedeptGid").toString());
				setRequstAttribute("department", department);
			}
			if(!CommonUtil.isNullObject(produceOrder.get("producemanagerGid"))){
				AaPerson aaperson = cacheCtrlService.getPerson(produceOrder.get("producemanagerGid").toString());
				setRequstAttribute("aaperson", aaperson);
			}
			if(!CommonUtil.isNullObject(produceOrder.get("WMProduceOrdergid"))){
				List produceOrderc = produceOrderService.getproduceOrderlist(produceOrder.get("WMProduceOrdergid").toString());
//				for(int i=0;i<produceOrderc.size();i++){
//					AaGoods good = cacheCtrlService.getGoods(((Map)produceOrderc.get(i)).get("goodsUid").toString());
//					((Map)produceOrderc.get(i)).put("good", good);
//				}
				setRequstAttribute("produceOrderc", produceOrderc);
			}
		}
		//得到自由项定义
		List<AaFreeSet> freeset = basicSettingService.getFreeSet();
		setRequstAttribute("produceOrder", produceOrder);
		setRequstAttribute("freeset", freeset);
		setRequstAttribute("changeOrder", changeOrder);
		setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "produceOrderAdd";
	}
/**
 * 
 * @category 设置用户是否可用
 * 2015年3月22日 上午8:21:08
 * @author 杨峥铖
 */
public void produceOrderEnable(){
	try {
		Integer enable = new Integer(getParameter("enable"));
		String id = getParameter("id");
		//produceOrderService.setproduceOrderEnable(enable,id);
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
public void addproduceOrder(){
	try {
		//防止重复提交
		if(!TokenHelper.validToken()){
			getResponse().getWriter().write("success");
			return;
		}
		
		String msg = "";
		boolean pass = true;
		String changeOrder = getParameter("changeOrder");
		
		if(pass){
			WmProduceorder produceOrder = new WmProduceorder();
			String uuid = UUID.randomUUID().toString();
			produceOrder.setGid(uuid);
			produceOrder.setNotes(getParameter("notes"));
			produceOrder.setBillcode(getParameter("billCode"));
			produceOrder.setBillstate("0");
			produceOrder.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			produceOrder.setDeptGid(getParameter("depUid"));
			produceOrder.setManagerGid(getParameter("managerGid"));
			produceOrder.setRecordpersonuid(getParameter("recordPersonUid"));
			produceOrder.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//produceOrder.setAuditpersonuid(getParameter("auditPersonUid"));
			//produceOrder.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			produceOrder.setSobgid(getSession().get("SobId").toString());
			produceOrder.setOrggid(getSession().get("OrgId").toString());
			produceOrder.setChangeOrder(CommonUtil.isNullString(changeOrder)?0:Integer.parseInt(changeOrder));
			
			List<WmProduceorderC> produceOrdercs = new ArrayList<WmProduceorderC>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] number = getRequest().getParameterValues("number");
			String[] startDate = getRequest().getParameterValues("startDate");
			String[] endDate = getRequest().getParameterValues("endDate");
			String[] note = getRequest().getParameterValues("note");
			for(int i=0;i<goodsUid.length;i++){
				WmProduceorderC produceOrderc = new WmProduceorderC();
			produceOrderc.setProduceOrderUid(uuid);
			produceOrderc.setGoodsUid(goodsUid[i]);
			produceOrderc.setNumber(new BigDecimal(number[i]));
			produceOrderc.setStartDate(startDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(startDate[i], "yyyy-MM-dd").getTime()):null);
			produceOrderc.setEndDate(endDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(endDate[i], "yyyy-MM-dd").getTime()):null);
			produceOrderc.setNotes(note[i]);
			produceOrdercs.add(produceOrderc);
			}
			}
			
			boolean suc = produceOrderService.addproduceOrder(produceOrder,produceOrdercs);
//			boolean suc1 = produceOrderService.addproduceOrderc(produceOrdercs);
			if(suc){
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

public String orgSelect(){
	//List classify = produceOrderService.getClassifyList("");
	Map m=new HashMap();
	m.put("id", 0);
	m.put("pId",-1);
	m.put("name", "全部");
	//classify.add(m);
	//getRequest().setAttribute("classify", JSONArray.fromObject(classify).toString());
	return "orgSelect";
}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void updateproduceOrder(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			WmProduceorder produceOrder = new WmProduceorder();
			produceOrder.setGid(getParameter("produceOrdergid"));
			produceOrder.setNotes(getParameter("notes"));
			produceOrder.setBillcode(getParameter("billCode"));
			produceOrder.setBillstate("0");
			produceOrder.setBilldate(getParameter("billDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("billDate"), "yyyy-MM-dd").getTime()):null);
			produceOrder.setDeptGid(getParameter("depUid"));
			produceOrder.setManagerGid(getParameter("managerGid"));
			produceOrder.setRecordpersonuid(getParameter("recordPersonUid"));
			produceOrder.setRecorddate(getParameter("recordDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("recordDate"), "yyyy-MM-dd").getTime()):null);
			//produceOrder.setAuditpersonuid(getParameter("auditPersonUid"));
			//produceOrder.setAuditdate(getParameter("auditDate").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("auditDate"), "yyyy-MM-dd").getTime()):null);
			produceOrder.setSobgid(getSession().get("SobId").toString());
			produceOrder.setOrggid(getSession().get("OrgId").toString());
			
			List<WmProduceorderC> produceOrdercs = new ArrayList<WmProduceorderC>();
			List<WmProduceorderC> produceOrdercs1 = new ArrayList<WmProduceorderC>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] gid = getRequest().getParameterValues("gid");
			String[] number = getRequest().getParameterValues("number");
			String[] startDate = getRequest().getParameterValues("startDate");
			String[] endDate = getRequest().getParameterValues("endDate");
			String[] note = getRequest().getParameterValues("note");
			for(int i=0;i<goodsUid.length;i++){
				if(CommonUtil.isNullObject(gid[i])){
					WmProduceorderC produceOrderc1 = new WmProduceorderC();
					produceOrderc1.setProduceOrderUid(getParameter("produceOrdergid"));
					produceOrderc1.setGoodsUid(goodsUid[i]);
					produceOrderc1.setNumber(new BigDecimal(number[i]));
					produceOrderc1.setStartDate(startDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(startDate[i], "yyyy-MM-dd").getTime()):null);
					produceOrderc1.setEndDate(endDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(endDate[i], "yyyy-MM-dd").getTime()):null);
					produceOrderc1.setNotes(note[i]);
					produceOrdercs1.add(produceOrderc1);
				}else{
					WmProduceorderC produceOrderc = new WmProduceorderC();
					produceOrderc.setGid(gid[i]);
					produceOrderc.setProduceOrderUid(getParameter("produceOrdergid"));
					produceOrderc.setGoodsUid(goodsUid[i]);
					produceOrderc.setNumber(new BigDecimal(number[i]));
					produceOrderc.setStartDate(startDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(startDate[i], "yyyy-MM-dd").getTime()):null);
					produceOrderc.setEndDate(endDate[i].length()>0?new Timestamp(DateUtil.stringtoDate(endDate[i], "yyyy-MM-dd").getTime()):null);
					produceOrderc.setNotes(note[i]);
					produceOrdercs.add(produceOrderc);
				}
			}
			}
			
			boolean suc = produceOrderService.updateproduceOrder(produceOrder);
			boolean suc1 = produceOrderService.updateproduceOrderc(produceOrdercs);
			boolean suc2 = produceOrderService.addproduceOrderc(produceOrdercs1);
			
			boolean suc3 = true;
			if(!CommonUtil.isNullObject(getParameter("deleteGids"))){
				suc3=produceOrderService.deleteproduceOrderc(getParameter("deleteGids").substring(0,getParameter("deleteGids").length()-1));
			}
					
			if(suc&&suc1&&suc2&&suc3){
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
 * @category 展位修改界面
 * 2015年3月22日 上午8:18:30
 * @author 杨峥铖
 * @return
 */
public String getproduceOrderlist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String condition="";
	//PageBean produceOrderlist = produceOrderService.getproduceOrderlist(pageIndex,pageSize,condition);
	//setRequstAttribute("data", produceOrderlist);
	return "produceOrderlist";
}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void printbarcode(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			
			//List<Map> printlist = new ArrayList<Map>();
			String[] goodsUid = getRequest().getParameterValues("goodsUid");
			if(goodsUid!=null&&goodsUid.length>0){
			String[] goodsCode = getRequest().getParameterValues("goodsCode");
			String[] batch = getRequest().getParameterValues("batch");
			String[] printamount = getRequest().getParameterValues("printamount");
			for(int i=0;i<goodsUid.length;i++){
			String ss = "SmartPrinter Pro|template|sncode="+goodsCode[i]+batch[i];
			for(int j=0;j<Integer.parseInt(printamount[i]);j++){
				FileOutputStream fos = null;
				FileInputStream fis = null;
				try {
					fos = new FileOutputStream("E:/temp/11/"+UUID.randomUUID()+".txt");
					System.out.println(UUID.randomUUID());
					ss.getBytes();
					fos.write(ss.getBytes());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fos != null) {
							fos.close();
						}
						if (fis != null) {
							fis.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			}
			}
			
			getResponse().getWriter().write("success");
		}else{
			getResponse().getWriter().write(msg);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
   public void deleteproduceOrder(){
	try {
		String produceorderGid = getParameter("gid");
		int producecount = produceOrderService.getproduceprocess(produceorderGid);
		if(producecount>0){
			getResponse().getWriter().write("error");
		}else{
			produceOrderService.deleteproduceOrder(produceorderGid);
			getResponse().getWriter().write("success");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
   
   public void checkproduceOrder(){
		try {
			String produceorderGid = getParameter("gid");
			int producecount = produceOrderService.getproduceprocess(produceorderGid);
			if(producecount>0){
				getResponse().getWriter().write("error");
			}else{
				getResponse().getWriter().write("success");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
   
   
   /**
    * 变更生产订单页面
    * @return
    * 2017年5月27日
    * String
    */
   public String toChangeOrderNum(){
	try {

			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
			setRequstAttribute("number", getParameter("number"));//lhgdialog参数，使之基于整个浏览器弹出
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toChangeOrderNum";
	}
   
   
   /**
    * @category 变更订单数量 
    * 2017年5月27日
    * void
    */
   public void changeOrderNum(){
		try {

			HttpServletRequest request=getRequest();
			String res=produceOrderService.changeOrderNum(request);
			getResponse().getWriter().write(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
   
   

   
   
   
}
