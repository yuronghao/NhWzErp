package com.emi.wms.servicedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.MesWmAccountinginform;
import com.emi.wms.bean.WmPurchaserequisition;
import com.emi.wms.bean.YmUser;

public class RequisitionDao extends BaseDao {

	public List getClassifyList(String conditionSql) {
		String sql = "select gid as id,parentid as pId,orgName as name from AA_Org where 1=1 and isDel='0'";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForList(sql);
	}

	public Map getorganizeInfo(String conditionSql) {
		String sql = "select * FROM AA_Org where 1=1 and isDel=0";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForMap(sql);
	}
	
	public List getCategoryList(String conditionSql) {
		String sql = "select * from AA_Category where 1=1 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForList(sql);
	}
	
	public boolean deletebooks(String exhTypeId) {
		try {
			String sql = "delete from WM_PurchaseRequisition_C where purchaseRequisitionUid='"+exhTypeId+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addorg(AaOrg aaorg) {
		return this.emiInsert(aaorg);
	}
	public boolean addrequisition(WmPurchaserequisition WmPurchaserequisition) {
		return this.emiInsert(WmPurchaserequisition);
	}
	public boolean addprocusbook(List list) {
		return this.emiInsert(list);
	}
	public Map findorg(String exhTypeId) {
		String sql="select * from AA_Org where gid='"+exhTypeId+"'";
		return  this.queryForMap(sql);
	}
	
	public boolean updateorg(AaOrg aaorg) {
		return this.emiUpdate(aaorg);
	}
	
	public boolean updaterequisition(WmPurchaserequisition WmPurchaserequisition) {
		return this.emiUpdate(WmPurchaserequisition);
	}

	public boolean updateymuser(YmUser YmUser) {
		return this.emiUpdate(YmUser);
	}
	
	public boolean deleterequisition(String orgclassid) {
		try {
				String sql = "update AA_Org set isDel='1' where gid='"+orgclassid+ "'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List getrequisitionbookList(String exhTypeId) {
		String sql="select * from AA_Provider_Customer_AddBook where pcGid='"+exhTypeId+"'";
		return  this.queryForList(sql);
	}
	public boolean findorgchild(String orgclassid){
		String sql = "select count(*) from AA_Org where parentid = '"+orgclassid+"'";
		int count = this.queryForInt(sql);
		if(count!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public Map findrequisition(String requisitiongid) {
		String sql="";
		if(CommonUtil.isNullString(requisitiongid)){
			sql="SELECT TOP 1 purchase.gid purchasegid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName,* FROM WM_PurchaseRequisition purchase left join AA_Person person on person.gid = purchase.salesmanUid left join AA_Department department on department.gid = purchase.departmentUid left join YM_User ymuser on ymuser.gid = purchase.recordPersonUid left join YM_User ymuser1 on ymuser1.gid = purchase.auditPersonUid  ORDER BY purchase.pk DESC";
		}else{
			sql="SELECT purchase.gid purchasegid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName,* FROM WM_PurchaseRequisition purchase left join AA_Person person on person.gid = purchase.salesmanUid left join AA_Department department on department.gid = purchase.departmentUid left join YM_User ymuser on ymuser.gid = purchase.recordPersonUid left join YM_User ymuser1 on ymuser1.gid = purchase.auditPersonUid where purchase.gid = '"+requisitiongid+"' order by purchase.pk desc";
		}
		
		return  this.queryForMap(sql);
	}
	
	public void setrequisitionEnable(int enable, String id) {
		String sql = "update MES_WM_AccountingInform set state="+enable+" where gid='"+id+"'";
		this.update(sql);
	}
	
	public boolean addrequisitionc(List list) {
		return this.emiInsert(list);
	}
	public boolean updaterequisitionc(List list) {
		return this.emiUpdate(list);
	}
	public List getrequisitionlist(String purchaseRequisitionUid) {
		String sql = "select * from WM_PurchaseRequisition_C where purchaseRequisitionUid='"+purchaseRequisitionUid+"'";
		return this.queryForList(sql);
	}
}
