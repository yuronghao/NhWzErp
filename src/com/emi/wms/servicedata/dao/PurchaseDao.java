package com.emi.wms.servicedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.*;

public class PurchaseDao extends BaseDao {

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
			String sql = "delete from WM_Purchasepurchase_C where purchasepurchaseUid='"+exhTypeId+"'";
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
	public boolean addpurchase(WmProcureorder WmProcureorder) {
		return this.emiInsert(WmProcureorder);
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
	
	public boolean updatepurchase(WmProcureorder WmProcureorder) {
		return this.emiUpdate(WmProcureorder);
	}

	public boolean updateymuser(YmUser YmUser) {
		return this.emiUpdate(YmUser);
	}
	
	public boolean deletepurchase(String orgclassid) {
		try {
				String sql = "update AA_Org set isDel='1' where gid='"+orgclassid+ "'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List getpurchasebookList(String exhTypeId) {
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
	
	public Map findpurchase(String purchasegid) {
		String sql="";
		if(CommonUtil.isNullString(purchasegid)){
			sql="SELECT TOP 1 purchase.gid purchasegid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,purchase.*,pt.cPTName,procus.pcName,department.depName,person.perName FROM WM_ProcureOrder purchase " +
					" LEFT JOIN AA_Person person ON person.gid = purchase.personUid " +
					" LEFT JOIN AA_Department department ON department.gid = purchase.departmentUid " +
					" LEFT JOIN YM_User ymuser ON ymuser.gid = purchase.recordPersonUid " +
					" LEFT JOIN YM_User ymuser1 ON ymuser1.gid = purchase.auditPersonUid " +
					" left join AA_Provider_Customer procus on procus.gid=purchase.supplierUid " +
					" left join AA_PurchaseType pt on pt.gid = purchase.procureType  " +
					" ORDER BY purchase.pk DESC";
		}else{
			sql=" SELECT purchase.gid purchasegid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,purchase.*,pt.cPTName,procus.pcName,department.depName,person.perName " +
					" FROM WM_ProcureOrder purchase " +
					" LEFT JOIN AA_Person person ON person.gid = purchase.personUid " +
					" LEFT JOIN AA_Department department ON department.gid = purchase.departmentUid " +
					" LEFT JOIN YM_User ymuser ON ymuser.gid = purchase.recordPersonUid " +
					" LEFT JOIN YM_User ymuser1 ON ymuser1.gid = purchase.auditPersonUid " +
					" left join AA_Provider_Customer procus on procus.gid=purchase.supplierUid " +
					" left join AA_PurchaseType pt on pt.gid = purchase.procureType  " +
					" where purchase.gid = '"+purchasegid+"' " +
					" order by purchase.pk desc";
		}
		
		return  this.queryForMap(sql);
	}
	
	public void setpurchaseEnable(int enable, String id) {
		String sql = "update MES_WM_AccountingInform set state="+enable+" where gid='"+id+"'";
		this.update(sql);
	}
	
	public boolean addpurchasec(List list) {
		return this.emiInsert(list);
	}
	public boolean updatepurchasec(List list) {
		return this.emiUpdate(list);
	}
	public List getpurchaselist(String purchasepurchaseUid) {
		String sql = "select * from WM_ProcureOrder_C where procureOrderUid='"+purchasepurchaseUid+"'";
		return this.queryForList(sql);
	}

    public PageBean getOtherWarehouseList(int pageIndex, int pageSize, String condition) {
		Map match = new HashMap();
		match.put("goodsUid", "goodsUid");
		match.put("number", "number");
		String sql ="select wp.*,wpc.goodsUid,wpc.number from WM_Procureorder wp left join WM_ProcureOrder_C wpc on wpc.procureOrderUid = wp.gid where 1=1";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		String sortSql="billDate desc";
		return this.emiQueryList(sql, pageIndex, pageSize, WmProcureorder.class,match,sortSql);

    }

	public List getpurchaseTypelist() {
		String sql = "  select * from AA_PurchaseType; ";
		return this.queryForList(sql);

	}
}
