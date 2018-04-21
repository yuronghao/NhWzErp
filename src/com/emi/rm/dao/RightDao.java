package com.emi.rm.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.rm.bean.RM_MenuFavorite;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_Settings;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RightDao extends BaseDao {

	/**
	 * @category 得到权限列表 
	 * 2014年9月1日 下午2:05:19 
	 * @author 朱晓陈
	 * @param rightFlag
	 * @return
	 */
	public List<RM_Right> getRightList(String systemName,String nodeId,boolean showHide) {
		return getRightList(systemName, nodeId, "",showHide);
	}
	public List<RM_Right> getRightList(String systemName,String nodeId) {
		return getRightList(systemName, nodeId, "",true);
	}
	
	/**
	 * @category 得到带条件的权限列表 
	 * 2014年9月15日 下午6:15:10 
	 * @author 朱晓陈
	 * @param sysName
	 * @param nodeId
	 * @param condition
	 * @return
	 */
	public List<RM_Right> getRightList(String sysName, String nodeId,
			String condition,boolean showHide) {
		String exSql = "";
		if(CommonUtil.isNullString(nodeId)){
			exSql += " and superiorRightId is null ";
		}else{
			exSql += " and superiorRightId='"+nodeId+"' ";
		}
		if(!CommonUtil.isNullString(sysName)){
			exSql += " and ownerSys='"+sysName+"' " ;
		}
		if(!showHide){
			exSql += " and isShow=1 " ;
		}
//		String sql = "select "+CommonUtil.colsFromBean(RM_Right.class)
//				+ " from RM_Right where superiorRightId in(SELECT gid FROM RM_Right WHERE onwerSys='"+systemName+"' and "+exSql+")"
//				+ " OR "+exSql;
		String sql = "select "+CommonUtil.colsFromBean(RM_Right.class)
				+ " from RM_Right where (isDelete=0 or isDelete is null) "+exSql+condition;
		//排序
		sql += " order by rightIndex";
		return this.emiQueryList(sql, RM_Right.class);
	}
	public List<RM_Right> getRightList(String systemName,boolean showHide) {
		String sql = "select "+CommonUtil.colsFromBean(RM_Right.class)+ " from RM_Right where (isDelete=0 or isDelete is null) and rightType=0";
		if(!CommonUtil.isNullString(systemName)){
			sql+= " and ownerSys='"+systemName+"' ";
		}
		if(!showHide){
			sql += " and isShow=1 " ;
		}
		sql += " order by rightIndex";
		return this.emiQueryList(sql, RM_Right.class);
	}
	
	public List<RM_Right> getRightList4Tree(String systemName){
		String sql = "with rightTree("+CommonUtil.colsFromBean(RM_Right.class)+")"
				+ "AS(select "+CommonUtil.colsFromBean(RM_Right.class)+" from RM_Right  ";
	    sql += "	where  ownerSys='" + systemName + "'";
		sql += " union ALL"
				+ " select "+CommonUtil.colsFromBean(RM_Right.class,"subr")+" from RM_Right as subr"
				+ " INNER JOIN rightTree as c on subr.superiorRightId=c.gid)"
				+ " SELECT * from rightTree";
		return this.emiQueryList(sql, RM_Right.class);
	}

	/**
	 * @category 根据权限
	 * 2014年9月1日 下午2:36:15 
	 * @author 朱晓陈
	 * @param gId
	 * @return
	 */
	public RM_Right findRight(String gId) {
		return (RM_Right) this.emiFind(gId, RM_Right.class);
	}

	public boolean updateRight(RM_Right p_right, boolean nullflag) {
		
		if(nullflag){
			String sql="update RM_Right set superiorRightId=null where gid='"+p_right.getGid()+"'";
			this.update(sql);
			return true;
		}else{
			return this.emiUpdate(p_right);
		}
			
	
	}

	public boolean addRight(RM_Right right) {
		return this.emiInsert(right);
	}
	
	public boolean deleteRight(String gid){
		try {
			gid = "'"+gid.replaceAll(",", "','")+"'";
//			String sql = "delete from RM_Right where gid in ("+gid+")";
			String sql = "update RM_Right set isDelete=1 where gid in ("+gid+")";
			this.update(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * @category 是否含有子权限 
	 * 2014年9月5日 下午3:49:14 
	 * @author 朱晓陈
	 * @param superiorRightId
	 * @return
	 */
	public boolean hashChildRight(String superiorRightId) {
		String sql = "select count(gid) from RM_Right where superiorRightId='"+superiorRightId+"'";
		int count = this.queryForInt(sql);
		return count>0;
	}
	
	/**
	 * @category 得到系统设置 
	 * 2014年9月5日 下午3:49:26 
	 * @author 朱晓陈
	 * @param setName
	 * @return
	 */
	public RM_Settings getSettings(String setName) {
		String sql = "select "+CommonUtil.colsFromBean(RM_Settings.class)+" from RM_Settings where setName='"+setName+"'";
		return (RM_Settings) this.emiQuery(sql, RM_Settings.class);
	}
	/**
	 * @category 父权限 
	 * 2014年9月5日 下午3:49:01 
	 * @author 朱晓陈
	 * @param rightId
	 * @return
	 */
	public RM_Right findSuperiorRight(String rightId) {
		String sql = "select "+CommonUtil.colsFromBean(RM_Right.class)+" from RM_Right where gid in (select superiorRightId from RM_Right where gid='"+rightId+"')";
		return (RM_Right) this.emiQuery(sql, RM_Right.class);
	}
	
	/**
	 * @category 是否含有相同的权限代码 
	 * 2014年9月12日 上午11:40:51 
	 * @author 朱晓陈
	 * @param rightCode
	 * @return
	 */
	public boolean hasSameCode(String rightCode,String rightId) {
		String sql = "select count(pk) from RM_Right where rightCode='"+rightCode+"' and gid<>'"+rightId+"'";
		return this.queryForInt(sql)>0;
	}

	/**
	 * @category 得到子权限，包括子权限的子权限 
	 * 2014年9月19日 上午11:16:22 
	 * @author 朱晓陈
	 * @param gid
	 * @return
	 */
	public List<RM_Right> getChildRight(String gid) {
		String sql = "with rightTree("+CommonUtil.colsFromBean(RM_Right.class)+")"
				+ "AS(select "+CommonUtil.colsFromBean(RM_Right.class)+" from RM_Right  ";
	    sql += "	where  gid='" + gid + "' ";
		sql += " union ALL"
				+ " select "+CommonUtil.colsFromBean(RM_Right.class,"subr")+" from RM_Right as subr"
				+ " INNER JOIN rightTree as c on subr.superiorRightId=c.gid)"
				+ " SELECT * from rightTree";
		return this.emiQueryList(sql, RM_Right.class);
	}

	/**
	 * @category 我收藏的菜单 
	 * 2014年10月10日 下午1:12:06 
	 * @author 朱晓陈
	 * @param userId
	 * @return
	 */
	public List<Map> getMenuFavorites(String userId) {
		String sql = "select "+CommonUtil.colsFromBean(RM_MenuFavorite.class,"mf")+",r.rightCode,r.rightUrl,r.rightName,r.ownerSys "
				+ "from RM_MenuFavorite mf left join RM_Right r on mf.menuId=r.gid "
				+ "where mf.userId='"+userId+"' ";
		return this.queryForList(sql);
	}
	
	/**
	 * @category 添加收藏 
	 * 2014年10月10日 下午1:18:17 
	 * @author 朱晓陈
	 * @param mf
	 * @return
	 */
	public boolean addFavorite(RM_MenuFavorite mf){
		return this.emiInsert(mf);
	}
	
	/**
	 * @category 更新收藏 
	 * 2014年10月10日 下午1:18:28 
	 * @author 朱晓陈
	 * @param mf
	 * @return
	 */
	public boolean updateFavorite(RM_MenuFavorite mf){
		return this.emiUpdate(mf);
	}
	
	/**
	 * @category 删除收藏 
	 * 2014年10月10日 下午1:24:22 
	 * @author 朱晓陈
	 * @param mfId
	 * @return
	 */
	public boolean deleteFavorite(String mfId){
		try {
			String sql = "delete from RM_MenuFavorite where gid='"+mfId+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
