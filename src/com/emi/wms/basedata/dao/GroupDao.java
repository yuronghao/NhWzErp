package com.emi.wms.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.Classify;

public class GroupDao extends BaseDao {

	public PageBean getgroupList(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		match.put("wcname", "AaGroup.wcname");
		String sql = "select "+CommonUtil.colsFromBean(AaGroup.class,"AaGroup")+",aaworkcenter.wcname wcname FROM AA_Group AaGroup left join MES_AA_WorkCenter aaworkcenter on aaworkcenter.gid = AaGroup.workcenterId where 1=1 and AaGroup.isDelete=0";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaGroup.class,match,"");
	}
	
	public boolean addgroup(AaGroup aagroup) {
		return this.emiInsert(aagroup);
	}
	
	public boolean addgrouplist(List list) {
		return this.emiInsert(list);
	}
	
	public Map findgroup(String gid) {
		String sql ="select aagroup.gid gid,aagroup.code code,aagroup.barcode barcode,aagroup.groupname groupname,aagroup.workcenterId workcenterId,aaworkcenter.wcname wcname from AA_Group aagroup left join MES_AA_WorkCenter aaworkcenter on aagroup.workcenterId=aaworkcenter.gid where aagroup.gid='"+gid+"'";
		return  this.queryForMap(sql);
	}
	
	public boolean updategroup(AaGroup aagroup) {
		return this.emiUpdate(aagroup);
	}

	public void deletegroup(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "update AA_Group set isDelete=1 where gid in ('"+processId+"')";
		this.update(sql);
	}
	
	public List getUserList(int pageIndex,int pageSize,String condition) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from AA_Person ");
		sql.append("where 1=1 "+condition);
		
		return this.queryForList(sql.toString(), pageIndex, pageSize,"pk asc");
	}
	
	public void deletegroupperson(String gid){
		String sql = "delete from groupperson where groupgid ='"+gid+"'";
		this.update(sql);
	}
	public List getUsersByRole(String exhHallId) {
		String sql = "select  groupperson.gid gid,aaperson.gid userId,aaperson.perName cUserName  from groupperson groupperson left join AA_Person aaperson on aaperson.gid = groupperson.persongid where groupgid = '"+exhHallId+"'";
		return this.queryForList(sql);
	}
	
}
