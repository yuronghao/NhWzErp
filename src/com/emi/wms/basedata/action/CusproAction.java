package com.emi.wms.basedata.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.wms.basedata.service.CusproService;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;

public class CusproAction extends BaseAction {
private CusproService cusproService;

public CusproService getCusproService() {
	return cusproService;
}
public void setCusproService(CusproService cusproService) {
	this.cusproService = cusproService;
}
/**
 * 
 * @category 类别树
 * 2015年3月22日 上午8:17:57
 * @author 杨峥铖
 * @return
 */
	public String treeandlist(){
		return "treeandlist";
	}
/**
 * 
 * @category 类别树
 * 2015年3月22日 上午8:17:57
 * @author 杨峥铖
 * @return
 */
	public String getClassifyList(){
		String classtype="";
		String returnjsp="";
		try {
			classtype=getParameter("classtype");
			String condition="and styleGid in("+classtype+")";
			List classify = cusproService.getClassifyList(condition);
			Map m=new HashMap();
			m.put("id", 0);
			m.put("pId",-1);
			m.put("name", "全部");
			classify.add(m);
			setRequstAttribute("classify", EmiJsonArray.fromObject(classify).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(classtype.equals("'01','02'")){
			returnjsp="classifyList";
			
		}
		if(classtype.equals("'03'")){
			returnjsp= "getGoodsTree";
		}
		if(classtype.equals("'04'")){
			returnjsp= "mouldTree";
		}
		return returnjsp;
		
	}
	
	/**
	 * 
	 * @category 客商列表
	 * 2015年3月22日 上午8:17:57
	 * @author 杨峥铖
	 * @return
	 */
		public String getcusproList(){
			try {
				int pageIndex = getPageIndex();								//页码，从1开始
				int pageSize = getPageSize();
				String condition="";
				if(!CommonUtil.isNullString(getParameter("type"))&&getParameter("type").equals("01")){
					condition += " and AaProviderCustomer.customerId='"+getParameter("id")+"'";
				}
				if(!CommonUtil.isNullString(getParameter("type"))&&getParameter("type").equals("02")){
					condition += " and AaProviderCustomer.providerId='"+getParameter("id")+"'";
				}
				PageBean cusprolist = cusproService.getcusproList(pageIndex, pageSize,condition);
				setRequstAttribute("data", cusprolist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "cusproList";
		}
		
		/**
		 * 
		 * @category 查询
		 * 2015年3月22日 上午8:17:57
		 * @author 杨峥铖
		 * @return
		 */
		private String getExhCondition() {
			String condition = "";
				if(CommonUtil.notNullObject(getParameter(""))){
					condition += "and boothType.gid like '%"+getParameter("")+"%'";
				}
			return condition;
		}
	/**
	 * 
	 * @category 展位修改界面
	 * 2015年3月22日 上午8:18:30
	 * @author 杨峥铖
	 * @return
	 */
	public String toUpdateCuspro(){
		String cusprogid = getParameter("gid");
		Map aaprocus = cusproService.findCuspro(cusprogid);
		List cusprobook = cusproService.getcusprobookList(cusprogid);
		String orgId = getSession().get("OrgId").toString();
		String sobId = getSession().get("SobId").toString();
		String condition="and orgGid = '"+orgId+"' and sobGid = '"+sobId+"' and isdel=0";
		List category = cusproService.getCategoryList(condition);
		setRequstAttribute("category", category);
		setRequstAttribute("cusprobook", cusprobook);
		setRequstAttribute("aaprocus", aaprocus);
		setRequstAttribute("cusprogid", cusprogid);
		return "cusproEdit";
	}
	
	/**
	 * 
	 * @category 修改展位
	 * 2015年3月22日 上午8:18:58
	 * @author 杨峥铖
	 */
	public void updateCuspro(){
		try {
			String msg = "";
			boolean pass = true;
			if(pass){
				String orgId = getSession().get("OrgId").toString();
				String sobId = getSession().get("SobId").toString();
				AaProviderCustomer aaprocus = new AaProviderCustomer();
				aaprocus.setGid(getParameter("cusprogid"));
				aaprocus.setPccode(getParameter("pcCode"));
				aaprocus.setPcname(getParameter("pcName"));
				aaprocus.setAddr(getParameter("addr"));
				aaprocus.setBegintimes(getParameter("beginTimes").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("beginTimes"), "yyyy-MM-dd").getTime()):null);
				aaprocus.setEndtimes(getParameter("endTimes").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("endTimes"), "yyyy-MM-dd").getTime()):null);
				aaprocus.setSoulationid(getParameter("category"));
				aaprocus.setOrgid(orgId);
				aaprocus.setSobid(sobId);
				aaprocus.setCustomerId(getParameter("customerId"));
				aaprocus.setProviderId(getParameter("providerId"));
				
				//保存联系人信息
				List<AaProviderCustomerAddbook> bookitems = new ArrayList<AaProviderCustomerAddbook>();
				cusproService.deletebooks(getParameter("cusprogid"));
				String[] deliverPersons = getRequest().getParameterValues("deliverPerson");
				if(deliverPersons!=null&&deliverPersons.length>0){
				String[] deliverTels = getRequest().getParameterValues("deliverTel");
				String[] deliverAddrs = getRequest().getParameterValues("deliverAddr");
				for(int i=0;i<deliverPersons.length;i++){
					AaProviderCustomerAddbook book = new AaProviderCustomerAddbook();
					book.setDeliverperson(deliverPersons[i]);
					book.setPcgid(getParameter("cusprogid"));
					book.setDelivertel(deliverTels[i]);
					book.setDeliveraddr(deliverAddrs[i]);
					bookitems.add(book);
				  }
				}
				boolean suc = cusproService.updateCuspro(aaprocus);
				boolean suc1 = cusproService.addprocusbook(bookitems);
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
	 * @category 添加展位
	 * 2015年3月22日 上午8:19:36
	 * @author 杨峥铖
	 */
	public void addCuspro(){
		try {
			String msg = "";
			boolean pass = true;
			
			if(pass){
				String orgId = getSession().get("OrgId").toString();
				String sobId = getSession().get("SobId").toString();
				AaProviderCustomer aaprocus = new AaProviderCustomer();
				String uuid = UUID.randomUUID().toString();
				aaprocus.setGid(uuid);
				aaprocus.setPccode(getParameter("pcCode"));
				aaprocus.setPcname(getParameter("pcName"));
				aaprocus.setAddr(getParameter("addr"));
				aaprocus.setBegintimes(getParameter("beginTimes").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("beginTimes"), "yyyy-MM-dd").getTime()):null);
				aaprocus.setEndtimes(getParameter("endTimes").length()>0?new Timestamp(DateUtil.stringtoDate(getParameter("endTimes"), "yyyy-MM-dd").getTime()):null);
				aaprocus.setSoulationid(getParameter("category"));
				aaprocus.setOrgid(orgId);
				aaprocus.setSobid(sobId);
				aaprocus.setCustomerId(getParameter("customerId"));
				aaprocus.setProviderId(getParameter("providerId"));
				aaprocus.setIsDel("0");
				
				//保存联系人信息
				List<AaProviderCustomerAddbook> bookitems = new ArrayList<AaProviderCustomerAddbook>();
				String[] deliverPersons = getRequest().getParameterValues("deliverPerson");
				if(deliverPersons!=null&&deliverPersons.length>0){
				String[] deliverTels = getRequest().getParameterValues("deliverTel");
				String[] deliverAddrs = getRequest().getParameterValues("deliverAddr");
				for(int i=0;i<deliverPersons.length;i++){
					AaProviderCustomerAddbook book = new AaProviderCustomerAddbook();
					book.setDeliverperson(deliverPersons[i]);
					book.setPcgid(uuid);
					book.setDelivertel(deliverTels[i]);
					book.setDeliveraddr(deliverAddrs[i]);
					bookitems.add(book);
				  }
				}
				boolean suc = cusproService.addprocus(aaprocus);
				boolean suc1 = cusproService.addprocusbook(bookitems);
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
	 * 2015年12月10日 下午5:19:10
	 * @author 杨峥铖
	 */
	public void deleteCuspro(){
		try {
			String[] strsums = getRequest().getParameterValues("strsum");
			boolean suc = cusproService.deleteCuspro(strsums);
			if(suc){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @category 展位添加界面
	 * 2015年3月22日 上午8:20:25
	 * @author 杨峥铖
	 * @return
	 */
public String toAddCuspro(){
	String seqcuspro = nextSequenceVal("cusproid");
	String orgId = getSession().get("OrgId").toString();
	String sobId = getSession().get("SobId").toString();
	String condition="and orgGid = '"+orgId+"' and sobGid = '"+sobId+"' and isdel=0";
	List category = cusproService.getCategoryList(condition);
	setRequstAttribute("seqcuspro", seqcuspro);
	setRequstAttribute("category", category);
	return "cusproAdd";
	}
	
public String ProCusSelect(){
	String condition = "";
	String typeid = getParameter("id");
	if(typeid.equals("0")){
		condition+=" and styleGid='01'";
	}
    if(typeid.equals("1")){
    	condition+=" and styleGid='02'";
	}
    if(typeid.equals("2")){
    	condition+=" and styleGid='03'";
	}
    if(typeid.equals("3")){
    	condition+=" and styleGid='04'";
	}
	List classify = cusproService.getClassifyList(condition);
	Map m=new HashMap();
	m.put("id", 0);
	m.put("pId",-1);
	m.put("name", "全部");
	classify.add(m);
//	System.out.println(JSONArray.fromObject(classify).toString());
	getRequest().setAttribute("classify", JSONArray.fromObject(classify).toString());
	setRequstAttribute("typeid", typeid);
	return "ProCusSelect";
}


}
