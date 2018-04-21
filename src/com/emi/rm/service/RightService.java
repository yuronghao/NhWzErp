package com.emi.rm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.rm.bean.RM_MenuFavorite;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_Settings;
import com.emi.rm.bean.RightSystem;
import com.emi.rm.dao.RightDao;
import com.emi.sys.util.SysPropertites;
@SuppressWarnings({"unchecked","rawtypes"})
public class RightService{
	private RightDao rightDao;

	public RightDao getRightDao() {
		return rightDao;
	}

	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	/**
	 * @category 得到权限列表 
	 * 2014年9月1日 下午2:42:26 
	 * @author 朱晓陈
	 * @param systemName
	 * @return
	 */
	public List<RM_Right> getRightList(String systemName) {
		return rightDao.getRightList(systemName,true);
	}
	public List<RM_Right> getRightList(String systemName,boolean showHide) {
		return rightDao.getRightList(systemName,showHide);
	}
	
	/**
	 * @category 带条件的权限列表 
	 * 2014年9月15日 下午6:11:49 
	 * @author 朱晓陈
	 * @param sysName
	 * @param nodeId
	 * @param condition
	 * @return
	 */
	public List<RM_Right> getRightList(String sysName, String nodeId,
			String condition) {
		if("null".equals(nodeId)){
			nodeId=null;
		}
		return rightDao.getRightList(sysName,nodeId,condition,true);
	}
	
	/**
	 * @category 树结构的权限列表 
	 * 2014年9月10日 下午5:53:51 
	 * @author 朱晓陈
	 * @param systemName
	 * @return
	 */
	public List<RM_Right> getRightList4Tree(String systemName) {
		return rightDao.getRightList4Tree(systemName);
	}
	
	public List<RM_Right> getRightList(String systemName,String pRightId) {
		if("null".equals(pRightId)){
			pRightId=null;
		}
		return rightDao.getRightList(systemName,pRightId);
	}

	/**
	 * @category 得到单个权限 
	 * 2014年9月1日 下午2:43:30 
	 * @author 朱晓陈
	 * @param gId
	 * @return
	 */
	public RM_Right findRight(String gId) {
		return rightDao.findRight(gId);
	}

	/**
	 * @category 更新权限 
	 * 2014年9月1日 下午2:43:43 
	 * @author 朱晓陈
	 * @param p_right
	 * @param nullflag 
	 * @return
	 */
	public boolean updateRight(RM_Right p_right, boolean nullflag) {
		return rightDao.updateRight(p_right, nullflag);
	}

	/**
	 * @category 添加权限 
	 * 2014年9月1日 下午2:43:52 
	 * @author 朱晓陈
	 * @param right
	 * @return
	 */
	public boolean addRight(RM_Right right) {
		return rightDao.addRight(right);
	}

	/**
	 * @category 删除权限 
	 * 2014年9月1日 下午2:44:01 
	 * @author 朱晓陈
	 * @param gid
	 * @return
	 */
	public boolean deleteRight(String gid){
		//级联子权限
		List<RM_Right> childRight = rightDao.getChildRight(gid);
		String childIds = "";
		for(RM_Right rr : childRight){
			childIds += rr.getGid()+",";
		}
		if(childIds.length()>0){
			childIds.substring(0,childIds.length()-1);
		}
		boolean mainSuc = rightDao.deleteRight(gid);
		boolean childSuc = rightDao.deleteRight(childIds);
		return mainSuc && childSuc;
	}
	
	/**
	 * @category 是否含有子权限 
	 * 2014年9月1日 下午2:56:26 
	 * @author 朱晓陈
	 * @param superiorRightId
	 * @return
	 */
	public boolean hashChildRight(String superiorRightId) {
		return rightDao.hashChildRight(superiorRightId);
	}

	/**
	 * @category 系统设置 
	 * 2014年9月5日 上午10:27:45 
	 * @author 朱晓陈
	 * @param setName
	 * @return
	 */
	public RM_Settings getSettings(String setName) {
		return rightDao.getSettings(setName);
	}

	/**
	 * @category 得到权限管理的系统 
	 * 2014年9月5日 下午2:47:16 
	 * @author 朱晓陈
	 * @return
	 */
	public List<RightSystem> getSystems() {
		List<RightSystem> list = new ArrayList<RightSystem>();
		RM_Settings setting = this.getSettings(Constants.SET_SYSTEMS);
		if(setting != null){
			String value = setting.getParamValue();
			String[] systems = value.split(";");
			for(String str : systems){
				RightSystem system = new RightSystem();
				String[] sys = str.split(",");
				system.setShortName(sys[0]);
				system.setFullName(sys[1]);
				list.add(system);
			}
		}
		return list;
	}

	/**
	 * @category 父权限 
	 * 2014年9月5日 下午3:48:38 
	 * @author 朱晓陈
	 * @param rightId
	 * @return
	 */
	public RM_Right findSuperiorRight(String rightId) {
		return rightDao.findSuperiorRight(rightId);
	}
	
	/**
	 * 将权限列表转成有序的适应树形结构的列表（如：1,1-1,1-2,2,2-1,2-1-1）
	 * @category 转换权限列表 
	 * 2014年9月11日 上午10:27:11 
	 * @author 朱晓陈
	 * @param tempRights
	 * @return
	 */
	public void toTreeList(List<Map> tempRights,List<Map> targetList, String startPid) {
		String curPid = null;
		for(int i=0;i<tempRights.size();i++){
			Map rr = tempRights.get(i);
			if(rr==null) {continue;}
			if(CommonUtil.isNullString(startPid) ){
				if(CommonUtil.isNullObject(rr.get("superiorRightId"))){
					targetList.add(rr);
					curPid = CommonUtil.Obj2String(rr.get("gid"));
					tempRights.set(i, null);
					toTreeList(tempRights, targetList, curPid);
				}
			}else if(startPid.equals(rr.get("superiorRightId"))){
				targetList.add(rr);
				curPid = CommonUtil.Obj2String(rr.get("gid"));
				tempRights.set(i, null);
				toTreeList(tempRights, targetList, curPid);
			}
		}
	}

	/**
	 * @category 是否含有相同的权限代码 
	 * 2014年9月12日 上午11:40:51 
	 * @author 朱晓陈
	 * @param rightCode
	 * @return
	 */
	public boolean hasSameCode(String rightCode,String rightId) {
		return rightDao.hasSameCode(rightCode,rightId);
	}

	/**
	 * @category 得到权限控制的范围 
	 * 2014年9月19日 上午8:43:26 
	 * @author 朱晓陈
	 * @return
	 */
	public String getRightRangeSwitch() {
		RM_Settings set = rightDao.getSettings("rightRange");
		return set.getParamValue();
	}

	public List<Map> getBtnCssTemplate() {
		List<Map> list = new ArrayList<Map>();
		/*String css = SysPropertites.get("buttonCss");
		String[] cs = css.split(";");
		for(String c:cs){
			String[] css_p = c.split(",");
			Map map = new HashMap();
			map.put("value", css_p[0]);
			map.put("name", css_p[1]);
			list.add(map);
		}*/
		return list;
	}

	/**
	 * @category 我的菜单收藏 
	 * 2014年10月10日 上午11:40:37 
	 * @author 朱晓陈
	 * @param userId
	 * @return
	 */
	public List<Map> getMenuFavorites(String userId) {
		return rightDao.getMenuFavorites(userId);
	}
	
	/**
	 * @category 添加收藏 
	 * 2014年10月10日 下午1:18:17 
	 * @author 朱晓陈
	 * @param mf
	 * @return
	 */
	public boolean addFavorite(RM_MenuFavorite mf){
		return rightDao.addFavorite(mf);
	}
	
	/**
	 * @category 更新收藏 
	 * 2014年10月10日 下午1:18:28 
	 * @author 朱晓陈
	 * @param mf
	 * @return
	 */
	public boolean updateFavorite(RM_MenuFavorite mf){
		return rightDao.updateFavorite(mf);
	}
	
	/**
	 * @category 删除收藏 
	 * 2014年10月10日 下午1:24:22 
	 * @author 朱晓陈
	 * @param mfId
	 * @return
	 */
	public boolean deleteFavorite(String mfId){
		return rightDao.deleteFavorite(mfId);
	}


}
