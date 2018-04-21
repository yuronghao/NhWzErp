package com.emi.wms.basedata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.GroupDao;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaProviderCustomer;

public class GroupService {

	private GroupDao groupDao;

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public PageBean getgroupList(int pageIndex, int pageSize,String conditionSql) {
		return groupDao.getgroupList(pageIndex, pageSize,conditionSql);
	}
	public boolean addgroup(AaGroup aagroup) {
		return groupDao.addgroup(aagroup);
	}
	public boolean addgrouplist(List list) {
		return groupDao.addgrouplist(list);
	}
	public Map findgroup(String gid) {
		return groupDao.findgroup(gid);
	}
	public boolean updategroup(AaGroup aagroup) {
		return groupDao.updategroup(aagroup);
	}
	public void deletegroup(String gid) {
		//1、删除主表（假删除）
		groupDao.deletegroup(gid);
	}
	public List getUserList(int pageIndex,int pageSize,String condition){
		return groupDao.getUserList( pageIndex, pageSize,condition);
	}
	public void deletegroupperson(String gid){
		groupDao.deletegroupperson(gid);
	}
	public Map getRoleUsersMap(String exhHallId) {
		List<Map> list = groupDao.getUsersByRole(exhHallId);
		Map map = new HashMap();
		String ids = "";
		String userNames = "";
		String gids = "";
		for(Map ru : list){
			ids += ru.get("userId")+",";
			userNames += ru.get("cUserName")+",";
			gids += ru.get("gid")+",";
		}
		if(ids.length()>0){
			ids = ids.substring(0,ids.length()-1);
			userNames = userNames.substring(0,userNames.length()-1);
			gids = gids.substring(0,gids.length()-1);
		}
		map.put("ids", "null".equals(ids)?"":ids);
		map.put("names", "null".equals(userNames)?"":userNames);
		map.put("gids", gids);
		return map;
	}
}
