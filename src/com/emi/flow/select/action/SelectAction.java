package com.emi.flow.select.action;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.flow.select.service.SelectService;
import com.emi.rm.service.RoleService;
import com.emi.sys.core.bean.PageBean;

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
	 * 角色选择
	 */
	public String roleSelect(){
		try {
			String selectIds = this.getParameter("selectValue");
			//所有角色列表
			PageBean pageBean = roleService.getRoleList(-1, -1, "");
			this.setRequstAttribute("roleList", pageBean.getList());
			this.setRequstAttribute("selectValue", selectIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "role_select";
	}
	
	/**
	 * 人员选择
	 */
	public String personSelect(){
		try {
			String selectIds = CommonUtil.null2Str(this.getParameter("selectValue"));
			String hasAccount = CommonUtil.null2Str(this.getParameter("hasAccount"));//是否只显示有账号的人员 1：是
			String doType_str = CommonUtil.null2Str(this.getParameter("doType"));	//对象类型 0-无  1-单人2-角色 3-组 4-部门
			String forIds = CommonUtil.null2Str(this.getParameter("forIds"));	//对象id
			String personOrUser = CommonUtil.null2Str(this.getParameter("personOrUser"));	//返回人员id还是用户id 1：用户id 其他：人员id
			String condition = "";
			if("1".equals(hasAccount)){
				condition = " and usr.gId is not null";
			}
			int doType = CommonUtil.isNullString(doType_str)?0:Integer.parseInt(doType_str);
//			List<Map> personList = organizationService.personList(0, 0, condition,doType,forIds);
//			this.setRequstAttribute("personList", personList);
			this.setRequstAttribute("selectValue", selectIds);
			this.setRequstAttribute("personOrUser", personOrUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "person_select";
	}
}
