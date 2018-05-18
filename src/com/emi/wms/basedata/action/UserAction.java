package com.emi.wms.basedata.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.Base64;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.common.util.PasswordUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.wms.basedata.service.BasicSettingService;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Accountperiod;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.Unitconversion;
import com.emi.wms.bean.YmUser;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = -7096047009988156782L;
	private BasicSettingService basicSettingService;
	
	private CacheCtrlService cacheCtrlService;

	public void setBasicSettingService(BasicSettingService basicSettingService) {
		this.basicSettingService = basicSettingService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	
	/**
	 * @category 用户列表页
	 *2016 2016年1月4日下午1:51:01
	 *String
	 *宋银海
	 */
	public String getUser(){
		
		try {
			int pageIndex = getPageIndex();	
			int pageSize = getPageSize();
			String keyWord = getParameter("keyWord");//搜索关键字
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id
			String condition = CommonUtil.combQuerySql("userCode,userName", keyWord);
			PageBean pageBean=basicSettingService.getYmUser(pageIndex, pageSize, orgId, sobId,condition);
			setRequstAttribute("data", pageBean);
			setRequstAttribute("keyWord",keyWord);
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "getUser";
	}
	
	
	/**
	 * @category 跳转到增加用户页
	 *2016 2016年1月4日下午1:52:14
	 *String
	 *宋银海
	 */
	public String toAddUser(){


		return "toAddUser";
	}

	public String toEditUser(){
		try {
			String gid = getParameter("gid");
			YmUser user = basicSettingService.findUser(gid);
			
			setRequstAttribute("type", "edit");
			setRequstAttribute("ymuser", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toAddUser";
	}
	
	/**
	 * @category 添加用户
	 *2016 2016年1月4日下午1:39:41
	 *void
	 *宋银海
	 */
	public void addUser(){
		try{
			String type = getParameter("type");
			
			String orgId=getSession().get("OrgId").toString();//组织id
			String sobId=getSession().get("SobId").toString();//账套id

			YmUser ymUser=new YmUser();
			if("edit".equals(type)){
				ymUser.setGid(getParameter("gid"));
			}else{
				ymUser.setGid(UUID.randomUUID().toString());
			}
			
			ymUser.setUserCode(getParameter("userCode"));
			ymUser.setUserName(getParameter("userName"));
			ymUser.setPassWord(PasswordUtil.generatePassword(getParameter("passWord")));
			ymUser.setIsDelete(Integer.valueOf(getParameter("isUse")));
			ymUser.setOrggid(orgId);
			ymUser.setSobgid(sobId);
			

			boolean ok= false;
			if("edit".equals(type)){
				ok=basicSettingService.editUser(ymUser);
			}else{
				ok=basicSettingService.addUser(ymUser);
			}
			
			
			if(ok){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("fail");
			}
			
		}catch(Exception e){
			try {
				getResponse().getWriter().write("保存失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	

}
