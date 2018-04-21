package com.emi.wms.basedata.dao;

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
import com.emi.wms.bean.YmUser;

public class SobDao extends BaseDao {

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
			String sql = "delete from AA_Provider_Customer_AddBook where pcGid='"+exhTypeId+"'";
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
	public boolean addsob(MesWmAccountinginform MesWmAccountinginform) {
		return this.emiInsert(MesWmAccountinginform);
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
	
	public boolean updatesob(MesWmAccountinginform MesWmAccountinginform) {
		return this.emiUpdate(MesWmAccountinginform);
	}

	public boolean updateymuser(YmUser YmUser) {
		return this.emiUpdate(YmUser);
	}
	
	public boolean deletesob(String orgclassid) {
		try {
				String sql = "update AA_Org set isDel='1' where gid='"+orgclassid+ "'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List getsobbookList(String exhTypeId) {
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
	
	public Map findsob(String sobgid) {
		String sql="";
		if(CommonUtil.isNullString(sobgid)){
			sql="select top 1 sob.gid sobgid,ymuser.gid ymusergid,aaorg.gid aaorggid,* from MES_WM_AccountingInform sob left join AA_Org aaorg on aaorg.gid=sob.orgId left join YM_User ymuser on ymuser.sobgId=sob.gid order by sob.pk desc";
		}else{
			sql="select sob.gid sobgid,ymuser.gid ymusergid,aaorg.gid aaorggid,* from MES_WM_AccountingInform sob left join AA_Org aaorg on aaorg.gid=sob.orgId left join YM_User ymuser on ymuser.sobgId=sob.gid where sob.gid = '"+sobgid+"' order by sob.pk desc";
		}
		
		return  this.queryForMap(sql);
	}
	
	public void setsobEnable(int enable, String id) {
		String sql = "update MES_WM_AccountingInform set state="+enable+" where gid='"+id+"'";
		this.update(sql);
	}
	
	public boolean addymuser(YmUser YmUser) {
		return this.emiInsert(YmUser);
	}
	public PageBean getsoblist(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		match.put("orgname", "MesWmAccountinginform.orgname");
		String sql = "select "+CommonUtil.colsFromBean(MesWmAccountinginform.class,"MesWmAccountinginform")+",aaorg.orgName orgname FROM MES_WM_AccountingInform MesWmAccountinginform "
				+ " left join AA_Org aaorg on aaorg.gid = MesWmAccountinginform.orgId where 1=1 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, MesWmAccountinginform.class,match,"");
	}
	public List getsystemsetting(){
		String sql = "select * from YM_Settings ";
		return this.queryForList(sql);
	}
	
	public boolean updatesystemsetting(String interfaceType,String interfaceAddress,String cacheserverIp,String cacheport,String printfile,String printmachine,String headCheckFlag,String bodyCheckFlag,String isReportExceedDis){
		try {
			String[] sqls = new String[6];
			sqls[0] = "update YM_Settings set paramValue = '"+interfaceType+"' where setName = 'interfaceType'";
			sqls[1] = "update YM_Settings set paramValue = '"+interfaceAddress+"' where setName = 'interfaceAddress'";
			sqls[2] = "update YM_Settings set paramValue = '"+printfile+"' where setName = 'printfile'";
			/*sqls[3] = "update YM_Settings set paramValue = '"+cacheserverIp+"' where setName = 'cacheserverIp'";
			sqls[4] = "update YM_Settings set paramValue = '"+cacheport+"' where setName = 'cacheport'";
			sqls[5] = "update YM_Settings set paramValue = '"+printmachine+"' where setName = 'printmachine'";*/
			sqls[3] = "update YM_Settings set paramValue = '"+headCheckFlag+"' where setName = 'headCheckFlag'";
			sqls[4] = "update YM_Settings set paramValue = '"+bodyCheckFlag+"' where setName = 'bodyCheckFlag'";
			sqls[5] = "update YM_Settings set paramValue = '"+isReportExceedDis+"' where setName = 'isReportExceedDis'";
			this.bathUpdate(sqls);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
