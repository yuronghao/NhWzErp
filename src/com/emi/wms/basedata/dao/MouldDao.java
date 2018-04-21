package com.emi.wms.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.WmAllocationstock;

public class MouldDao extends BaseDao {

	public PageBean getmouldList(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		match.put("aadepName", "Mould.aadepName");
		
		match.put("createName", "Mould.createName");
		match.put("modifyName", "Mould.modifyName");
		
		//String sql = "select "+CommonUtil.colsFromBean(Mould.class,"mould")+",aadepartment.depName aadepName FROM mould mould left join AA_Department aadepartment on aadepartment.gid = mould.department where 1=1 and mould.isDelete=0 ";
		String sql = "select "+CommonUtil.colsFromBean(Mould.class,"mould")+",yuc.username createName,yue.username modifyName FROM mould mould "
				+ " left join YM_User yuc on yuc.gid=mould.createUser "
				+ " left join YM_User yue on yue.gid=mould.modifyUser where 1=1 and mould.isDelete=0 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, Mould.class,"");
	}
	
	public boolean addmould(Mould mould) {
		return this.emiInsert(mould);
	}
	
	public Mould findmould(String gid) {
		return  (Mould)this.emiFind(gid, Mould.class);
	}
	
	public boolean updatemould(Mould mould) {
		return this.emiUpdate(mould);
	}

	public void deletemould(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "update mould set isDelete=1 where gid in ('"+processId+"')";
		this.update(sql);
	}
	
	
	
	public PageBean selectMouldListDatils(int pageIndex, int pageSize,String classifyGid,String condition){
		Map match = new HashMap();
		match.put("depName", "Mould.depName");
		match.put("customer", "Mould.customer");
		match.put("provider", "Mould.provider");
		match.put("classificationName", "Mould.classificationName");
		
		match.put("createName", "Mould.createName");
		match.put("modifyName", "Mould.modifyName");
		
		String sql= "select "+CommonUtil.colsFromBean(Mould.class,"Mould")+",a.depName depName,ab.pcName customer,ad.pcName provider,c.classificationName classificationName "
				+ " ,yuc.username createName,yue.username modifyName FROM mould mould"
				+ " LEFT JOIN AA_Department a on a.gid=mould.currentDeptGid"
				+ " LEFT JOIN AA_Provider_Customer ab on ab.gid= mould.cutomerGid "
				+ "LEFT JOIN AA_Provider_Customer ad on ad.gid= mould.providerGid  "
				+ "LEFT JOIN classify c on c.gid=mould.mouldStyle"
				+ " left join YM_User yuc on yuc.gid=mould.createUser "
				+ " left join YM_User yue on yue.gid=mould.modifyUser "
				+ " where mould.isDelete=0 ";
		if(!CommonUtil.isNullString(classifyGid)&&classifyGid!="0"){
			sql += "and mouldStyle='"+classifyGid+"'";
		}
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, Mould.class,match,"");
		
	}
	
	public List getClassifyList() {
		String sql = "select gid as id,isnull(depParentCode,0) as pId,depName name from AA_Department where isDel=0";
		
		return this.queryForList(sql);
	}
	
	public AaProviderCustomer selectCustomer(String customer){
		return (AaProviderCustomer)this.emiFind(customer, AaProviderCustomer.class);
	}

	public AaProviderCustomer selectCurrentDept(String providerGid){
		return (AaProviderCustomer)this.emiFind(providerGid, AaProviderCustomer.class);
	}
	
	public AaDepartment selectDepartment(String currentDeptGid){
		return (AaDepartment)this.emiFind(currentDeptGid, AaDepartment.class);
	}

	public Classify selectMouldStyle(String mouldStyleId){
		return (Classify)this.emiFind(mouldStyleId, Classify.class);
	}
}
