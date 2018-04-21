package com.emi.wms.basedata.dao;

import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.Classify;

public class CusproDao extends BaseDao {

	public List getClassifyList(String conditionSql) {
		String sql = "select gid as id,isnull(parentid,0) as pId,classificationName as name,styleGid type from classify where 1=1 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForList(sql);
	}

	public PageBean getcusproList(int pageIndex, int pageSize,String conditionSql) {
		String sql = "select "+CommonUtil.colsFromBean(AaProviderCustomer.class,"AaProviderCustomer")+" FROM AA_Provider_Customer AaProviderCustomer where 1=1 and AaProviderCustomer.isDel=0 or AaProviderCustomer.isDel is null";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		System.out.println(sql);
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaProviderCustomer.class,"");
	}
	
	public List getCategoryList(String conditionSql) {
		String sql = "select * from AA_Soulation where 1=1 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForList(sql);
	}
	
	public boolean deletebooks(String exhTypeId) {
		try {
			String sql = "delete from AA_Provider_Customer_AddBook where pcGid='"+exhTypeId+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addprocus(AaProviderCustomer AaProviderCustomer) {
		return this.emiInsert(AaProviderCustomer);
	}
	
	public boolean addprocusbook(List list) {
		return this.emiInsert(list);
	}
	public Map findCuspro(String exhTypeId) {
		String sql="select *,classify.classificationName customerName,classify1.classificationName providerName from AA_Provider_Customer aaProviderCustomer left join classify classify on aaProviderCustomer.customerId=classify.gid  left join classify classify1 on aaProviderCustomer.providerId=classify1.gid where aaProviderCustomer.gid='"+exhTypeId+"'";
		return  this.queryForMap(sql);
	}
	
	public boolean updateCuspro(AaProviderCustomer AaProviderCustomer) {
		return this.emiUpdate(AaProviderCustomer);
	}
	

	public boolean deleteCuspro(String[] strsums) {
		try {
			String[] sqls = new String[strsums.length];
			for (int i = 0; i < strsums.length; i++) {
				String sql = "update AA_Provider_Customer set isDel='1' where gid='"+ strsums[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List getcusprobookList(String exhTypeId) {
		String sql="select * from AA_Provider_Customer_AddBook where pcGid='"+exhTypeId+"'";
		return  this.queryForList(sql);
	}
}
