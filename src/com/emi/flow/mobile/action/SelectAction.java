package com.emi.flow.mobile.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.flow.select.service.SelectService;
import com.emi.rm.service.RoleService;
import com.emi.sys.core.format.EmiJsonObj;

public class SelectAction extends BaseAction{
	private SelectService selectService;
	private RoleService roleService;
	
	public void setSelectService(SelectService selectService) {
		this.selectService = selectService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}


	/**
	 * @category 人员选择
	 * 2015年2月11日 下午1:45:23 
	 * @author 朱晓陈
	 * @return
	 */
	public void personSelect(){
		try {
			JSONObject json = getJsonObject();
//			JSONObject json = JSONObject.fromObject("{'hasAccount':'1','doType':2,'forIds':'9eee7717-3e76-4bcc-aba3-2c2e3ed02cd1'}");
			String hasAccount =CommonUtil.Obj2String(json.get("hasAccount"));	//是否只显示有账号的人员 1：是
			int doType =json.get("doType")==null?0:json.getInt("doType");		//对象类型 0-无  1-单人2-角色 3-组 4-部门
			String forIds =CommonUtil.Obj2String(json.get("forIds"));			//对象id
			String condition = "";
			if("1".equals(hasAccount)){
				condition = " and usr.gId is not null";
			}
			//人员列表
//			List<Map> personList = organizationService.personList(0, 0, condition,doType,forIds);
			Map retMap = new HashMap();
			retMap.put("success", "1");
//			retMap.put("list", personList);
			getResponse().getWriter().write(EmiJsonObj.fromObject(retMap).toString());
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
}
