package com.emi.rm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.emi.common.util.CommonUtil;
import com.emi.common.util.PasswordUtil;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_UserRight;
import com.emi.rm.dao.UserRightDao;
import com.emi.wms.bean.YmUser;
@SuppressWarnings({"unchecked","rawtypes"})
public class UserRightService {
	private UserRightDao userRightDao;

	public UserRightDao getUserRightDao() {
		return userRightDao;
	}

	public void setUserRightDao(UserRightDao userRightDao) {
		this.userRightDao = userRightDao;
	}

	/**
	 * @category 保存用户自定义权限 
	 * 2014年9月1日 下午4:51:18 
	 * @author 朱晓陈
	 * @param userId
	 * @param rightIds_add
	 * @param rightIds_del
	 * @param enterpriseId
	 * @return
	 */
	public boolean saveUserRight(String userId, String rightIds_add,
			String rightIds_del,String enterpriseId) {
		RM_UserRight userRight = null;
		List<RM_UserRight> list = new ArrayList<RM_UserRight>();	//角色用户列表
		String[] rights_add = rightIds_add.split(",");
		for(String rightId : rights_add){
			userRight = new RM_UserRight();
			userRight.setUserId(userId);
			userRight.setRightId(rightId);
			list.add(userRight);
		}
		boolean del_suc = userRightDao.deleteUserRight(userId,rightIds_del);
		boolean add_suc = userRightDao.addUserRight(list);
		return del_suc && add_suc;
	}

	/**
	 * @category 用户的所有权限id 
	 * 2014年9月1日 下午4:51:34 
	 * @author 朱晓陈
	 * @param userId
	 * @param enterpriseId
	 * @return
	 */
	public String getRightIds4User(String userId,String sysName) {
		String rightIds = "";
		List<RM_Right> list_right = userRightDao.getUserRightFromRole(userId,sysName,false);
//		List<RM_Right> list_ur = userRightDao.getUserRights(userId,enterpriseId);
//		list_right.addAll(list_ur);
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
	 * @category 用户的所有权限 
	 * 2014年9月1日 下午4:51:48 
	 * @author 朱晓陈
	 * @param userId
	 * @param enterpriseId
	 * @return
	 */
	public List<RM_Right> getRights4User(String userId,String sysName,boolean showHide) {
		List<RM_Right> list_right = userRightDao.getUserRightFromRole(userId,sysName,showHide);
//		List<RM_Right> list_ur = userRightDao.getUserRights(userId,enterpriseId);
//		list_right.addAll(list_ur);
		return list_right;
	}

	/**
	 * @category 权限list转成层次结构的json 
	 * 2014年9月17日 下午3:02:48 
	 * @author 朱晓陈
	 * @param rights
	 * @return
	 */
	public String rightsToJson(List<RM_Right> rights) {
		List<Map<String,Object>> jsonlist = CommonUtil.hierarchicalChild(rights, "gid", "superiorRightId");
		return JSONArray.fromObject(jsonlist).toString();
	}
	
	public List<Map<String,Object>> rightsToTree(List<RM_Right> rights) {
		List<Map<String,Object>> jsonlist = CommonUtil.hierarchicalChild(rights, "gid", "superiorRightId");
		return jsonlist;
	}

	/**
	 * @category  
	 * 2014年9月22日 下午5:46:32 
	 * @author 朱晓陈
	 * @param userId
	 * @param sysnameWms
	 * @return
	 */
	public Map getRightFuns(String userId, String sysName) {
		Map map = new HashMap();//权限代码为key，最大功能权值为值
		List<Map> funList = userRightDao.getRightFuns(userId,sysName);
		//循环，将同权限的功能权值做位运算 或
		for(Map m : funList){
			int curFun = m.get("useFuns")==null?0:Integer.parseInt(m.get("useFuns").toString());
			String curRightCode = CommonUtil.Obj2String(m.get("rightCode"));
			if(map.get(curRightCode)==null){
				map.put(curRightCode, curFun);
			}else{
				int fun = Integer.parseInt(map.get(curRightCode).toString());
				map.put(curRightCode, curFun|fun);
			}
		}
		return map;
	}
	
	public boolean checkPassword(String userId, String oldPassword) {
		boolean flag = false;
		String secPwd = PasswordUtil.generatePassword(oldPassword);
		YmUser user = userRightDao.getUserById(userId);
		if(secPwd!=null && secPwd.equals(user.getPassWord())){
			flag = true;
		}
		return flag;
	}

	public boolean updateUser(YmUser user) {
		return userRightDao.updateUser(user);
	}

	//(新)功能map
	public Map<String,List<RM_Right>> getFunRight(String userId, String sysName,List<RM_Right> rights) {
		List<RM_Right> funRightList = userRightDao.getUserRightFromRole(userId, sysName, 1,false);
		Map<String,List<RM_Right>> map = new HashMap<String,List<RM_Right>>();//权限代码为key，含有的功能list是值
		for(RM_Right right : funRightList){
			String superiorGid = CommonUtil.Obj2String(right.getSuperiorRightId());
			String menuCode = "0";
			for(RM_Right f_right : rights){
				if(f_right.getGid().equals(superiorGid)){
					menuCode = f_right.getRightCode();
				}
			}
			//以模块编码作为key
			if(map.get(menuCode)==null){
				List<RM_Right> subRights = new ArrayList<RM_Right>();
				subRights.add(right);
				map.put(menuCode, subRights);
			}else{
				List<RM_Right> subRights = (List<RM_Right>) map.get(menuCode);
				subRights.add(right);
				map.put(menuCode, subRights);
			}
		}
		return map;
	}
}
