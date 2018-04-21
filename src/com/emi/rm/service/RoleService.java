package com.emi.rm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.util.CommonUtil;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_Role;
import com.emi.rm.bean.RM_RoleRight;
import com.emi.rm.bean.RM_RoleUser;
import com.emi.rm.dao.RoleDao;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.RmRoleData;
@SuppressWarnings({"unchecked","rawtypes"})
public class RoleService{
	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	
	/**
	 * @category 添加角色 
	 * 2014年9月1日 下午4:08:15 
	 * @author 朱晓陈
	 * @param role
	 * @return
	 */
	public boolean addRole(RM_Role role) {
		return roleDao.addRole(role);
	}

	/**
	 * @category 更新角色 
	 * 2014年9月1日 下午4:08:23 
	 * @author 朱晓陈
	 * @param role
	 * @return
	 */
	public boolean updateRole(RM_Role role) {
		return roleDao.updateRole(role);
	}

	/**
	 * @category 删除角色 
	 * 2014年9月1日 下午4:08:30 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public boolean deleteRole(String roleId) {
		boolean ro_flag = roleDao.deleteRole(roleId);
		boolean rr_flag = roleDao.deleteRoleRight(roleId);
		boolean ru_flag = roleDao.deleteRoleUser(roleId);
		return ro_flag && rr_flag && ru_flag;
	}

	/**
	 * @category 角色列表 
	 * 2014年9月1日 下午4:08:38 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param enterpriseId
	 * @param conditionSql
	 * @return
	 */
	public PageBean getRoleList(int pageIndex, int pageSize,String conditionSql) {
		return roleDao.getRoleList(pageIndex, pageSize, conditionSql);
	}

	/**
	 * @category 保存角色权限 
	 * 2014年9月1日 下午4:08:49 
	 * @author 朱晓陈
	 * @param roleId 角色id
	 * @param rightIds_add 添加的权限
 	 * @param rightIds_del 删除的权限
	 * @return
	 */
	public boolean saveRoleRight(String roleId, String rightIds_add,String rightIds_del) {
		RM_RoleRight roleRight = null;
		List<RM_RoleRight> list = new ArrayList<RM_RoleRight>();	//角色用户列表
		String[] rights_add = rightIds_add.split(",");
		for(String rightId : rights_add){
			roleRight = new RM_RoleRight();
			roleRight.setRoleId(roleId);
			roleRight.setRightId(rightId);
			list.add(roleRight);
		}
		boolean del_suc = roleDao.deleteRoleRight(roleId,rightIds_del);
		boolean add_suc = roleDao.addRoleRight(list);
		return del_suc && add_suc;
	}
	
	/**
	 * @category  保存角色权限 
	 * 2014年9月12日 上午8:50:19 
	 * @author 朱晓陈
	 * @param roleId 角色id
	 * @param rightAndFuns 权限id和功能权值拼接的字符串
	 * @return
	 */
	public boolean saveRoleRight(String roleId, String rightAndFuns) {
		boolean flag = false;
		RM_RoleRight roleRight = null;
		List<RM_RoleRight> rr_list = new ArrayList<RM_RoleRight>();	//角色用户列表
		//解析字符串，同时创建RM_RoleRight对象
		String[] rightFuns = rightAndFuns.split(";");
		for(String rf : rightFuns){
			roleRight = new RM_RoleRight();
			String[] arr = rf.split(",");
			roleRight.setRoleId(roleId);
			roleRight.setRightId(arr[0]);
			roleRight.setUseFuns(Integer.parseInt(arr[1]));
			rr_list.add(roleRight);
		}
		
		boolean del_suc = roleDao.deleteRoleRight(roleId);
		if(del_suc){
			flag = roleDao.addRoleRight(rr_list);
		}
		
		return flag;
	}

	/**
	 * @category 角色的所有权限 
	 * 2014年9月1日 下午4:09:07 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public String getRightIds4Role(String roleId) {
		String rightIds = "";
		List<RM_Right> list_right = roleDao.getRights4Role(roleId);
		for(RM_Right right : list_right){
			String id = right.getGid();
			if(!CommonUtil.isNullString(id)){
				rightIds += id + ",";
			}
		}
		if(rightIds.length()>0){
			rightIds = rightIds.substring(0,rightIds.length()-1);
		}
		return rightIds;
	}
	
	/**
	 * @category 保存角色用户 
	 * 2014年9月1日 下午4:09:26 
	 * @author 朱晓陈
	 * @param userIds
	 * @param roleId
	 * @param enterpriseId
	 * @return
	 */
	public boolean saveRoleUser(String userIds, String roleId) {
		RM_RoleUser roleUser = null;
		List<RM_RoleUser> list = new ArrayList<RM_RoleUser>();	//角色用户列表
		String[] users = userIds.split(",");
		for(String usrId : users){
			roleUser = new RM_RoleUser();
			roleUser.setRoleId(roleId);
			roleUser.setUserId(usrId);
			list.add(roleUser);
		}
		//先删除该角色下面的所有用户关系，再进行添加
		boolean del_flag = roleDao.deleteRoleUser(roleId);
		boolean add_flag = roleDao.addRoleUser(list);
		return del_flag&&add_flag;
	}
	
	public boolean saveRoleData(String wareHouseIds, String roleId,String orgId,String sobId) {
		RmRoleData roleData = null;
		List<RmRoleData> list = new ArrayList<RmRoleData>();	//角色用户列表
		String[] wareHouseId = wareHouseIds.split(",");
		for(String wareHouse : wareHouseId){
			roleData = new RmRoleData();
			roleData.setRoleId(roleId);
			roleData.setDataGid(wareHouse);
			roleData.setDataType("W");
			roleData.setOrgId(orgId);
			roleData.setSobId(sobId);
			list.add(roleData);
		}
		//先删除该角色下面的所有用户关系，再进行添加
		boolean del_flag = roleDao.deleteRoleData(roleId);
		boolean add_flag = roleDao.addRoleData(list);
		return del_flag&&add_flag;
	}

	public RM_Role findRole(String roleId) {
		return roleDao.findRole(roleId);
	}

	/**
	 * @category 得到角色权限 
	 * 2014年9月12日 上午9:30:53 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public List<RM_RoleRight> getRoleRights(String roleId) {
		return roleDao.getRoleRights(roleId);
	}
	
	/**得到所有权限，及角色对应的权限功能
	 * @category 权限及角色权限功能 
	 * 2014年9月12日 上午10:03:52 
	 * @author 朱晓陈
	 * @param shortName
	 * @param roleId
	 * @return
	 */
	public List<Map> getRightAndFunsList(String shortName, String roleId) {
		return roleDao.getRightAndFunsList(shortName,roleId);
	}

	/**
	 * @category 得到角色的人员ids和账号 
	 * 2014年9月18日 下午1:21:43 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public Map getRoleUsersMap(String roleId) {
		List<Map> list = roleDao.getUsersByRole(roleId);
		Map map = new HashMap();
		String ids = "";
		String userNames = "";
		for(Map ru : list){
			ids += ru.get("gid")+",";
			userNames += ru.get("userName")+",";
		}
		if(ids.length()>0){
			ids = ids.substring(0,ids.length()-1);
			userNames = userNames.substring(0,userNames.length()-1);
		}
		map.put("ids", ids);
		map.put("names", userNames);
		return map;
	}
	
	public Map getRoleDataMap(String roleId) {
		List<Map> list = roleDao.getDataByRole(roleId);
		Map map = new HashMap();
		String ids = "";
		String userNames = "";
		for(Map ru : list){
			ids += ru.get("gid")+",";
			userNames += ru.get("whName")+",";
		}
		if(ids.length()>0){
			ids = ids.substring(0,ids.length()-1);
			userNames = userNames.substring(0,userNames.length()-1);
		}
		map.put("wareHouseIds", ids);
		map.put("wareHouseNames", userNames);
		return map;
	}

	public List getUserList(int pageIndex, int pageSize,String condition) {
		return roleDao.getUserList(pageIndex,pageSize,condition);
	}

	public List getWareHouseList(int pageIndex,int pageSize,String condition){
		return roleDao.getWareHouseList(pageIndex, pageSize, condition);
	}
	
}
