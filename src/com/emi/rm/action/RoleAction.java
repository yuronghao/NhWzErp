package com.emi.rm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.util.TokenHelper;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.rm.bean.RM_Role;
import com.emi.rm.bean.RightSystem;
import com.emi.rm.service.RightService;
import com.emi.rm.service.RoleService;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;

public class RoleAction extends BaseAction {

	private static final long serialVersionUID = -2100832030798307514L;
	private RoleService roleService;
	private RightService rightService;
	private RM_Role role;		//权限实体

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public RM_Role getRole() {
		return role;
	}

	public void setRole(RM_Role role) {
		this.role = role;
	}

	/**
	 * @category 跳转到创建角色 
	 * 2014年9月3日 下午2:44:13 
	 * @author 朱晓陈
	 * @return
	 */
	public String toAddRole(){
		
		return "roleAdd";
	}
	/**
	 * @category 创建角色
	 * 2014年8月11日 上午9:18:00 
	 * @author 朱晓陈
	 */
	public String addRole(){
		//防止重复提交
		if(!TokenHelper.validToken()){
			return roleList();
		}
		try {
//			String userId = CommonUtil.Obj2String(getSession().get("UserId"));		//操作人
			String userIds = getParameter("userIds");	//分配的人
			String wareHouseIds = getParameter("wareHouseIds");	//分配的仓库
			
			String roleId = UUID.randomUUID().toString();
			role.setGid(roleId);
			
			boolean suc = roleService.addRole(role);
			boolean ru_suc = true;
			boolean ru_suc1 = true;
			if(suc && !CommonUtil.isNullString(userIds)){
				//如果有人员，则保存
				ru_suc = roleService.saveRoleUser(userIds, roleId);
				ru_suc1 = roleService.saveRoleData(wareHouseIds, roleId,getSession().get("OrgId").toString(),getSession().get("SobId").toString());
			}
			if (suc && ru_suc && ru_suc1) {
				return roleList();
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * @category  
	 * 2014年9月9日 上午8:51:06 
	 * @author 朱晓陈
	 * @return
	 */
	public String toUpdateRole(){
		String roleId = getParameter("gId");
		Map usersMap = roleService.getRoleUsersMap(roleId);	//查找已有的用户id和用户名
		Map dataMap = roleService.getRoleDataMap(roleId);	//查找已有的用户id和用户名
		RM_Role role = roleService.findRole(roleId);
		getRequest().setAttribute("role", role);
		getRequest().setAttribute("userIds", usersMap.get("ids"));
		getRequest().setAttribute("userNames", usersMap.get("names"));
		getRequest().setAttribute("wareHouseIds", dataMap.get("wareHouseIds"));
		getRequest().setAttribute("wareHouseNames", dataMap.get("wareHouseNames"));
		getRequest().setAttribute("onlyShow", getParameter("onlyShow"));
		return "roleEdit";
	}
	
	/**
	 * @category 编辑角色/角色增加人员
	 * 2014年8月11日 上午10:25:59 
	 * @author 朱晓陈
	 */
	public void updateRole(){
		try {
			String userIds = getParameter("userIds");		 	//人员id，多个用逗号隔开
			String wareHouseIds = getParameter("wareHouseIds");	//分配的仓库
			boolean suc_ro = roleService.updateRole(role);		//更新角色
			boolean suc_rr = roleService.saveRoleUser(userIds,role.getGid());	//保存角色分配用户
			boolean suc_rd = roleService.saveRoleData(wareHouseIds, role.getGid(),getSession().get("OrgId").toString(),getSession().get("SobId").toString());
			if (suc_ro && suc_rr&&suc_rd) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 删除角色
	 * 2014年8月11日 下午2:33:13 
	 * @author 朱晓陈
	 */
	public String deleteRole(){
		try {
			String roleId = getParameter("gId");			//角色ID
			//删除角色
			boolean suc = roleService.deleteRole(roleId);	
			if (suc ) {
				return roleList();
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * @category 角色列表
	 * 2014年8月11日 上午9:44:43 
	 * @author 朱晓陈
	 */
	public String roleList(){
		try {
			String searchText = getParameter("searchText");				//搜索关键字
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = getPageSize();							//每页总条数
			
			PageBean bean = roleService.getRoleList(pageIndex,pageSize,"");
			getRequest().setAttribute("data", bean);
			/****导出数据 begin****/
			if(isEmiExport()){
				return exportData(bean.getList(),"角色列表"+DateUtil.dateToString(new Date(), "yyyyMMddHHmmss"));
			}
			/****分页条上的 导出数据 end****/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "roleList";
	}
	
	/**
	 * 跳转到角色权限分配界面
	 * 2014年9月9日 下午4:06:34 
	 * @author 朱晓陈
	 * @return
	 */
	public String toRoleAuth(){
		try {
			String roleId = getParameter("gid");
			RM_Role role = roleService.findRole(roleId);
//			String rightIds = roleService.getRightIds4Role(roleId);
			
			//权限id级含有的功能值
			String rightFunctions = "";
			//各个系统的权限集合
			List<Map> allrights = new ArrayList<Map>();
			List<RightSystem> systems = rightService.getSystems();
			for(RightSystem sys : systems){
				//所有的权限及该角色对应的权限功能
				List<Map> tempRights = roleService.getRightAndFunsList(sys.getShortName(),roleId);
				//将list转成有序的适应树形结构的列表（如：1,1-1,1-2,2,2-1,2-1-1）
				//并放入新list中
				List<Map> sysRights = new ArrayList<Map>();
				rightService.toTreeList(tempRights,sysRights,null);
				Map map = new HashMap();
				map.put("systemName", sys.getFullName());
				map.put("shortName", sys.getShortName());
				map.put("list", sysRights);
				allrights.add(map);
				
				for(Map r : sysRights){
					rightFunctions += CommonUtil.Obj2String(r.get("gid"))+","+
								CommonUtil.Obj2String(r.get("functions"))+","+
								CommonUtil.Obj2String(r.get("useFuns")+";");
				}
			}
			if(rightFunctions.length()>0){
				rightFunctions=rightFunctions.substring(0,rightFunctions.length()-1);
			}
			//查询该角色的权限
//			List<RM_RoleRight> roleRights = roleService.getRoleRights(roleId);
			//查询权限的控制范围 'on':控制菜单和增删改操作，'off':只控制菜单
			String rangeSwitch = rightService.getRightRangeSwitch();
			
			getRequest().setAttribute("rangeSwitch", rangeSwitch);
			getRequest().setAttribute("rightFunctions", rightFunctions);
			getRequest().setAttribute("allRights", allrights);
			getRequest().setAttribute("role", role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "roleAuth";
	}
	/**
	 * @category 保存角色分配的权限
	 * 2014年8月11日 上午10:58:11 
	 * @author 朱晓陈
	 */
	public void saveRoleRight(){
		try {
			String roleId = getParameter("roleId");				//角色id
			String rightAndFuns = getParameter("rightFuns");
//			String rightIds_add = getParameter("rightIds_add");			//权限id，多个用逗号隔开
//			String rightIds_del = getParameter("rightIds_del");
			boolean suc = roleService.saveRoleRight(roleId,rightAndFuns);
			if (suc) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 得到角色的所有权限id 
	 * 2014年8月14日 下午5:10:29 
	 * @author 朱晓陈
	 */
	public void getRights4Role(){
		try {
			String roleId = getParameter("roleId");
			String rightIds = roleService.getRightIds4Role(roleId);
			getResponse().getWriter().write("{\"success\":\"1\",\"rightIds\":"+rightIds+"}");
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}
	}
	
	/**
	 * @category  选择用户界面
	 * 2014年9月18日 上午10:40:03 
	 * @author 朱晓陈
	 * @return
	 */
	public String toSelectUser(){
		String selectedUserIds = getParameter("selectedUserIds");
		String keyWord=getParameter("keyWord");
		String condition="";
		if(!CommonUtil.isNullObject(keyWord)){
			condition=" and (userCode like '%"+keyWord+"%' or userName like '%"+keyWord+"%')";
		}
		List list = roleService.getUserList(-1, -1,condition);//用户列表
		getRequest().setAttribute("userList", list);
		getRequest().setAttribute("selectedUserIds", selectedUserIds);
		return "selectUser";
	}
	
	
	
	/**
	 * 
	 *2016 2016年12月9日下午10:19:00
	 *String
	 *宋银海
	 */
	public String toSelectWareHouse(){
		String selectedIds = getParameter("selectedIds");
		String keyWord=getParameter("keyWord");
		String condition="";
		if(!CommonUtil.isNullObject(keyWord)){
			condition=" and (whCode like '%"+keyWord+"%' or whName like '%"+keyWord+"%')";
		}
		List list = roleService.getWareHouseList(-1, -1,condition);//用户列表
		getRequest().setAttribute("whList", list);
		getRequest().setAttribute("selectedIds", selectedIds);
		return "selectWareHouse";
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		String[] aa = {"1","2"};
		int d = 0;
		for(String a1 : aa){
			d |= Integer.parseInt(a1);
		}
		System.out.println(d);
	}
	
}
