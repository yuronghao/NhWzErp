package com.emi.wms.basedata.dao;

import java.util.HashMap;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaReason;

public class ReasonDao extends BaseDao {

	public PageBean getreasonList(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		String sql = "select "+CommonUtil.colsFromBean(AaReason.class,"Reason")+" FROM AA_Reason Reason where 1=1 and isDelete = 0 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaReason.class,match,"");
	}
	
	public boolean addreason(AaReason reason) {
		return this.emiInsert(reason);
	}
	
	public AaReason findreason(String gid) {
		return  (AaReason)this.emiFind(gid, AaReason.class);
	}
	
	public boolean updatereason(AaReason reason) {
		return this.emiUpdate(reason);
	}

	public void deletereason(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "update AA_Reason set isDelete=1 where gid in ('"+processId+"')";
		this.update(sql);
	}
}
