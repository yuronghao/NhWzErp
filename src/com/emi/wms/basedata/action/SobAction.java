package com.emi.wms.basedata.action;

import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

import net.sf.json.JSONArray;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.common.util.ImagePrint;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.wms.basedata.service.SobService;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.YmUser;
import com.emi.wms.bean.Ymsetting;

public class SobAction extends BaseAction {
private SobService sobService;

public SobService getSobService() {
	return sobService;
}
public void setSobService(SobService sobService) {
	this.sobService = sobService;
}
/**
 * 
 * @category 类别树
 * 2015年3月22日 上午8:17:57
 * @author 杨峥铖
 * @return
 */
	public String treeandorg(){
		return "treeandorg";
	}
/**
 * 
 * @category 类别树
 * 2015年3月22日 上午8:17:57
 * @author 杨峥铖
 * @return
 */
	public String getorgclassify(){
		try {
			List classify = sobService.getClassifyList("");
			Map m=new HashMap();
			m.put("id", 0);
			m.put("pId",-1);
			m.put("name", "全部");
			classify.add(m);
			setRequstAttribute("classify", EmiJsonArray.fromObject(classify).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "orgclassify";
	}
	
	/**
	 * 
	 * @category 客商列表
	 * 2015年3月22日 上午8:17:57
	 * @author 杨峥铖
	 * @return
	 */
		public String getorganizeInfo(){
			try {
				String condition=" and gid='"+getParameter("id")+"'";
				Map organizeInfo = sobService.getorganizeInfo(condition);
				setRequstAttribute("organizeInfo", organizeInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "organizeInfo";
		}
		
	/**
	 * 
	 * @category 展位修改界面
	 * 2015年3月22日 上午8:18:30
	 * @author 杨峥铖
	 * @return
	 */
	public String toUpdateorg(){
		String orgclassid = getParameter("orgclassid");
		Map aaorg = sobService.findorg(orgclassid);
		setRequstAttribute("aaorg", aaorg);
		setRequstAttribute("orgclassid", orgclassid);
		return "orgEdit";
	}
	
	/**
	 * 
	 * @category 修改展位
	 * 2015年3月22日 上午8:18:58
	 * @author 杨峥铖
	 */
	public void updateorg(){
		try {
			String msg = "";
			boolean pass = true;
			if(pass){
				AaOrg aaorg = new AaOrg();
				aaorg.setGid(getParameter("orgclassid"));
				aaorg.setNotes(getParameter("notes"));
				aaorg.setOrgcode(getParameter("orgCode"));
				aaorg.setOrgname(getParameter("orgName"));
				aaorg.setOrgadder(getParameter("orgAdder"));
				aaorg.setOrgtel(getParameter("orgTel"));
				aaorg.setOrgfax(getParameter("orgFax"));
				aaorg.setOrgmoboile(getParameter("orgMoboile"));
				aaorg.setLegal(getParameter("legal"));
				aaorg.setOrgpostcode(getParameter("orgPostCode"));
				aaorg.setOrgurl(getParameter("orgUrl"));
				aaorg.setOrgurls(getParameter("orgUrls"));
				
				boolean suc = sobService.updateorg(aaorg);
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
	/**
	 * 
	 * @category 添加展位
	 * 2015年3月22日 上午8:19:36
	 * @author 杨峥铖
	 */
	public void addOrg(){
		try {
			String msg = "";
			boolean pass = true;
			
			if(pass){
				AaOrg aaorg = new AaOrg();
				aaorg.setNotes(getParameter("notes"));
				aaorg.setOrgcode(getParameter("orgCode"));
				aaorg.setOrgname(getParameter("orgName"));
				aaorg.setOrgadder(getParameter("orgAdder"));
				aaorg.setOrgtel(getParameter("orgTel"));
				aaorg.setOrgfax(getParameter("orgFax"));
				aaorg.setOrgmoboile(getParameter("orgMoboile"));
				aaorg.setLegal(getParameter("legal"));
				aaorg.setOrgpostcode(getParameter("orgPostCode"));
				aaorg.setOrgurl(getParameter("orgUrl"));
				aaorg.setOrgurls(getParameter("orgUrls"));
				aaorg.setParentid(getParameter("parentid"));
				aaorg.setIsDel("0");
				
				boolean suc = sobService.addorg(aaorg);
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
	/**
	 * 
	 * @category
	 * 2015年12月10日 下午5:19:10
	 * @author 杨峥铖
	 */
	public void deleteorg(){
		try {
			String orgclassid = getParameter("orgclassid");
			boolean suc1 = sobService.findorgchild(orgclassid);
			if(suc1){
				getResponse().getWriter().write("error");
			}else{
				sobService.deletesob(orgclassid);
				getResponse().getWriter().write("success");
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
public String toAddorg(){
	String orgclassid=getParameter("orgclassid");
	setRequstAttribute("orgclassid", orgclassid);
	return "orgAdd";
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
	List classify = sobService.getClassifyList(condition);
	Map m=new HashMap();
	m.put("id", 0);
	m.put("pId",-1);
	m.put("name", "全部");
	classify.add(m);
	getRequest().setAttribute("classify", JSONArray.fromObject(classify).toString());
	setRequstAttribute("typeid", typeid);
	return "ProCusSelect";
}

public String toAddsob(){
	String sobgid = getParameter("sobgid");
	Map sob = sobService.findsob(sobgid);
	setRequstAttribute("sob", sob);
	return "sobAdd";
	}
/**
 * 
 * @category 设置用户是否可用
 * 2015年3月22日 上午8:21:08
 * @author 杨峥铖
 */
public void sobEnable(){
	try {
		Integer enable = new Integer(getParameter("enable"));
		String id = getParameter("id");
		sobService.setsobEnable(enable,id);
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
public void addsob(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			MesWmAccountinginform sob = new MesWmAccountinginform();
			String uuid = UUID.randomUUID().toString();
			sob.setGid(uuid);
			sob.setSobcode(getParameter("sobCode"));
			sob.setSobname(getParameter("sobName"));
			sob.setState(new Integer(getParameter("state")));
			sob.setOrgId(getParameter("orgId"));
			
			YmUser ymuser = new YmUser();
			ymuser.setUserCode(getParameter("userCode"));
			ymuser.setUserName(getParameter("userName"));
			ymuser.setPassWord(getParameter("passWord"));
			ymuser.setSobgid(uuid);
			ymuser.setOrggid("orgId");
			ymuser.setIsDelete(0);
			ymuser.setIsAdmin(2);
			boolean suc = sobService.addsob(sob);
			boolean suc1 = sobService.addymuser(ymuser);
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

public String orgSelect(){
	List classify = sobService.getClassifyList("");
	Map m=new HashMap();
	m.put("id", 0);
	m.put("pId",-1);
	m.put("name", "全部");
	classify.add(m);
	getRequest().setAttribute("classify", JSONArray.fromObject(classify).toString());
	return "orgSelect";
}

/**
 * 
 * @category
 * 2015年12月15日 下午1:45:26
 * @author 杨峥铖
 */
public void updatesob(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			MesWmAccountinginform sob = new MesWmAccountinginform();
			sob.setGid(getParameter("sobgid"));
			sob.setSobcode(getParameter("sobCode"));
			sob.setSobname(getParameter("sobName"));
			sob.setState(new Integer(getParameter("state")));
			sob.setOrgId(getParameter("orgId"));
			
			YmUser ymuser = new YmUser();
			ymuser.setGid(getParameter("ymusergid"));
			ymuser.setUserCode(getParameter("userCode"));
			ymuser.setUserName(getParameter("userName"));
			ymuser.setPassWord(getParameter("passWord"));
			ymuser.setSobgid(getParameter("sobgid"));
			ymuser.setOrggid(getParameter("orgId"));
			boolean suc = sobService.updatesob(sob);
			boolean suc1 = sobService.updateymuser(ymuser);
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
 * @category 展位修改界面
 * 2015年3月22日 上午8:18:30
 * @author 杨峥铖
 * @return
 */
public String getsoblist(){
	int pageIndex = getPageIndex();
	int pageSize = getPageSize();
	String keyWord = getParameter("keyWord");//搜索关键字
	String condition = CommonUtil.combQuerySql("MesWmAccountinginform.sobCode,MesWmAccountinginform.sobName", keyWord);
	setRequstAttribute("keyWord",keyWord);
	PageBean soblist = sobService.getsoblist(pageIndex,pageSize,condition);
	setRequstAttribute("data", soblist);
	return "soblist";
}

public String tosystemsetting(){
	List systemsetting = sobService.getsystemsetting();
	for(int i=0;i<systemsetting.size();i++){
		if(!CommonUtil.isNullObject(((Map)systemsetting.get(i)).get("setName"))){
			setRequstAttribute(((Map)systemsetting.get(i)).get("setName").toString(), ((Map)systemsetting.get(i)).get("paramValue").toString());
		}
	}
	
	return "systemsetting";
}

public void updatesystemsetting(){
	try {
		String msg = "";
		boolean pass = true;
		
		if(pass){
			boolean suc = sobService.updatesystemsetting(getParameter("interfaceType"),getParameter("interfaceAddress"),getParameter("cacheserverIp"),getParameter("cacheport"),getParameter("printfile"),getParameter("printmachine"),getParameter("headCheckFlag"),getParameter("bodyCheckFlag"),getParameter("isReportExceedDis"));
			Config.INTERFACETYPE= getParameter("interfaceType");
			Config.INTERFACEADDRESS= getParameter("interfaceAddress");
//			Config.CACHESERVERIP= getParameter("cacheserverIp");
//			Config.CACHEPORT= getParameter("cacheport");
			Config.PRINTFILE= getParameter("printfile");
			Config.PRINTMACHINE= getParameter("printmachine");
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

}
