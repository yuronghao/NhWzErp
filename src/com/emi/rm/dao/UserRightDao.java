package com.emi.rm.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_Role;
import com.emi.rm.bean.RM_RoleUser;
import com.emi.rm.bean.RM_UserRight;
import com.emi.wms.bean.YmUser;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserRightDao extends BaseDao{

	/**
	 * @category 添加用户权限 
	 * 2014年9月1日 下午4:36:34 
	 * @author 朱晓陈
	 * @param list
	 * @return
	 */
	public boolean addUserRight(List<RM_UserRight> list) {
		return this.emiInsert(list);
	}

	/**
	 * @category 得到用户的权限 
	 * 2014年9月1日 下午4:37:40 
	 * @author 朱晓陈
	 * @param userId
	 * @param enterpriseId
	 * @return
	 */
	public List<RM_Right> getUserRights(String userId,String enterpriseId) {
		String enter_sql = "";
		if(!CommonUtil.isNullString(enterpriseId)){
			enter_sql = " and enterpriseId='"+enterpriseId+"'";
		}
		String sql = "select "+CommonUtil.colsFromBean(RM_Right.class, "")+" from RM_Right where (isDelete=0 or isDelete is null) and gid in "
				+ "(select rightId from RM_UserRight where userId='"+userId+"'"+enter_sql+")" ;
		return this.emiQueryList(sql, RM_Right.class);
	}

	/**
	 * @category 得到用户的角色 
	 * 2014年9月1日 下午4:39:26 
	 * @author 朱晓陈
	 * @param userId
	 * @param enterpriseId
	 * @return
	 */
	public List<RM_Role> getUserRole(String userId) {
		String sql = "select "+CommonUtil.colsFromBean(RM_Role.class, "")+" from RM_Role where (isDelete=0 or isDelete is null) and "
				+ " gid in (select distinct roleId from RM_RoleUser where userId='"+userId+"')";
		return this.emiQueryList(sql, RM_Role.class);
	}

	/**
	 * @category 得到用户自定义权限 
	 * 2014年9月1日 下午4:40:55 
	 * @author 朱晓陈
	 * @param userId
	 * @param enterpriseId
	 * @return
	 */
	public List<RM_UserRight> getUserRight(String userId, String enterpriseId) {
		String sql = "select "+CommonUtil.colsFromBean(RM_UserRight.class)+" from RM_UserRight where "
				+ "enterpriseId='"+enterpriseId+"' and userId='"+userId+"'";
		return this.emiQueryList(sql, RM_UserRight.class);
	}

	/**
	 * @category 拷贝权限
	 * 2014年9月1日 下午4:41:22 
	 * @author 朱晓陈
	 * @param toUserId
	 * @param roleList
	 * @param userRights
	 * @param enterpriseId
	 */
	public void copyRight( String toUserId,List<RM_Role> roleList, List<RM_UserRight> userRights) {
		List<RM_RoleUser> roleUser_list = new ArrayList<RM_RoleUser>();
		List<RM_UserRight> userRight_list = new ArrayList<RM_UserRight>();
		
		RM_RoleUser roleUser = null;
		RM_UserRight userRight = null;
		for(RM_Role role : roleList){
			roleUser = new RM_RoleUser();
			roleUser.setRoleId(role.getGid());
			roleUser.setUserId(toUserId);
			roleUser_list.add(roleUser);
		}
		for(RM_UserRight ur : userRights){
			userRight = new RM_UserRight();
			userRight.setRightId(ur.getRightId());
			userRight.setUserId(toUserId);
			userRight_list.add(userRight);
		}
		this.emiInsert(roleUser_list);
		this.emiInsert(userRight_list);
		
	}

	/**
	 * @category 清除用户权限 
	 * 2014年9月1日 下午4:42:26 
	 * @author 朱晓陈
	 * @param userId
	 * @param enterpriseId
	 */
	public void clearRight(String userId,String enterpriseId) {
		String del_roleUser = "delete from RM_RoleUser where roleId in "
				+ "(select gid from RM_Role where enterpriseId='"+enterpriseId+"') and userId='"+userId+"'";
		String del_userRight = "delete from RM_UserRight where userId='"+userId+"' and enterpriseId='"+enterpriseId+"'";
		this.update(del_roleUser);
		this.update(del_userRight);
	}
	
	/**
	 * @category 删除用户自定义的权限 
	 * 2014年9月1日 下午4:42:41 
	 * @author 朱晓陈
	 * @param userId
	 * @param rightIds_del
	 * @return
	 */
	public boolean deleteUserRight(String userId, String rightIds_del) {
		boolean flag = false;
		try {
			rightIds_del = "'" + rightIds_del.replaceAll(",", "','") + "'";
			String sql = "delete from YM_UserRight where userId='"+userId+"' and rightId in ("+rightIds_del+")";
			this.update(sql);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	/**
	 * @category 用户所在角色中的权限 
	 * 2014年9月1日 下午4:46:51 
	 * @author 朱晓陈
	 * @param userId
	 * @param enterpriseId
	 * @return
	 */
	public List<RM_Right> getUserRightFromRole(String userId,String sysName,int rightType,boolean showHide) {
//		Map map = new HashMap();
//		map.put("useFuns", "RM_Right.useFuns");
//		String sql = "SELECT "+CommonUtil.colsFromBean(RM_Right.class, "r")+",rr.useFuns FROM RM_Right r LEFT JOIN RM_RoleRight rr ON r.gid=rr.rightId WHERE rr.roleId IN "+ 
//					" (SELECT ru.roleId FROM RM_RoleUser ru WHERE ru.userId='"+userId+"' )  and ownerSys='"+sysName+"' group by "+CommonUtil.colsFromBean(RM_Right.class, "r");
//		return this.emiQueryList(sql, RM_Right.class,map);
		
		String sql = "SELECT "+CommonUtil.colsFromBean(RM_Right.class, "r")+" FROM RM_Right r WHERE r.gid IN "
				+ "(SELECT rr.rightId FROM RM_RoleRight rr WHERE rr.roleId IN "
				+ "(SELECT ru.roleId FROM RM_RoleUser ru WHERE ru.userId='"+userId+"' ) and rr.useFuns&1=1 ) and ownerSys='"+sysName+"' and rightType="+rightType;
		if(!showHide){
			sql += " and r.isShow=1 and (r.isDelete=0 or r.isDelete is null) " ;
		}
		sql += " order by r.rightIndex ";
		return this.emiQueryList(sql, RM_Right.class);
	}
	
	public List<RM_Right> getUserRightFromRole(String userId,String sysName,boolean showHide) {
		return this.getUserRightFromRole(userId, sysName,0, showHide);
	}

	/**
	 * @category 得到用户操作功能权限 
	 * 2014年9月22日 下午5:50:14 
	 * @author 朱晓陈
	 * @param userId
	 * @param sysName
	 * @return
	 */
	public List<Map> getRightFuns(String userId, String sysName) {
		String sql = "SELECT r.gid,r.rightCode,r.rightName,rr.roleId,rr.useFuns FROM RM_Right r LEFT JOIN RM_RoleRight rr ON r.gid=rr.rightId "
				+ " WHERE rr.roleId IN "
				+ " (SELECT ru.roleId FROM RM_RoleUser ru WHERE ru.userId='"+userId+"' ) "
				+ " AND r.ownerSys='"+sysName+"'";
		List<Map> list = this.queryForList(sql);
		return list;
	}
	
	public YmUser getUserById(String userId) {
		String sql = "select "+CommonUtil.colsFromBean(YmUser.class, "")+" from Ym_User where gid='"+userId+"'";
		return (YmUser) emiQuery(sql, YmUser.class);
	}

	public boolean updateUser(YmUser user) {
		return this.emiUpdate(user);
	}
}
