package com.emi.rm.dao;


import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_Role;
import com.emi.rm.bean.RM_RoleRight;
import com.emi.rm.bean.RM_RoleUser;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.RmRoleData;
import com.emi.wms.bean.YmUser;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleDao extends BaseDao {

	/**
	 * @category 增加角色 
	 * 2014年9月1日 下午4:02:50 
	 * @author 朱晓陈
	 * @param role
	 * @return
	 */
	public boolean addRole(RM_Role role) {
		return this.emiInsert(role);
	}

	/**
	 * @category 更新角色 
	 * 2014年9月1日 下午4:03:02 
	 * @author 朱晓陈
	 * @param role
	 * @return
	 */
	public boolean updateRole(RM_Role role) {
		return this.emiUpdate(role);
	}

	/**
	 * @category 删除角色 
	 * 2014年9月1日 下午4:03:10 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public boolean deleteRole(String roleId) {
		try {
			roleId = roleId.replaceAll(",", "','");
//			String sql = "delete from RM_Role where gid='"+roleId+"'";
			String sql = "update RM_Role set isDelete=1 where gid in ('"+roleId+"')";
			this.update(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @category 得到角色列表
	 * 2014年9月1日 下午4:03:18 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @param conditionSql
	 * @return
	 */
	public PageBean getRoleList(int pageIndex, int pageSize,String conditionSql) {
		String sql = "select pk,"+CommonUtil.colsFromBean(RM_Role.class)+" FROM dbo.RM_Role where (isDelete=0 or isDelete is null) ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, RM_Role.class,"");
	}

	/**
	 * @category 得到角色的权限 
	 * 2014年9月1日 下午4:03:30 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public List<RM_Right> getRights4Role(String roleId) {
		String sql = "SELECT "+CommonUtil.colsFromBean(RM_Right.class, "")+" FROM RM_Right WHERE gid IN (SELECT rr.rightId FROM RM_RoleRight rr WHERE rr.roleId='"+roleId+"')";
		return this.emiQueryList(sql, RM_Right.class);
	}
	
	/**
	 * @category 删除角色下的所有用户 
	 * 2014年9月1日 下午4:03:46 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public boolean deleteRoleUser(String roleId){
		try {
			roleId = roleId.replaceAll(",", "','");
			String sql = "delete from RM_RoleUser where roleId in ('"+roleId+"')";
			this.update(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteRoleData(String roleId){
		try {
			roleId = roleId.replaceAll(",", "','");
			String sql = "delete from RM_RoleData where roleId in ('"+roleId+"')";
			this.update(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @category 删除角色下的所有权限 
	 * 2014年9月1日 下午4:03:58 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public boolean deleteRoleRight(String roleId) {
		try {
			roleId = roleId.replaceAll(",", "','");
			String sql = "delete from RM_RoleRight where roleId in ('"+roleId+"')";
			this.update(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @category 删除角色下的指定权限 
	 * 2014年9月1日 下午4:04:16 
	 * @author 朱晓陈
	 * @param roleId
	 * @param rightIds_del
	 * @return
	 */
	public boolean deleteRoleRight(String roleId, String rightIds_del) {
		boolean flag = false;
		try {
			rightIds_del = "'" + rightIds_del.replaceAll(",", "','") + "'";
			String sql = "delete from RM_RoleRight where roleId='"+roleId+"' and rightId in ("+rightIds_del+")";
			this.update(sql);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	/**
	 * @category  添加角色权限
	 * 2014年9月1日 下午4:04:35 
	 * @author 朱晓陈
	 * @param list
	 * @return
	 */
	public boolean addRoleRight(List<RM_RoleRight> list) {
		return this.emiInsert(list);
	}
	
	/**
	 * @category  添加角色用户
	 * 2014年9月1日 下午4:05:47 
	 * @author 朱晓陈
	 * @param list
	 * @return
	 */
	public boolean addRoleUser(List<RM_RoleUser> list) {
		return this.emiInsert(list);
	}
	
	public boolean addRoleData(List<RmRoleData> list) {
		return this.emiInsert(list);
	}

	/**
	 * @category 查单个角色 
	 * 2014年9月12日 上午9:32:04 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public RM_Role findRole(String roleId) {
		return (RM_Role) this.emiFind(roleId, RM_Role.class);
	}

	/**
	 * @category 得到角色权限
	 * 2014年9月12日 上午9:31:56 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public List<RM_RoleRight> getRoleRights(String roleId) {
		String sql = "select "+CommonUtil.colsFromBean(RM_RoleRight.class)+" from RM_RoleRight";
		return this.emiQueryList(sql, RM_RoleRight.class);
	}
	
	/**
	 * 得到所有权限，及角色对应的权限功能
	 * @category 权限及角色权限功能 
	 * 2014年9月12日 上午10:03:52 
	 * @author 朱晓陈
	 * @param shortName
	 * @param roleId
	 * @return
	 */
	public List<Map> getRightAndFunsList(String systemName, String roleId) {
		String sql = "select "+CommonUtil.colsFromBean(RM_Right.class,"r")+",rrr.useFuns "
				+ " from RM_Right r left join (SELECT gid,rightId,roleId,rr.useFuns FROM RM_RoleRight rr where rr.roleId='"+roleId+"' ) AS rrr "
				+ " on r.gid=rrr.rightId ";
		if(!CommonUtil.isNullString(systemName)){
			sql+= " where r.ownerSys='"+systemName+"' and r.isShow=1 ";
		}
		return this.queryForList(sql);
	}

	/**
	 * @category 得到角色用户集 
	 * 2014年9月18日 下午1:40:50 
	 * @author 朱晓陈
	 * @param roleId
	 * @return
	 */
	public List getUsersByRole(String roleId) {
		String sql = "select u.gid,u.notes,u.userCode,u.userName,u.passWord,u.pause,u.isAdmin from YM_User u left join RM_RoleUser ru on u.gid=ru.userId where ru.roleId='"+roleId+"'";
		return this.queryForList(sql);
	}
	
	public List getDataByRole(String roleId) {
		String sql = "select wh.gid,wh.whCode,wh.whName from AA_WareHouse wh left join RM_RoleData rd on wh.gid=rd.dataGid where rd.roleId='"+roleId+"'";
		return this.queryForList(sql);
	}

	/**
	 * @category 用户列表
	 * 2015年12月7日 下午5:16:34 
	 * @author 朱晓陈
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List getUserList(int pageIndex, int pageSize,String condition) {
		String sql = "select "+CommonUtil.colsFromBean(YmUser.class)+" from YM_User where (isDelete=0 or isDelete is null) "+condition;
		return this.emiQueryList(sql, YmUser.class);
	}
	
	public List getWareHouseList(int pageIndex, int pageSize,String condition) {
		String sql = "select "+CommonUtil.colsFromBean(AaWarehouse.class)+" from AA_WareHouse where (isDel=0 or isDel is null) "+condition;
		return this.emiQueryList(sql, AaWarehouse.class);
	}
}
